import java.util.LinkedList;
import java.util.Queue;

public class BetweenessRadius extends Thread {
	private int from, to, core;
	private static double global_max_radius;
	private static int n;
	private static int doneSoFar = 0;
	private static boolean verbose = false;

	private static int[][] links;
	private static Graph graph;

	private static ResultRow[] betweenessResult;
	private static ResultRow[] radiusResult;

	private static BetweenessRadius[] threads;
	private static double[][] betweenessParts;

	private BetweenessRadius(int core, int from, int to) {
		this.from = from;
		this.to = to;
		this.core = core;
	}

	public void run() {
		double[] betweenessPart = new double[n];
		betweenessParts[core] = betweenessPart;

		double max_radius = 0;
		for (int s = from; s < to; s++) {
			// List of predecessors.
			Stack[] p = new Stack[n];
			Stack stack = new Stack();
			int[] sigma = new int[n];
			int[] d = new int[n];

			for (int v = 0; v < n; v++) {
				p[v] = new Stack();
				d[v] = -1;
			}
			sigma[s] = 1;
			d[s] = 0;

			Queue<Integer> q = new LinkedList<Integer>();

			double radius = 0;

			q.offer(s);
			while (!q.isEmpty()) {
				int v = q.poll();
				stack.push(v);

				if (d[v] > radius)
					radius = d[v];

				for (int w : links[v]) {
					if (d[w] == -1) {
						q.offer(w);
						d[w] = d[v] + 1;
					}

					if (d[w] == d[v] + 1) {
						sigma[w] += sigma[v];
						p[w].push(v);
					}
				}
			}

			if (radius > max_radius)
				max_radius = radius;

			radiusResult[s] = new ResultRow(graph.getVertexName(s), radius);

			double[] delta = new double[n];
			while (!stack.isEmpty()) {
				int w = stack.pop();
				for (Stack.ListNode node = p[w].root; node != null; node = node.next) {
					int v = node.x;
					delta[v] += ((double) sigma[v] / (double) sigma[w])
							* (1d + delta[w]);
					if (w != s)
						betweenessPart[w] += delta[v];
				}
			}

			synchronized (links) {
				doneSoFar++;
				if (verbose && doneSoFar % 10000 == 0)
					System.out.println("Betweeness Radius: " + doneSoFar + "/"
							+ n);
			}
		}

		synchronized (links) {
			if (max_radius > global_max_radius)
				global_max_radius = max_radius;
		}
	}

	public static ResultRow[][] compute(Graph g, boolean verbos) {
		n = g.vSize();
		graph = g;
		verbose = verbos;
		radiusResult = new ResultRow[n];
		betweenessResult = new ResultRow[n];

		links = Links.getOuts(g);

		int cores = Runtime.getRuntime().availableProcessors();
		threads = new BetweenessRadius[cores];
		betweenessParts = new double[cores][];

		for (int i = 0; i < cores; i++) {
			threads[i] = new BetweenessRadius(i, (i * n) / cores, ((i + 1) * n)
					/ cores);
			threads[i].start();
		}

		try {
			for (int i = 0; i < n; i++)
				betweenessResult[i] = new ResultRow(graph.getVertexName(i), 0);
			for (int i = 0; i < cores; i++) {
				threads[i].join();
				for (int j = 0; j < n; j++)
					betweenessResult[j].value += betweenessParts[threads[i].core][j];
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Max radius: " + global_max_radius);
		return new ResultRow[][] { betweenessResult, radiusResult };
	}

	private static class Stack {
		ListNode root;

		private boolean isEmpty() {
			return root == null;
		}

		private void push(int x) {
			if (root == null) {
				root = new ListNode();
				root.x = x;
			} else
				root.add(x);
		}

		private int pop() {
			ListNode popped = root;
			root = popped.next;
			return popped.x;
		}

		private class ListNode {
			int x;
			ListNode next;

			private void add(int x) {
				ListNode n = new ListNode();
				n.x = x;
				n.next = next;
				next = n;
			}
		}
	}
}
