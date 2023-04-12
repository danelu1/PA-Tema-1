import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class Semnale {
	// Toate formulele cu "mod" sunt preluate din laborator.
	static int sig_type, x, y;
	static final int  mod = 1000000007;

	Semnale(){}

	/*
	 * Functie ce rezolva task-ul "1".
	 */
	static int type1() {
		// TODO Compute the number of type 1 signals.

		// Matricea in care retinem rezultatele.
		long[][] dp = new long[y + 1][x + y + 1];

		// Cazurile de baza.
		for (int i = 1; i <= x + y; i++) {
			dp[1][i] = i;
		}

		// Relatia de recurenta dintre elementele din matrice.
		for (int i = 2; i <= y; i++) {
			for (int j = 3; j <= x + y; j++) {
				dp[i][j] = (dp[i][j - 1] % mod + dp[i - 1][j - 2] % mod) % mod;
			}
		}

		// Rezultatul(ultimul element din matrice).
		return (int)(dp[y][x + y] % (long)mod);
	}
	

	/*
	 * Functie ce rezolva task-ul "2".
	 */
	static int type2() {
		// TODO Compute the number of type 2 signals.

		// Matricea in care stocam rezultatele.
		long[][] dp = new long[y + 1][x + y + 1];

		// Cazurile de baza.
		for (int i = 1; i <= x + y; i++) {
			dp[1][i] = i;
			dp[2][i] = (i * (i - 1)) / 2;
		}

		// Relatia de recurenta dintre elementele din matrice.
		for (int i = 3; i <= y; i++) {
			for (int j = 4; j <= x + y; j++) {
				dp[i][j] += (dp[i][j - 1] % mod + dp[i - 1][j - 2] % mod 
							+ dp[i - 2][j - 3] % mod) % mod;
			}
		}	

		// Rezultatul(ultimul element din matrice).
		return (int)(dp[y][x + y] % (long)mod);
	}


	public static void main(String[] args) {
		try {
			var sc = new MyScanner(new FileReader("semnale.in"));

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

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

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
