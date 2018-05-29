package hackerrank;

import java.util.Scanner;

public class MakingAnagrams {
	public static int numberNeeded(String first, String second) {
		char[] a1 = first.toCharArray();
		for (int i = 0; i < a1.length; i++) {
			if (second.contains(a1[i] + "")) {
				first = first.replaceFirst(String.valueOf(a1[i]), "");
				second = second.replaceFirst(String.valueOf(a1[i]), "");
			}
		}
		return first.length() + second.length();
	}

	public static void deleteCharFromString() {
		String a = "Stack Overflow";
		char[] b = a.toCharArray();
		int j = 0; // index position of the character that you want to delete
		for (int i = j; i < b.length - 1; i++) {
			b[i] = b[i + 1];
		}
		b[b.length - 1] = '\000'; // '\000' is the null character

		System.out.println(new String(b));

	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String a = in.next();
		String b = in.next();
		System.out.println(numberNeeded(a, b));
	}

}
