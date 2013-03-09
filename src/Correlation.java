import java.util.Arrays;
import java.util.Comparator;

public class Correlation {

	private static double mean(ResultRow[] rs) {
		double mean = 0;

		for (ResultRow r : rs)
			mean += r.value;
		mean /= rs.length;

		return mean;
	}

	private static double variance(ResultRow[] rs, double mean) {
		assert (rs.length > 1); // else divide by zero.

		double var = 0;

		for (ResultRow r : rs)
			var += (r.value - mean) * (r.value - mean);
		var /= (double) (rs.length - 1);

		var = Math.sqrt(var);
		
		return var;
	}
	
	private static void sortByName(ResultRow[] xs) {
		Arrays.sort(xs, new Comparator<ResultRow>() {
			@Override
			public int compare(ResultRow r1, ResultRow r2) {
				return r1.name.compareTo(r2.name);
			}
		});
	}
	private static void sortByValue(ResultRow[] xs) {
		Arrays.sort(xs, new Comparator<ResultRow>() {
			@Override
			public int compare(ResultRow r1, ResultRow r2) {
				return new Double(r1.value).compareTo(r2.value);
			}
		});
	}
	
	private static ResultRow[] toRanks(ResultRow[] xs) {
		sortByValue(xs);
		ResultRow[] ns = new ResultRow[xs.length];
		
		for (int i = 0; i < xs.length; i++)
			ns[i] = new ResultRow(xs[i].name, i);
		
//		for (int i = 0; i < ns.length; i++) {
//			int count = 0;
//			for (int j = i + 1; j < ns.length && ns[j].value == ns[i].value; j++)
//				count++;
//			
//			double rank = (double) (i + i + count) / 2d;
//			for (int j = i; j <= i + count; j++)
//				ns[j].value = rank;
//			
//			i += count;
//		}
		
		return ns;
	}
	
	public static double spearman(ResultRow[] xs, ResultRow[] ys) {
		return pearson(toRanks(xs), toRanks(ys));
	}

	public static double pearson(ResultRow[] xs, ResultRow[] ys) {
		assert (xs.length == ys.length);
		int n = xs.length;

		sortByName(xs);
		sortByName(ys);

		double x_hat = mean(xs);
		double y_hat = mean(ys);

		double s_x = variance(xs, x_hat);
		double s_y = variance(ys, y_hat);

		double cov = 0;

		double nxy = (double) n * x_hat * y_hat;
		for (int i = 0; i < n; i++)
			cov += xs[i].value * ys[i].value;
		cov = (cov - nxy) / (((double) n - 1d) * s_x * s_y);

		return cov;
	}
}
