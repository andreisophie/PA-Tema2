import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Ferate {
	static class Task {
		public static final String INPUT_FILE = "ferate.in";
		public static final String OUTPUT_FILE = "ferate.out";

		// numarul maxim de noduri
		public static final int NMAX = (int)1e5 + 5; // 10^5 + 5 = 100.005

		// n = numar de noduri, m = numar de muchii/arce
		int n, m, s;

		// tipul de date necesar fiecarui nod
		int[] nodeData = new int[NMAX];

		// adj[node] = lista de adiacenta a nodului node
		// exemplu: daca adj[node] = {..., neigh, ...} => exista arcul (node, neigh)
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] adj = new ArrayList[NMAX];

		public void solve() {
			readInput();
			writeOutput(getResult());
		}

		private void readInput() {
			try {
				MyScanner sc = new MyScanner(new FileReader(INPUT_FILE));
				n = sc.nextInt();
				m = sc.nextInt();
				s = sc.nextInt();

				for (int node = 1; node <= n; node++) {
					adj[node] = new ArrayList<>();
				}

				for (int i = 1, x, y; i <= m; i++) {
					// arc (x, y)
					x = sc.nextInt();
					y = sc.nextInt();
					adj[x].add(y);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int answer) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				pw.println(answer);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		boolean[] viz = new boolean[NMAX];
		boolean[] good = new boolean[NMAX];
		int cnt;

		private void dfs1(int src) {
			good[src] = true;
			for (int neigh : adj[src]) {
				if (!good[neigh]) {
					dfs1(neigh);
				}
			}
		}

		private void dfs2(int src) {
			viz[src] = true;
			cnt++;
			for (int neigh : adj[src]) {
				if (!viz[neigh] && !good[neigh]) {
					dfs2(neigh);
				}
			}
		}

		static class Pair implements Comparable<Pair> {
			int first,second;

			Pair(int a,int b) {
				first = a;
				second = b;
			}

			@Override
			public int compareTo(Pair arg0) {
				return arg0.second - this.second;
			}

		}

		private int getResult() {
			// caut toate nodurile catre care pot ajunge deja din s
			dfs1(s);

			// tin minte aici nodurile la care nu pot ajunge si cate noduri
			// inaccesibile imi permite fiecare
			ArrayList<Pair> val = new ArrayList<Pair>();

			// completez lista definita mai sus
			for (int i = 1; i <= n; i++) {
				if (!good[i]) {
					cnt = 0;
					for (int j = 1; j <= n; j++) {
						viz[j] = false;
					}
					dfs2(i);
					val.add(new Pair(i, cnt));
				}
			}

			// sortez nodurile descrescator dupa nr de noduri
			// accesibile din el
			Collections.sort(val);
			
			// adaug pe rand muchii in graf pana cand am vizitat toate nodurile
			int ans = 0;
			for (Pair pair : val) {
				if (!good[pair.first]) {
					ans++;
					dfs1(pair.first);
				}
			}

			return ans;
		}
	}

	public static void main(String[] args) {
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
