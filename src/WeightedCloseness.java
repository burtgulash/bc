public class WeightedCloseness {
	public static ResultRow[] compute(Graph g, boolean verbose) {
		int n = g.vSize();
		ResultRow[] result = new ResultRow[n];
		int[][] links = Links.getIns(g);
		double[][] ws = Links.getReciprocalWeightsInEdges(g);
		double total_length = 0;
		double nPaths = 0;
		double diameter = -1;

		for (int v = 0; v < n; v++) {
			if (verbose)
				if (v % 10000 == 0)
					System.out.println("Weighted Closeness: " + v + " / " + n);

			double farness = 0;
			PQueue<E> q = new PQueue<E>();
			boolean[] visited = new boolean[n];

			q.insert(new E(v, 0));
			try {
				while (!q.empty()) {
					E cur = q.extractMin();

					visited[cur.to] = true;

					farness += cur.w;
					if (cur.w > diameter)
						diameter = cur.w;
					nPaths++;

					for (int i = 0; i < links[cur.to].length; i++) {
						int e = links[cur.to][i];

						if (!visited[e]) {
							double newDist = cur.w + ws[cur.to][i];
							// BFS
							// double newDist = cur.w + 1;
							if (!q.decreasePriorityAndContainsTest(e, newDist))
								q.insert(new E(e, newDist));
						}
					}
				}
			} catch (PQueue.EmptyQueueException e) {
				assert (false);
				break;
			}
			
			total_length += farness;

			double closeness = 0;
			if (farness > 0)
				closeness = (n - 1) / farness;
			result[v] = new ResultRow(g.getVertexName(v), closeness);
		}
		
		System.out.println("Average weighted shortest path: " + (total_length / nPaths));
		System.out.println("Weighted diameter: " + diameter);

		return result;
	}
}

class E implements Queable {
	int to;
	double w;

	E(int to, double w) {
		this.to = to;
		this.w = w;
	}

	@Override
	public int id() {
		return to;
	}

	@Override
	public double priority() {
		return w;
	}

	@Override
	public void setPriority(double newPriority) {
		w = newPriority;
	}
}