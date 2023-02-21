// --== CS400 Project One File Header ==--
// Name: Nico Salm
// CSL Username: salm
// Email: nbsalm@wisc.edu
// Lecture #: 01 @ 09:30
// Notes to Grader: sometimes I wonder if I'm actually a dragon. I mean, I'm not, but sometimes I wonder. You know?

import java.util.NoSuchElementException;

/**
 * Tester class for the HashtableMap class.
 */
public class HashtableMapTests {

    /**
     * Tests the put method.
     * 
     * @return true if the test passed, false otherwise
     */
    public static boolean test1() {
        HashtableMap<Integer, String> map = new HashtableMap<>();

        // Add some elements to the map
        map.put(11, "a");
        map.put(12, "b");
        map.put(23, "c");

        // Check that the elements were added correctly
        if (map.size != 3)
            return false;

        if (!map.containsKey(11) || !map.containsKey(12) || !map.containsKey(23))
            return false;

        if (!map.get(11).equals("a") || !map.get(12).equals("b") || !map.get(23).equals("c"))
            return false;

        // Try to add a duplicate key
        try {
            map.put(11, "d");
            return false;
        } catch (IllegalArgumentException e) {
        }

        // Try to add a null key
        try {
            map.put(null, "d");
            return false;
        } catch (IllegalArgumentException e) {
        }

        return true; // if we got here, the test passed
    }

    /**
     * Tests the getSize method.
     * 
     * @return true if the test passed, false otherwise
     */
    private static boolean test2() {
        HashtableMap<Integer, String> map = new HashtableMap<>();

        if (map.size != 0) // make sure the size is 0 when the map is empty
            return false;

        // Add some elements to the map and make sure size tracks correctly
        map.put(11, "a");

        if (map.size != 1)
            return false;

        map.put(12, "b");
        map.put(23, "c");

        if (map.size != 3)
            return false;

        map.remove(11);

        if (map.size != 2)
            return false;

        return true; // if we got here, the test passed
    }

    /**
     * Tests the getCapacity method.
     * 
     * @return true if the test passed, false otherwise
     */
    private static boolean test3() {
        { // Default constructor
            HashtableMap<Integer, String> map = new HashtableMap<>();

            if (map.capacity != 8) // make sure the capacity is 8 when the map is empty
                return false;
            map.put(11, "a"); // add some elements to the map

            if (map.capacity != 8) // capacity should not change when the load factor < 0.7
                return false;

            map.put(12, "b");
            map.put(23, "c");

            if (map.capacity != 8)
                return false;

            map.put(34, "d"); // capacity should double to 16 when the load factor becomes 0.8
            map.put(45, "e");
            map.put(56, "f");
            map.put(67, "g");
            map.put(78, "h");

            if (map.capacity != 16)
                return false;
        }
        { // Constructor where capacity is 5
            HashtableMap<Integer, String> map = new HashtableMap<>(5);

            if (map.capacity != 5) // make sure the capacity is 5 when the map is empty
                return false;

            map.put(11, "a"); // add some elements to the map

            if (map.capacity != 5) // capacity should not change when the load factor < 0.7
                return false;

            map.put(12, "b");

            if (map.capacity != 5)
                return false;

            map.put(23, "c");

            if (map.capacity != 5)
                return false;

            map.put(34, "d");

            if (map.capacity != 10) // capacity should double to 10 when the load factor becomes 0.8
                return false;
        }

        return true; // if we got here, the test passed
    }

    /**
     * Tests the rehash method.
     * 
     * @return true if the test passed, false otherwise
     */
    private static boolean test4() {
        HashtableMap<Integer, String> map = new HashtableMap<>();

        // Add some elements to the map
        map.put(11, "a");
        map.put(12, "b");
        map.put(23, "c");
        map.put(34, "d");
        map.put(45, "e");
        map.put(56, "f");
        map.put(67, "g");
        map.put(78, "h"); // capacity should double to 16 when the load factor becomes 0.8

        if (map.capacity != 16) // make sure the capacity is 16
            return false;

        map.put(89, "i");
        map.put(90, "j");
        map.put(91, "k");
        map.put(92, "l"); // capacity should double to 32 when the load factor becomes 0.8

        if (map.capacity != 32) // make sure the capacity is 32
            return false;

        return true;
    }

    /**
     * Tests the clear method.
     * 
     * @return true if the test passed, false otherwise
     */
    private static boolean test5() {
        HashtableMap<Integer, String> map = new HashtableMap<>();

        map.put(11, "a"); // Add some elements to the map
        map.put(12, "b");
        map.put(23, "c");

        if (map.size != 3)
            return false;

        map.clear(); // Clear the map and compare the size to 0

        if (map.size != 0)
            return false;

        return true; // if we got here, the test passed
    }

    /**
     * Test the remove method.
     * 
     * @return true if the test passed, false otherwise
     */
    private static boolean test6() {
        HashtableMap<Integer, String> map = new HashtableMap<>();
        // Test removing from an empty map
        try {
            map.remove(11);
            return false;
        } catch (NoSuchElementException e) {
        }

        // Add some elements to the map
        map.put(11, "a");
        map.put(12, "b");
        map.put(23, "c");
        map.put(34, "d");
        map.put(45, "e");
        map.put(46, "f");
        map.put(47, "g");

        /*
         * Go through each element and systematically remove it. Check if size is
         * correctly adjusted and if the correct elements are still in the map. Check
         * the get method for correctness against removed values and values adjacent to
         * removed values. Ensures all functionality is working correctly.
         */
        if (map.size != 7)
            return false;

        map.remove(11);

        if (map.size != 6)
            return false;

        if (!map.containsKey(12) || !map.containsKey(23) || !map.containsKey(34)
                || !map.containsKey(45) || !map.containsKey(46) || !map.containsKey(47))
            return false;

        if (!map.get(12).equals("b") || !map.get(23).equals("c") || !map.get(34).equals("d")
                || !map.get(45).equals("e") || !map.get(46).equals("f") || !map.get(47).equals("g"))
            return false;

        map.remove(34);

        if (map.size != 5)
            return false;

        if (!map.containsKey(12) || !map.containsKey(23) || !map.containsKey(45)
                || !map.containsKey(46) || !map.containsKey(47))
            return false;

        if (!map.get(12).equals("b") || !map.get(23).equals("c") || !map.get(45).equals("e")
                || !map.get(46).equals("f") || !map.get(47).equals("g"))
            return false;

        map.remove(45);

        if (map.size != 4)
            return false;

        if (!map.containsKey(12) || !map.containsKey(23) || !map.containsKey(46)
                || !map.containsKey(47))
            return false;

        if (!map.get(12).equals("b") || !map.get(23).equals("c") || !map.get(46).equals("f")
                || !map.get(47).equals("g"))
            return false;

        map.remove(46);

        if (map.size != 3)
            return false;

        if (!map.containsKey(12) || !map.containsKey(23) || !map.containsKey(47))
            return false;

        if (!map.get(12).equals("b") || !map.get(23).equals("c") || !map.get(47).equals("g"))
            return false;

        map.remove(47);

        if (map.size != 2)
            return false;

        if (!map.containsKey(12) || !map.containsKey(23))
            return false;

        return true;
    }

    /**
     * Tests the get method.
     * 
     * @return true if the test passed, false otherwise
     */
    private static boolean test7() {
        HashtableMap<Integer, String> map = new HashtableMap<>();

        // Test getting from an empty map
        try {
            map.get(11);
            return false;
        } catch (NoSuchElementException e) {
        }

        // Add some elements to the map
        map.put(11, "a");
        map.put(12, "b");
        map.put(23, "c");
        map.put(34, "d");
        map.put(45, "e");
        map.put(46, "f");
        map.put(47, "g");

        /**
         * Go through each element and check if the correct value is returned. Remove
         * elements and check if the correct values are returned. Check the get method
         * for correctness and ensure all functionality is working correctly.
         */

        if (!map.get(11).equals("a") || !map.get(12).equals("b") || !map.get(23).equals("c")
                || !map.get(34).equals("d") || !map.get(45).equals("e") || !map.get(46).equals("f")
                || !map.get(47).equals("g"))
            return false;

        map.remove(11);

        if (!map.get(12).equals("b") || !map.get(23).equals("c") || !map.get(34).equals("d")
                || !map.get(45).equals("e") || !map.get(46).equals("f") || !map.get(47).equals("g"))
            return false;

        map.remove(34);

        if (!map.get(12).equals("b") || !map.get(23).equals("c") || !map.get(45).equals("e")
                || !map.get(46).equals("f") || !map.get(47).equals("g"))
            return false;

        map.remove(45);

        if (!map.get(12).equals("b") || !map.get(23).equals("c") || !map.get(46).equals("f")
                || !map.get(47).equals("g"))
            return false;

        map.remove(46);

        if (!map.get(12).equals("b") || !map.get(23).equals("c") || !map.get(47).equals("g"))
            return false;

        map.remove(47);

        if (!map.get(12).equals("b") || !map.get(23).equals("c"))
            return false;

        return true; // All tests passed
    }

    /**
     * Tests the containsKey method.
     * 
     * @return true if the test passed, false otherwise
     */
    private static boolean test8() {
        HashtableMap<Integer, String> map = new HashtableMap<>();

        // Test containsKey on an empty map
        if (map.containsKey(11))
            return false;

        // Add some elements to the map
        map.put(11, "a");
        map.put(12, "b");
        map.put(23, "c");

        // Test containsKey on a non-empty map
        if (!map.containsKey(11) || !map.containsKey(12) || !map.containsKey(23))
            return false;

        if (map.containsKey(13))
            return false;

        if (map.containsKey(null)) // null is not a valid key
            return false;

        return true; // All tests passed
    }

    /**
     * Runs all the tests.
     * 
     * @param args unused
     */
    public static void main(String[] args) {
        System.out.println("testPut(): " + test1());
        System.out.println("testSize(): " + test2());
        System.out.println("testGetCapacity(): " + test3());
        System.out.println("testRehash():" + test4());
        System.out.println("testClear(): " + test5());
        System.out.println("testRemove(): " + test6());
        System.out.println("testGet(): " + test7());
        System.out.println("testContainsKey(): " + test8());

    }
}