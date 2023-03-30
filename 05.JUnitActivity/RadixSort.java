
public class RadixSort {
	public String sort(int[] in) {
		String output = "";
		int num = largestNum(in);
		int count = 0;
		while (num > 0) {
			num /= 10;
			count++;
		}
		for (int i = 0; i < count; ++i)
			countingSort(in, i);
		for (int i: in)
			output += i + " ";
		return output;
	}
	
	public void countingSort(int[] array, int lsd) {
		int[] arrayDigit = new int[10];
		for (int i: array) {
			int digit =  ((i / (int)Math.pow(10, lsd)) % 10);
			arrayDigit[digit]++;
		}
		
		for (int i = 1; i < 10; ++i)
			arrayDigit[i] += arrayDigit[i-1];
		
		int[] output = new int[array.length];
		for (int i = array.length -1 ; i >= 0; --i) {
			int digit =  ((array[i] / (int)Math.pow(10, lsd)) % 10);
			output[arrayDigit[digit] - 1] = array[i];
			arrayDigit[digit]--;
		}
		
		for (int i = 0; i < array.length; ++i)
			array[i] = output[i];
	}
	
	public int largestNum(int[] array) {
		int output = array[0];
		for (int i = 1; i < array.length; ++i)
			if (array[i] > output)
				output = array[i];
		return output;
	}
}
