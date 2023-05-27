import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.StringTokenizer;

public class Feribot {
	long[] v;
	int n, k;

	private boolean isPossible(long maxCost) {
		int nrFerries = 0;
		long currentSum = 0;
		for (int i = 0; i < n; i++) {
			if (currentSum + v[i] > maxCost) {
				nrFerries++;
				currentSum = v[i];
			} else {
				currentSum += v[i];
			}
		}
		if (currentSum > 0) {
			nrFerries++;
		}

		return nrFerries <= k;
	}

	private void solve() throws IOException {
		long maxCar = 0, totalWeight = 0;

		for (int i = 0; i < n; i++) {
			totalWeight += v[i];
			if (v[i] > maxCar) {
				maxCar = v[i];
			}
		}

		long result = 0;
		long stg = maxCar, dr = totalWeight;
		long middle;
		while (stg <= dr) {
			middle = (stg + dr) / 2;
			if (isPossible(middle)) {
				result = middle;
				dr = middle - 1;				
			} else {
				stg = middle + 1;
			}
		}

		PrintStream printer = new PrintStream("feribot.out");
		printer.println(result);
		printer.close();
	}

	private void readInput() throws IOException {
		MyScanner scanner = new MyScanner(new FileReader("feribot.in"));
		n = scanner.nextInt();
		k = scanner.nextInt();
		v = new long[n];
		for (int i = 0; i < n; i++) {
			v[i] = scanner.nextLong();
		}
	}

	public static void main(String[] args) throws IOException {
		Feribot task = new Feribot();
		task.readInput();
		task.solve();
	}

	/**
	 * A class for buffering read operations, inspired from here:
	 * https://pastebin.com/XGUjEyMN.
	 */
	private static class MyScanner {
		private BufferedReader br;
		private StringTokenizer st;

		public MyScanner(Reader reader) {
			br = new BufferedReader(reader);
		}

		public String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}

		public String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}
}