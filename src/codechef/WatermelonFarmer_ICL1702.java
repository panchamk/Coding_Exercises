package codechef;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class WatermelonFarmer_ICL1702 {
	static long[][] wfm = null;
	static long[][] ifm = null;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int t = Integer.parseInt(br.readLine());
		int m, n, i, j, k, l;
		long w, p, x, y;
		while (t > 0) {
			String input = br.readLine();
			StringTokenizer strToken = new StringTokenizer(input);
			int count = strToken.countTokens();
			if (count > 6)
				return;
			m = Integer.parseInt((String) strToken.nextElement());
			n = Integer.parseInt((String) strToken.nextElement());
			w = Long.parseLong((String) strToken.nextElement());
			p = Long.parseLong((String) strToken.nextElement());
			x = Long.parseLong((String) strToken.nextElement());
			y = Long.parseLong((String) strToken.nextElement());
			wfm = new long[m][n];
			ifm = new long[m][n];

			ifm[0][0] = x;
			ifm[m - 1][n - 1] = y;
			wfm[0][0] = x;
			wfm[m - 1][n - 1] = y;
			for (int weeks = 1; weeks <= w; weeks++) {
				for (i = 0; i < m; i++) {
					for (j = 0; j < n; j++) {
						if (ifm[i][j] > 0) {
							if (i - 1 >= 0 && j - 1 >= 0 && i - 1 < m && j - 1 < n)
								wfm[i - 1][j - 1] += ifm[i][j];
							if (i - 1 >= 0 && j >= 0 && i - 1 < m && j < n)
								wfm[i - 1][j] += ifm[i][j];
							if (i - 1 >= 0 && j + 1 >= 0 && i - 1 < m && j + 1 < n)
								wfm[i - 1][j + 1] += ifm[i][j];
							if (i >= 0 && j - 1 >= 0 && i < m && j - 1 < n)
								wfm[i][j - 1] += ifm[i][j];
							if (i >= 0 && j + 1 >= 0 && i < m && j + 1 < n)
								wfm[i][j + 1] += ifm[i][j];
							if (i + 1 >= 0 && j - 1 >= 0 && i + 1 < m && j - 1 < n)
								wfm[i + 1][j - 1] += ifm[i][j];
							if (i + 1 >= 0 && j >= 0 && i + 1 < m && j < n)
								wfm[i + 1][j] += ifm[i][j];
							if (i + 1 >= 0 && j + 1 >= 0 && i + 1 < m && j + 1 < n)
								wfm[i + 1][j + 1] += ifm[i][j];

						}
					}
				}
				for (i = 0; i < m; i++) {
					for (j = 0; j < n; j++) {
						wfm[i][j] = wfm[i][j] % 20;
						ifm[i][j] = wfm[i][j];
					}
				}
			}
			for (i = 0; i < m; i++) {
				for (j = 0; j < n; j++)
					System.out.print(wfm[i][j] + "\t");
				System.out.println();
			}
			t--;
		}
	}

}
