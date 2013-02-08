public class ResultRow {
	String name;
	double value;
	
	public ResultRow(String name, double value) {
		this.name = name;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return String.format("%s;%.3f", name, value);
	}
}
