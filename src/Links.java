public class Links {
	public static int[][] getOuts(Graph g) {
		int[][] links = new int[g.vSize()][];

		for (Vertex v : g.getVertices()) {
			links[v.id] = new int[v.outdegree()];

			int m = 0;
			for (Edge e : v.outs)
				links[v.id][m++] = e.end;
		}

		return links;
	}

	public static int[][] getIns(Graph g) {
		int[][] links = new int[g.vSize()][];

		for (Vertex v : g.getVertices()) {
			links[v.id] = new int[v.indegree()];

			int m = 0;
			for (Edge e : v.ins)
				links[v.id][m++] = e.end;
		}

		return links;
	}

	public static double[][] getReciprocalWeightsInEdges(Graph g) {
		double[][] ws = new double[g.vSize()][];

		for (Vertex v : g.getVertices()) {
			ws[v.id] = new double[v.indegree()];

			int m = 0;
			for (Edge e : v.ins)
				ws[v.id][m++] = 1d / e.w;
		}

		return ws;
	}
}
