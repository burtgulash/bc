import java.util.LinkedList;
import java.util.Queue;

public class Betweeness {
	public static ResultRow[] compute(Graph g) {
		int n = g.vSize();
		ResultRow[] result = new ResultRow[n];
		double[] betweeness = new double[n];

		int[][] links = new int[n][];
		Vertex[] vs = g.getVertices().toArray(new Vertex[n]);

		for (Vertex node : vs) {
			links[node.id] = new int[node.outdegree()];

			int k = 0;
			for (Edge e : node.outs)
				links[node.id][k++] = e.end;
		}

		for (int s = 0; s < n; s++) {
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

			System.out.println("Betweeness: " + s + "/" + n);
		}

		for (int s = 0; s < n; s++)
			result[s] = new ResultRow(g.getVertexName(s), betweeness[s]);

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