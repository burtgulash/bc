import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Clique {
	public static boolean isClique(Graph g, List<Integer> vs) {
		for (int v : vs)
			for (int u : vs)
				if (u != v)
					// Edge doesn't exist in neither direction -> no clique.
					if (g.getOutEdge(u, v) == null
							&& g.getOutEdge(v, u) == null)
						return false;
		return true;
	}

	public static String[] findMaxClique(Graph g, ResultRow[] rs, int limit) {
		int size = Math.min(rs.length, limit);
		int[] vs = new int[size];

		for (int i = 0; i < size; i++)
			vs[i] = g.getVertexId(rs[i].name);

		int[] max_clique = findMaxClique(g, vs);

		String[] clique = new String[max_clique.length];
		for (int i = 0; i < max_clique.length; i++)
			clique[i] = g.getVertexName(max_clique[i]);

		Arrays.sort(clique);

		return clique;
	}

	public static int[] findMaxClique(Graph g, int[] vs) {
		int limit = 1;
		for (int i = 0; i < vs.length; i++)
			limit *= 2;

		List<Integer> max_clique = null;
		int max_size = 0;

		// i from 1 to 2^n - 1
		for (int i = 1; i < limit - 1; i++) {
			List<Integer> subset = new LinkedList<Integer>();
			for (int j = 0; j < vs.length; j++)
				if ((i & (1 << j)) > 0)
					subset.add(vs[j]);

			if (max_size < subset.size()) {
				if (isClique(g, subset)) {
					max_clique = subset;
					max_size = max_clique.size();
				}
			}
		}

		int[] clique = new int[max_clique.size()];
		int i = 0;
		for (int v : max_clique)
			clique[i++] = v;

		return clique;
	}
}
