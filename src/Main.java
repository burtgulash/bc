import java.io.File;
import java.util.Comparator;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
	final static String DB = dblp;
	final static String BIB_DB = dir + DB;

	private static Graph authors;
	private static ResultRow[][] results = new ResultRow[9][];
	private static String dbname = null;

	public static void work() {

		// Create graphs
		Graph publications = Load.publications(new File(BIB_DB), VERBOSE);
		authors = Load.authors(publications, VERBOSE);

		// free publications
		publications = null;
		authors.makeUndirected();

		// Compute h-index
		ResultRow[] hindex = HIndex.compute(new File(BIB_DB));
		sortAndWrite(hindex, "hindex.csv", LIMIT);
		printChecksum(hindex);
		results[0] = hindex;

		System.gc();
		StatisticalDistribution.printEdgeWeightDistribution(authors);

		// Compute indegree
		ResultRow[][] btwnsrds = BetweenessRadius.compute(authors, true);
		sortAndWrite(btwnsrds[0], "betweenessParallel.csv", LIMIT);
		printChecksum(btwnsrds[0]);
		printClique(authors, btwnsrds[0], TOP_K);
		results[1] = btwnsrds[0];

		sortAndWrite(btwnsrds[1], "radius.csv", LIMIT, true);
		printChecksum(btwnsrds[1]);
		printClique(authors, btwnsrds[1], TOP_K);
		results[2] = btwnsrds[1];
		btwnsrds = null;

		// if (true)
		// return;

		// Compute indegree
		ResultRow[] indegree = Degree.compute(authors, false, true);
		sortAndWrite(indegree, "indegree.csv", LIMIT);
		printChecksum(indegree);
		printClique(authors, indegree, TOP_K);
		results[3] = indegree;

		// Compute outdegree
		ResultRow[] outdegree = Degree.compute(authors, false, false);
		sortAndWrite(outdegree, "outdegree.csv", LIMIT);
		printChecksum(outdegree);
		printClique(authors, outdegree, TOP_K);
		results[4] = outdegree;

		// Compute weighted indegree
		ResultRow[] wIndegree = Degree.compute(authors, true, true);
		sortAndWrite(wIndegree, "wIndegree.csv", LIMIT);
		printChecksum(wIndegree);
		printClique(authors, wIndegree, TOP_K);
		results[5] = wIndegree;

		// Compute outdegree
		ResultRow[] wOutdegree = Degree.compute(authors, true, false);
		sortAndWrite(wOutdegree, "wOutdegree.csv", LIMIT);
		printChecksum(wOutdegree);
		printClique(authors, wOutdegree, TOP_K);
		results[6] = wOutdegree;

		// Compute pagerank
		ResultRow[] pagerank = PageRank.compute(authors);
		sortAndWrite(pagerank, "pagerank.csv", LIMIT);
		printChecksum(pagerank);
		printClique(authors, pagerank, TOP_K);
		results[7] = pagerank;

		final int BETWEENESS_C = DONT_APPROXIMATE;

		// Compute Betweeness
		ResultRow[] betweeness = Betweeness.compute(authors, 4, VERBOSE);
		sortAndWrite(betweeness, "approximatedBetweeness_Nover4.csv", LIMIT);
		printChecksum(betweeness);
		printClique(authors, betweeness, TOP_K);
		results[7] = betweeness;

		// Compute weightedBetweeness
		ResultRow[] wBetweeness = WeightedBetweeness.compute(authors,
				BETWEENESS_C, VERBOSE);
		sortAndWrite(wBetweeness, "wBetweeness.csv", LIMIT);
		printChecksum(wBetweeness);
		printClique(authors, wBetweeness, TOP_K);
		results[8] = wBetweeness;

		// Get largest (main) component of the graph of authors.
		Graph mainComponent = Components.getLargest(authors);
		System.out.printf("Main component size (|V|, |E|): %d, %d%n",
				mainComponent.vSize(), mainComponent.eSize());

		// Compute closeness
		ResultRow[] closeness = Closeness.compute(mainComponent, VERBOSE);
		sortAndWrite(closeness, "closeness.csv", LIMIT);
		printChecksum(closeness);
		printClique(authors, closeness, TOP_K);
		// results[9] = closeness;

		// Compute weighted closeness
		ResultRow[] wCloseness = WeightedCloseness.compute(mainComponent,
				VERBOSE);
		sortAndWrite(wCloseness, "wCloseness.csv", LIMIT);
		printChecksum(wCloseness);
		printClique(authors, wCloseness, TOP_K);
		// results[10] = wCloseness;
	}

	private static void printCorrelations(ResultRow[][] results) {
		for (int i = 0; i < results.length; i++)
			results[i] = removeSingletons(authors, results[i]);
		
		for (int i = 0; i < results.length; i++) {
			for (int j = 0; j < i; j++) {
				System.out.printf("spearman(%d, %d) = %f%n", i, j,
						Correlation.spearman(results[i], results[j]));
				System.out.println();
			}
		}
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

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		if (DB == dblp)
			dbname = "DBLP";
		else if (DB == citeseer)
			dbname = "CiteSeer";

		System.out.println("START");
		System.out.println("file: " + BIB_DB);
		Date start = getTime();
		printDate(start);
		System.out.println();

		work();

		printCorrelations(results);

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

	private static ResultRow[] removeSingletons(Graph authors,
			ResultRow[] result) {
		List<ResultRow> notSingletons = new LinkedList<ResultRow>();

		for (ResultRow r : result) {
			if (authors.getVertex(r.name).degree() > 0)
				notSingletons.add(r);
		}

		return notSingletons.toArray(new ResultRow[notSingletons.size()]);
	}

	private static void sortAndWrite(ResultRow[] result, String fileName,
			int limit) {
		sortAndWrite(result, fileName, limit, false);
	}

	private static void sortAndWrite(ResultRow[] result, String fileName,
			int limit, boolean reversed) {

		Arrays.sort(result, new Comparator<ResultRow>() {
			@Override
			public int compare(ResultRow r1, ResultRow r2) {
				return r1.value > r2.value ? -1 : r1.value < r2.value ? +1 : 0;
			}
		});

		if (reversed)
			for (int i = 0; i < result.length / 2; i++) {
				ResultRow tmp = result[i];
				result[i] = result[result.length - 1 - i];
				result[result.length - 1 - i] = tmp;
			}

		boolean[] Codd = null, ACMFell = null, ISIHC = null, Turing = null;

		if (dbname != null) {
			Codd = Awards.assignAwards(result, dbname, "Codd");
			System.out.println("Codd sum: " + Awards.getSum(Codd));
			ACMFell = Awards.assignAwards(result, dbname, "ACMFell");
			System.out.println("ACMFell sum: " + Awards.getSum(ACMFell));
			ISIHC = Awards.assignAwards(result, dbname, "ISIHC");
			System.out.println("ISIHC sum: " + Awards.getSum(ISIHC));
			Turing = Awards.assignAwards(result, dbname, "Turing");
			System.out.println("Turing sum: " + Awards.getSum(Turing));
		}

		PrintWriter w = null;
		try {
			w = new PrintWriter(new FileWriter(new File(fileName)));
			w.print("author;score");
			if (dbname != null)
				w.print(";Codd;ACMFell;ISIHC;Turing");
			w.println();

			for (int i = 0; i < result.length; i++) {
				if (limit-- <= 0)
					break;

				ResultRow r = result[i];

				w.print(r.name);
				w.print(";");
				w.print(r.value);

				if (dbname != null) {
					w.print(";");

					if (Codd[i])
						w.print("x");
					w.print(";");
					if (ACMFell[i])
						w.print("x");
					w.print(";");
					if (ISIHC[i])
						w.print("x");
					w.print(";");
					if (Turing[i])
						w.print("x");
				}

				w.println();
			}
		} catch (IOException e) {
			System.err.println("Error writing to file.");
		} finally {
			w.close();
		}

		System.out.println(fileName + " finished.");
	}

}
