public class Degree {
	public static ResultRow[] compute(Graph g, boolean weighted, boolean in) {
		ResultRow[] result = new ResultRow[g.vSize()];

		int i = 0;
		for (Vertex v : g.getVertices()) {
			int deg;

			if (in) {
				if (weighted)
					deg = v.weightedIndegree();
				else
					deg = v.indegree();
			} else {
				if (weighted)
					deg = v.weightedOutdegree();
				else
					deg = v.outdegree();
			}

			result[i] = new ResultRow(g.getVertexName(v.id), deg);

			i++;
		}

		return result;
	}
}
