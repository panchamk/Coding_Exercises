package codechef;

import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;

public class ShubhamLovesMath_ICL1706 {

	public static void main(String[] args) {
		InputReader in = new InputReader(System.in);
		long n = in.nextLong();
		long k = in.nextLong();
		long sum = 0;
		for (int i = 1; i <= n; i++) {
			sum += strength((long) (i * (java.lang.Math.pow(n, k))));
		}
		System.out.println(sum);
	}

	public static long strength(long N) {
		long value = 0, temp = N;
		long lp;
		/*
		 * Array lp stores the lowest prime divisor i.e. lp[x] stores lowest
		 * prime divisor of x
		 */
		while (temp != 1) {
			lp = smallestPrimeFactor(temp);
			value += N / lp;
			temp /= lp;
		}
		return value;
	}

	private static final long smallestPrimeFactor(long n) {
		if (n < 1) {
			return 0;
		}
		if (n % 2 == 0) {
			return 2;
		}
		// only need aproximate end-point... even if n is larger than 48 bits
		// (the largest accurate integer number in double),
		// the sqrt will be only off by 1 at most.
		long root = (long) (Math.sqrt(n)) + 1;
		for (long i = 3; i <= root; i += 2) {
			if (n % i == 0) {
				return i;
			}
		}
		// it's prime!
		return n;
	}

}

class InputReader {

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
