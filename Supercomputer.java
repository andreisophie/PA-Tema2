import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Supercomputer {
	static class Task {
		public static final String INPUT_FILE = "supercomputer.in";
		public static final String OUTPUT_FILE = "supercomputer.out";

		// numarul maxim de noduri
		public static final int NMAX = (int)1e5 + 5; // 10^5 + 5 = 100.005

		// n = numar de noduri, m = numar de muchii/arce
		int n, m;

		// tipul de date necesar fiecarui nod
		int[] nodeData = new int[NMAX];

		// adj[node] = lista de adiacenta a nodului node
		// exemplu: daca adj[node] = {..., neigh, ...} => exista arcul (node, neigh)
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] adj = new ArrayList[NMAX];

		// in_degree[node] = gradul intern al nodului node
		int[] inDegree;

		public void solve() {
			readInput();
			writeOutput(getResult());
		}

		private void readInput() {
			try {
				MyScanner sc = new MyScanner(new FileReader(INPUT_FILE));
				n = sc.nextInt();
				m = sc.nextInt();

				for (int node = 1; node <= n; node++) {
					nodeData[node] = sc.nextInt();
					adj[node] = new ArrayList<>();
				}

				inDegree = new int[n + 1];
				Arrays.fill(inDegree, 0);

				for (int i = 1, x, y; i <= m; i++) {
					// arc (x, y)
					x = sc.nextInt();
					y = sc.nextInt();
					adj[x].add(y);
					++inDegree[y];
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

		private int getResult() {
			return Math.min(solve_bfs(1), solve_bfs(2)); // BFS: O(n + m)
		}

		private int solve_bfs(int initialData) {
			// Declaram o coada ce va contine toate nodurile care nu mai au dependente
			// si pe care le putem adauga apoi in topsort.
			Deque<Integer> q = new ArrayDeque<>();
			// am nevoie de o copie locala a indegree-ului, caci fac 2 sortari
			int[] localInDegree = new int[NMAX];
			for (int i = 1; i <= n; i++) {
				localInDegree[i] = inDegree[i];
			} 
			int nrSwaps = 0;

			// Initial adaugam in coada toate nodurile cu grad intern 0 (fara dependente)
			for (int node = 1; node <= n; node++) {
				if (localInDegree[node] == 0) {
					if (nodeData[node] == initialData) {
						q.addFirst(node);
					} else {
						q.addLast(node);
					}
				}
			}

			int currentData = initialData;
			// Cat timp mai sunt noduri in coada
			while (!q.isEmpty()) {
				// Scot primul nod din coada si il adaug la solutie
				int node = q.poll();

				// daca nodul este cu date diferite de ce am incarcat in memorie,
				// trebuie sa incrementez numarul de swap-uri
				if (nodeData[node] != currentData) {
					currentData = nodeData[node];
					nrSwaps++;
				}

				// Parcurg toti vecinii nodului si sterg arcele catre acestia
				for (Integer neigh : adj[node]) {
					// Obs: nu e nevoie sa sterg efectiv arcul,
					// ci pot simula asta prin scaderea gradului intern
					--localInDegree[neigh];

					// daca gradul intern al lui neigh a ajuns la 0 (nu mai are dependente),
					// il adaug in coada
					if (localInDegree[neigh] == 0) {
						// nodurile cu aceleasi date ca si cele incarcate in memorie
						// le adaug in fata, celelalte in spate
						if (nodeData[neigh] == currentData) {
							q.addFirst(neigh);
						} else {
							q.addLast(neigh);
						}
					}
				}
			}

			return nrSwaps;
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