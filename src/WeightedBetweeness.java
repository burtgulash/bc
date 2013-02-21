import java.util.Random;

public class WeightedBetweeness {
	public static ResultRow[] compute(Graph g, int C, boolean verbose) {
		int n = g.vSize();
		ResultRow[] result = new ResultRow[n];
		double[] betweeness = new double[n];

		int[][] links = Links.getOuts(g);
		double[][] ws = Links.getReciprocalWeightsOutEdges(g);

		Random r = new Random(System.currentTimeMillis());
		int[] shuffled = new int[n];
		for (int i = 0; i < n; i++)
			shuffled[i] = i;

		for (int i = n - 1; i > 0; i--) {
			int rand = r.nextInt(i);
			int tmp = shuffled[i];
			shuffled[i] = shuffled[rand];
			shuffled[rand] = tmp;
		}

		int k = 0;
		for (int i = 0; i < n; i++) {
			int s = shuffled[i];

			// Stopping condition for approximation.
			if (betweeness[s] > C * n)
				break;
			k++;

			// List of predecessors.
			Stack[] p = new Stack[n];
			Stack stack = new Stack();
			int[] sigma = new int[n];
			double[] d = new double[n];

			boolean[] visited = new boolean[n];

			for (int v = 0; v < n; v++) {
				p[v] = new Stack();
				d[v] = -1d;
			}
			sigma[s] = 1;
			d[s] = 0d;

			PQueue<E> q = new PQueue<E>();

			q.insert(new E(s, 0));
			try {
				while (!q.empty()) {
					E cur = q.extractMin();
					int v = cur.to;
					visited[v] = true;

					assert (cur.w == d[v]);
					stack.push(v);

					for (int j = 0; j < links[v].length; j++) {
						int w = links[v][j];

						double newDist = d[v] + ws[v][j];
						// double newDist = d[v] + 1;
						if (d[w] < 0) {
							d[w] = newDist;
							q.insert(new E(w, d[w]));
						} else if (newDist < d[w]) {
							assert (!visited[w]);
							d[w] = newDist;
							q.decreasePriorityAndContainsTest(w, d[w]);
						}

						if (d[w] == d[v] + ws[v][j]) {
							// if (d[w] == d[v] + 1) {
							sigma[w] += sigma[v];
							p[w].push(v);
						}
					}
				}
			} catch (PQueue.EmptyQueueException e) {
				e.printStackTrace();
			}

			double[] delta = new double[n];
			while (!stack.isEmpty()) {
				int w = stack.pop();
				for (Stack.ListNode node = p[w].root; node != null; node = node.next) {
					int v = node.x;
					delta[v] += ((double) sigma[v] / (double) sigma[w])
							* (1d + delta[w]);
					if (w != s)
						betweeness[w] += delta[v];
				}
			}

			if (verbose)
				System.out.println("Weighted Betweeness: " + i + "/" + n);
		}

		for (int s = 0; s < n; s++) {
			betweeness[s] = (n * betweeness[s]) / k;
			result[s] = new ResultRow(g.getVertexName(s), betweeness[s]);
		}

		return result;
	}

	private static class Stack {
		ListNode root;

		private boolean isEmpty() {
			return root == null;
		}

		private void push(int x) {
			if (root == null) {
				root = new ListNode();
				root.x = x;
			} else
				root.add(x);
		}

		private int pop() {
			ListNode popped = root;
			root = popped.next;
			return popped.x;
		}

		private class ListNode {
			int x;
			ListNode next;

			private void add(int x) {
				ListNode n = new ListNode();
				n.x = x;
				n.next = next;
				next = n;
			}
		}
	}

	private static class E implements Queable {
		int to;
		double w;

		E(int to, double w) {
			this.to = to;
			this.w = w;
		}

		@Override
		public int id() {
			return to;
		}

		@Override
		public double priority() {
			return w;
		}

		@Override
		public void setPriority(double newPriority) {
			w = newPriority;
		}
	}
}
