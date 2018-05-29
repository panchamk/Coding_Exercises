package amazon;

/**
 * Given an array and an operation -> foo(index, value), the value can be either
 * 1 or -1, if foo(index, value) is called, it will add ‘value’ to all elements
 * from index till end of the array,
 * 
 * find minimum number of operation to make all array elements 0.
 */
public class FindMinOpToZeroArray {

	public static void main(String[] args) {
		int arr[] = new int[] { 1, 2, 0, 5, 3 };
		int r1 = 0, r2 = 0;
		int tmp = 0;
		int i = 0;
		if (arr[i] > 0)
			r1 = arr[i];
		else
			r2 = arr[i];
		int n = arr.length;
		for (i = 1; i < n; i++) {
			tmp = arr[i] - (r1 - r2);
			System.out.println("TEMP : " + tmp);
			System.out.println("Previous : " + r1 + " " + r2);
			if (tmp > 0)
				r1 += Math.abs(tmp);
			else
				r2 += Math.abs(tmp);
			System.out.println("After : " + r1 + " " + r2);
		}
		System.out.println(r1 + r2);
	}
}
