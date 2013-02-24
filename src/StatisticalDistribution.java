import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StatisticalDistribution {
	public static void printEdgeWeightDistribution(Graph g) {
		Di[] result = edgeWeightDistribution(g);
		
		PrintWriter w = null;
		try {
			w = new PrintWriter(new FileWriter(new File("edgeWeightDistribution.csv")));
			w.println("weight;frequency");
			for (Di di : result)
				w.println(di.d + ";" + di.i);
		} catch (IOException e) {
			System.err.println("Error writing to file.");
		} finally {
			w.close();
		}
	}

	public static Di[] edgeWeightDistribution(Graph g) {
		Map<Double, Integer> ws = new HashMap<Double, Integer>();
		
		for (Vertex v : g.getVertices()) {
			for (Edge e : v.outs) {
				double w = 1d / e.w;
				if (!ws.containsKey(w))
					ws.put(w, 0);
				ws.put(w, ws.get(w) + 1);
			}
		}
		
		Di[] dist = new Di[ws.size()];
		int i = 0;
		for (Map.Entry<Double, Integer> di : ws.entrySet())
			dist[i++] = new Di(di.getKey(), di.getValue());
		
		Arrays.sort(dist);
		
		return dist;
	}
	
	private static class Di implements Comparable<Di> {
		private double d; 
		private int i;
		
		private Di(double d, int i) {
			this.d = d;
			this.i = i;
		}

		@Override
		public int compareTo(Di di) {
			return d < di.d ? +1 : d == di.d ? 0 : -1;
		}
	}
}
