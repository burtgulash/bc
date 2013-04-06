import java.util.LinkedList;
import java.util.Queue;

public class Closeness {
	public static ResultRow[] compute(Graph g, boolean in, boolean verbose) {
		int n = g.vSize();
		ResultRow[] result = new ResultRow[n];
		double nPaths = 0;
		double diameter = 0;
		double total_length = 0;

		// FIXME Closeness on IN-edges!!!
		int[][] links;
		if (in)
			links = Links.getIns(g);
		else
			links = Links.getOuts(g);

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
				if (cur.w > diameter)
					diameter = cur.w;
				nPaths ++;

				for (int e : links[cur.to]) {
					if (!visited[e]) {
						q.offer(new E(e, cur.w + 1));
						visited[e] = true;
					}
				}
			}
			
			total_length += farness;

			double closeness = 0;
			if (farness > 0)
				closeness = (n - 1) / farness;

			result[v] = new ResultRow(g.getVertexName(v), closeness);
		}
		
		System.out.println("Average shortest path: " + (total_length / nPaths));
		System.out.println("Diameter: " + diameter);

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