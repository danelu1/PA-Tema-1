import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.StringTokenizer;

public class Feribot {
	public static void main(final String[] args) throws IOException {
		var scanner = new MyScanner(new FileReader("feribot.in"));
		
		// numarul de masini
		var n = scanner.nextInt();

		// Numarul de partitionari permise
		var k = scanner.nextInt();

		// Vectorul cu greutatile masinilor
		var a = new long[n];
		for (var i = 0; i < n; i += 1) {
			a[i] = scanner.nextLong();
		}

		try (var printer = new PrintStream("feribot.out")) {
			printer.println(solveTask(n, k, a));
		}
	}

	/*
	 * Functie auxiliara ce verifica daca putem impartii sirul nostru
	 * de numere in maxim "k" subsecvente, astfel incat suma maxima a fiecarei
	 * subsecvente sa fie cel mult numarul "maxSubArray" primit ca
	 * parametru.
	 */
	private static boolean check(long maxSubarraySum, long[] arr, int k) {
		if (arr[0] > maxSubarraySum) {
			return false;
		} 

		int cnt = 1;
		long currentSum = arr[0];

		for (int i = 1; i < arr.length; i++) {
			if (arr[i] > maxSubarraySum) {
				return false;
			}

			if (currentSum + arr[i] > maxSubarraySum) {
				currentSum = arr[i];
				cnt++;
			} else {
				currentSum += arr[i];
			}
		}

		return cnt <= k;
	}

	/*
	 * Functie care intoarce maximul unui sir de numere.
	 */
	private static long max(long[] arr) {
		long max = arr[0];
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > max) {
				max = arr[i];
			}
		}
		return max;
	}

	/*
	 * Functie care rezolva problema prin metoda cautarii binare.
	 */
	private static long solveTask(int n, int k, long[] arr) {
		// Capatul din stanga al intervalului asupra caruia facem prelucrarile
		long left = max(arr);
		// Capatul din dreapta
		long right = 0;

		for (int i = 0; i < n; i++) {
			right += arr[i];
		}

		// Variabila in care retinem rezultatul.
		long ans = 0;

		// Cautarea binara propriu-zisa.
		while (left <= right) {
			long mid = left + (right - left) / 2;
			
			// Verificarea conditiei descrisa.
			if (check(mid, arr, k)) {
				ans = mid;
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}
		
		// Rezultatul.
		return ans;
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