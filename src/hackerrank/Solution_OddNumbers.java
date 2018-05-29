package hackerrank;

import java.io.IOException;
import java.util.Scanner;

public class Solution_OddNumbers {
	/*
	 * Complete the function below.
	 */

	static int[] oddNumbers(int l, int r) {
		int[] arr;
		if ((r - l) % 2 == 0)
			arr = new int[(r - l) / 2];
		else
			arr = new int[(r - l) / 2 + 1];
		if (l % 2 == 0)
			arr[0] = l + 1;
		else
			arr[0] = l;
		// arr[1] = arr[0] + 2;
		int i = 1;
		while (/* arr[i - 1] < r */ i < arr.length) {
			arr[i] = arr[i - 1] + 2;
			i++;

		}
		return arr;
	}

	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
		final String fileName = System.getenv("OUTPUT_PATH");
		// BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
		int[] res;
		int _l;
		_l = Integer.parseInt(in.nextLine().trim());

		int _r;
		_r = Integer.parseInt(in.nextLine().trim());

		res = oddNumbers(_l, _r);
		for (int res_i = 0; res_i < res.length; res_i++) {
			// bw.write(String.valueOf(res[res_i]));
			// bw.newLine();
			System.out.println(res[res_i]);
		}

		// bw.close();
	}
}