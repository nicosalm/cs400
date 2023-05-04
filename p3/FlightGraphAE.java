import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class FlightGraphAE<N, E extends Number> implements GraphADT<N, E>, IFlightGraph<N,E>
{ 
	
	protected Hashtable<N, CityNode> nodes;
	protected Hashtable<String, FlightEdge> flights;
	
	/**
	   * This method is the constructor for this class.
	   * 
	   */
    public FlightGraphAE() 
    {
        nodes = new Hashtable<N, CityNode>();
        flights = new Hashtable<String, FlightEdge>();
    }
	
    /**
	   * This is a class to create a node for the City object
	   *
	   */
	protected class CityNode 
	{
		public N data;
	    public LinkedList<FlightEdge> edgesLeaving;
	    public LinkedList<FlightEdge> edgesEntering;

	    /**
		   * This is the constructor for CityNode, and it instantiates the linked lists, and the data.
		   *
		   */
	    public CityNode(N city) 
	    {
	        this.data = city;
	        edgesLeaving = new LinkedList<>();
	        edgesEntering = new LinkedList<>();
	    }
    }

	/**
	   * This is a class to create an Edge using the Flight object data.
	   *
	   */
    protected class FlightEdge 
    {
    	public IFlight flight;
        public E weight;
        public CityNode predecessor;
        public CityNode successor;

       /**
  	   * This is a constructor for the FlightEdge class, and it instantiates an IFlight implementation object
  	   * and the weight, predecessor, and successor variables of the class.
  	   *
  	   */
        public FlightEdge(E weight, CityNode predecessor,CityNode successor) 
        {
        	
            this.flight = new FlightDW((double) weight, (ICity) predecessor.data, (ICity) successor.data);
            this.weight = weight;
            this.predecessor = predecessor;
            this.successor = successor;
        }
    }
	
	
		private int numberOfCities = 0;
		private int numberOfFlights = 0;
		
		  /**
		   * Clears the Graph.
		   */
		 public void clear()
		 {
			 nodes.clear();
			 flights.clear();
			 numberOfCities = 0;
			 numberOfFlights = 0;
		 }
		  
		  /**
		   * This method returns the city with the given name
		   * 
		   * @param cityName - name of the city
		   * @return city with the given name 
		   */
		 public ICity getCity(String cityName)
		 {

			 for (CityNode node : nodes.values()) 
			 {
			        if (node.data instanceof ICity && ((ICity)node.data).getCityName().equals(cityName)) 
			        {
			            return (ICity)node.data;
			        }
			 }

			 throw new NoSuchElementException("No city with name " + cityName + " found in the graph");
		 }
		  
		  /**
		   * 
		   * @param flightCode - flightCode of the airline
		   * @return flight with the given flightCode
		   */
		 public IFlight getFlight(String flightCode)
		 {
			 for (FlightEdge edge : flights.values())
			 {
			        if (edge.flight.flightCode().equals(flightCode)) 
			        {
			            return edge.flight;
			        }
			 }

			 throw new NoSuchElementException("No flight with code " + flightCode + " found in the graph");
			
		 }

		 /**
		   * This method inserts a node to the graph.
		   * 
		   * @param data - a N object
		   * @return  returns true if the node is inserted, false if it isn't.
		   */
		@Override
		public boolean insertNode(N data) 
		{
			for (CityNode node : nodes.values()) 
			 {
			        if (((ICity)node.data).getCityName().equals(((ICity)data).getCityName())) 
			        {
			            return false;
			        }
			 }
			
		    CityNode node = new CityNode(data);
		    nodes.put(data, node);
		    
		    
		    numberOfCities++;
		    
		    return true;
		}

		/**
		   * This method removes a node from the graph.
		   *
		   * @param data - a N object that has the data of the node to remove.
		   * @return  returns true if the node is removed, false if it isn't.
		   */
		@Override
		public boolean removeNode(N data) 
		{
			
			if (!nodes.containsKey(data)) 
			{
		        return false; 
		    }

		    CityNode nodeToRemove = nodes.get(data);
		    
		    for (FlightEdge edge : nodeToRemove.edgesEntering) 
		    {
		        edge.predecessor.edgesLeaving.remove(edge);
		        numberOfFlights--;
		    }

		    for (FlightEdge edge : nodeToRemove.edgesLeaving) 
		    {
		        edge.successor.edgesEntering.remove(edge);
		        numberOfFlights--;
		    }

		    nodes.remove(data);
		    
		    numberOfCities--;

		    return true;
		}

		/**
		   * This method checks to see if the graph has a node that contains the inputed data.
		   *
		   * @param data - a N object that has the data of the node to find.
		   * @return  returns true if the node is found, false if it isn't.
		   */
		@Override
		public boolean containsNode(N data) 
		{
			return nodes.containsKey(data);
		}

		/**
		   * This method finds the number of nodes in the graph.
		   *
		   * @return the number of nodes in the graph
		   */
		@Override
		public int getNodeCount() 
		{
			return nodes.size();
		}

		/**
		   * This method inserts an edge to the graph.
		   *
		   * @param	   data for the predecessor, data for the successor, and a weight parameter.
		   * @return  returns true if the edge is added, false if it isn't.
		   */
		@Override
		public boolean insertEdge(N pred, N succ, E weight) 
		{
			
			

			if (!nodes.containsKey(pred) || !nodes.containsKey(succ)) 
		    {
		        return false;
		    }
		    
			for (FlightEdge edge : flights.values()) 
		    {
		        if (edge.predecessor.data.equals(pred) && edge.successor.data.equals(succ))
		        {
		            return false;
		        }
		    }
		    
		    CityNode predecessor = nodes.get(pred);
		    CityNode successor = nodes.get(succ);
		    
		    FlightEdge edge2 = new FlightEdge(weight, predecessor, successor);
		    predecessor.edgesLeaving.add(edge2);
		    successor.edgesEntering.add(edge2);
		    
		    flights.put(edge2.flight.flightCode(), edge2);
		    		
		    		
		    numberOfFlights++;
		    
		    return true;
		}

		/**
		   * This method removes an edge from the graph.
		   *
		   * @param		A predecessor data input, and a successor data input.
		   * @return  returns true if the edge is removed, false if it isn't.
		   */
		@Override
		public boolean removeEdge(N pred, N succ) 
		{
		

		    FlightEdge edgeToRemove = null;
		    
		    for (FlightEdge edge : flights.values()) 
		    {
		        if (((ICity)edge.predecessor.data).equals(((ICity)pred)) 
		                && ((ICity)edge.successor.data).equals(((ICity)succ))) 
		        {
		            edgeToRemove = edge;
		            
		            edgeToRemove.predecessor.edgesLeaving.remove(edgeToRemove);
				    edgeToRemove.successor.edgesEntering.remove(edgeToRemove);

				    flights.remove(edgeToRemove.flight.flightCode());

				    numberOfFlights--;

				    return true;
		        }
		    }
		    return false;

		    
		}

		/**
		   * This method checks to see if the edge with the predecessor and successor inputed is in the graph.
		   *
		   * @param A predecessor data input, and a successor data input.
		   * @return  returns true if the edge is found, false if it isn't.
		   */
		@Override
		public boolean containsEdge(N pred, N succ) 
		{
			if (!nodes.containsKey(pred) || !nodes.containsKey(succ)) 
			{
		        return false; 
		    }
			
		    CityNode predecessor = nodes.get(pred);
		    
		    for (FlightEdge edge : predecessor.edgesLeaving) 
		    {
		        if (edge.successor.data.equals(succ)) 
		        {
		            return true;
		        }
		    }
		    return false;
		}

		/**
		   * This method gets the edge with the right predecessor and successor as the input, and throws an exception if it does not exist.
		   *
		   * @param A predecessor data input, and a successor data input.
		   * @return  returns true if the edge is found, false if it isn't.
		   */
		@Override
		public E getEdge(N pred, N succ) 
		{
			CityNode predNode = nodes.get(pred);
			
			if (predNode == null) 
			{
				throw new NoSuchElementException("No such node: " + pred);
			}
			
			for (FlightEdge edge : predNode.edgesLeaving) 
			{
				if (edge.successor.data.equals(succ)) 
				{
					
					return edge.weight;
				}
			}
			
			throw new NoSuchElementException("No such edge: (" + pred + ", " + succ + ")");
		}

		/**
		   * This method gets the number of edges in the graph.
		   *
		   * @param A predecessor data input, and a successor data input.
		   * @return  returns true if the edge is found, false if it isn't.
		   */
		@Override
		public int getEdgeCount() 
		{
			return numberOfFlights;
		}

		/**
		   * This method finds and returns all of the cities that the flight goes through on its path.
		   *
		   * @param A start data input, and a end data input.
		   * @return  returns the list of all the cities that the shortest path goes through. 
		   */
		@Override
		public List<N> shortestPathData(N start, N end) 
		{
			try 
			{
		        LinkedList<N> toReturn = new LinkedList<>();    	        
		        SearchNode node = computeShortestPath(start, end);
		        	
		        if (node == null) 
		        {
		            throw new NoSuchElementException("No path from start to end is found or either " 
		                    + "start or end data does not correspond to a graph node");
		        }    	    
		        	
		        while (node != null) 
		        {
		            toReturn.add(node.node.data);
		            node = node.predecessor;
		        }
		            	
		        for (int i = 0; i < toReturn.size() / 2; i++) 
		        {
		            N temp = toReturn.get(i);
		            toReturn.set(i, toReturn.get(toReturn.size() - 1 - i));
		            toReturn.set(toReturn.size() - 1 - i, temp);
		        }
		        	
		        return toReturn;
		        
		    } 
			
			catch (NoSuchElementException e) 
			{
		        throw new NoSuchElementException("Error finding shortest path: " + e.getMessage());
		    }
		}

		/**
		   * This method finds the cost of the path as a whole.
		   *
		   * @param A start data input, and a end data input.
		   * @return  returns the total cost of all the cities that the shortest path goes through. 
		   */
		@Override
		public double shortestPathCost(N start, N end) 
		{
			 try 
			 {
			        SearchNode shortestPath = computeShortestPath(start, end);
			        double cost = 0.0;

			        while (shortestPath != null && shortestPath.predecessor != null) 
			        {
			            for (FlightEdge edge : shortestPath.predecessor.node.edgesLeaving) 
			            {
			                if (edge.successor != null && edge.successor == shortestPath.node) 
			                {
			                    cost += edge.weight.doubleValue();
			                    break;
			                }
			            }
			            shortestPath = shortestPath.predecessor;
			        }

			        return cost;
			 } 
			 
			 catch (NoSuchElementException e) 
			 {
			        throw new NoSuchElementException("Error finding shortest path cost: " + e.getMessage());
			 }
		}
		
		/**
		   * This class defines a SearchNode that helps go through and keep track of the path.
		   */
		protected class SearchNode implements Comparable<SearchNode> 
		{
	        public CityNode node;
	        public double cost;
	        public SearchNode predecessor;
	        
	        /**
			   * This is the constructor for the Searchnode class. It takes in a CityNode, a cost, and the predecessor.
			   */
	        public SearchNode(CityNode node, double cost, SearchNode predecessor) 
	        {
	            this.node = node;
	            this.cost = cost;
	            this.predecessor = predecessor;
	        }
	        
	        public int compareTo(SearchNode other) 
	        {
	            if( cost > other.cost ) return +1;
	            if( cost < other.cost ) return -1;
	            return 0;
	        }
	    }
		
		/**
		   * This method finds the shortest path between the start city and the end city. 
		   *
		   * @param A start data input, and a end data input.
		   * @return  A searchnode that traces the path from start to end.
		   */
		protected SearchNode computeShortestPath(N start, N end) 
	    {
			
			    if(!containsNode(start) || !containsNode(end))
			    {
			        throw new NoSuchElementException("No path from start to end is found or either "
			                + "start or end data does not correspond to a graph node"); 
			    }

			    Hashtable<ICity, Boolean> visited = new Hashtable<ICity, Boolean>();
			    
			    for (CityNode node : nodes.values())
			    {
			        visited.put((ICity) node.data, false);
			    }

			    PriorityQueue<SearchNode> queue = new PriorityQueue<SearchNode>();
			    queue.add(new SearchNode(nodes.get(start), 0.0, null));

			    Hashtable<ICity, Double> totalCosts = new Hashtable<ICity, Double>();
			    totalCosts.put((ICity) start, 0.0);

			    while (!queue.isEmpty()) 
			    {
			        SearchNode current = queue.remove();
			        ICity currentData = (ICity) current.node.data;

			        visited.put(currentData, true);

			        if (currentData.equals(end)) 
			        {
			            return current;
			        }

			        for (FlightEdge edge : current.node.edgesLeaving) 
			        {
			            ICity successorData = (ICity) edge.successor.data;

			            Double newCost = totalCosts.get(currentData) + edge.weight.doubleValue();

			            if (!totalCosts.containsKey(successorData) || newCost < totalCosts.get(successorData)) 
			            {
			                totalCosts.put(successorData, newCost);
			                queue.add(new SearchNode(edge.successor, newCost, current));
			            }
			        }
			    }

			    throw new NoSuchElementException("No path from start to end is found or either "
			            + "start or end data does not correspond to a graph node"); 
			
	    }

		/**
		   * This method finds and returns all of the cities that the flight goes through on its path when there
		   * is a required edge.
		   *
		   * @param A start data input, and a end data input, as well as a required edge.
		   * @return  returns the list of all the cities that the shortest path that contains the edge goes through. 
		   */
		@Override
		public List<N> shortestPathDataWithRequiredEdge(N start, N end, N requiredEdgeStart, N requiredEdgeEnd) 
	 	{
	 		try 
			{
	 			LinkedList<N> toReturn1 = new LinkedList<>(); 
	 			LinkedList<N> toReturn2 = new LinkedList<>(); 
	 			LinkedList<N> toReturn3 = new LinkedList<>(); 
	 			
		        SearchNode node = computeShortestPath(start, requiredEdgeStart);
		        SearchNode node2 = computeShortestPath(requiredEdgeStart, requiredEdgeEnd);
		        SearchNode node3 = computeShortestPath(requiredEdgeEnd, end);		        
		        	
		        if (node == null || node2 == null || node3 == null) 
		        {
		            throw new NoSuchElementException("No path from start to end is found or either " 
		                    + "start or end data does not correspond to a graph node");
		        }    	    
		        	
		        while (node != null) 
		        {
		        	toReturn1.add(node.node.data);
		            node = node.predecessor;
		        }
		        
		        for (int i = 0; i < toReturn1.size() / 2; i++) 
		        {
		            N temp = toReturn1.get(i);
		            toReturn1.set(i, toReturn1.get(toReturn1.size() - 1 - i));
		            toReturn1.set(toReturn1.size() - 1 - i, temp);
		        }
		        
		        while (node2 != null) 
		        {
		        	if(!toReturn1.contains(node2.node.data))
		        	{
		        		toReturn2.add(node2.node.data);
		        	}            
		            node2 = node2.predecessor;
		        }
		        
		        for (int i = 0; i < toReturn2.size() / 2; i++) 
		        {
		            N temp2 = toReturn2.get(i);
		            toReturn2.set(i, toReturn2.get(toReturn2.size() - 1 - i));
		            toReturn2.set(toReturn2.size() - 1 - i, temp2);
		        }
		        
		        
		        while (node3 != null) 
		        {
		        	if(!toReturn1.contains(node3.node.data) &&  !toReturn2.contains(node3.node.data))
		        	{
		        		toReturn3.add(node3.node.data);
		        	}            
		            node3 = node3.predecessor;
		        }
		        
		        for (int i = 0; i < toReturn3.size() / 2; i++) 
		        {
		            N temp3 = toReturn3.get(i);
		            toReturn3.set(i, toReturn3.get(toReturn3.size() - 1 - i));
		            toReturn3.set(toReturn3.size() - 1 - i, temp3);
		        }
		        
		        LinkedList<N> toReturn = new LinkedList<>(); 
		        
		        toReturn.addAll(toReturn1);
		        toReturn.addAll(toReturn2);
		        toReturn.addAll(toReturn3);
		        
		        return toReturn;
		    } 
			
			catch (NoSuchElementException e) 
			{
		        throw new NoSuchElementException("Error finding shortest path: " + e.getMessage());
		    }
	}
		
		/**
		   * This method finds the cost of the path as a whole when there is a required edge.
		   *
		   * @param A start data input, and a end data input, and a required edge.
		   * @return  returns the total cost of all the cities that the shortest path goes through with the requiredEdge.
		   */
		@Override
		public double shortestPathCostWithRequiredEdge(N start, N end, N requiredEdgeStart,N requiredEdgeEnd) 
		{
			try 
			{
				if(containsEdge(requiredEdgeStart, requiredEdgeEnd))
				{
					double shortestPath1 = shortestPathCost(start, requiredEdgeStart);
					double shortestPath2 = shortestPathCost(requiredEdgeStart, requiredEdgeEnd);
					double shortestPath3 = shortestPathCost(requiredEdgeEnd, end);
					
					
					return shortestPath1 + shortestPath2 + shortestPath3;
				}
				else
				{
					throw new NoSuchElementException("Error finding shortest path cost, no required edge available");
				}
				
		    } 
			catch (NoSuchElementException e) 
			{
				throw new NoSuchElementException("Error finding shortest path cost, no required edge available");
		    }
		}
		  
		

}
