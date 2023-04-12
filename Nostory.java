/*
 * Acest schelet citește datele de intrare și scrie răspunsul generat de voi,
 * astfel că e suficient să completați cele două metode.
 *
 * Scheletul este doar un punct de plecare, îl puteți modifica oricum doriți.
 *
 * Dacă păstrați scheletul, nu uitați să redenumiți clasa și fișierul.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Nostory {
	public static void main(final String[] args) throws IOException {
		var scanner = new MyScanner(new FileReader("nostory.in"));

		var task = scanner.nextInt();
		var n = scanner.nextInt();
		var moves = task == 2 ? scanner.nextInt() : 0;

		var a = new int[n];
		for (var i = 0; i < n; i += 1) {
			a[i] = scanner.nextInt();
		}

		var b = new int[n];
		for (var i = 0; i < n; i += 1) {
			b[i] = scanner.nextInt();
		}

		try (var printer = new PrintStream("nostory.out")) {
			if (task == 1) {
				printer.println(solveTask1(a, b));
			} else {
				printer.println(solveTask2(a, b, moves));
			}
		}
	}

	/*
	 * Functie ce rezolva task-ul "1".
	 */
	private static long solveTask1(int[] a, int[] b) {
		
		// Lungimea sirurilor initiale.
		int n = a.length;
		
		// Sir in care stocam toate elementele din cele 2 siruri.
		int[] arr = new int[2 * n];

		// Salvez in sir elementele din "a".
		for (int i = 0; i < n; i++) {
			arr[i] = a[i];
		}

		// Salvez in sir elementele din "b". Salvarea se face
		// in jumatatea libera alocata initial(n -> 2 * n - 1).
		for (int i = n; i < 2 * n; i++) {
			arr[i] = b[2 * n - i - 1];
		}

		// Sortam sirul astfel format.
		Arrays.sort(arr);

		// Variabila in care retinem rezultatul
		long sum = 0;

		// Adunam in variabila "sum" doar primele cele mai mari
		// "n" elemente din sir.
		for (int i = n; i < 2 * n; i++) {
			sum += (long)arr[i];
		}

		// Intoarcem rezultatul.
		return sum;
	}

	/*
	 * Functie ce rezolva task-ul "2".
	 */
	private static long solveTask2(int[] a, int[] b, int moves) {
		// Lungimea celor 2 siruri initiale.
		int n = a.length;

		// Vectorii in care stocam minimele si maximele.
		int[] maxSet = new int[n];
		int[] minSet = new int[n];

		// Comparam numerele de pe pozitia curenta din fiecare sir
		// si le punem in set-ul corespunzator.
		for (int i = 0; i < n; i++) {
			if (a[i] < b[i]) {
				minSet[i] = a[i];
				maxSet[i] = b[i];
			} else {
				minSet[i] = b[i];
				maxSet[i] = a[i];
			}
		}

		// Sortam cei 2 vectori pentru a putea extrage maximele ulterior.
		Arrays.sort(minSet);
		Arrays.sort(maxSet);

		// Conditiile de oprire, respectiv de interschimbare.
		for (int i = 0; i < moves; i++) {

			// Conditia de oprire.
			if (maxSet[i] >= minSet[n - i - 1]) {
				break;
			}

			// Nu ne interseaza sa mai mutam si in minime elementele din maxime.
			maxSet[i] = minSet[n - i - 1];
		}

		// Variabila in care stocam rezultatul.
		long sum = 0;

		// Adunam in rezultat maximele astfel obtinute.
		for (int i = 0; i < maxSet.length; i++) {
			sum += (long)maxSet[i];
		}

		// Rezultatul.
		return sum;
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
