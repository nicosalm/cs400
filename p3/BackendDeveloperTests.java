import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.junit.Test;

/**
 * This class contains tests for the BackendDeveloper class. It tests that the
 * methods in the BackendDeveloper class are implemented correctly.
 * 
 * @author Nico
 */
public class BackendDeveloperTests {

    /**
     * Tests BackendDeveloper's loadDataFromFile method. This method should call
     * {@link IFlightReader#readFromFile(String)} to load the Cities into the
     * backend. For each city in the list of cities, add it to the graph. For each
     * city in the list of cities, add its flights to the graph.
     */
    @Test
    public void testLoadDataFromFile() {
        BackendBD backend = new BackendBD(new FlightGraphBD(), new FlightReaderBD());

        // test that the cities are added to the graph - this should not throw an error
        try {
            backend.loadDataFromFile("src/test/resources/BackendDeveloperTests/testLoadDataFromFile.dot");
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }

        // this should throw an error because the file doesn't exist
        assertThrows(IllegalArgumentException.class, () -> backend.loadDataFromFile("DNE"));

        // this should throw an error because the file is not in DOT format
        assertThrows(IllegalArgumentException.class,
                () -> backend.loadDataFromFile("src/test/resources/BackendDeveloperTests/testLoadDataFromFile.txt"));

        // this should throw an error because the file is a directory
        assertThrows(IllegalArgumentException.class,
                () -> backend.loadDataFromFile("src/test/resources/BackendDeveloperTests"));

    }

    /**
     * Tests BackendDeveloper's searchForFlight method. This method should call
     * {@link IFlightGraph#getEdge(String)} to search for a flight (an edge) between
     * two cities in the backend's flightGraph.
     */
    @Test
    public void testSearchForFlight() {
        BackendBD backend = new BackendBD(new FlightGraphBD(), new FlightReaderBD());

        // an existing flight should be returned
        assertEquals("FlightBD [destinationCityName=Chicago, flightDuration=3.0]",
                backend.searchForFlight("Madison", "Chicago").toString());

        // a non-existing flight should throw a NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> backend.searchForFlight("Madison", "DNE"));
    }

    /**
     * Tests BackendDeveloper's getStatisticsString method. This method should call
     * {@link IFlightGraph#getStatisticsString()} to get the statistics of the
     * backend's flightGraph, and return it as a String to be parsed by the
     * Frontend.
     */
    @Test
    public void testGetStatisticsString() {
        BackendBD backend = new BackendBD(new FlightGraphBD(), new FlightReaderBD());

        // test that the statistics string is returned correctly
        String expected = "Statistics for Madison:\n"
                + "Number of flights: 2\n"
                + "Flights:\n"
                + "FlightBD [destinationCityName=Chicago, flightDuration=3.0]\n"
                + "FlightBD [destinationCityName=Houston, flightDuration=6.0]\n";
        assertEquals(expected, backend.getStatisticsString("Madison"));

        // test that an exception is thrown when the city is not in the graph
        assertThrows(NoSuchElementException.class, () -> backend.getStatisticsString("DNE"));

    }

    /**
     * Tests BackendDeveloper's addCity method. This method should call
     * {@link FlightGraph#insertNode(NodeType)} to add a city to
     * the backend's flightGraph, and
     * {@link IFlightGraph#insertEdge(NodeType, NodeType, double)} to add each of
     * the city's flights to the backend's flightGraph.
     */
    @Test
    public void testAddCity() throws IllegalArgumentException, IOException {
        // add a city to the Flightgraph using BackendBD's addCity method and check that
        // the city was added correctly; duplicate cities should not be added to the
        // graph and should throw an IllegalArgumentException

        BackendBD backend = new BackendBD(new FlightGraphBD(), new FlightReaderBD());

        // addCity provides data to FlightGraphBD's insertNode method as intended
        backend.addCity("Berlin");
    }

    @Test
    public void testAddFlight() {
        // add an edge to the Flightgraph using BackendBD's addFlight method and check
        // that the edge was added correctly; duplicate edges should not be added to the
        // graph and should throw an IllegalArgumentException

        BackendBD backend = new BackendBD(new FlightGraphBD(), new FlightReaderBD());

        // expect an IllegalArgumentException to be thrown when adding a null edge
        assertThrows(IllegalArgumentException.class, () -> backend.addFlight(null, null, 0));

        // expect an IllegalArgumentException to be thrown when adding an edge with a
        // weight < 0
        assertThrows(IllegalArgumentException.class, () -> backend.addFlight("Madison", "Chicago", -1));

        // expect an IllegalArgumentException to be thrown when adding an edge with src
        // and dst the same
        assertThrows(IllegalArgumentException.class, () -> backend.addFlight("Madison", "Madison", 1));

        // add an edge which already exists in the graph
        assertThrows(IllegalArgumentException.class, () -> backend.addFlight("Madison", "Chicago", 1));

    }

    /**
     * Tests BackendDeveloper's getShortestPathData method. This method should call
     * {@link IFlightGraph#getShortestPathData(String, String)} to get the shortest
     * path data between two cities in the backend's flightGraph.
     */
    @Test
    public void testGetShortestPathData() {

        // backend interfaces with FlightGraph to pull data from the graph, in our
        // hardcoded case {@link FlightGraphBD#getShortestPathData(String, String)}
        // returns a list of cities, the shortest path between Madison and London

        BackendBD backend = new BackendBD(new FlightGraphBD(), new FlightReaderBD());

        List<String> routeNames = new ArrayList<String>();
        for (ICity city : backend.getShortestPathData("Madison", "London")) {
            routeNames.add(city.getCityName());
        }

        List<String> expectedRouteNames = new ArrayList<String>(
                Arrays.asList("Madison", "Chicago", "New York", "London"));

        // check that the route names are the same as the expected route names (Madison,
        // Chicago, New York, London) for our hardcoded case of FlightGraphBD

        assertEquals(expectedRouteNames, routeNames);
    }

    /**
     * Tests BackendDeveloper's getShortestPathCost method. This method should call
     * {@link IFlightGraph#getShortestPathCost(String, String)} to get the shortest
     * path cost between two cities in the backend's flightGraph.
     */
    @Test
    public void testGetShortestPathCost() {
        BackendBD backend = new BackendBD(new FlightGraphBD(), new FlightReaderBD());

        // backend interfaces with FlightGraph to pull data from the graph, in our
        // hardcoded case {@link FlightGraphBD#getShortestPathCost(String, String)}
        // returns 21 for the shortest path cost between Madison and London

        double cost = backend.getShortestPathCost("Madison", "London");
        assertEquals(21.0, cost);

    }

    /**
     * Checks {@link FrontendFD#addCommand()} properly handles exceptions that
     * occur.
     */
    @Test
    public void codeReviewOfFrontendDeveloper() {

        // test valid input
        {
            TextUITester uiTester = new TextUITester("T\nMadison\nT\nChicago\nA\nMadison\nChicago\n15\nQ\n");

            try (Scanner scanner = new Scanner(System.in)) {
                FrontendFD frontend = new FrontendFD(scanner, new BackendFD());
                frontend.runCommandLoop();
                String output = uiTester.checkOutput();
                assertTrue(output.contains("Flight added!"));
            }
        }

    }

    /**
     * Checks that {@link FrontendFD#searchCommand()} reports corrects information
     * when in a state where no exception is thrown (i.e., here we assume inputs are
     * valid).
     */
    @Test
    public void codeReviewOfFrontendDeveloper2() {
        TextUITester uiTester = new TextUITester("S\nMadison\nChicago\nQ\n");

        try (Scanner scanner = new Scanner(System.in)) {
            FrontendFD frontend = new FrontendFD(scanner, new BackendBD(new FlightGraphBD(), new FlightReaderBD()));
            frontend.runCommandLoop();
            String output = uiTester.checkOutput();
            System.out.println(output);
            assertTrue(output.contains("The shortest route is: ")); // checks that no exception occurs w/ valid inputs
        }
    }

    /**
     * This test checks adding cities; adding flights between those cities; and
     * calculating the shortest path between two cities.
     */
    @Test
    public void integrationTest1() {

        // creates a backend working with a FlightGraphAE and a FlightReaderDW
        var backend = new BackendBD(new FlightGraphAE(), new FlightReaderDW());

        // adds all components to the flight graph
        backend.addCity("Madison");
        backend.addCity("Chicago");
        backend.addCity("Milwaukee");
        backend.addCity("LA");

        backend.addFlight("Madison", "Chicago", 2);
        backend.addFlight("Madison", "Milwaukee", 1.5);

        backend.addFlight("Chicago", "Milwaukee", 1);

        backend.addFlight("Milwaukee", "LA", 4.1);
        backend.addFlight("Chicago", "LA", 3.9);

        // correctly calculates the shortest path between Madison and LA
        var shortestPath = backend.getShortestPathData("Madison", "LA");
        var expectedShortestPath = new ArrayList<String>(Arrays.asList("Madison", "Milwaukee", "LA"));

        assertEquals(expectedShortestPath, shortestPath.stream().map(ICity::getCityName).collect(Collectors.toList()));
    }

    /**
     * Tests calculating a shortest flight with a required layover.
     */
    @Test
    public void integrationTest2() {
        // creates a backend working with a FlightGraphAE and a FlightReaderDW
        var backend = new BackendBD(new FlightGraphAE(), new FlightReaderDW());

        // adds all components to the flight graph
        backend.addCity("Madison");
        backend.addCity("Chicago");
        backend.addCity("Milwaukee");
        backend.addCity("LA");

        backend.addFlight("Madison", "Chicago", 2);
        backend.addFlight("Madison", "Milwaukee", 1.5);

        backend.addFlight("Chicago", "Milwaukee", 1);

        backend.addFlight("Milwaukee", "LA", 4.1);   
        backend.addFlight("Chicago", "LA", 3.9);

        // correctly calculates the shortest path between Madison and LA
        var shortestPath = backend.getShortestPathDataWithRequiredEdge("Madison", "LA", "Chicago", "Milwaukee");
        var expectedShortestPath = new ArrayList<String>(Arrays.asList("Madison", "Chicago", "Milwaukee", "LA"));

        // tests a case where the provided edge does not exist
        assertThrows(NoSuchElementException.class,
                () -> backend.getShortestPathDataWithRequiredEdge("Madison", "LA", "Milwaukee", "Chicago"));

        assertEquals(expectedShortestPath, shortestPath.stream().map(ICity::getCityName).collect(Collectors.toList()));
    }
}
