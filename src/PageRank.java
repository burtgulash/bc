public class PageRank {
	private static final int ITERATIONS = 20;
	private static final double d = .85;

	public static ResultRow[] compute(Graph g) {
		int n = g.vSize();
		int i, i1, i2;
		int v;
		double[][] pr = new double[2][n];
		ResultRow[] result = new ResultRow[n];
		int[][] inLinks = new int[n][];
		double[] outDeg = new double[n];
		
		// Fill inlinks.
		for (Vertex vertex : g.getVertices()) {
			outDeg[vertex.id] = vertex.outdegree();
			inLinks[vertex.id] = new int[vertex.indegree()];
			int ei = 0;
			for (Edge e : vertex.ins)
				inLinks[vertex.id][ei++] = e.end;
		}
		
		// ------------------------------------------------------
		// ---------------------- PageRank ----------------------
		// ------------------------------------------------------
		i = i1 = i2 = 0;

		// Fill initial probabilities to 1/n for each vertex
		for (v = 0; v < n; v++)
			pr[0][v] = 1.0 / (double) n;

		// PageRank iterations
		for (; i < ITERATIONS; i++) {

			i1 = i & 1;
			i2 = (i + 1) & 1;
			double dang_sum = 0;

			// Dangling nodes
			for (v = 0; v < n; v++)
				if (outDeg[v] == 0)
					dang_sum += pr[i1][v];
			dang_sum /= (double) n;

			// Initial pr = 0 + dang_sum
			for (v = 0; v < n; v++)
				pr[i2][v] = dang_sum;

			// PageRank
			for (v = 0; v < n; v++)
				for (int k = 0; k < inLinks[v].length; k++)
					pr[i2][v] += pr[i1][inLinks[v][k]] / outDeg[inLinks[v][k]];

			// Add damping factor
			for (v = 0; v < n; v++)
				pr[i2][v] = (1.0 - d) / (double) n + d * pr[i2][v];
		}

		// Populate result rows
		// Note: results are multiplied by n
		for (v = 0; v < n; v++)
			result[v] = new ResultRow(g.getVertexName(v), pr[i2][v] * n);
		
		return result;
	}
}
