import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RadixSortTester {
	
	@Test
	public void hi() {
		int[] array = new int[] {701, 1394, 660, 1895, 429, 1032, 897, 1046, 1855,
				1148, 30, 333, 1309, 118, 259, 712, 1187, 1863, 739, 893};
		RadixSort sorter = new RadixSort();
		String output = sorter.sort(array);
		assertEquals("30 118 259 333 429 660 701 712 739 893 897 1032 1046 1148 1187 1309 1394 1855 1863 1895 ", output, "Error!");
	}
}
