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
	final static String BIB_DB = dir + dblp;

	public static void work() {
		// // Compute h-index
		// ResultRow[] hindex = HIndex.compute(new File(BIB_DB));
		// sortAndWrite(hindex, "hindex.csv", LIMIT);
		// hindex = null;

		// Create graphs
		Graph publications = Load.publications(new File(BIB_DB), VERBOSE);
		Graph authors = Load.authors(publications, VERBOSE);

		// free publications
		publications = null;
		authors.makeUndirected();

		final int BETWEENESS_C = 10;

		// Compute weightedBetweeness
		ResultRow[] weightedBetweeness = WeightedBetweeness.compute(authors,
				BETWEENESS_C, VERBOSE);
		sortAndWrite(weightedBetweeness, "weightedBetweeness.csv", LIMIT);
		weightedBetweeness = null;
		
		 // Compute Betweeness
		 ResultRow[] betweeness = Betweeness.compute(authors, BETWEENESS_C, VERBOSE);
		 sortAndWrite(betweeness, "betweeness.csv", LIMIT);
		 betweeness = null;

		// TODO remove to complete everything.
		if (true)
			return;

		Graph[] cs = Components.getN(authors, 3);

		// Compute approximated closeness
		for (int i = 0; i < cs.length; i++) {
			ResultRow[] acloseness = WeightedCloseness.compute(cs[i], VERBOSE);
			if (VERBOSE)
				System.out.printf("Component %d size (|V|, |E|): %d, %d%n", i,
						cs[i].vSize(), cs[i].eSize());
			sortAndWrite(acloseness, "acloseness" + i + ".csv", LIMIT);
		}
		cs = null;

		// // Compute closeness
		// ResultRow[] closeness = Closeness.compute(authors, VERBOSE);
		// sortAndWrite(closeness, "closeness.csv", LIMIT);
		// closeness = null;

		// // Compute weightedCloseness
		// ResultRow[] weightedCloseness = WeightedCloseness.compute(authors);
		// sortAndWrite(weightedCloseness, "weightedCloseness.csv", LIMIT);
		// weightedCloseness = null;

		// // Compute betweeness
		// ResultRow[] betweeness = Betweeness.compute(authors);
		// sortAndWrite(betweeness, "betweeness.csv", LIMIT);
		// betweeness = null;

		// Compute indegree
		final boolean WEIGHTED = true;
		ResultRow[] indegree = Degree.compute(authors, WEIGHTED, true);
		sortAndWrite(indegree, "indegree.csv", LIMIT);
		indegree = null;

		// Compute outdegree
		ResultRow[] outdegree = Degree.compute(authors, WEIGHTED, false);
		sortAndWrite(outdegree, "outdegree.csv", LIMIT);
		outdegree = null;

		// Compute pagerank
		ResultRow[] pagerank = PageRank.compute(authors);
		sortAndWrite(pagerank, "pagerank.csv", LIMIT);
		pagerank = null;

		// // Compute closeness and betweeness at once
		// ResultRow[][] closenessBetweeness = ClsnsBtwns.compute(authors);
		//
		// sortAndWrite(closenessBetweeness[0], "closeness.csv", LIMIT);
		// sortAndWrite(closenessBetweeness[1], "betweeness.csv", LIMIT);
		// closenessBetweeness = null;

	}

	public static void main(String[] args) {
		System.out.println("START");
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

	private static void sortAndWrite(ResultRow[] result, String fileName,
			int limit) {
		double checkSum = 0;

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

				checkSum += r.value;
				w.println(r);
			}
		} catch (IOException e) {
			System.err.println("Error writing to file.");
		} finally {
			w.close();
		}

		System.out.println(fileName + " finished. Checksum: " + checkSum);
	}

}
