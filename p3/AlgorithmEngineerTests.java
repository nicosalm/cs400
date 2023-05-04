import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class AlgorithmEngineerTests 
{
	/**
     * This test tests the constructor, as well as the insert and remove methods. I also test the count methods.  
     * To do this, I create a FlightGraphAE object, and then I create cities, insert Nodes and edges, and then test
     * all the methods I mentioned above on them. I use assertEquals to make sure the outputs are what they should be.
     */
	@Test
    public void test1() 
    {
		FlightGraphAE<CityAE, Double> flightGraph = new FlightGraphAE<CityAE, Double>();
		
		CityAE city1 = new CityAE("1");
		CityAE city2 = new CityAE("2");
		CityAE city3 = new CityAE("3");
		CityAE city4 = new CityAE("4");
		CityAE city5 = new CityAE("5");
		
		flightGraph.insertNode(city1);
		assertEquals(flightGraph.getNodeCount(), 1);
		flightGraph.insertNode(city2);
		assertEquals(flightGraph.getNodeCount(), 2);
		flightGraph.insertNode(city3);
		assertEquals(flightGraph.getNodeCount(), 3);
		flightGraph.insertNode(city4);
		assertEquals(flightGraph.getNodeCount(), 4);
		flightGraph.insertNode(city5);
		assertEquals(flightGraph.getNodeCount(), 5);
		
		try
		{
			flightGraph.insertNode(city5);
		}
		catch(Exception e)
		{
		}
		
		flightGraph.insertEdge(city4, city5, 9.0);
		assertEquals(flightGraph.getEdgeCount(), 1);
		flightGraph.insertEdge(city1, city4, 12.0);
		assertTrue(flightGraph.containsEdge(city1, city4));
		assertEquals(flightGraph.getEdgeCount(), 2);
		flightGraph.insertEdge(city2, city5, 2.0);
		flightGraph.insertEdge(city3, city5, 93.45);
		flightGraph.insertEdge(city2, city3, 1.2);
		flightGraph.insertEdge(city4, city1, 8.53);
		flightGraph.insertEdge(city4, city2, 3.45);
		
		
		
		flightGraph.removeEdge(city4, city5);
		assertEquals(flightGraph.getEdgeCount(), 6);

		flightGraph.removeEdge(city2, city4);

		
		assertEquals(flightGraph.getEdgeCount(), 6);
		
		flightGraph.removeNode(city5);
		flightGraph.removeNode(city2);
		
		assertEquals(flightGraph.getNodeCount(), 3);
		assertEquals(flightGraph.getEdgeCount(), 2);
    }
	
	/**
     * This test tests the get methods, besides the count methods.
     * To do this, I create a FlightGraphAE object, and then I create cities, insert Nodes and edges, and then test
     * all the methods I mentioned above on them. I use assertEquals to make sure the outputs are what they should be.
     */
	@Test
    public void test2() 
    {
		FlightGraphAE<CityAE, Double> flightGraph = new FlightGraphAE<CityAE, Double>();
		
		CityAE city1 = new CityAE("1");
		CityAE city2 = new CityAE("2");
		CityAE city3 = new CityAE("3");
		CityAE city4 = new CityAE("4");
		CityAE city5 = new CityAE("5");
		
		flightGraph.insertNode(city1);
		flightGraph.insertNode(city2);
		flightGraph.insertNode(city3);
		flightGraph.insertNode(city4);
		flightGraph.insertNode(city5);
		
		flightGraph.insertEdge(city4, city5, 9.0);
		flightGraph.insertEdge(city1, city4, 12.0);
		flightGraph.insertEdge(city2, city5, 2.0);
		flightGraph.insertEdge(city3, city5, 93.45);
		flightGraph.insertEdge(city2, city3, 1.2);
		flightGraph.insertEdge(city4, city1, 8.53);
		flightGraph.insertEdge(city4, city2, 3.45);
				
		int a = 0;
		
		try
		{
			flightGraph.getCity("city");
		}
		catch(Exception e)
		{
			a++;
		}
		
		assertEquals(a, 1);
		
		int b = 0;
		
		try
		{
			flightGraph.getFlight("flight");
		}
		catch(Exception e)
		{
			b++;
		}
		
		assertEquals(b, 1);
		
		int c = 0;
		
		try
		{
			flightGraph.getEdge(city1, city3);
		}
		catch(Exception e)
		{
			c++;
		}
		
		assertEquals(c, 1);
		
		assertEquals(flightGraph.getEdge(city4, city5), 9.0);
		assertEquals(flightGraph.getEdge(city1, city4), 12.0);
		
		assertEquals(flightGraph.getCity("1").getCityName(), city1.getCityName());
		
		assertEquals(flightGraph.getFlight("000045").flightCode(), "000045");
		
	
    }
	
	/**
     * This test tests the contains methods, to make sure the graph is updating like it should.
     * To do this, I create a FlightGraphAE object, and then I create cities, insert Nodes and edges, and then test
     * all the methods I mentioned above on them. I use assertEquals to make sure the outputs are what they should be.
     */
	@Test
    public void test3() 
    {
		FlightGraphAE<CityAE, Double> flightGraph = new FlightGraphAE<CityAE, Double>();
		
		CityAE city1 = new CityAE("1");
		CityAE city2 = new CityAE("2");
		CityAE city3 = new CityAE("3");
		CityAE city4 = new CityAE("4");
		CityAE city5 = new CityAE("5");
		CityAE city6 = new CityAE("6");
		
		flightGraph.insertNode(city1);
		flightGraph.insertNode(city2);
		flightGraph.insertNode(city3);
		flightGraph.insertNode(city4);
		flightGraph.insertNode(city5);
		
		flightGraph.insertEdge(city4, city5, 9.0);
		flightGraph.insertEdge(city1, city4, 12.0);
		flightGraph.insertEdge(city2, city5, 2.0);
		flightGraph.insertEdge(city3, city5, 93.45);
		flightGraph.insertEdge(city2, city3, 1.2);
		flightGraph.insertEdge(city4, city1, 8.53);
		flightGraph.insertEdge(city4, city2, 3.45);
		
		assertTrue(flightGraph.containsEdge(city4, city5));
		assertFalse(flightGraph.containsEdge(city2, city1));
		
		assertTrue(flightGraph.containsNode(city4));
		assertFalse(flightGraph.containsNode(city6));		
		
    }
	
	/**
     * This test tests the shortestPath methods that do NOT require a specific edge that is needed.
     * To do this, I create a FlightGraphAE object, and then I create cities, insert Nodes and edges, and then test
     * all the methods I mentioned above on them. I use assertEquals to make sure the outputs are what they should be.
     */
	@Test
    public void test4() 
    {
		FlightGraphAE<CityAE, Double> flightGraph = new FlightGraphAE<CityAE, Double>();
		
		CityAE city1 = new CityAE("1");
		CityAE city2 = new CityAE("2");
		CityAE city3 = new CityAE("3");
		CityAE city4 = new CityAE("4");
		CityAE city5 = new CityAE("5");
		
		flightGraph.insertNode(city1);
		flightGraph.insertNode(city2);
		flightGraph.insertNode(city3);
		flightGraph.insertNode(city4);
		flightGraph.insertNode(city5);
		
		flightGraph.insertEdge(city4, city5, 9.0);
		flightGraph.insertEdge(city1, city4, 12.0);
		flightGraph.insertEdge(city2, city5, 2.0);
		flightGraph.insertEdge(city3, city5, 93.45);
		flightGraph.insertEdge(city2, city3, 1.2);
		flightGraph.insertEdge(city4, city1, 8.53);
		flightGraph.insertEdge(city4, city2, 3.45);
		
		assertEquals(flightGraph.shortestPathCost(city4, city5), 5.45);
		assertEquals(flightGraph.shortestPathCost(city1, city4), 12.0);
		
		List<CityAE> linkedList = new LinkedList<CityAE>();

		linkedList.add(city4);
		linkedList.add(city2);
		linkedList.add(city5);
		
		assertEquals(flightGraph.shortestPathData(city4, city5), linkedList);
		
    }
	
	/**
     * This test tests the shortestPath methods that do require a specific edge that is needed.
     * To do this, I create a FlightGraphAE object, and then I create cities, insert Nodes and edges, and then test
     * all the methods I mentioned above on them. I use assertEquals to make sure the outputs are what they should be.
     */	
	@Test
    public void test5() 
    {
		FlightGraphAE<CityAE, Double> flightGraph = new FlightGraphAE<CityAE, Double>();
		
		CityAE city1 = new CityAE("1");
		CityAE city2 = new CityAE("2");
		CityAE city3 = new CityAE("3");
		CityAE city4 = new CityAE("4");
		CityAE city5 = new CityAE("5");
		
		flightGraph.insertNode(city1);
		flightGraph.insertNode(city2);
		flightGraph.insertNode(city3);
		flightGraph.insertNode(city4);
		flightGraph.insertNode(city5);
		
		flightGraph.insertEdge(city4, city5, 9.0);
		flightGraph.insertEdge(city1, city4, 12.0);
		flightGraph.insertEdge(city2, city5, 2.0);
		flightGraph.insertEdge(city3, city5, 93.45);
		flightGraph.insertEdge(city2, city3, 1.2);
		flightGraph.insertEdge(city5, city1, 8.53);
		flightGraph.insertEdge(city4, city2, 3.45);
				
		
		assertEquals(flightGraph.shortestPathCostWithRequiredEdge(city4, city3, city2, city3), 4.65);
		assertEquals(flightGraph.shortestPathCostWithRequiredEdge(city2, city1, city2, city3), 103.18);
		 
		List<CityAE> linkedList = new LinkedList<CityAE>();

		linkedList.add(city4);
		linkedList.add(city2);
		linkedList.add(city3);
		
		assertEquals(flightGraph.shortestPathDataWithRequiredEdge(city4, city3, city2, city3), linkedList);
		
		List<CityAE> linkedList2 = new LinkedList<CityAE>();

		linkedList2.add(city2);
		linkedList2.add(city3);
		linkedList2.add(city5);
		linkedList2.add(city1);
		
		assertEquals(flightGraph.shortestPathDataWithRequiredEdge(city2, city1, city2, city3), linkedList2);
		
		
    }
	
	/**
     * Here, I test the data wrangler code. I check to see if creating the object that I create has the correct functionalities that it should be having. I 
     * make sure that the codes, names, etc of each city and flight are showing up to be what I want them to be.
     */	
	@Test	
	public void CodeReviewOfDataWrangler1()
	{
		List<IFlight> list1 = new LinkedList<IFlight>();
		List<IFlight> list2 = new LinkedList<IFlight>();
		List<IFlight> list3 = new LinkedList<IFlight>();
		List<IFlight> list4 = new LinkedList<IFlight>();
		List<IFlight> list5 = new LinkedList<IFlight>();
		List<IFlight> list6 = new LinkedList<IFlight>();
		List<IFlight> list7 = new LinkedList<IFlight>();
		
		CityDW city1 = new CityDW("City 1", list1);
		CityDW city2 = new CityDW("City 2", list2);
		CityDW city3 = new CityDW("City 3", list3);
		CityDW city4 = new CityDW("City 4", list4);
		CityDW city5 = new CityDW("City 5", list5);
		CityDW city6 = new CityDW("City 6", list6);
		CityDW city7 = new CityDW("City 7", list7);
		
		FlightDW flight1 = new FlightDW(3.2, city1, city2);
		FlightDW flight2 = new FlightDW(33.1, city2, city1);
		FlightDW flight3 = new FlightDW(2.3, city3, city2);
		FlightDW flight4 = new FlightDW(351.0, city2, city3);
		FlightDW flight5 = new FlightDW(11.1, city4, city1);
		FlightDW flight6 = new FlightDW(9.8, city2, city5);
		FlightDW flight7 = new FlightDW(4.55, city6, city7);
		FlightDW flight8 = new FlightDW(24.5, city7, city3);
		
		list1.add(flight1);
		list1.add(flight2);
		list1.add(flight5);
		
		list2.add(flight1);
		list2.add(flight2);
		list2.add(flight3);
		list2.add(flight4);
		list2.add(flight6);
		
		list3.add(flight3);
		list3.add(flight4);
		list3.add(flight8);
		
		list4.add(flight5);
		
		list5.add(flight6);
		
		list6.add(flight7);
		
		list7.add(flight7);
		list7.add(flight8);
		
		assertEquals(city2.getFlightList(), list2);
		assertEquals(city5.getCityName(), "City 5");
		
		assertTrue(city3.getFlightList().contains(flight4));
		assertTrue(city3.getFlightList().contains(flight8));
		
		assertTrue(city5.getFlightList().contains(flight6));
		assertTrue(city6.getFlightList().contains(flight7));
		
		assertTrue(city7.getFlightList().contains(flight7));
		assertTrue(city1.getFlightList().contains(flight1));	
		
		assertEquals(flight1.destinationCity(), city2);
		assertEquals(flight4.destinationCity(), city3);
		
		assertEquals(flight6.sourceCity(), city2);
		assertEquals(flight5.sourceCity(), city4);
		
		assertEquals(flight2.flightCode(), "000019");
		assertEquals(flight3.flightCode(), "000020");
		assertEquals(flight4.flightCode(), "000021");
		assertEquals(flight5.flightCode(), "000022");
		
		assertEquals(flight5.flightDuration(), 11.1);
		assertEquals(flight2.flightDuration(), 33.1);
		assertEquals(flight3.flightDuration(), 2.3);
	}
	
	/**
     * Here, I add different Data Wrangler implementations to make sure that they work with my code. I call backend.loadDataFromFile since it 
     * just calls the data wrangler code, and then I check to see which cities (or nodes) are inputted into the graph.
     */	
	@Test	
	public void CodeReviewOfDataWrangler2()
	{

        
        var reader = new FlightReaderDW();
		var graph = new FlightGraphAE<ICity, Double>();
		var backend = new BackendBD(graph, reader);
		
		try 
		{
			backend.loadDataFromFile("graph.dot");
		} catch (IllegalArgumentException | SecurityException | IOException e) 
		{
			System.out.println("error");
		}
		
		assertEquals(graph.getNodeCount(), 9);

		graph.insertEdge(graph.getCity("Seattle"), graph.getCity("Chicago"), 5.0);		
		assertTrue(graph.containsEdge(graph.getCity("Seattle"), graph.getCity("Chicago")));

		assertEquals(graph.shortestPathCost(graph.getCity("Seattle"), graph.getCity("Chicago")), 5.0);
		
		
	}
	
	/**
     * Here, I add different Data Wrangler implementations to make sure that they work with my code. I create different objects in the 
     * way that the data wrangler intended them to be made, and then I called the more basic methods that I have in my FlightGraphAE 
     *implementation, just so that I know that the objects work correctly.
     */	
	@Test
	public void testIntegration1()
	{
		
FlightGraphAE<CityDW, Double> flightGraph = new FlightGraphAE<CityDW, Double>();
		
		List<IFlight> list1 = new LinkedList<IFlight>();
		List<IFlight> list2 = new LinkedList<IFlight>();
		List<IFlight> list3 = new LinkedList<IFlight>();
		List<IFlight> list4 = new LinkedList<IFlight>();
		List<IFlight> list5 = new LinkedList<IFlight>();
		List<IFlight> list6 = new LinkedList<IFlight>();
		List<IFlight> list7 = new LinkedList<IFlight>();
		
		CityDW city1 = new CityDW("City 1", list1);
		CityDW city2 = new CityDW("City 2", list2);
		CityDW city3 = new CityDW("City 3", list3);
		CityDW city4 = new CityDW("City 4", list4);
		CityDW city5 = new CityDW("City 5", list5);
		CityDW city6 = new CityDW("City 6", list6);
		CityDW city7 = new CityDW("City 7", list7);
		
		flightGraph.insertNode(city1);
		flightGraph.insertNode(city2);
		flightGraph.insertNode(city3);
		flightGraph.insertNode(city4);
		flightGraph.insertNode(city5);
		flightGraph.insertNode(city6);
		flightGraph.insertNode(city7);
		
		flightGraph.insertEdge(city2, city1, 3.2);
		flightGraph.insertEdge(city1, city2, 33.1);
		flightGraph.insertEdge(city2, city3, 2.3);
		flightGraph.insertEdge(city3, city2, 351.0);
		flightGraph.insertEdge(city1, city4, 11.1);
		flightGraph.insertEdge(city5, city2, 9.8);
		flightGraph.insertEdge(city7, city6, 4.55);
		flightGraph.insertEdge(city3, city7, 24.5);


		assertTrue(flightGraph.containsEdge(city1, city4));
		assertTrue(flightGraph.containsEdge(city3, city2));
		assertFalse(flightGraph.containsEdge(city6, city7));
		
		assertTrue(flightGraph.containsNode(city4));
		assertTrue(flightGraph.containsNode(city5));
		
		assertEquals(flightGraph.getCity("City 2"), city2);
		assertEquals(flightGraph.getCity("City 5"), city5);
		
		assertEquals(flightGraph.getFlight("000006").flightDuration(), 9.8);
		assertEquals(flightGraph.getFlight("000005").flightDuration(), 11.1); 
		
	}
	
	
	/**
     * Here, I make sure that the Data Wrangler code and my own code work together in the harder functionalities,
     * and that all the functions of each are compatible. To do this, I make sure to add a lot of different flights and cities
     * using the Data Wrangler implementation, and make sure that those objects work with my implementation of different methods in the
     * FlightGraphAE class.
     */	
	@Test
	public void testIntegration2()
	{
		FlightGraphAE<CityDW, Double> flightGraph = new FlightGraphAE<CityDW, Double>();

		List<IFlight> list1 = new LinkedList<IFlight>();
		List<IFlight> list2 = new LinkedList<IFlight>();
		List<IFlight> list3 = new LinkedList<IFlight>();
		List<IFlight> list4 = new LinkedList<IFlight>();
		List<IFlight> list5 = new LinkedList<IFlight>();
		List<IFlight> list6 = new LinkedList<IFlight>();
		List<IFlight> list7 = new LinkedList<IFlight>();
		
		CityDW city1 = new CityDW("City 1", list1);
		CityDW city2 = new CityDW("City 2", list2);
		CityDW city3 = new CityDW("City 3", list3);
		CityDW city4 = new CityDW("City 4", list4);
		CityDW city5 = new CityDW("City 5", list5);
		CityDW city6 = new CityDW("City 6", list6);
		CityDW city7 = new CityDW("City 7", list7);
		
		flightGraph.insertNode(city1);
		flightGraph.insertNode(city2);
		flightGraph.insertNode(city3);
		flightGraph.insertNode(city4);
		flightGraph.insertNode(city5);
		flightGraph.insertNode(city6);
		flightGraph.insertNode(city7);
		
		flightGraph.insertEdge(city2, city1, 3.2);
		flightGraph.insertEdge(city1, city2, 33.1);
		flightGraph.insertEdge(city2, city3, 2.3);
		flightGraph.insertEdge(city3, city2, 351.1);
		flightGraph.insertEdge(city1, city4, 11.1);
		flightGraph.insertEdge(city5, city2, 9.8);
		flightGraph.insertEdge(city7, city6, 4.55);
		flightGraph.insertEdge(city3, city7, 24.5);
		flightGraph.insertEdge(city2, city4, 2.5);
		
		
		
		assertEquals(flightGraph.shortestPathCost(city3, city1), 354.3);
		assertEquals(flightGraph.shortestPathCost(city3, city7), 24.5);
		
		List<CityDW> linkedList = new LinkedList<CityDW>();

		linkedList.add(city3);
		linkedList.add(city7);
		
		assertEquals(flightGraph.shortestPathData(city3, city7), linkedList);
		
		List<CityDW> linkedList2 = new LinkedList<CityDW>();

		linkedList2.add(city3);
		linkedList2.add(city2);
		linkedList2.add(city1);
		
		assertEquals(flightGraph.shortestPathData(city3, city1), linkedList2);
		
		assertEquals(flightGraph.shortestPathCostWithRequiredEdge(city3, city1, city2, city3), 707.7);
		assertEquals(flightGraph.shortestPathCostWithRequiredEdge(city2, city3, city2, city1), 38.6);
		
		List<CityDW> linkedList3 = new LinkedList<CityDW>();

		linkedList3.add(city1);
		linkedList3.add(city2);
		linkedList3.add(city4);
		
		assertEquals(flightGraph.shortestPathDataWithRequiredEdge(city1, city4, city2, city4), linkedList3);	
		
	}

}

