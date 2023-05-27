import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Sushi {

	static int n, m, x;
	static int[] prices;
	static int[][] grades;
	static int[] sumGrades;

	Sushi(){}

	static void calculateSumgrades() {
		sumGrades = new int[m + 1];
		// i is the friend, j is the sushi
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				sumGrades[j] += grades[i][j];
			}
		}
	}

	static int task1() {
		calculateSumgrades();

		int totalMoney = n * x;
		int[][] dp = new int[m + 1][totalMoney + 1];
		// base case
		for (int cap = 0; cap <= totalMoney; cap++) {
			dp[0][cap] = 0;
		}
		// cazul general
		for (int i = 1; i <= m; i++) {
			for (int cap = 0; cap <= totalMoney; cap++) {
				// nu cumpar platoul i => e soluția de la pasul i - 1
				dp[i][cap] = dp[i - 1][cap];

				// cumpar platoul i, deci trebuie sa ma asigur ca am suficienti bani
				// înseamnă ca trebuie sa mai am cap - prices[i] unități
				if (cap - prices[i] >= 0) {
					int sol_aux = dp[i - 1][cap - prices[i]] + sumGrades[i];

					dp[i][cap] = Math.max(dp[i][cap], sol_aux);
				}
			}
		}
		return dp[m][totalMoney];
	}

	static int task2() {
		calculateSumgrades();
		int[] prices2 = new int[2 * m + 1];
		int[] sumGrades2 = new int[2 * m + 1];
		for (int i = 1; i <= m; i++) {
			prices2[2 * i - 1] = prices2[2 * i] = prices[i];
			sumGrades2[2 * i - 1] = sumGrades2[2 * i] = sumGrades[i];
		}
		int totalMoney = n * x;
		int[][] dp = new int[2 * m + 1][totalMoney + 1];
		// base case
		for (int cap = 0; cap <= totalMoney; cap++) {
			dp[0][cap] = 0;
		}
		// cazul general
		for (int i = 1; i <= 2 * m; i++) {
			for (int cap = 0; cap <= totalMoney; cap++) {
				// nu cumpar platoul i => e soluția de la pasul i - 1
				dp[i][cap] = dp[i - 1][cap];

				// cumpar platoul i, deci trebuie sa ma asigur ca am suficienti bani
				// înseamnă ca trebuie sa mai am cap - prices[i] unități
				if (cap - prices2[i] >= 0) {
					int sol_aux = dp[i - 1][cap - prices2[i]] + sumGrades2[i];

					dp[i][cap] = Math.max(dp[i][cap], sol_aux);
				}
			}
		}
		return dp[2 * m][totalMoney];
	}

	static int task3() {
		calculateSumgrades();
		int[] prices2 = new int[2 * m + 1];
		int[] sumGrades2 = new int[2 * m + 1];
		for (int i = 1; i <= m; i++) {
			prices2[2 * i - 1] = prices2[2 * i] = prices[i];
			sumGrades2[2 * i - 1] = sumGrades2[2 * i] = sumGrades[i];
		}
		int totalMoney = n * x;
		int[][][] dp = new int[2 * m + 1][totalMoney + 1][n + 1];
		// base case
		for (int cap = 0; cap <= totalMoney; cap++) {
			dp[0][cap][0] = 0;
		}
		// cazul general
		for (int i = 1; i <= 2 * m; i++) {
			for (int cap = 1; cap <= totalMoney; cap++) {
				for (int k = 1; k <= n; k++) {
					if (cap >= prices2[i]) {
						dp[i][cap][k] = Math.max(dp[i - 1][cap][k],
							dp[i - 1][cap - prices2[i]][k - 1] + sumGrades2[i]);
					} else {
						dp[i][cap][k] = dp[i - 1][cap][k];
					}
				}
			}
		}
		System.out.println(dp[2 * m][totalMoney][n]);
		return dp[2 * m][totalMoney][n];
	}

	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(new File("sushi.in"));

			final int task = sc.nextInt(); // task number

			n = sc.nextInt(); // number of friends
			m = sc.nextInt(); // number of sushi types
			x = sc.nextInt(); // how much each of you is willing to spend

			// prices of each sushi type
			prices = new int[m + 1];
			// the grades you and your friends gave to each sushi type
			grades = new int[n + 1][m + 1];

			// price of each sushi
			for (int i = 1; i <= m; i++) {
				prices[i] = sc.nextInt();
			}

			// each friends rankings of sushi types
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= m; j++) {
					grades[i][j] = sc.nextInt();
				}
			}

			int ans;
			switch (task) {
				case 1:
					ans = Sushi.task1();
					break;
				case 2:
					ans = Sushi.task2();
					break;
				case 3:
					ans = Sushi.task3();
					break;
				default:
					ans = -1;
					System.out.println("wrong task number");
			}

			try {
				FileWriter fw = new FileWriter("sushi.out");
				fw.write(Integer.toString(ans) + '\n');
				fw.close();

			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}
