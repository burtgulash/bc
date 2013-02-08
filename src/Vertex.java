import java.util.LinkedList;
import java.util.List;

public class Vertex {
	final int id;
	List<Edge> links;
	List<String> data;
	
	public Vertex(int id) {
		this.id = id;
		links = new LinkedList<Edge>();
		data = new LinkedList<String>();
	}
	
	public void addDatum(String datum) {
		data.add(datum);
	}
	
	public void addLink(int to, int w, boolean in) {
		Edge e = new Edge(id, to, w, in);
		if (!links.contains(e))
			links.add(e);
	}
	
	public int inDegree() {
		int c = 0;
		
		for (Edge e : links)
			if (e.in)
				c ++;
		
		return c;
	}
	
	public int outDegree() {
		return degree() - inDegree();
	}
	
	public int degree() {
		return links.size();
	}
}
