public class Degree {
	public static ResultRow[] compute(Graph g, boolean in) {
		ResultRow[] result = new ResultRow[g.vSize()];

		int i = 0;
		for (Vertex v : g.getVertices()) {
			int deg;
			
			if (in)
				deg = v.inDegree();
			else
				deg = v.outDegree();
			
			result[i] = new ResultRow(g.getVertexName(v.id), deg);

			// weighted degree?
			// for (Edge e : v.links)
			// result[i].value += e.w;

			i++;
		}

		return result;
	}
}
