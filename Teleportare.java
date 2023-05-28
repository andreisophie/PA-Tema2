import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Teleportare {
	static class Task {
		public static final String INPUT_FILE = "teleportare.in";
		public static final String OUTPUT_FILE = "teleportare.out";

		// numarul maxim de noduri
		public static final int NMAX = 50005;

		// valoare mai mare decat orice distanta din graf
		public static final int INF = (int) 1e9;

		// n = numar de noduri, m = numar de muchii
		int n, m, t;

		public class Pair implements Comparable<Pair> {
			public int destination;
			public int cost;

			Pair(int _destination, int _cost) {
				destination = _destination;
				cost = _cost;
			}

			public int compareTo(Pair rhs) {
				return Integer.compare(cost, rhs.cost);
			}
		}

		// adj[node] = lista de adiacenta a nodului node
		// perechea (neigh, w) semnifica arc de la node la neigh de cost w
		@SuppressWarnings("unchecked")
		ArrayList<Pair>[] adj = new ArrayList[NMAX];

		public void solve() {
			readInput();
			writeOutput(getResult());
		}

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
						INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();
				t = sc.nextInt();

				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
					int x, y, w;
					x = sc.nextInt();
					y = sc.nextInt();
					w = sc.nextInt();
					adj[x].add(new Pair(y, w));
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int result) {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(
						OUTPUT_FILE));
				bw.write(result + "\n");
				System.out.println(result);
				bw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private int getResult() {
			List<Integer> d = new ArrayList<>();
			List<Integer> p = new ArrayList<>();

			for (int i = 0; i <= n; i++) {
				d.add(INF);
				p.add(-1);
			}

			PriorityQueue<Pair> pq = new PriorityQueue<>();

			pq.add(new Pair(1, 0));
			d.set(1, 0);

			while (!pq.isEmpty()) {
				Pair nodePair = pq.poll();
				int node = nodePair.destination;

				for (Pair neighPair : adj[node]) {
					int neigh = neighPair.destination;
					int cost = neighPair.cost;

					if (d.get(node) + cost < d.get(neigh)) {
						d.set(neigh, d.get(node) + cost);
						p.set(neigh, node);

						pq.removeIf((Pair pair) -> { 
							return pair.destination == neigh; 
						});
						pq.add(new Pair(neigh, d.get(neigh)));
					}
				}
			}

			for (int i = 0; i <= n; i++) {
				if (d.get(i) == INF) {
					d.set(i, -1);
				}
			}

			return d.get(n);
		}

	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
