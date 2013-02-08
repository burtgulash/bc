public class Edge {
	int start, end;
	int w;
	
	public Edge(int start, int end, int w) {
		this.start = start;
		this.end = end;
		this.w = w;
	}
	
	public int hashCode() {
		return new Integer(start + end + w).hashCode();
	}
	
	public boolean equals(Object o) {
		Edge e = (Edge) o;
		return e.start == start && e.end == end;
	}
	
}
