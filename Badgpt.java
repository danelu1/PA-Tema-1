import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Badgpt {
	// String-ul primit ca input.
	private static String str;
	// Constanta descrisa in enunt(10^9 + 7).
	private static final int mod = 1000000007;

	Badgpt(){}

	/*
	 * Functiile pentru exponentierea logaritmica sunt preluate
	 * din lab-ul de PA si modificate pe baza nevoilor problemei.
	 */

	/*
	 * Functie auxiliara ce inmulteste submatricile superioare de
	 * 2X2 din matricile initiale.
	 */
	private static long[][] multiply(long[][] a, long[][] b) {
		long[][] res = new long[2][2];

		res[0][0] = (a[0][0] % mod * b[0][0] % mod) % mod + (a[0][1] % mod * b[1][0] % mod) % mod;
		res[0][1] = (a[0][0] % mod * b[0][1] % mod) % mod + (a[0][1] % mod * b[1][1] % mod) % mod;
		res[1][0] = (a[1][0] % mod * b[0][0] % mod) % mod + (a[1][1] % mod * b[1][0] % mod) % mod;
		res[1][1] = (a[1][0] % mod * b[0][1] % mod) % mod + (a[1][1] % mod * b[1][1] % mod) % mod;

		return res;
	}

	/*
	 * Functie ce realizeaza ridicarea la putere a unei matrici
	 * in timp logaritmic. Preluata din lab-ul 4 de PA si modificata
	 * putin.
	 */
	private static long[][] matrixNthPower(long[][] matrix, long n) {
		long[][] res = { {1, 0}, {0, 1} };
		while (n != 0) {
			if (n % 2 != 0) {
				res = multiply(res, matrix);
			}
			matrix = multiply(matrix, matrix);
			n /= 2;
		}

		return res;
	}

	/*
	 * Functie ce intoarce al "n"-lea numar Fibonacci
	 */
	private static long fibb(long n) {
		long[][] matrix = { {1, 1}, {1, 0} };
		return matrixNthPower(matrix, n)[0][0] % mod;
	}

	/*
	 * Functie ce proceseaza un string primit ca input, extragand din acesta
	 * doar numerele din dreptul caracterelor 'u' si 'v'(daca acestea exista).
	 */
	private static List<String> transform(String s) {
		List<String> list = new ArrayList<>();
		int i = 0;
		int len = s.length();

		while (i < len) {
			
			if (s.charAt(i) == 'n' || s.charAt(i) == 'u') {
				i++;
				String str = "";
			
				while (i < len && Character.isDigit(s.charAt(i))) {
					str += s.charAt(i);
					i++;
				}
			
				list.add(str);
			
			} else {
				i++;
			}
		}

		return list;
	}

	/*
	 * Functie ce rezolva problema.
	 */
	private static int solve() {

		// Extragem din string-ul initial doar numerele
		// din dreptul literelor 'u' si 'v'.
		List<String> list = transform(str);

		// Daca lista e goala, nu avem 'n' sau 'u', deci intoarcem 1.
		if (list.size() == 0) {
			return 1;
		}

		// Variabila pentru stocarea rezultatului.
		long result = 1;

		// Parcurgem lista extrasa si aplicam metoda descrisa
		// la inceput. De fiecare data trebuie transformat string-ul
		// de la indicele "i" din lista de mai sus in numar.
		for (int i = 0; i < list.size(); i++) {
			result = (result % mod * fibb(Long.parseLong(list.get(i))) % (long)mod) % (long)mod;
		}

		return (int)(result % (long)mod);
	}

	/*
	 * Codul de mai jos e preluat de la exercitiile anterioare si
	 * adaptat pentru datele de intrare ale problemei.
	 */
	public static void main(String[] args) {
		try {
			var sc = new MyScanner(new FileReader("badgpt.in"));

			str = sc.next();

			int ans = Badgpt.solve();

			try {
				FileWriter fw = new FileWriter("badgpt.out");
				fw.write(Integer.toString(ans) + '\n');
				fw.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * Clasa pusa la dispozitie de echipa de "PA"
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