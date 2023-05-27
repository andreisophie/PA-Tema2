import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Badgpt {
	static int MOD = 1000000007;
	static long pisano = 2000000016;
	String input;

	public class Fibonacci {
		final int[][] base = {{1, 1}, {1, 0}};
		HashMap<Integer, Integer> calculatedValues = new HashMap<>();

		public int[][] matrixMultiplication(int[][] matrix1, int[][] matrix2) {
			int[][] result = new int[2][2];
			result[0][0] = (int)(((1L * matrix1[0][0] * matrix2[0][0]) % MOD
				+ (1L * matrix1[0][1] * matrix2[1][0]) % MOD) % MOD);
			result[0][1] = (int)(((1L * matrix1[0][0] * matrix2[0][1]) % MOD
				+ (1L * matrix1[0][1] * matrix2[1][1]) % MOD) % MOD);
			result[1][0] = (int)(((1L * matrix1[1][0] * matrix2[0][0]) % MOD
				+ (1L * matrix1[1][1] * matrix2[1][0]) % MOD) % MOD);
			result[1][1] = (int)(((1L * matrix1[1][0] * matrix2[0][1]) % MOD
				+ (1L * matrix1[1][1] * matrix2[1][1]) % MOD) % MOD);
			return result;
		}

		public int[][] matrixExponentiation(int[][] matrix, int exponent) {
			if (exponent == 1) {
				return matrix;
			}
			if (exponent % 2 == 0) {
				return matrixExponentiation(matrixMultiplication(matrix, matrix), exponent / 2);
			}
			return matrixMultiplication(matrixExponentiation(
				matrixMultiplication(matrix, matrix), exponent / 2), matrix);
		}

		public int get(int index) {
			if (calculatedValues.containsKey(index)) {
				return calculatedValues.get(index).intValue();
			}
			int[][] matrix = matrixExponentiation(base, index);
			calculatedValues.put(index + 1, matrix[0][0]);
			calculatedValues.put(index, matrix[0][1]);
			calculatedValues.put(index - 1, matrix[1][1]);
			return matrix[0][1];
		}
	}

	private void solve() throws IOException {
		Fibonacci fibonacci = new Fibonacci();
		long total = 1;
		fibonacci.get(1);
		String[] letters = input.split("[0-9]+");
		String[] numbersStrings = input.split("[a-z]");
		long[] numbers = new long[numbersStrings.length - 1];
		for (int i = 1; i < numbersStrings.length; i++) {
			numbers[i - 1] = (Long.parseLong(numbersStrings[i])) % pisano;
		}
		for (int i = 0; i < letters.length; i++) {
			if (letters[i].equals("u") || letters[i].equals("n")) {
				total = (total * (fibonacci.get((int)numbers[i])
					+ fibonacci.get((int)numbers[i] - 1)) % MOD) % MOD;
			}
		}

		PrintStream printer = new PrintStream("badgpt.out");
		printer.println(total);
		printer.close();
	}

	private void readInput() throws IOException {
		MyScanner scanner = new MyScanner(new FileReader("badgpt.in"));
		input = scanner.nextLine();
	}

	public static void main(String[] args) throws IOException {
		Badgpt task = new Badgpt();
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