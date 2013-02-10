public class WeightedCloseness {
	public static ResultRow[] compute(Graph g) {
		int n = g.vSize();
		ResultRow[] result = new ResultRow[n];

		for (int v = 0; v < n; v++) {
			PQueue<E> q = new PQueue<E>();
			boolean[] visited = new boolean[n];
			E[][] links = new E[n][];
			for (Vertex vertex : g.getVertices()) {
				links[vertex.id] = new E[vertex.outdegree()];

				int k = 0;
				for (Edge e : vertex.outs)
					links[vertex.id][k++] = new E(e.end, e.w);
			}

			int closeness = 0;

			q.insert(new E(v, 0));
			try {
				while (!q.empty()) {
					E cur = q.extractMin();

					visited[cur.to] = true;

					if (cur.w > 0)
						closeness += 1d / cur.w;

					for (E e : links[cur.to]) {
						if (!visited[e.to]) {
							double newDist = cur.w + e.w;
							// BFS
							// double newDist = cur.w + 1;
							if (!q.decreasePriorityAndContainsTest(e.to,
									newDist))
								q.insert(new E(e.to, newDist));
						}
					}
				}
			} catch (PQueue.EmptyQueueException e) {
				assert (false);
				break;
			}

			System.out.println("Weighted Closeness: " + v + "/" + n);
			result[v] = new ResultRow(g.getVertexName(v), closeness);
		}

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