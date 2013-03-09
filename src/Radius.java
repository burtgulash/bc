import java.util.LinkedList;
import java.util.Queue;

public class Radius {
	public static ResultRow[] compute(Graph g, boolean verbose) {
		int n = g.vSize();
		ResultRow[] result = new ResultRow[n];

		int[][] links = Links.getOuts(g);

		double max_radius = 0;
		for (int v = 0; v < n; v++) {
			if (verbose)
				if (v % 10000 == 0)
					System.out.println("Radius: " + v + " / " + n);

			Queue<E> q = new LinkedList<E>();
			boolean[] visited = new boolean[n];
			double radius = 0;

			q.offer(new E(v, 0));
			visited[v] = true;

			while (!q.isEmpty()) {
				E cur = q.poll();

				if (cur.w > radius)
					radius = cur.w;

				for (int e : links[cur.to]) {
					if (!visited[e]) {
						q.offer(new E(e, cur.w + 1));
						visited[e] = true;
					}
				}
			}

			if (radius > max_radius)
				max_radius = radius;
			result[v] = new ResultRow(g.getVertexName(v), radius);
		}

			System.out.println("Max radius of the graph: " + max_radius);
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
