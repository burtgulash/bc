import java.io.File;
import java.util.Comparator;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {
	private final static boolean VERBOSE = true;
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
	final static String DB = citeseer;
	final static String BIB_DB = dir + DB;

	private static Graph authors;
	private static String dbname = null;
	private static Map<Integer, String> methodName = new HashMap<Integer, String>();

	public static void work() {
		// Create graphs
		Graph publications = Load.publications(new File(BIB_DB), VERBOSE);
		authors = Load.authors(publications, VERBOSE);

		// free publications
		publications = null;
		authors.makeUndirected();

		long start;

		// Compute h-index
		start = System.currentTimeMillis();
		ResultRow[] hindex = HIndex.compute(new File(BIB_DB));
		printRunningTime(start);
		sortAndWrite(hindex, "hi.csv", LIMIT);
		printClique(authors, hindex, TOP_K);
		printChecksum(hindex);

		System.gc();
		StatisticalDistribution.printEdgeWeightDistribution(authors);
		
		// Compute parallel exact betweenness
		start = System.currentTimeMillis();
		ResultRow[] betweennessExact = Betweenness.compute(authors, 1, true);
		printRunningTime(start);
		sortAndWrite(betweennessExact, "btw.csv", LIMIT);
		printChecksum(betweennessExact);
		printClique(authors, betweennessExact, TOP_K);

		// Compute parallel betweenness approximated : n / 4
		start = System.currentTimeMillis();
		ResultRow[] betweenness4 = Betweenness.compute(authors, 4, true);
		printRunningTime(start);
		sortAndWrite(betweenness4, "btwA.csv", LIMIT);
		printChecksum(betweenness4);
		printClique(authors, betweenness4, TOP_K);

		// Compute weightedBetweenness
		start = System.currentTimeMillis();
		ResultRow[] wBetweenness = WeightedBetweenness.compute(authors, 4,
				VERBOSE);
		printRunningTime(start);
		sortAndWrite(wBetweenness, "wBtwA.csv", LIMIT);
		printChecksum(wBetweenness);
		printClique(authors, wBetweenness, TOP_K);

		// Compute indegree

		start = System.currentTimeMillis();
		ResultRow[] indegree = Degree.compute(authors, false,
				Degree.Direction.IN);
		printRunningTime(start);
		sortAndWrite(indegree, "ideg.csv", LIMIT);
		printChecksum(indegree);
		printClique(authors, indegree, TOP_K);

		// Compute outdegree
		start = System.currentTimeMillis();
		ResultRow[] outdegree = Degree.compute(authors, false,
				Degree.Direction.OUT);
		printRunningTime(start);
		sortAndWrite(outdegree, "odeg.csv", LIMIT);
		printChecksum(outdegree);
		printClique(authors, outdegree, TOP_K);

		// Compute indegree
		start = System.currentTimeMillis();
		ResultRow[] degree = Degree.compute(authors, false,
				Degree.Direction.BOTH);
		printRunningTime(start);
		sortAndWrite(degree, "deg.csv", LIMIT);
		printChecksum(degree);
		printClique(authors, degree, TOP_K);

		// Compute weighted indegree
		start = System.currentTimeMillis();
		ResultRow[] wIndegree = Degree.compute(authors, true,
				Degree.Direction.IN);
		printRunningTime(start);
		sortAndWrite(wIndegree, "wideg.csv", LIMIT);
		printChecksum(wIndegree);
		printClique(authors, wIndegree, TOP_K);

		// Compute outdegree
		start = System.currentTimeMillis();
		ResultRow[] wOutdegree = Degree.compute(authors, true,
				Degree.Direction.OUT);
		printRunningTime(start);
		sortAndWrite(wOutdegree, "wodeg.csv", LIMIT);
		printChecksum(wOutdegree);
		printClique(authors, wOutdegree, TOP_K);

		// Compute outdegree
		start = System.currentTimeMillis();
		ResultRow[] wDegree = Degree.compute(authors, true,
				Degree.Direction.BOTH);
		printRunningTime(start);
		sortAndWrite(wDegree, "wdeg.csv", LIMIT);
		printChecksum(wDegree);
		printClique(authors, wDegree, TOP_K);

		// Compute pagerank
		start = System.currentTimeMillis();
		ResultRow[] pagerank = PageRank.compute(authors);
		printRunningTime(start);
		sortAndWrite(pagerank, "pr.csv", LIMIT);
		printChecksum(pagerank);
		printClique(authors, pagerank, TOP_K);


		// Get largest (main) component of the graph of authors.
		Graph mainComponent = Components.getLargest(authors);
		System.out.printf("Main component size (|V|, |E|): %d, %d%n",
				mainComponent.vSize(), mainComponent.eSize());
		System.out.println("Percentage: "
				+ ((double) mainComponent.vSize() * 100)
				/ (double) authors.vSize());

		// Compute closeness
		start = System.currentTimeMillis();
		ResultRow[] inCloseness = Closeness.compute(mainComponent, true,
				VERBOSE);
		printRunningTime(start);
		sortAndWrite(inCloseness, "ic.csv", LIMIT);
		printChecksum(inCloseness);
		printClique(authors, inCloseness, TOP_K);

		ResultRow[] outCloseness = Closeness.compute(mainComponent, false,
				VERBOSE);
		printRunningTime(start);
		sortAndWrite(outCloseness, "oc.csv", LIMIT);
		printChecksum(outCloseness);
		printClique(authors, outCloseness, TOP_K);

		// Compute weighted closeness
		start = System.currentTimeMillis();
		ResultRow[] wCloseness = WeightedCloseness.compute(mainComponent,
				VERBOSE);
		printRunningTime(start);
		sortAndWrite(wCloseness, "wic.csv", LIMIT);
		printChecksum(wCloseness);
		printClique(authors, wCloseness, TOP_K);

		ResultRow[][] results = new ResultRow[11][];
		results[0] = hindex;
		results[1] = indegree;
		results[2] = outdegree;
		results[3] = degree;
		results[4] = wIndegree;
		results[5] = wOutdegree;
		results[6] = wDegree;
		results[7] = pagerank;
		results[9] = betweennessExact;
		results[8] = betweenness4;
		results[10] = wBetweenness;

		sortAndWrite(results[0], "test.csv", 30);
		writeRankTable("table.csv", results);

		methodName.put(0, "hindex    ");
		methodName.put(1, "indegree  ");
		methodName.put(2, "outdegree ");
		methodName.put(3, "degree    ");
		methodName.put(4, "wIndegree ");
		methodName.put(5, "wOutdegree");
		methodName.put(6, "wDegree   ");
		methodName.put(7, "pagerank  ");
		methodName.put(8, "betwParall");
		methodName.put(9, "betwApp4  ");
		methodName.put(10, "wBetweenne");

		// Correlations for table without closeness
		printCorrelations(results);

		ResultRow[][] resultsForClosenessCorrelations = new ResultRow[14][];

		for (int i = 0; i < results.length; i++)
			resultsForClosenessCorrelations[i] = matchResultRows(inCloseness,
					results[i]);

		resultsForClosenessCorrelations[11] = inCloseness;
		resultsForClosenessCorrelations[12] = outCloseness;
		resultsForClosenessCorrelations[13] = wCloseness;

		for (int i = 0; i < resultsForClosenessCorrelations.length; i++)
			sortAndWrite(resultsForClosenessCorrelations[i], "test.csv", 30);
		writeRankTable("tableCloseness.csv", resultsForClosenessCorrelations);

		methodName.put(11, "inClosenes");
		methodName.put(12, "outClosene");
		methodName.put(13, "wCloseness");

		// Correlations for table with closeness
		printCorrelations(resultsForClosenessCorrelations);
	}

	private static void printRunningTime(long start) {
		long end = System.currentTimeMillis();
		int seconds = (int) ((end - start) / 1000);
		int minutes = seconds / 60;
		int hours = minutes / 60;
		System.out.printf("Running time: %d:%02d:%02d:%03d%n%n", hours,
				minutes % 60, seconds % 60, (int) ((end - start) % 1000));
	}

	public static void work2() {
		// Create graphs
		Graph publications = Load.publications(new File(BIB_DB), VERBOSE);
		authors = Load.authors(publications, VERBOSE);

		// free publications
		publications = null;
		authors.makeUndirected();

		ResultRow[][] results = new ResultRow[7][];
		results[0] = Betweenness.compute(authors, 1, true);
		results[1] = Betweenness.compute(authors, 2, true);
		results[2] = Betweenness.compute(authors, 4, true);
		results[3] = Betweenness.compute(authors, 8, true);
		results[4] = Betweenness.compute(authors, 16, true);
		results[5] = Betweenness.compute(authors, 32, true);
		results[6] = Betweenness.compute(authors, 64, true);

		for (int i = 0; i < results.length; i++)
			sortAndWrite(results[i], "btw_no_" + i + ".csv", 50);

		methodName = new HashMap<Integer, String>();
		methodName.put(0, "1");
		methodName.put(1, "2");
		methodName.put(2, "4");
		methodName.put(3, "8");
		methodName.put(4, "16");
		methodName.put(5, "32");
		methodName.put(6, "64");

		printCorrelations(results);
	}

	private static void writeRankTable(String fileName, ResultRow[]... methods) {
		int[][] ranks = new int[methods.length][methods[0].length];
		int[] sums = new int[methods.length];
		Map<String, Integer> firstRank = new HashMap<String, Integer>();
		PrintWriter w = null;
		try {
			w = new PrintWriter(new FileWriter(new File(fileName)));
		} catch (IOException e) {
			System.err.println("Error writing to file.");
			return;
		}

		for (int i = 0; i < methods[0].length; i++)
			firstRank.put(methods[0][i].name, i);

		for (int i = 0; i < methods.length; i++)
			for (int j = 0; j < methods[i].length; j++)
				ranks[i][firstRank.get(methods[i][j].name)] = j + 1;

		for (int i = 0; i < methods.length; i++)
			for (int j = 0; j < 30; j++)
				sums[i] += ranks[i][j];

		for (int j = 0; j < methods[0].length; j++) {
			w.print(methods[0][j].name);
			for (int i = 0; i < methods.length; i++) {
				w.print(";");
				w.print(ranks[i][j]);
			}
			w.println();
		}
		w.print("sums");
		for (int i = 0; i < methods.length; i++) {
			w.print(";");
			w.print(sums[i]);
		}

		w.close();
	}

	private static ResultRow[] matchResultRows(ResultRow[] r1, ResultRow[] r2) {
		ResultRow[] smaller = r1;
		ResultRow[] larger = r2;

		Map<String, Boolean> exists = new HashMap<String, Boolean>();
		for (ResultRow r : smaller)
			exists.put(r.name, true);
		ResultRow[] newR = new ResultRow[smaller.length];

		int i = 0;
		for (ResultRow r : larger)
			if (exists.containsKey(r.name))
				newR[i++] = r;

		return newR;
	}

	private static void printCorrelations(ResultRow[][] results) {
		for (int i = 0; i < results.length; i++)
			results[i] = removeSingletons(authors, results[i]);

		for (int i = 0; i < results.length; i++) {
			for (int j = 0; j < results.length; j++) {
				if (i != j) {
					System.out.printf("spearman(%s, %s) = %f",
							methodName.get(i), methodName.get(j),
							Correlation.spearman(results[i], results[j]));
					System.out.println();
				}
			}
		}

		System.out.println();
		System.out.println();
		System.out.println();
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

		// TODO TODO TODO
		work();
		// work2();
		// TODO TODO TODO

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

		for (ResultRow r : result)
			if (authors.getVertex(r.name).degree() > 0)
				notSingletons.add(r);

		return notSingletons.toArray(new ResultRow[notSingletons.size()]);
	}

	private static void sortAndWrite(ResultRow[] result, String fileName,
			int limit) {

		Arrays.sort(result, new Comparator<ResultRow>() {
			@Override
			public int compare(ResultRow r1, ResultRow r2) {
				return r1.value > r2.value ? -1 : r1.value < r2.value ? +1 : 0;
			}
		});

		// if (reversed)
		// for (int i = 0; i < result.length / 2; i++) {
		// ResultRow tmp = result[i];
		// result[i] = result[result.length - 1 - i];
		// result[result.length - 1 - i] = tmp;
		// }

		boolean[] Codd = null, ACMFell = null, ISIHC = null, Turing = null;

		System.out.println("Codd,ACMFell,ISIHC,Turing:");
		if (dbname != null) {
			Codd = Awards.assignAwards(result, dbname, "Codd");
			System.out.println(Awards.getSum(Codd));
			ACMFell = Awards.assignAwards(result, dbname, "ACMFell");
			System.out.println(Awards.getSum(ACMFell));
			ISIHC = Awards.assignAwards(result, dbname, "ISIHC");
			System.out.println(Awards.getSum(ISIHC));
			Turing = Awards.assignAwards(result, dbname, "Turing");
			System.out.println(Awards.getSum(Turing));
		}

		PrintWriter w = null;
		try {
			w = new PrintWriter(new FileWriter(new File(fileName)));
			w.print("author;score");
			if (dbname != null)
				w.print(";Turing;Codd;ACMFell;ISIHC");
			w.println();

			for (int i = 0; i < result.length; i++) {
				if (limit-- <= 0)
					break;

				ResultRow r = result[i];

				w.printf("%s;%.3f", r.name, r.value);

				if (dbname != null) {
					w.print(";");

					if (Turing[i])
						w.print("x");
					w.print(";");
					if (Codd[i])
						w.print("x");
					w.print(";");
					if (ACMFell[i])
						w.print("x");
					w.print(";");
					if (ISIHC[i])
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
