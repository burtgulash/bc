import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Betweeness {
	public static ResultRow[] compute(Graph g, int C, boolean verbose) {
		int n = g.vSize();
		ResultRow[] result = new ResultRow[n];
		double[] betweeness = new double[n];

		int[][] links = Links.getOuts(g);

		Random r = new Random();
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
		// for (int s = 0; s < n; s++) {
		for (int i = 0; i < n / C; i++, k++) {
			int s = shuffled[i];

			// List of predecessors.
			Stack[] p = new Stack[n];
			Stack stack = new Stack();
			int[] sigma = new int[n];
			int[] d = new int[n];

			for (int v = 0; v < n; v++) {
				p[v] = new Stack();
				d[v] = -1;
			}
			sigma[s] = 1;
			d[s] = 0;

			Queue<Integer> q = new LinkedList<Integer>();

			
			q.offer(s);
			while (!q.isEmpty()) {
				int v = q.poll();
				stack.push(v);
				

				for (int w : links[v]) {
					if (d[w] == -1) {
						q.offer(w);
						d[w] = d[v] + 1;
					}

					if (d[w] == d[v] + 1) {
						sigma[w] += sigma[v];
						p[w].push(v);
					}
				}
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
				if (i % 10000 == 0)
					System.out.println("Betweeness: " + i + "/" + n);
		}

		for (int s = 0; s < n; s++) {
			betweeness[s] = (n * betweeness[s]) / k;
//			// normalize
//			betweeness[s] /= ((double) n - 1d) * ((double) n - 2d) / 2d;
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
}