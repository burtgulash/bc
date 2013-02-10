import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HIndex {
	public static ResultRow[] compute(File db) {
		Map<String, Integer> n_citations = new HashMap<String, Integer>();
		Map<String, List<Publication>> authors = new HashMap<String, List<Publication>>();
		BufferedReader r;
		String line;

		try {
			r = new BufferedReader(new FileReader(db));

			// remove csv description line
			r.readLine();
			while ((line = r.readLine()) != null) {
				String[] xs = line.split(";");
				String pubId = xs[0];
				if (pubId == null)
					continue;

				if (!n_citations.containsKey(pubId))
					n_citations.put(pubId, 0);

				if (xs.length >= 6)
					for (String citationId : xs[5].split("#")) {
						if (!n_citations.containsKey(citationId))
							n_citations.put(citationId, 0);
						n_citations.put(citationId,
								n_citations.get(citationId) + 1);
					}

				for (String author : xs[3].split("#")) {
					author = author.toUpperCase();

					if (!authors.containsKey(author))
						authors.put(author, new LinkedList<Publication>());
					authors.get(author).add(new Publication(pubId));
				}
			}

			r.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found.");
			return null;
		} catch (IOException e) {
			System.err.println("Input exception.");
			return null;
		}

		for (List<Publication> pubs : authors.values())
			for (Publication pub : pubs)
				pub.cited = n_citations.get(pub.id);

		ResultRow[] result = new ResultRow[authors.size()];

		int i = 0;
		for (Map.Entry<String, List<Publication>> ai : authors.entrySet()) {
			List<Publication> pubs = ai.getValue();

			Collections.sort(pubs, new Comparator<Publication>() {
				@Override
				public int compare(Publication p1, Publication p2) {
					return p1.cited > p2.cited ? -1 : p1.cited < p2.cited ? +1
							: 0;
				}
			});

			// Compute h-index
			int h = 0;
			for (Publication pub : pubs) {
				if (pub.cited < h)
					break;
				h++;
			}

			result[i++] = new ResultRow(ai.getKey(), h);
		}

		return result;
	}
}

class Publication {
	String id;
	int cited = 0;

	Publication(String id) {
		this.id = id;
	}
}