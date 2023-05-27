import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Semnale {

	static int sig_type, x, y;
	static final int  mod = 1000000007;

	Semnale(){}

	static int pow_modulo(int base, int exp) {
		if (exp == 1) {
			return base;
		}
		if (exp % 2 == 0) {
			return pow_modulo((int)((1L * base * base) % mod), exp / 2);
		}
		return (int)((1L * base * pow_modulo((int)((1L * base * base) % mod), exp / 2)) % mod);
	}

	static int modulo_inverse(int n) {
		return pow_modulo(n, mod - 2);
	}

	static int combinari(int n, int k) {
		int result = 1;
		for (int i = n - k + 1;  i <= n; i++) {
			result = (int)((1L * result * i) % mod);
		}
		for (int i = 1; i <= k; i++) {
			result = (int)((1L * result * modulo_inverse(i)) % mod);
		}
		return result;
	}

	static int type1() {
		return combinari(x + 1, y);
	}

	static int type2() {
		int result = 0;
		for (int nr_groups = y, nr_double = 0; nr_double <= nr_groups; nr_groups--, nr_double++) {
			result = (int)((result + (1L * combinari(x + 1, nr_groups)
				* combinari(nr_groups, nr_double)) % mod) % mod);
		}
		return result;
	}

	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(new File("semnale.in"));

			sig_type = sc.nextInt();
			x = sc.nextInt();
			y = sc.nextInt();

			int ans;
			switch (sig_type) {
				case 1:
					ans = Semnale.type1();
					break;
				case 2:
					ans = Semnale.type2();
					break;
				default:
					ans = -1;
					System.out.println("wrong task number");
			}

			try {
				FileWriter fw = new FileWriter("semnale.out");
				fw.write(Integer.toString(ans));
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
