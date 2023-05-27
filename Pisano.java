public class Pisano {
	public static void main(String[] args) {
		long m = 1000000007;
		long prev = 0;
		long curr = 1;
		long res = 0;

		for (long i = 0; i < m * m; i++) {
			if (i % 100000000 == 0) {
				System.out.println(i);
			}
			long temp = 0;
			temp = curr;
			curr = (prev + curr) % m;
			prev = temp;
			
			if (prev == 0 && curr == 1) {
				if (res == 0) {
					res = i + 1;
					break;
				}               
			}
		}
		System.out.println(res);
	}
}
