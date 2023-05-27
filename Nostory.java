/*
 * Acest schelet citește datele de intrare și scrie răspunsul generat de voi,
 * astfel că e suficient să completați cele două metode.
 *
 * Scheletul este doar un punct de plecare, îl puteți modifica oricum doriți.
 *
 * Dacă păstrați scheletul, nu uitați să redenumiți clasa și fișierul.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Nostory {
	public static void main(final String[] args) throws IOException {
		var scanner = new MyScanner(new FileReader("nostory.in"));

		var task = scanner.nextInt();
		var n = scanner.nextInt();
		var moves = task == 2 ? scanner.nextInt() : 0;

		var a = new int[n];
		for (var i = 0; i < n; i += 1) {
			a[i] = scanner.nextInt();
		}

		var b = new int[n];
		for (var i = 0; i < n; i += 1) {
			b[i] = scanner.nextInt();
		}

		try (var printer = new PrintStream("nostory.out")) {
			if (task == 1) {
				printer.println(solveTask1(a, b));
			} else {
				printer.println(solveTask2(a, b, moves));
			}
		}
	}

	private static long solveTask1(int[] a, int[] b) {
		int n = a.length;
		Arrays.sort(a);
		Arrays.sort(b);

		long score = 0;
		int idx_a = n - 1, idx_b = n - 1;
		for (int i = 0; i < n; i++) {
			if (a[idx_a] > b[idx_b]) {
				score += a[idx_a--];
			} else {
				score += b[idx_b--];
			}
		}
		return score;
	}

	private static long solveTask2(int[] a, int[] b, int moves) {
		int n = a.length;
		int[] mins = new int[n];
		int[] maxs = new int[n];

		long score = 0;
		for (int i = 0; i < n; i++) {
			if (a[i] < b[i]) {
				mins[i] = a[i];
				maxs[i] = b[i];
				score += b[i];
			} else {
				mins[i] = b[i];
				maxs[i] = a[i];
				score += a[i];
			}
		}

		Arrays.sort(mins);
		Arrays.sort(maxs);
		for (int i = 0; i < moves; i++) {
			if (mins[n - 1 - i] < maxs[i]) {
				break;
			}
			score += mins[n - 1 - i] - maxs[i];
		}


		System.out.println(score);
		return score;
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
