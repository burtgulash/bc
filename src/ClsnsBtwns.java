import java.util.LinkedList;
import java.util.Queue;

public class ClsnsBtwns extends Thread {
	private static final int NIL = -1;

	private static Graph g;
	private static int nVertices;
	private static int doneSoFar;
	private static ResultRow[] closeness;
	private static ResultRow[] betweeness;
	private static long startTime;
	private int from, to;
	private int core;

	private static int[][] links;
	private static Vertex[] nodes;

	private static ClsnsBtwns[] threads;
	private static double[][] betweenessParts;

	private ClsnsBtwns(int core, int from, int to) {
		this.from = from;
		this.to = to;
		this.core = core;

	}

	public static ResultRow[][] compute(Graph graph) {
		g = graph;
		nVertices = g.vSize();
		doneSoFar = 0;
		startTime = System.nanoTime();
		closeness = new ResultRow[nVertices];
		betweeness = new ResultRow[nVertices];

		links = new int[nVertices][];
		nodes = g.getVertices().toArray(new Vertex[nVertices]);

		int v;
		for (v = 0; v < betweeness.length; v++)
			betweeness[v] = new ResultRow(g.getVertexName(v), 0);


		for (Vertex node : nodes) {
			links[node.id] = new int[node.outdegree()];

			int k = 0;
			for (Edge e : node.outs)
				links[node.id][k++] = e.end;
		}

		int cores = Runtime.getRuntime().availableProcessors();
		threads = new ClsnsBtwns[cores];
		betweenessParts = new double[cores][];

		for (int i = 0; i < cores; i++) {
			threads[i] = new ClsnsBtwns(i, (i * nVertices) / cores,
					((i + 1) * nVertices) / cores);
			threads[i].start();
		}

		// Join threads and collect betweeness values
		try {
			for (int i = 0; i < cores; i++) {
				threads[i].join();
				for (int j = 0; j < nVertices; j++)
					betweeness[j].value += betweenessParts[threads[i].core][j];
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return new ResultRow[][] { closeness, betweeness };
	}

	public void run() {
		double[] betweenessPart = new double[nVertices];
		betweenessParts[core] = betweenessPart;

		for (int v = from; v < to; v++) {
			double farness = 0;

			Queue<Path> q = new LinkedList<Path>();
			int[] previous = new int[nVertices];
			boolean[] visited = new boolean[nVertices];

			for (int j = 0; j < nVertices; j++) {
				previous[j] = NIL;
				visited[j] = false;
			}

			q.offer(new Path(v, 0));
			visited[v] = true;

			while (!q.isEmpty()) {
				Path cur = q.poll();

				int traceBack = previous[cur.vertex];
				while (traceBack != v && traceBack != NIL) {
					betweenessPart[traceBack] += 1;
					traceBack = previous[traceBack];
				}

				// Update closeness/farness here
				farness += cur.d;

				for (int e : links[cur.vertex]) {
					if (!visited[e]) {
						q.offer(new Path(e, cur.d + 1));
						previous[e] = cur.vertex;
						visited[e] = true;
					}
				}
			}

			closeness[v] = new ResultRow(g.getVertexName(v), farness);

			// Synchronized print of status of computation
			synchronized (nodes) {
				doneSoFar++;
				if (doneSoFar % 10000 == 0) {
					long t = System.nanoTime() - startTime;
					int s = (int) (t / 1000000000l);
					int m = s / 60;
					int h = m / 60;
					System.out.printf("Clsns Btns: %03d:%02d:%02d   ", h,
							m % 60, s % 60);
					System.out.printf(
							"Clsns Btns: Computed so far: %.2f%%  (%d/%d)%n",
							(double) doneSoFar * 100d / nVertices, doneSoFar,
							nVertices);
				}
			}
		}
	}

	private static class Path {
		int vertex, d;

		private Path(int vertex, int d) {
			this.vertex = vertex;
			this.d = d;
		}
	}
}