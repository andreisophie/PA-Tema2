import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Magazin {
	static class Task {
		public static final String INPUT_FILE = "magazin.in";
		public static final String OUTPUT_FILE = "magazin.out";

		// numarul maxim de noduri
		public static final int NMAX = (int)1e5 + 5; // 10^5 + 5 = 100.005

		// n = numar de noduri, m = numar de muchii/arce
		int n, q;

		// declar scanner global ca sa il po refolosi
		MyScanner sc;

		// adj[node] = lista de adiacenta a nodului node
		// exemplu: daca adj[node] = {..., neigh, ...} => exista arcul (node, neigh)
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] adj = new ArrayList[NMAX];

		// vectorul de timp cand se viziteaza un nod
		int[] firstVisit = new int[NMAX];
		int[] lastVisit = new int[NMAX];
		int time;
		int[] link = new int[NMAX];

		public void solve() throws IOException {
			readInput();
			dfs(1);

			for (int i = 1; i <= n; i++) {
				link[firstVisit[i]] = i;
			}

			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
			StringBuilder result = new StringBuilder();

			for (int i = 1, src, t; i <= q; i++) {
				src = sc.nextInt();
				t = sc.nextInt();

				int find = firstVisit[src] + t;
				if (find > lastVisit[src]) {
					result.append("-1\n");
					continue;
				}
				result.append(link[find] + "\n");
			}
			pw.write(result.toString());
			
			pw.close();
		}

		private void dfs(int node) {
			firstVisit[node] = ++time;
			for (int neigh : adj[node]) {
				dfs(neigh);
			}
			lastVisit[node] = time;
		}

		private void readInput() {
			try {
				sc = new MyScanner(new FileReader("magazin.in"));
				n = sc.nextInt();
				q = sc.nextInt();

				for (int node = 1; node <= n; node++) {
					adj[node] = new ArrayList<>();
				}

				for (int i = 2, src; i <= n; i++) {
					src = sc.nextInt();
					adj[src].add(i);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		new Task().solve();
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