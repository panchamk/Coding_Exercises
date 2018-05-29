package hackerrank;

import java.util.Scanner;

public class RemoveDuplicates {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = 10;// scan.nextInt();
		String[] inputArray = new String[] { "a", "b", "c", "c", "c", "b", "b", "d", "f", "g" };
		// for (int i = 0; i < n; i++)
		// inputArray[i] = scan.nextLine();
		int burstLength = 3;// scan.nextInt();
		inputArray = getShrunkArray(inputArray, burstLength);
		for (String string : inputArray) {
			System.out.println(string);
		}
	}

	/*
	 * Complete the function below.
	 */
	static String[] getShrunkArray(String[] inputArray, int burstLength) {
		// String tempStr = inputArray[0];
		// String[] returnArray = inputArray;
		/*
		 * for(String s : inputArray) System.out.print(s);
		 * System.out.println("");
		 */
		String tempStr = null;
		// List<String> tempList = null;
		int count = 0;
		int start = 0;
		int medStart = 0;
		while (start < inputArray.length - burstLength - 1) {
			count = 0;
			tempStr = inputArray[start];
			medStart = start;
			while (medStart < inputArray.length) {
				if (tempStr.equalsIgnoreCase(inputArray[medStart]))
					count++;
				else
					break;
				medStart++;
			}
			if (count >= burstLength) {
				medStart = start;
				/*
				 * tempList = new ArrayList<String>(Arrays.asList(inputArray));
				 * while(medStart < tempList.size()) {
				 * //System.out.println(tempList.get(medStart));
				 * tempList.remove(medStart); medStart++; } if(tempList != null)
				 * inputArray = tempList.toArray(inputArray);
				 */
				String[] tempArray = new String[inputArray.length - count];
				int i = 0;
				for (; i < medStart; i++)
					tempArray[i] = inputArray[i];
				medStart = medStart + count;
				for (; i < tempArray.length; i++) {
					tempArray[i] = inputArray[medStart];
					medStart++;
				}

				inputArray = tempArray.clone();
				start = -1;
				/*
				 * for(String s : inputArray) System.out.print(s);
				 * System.out.println("");
				 */
				/*
				 * for(String s : tempList) System.out.print(s);
				 */
			}
			start++;
		}
		return inputArray;
	}

}
