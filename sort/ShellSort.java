
public class ShellSort {

	public static void sort(int[] arr) {

		int n = arr.length;
		int j;
		for (int gap = arr.length / 2; gap > 0; gap /= 2) {
			for (int i = gap; i < arr.length; i++) {

				for (j = i; j > gap && arr[i] > arr[j - gap]; j -= gap) {
					swap(arr, i, j - gap);
				}
			}
		}

	}

	private static void swap(int[] arr, int i, int j) {
		int t = arr[i];
		arr[i] = arr[j];
		arr[j] = t;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
