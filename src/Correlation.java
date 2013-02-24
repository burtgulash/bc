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

		return var;
	}

	public static double pearson(ResultRow[] xs, ResultRow[] ys) {
		assert (xs.length == ys.length);
		int n = xs.length;

		Arrays.sort(xs, new Comparator<ResultRow>() {
			@Override
			public int compare(ResultRow r1, ResultRow r2) {
				return r1.name.compareTo(r2.name);
			}
		});

		Arrays.sort(ys, new Comparator<ResultRow>() {
			@Override
			public int compare(ResultRow r1, ResultRow r2) {
				return r1.name.compareTo(r2.name);
			}
		});

		double x_hat = mean(xs);
		double y_hat = mean(ys);

		double s_x = variance(xs, x_hat);
		double s_y = variance(ys, y_hat);

		double cov = 0;

		double nxy = n * x_hat * y_hat;
		for (int i = 0; i < n; i++)
			cov += xs[i].value * ys[i].value - nxy;
		cov /= ((double) n - 1d) * s_x * s_y;

		return cov;
	}
}
