package hackerrank;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RansomNoteProblem {
	Map<String, Integer> magazineMap;
	Map<String, Integer> noteMap;

	public RansomNoteProblem(String magazineString, String noteString) {
		if (magazineString != null && noteString != null) {
			if (magazineMap == null)
				magazineMap = new HashMap<String, Integer>();
			if (noteMap == null)
				noteMap = new HashMap<String, Integer>();
			String[] array = magazineString.split("\\s+");
			for (String magazine : array) {
				if (magazineMap.containsKey(magazine))
					magazineMap.put(magazine, magazineMap.get(magazine) + 1);
				else
					magazineMap.put(magazine, 1);
			}
			array = noteString.split("\\s+");
			for (String note : array) {
				if (noteMap.containsKey(note))
					noteMap.put(note, noteMap.get(note) + 1);
				else
					noteMap.put(note, 1);
			}
		}
	}

	public boolean solve() {
		boolean flag = true;
		for (String note : noteMap.keySet()) {
			if (!magazineMap.containsKey(note)) {
				flag = false;
				break;
			} else if (magazineMap.get(note) != null && noteMap.get(note) != null
					&& (Integer) magazineMap.get(note) != (Integer) noteMap.get(note)) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int m = scanner.nextInt();
		int n = scanner.nextInt();

		// Eat whitespace to beginning of next line
		scanner.nextLine();

		RansomNoteProblem s = new RansomNoteProblem(scanner.nextLine(), scanner.nextLine());
		scanner.close();

		boolean answer = s.solve();
		if (answer)
			System.out.println("Yes");
		else
			System.out.println("No");

	}
}
