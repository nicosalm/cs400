import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class FrontendDeveloperTests {

    /**
     * Checks that exceptions encountered in {@link FrontendFD#loadDataCommand()} are property handled.
     */
    @Test
    public void testLoadExceptionHandling() {

        // check FileNotFoundException
        {
            var uiTester = new TextUITester("L\nFileNotFoundException\nQ\n");

            try (var scanner = new Scanner(System.in)) {
                var frontend = new FrontendFD(scanner, new BackendFD());
                frontend.runCommandLoop(); // exception should be caught and not propagate
                var output = uiTester.checkOutput();
                assertTrue(output.contains("Error: The provided file path does not exist.")); // check error message is printed
            }
        }

        // check IllegalArgumentException
        {
            var uiTester = new TextUITester("L\nIllegalArgumentException\nQ\n");

            try (var scanner = new Scanner(System.in)) {
                var frontend = new FrontendFD(scanner, new BackendFD());
                frontend.runCommandLoop(); // exception should be caught and not propagate
                var output = uiTester.checkOutput();
                assertTrue(output.contains("Error: The provided file is not in DOT format.")); // check error message is printed
            }
        }

        // check SecurityException
        {
            var uiTester = new TextUITester("L\nSecurityException\nQ\n");

            try (var scanner = new Scanner(System.in)) {
                var frontend = new FrontendFD(scanner, new BackendFD());
                frontend.runCommandLoop(); // exception should be caught and not propagate
                var output = uiTester.checkOutput();
                assertTrue(output.contains("Error: The program doesn't have permission to read the provided file.")); // check error message is printed
            }
        }

        // check IOException
        {
            var uiTester = new TextUITester("L\nIOException\nQ\n");

            try (var scanner = new Scanner(System.in)) {
                var frontend = new FrontendFD(scanner, new BackendFD());
                frontend.runCommandLoop(); // exception should be caught and not propagate
                var output = uiTester.checkOutput();
                assertTrue(output.contains("Error: An internal I/O error occurred while reading. Please try again.")); // check error message is printed
            }
        }
    }

    /**
     * Checks that exceptions encountered in {@link FrontendFD#addCommand()} are property handled.
     */
    @Test
    public void testAddExceptionHandling() {

        // check IllegalArgumentException
        {
            var uiTester = new TextUITester("A\nIllegalArgumentException\nQ\n");

            try (var scanner = new Scanner(System.in)) {
                var frontend = new FrontendFD(scanner, new BackendFD());
                frontend.runCommandLoop(); // exception should be caught and not propagate
                var output = uiTester.checkOutput();
                assertTrue(output.contains("Error: City already exists or is malformed.")); // check error message is printed
            }
        }

        // check IOException
        {
            var uiTester = new TextUITester("A\nIOException\nQ\n");

            try (var scanner = new Scanner(System.in)) {
                var frontend = new FrontendFD(scanner, new BackendFD());
                frontend.runCommandLoop(); // exception should be caught and not propagate
                var output = uiTester.checkOutput();
                assertTrue(output.contains("Error: An internal I/O error occurred while reading. Please try again.")); // check error message is printed
            }
        }
    }

    /**
     * Checks that exceptions encountered in {@link FrontendFD#searchCommand()} are property handled.
     */
    @Test
    public void testSearchExceptionHandling() {

        // check NoSuchElementException
        {
            var uiTester = new TextUITester("S\nNoSuchElementException\nNoSuchElementException\nQ\n");

            try (var scanner = new Scanner(System.in)) {
                var frontend = new FrontendFD(scanner, new BackendFD());
                frontend.runCommandLoop(); // exception should be caught and not propagate
                var output = uiTester.checkOutput();
                assertTrue(output.contains("Error: No flight matches the search key.")); // check error message is printed
            }
        }
    }

    /**
     * Checks that {@link FrontendFD#loadDataCommand()} reports information in the expected form.
     */
    @Test
    public void testLoadReporting() {
        var uiTester = new TextUITester("L\nvalid_placeholder.dot\nQ\n");

        try(var scanner = new Scanner(System.in)) {
            var frontend = new FrontendFD(scanner, new BackendFD());
            frontend.runCommandLoop();
            var output = uiTester.checkOutput();
            assertTrue(output.contains("Enter path: "));
            assertTrue(output.contains("DOT loaded!"));
        }
    }

    /**
     * Checks that {@link FrontendFD#addCommand()} reports information in the expected form.
     */
    @Test
    public void testAddReporting() {
        var uiTester = new TextUITester("A\nvalid_placeholder_dot_str\nQ\n");

        try(var scanner = new Scanner(System.in)) {
            var frontend = new FrontendFD(scanner, new BackendFD());
            frontend.runCommandLoop();
            var output = uiTester.checkOutput();
            assertTrue(output.contains("Enter input in DOT format: "));
            assertTrue(output.contains("City added!"));
        }
    }

    /**
     * Test against {@link IBackend#getShortestPathCostWithRequiredEdge} and
     * {@link IBackend#getShortestPathDataWithRequiredEdge}
     */
    @Test
    public void codeReviewOfBackendDeveloper1() {

        // test IBackend#getShortestPathCostWithRequiredEdge
        {
            BackendBD backend = new BackendBD(new FlightGraphBD(), new FlightReaderBD());
            double cost = backend.getShortestPathCostWithRequiredEdge("Madison", "London", "Chicago", "New York");

            assertEquals(45.0, cost);
        }

        // test IBackend#getShortestPathWithRequiredEdge
        {
            BackendBD backend = new BackendBD(new FlightGraphBD(), new FlightReaderBD());
            List<String> routeNames = new ArrayList<>();
            for (ICity city : backend.getShortestPathDataWithRequiredEdge("Madison", "London", "Chicago", "New York")) {
                routeNames.add(city.getCityName());
            }

            List<String> expectedRouteNames = new ArrayList<>(
                    List.of("Madison", "Chicago", "New York", "London"));

            assertEquals(expectedRouteNames, routeNames);
        }
    }

    /**
     * Checks BD methods with erroneous inputs.
     */
    @Test
    public void codeReviewOfBackendDeveloper2() {

        // checks BD code against erroneous inputs
        {
            BackendBD backend = new BackendBD(new FlightGraphBD(), new FlightReaderBD());

            assertThrows(IllegalArgumentException.class,
                    () -> backend.getShortestPathCostWithRequiredEdge("Madison", "London", "Chicago", null));

            assertThrows(IllegalArgumentException.class,
                    () -> backend.getShortestPathDataWithRequiredEdge("Madison", "London", null, "New York"));

            assertThrows(IllegalArgumentException.class,
                    () -> backend.getStatisticsString(null));

            assertThrows(IllegalArgumentException.class,
                    () -> backend.addCity(null));

            assertThrows(IllegalArgumentException.class,
                    () -> backend.searchForFlight(null, "Chicago"));
        }

        // prints out the statistics for a city that does not exist
        {
            BackendBD backend = new BackendBD(new FlightGraphBD(), new FlightReaderBD());
            assertThrows(NoSuchElementException.class, () -> backend.getStatisticsString("DNE"));
        }
    }

    /**
     * Tests loading a file into the graph via frontend commands.
     * Then, ensures the shortest route from Seattle to Atlanta is "Seattle -> Chicago -> New_York -> Atlanta".
     * This result is expect given that the functionality of the graph and flight reader is implemented properly.
     */
    @Test
    public void integrationTest1() {
        var uiTester = new TextUITester("L\ngraph.dot\nS\nSeattle\nAtlanta\nQ\n"); // run a basic search

        try (var scanner = new Scanner(System.in)) {
            var graph = new FlightGraphAE<ICity, Double>();
            var reader = new FlightReaderDW();
            var backend = new BackendBD(graph, reader);
            var frontend = new FrontendFD(scanner, backend);
            frontend.runCommandLoop();
            var output = uiTester.checkOutput();
            assertTrue(output.contains("Seattle -> Chicago -> New_York -> Atlanta"));
        }
    }


    // /**
    //  * Tests add functionality via frontend commands.
    //  */
    // @Test
    // public void integrationTest2() {
    //     var uiTester = new TextUITester("L\ngraph.dot\nA\nSeattle\nAtlanta\nQ\n"); // run a basic search
    //
    //     try (var scanner = new Scanner(System.in)) {
    //         var graph = new FlightGraphAE<ICity, Double>();
    //         var reader = new FlightReaderDW();
    //         var backend = new BackendBD(graph, reader);
    //         var frontend = new FrontendFD(scanner, backend);
    //         frontend.runCommandLoop();
    //         var output = uiTester.checkOutput();
    //         assertTrue(output.contains("Seattle -> Chicago -> New_York -> Atlanta"));
    //     }
    //
    // }

    @Test
    public void testGraphShortestPathAfterInsert() {
        var graph = new FlightGraphAE<ICity, Double>();
        
        var c1 = new CityAE("c1");
        var c2 = new CityAE("c2");
        var c3 = new CityAE("c3");
        var c4 = new CityAE("c4");
        
        // should not be able to add another node with the same name

        graph.insertNode(c1);
        graph.insertNode(c2);
        graph.insertNode(c3);
        graph.insertNode(c4);
        assertFalse(graph.insertNode(new CityAE("c1")));

        graph.insertEdge(c1, c2, 10d);
        graph.insertEdge(c2, c3, 10d);
        graph.insertEdge(c3, c4, 10d);
        graph.insertEdge(c1, c3, 10d);

        var res = graph.computeShortestPath(c1, c4);
        assertSame(res.node.data, c4);
        assertSame(res.predecessor.node.data, c3);
        assertSame(res.predecessor.predecessor.node.data, c1);
    }

    @Test
    public void testGraphUniqueness() {
        var graph = new FlightGraphAE<ICity, Double>();

        var c = new CityAE("c1");
        var dupe = new CityAE("c1");
        graph.insertNode(c);
        assertFalse(graph.insertNode(dupe));
    }

    @Test
    public void loadGraphFromDOTFileContainsAllNodes() throws IOException {
        var reader = new FlightReaderDW();

        var cities = reader.readFromFile("graph.dot");
        assertEquals(9, cities.size());
    }

    @Test
    public void testReadDOTLine() throws IOException {
        var reader = new FlightReaderDW();

        var cities = reader.readDOTLineIntoCity("Albuquerque->Detroit [label=\"3-8092\"]");
        var src = cities.get(0);
        var dst = cities.get(1);

        assertTrue(src.getFlightList().size() != 0);
        assertEquals(src.getFlightList().get(0).destinationCity().getCityName(), dst.getCityName());
    }

}
