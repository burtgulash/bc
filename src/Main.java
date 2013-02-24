import java.io.File;
import java.util.Comparator;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Main {
	private final static boolean VERBOSE = true;
	private final static int DONT_APPROXIMATE = -1;
	private final static int TOP_K = 20;
	private final static int LIMIT = 30;

	final static String dir = "data/";
	final static String dblp = "dblp2004-vse.csv";
	final static String citeseer = "citeseer2005-cisty.csv";
	final static String wos = "WoS.csv";
	final static String test1 = "test.csv";
	final static String test2 = "test2.csv";
	final static String test3 = "test3.csv";
	final static String test3rev = "test3rev.csv";
	final static String test4 = "test4.csv";
	final static String BIB_DB = dir + citeseer;

	public static void work() {
		// Compute h-index
		ResultRow[] hindex = HIndex.compute(new File(BIB_DB));
		sortAndWrite(hindex, "hindex.csv", LIMIT);
		printChecksum(hindex);
		hindex = null;

		// Create graphs
		Graph publications = Load.publications(new File(BIB_DB), VERBOSE);
		Graph authors = Load.authors(publications, VERBOSE);

		// free publications
		publications = null;
		authors.makeUndirected();

		System.gc();
		StatisticalDistribution.printEdgeWeightDistribution(authors);

		// Compute indegree
		ResultRow[] indegree = Degree.compute(authors, false, true);
		sortAndWrite(indegree, "indegree.csv", LIMIT);
		printChecksum(indegree);
		printClique(authors, indegree, TOP_K);
		indegree = null;

		// Compute outdegree
		ResultRow[] outdegree = Degree.compute(authors, false, false);
		sortAndWrite(outdegree, "outdegree.csv", LIMIT);
		printChecksum(outdegree);
		printClique(authors, outdegree, TOP_K);
		outdegree = null;

		// Compute weighted indegree
		ResultRow[] wIndegree = Degree.compute(authors, true, true);
		sortAndWrite(wIndegree, "wIndegree.csv", LIMIT);
		printChecksum(wIndegree);
		printClique(authors, wIndegree, TOP_K);
		wIndegree = null;

		// Compute outdegree
		ResultRow[] wOutdegree = Degree.compute(authors, true, false);
		sortAndWrite(wOutdegree, "wOutdegree.csv", LIMIT);
		printChecksum(wOutdegree);
		printClique(authors, wOutdegree, TOP_K);
		wIndegree = null;
		wOutdegree = null;

		// Compute pagerank
		ResultRow[] pagerank = PageRank.compute(authors);
		sortAndWrite(pagerank, "pagerank.csv", LIMIT);
		printChecksum(pagerank);
		printClique(authors, pagerank, TOP_K);
		pagerank = null;

		final int BETWEENESS_C = DONT_APPROXIMATE;

		if (false) {
			// Compute Betweeness
			ResultRow[] betweeness = Betweeness.compute(authors, BETWEENESS_C,
					VERBOSE);
			sortAndWrite(betweeness, "betweeness.csv", LIMIT);
			printChecksum(betweeness);
			printClique(authors, betweeness, TOP_K);
			betweeness = null;

			// Compute weightedBetweeness
			ResultRow[] wBetweeness = WeightedBetweeness.compute(authors,
					BETWEENESS_C, VERBOSE);
			sortAndWrite(wBetweeness, "wBetweeness.csv", LIMIT);
			printChecksum(wBetweeness);
			printClique(authors, wBetweeness, TOP_K);
			wBetweeness = null;
		}

		// Get largest (main) component of the graph of authors.
		Graph mainComponent = Components.getLargest(authors);
		System.out.printf("Main component size (|V|, |E|): %d, %d%n",
				mainComponent.vSize(), mainComponent.eSize());

		// Compute closeness
		ResultRow[] closeness = Closeness.compute(mainComponent, VERBOSE);
		sortAndWrite(closeness, "closeness.csv", LIMIT);
		printChecksum(closeness);
		printClique(authors, closeness, TOP_K);
		closeness = null;

		// Compute weighted closeness
		ResultRow[] wCloseness = WeightedCloseness.compute(mainComponent,
				VERBOSE);
		sortAndWrite(wCloseness, "wCloseness.csv", LIMIT);
		printChecksum(wCloseness);
		printClique(authors, wCloseness, TOP_K);
		wCloseness = null;
		mainComponent = null;
	}

	private static void printClique(Graph g, ResultRow[] rs, int limit) {
		String[] clique = Clique.findMaxClique(g, rs, limit);
		System.out.println("Largest clique from top " + limit + " size: "
				+ clique.length);

		for (int i = 0; i < clique.length; i++) {
			if (i > 0)
				System.out.print(", ");
			System.out.print(clique[i]);
		}

		System.out.println();
		System.out.println();
	}

	public static void main(String[] args) {
		System.out.println("START");
		System.out.println("file: " + BIB_DB);
		Date start = getTime();
		printDate(start);
		System.out.println();

		work();

		Date end = getTime();
		printDate(end);

		long diff = end.getTime() - start.getTime();

		int seconds = (int) (diff / 1000);
		int minutes = seconds / 60;
		int hours = minutes / 60;

		System.out.println();
		System.out.print("Elapsed time: ");
		System.out.printf("%d:%02d:%02d%n", hours, minutes % 60, seconds % 60);

		System.out.println("END");
	}

	private static void printDate(Date date) {
		System.out.println(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
				.format(getTime()));
	}

	private static Date getTime() {
		return Calendar.getInstance().getTime();
	}

	private static void printChecksum(ResultRow[] rs) {
		System.out.println("checksum: " + checksum(rs));
	}

	private static double checksum(ResultRow[] rs) {
		double sum = 0;

		for (ResultRow r : rs)
			sum += r.value;

		return sum;
	}

	private static void sortAndWrite(ResultRow[] result, String fileName,
			int limit) {

		Arrays.sort(result, new Comparator<ResultRow>() {
			@Override
			public int compare(ResultRow r1, ResultRow r2) {
				return r1.value > r2.value ? -1 : r1.value < r2.value ? +1 : 0;
			}
		});

		PrintWriter w = null;
		try {
			w = new PrintWriter(new FileWriter(new File(fileName)));
			w.println("author;score");
			for (ResultRow r : result) {
				if (limit-- <= 0)
					break;

				w.println(r);
			}
		} catch (IOException e) {
			System.err.println("Error writing to file.");
		} finally {
			w.close();
		}

		System.out.println(fileName + " finished.");
	}

}
