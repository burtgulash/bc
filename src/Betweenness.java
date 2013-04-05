import java.util.LinkedList;
import java.util.Queue;

public class Betweenness extends Thread {
	private int from, to, core;
	private static int n;
	private static int doneSoFar;
	private static boolean verbose = false;

	private static int[][] links;
	private static Graph graph;

	private static ResultRow[] betweennessResult;

	private static Betweenness[] threads;
	private static double[][] betweennessParts;

	private static int frac;

	private Betweenness(int core, int from, int to) {
		this.from = from;
		this.to = to;
		this.core = core;
	}
	
	private static void reset() {
		n = 0;
		doneSoFar = 0;
		links = null;
		graph = null;
		betweennessResult = null;
		threads = null;
		betweennessParts = null;
		frac = 0;
	}

	public void run() {
		double[] betweenessPart = new double[n];
		betweennessParts[core] = betweenessPart;

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
					System.out.println("Betweeness (" + frac + ") : "
							+ doneSoFar + "/" + n);

				if (doneSoFar * frac > n)
					break;
				// TODO the Harlem shake
			}
		}
	}

	public static ResultRow[] compute(Graph g, int fraction, boolean verbos) {
		reset();
		frac = fraction;
		n = g.vSize();
		graph = g;
		verbose = verbos;
		betweennessResult = new ResultRow[n];

		links = Links.getOuts(g);

		int cores = Runtime.getRuntime().availableProcessors();
		threads = new Betweenness[cores];
		betweennessParts = new double[cores][];

		for (int i = 0; i < cores; i++) {
			threads[i] = new Betweenness(i, (i * n) / cores, ((i + 1) * n)
					/ cores);
			threads[i].start();
		}

		for (int i = 0; i < n; i++)
			betweennessResult[i] = new ResultRow(graph.getVertexName(i), 0);
		try {
			for (int i = 0; i < cores; i++) {
				threads[i].join();
				for (int j = 0; j < n; j++)
					betweennessResult[j].value += betweennessParts[threads[i].core][j];
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Approximation correction ( b := b * n/k )
		for (int i = 0; i < n; i++)
			betweennessResult[i].value *= frac;

		return betweennessResult;
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
