import java.util.Arrays;
import java.util.LinkedList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class Components {
	public static Graph[] get(Graph g) {
		return getN(g, g.vSize());
	}

	public static Graph[] getN(Graph g, int n) {
		int[][] outs = Links.getOuts(g);
		int[][] ins = Links.getIns(g);

		int[][] cs = getIntComponents(outs, ins);
		Graph[] gcs = new Graph[n];

		for (int i = 0; i < n; i++) {
			gcs[i] = new Graph();

			for (int v : cs[i])
				gcs[i].addVertex(g.getVertexName(v));

			for (int v : cs[i])
				for (int e : outs[v])
					if (in(e, cs[i]))
						gcs[i].addEdge(g.getVertexName(v), g.getVertexName(e),
								1, false);
			
			gcs[i].makeUndirected();
		}

		return gcs;
	}

	private static boolean in(int e, int[] arr) {
		int lo = 0;
		int hi = arr.length - 1;
		while (lo <= hi) {
			int mid = (lo + hi) / 2;
			if (e < arr[mid])
				hi = mid - 1;
			else if (e > arr[mid])
				lo = mid + 1;
			else
				return true;
		}

		return false;
	}

	private static int[][] getIntComponents(int[][] outs, int[][] ins) {
		// forward pass stack
		Stack<Integer> order = new Stack<Integer>();
		// dfs stack
		Stack<Integer> s = new Stack<Integer>();
		// save vertex on stack until completed stack
		Stack<Integer> vs = new Stack<Integer>();
		boolean[] visited = new boolean[outs.length];

		List<int[]> components = new LinkedList<int[]>();

		// Forward dfs
		for (int v = 0; v < outs.length; v++) {
			if (!visited[v]) {
				s.push(v);
				visited[v] = true;
				while (!s.isEmpty()) {
					int c = s.pop();
					vs.push(c);
					for (int e : outs[c])
						if (!visited[e]) {
							s.push(e);
							visited[e] = true;
						}
				}

				while (!vs.isEmpty())
					order.push(vs.pop());
			}
		}

		s = new Stack<Integer>();
		visited = new boolean[ins.length];

		// dfs on reversed graph
		while (!order.isEmpty()) {
			int v = order.pop();
			if (!visited[v]) {
				LinkedList<Integer> component = new LinkedList<Integer>();

				s.push(v);
				visited[v] = true;
				while (!s.isEmpty()) {
					int c = s.pop();
					component.add(c);
					for (int e : ins[c])
						if (!visited[e]) {
							s.push(e);
							visited[e] = true;
						}
				}

				int[] component_int = new int[component.size()];
				int i = 0;
				for (int c : component)
					component_int[i++] = c;

				Arrays.sort(component_int);

				components.add(component_int);
			}
		}

		int[][] components_arr = components
				.toArray(new int[components.size()][]);

		Arrays.sort(components_arr, new Comparator<int[]>() {
			@Override
			public int compare(int[] r1, int[] r2) {
				return r1.length > r2.length ? -1 : r1.length < r2.length ? +1
						: 0;
			}
		});

		return components_arr;

	}
}
