import java.util.LinkedList;
import java.util.Queue;

public class Closeness {
	public static ResultRow[] compute(Graph g, boolean verbose) {
		int n = g.vSize();
		ResultRow[] result = new ResultRow[n];

		// FIXME Closeness on IN-edges!!!
		int[][] links = Links.getIns(g);

		for (int v = 0; v < n; v++) {
			if (verbose)
				if (v % 10000 == 0)
					System.out.println("Closeness: " + v + " / " + n);
			double farness = 0;

			Queue<E> q = new LinkedList<E>();
			boolean[] visited = new boolean[n];

			q.offer(new E(v, 0));
			visited[v] = true;

			while (!q.isEmpty()) {
				E cur = q.poll();

				// Update closeness/farness here
				farness += cur.w;

				for (int e : links[cur.to]) {
					if (!visited[e]) {
						q.offer(new E(e, cur.w + 1));
						visited[e] = true;
					}
				}
			}

			double closeness = 0;
			if (farness > 0)
				closeness = (n - 1) / farness;

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