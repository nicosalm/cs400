import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * This class tests the functionality of the CHSearchFrontEnd class. It
 * hardcodes the functionality of the CHSearchBackend class so that it can be
 * tested.
 */
public class FrontendDeveloper_Tests {

    /**
     * Tests that the menu is
     * displayed correctly, that the user can quit, and that invalid inputs are
     * handled correctly.
     * 
     * @return true if the test passed, false otherwise
     */
    public static boolean test1() {
        // 1.1 base menu and command loop test. Expect the menu to be printed out and
        // user inputs to be accepted.
        {
            // create a tester that will simulate user input
            TextUITester tester = new TextUITester("q");
            // create a backend and frontend
            CHSearchBackendFD backend = new CHSearchBackendFD();
            CHSearchFrontendFD frontend = new CHSearchFrontendFD(new Scanner(System.in), backend);

            // run the command loop
            frontend.runCommandLoop();

            // check that the menu was printed out correctly
            String expected = "Main Menu:" + System.lineSeparator() + "l) Load data from file" + System.lineSeparator()
                    + "t) Search for posts by title" + System.lineSeparator() + "b) Search for posts by body"
                    + System.lineSeparator()
                    + "p) Search for posts by title or body" + System.lineSeparator() + "s) Display statistics"
                    + System.lineSeparator() + "q) Quit" + System.lineSeparator() + "Enter a command:";

            // if it wasn't printed out correctly, fail the test
            if (!tester.checkOutput().contains(expected)) {
                System.out.println("Failed test 1.1");
                return false;
            }
        }
        // 1.2 test incorrect inputs. Expect "Invalid command" to be printed out.
        {
            TextUITester tester = new TextUITester(" " + System.lineSeparator() + "k" + System.lineSeparator() + "q");
            CHSearchBackendFD backend = new CHSearchBackendFD();
            CHSearchFrontendFD frontend = new CHSearchFrontendFD(new Scanner(System.in), backend);
            frontend.runCommandLoop();
            String expected = "Invalid command";
            if (!tester.checkOutput().contains(expected)) {
                System.out.println("Failed test 1.1");
                return false;
            }
        }
        return true; // if we get here, all tests passed
    }

    /**
     * Tests loadDataCommand and stats commands
     * 
     * @return true if the test passed, false otherwise
     */
    public static boolean test2() {
        // 2.1.1 test loadDataCommand.
        {
            TextUITester tester = new TextUITester(
                    "l" + System.lineSeparator() + "posts.txt" + System.lineSeparator() + "q");
            CHSearchBackendFD backend = new CHSearchBackendFD();
            CHSearchFrontendFD frontend = new CHSearchFrontendFD(new Scanner(System.in), backend);
            frontend.runCommandLoop();
            String expected = "File loaded!";
            if (!tester.checkOutput().contains(expected)) {
                System.out.println("Failed test 4.1");
                return false;
            }
        }
        // 2.1.2 test loadDataCommand with invalid file name.
        {
            TextUITester tester = new TextUITester(
                    "l" + System.lineSeparator() + "test2.txt" + System.lineSeparator() + "q");
            CHSearchBackendFD backend = new CHSearchBackendFD();
            CHSearchFrontendFD frontend = new CHSearchFrontendFD(new Scanner(System.in), backend);
            frontend.runCommandLoop();
            String expected = "File not found";
            if (!tester.checkOutput().contains(expected)) {
                System.out.println("Failed test 2.1.2");
                return false;
            }
        }
        // 2.2 gets stats
        {
            TextUITester tester = new TextUITester(
                    "s" + System.lineSeparator() + "q");
            CHSearchBackendFD backend = new CHSearchBackendFD();
            CHSearchFrontendFD frontend = new CHSearchFrontendFD(new Scanner(System.in), backend);
            frontend.runCommandLoop();
            String expected = "Statistics: 4 posts, 4 unique words";
            if (!tester.checkOutput().contains(expected)) {
                System.out.println("Failed test 2.2");
                return false;
            }
        }
        return true; // if we get here, all tests passed
    }

    /**
     * Test searching the for posts by Title content.
     * 
     * @return true if the test passed, false otherwise
     */
    public static boolean test3() {
        TextUITester tester = new TextUITester(
                "t" + System.lineSeparator() + "banana" + System.lineSeparator() + "q");
        CHSearchBackendFD backend = new CHSearchBackendFD();
        CHSearchFrontendFD frontend = new CHSearchFrontendFD(new Scanner(System.in), backend);
        frontend.runCommandLoop();
        String expected = "Retrieving posts with title words: \"[banana]\"\n\tBananas [title]\n\tBananas and Apples [title]";
        if (!tester.checkOutput().contains(expected)) {
            return false;
        }
        return true; // if we get here, all tests passed
    }

    /**
     * Test searching for posts by body content.
     * 
     * @return true if the test passed, false otherwise
     */
    public static boolean test4() {
        TextUITester tester = new TextUITester(
                "b" + System.lineSeparator() + "banana" + System.lineSeparator() + "q");
        CHSearchBackendFD backend = new CHSearchBackendFD();
        CHSearchFrontendFD frontend = new CHSearchFrontendFD(new Scanner(System.in), backend);
        frontend.runCommandLoop();
        String expected = "Bananas and apples are good for you [body]"; // because we verified the absolute string in
                                                                        // test 3, we can just check for a substring
                                                                        // here. Same code different methods.
        if (!tester.checkOutput().contains(expected)) {
            return false;
        }
        return true; // if we get here, all tests passed

    }

    /**
     * Test searching for posts by title or body content.
     * 
     * @return true if the test passed, false otherwise
     */
    public static boolean test5() {
        TextUITester tester = new TextUITester(
                "p" + System.lineSeparator() + "banana" + System.lineSeparator() + "q");
        CHSearchBackendFD backend = new CHSearchBackendFD();
        CHSearchFrontendFD frontend = new CHSearchFrontendFD(new Scanner(System.in), backend);
        frontend.runCommandLoop();
        String expected = "Bananas and apples are good for you [body]"; // validates presence of body content
        String expected2 = "Bananas [title]\n\tBananas and Apples [title]";
        if (!tester.checkOutput().contains(expected) && !tester.checkOutput().contains(expected2)) {
            return false;
        }
        return true; // if we get here, all tests passed
    }

    /**
     * Test the integration (1/2) of the frontend and backend. Tests loading data
     * from files and displaying the stats.
     * 
     * @return true if the test passed, false otherwise
     */
    private static boolean test6() {
        // Loading and displaying data from a file (large.txt)
        {
            TextUITester tester = new TextUITester(
                    "l" + System.lineSeparator() + "data/large.txt" + System.lineSeparator() + "s"
                            + System.lineSeparator()
                            + "q");
            CHSearchBackendBD backend = new CHSearchBackendBD(new HashtableWithDuplicateKeysAE<>(), new PostReaderDW());
            CHSearchFrontendFD frontend = new CHSearchFrontendFD(new Scanner(System.in), backend);
            frontend.runCommandLoop();
            if (!tester.checkOutput().contains("856 posts")) {
                return false;
            }
        }
        // Loading and displaying data from a second file (small.txt)
        {
            TextUITester tester = new TextUITester(
                    "l" + System.lineSeparator() + "data/small.txt" + System.lineSeparator() + "s"
                            + System.lineSeparator()
                            + "q");
            CHSearchBackendBD backend = new CHSearchBackendBD(new HashtableWithDuplicateKeysAE<>(), new PostReaderDW());
            CHSearchFrontendFD frontend = new CHSearchFrontendFD(new Scanner(System.in), backend);
            frontend.runCommandLoop();
            if (!tester.checkOutput().contains("12 posts")) {
                return false;
            }
        }
        // Loading a file that DNE
        {
            TextUITester tester = new TextUITester(
                    "l" + System.lineSeparator() + "data/doesnotexist.txt" + System.lineSeparator() + "s"
                            + System.lineSeparator()
                            + "q");
            CHSearchBackendBD backend = new CHSearchBackendBD(new HashtableWithDuplicateKeysAE<>(), new PostReaderDW());
            CHSearchFrontendFD frontend = new CHSearchFrontendFD(new Scanner(System.in), backend);
            frontend.runCommandLoop();
            if (!tester.checkOutput().contains("File not found")) {
                return false;
            }
        }
        return true;
    }

    /**
     * Test the integration (2/2) of the frontend and backend. Tests searching for
     * posts by title, body, or both.
     * 
     * @return true if the test passed, false otherwise
     */
    private static boolean test7() {
        // Search by title - keyword: Protein (appears in title)
        {
            TextUITester tester = new TextUITester(
                    "l" + System.lineSeparator() + "data/small.txt" + System.lineSeparator() + "t"
                            + System.lineSeparator() + "Protein" + System.lineSeparator() + "q");
            CHSearchBackendBD backend = new CHSearchBackendBD(new HashtableWithDuplicateKeysAE<>(), new PostReaderDW());
            CHSearchFrontendFD frontend = new CHSearchFrontendFD(new Scanner(System.in), backend);
            frontend.runCommandLoop();
            if (!tester.checkOutput().contains(
                    "protein")) {
                return false;
            }
        }
        // Search by body - keyword: mercury (appears in body)
        {
            TextUITester tester = new TextUITester(
                    "l" + System.lineSeparator() + "data/small.txt" + System.lineSeparator() + "b"
                            + System.lineSeparator() + "mercury" + System.lineSeparator() + "q");
            CHSearchBackendBD backend = new CHSearchBackendBD(new HashtableWithDuplicateKeysAE<>(), new PostReaderDW());
            CHSearchFrontendFD frontend = new CHSearchFrontendFD(new Scanner(System.in), backend);
            frontend.runCommandLoop();
            if (!tester.checkOutput().contains(
                    "https://www.reddit.com/r/EatCheapAndHealthy/comments/zp6q1y/protein_snacks/")) {
                return false;
            }

        }
        // Search title and body - keyword: protein (appears in either title or body)
        {
            TextUITester tester = new TextUITester(
                    "l" + System.lineSeparator() + "data/small.txt" + System.lineSeparator() + "p"
                            + System.lineSeparator() + "avocado" + System.lineSeparator() + "q");
            CHSearchBackendBD backend = new CHSearchBackendBD(new HashtableWithDuplicateKeysAE<>(), new PostReaderDW());
            CHSearchFrontendFD frontend = new CHSearchFrontendFD(new Scanner(System.in), backend);
            frontend.runCommandLoop();
            if (!tester.checkOutput().contains(
                    "https://www.reddit.com/r/EatCheapAndHealthy/comments/zp3qhf/what_is_a_good_high_protein_easy_to_make_plant/")) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests the implementation of CHSearchBackendBD constructor and it's default
     * values.
     * 
     * @return true if the test passed, false otherwise
     */
    public static boolean test8() {
        CHSearchBackendBD backend = new CHSearchBackendBD(new HashtableWithDuplicateKeysAE<>(), new PostReaderDW());

        // Check default values
        if (!backend.getStatisticsString().contains("0 posts")) {
            return false;
        }

        if (!backend.getStatisticsString().contains("0 unique words")) {
            return false;
        }

        return (!backend.getStatisticsString().contains("0 total word-post pairs")) ? false : true;
    }

    /**
     * Tests the implementation of backend information retrieval from file data.
     * 
     * @return true if the test passed, false otherwise
     */
    private static boolean test9() {
        CHSearchBackendBD backend = new CHSearchBackendBD(new HashtableWithDuplicateKeysAE<>(), new PostReaderDW());
        try {
            backend.loadData("data/small.txt");
        } catch (FileNotFoundException e) {
            return false;
        }

        // Searches body for keyword: mercury (appears in body)
        {
            List<String> results = backend.findPostsByBodyWords("mercury");
            if (results.size() != 1) {
                return false;
            }
            if (!results.get(0).contains(
                    "https://www.reddit.com/r/EatCheapAndHealthy/comments/zp6q1y/protein_snacks/")) {
                return false;
            }
        }

        // Searches title for keyword: protein (appears in title)
        {
            List<String> results = backend.findPostsByTitleWords("protein");
            if (results.size() != 1) {
                return false;
            }
            if (!results.get(0).contains(
                    "https://www.reddit.com/r/EatCheapAndHealthy/comments/zp3qhf/what_is_a_good_high_protein_easy_to_make_plant/")) {
                return false;
            }
        }

        // Searches title and body for keyword: mercury (appears in either title or
        // body)
        {
            List<String> results = backend.findPostsByTitleOrBodyWords("mercury");
            if (results.size() != 1) {
                return false;
            }
            if (!results.get(0).contains(
                    "https://www.reddit.com/r/EatCheapAndHealthy/comments/zp6q1y/protein_snacks/")) {
                return false;
            }
        }
        return true;
    }

    /**
     * Main method for testing, runs all tests and prints the results.
     * 
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("FrontendDeveloper Individual Test 1: " + (test1() ? "passed" : "failed"));
        System.out.println("FrontendDeveloper Individual Test 2: " + (test2() ? "passed" : "failed"));
        System.out.println("FrontendDeveloper Individual Test 3: " + (test3() ? "passed" : "failed"));
        System.out.println("FrontendDeveloper Individual Test 4: " + (test4() ? "passed" : "failed"));
        System.out.println("FrontendDeveloper Individual Test 5: " + (test5() ? "passed" : "failed"));
        System.out.println("FrontendDeveloper Integration Test 1: " + (test6() ? "passed" : "failed"));
        System.out.println("FrontendDeveloper Integration Test 2: " + (test7() ? "passed" : "failed"));
        System.out.println("FrontendDeveloper Partner BackendDeveloper Test 1: " + (test8() ? "passed" : "failed"));
        System.out.println("FrontendDeveloper Partner BackendDeveloper Test 2: " + (test9() ? "passed" : "failed"));
    }
}
