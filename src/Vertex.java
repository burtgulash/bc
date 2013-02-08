import java.util.LinkedList;
import java.util.List;

public class Vertex {
	final int id;
	List<Edge> ins;
	List<Edge> outs;
	List<String> data;

	public Vertex(int id) {
		this.id = id;
		ins = new LinkedList<Edge>();
		outs = new LinkedList<Edge>();
		data = new LinkedList<String>();
	}

	public void addDatum(String datum) {
		data.add(datum);
	}

	public void addInEdge(int to, int w) {
		Edge e = new Edge(id, to, w);
		if (!ins.contains(e))
			ins.add(e);
	}
	
	public void addOutEdge(int to, int w) {
		Edge e = new Edge(id, to, w);
		if (!outs.contains(e))
			outs.add(e);
	}
	
	public int weightedIndegree() {
		int d = 0;
		
		for (Edge i : ins)
			d += i.w;
		
		return d;
	}
	
	public int weightedOutdegree() {
		int d = 0;
		
		for (Edge i : outs)
			d += i.w;
		
		return d;
	}
	
	public int weightedDegree() {
		return weightedIndegree() + weightedOutdegree();
	}

	public int indegree() {
		return ins.size();
	}

	public int outdegree() {
		return outs.size();
	}

	public int degree() {
		return indegree() + outdegree();
	}
}
