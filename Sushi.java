import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;
import javax.script.ScriptEngine;

class Sushi {

	static int n, m, x;
	static int[] prices;
	static int[][] grades;

	Sushi(){}

	/*
	 * Functie care calculeaza suma de pe fiecare coloana dintr-o matrice
	 * data ca parametru(folosita in rezolvarea fiecarui task pentru a
	 * extrage scorurile pentru fiecare platou).
	 */
	static int[] columnSum(int[][] arr) {
		int size = arr[0].length;
		int[] res = new int[size];

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				res[j] += arr[i][j];
			}
		}

		return res;
	}

	/*
	 * Functie ce rezolva task-ul "1".
	 */
	static int task1() {
		// TODO solve task 1

		// Matricea in care stochez solutiile
		int[][] dp = new int[m + 1][n * x + 1];
		// Vectorul cu scorurile pentru fiecare platou.
		int[] sums = columnSum(grades);

		// Cazurile de baza(nu am scor daca nu am platouri cumparate).
		for (int i = 0; i <= n * x; i++) {
			dp[0][i] = 0;
		}

		// Construirea solutiilor
		for (int i = 1; i <= m; i++) {
			for (int j = 0; j <= n * x; j++) {
				// Initial avem solutia anterioara
				dp[i][j] = dp[i - 1][j];
				
				// Verificam daca se poate cumpara platoul urmator, caz in care
				// inlocuim solutia anterioara cu maximul dintre ea si scorul
				// obtinut prin adunarea scorului platoului nou cumparat.
				if (j - prices[i - 1] >= 0) {
					dp[i][j] = Math.max(dp[i][j], sums[i - 1] + dp[i - 1][j - prices[i - 1]]);
				}
			}
		}

		// Rezultatul e ultimul element din matrice.
		return dp[m][n * x];
	}


	/*
	 * Functie care primeste ca argument un vector si intoarce
	 * vectorul rezultat prin dublarea celui initial.
	 */
	static int[] repeat(int[] a) {
		int m = a.length;
		int[] b = new int[2 * m];
		
		for (int i = 0; i < m; i++) {
			b[i] = a[i];
		}

		for (int i = m; i < 2 * m; i++) {
			b[i] = a[i - m];
		}

		return b;
	}


	/*
	 * Functie ce rezolva task-ul "2".
	 */
	static int task2() {
		// TODO solve task 2

		// Matricea ce contine toate solutiile.
		int[][] dp = new int[2 * m + 1][n * x + 1];
		// Vectorul cu scorurile fiecarui platou.
		int[] scores = columnSum(grades);
		// Vectorul cu preturile care apar de 2 ori.
		int[] repeatingPrices = repeat(prices);
		// Vecotrul cu scorurile care apar de 2 ori.
		int[] repeatingScores = repeat(scores);

		// Cazurile de baza
		for (int i = 0; i <= n * x; i++) {
			dp[0][i] = 0;
		}

		// Aceeasi dinamica de mai devreme, dar pentru "2 * m" platouri.
		for (int i = 1; i <= 2 * m; i++) {
			for (int j = 0; j <= n * x; j++) {
				dp[i][j] = dp[i - 1][j];
				
				if (j - repeatingPrices[i - 1] >= 0) {
					dp[i][j] = Math.max(dp[i][j], repeatingScores[i - 1] 
										+ dp[i - 1][j - repeatingPrices[i - 1]]);
				}
			}
		}

		return dp[2 * m][n * x];
	}


	/*
	 * Functie care "rearanjeaza" vectorii primiti ca parametru(mai 
	 * multe detalii in "README").
	 */
	static int[][] rearrange(int[] a, int[] b, int k) {
		int len = a.length;
		int[] repeatA = repeat(a);
		int[] repeatB = repeat(b);
		ArrayList<ArrayList<Integer>> res = new ArrayList<>();

		for (int i = 0; i < 2 * len; i++) {
			res.add(new ArrayList<>(Arrays.asList(repeatA[i], repeatB[i])));
		}

		Collections.sort(res, new Comparator<ArrayList<Integer>>() {
			public int compare(ArrayList<Integer> l1, ArrayList<Integer> l2) {
				if (l1.get(0) > l2.get(0)) {
					return -1;
				} else if (l1.get(0) == l2.get(0)) {
					return l1.get(1) - l2.get(1);
				} else {
					return 1;
				}
			}
		});

		int[] finalA = new int[k];
		int[] finalB = new int[k];

		for (int i = 0; i < k; i++) {
			finalA[i] = res.get(i).get(1);
			finalB[i] = res.get(i).get(0);
		}

		return new int[][] {finalA, finalB};
	}


	/*
	 * Functie pentru rezolvarea task-ului "3".
	 */
	static int task3() {
		// TODO solve task 3

		// Matricea ce contine toate solutiile
		int[][] dp = new int[n + 1][n * x + 1];
		// Vectorul cu scorurile fiecarui platou
		int[] scores = columnSum(grades);
		// Vectorul final de scoruri in urma aplicarii functiei "rearrange".
		int[] finalScores = rearrange(scores, prices, n)[1];
		// Vectorul final de preturi in urma aplicarii functiei "rearrange".
		int[] finalPrices = rearrange(scores, prices, n)[0];

		// Cazurile de baza.
		for (int i = 0; i <= n * x; i++) {
			dp[0][i] = 0;
		}

		// Aceeasi dinamica aplicata pentru primele "n" platouri.
		for (int i = 1; i <= n; i++) {
			for (int j = 0; j <= n * x; j++) {
				dp[i][j] = dp[i - 1][j];
				
				if (j - finalPrices[i - 1] >= 0) {
					dp[i][j] = Math.max(dp[i][j], finalScores[i - 1] 
										+ dp[i - 1][j - finalPrices[i - 1]]);
				}
			}
		}

		return dp[n][n * x];
	}
	

	public static void main(String[] args) {
		try {
			var sc = new MyScanner(new FileReader("sushi.in"));

			final int task = sc.nextInt(); // task number

			n = sc.nextInt(); // number of friends
			m = sc.nextInt(); // number of sushi types
			x = sc.nextInt(); // how much each of you is willing to spend

			prices = new int[m]; // prices of each sushi type
			grades = new int[n][m]; // the grades you and your friends gave to each sushi type

			// price of each sushi
			for (int i = 0; i < m; ++i) {
				prices[i] = sc.nextInt();
			}

			// each friends rankings of sushi types
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < m; ++j) {
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
