
public class InsertionSort {

	// ���ǵ��㷨�಻��������κ�ʵ��
	private InsertionSort() {
	}

	public static void insert_sort(Integer[] arr) {
		int n = arr.length;
		for (int i = 0; i < n; i++) {

		}
	}

	public static void sort(Integer[] arr) {

		int n = arr.length;
		for (int i = 0; i < n; i++) {

			// Ѱ��Ԫ��arr[i]���ʵĲ���λ��

			// д��1
			for (int j = i; j > 0; j--)
				if (arr[j].compareTo(arr[j - 1]) < 0)
					swap(arr, j, j - 1);
				else
					break;

			// д��2
			for (int j = i; j > 0 && arr[j].compareTo(arr[j - 1]) < 0; j--)
				swap(arr, j, j - 1);

			// д��3
			int e = arr[i];
			int j = i;
			for (; j > 0 && arr[j - 1].compareTo(e) > 0; j--)
				arr[j] = arr[j - 1];
			arr[j] = e;

		}
	}

	private static void swap(Object[] arr, int i, int j) {
		Object t = arr[i];
		arr[i] = arr[j];
		arr[j] = t;
	}

	// ����InsertionSort
	public static void main(String[] args) {

		int N = 20000;
		Integer[] arr = SortTestHelper.generateRandomArray(N, 0, 100000);
		SortTestHelper.testSort("bobo.algo.InsertionSort", arr);

		return;
	}
}