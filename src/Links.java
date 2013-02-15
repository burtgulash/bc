import java.util.Collection;

public class Links {
	public static int[][] getOuts(Graph g) {
		int[][] links = new int[g.vSize()][];

		for (Vertex node : g.getVertices()) {
			links[node.id] = new int[node.outdegree()];

			int m = 0;
			for (Edge e : node.outs)
				links[node.id][m++] = e.end;
		}

		return links;
	}

	public static int[][] getIns(Graph g) {
		int[][] links = new int[g.vSize()][];
		int[] sizes = new int[g.vSize()];

		Collection<Vertex> vs = g.getVertices();

		for (Vertex v : vs)
			for (Edge e : v.ins)
				sizes[e.end]++;

		for (int v = 0; v < links.length; v++)
			links[v] = new int[sizes[v]];

		sizes = null;
		int[] cs = new int[links.length];

		for (Vertex v : vs)
			for (Edge e : v.ins)
				links[e.end][cs[e.end]++] = v.id;

		return links;
	}
}
