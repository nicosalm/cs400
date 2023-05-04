import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DataWranglerTests {

    /**
     * Tests readFromFile() method with valid dot string input testReadFromFileValid 
    */ 
    @Test
    void testReadFromFileValid() {
        FlightReaderDW tester = new FlightReaderDW();
        List<ICity> cities = new ArrayList<>();
        try {
            // test valid file 
            cities = tester.readFromFile("graph.dot");

            // test for city names
            assertEquals(cities.get(0).getCityName(), "Seattle");
            assertEquals(cities.get(1).getCityName(), "Chicago");
            assertEquals(cities.get(2).getCityName(), "Chicago");
            assertEquals(cities.get(3).getCityName(), "New_York");
            assertEquals(cities.get(4).getCityName(), "New_York");
            assertEquals(cities.get(5).getCityName(), "Atlanta");
            assertEquals(cities.get(6).getCityName(), "Seattle");
            assertEquals(cities.get(7).getCityName(), "San_Francisco");
        } catch(IOException e) {}
    }

    /**
     * Tests readFromFile() method with invalid dot string input (empty, null, w/o .dot) and 
     * tests inaccessible file
     * @throws IllegalArgumentException 
     * @throws FileNotFoundException
    */
    @Test
    void testReadFromFileInvalid() {
        FlightReaderDW tester = new FlightReaderDW();
        boolean exception1 = false;
        boolean exception2 = false;

        // test for invalid input w/o .dot
        try {
            List<ICity> cities1 = tester.readFromFile("data");
        } catch(IllegalArgumentException e) {
            exception1 = true;
        } catch(IOException e) {} 

        // test for invalid file
        try {
            List<ICity> cities2 = tester.readFromFile("data.dot");
        } catch(FileNotFoundException e) {
            exception2 = true;
        } catch(IOException e) {} 

        // test exceptions were caught in both cases
        assertEquals(exception1, true);
        assertEquals(exception2, true);
    }

    /**
     * Tests readDOTLineIntoCity() with invalid DOT format
     * @throws IllegalArgumentException
    */
    @Test
    void testReadDOTLineIntoCityInvalid1() {
        FlightReaderDW tester = new FlightReaderDW();

        // test for invalid DOT input (without label)
        String dotString1 = "Berlin->Rome[\"-\"]";
        boolean exceptionCaught1 = false;
        try {
            List<ICity> city1 = tester.readDOTLineIntoCity(dotString1);
        } catch(IllegalArgumentException e) {
            exceptionCaught1 = true;
        } catch(IOException e) {}

        // test for invalid DOT input (without destination)
        String dotString2 = "Berlin";
        boolean exceptionCaught2 = false;
        try {
            List<ICity> city2 = tester.readDOTLineIntoCity(dotString2);
        } catch(IllegalArgumentException e) {
            exceptionCaught2 = true;
        } catch(IOException e) {}

        // test exceptions were caught in both cases
        assertEquals(exceptionCaught1, true);
        assertEquals(exceptionCaught2, true);
    }

    /**
     * Tests readDOTLineIntoCity() with valid DOT input
     * @throws IllegalArgumentException
    */
    @Test
    void testReadDOTLineIntoCityValid1() {
        FlightReaderDW tester = new FlightReaderDW();
        String dotString = "Delhi->Mumbai [label=\"2-1234\"]";
        List<ICity> city = new ArrayList<>();
        try {
            city = tester.readDOTLineIntoCity(dotString);
        } catch(IOException e) {}
        ICity source = city.get(0);
        ICity destination = city.get(1);

        // check city names
        assertEquals("Delhi", source.getCityName());
        assertEquals("Mumbai", destination.getCityName());
    
        // check flight list sizes
        List<IFlight> flights1 = source.getFlightList();
        assertEquals(1, flights1.size());
        
        // check flight details
        IFlight flight = flights1.get(0);
        assertEquals("Mumbai", flight.destinationCity().getCityName());
        assertEquals(2, flight.flightDuration());
    }

    /**
     * Tests readDOTLineIntoCity() with valid DOT input (different example)
    */
    @Test
    void testReadDOTLineIntoCityValid2() {
        FlightReaderDW tester = new FlightReaderDW();
        String dotString = "Berlin->Rome [label=\"2-0000\"]";
        List<ICity> city = new ArrayList<>();
        try {
            city = tester.readDOTLineIntoCity(dotString);
        } catch(IOException e) {}
        ICity source = city.get(0);
        ICity destination = city.get(1);

        // check city names
        assertEquals("Berlin", source.getCityName());
        assertEquals("Rome", destination.getCityName());
    
        // check flight list sizes
        List<IFlight> flights1 = source.getFlightList();
        assertEquals(1, flights1.size());

        // check flight details
        IFlight flight = flights1.get(0);
        assertEquals("Rome", flight.destinationCity().getCityName());
        assertEquals(2, flight.flightDuration());
    }

    /**
     * Tests shortestPathCost() and shortestPathCostWithRequiredEdge() in FlightGraphAE
    */
    @Test
    void CodeReviewOfAlgorithmEngineer1() {
        // create FlightGraphAE
        FlightGraphAE<CityDW, Double> graph = new FlightGraphAE<CityDW, Double>();

        // create cityDW objects 
		CityDW seattle = new CityDW("Seattle", new ArrayList<IFlight>());
		CityDW chicago = new CityDW("Chicago", new ArrayList<IFlight>());
		CityDW newYork = new CityDW("New_York", new ArrayList<IFlight>());
		CityDW sanFrancisco = new CityDW("San_Francisco", new ArrayList<IFlight>());
		CityDW memphis = new CityDW("Memphis", new ArrayList<IFlight>());
		
        // insert nodes intro graph
		graph.insertNode(seattle);
        graph.insertNode(chicago);
        graph.insertNode(newYork);
        graph.insertNode(sanFrancisco);
        graph.insertNode(memphis);
		
        // insert edges into graph
		graph.insertEdge(seattle, chicago, 6.0);
        graph.insertEdge(seattle, newYork, 1.0);
		graph.insertEdge(chicago, sanFrancisco, 2.0);
        graph.insertEdge(chicago, newYork, 2.0);
        graph.insertEdge(newYork, sanFrancisco, 1.0);
        graph.insertEdge(chicago, memphis, 5.0);
        graph.insertEdge(sanFrancisco, memphis, 5.0);
		
        // check for shortestPathCost
		assertEquals(graph.shortestPathCost(seattle, memphis), 7.0);
        // check for shortestPathCostWithRequiredEdge
        assertEquals(graph.shortestPathCostWithRequiredEdge(seattle, memphis, chicago, newYork), 14.0);
    }

    /**
     * Tests shortestPathData() and shortestPathDataWithRequiredEdge() using CodeReviewOfAlgorithmEngineer1 
     * example in FlightGraphAE
    */
    @Test
    void CodeReviewOfAlgorithmEngineer2() {
        // create FlightGraphAE
        FlightGraphAE<CityDW, Double> graph = new FlightGraphAE<CityDW, Double>();
        // create cityDW objects 
        CityDW seattle = new CityDW("Seattle", new ArrayList<IFlight>());
        CityDW chicago = new CityDW("Chicago", new ArrayList<IFlight>());
        CityDW newYork = new CityDW("New_York", new ArrayList<IFlight>());
        CityDW sanFrancisco = new CityDW("San_Francisco", new ArrayList<IFlight>());
        CityDW memphis = new CityDW("Memphis", new ArrayList<IFlight>());
        // insert nodes intro graph
        graph.insertNode(seattle);
        graph.insertNode(chicago);
        graph.insertNode(newYork);
        graph.insertNode(sanFrancisco);
        graph.insertNode(memphis);
        // insert edges into graph
        graph.insertEdge(seattle, chicago, 6.0);
        graph.insertEdge(seattle, newYork, 1.0);
        graph.insertEdge(chicago, sanFrancisco, 2.0);
        graph.insertEdge(chicago, newYork, 2.0);
        graph.insertEdge(newYork, sanFrancisco, 1.0);
        graph.insertEdge(chicago, memphis, 5.0);
        graph.insertEdge(sanFrancisco, memphis, 5.0);
        // seattle -> newYork -> sanFrancisco -> memphis (1 + 1 + 5 = 7)
        List<CityDW> shortestPathData = new LinkedList<CityDW>();
		shortestPathData.add(seattle);
		shortestPathData.add(newYork);
		shortestPathData.add(sanFrancisco);
        shortestPathData.add(memphis);
        // test for shortestPathData
        assertEquals(graph.shortestPathData(seattle, memphis), shortestPathData);
        // seattle -> chicago -> newYork -> sanFrancisco -> memphis (6 + 2 + 1 + 5 = 14)
        List<CityDW> shortestPathDataWithRequiredEdge = new LinkedList<CityDW>();
        shortestPathDataWithRequiredEdge.add(seattle);
		shortestPathDataWithRequiredEdge.add(chicago);
		shortestPathDataWithRequiredEdge.add(newYork);
        shortestPathDataWithRequiredEdge.add(sanFrancisco);
        shortestPathDataWithRequiredEdge.add(memphis);
        // test for shortestPathDataWithRequiredEdge
        assertEquals(graph.shortestPathDataWithRequiredEdge(seattle, memphis, chicago, newYork), shortestPathDataWithRequiredEdge);
    }

    /**
     * Integration test for BackendBD, FlightGraphAE, and FlightReaderDW tests getShortestPathCost()
    */
    @Test
    void integrationTest1() {
        FlightGraphAE<ICity, Double> graph = new FlightGraphAE<ICity,Double>();
        FlightReaderDW reader = new FlightReaderDW();
        BackendBD backend = new BackendBD(graph, reader);
        try {
            backend.loadDataFromFile("graph.dot");
            double cost = backend.getShortestPathCost("Seattle", "Chicago");
            assertEquals(5.0, cost);
        } catch(IOException e) {}
    }

    /**
     * Integration test for BackendBD, FlightGraphAE, and FlightReaderDW tests getShortestPathData()
    */
    @Test
    void integrationTest2() {
        FlightGraphAE<ICity, Double> graph = new FlightGraphAE<ICity,Double>();
        FlightReaderDW reader = new FlightReaderDW();
        BackendBD backend = new BackendBD(graph, reader);
        try {
            backend.loadDataFromFile("graph.dot");
            double cost = backend.getShortestPathCostWithRequiredEdge("Seattle", "New_York", "Seattle", "Chicago");
            assertEquals(9.0, cost);
        } catch(IOException e) {}
    }

}