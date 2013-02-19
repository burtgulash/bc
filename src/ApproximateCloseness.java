import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ApproximateCloseness {
	private static final int NIL = -1;

	private static boolean verbose;


	private static int getL(int n) {
		double nn = Math.pow((double) n, 2d / 3d);
		double log = Math.pow(Math.log(n), 1d / 3d);

		int res = (int) (nn * log);
		assert res > 0;
		return res;
	}

	private static double f(int n, int l) {
		double alpha_prime = 2;
		return alpha_prime * Math.sqrt(Math.log((double) n) / (double) l);
	}

	public static ResultRow[] compute(Graph graph, int k, boolean ver) {
		verbose = ver;
		int n = graph.vSize();
		k = Math.min(n, k);

		// FIXME decide inlinks or outlinks.
		int[][] links = Links.getIns(graph);
		List<ResultRow> resultList = new LinkedList<ResultRow>();

		int l = getL(n);
		if (verbose)
			System.out.println("l = " + l);

		Rank[] approximates = new Rank[n];
		double delta_hat = RAND(links, approximates, l);
		double bound = approximates[k - 1].value + 2 * f(n, l) * delta_hat;

		if (verbose) {
			System.out.println("Approximate diameter: " + delta_hat);
			System.out.println("Bound: " + bound);
		}

		int ii = 0;
		for (Rank r : approximates) {
			ii++;
			// Approximates already sorted.
			if (r.value > bound)
				break;

			int v = r.id;
			if (verbose)
				System.out.println("Exact closeness: " + ii + " / " + n);

			double farness = 0;

			Queue<E> q = new LinkedList<E>();
			int[] previous = new int[n];
			boolean[] visited = new boolean[n];

			for (int j = 0; j < n; j++) {
				previous[j] = NIL;
				visited[j] = false;
			}

			q.offer(new E(v, 0));
			visited[v] = true;

			for (int i = 0; i < l && !q.isEmpty(); i++) {
				E cur = q.poll();

				// Update closeness/farness here
				farness += cur.w;

				for (int e : links[cur.to]) {
					if (!visited[e]) {
						q.offer(new E(e, cur.w + 1));
						previous[e] = cur.to;
						visited[e] = true;
					}
				}
			}

			double closeness = 0;
			if (farness > 0)
				closeness = (n - 1) / farness;

			resultList.add(new ResultRow(graph.getVertexName(v), closeness));
		}

		return resultList.toArray(new ResultRow[resultList.size()]);
	}
	
	private static double RAND(int[][] links, Rank[] ranks, int l) {
		int n = links.length;

		// FIXME avoid abandoned nodes.
		assert (n > 1);

		double max_d = 0;

		for (int v = 0; v < n; v++) {
			if (verbose)
				System.out.println("RAND: " + v + " / " + n);
			double farness = 0;

			Queue<E> q = new LinkedList<E>();
			boolean[] visited = new boolean[n];

			q.offer(new E(v, 0));
			visited[v] = true;

			for (int i = 0; i < l && !q.isEmpty(); i++) {
				E cur = q.poll();

				// update diameter
				if (cur.w > max_d)
					max_d = cur.w;

				// Update closeness/farness here
				farness += cur.w;

				for (int e : links[cur.to]) {
					if (!visited[e]) {
						q.offer(new E(e, cur.w + 1));
						visited[e] = true;
					}
				}
			}

			farness *= n;

			// FIXME abandoned nodes problem.
			if (n > 1)
				farness /= l * (n - 1);

			ranks[v] = new Rank(v, farness);
		}

		Arrays.sort(ranks, new Comparator<Rank>() {
			@Override
			public int compare(Rank r1, Rank r2) {
				return r1.value > r2.value ? +1 : r1.value < r2.value ? -1 : 0;
			}
		});

		return 2 * max_d;
	}

	private static class Rank {
		int id;
		double value;

		private Rank(int id, double value) {
			this.id = id;
			this.value = value;
		}
	}

	private static class E {
		int to, w;

		private E(int vertex, int d) {
			this.to = vertex;
			this.w = d;
		}
	}
}