public class Edge {
	int start, end;
	int w;
	boolean in;
	
	public Edge(int start, int end, int w, boolean in) {
		this.start = start;
		this.end = end;
		this.w = w;
		this.in = in;
	}
	
	public int hashCode() {
		return new Integer(start + end + w).hashCode();
	}
	
	public boolean equals(Object o) {
		Edge e = (Edge) o;
		return e.start == start && e.end == end && e.in == in;
	}
	
}
