import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
	private Map<Integer, String> toName;
	private Map<String, Integer> toId;
	private List<Vertex> vertices;
	private int globalId = 0;
	private boolean undirected = false;

	public Graph() {
		toName = new HashMap<Integer, String>();
		toId = new HashMap<String, Integer>();
		vertices = new ArrayList<Vertex>();
	}

	public Collection<Vertex> getVertices() {
		return vertices;
	}

	public int vSize() {
		return vertices.size();
	}

	public int eSize() {
		int es = 0;
		for (Vertex v : vertices)
			es += v.degree();

		if (undirected)
			es /= 2;

		return es;
	}

	public String getVertexName(int id) {
		return toName.get(id);
	}

	public Integer getVertexId(String name) {
		return toId.get(name);
	}

	public Vertex getVertex(int id) {
		if (0 <= id && id < vSize())
			return vertices.get(id);

		return null;
	}

	public Vertex getVertex(String name) {
		Integer id = toId.get(name);
		if (id == null)
			return null;

		return vertices.get(id);
	}

	public Vertex addVertex(String name) {
		if (!toId.containsKey(name)) {
			toId.put(name, globalId);
			toName.put(globalId, name);

			Vertex v = new Vertex(globalId);
			vertices.add(v);

			assert (vertices.get(globalId) == v);
			globalId++;

			return v;
		}

		return vertices.get(toId.get(name));
	}

	public boolean vertexExists(int id) {
		return 0 <= id && id < vSize();
	}
	
	public Edge getOutEdge(int from, int to) {
		Vertex fromVertex = vertices.get(from);

		// `From id' must exist.
		assert (fromVertex != null);

		for (Edge e : fromVertex.outs)
			if (e.end == to)
				return e;

		return null;
	}

	public Edge getOutEdge(String from, String to) {
		Integer fromVertexId = toId.get(from);
		Integer toVertexId = toId.get(to);

		if (fromVertexId == null || toVertexId == null)
			return null;

		return getOutEdge(fromVertexId, toVertexId);
	}

	public void addEdge(String from, String to, int w, boolean in) {
		// Add non-data vertices. The vertices should exist already, but just in
		// case they are added to prevent null-pointer exceptions in case of non
		// existing vertices.

		addVertex(from);
		addVertex(to);

		Integer fromVertexId = toId.get(from);
		Integer toVertexId = toId.get(to);

		if (in)
			vertices.get(toVertexId).addInEdge(fromVertexId, w);
		else
			vertices.get(fromVertexId).addOutEdge(toVertexId, w);
	}

	public void addBidirectionalEdge(String from, String to, int w) {
		addEdge(from, to, w, false);
		addEdge(to, from, w, true);
	}

	public void printInEdges() {
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).ins.size() > 0) {
				System.out.print(toName.get(i) + " : ");

				for (Edge e : vertices.get(i).ins) {
					System.out.print(toName.get(e.end));
					System.out.print(", ");
				}

				System.out.println();
			}
		}
	}

	public void makeUndirected() {
		for (Vertex v : vertices) {
			for (Edge e : v.ins)
				vertices.get(e.end).addOutEdge(v.id, e.w);
			for (Edge e : v.outs)
				vertices.get(e.end).addInEdge(v.id, e.w);
		}

		undirected = true;
	}
}
