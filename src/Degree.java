public class Degree {
	public static ResultRow[] compute(Graph g, boolean weighted, Direction d) {
		ResultRow[] result = new ResultRow[g.vSize()];

		int i = 0;
		for (Vertex v : g.getVertices()) {
			int deg = 0;

			switch (d) {
			case IN:
				if (weighted)
					deg = v.weightedIndegree();
				else
					deg = v.indegree();
				break;
			case OUT:
				if (weighted)
					deg = v.weightedOutdegree();
				else
					deg = v.outdegree();
				break;
			case BOTH:
				if (weighted)
					deg = v.weightedDegree();
				else
					deg = v.degree();
				break;
			}

			result[i] = new ResultRow(g.getVertexName(v.id), deg);

			i++;
		}

		return result;
	}

	public enum Direction {
		IN, OUT, BOTH;
	}
}
