import java.util.LinkedList;
import java.util.Queue;

public class Closeness {
	private static final int NIL = -1;

	private static ResultRow[] result;

	private static int[][] links;
	private static Vertex[] nodes;

	public static ResultRow[] compute(Graph g) {
		int n = g.vSize();
		result = new ResultRow[n];

		links = new int[n][];
		nodes = g.getVertices().toArray(new Vertex[n]);

		for (Vertex node : nodes) {
			links[node.id] = new int[node.outdegree()];

			int k = 0;
			for (Edge e : node.outs)
				links[node.id][k++] = e.end;
		}

		for (int v = 0; v < n; v++) {
			double closeness = 0;

			Queue<E> q = new LinkedList<E>();
			int[] previous = new int[n];
			boolean[] visited = new boolean[n];

			for (int j = 0; j < n; j++) {
				previous[j] = NIL;
				visited[j] = false;
			}

			q.offer(new E(v, 0));
			visited[v] = true;

			while (!q.isEmpty()) {
				E cur = q.poll();

				// Update closeness/farness here
				if (cur.w > 0)
					closeness += 1d / cur.w;

				for (int e : links[cur.to]) {
					if (!visited[e]) {
						q.offer(new E(e, cur.w + 1));
						previous[e] = cur.to;
						visited[e] = true;
					}
				}
			}
			
			// normalize by dividing by maximum attainable amount (n - 1).
			// FIXME: uncomment line below
//			closeness /= (double) (n - 1);

			result[v] = new ResultRow(g.getVertexName(v), closeness);
		}

		return result;
	}

	private static class E {
		int to, w;

		private E(int vertex, int d) {
			this.to = vertex;
			this.w = d;
		}
	}
}