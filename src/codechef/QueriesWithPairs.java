package codechef;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

class QueriesWithPairs {

	static int n, q;
	static Pair pA[], pB[];
	static Pair Q[];
	static int ans[];
	static int revA[], revB[];
	static int arr[];
	static int segtree[];

	public static void main(String args[]) {
		InputReader in = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);

		n = in.nextInt();
		q = in.nextInt();

		pA = new Pair[n];
		pB = new Pair[n];

		for (int i = 0; i < n; i++) {
			pA[i] = new Pair(in.nextInt(), -1, i);
			pB[i] = new Pair(pA[i].a, -1, i);
		}

		for (int i = 0; i < n; i++)
			pA[i].b = pB[i].b = in.nextInt();

		Arrays.sort(pA, new PairA());
		Arrays.sort(pB, new PairB());

		revA = new int[n];
		revB = new int[n];

		for (int i = 0; i < n; i++)
			revA[pA[i].idx] = i;

		for (int i = 0; i < n; i++)
			revB[pB[i].idx] = i;

		Q = new Pair[q];
		for (int i = 0; i < q; i++)
			Q[i] = new Pair(in.nextInt(), in.nextInt(), i);

		Arrays.sort(Q, new PairA());
		ans = new int[q];

		segtree = new int[4 * n];

		arr = new int[n];
		Arrays.fill(arr, Integer.MAX_VALUE - 10);

		build(0, n - 1, 0);

		int wow = n - 1;

		for (int i = q - 1; i >= 0; i--) {
			while (wow >= 0 && pA[wow].a >= Q[i].a) {
				int idx = pA[wow].idx;
				int proIdx = revB[idx];
				arr[proIdx] = pA[wow].a + pA[wow].b;
				update(0, n - 1, 0, proIdx);
				wow--;
			}
			int start = 0, end = n;
			int pro = -1;
			while (start < end) {
				int mid = (start + end) >> 1;
				if (pB[mid].b >= Q[i].b) {
					pro = mid;
					end = mid;
				} else
					start = mid + 1;
			}

			if (pro == -1)
				ans[Q[i].idx] = -1;
			else {
				int get = find(0, n - 1, 0, pro, n - 1);

				if (pB[get].a >= Q[i].a && pB[get].b >= Q[i].b)
					ans[Q[i].idx] = pB[get].idx + 1;
				else
					ans[Q[i].idx] = -1;
			}
		}

		for (int i = 0; i < q; i++)
			w.println(ans[i]);

		w.close();
	}

	static void build(int s, int e, int c) {
		if (s == e) {
			segtree[c] = s;
			return;
		}
		int m = (s + e) >> 1;
		build(s, m, 2 * c + 1);
		build(m + 1, e, 2 * c + 2);
		segtree[c] = merge(segtree[2 * c + 1], segtree[2 * c + 2]);
	}

	static void update(int s, int e, int c, int x) {
		if (s == e)
			return;
		int m = (s + e) >> 1;
		if (x <= m)
			update(s, m, 2 * c + 1, x);
		else
			update(m + 1, e, 2 * c + 2, x);
		segtree[c] = merge(segtree[2 * c + 1], segtree[2 * c + 2]);
	}

	static int find(int s, int e, int c, int l, int r) {
		if (s == l && e == r)
			return segtree[c];
		int m = (s + e) >> 1;
		if (r <= m)
			return find(s, m, 2 * c + 1, l, r);
		if (l > m)
			return find(m + 1, e, 2 * c + 2, l, r);
		return merge(find(s, m, 2 * c + 1, l, m), find(m + 1, e, 2 * c + 2, m + 1, r));
	}

	static int merge(int x, int y) {
		if (arr[x] < arr[y])
			return x;
		if (arr[y] < arr[x])
			return y;
		if (pB[x].idx < pB[y].idx)
			return x;
		return y;
	}

	static class Pair {
		int a, b, idx;

		Pair(int a, int b, int idx) {
			this.a = a;
			this.b = b;
			this.idx = idx;
		}
	}

	static class PairA implements Comparator<Pair> {
		public int compare(Pair a, Pair b) {
			if (a.a != b.a)
				return Integer.compare(a.a, b.a);
			return Integer.compare(a.b, b.b);
		}
	}

	static class PairB implements Comparator<Pair> {
		public int compare(Pair a, Pair b) {
			if (a.b != b.b)
				return Integer.compare(a.b, b.b);
			return Integer.compare(a.a, b.a);
		}
	}

	static class InputReader {

		private InputStream stream;
		private byte[] buf = new byte[8192];
		private int curChar;
		private int snumChars;

		public InputReader(InputStream stream) {
			this.stream = stream;
		}

		public int snext() {
			if (snumChars == -1)
				throw new InputMismatchException();
			if (curChar >= snumChars) {
				curChar = 0;
				try {
					snumChars = stream.read(buf);
				} catch (IOException e) {
					throw new InputMismatchException();
				}
				if (snumChars <= 0)
					return -1;
			}
			return buf[curChar++];
		}

		public int nextInt() {
			int c = snext();
			while (isSpaceChar(c))
				c = snext();
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = snext();
			}

			int res = 0;

			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = snext();
			} while (!isSpaceChar(c));

			return res * sgn;
		}

		public long nextLong() {
			int c = snext();
			while (isSpaceChar(c))
				c = snext();
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = snext();
			}

			long res = 0;

			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = snext();
			} while (!isSpaceChar(c));

			return res * sgn;
		}

		public String readString() {
			int c = snext();
			while (isSpaceChar(c))
				c = snext();
			StringBuilder res = new StringBuilder();
			do {
				res.appendCodePoint(c);
				c = snext();
			} while (!isSpaceChar(c));
			return res.toString();
		}

		public boolean isSpaceChar(int c) {
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

	}
}
