import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class Load {
	public static Graph publications(File file, boolean verbose) {
		String line;
		BufferedReader r;

		if (verbose)
			System.out.println("Create network of publications.");
		Graph publications = new Graph();

		try {
			r = new BufferedReader(new FileReader(file));

			// remove csv description line
			r.readLine();
			while ((line = r.readLine()) != null) {
				String[] xs = line.split(";");
				String pubId = xs[0];
				if (pubId == null)
					continue;

				Vertex v = publications.addVertex(pubId);

				for (String author : xs[3].split("#"))
					v.addDatum(author.toUpperCase());
				if (xs.length >= 6)
					for (String citationId : xs[5].split("#"))
						publications.addEdge(pubId, citationId, 1, false);
			}

			r.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found.");
			return null;
		} catch (IOException e) {
			System.err.println("Input exception.");
			return null;
		}

		if (verbose) {
			System.out.println("Network of publications: # of publications: "
					+ publications.vSize());
			System.out.println("Network of publications: # of edges: "
					+ publications.eSize());
		}

		return publications;
	}

	public static Graph authors(Graph publications, boolean verbose) {
		if (publications == null)
			return null;

		if (verbose)
			System.out.println("Create network of authors.");
		Graph authors = new Graph();

		for (Vertex pub : publications.getVertices()) {
			for (String author : pub.data)
				authors.addVertex(author);

			// For all authors of publication
			for (String authorFrom : pub.data)
				// For all citations from publication
				for (Edge link : pub.links)
					// Only if the citation points to existing publication
					if (publications.vertexExists(link.end)) {
						// For all authors of publications cited by publication
						for (String authorTo : publications.getVertex(link.end).data) {
							// Order of authors swapped - we are interested in
							// cited authors, not citing authors
							Edge e = authors.getEdge(authorFrom, authorTo);
							if (e == null)
								authors.addEdge(authorFrom, authorTo, 1, false);
							else
								e.w++;
						}
					}
		}

		if (verbose) {
			System.out.println("Network of authors: # of authors: "
					+ authors.vSize());
			System.out.println("Network of authors: # of edges: "
					+ authors.eSize());
		}

		// Remove self-citations
		int nSelfCitations = 0;
		for (Vertex v : authors.getVertices()) {
			Iterator<Edge> it = v.links.iterator();
			while (it.hasNext()) {
				Edge e = it.next();
				if (e.start == e.end) {
					nSelfCitations++;
					it.remove();
				}
			}
		}

		if (verbose)
			System.out.println("Network of authors: # of self-citations: "
					+ nSelfCitations);

		return authors;
	}
}
