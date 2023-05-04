import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Interface for the program backend.
 * The backend developer (BD) will be responsible for implementing this (named {@code BackendBD}) during role code.
 */
public interface IBackend {

    // public BackendROLE(IFightGraph<ICity> graph, IFlightReader reader);
    // ^ This is the constructor everyone will need to make in their stub (placeholder) implementation of this interface.

    /**
     * Calls {@link IFlightReader#readFromFile(String)} to load the Cities into the backend.
     *
     * @param filename the name of the {@code .csv} file to read from
     * @throws FileNotFoundException    if the file path doesn't exist, or points to a directory rather than a regular file
     * @throws IllegalArgumentException if the file is not in CSV format
     * @throws SecurityException        if the program doesn't have permission to read the file
     * @throws IOException              if an I/O error occurs while reading
     * @see IFlightReader#readFromFile(String)
     */
    void loadDataFromFile(String filename) throws FileNotFoundException, IllegalArgumentException, SecurityException, IOException;

    /**
     * Calls {@link IFlightGraph#getEdge(Object, Object)} to search for a flight (an edge) between two cities in the backend's flightGraph.
     *
     *  @return flight - the flight between two cities in the backend's flightGraph
     * @throws NoSuchElementException if no element matches the search key
     * @see IFlightGraph#getEdge(Object, Object), IFlightGraph#containsEdge(NodeType pred, NodeType succ)
     */
    IFlight searchForFlight(String sourceCityName, String destinationCityName) throws NoSuchElementException;
  
    /**
     * Statistics string for the Frontend to parse and display.
     * 
     * @param cityName, the city to get statistics for.
     * @throws NoSuchElementException if the provided ICity object does not exist.
     * 
     * @return a String of relevant statistics array about a city for the Frontend to display. 
     */
    String getStatisticsString(String cityName) throws NoSuchElementException;
  
    /**
     * Adds a city.
     *
     * @param cityStr the name of the City.
     * @throws IllegalArgumentException if the City with name {@code cityName} already exists in the FlightGraph
     * or {@code cityDOTFmtStr} is malformed
     * @throws IOException   if an I/O error occurs while reading
     * @implNote Consider using {@link IFlightReader#readDOTLineIntoCity(String)} to create the City instance
     * and then add it into the FlightGraph.
     * @see IFlightReader#readDOTLineIntoCity(String)
     * @see IFlightGraph#insertNode(Object)
     */
    void addCity(String cityStr) throws IllegalArgumentException, IOException;

    void addFlight(String sourceCity, String destinationCity, double flightDuration);

    /**
     * Calls FlightGraph#getShortestPathData which invokes Dijkstra's shortest path algorithm to determine the shortest path.
     * Calls {@link IFlightGraph#getCity(String)} to get the actual city object first
     * @return an List of City nodes representing the shortest path.
     */
    List<ICity> getShortestPathData(String sourceCityName, String destinationCityName);
  
    /**
     * Calls the FlightGraph's getShortestPathCost which returns the cost of the shortest path as calculated in FlightGraph#shortestPathData
     * Calls {@link IFlightGraph#getCity(String)} to get the actual city object first
     * @return double cost, the cost of the shortest flight sequence.
     */
    double getShortestPathCost(String sourceCityName, String destinationCityName);
    
    /**
     * Calls FlightGraph#shortestPathDataWithRequiredEdge which invokes Dijkstra's shortest path algorithm to determine the shortest path. The added functionality is it must pass through two gives points.
     * Calls {@link IFlightGraph#getCity(String)} to get the actual city object first
     * @return an List of City nodes representing the shortest path including point A and point B. 
     */
    List<ICity> getShortestPathDataWithRequiredEdge(String sourceCityName, String destinationCityName, String pointA, String pointB);
    
    /**
     * Calls the FlightGraph's getShortestPathDataWIthRequiredEdge which returns the cost of the shortest path which pasts between two points (two cities) pointA and pointB.
     * @return double cost, the cost of the shortest flight sequence
     */
    double getShortestPathCostWithRequiredEdge(String sourceCityName, String destinationCityName, String pointA, String pointB);
}
