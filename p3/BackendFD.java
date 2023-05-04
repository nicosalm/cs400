import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BackendFD implements IBackend {

    public static final String PLACEHOLDER_STATS_STRING = "placeholder_stats_string";
    public static final IFlight PLACEHOLDER_FLIGHT = new FlightFD();

    // Uses default constructor.
    // For the purposes of testing FrontendFD, there is no need for a graph or flight reader.

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
    @Override
    public void loadDataFromFile(String filename) throws FileNotFoundException, IllegalArgumentException, SecurityException, IOException {
        if(filename.equals("FileNotFoundException")) throw new FileNotFoundException();
        if(filename.equals("IllegalArgumentException")) throw new IllegalArgumentException();
        if(filename.equals("SecurityException")) throw new SecurityException();
        if(filename.equals("IOException")) throw new IOException();
        // ^ some placeholders so we can test error reports.

        // Else, this method assumes a successful backend call. Return.
    }

    /**
     * Calls {@link IFlightGraph#getEdge(Object, Object)} to search for a flight (an edge) between two cities in the backend's flightGraph.
     *
     * @param source
     * @param dest
     * @return flight - the flight between two cities in the backend's flightGraph
     * @throws NoSuchElementException if no element matches the search key
     * @see IFlightGraph#getEdge(Object, Object), IFlightGraph#containsEdge(NodeType pred, NodeType succ)
     */
    @Override
    public IFlight searchForFlight(String source, String dest) throws NoSuchElementException {
        if(source.equals("NoSuchElementException") || dest.equals("NoSuchElementException")) throw new NoSuchElementException();
        // ^ some placeholders so we can test error reports.

        // Else, this method assumes a successful backend call. Return.
        return PLACEHOLDER_FLIGHT;
    }

    /**
     * Statistics string for the Frontend to parse and display.
     *
     * @param cityStr
     * @throws NoSuchElementException if the provided ICity object does not exist.
     * @return a comma separated String of relevant statistics array about a city for the Frontend to display.
     */
    @Override
    public String getStatisticsString(String cityStr) throws NoSuchElementException {
        if(cityStr.equals("NoSuchElementException")) throw new NoSuchElementException();
        // ^ some placeholders so we can test error reports.

        // Else, this method assumes a successful backend call. Return.
        return PLACEHOLDER_STATS_STRING;
    }

    /**
     * Adds a city given by a valid DOT line.
     *
     * @param cityDOTFmtStr the DOT line to serialize into a City.
     * @throws IllegalArgumentException if the City with name {@code cityName} already exists in the FlightGraph
     *                                  or {@code cityDOTFmtStr} is malformed
     * @throws IOException              if an I/O error occurs while reading
     * @implNote Consider using {@link IFlightReader#readDOTLineIntoCity(String)} to create the City instance
     * and then add it into the FlightGraph.
     * @see IFlightReader#readDOTLineIntoCity(String)
     * @see IFlightGraph#insertNode(Object)
     */
    @Override
    public void addCity(String cityDOTFmtStr) throws IllegalArgumentException, IOException {
        if(cityDOTFmtStr.equals("IllegalArgumentException")) throw new IllegalArgumentException();
        if(cityDOTFmtStr.equals("IOException")) throw new IOException();
        // ^ some placeholders so we can test error reports.

        // Else, this method assumes a successful backend call. Return.
    }

    @Override
    public void addFlight(String sourceCity, String destinationCity, double flightDuration) {

    }

    /**
     * Calls FlightGraph#getShortestPathData which invokes Dijkstra's shortest path algorithm to determine the shortest path.
     * Calls {@link IFlightGraph#getCity(String)} to get the actual city object first
     *
     * @param sourceCityName
     * @param destinationCityName
     * @return an List of City nodes representing the shortest path.
     */
    @Override
    public List<ICity> getShortestPathData(String sourceCityName, String destinationCityName) {
        return new ArrayList<>();
    }

    /**
     * Calls the FlightGraph's getShortestPathCost which returns the cost of the shortest path as calculated in FlightGraph#shortestPathData
     * Calls {@link IFlightGraph#getCity(String)} to get the actual city object first
     *
     * @param sourceCityName
     * @param destinationCityName
     * @return doube cost, the cost of the shortest flight sequence.
     */
    @Override
    public double getShortestPathCost(String sourceCityName, String destinationCityName) {
        return 0;
    }

    /**
     * Calls FlightGraph#shortestPathDataWithRequiredEdge which invokes Dijkstra's
     * shortest path algorithm to determine the shortest path. The added
     * functionality is it must pass through two gives points.
     * Calls {@link IFlightGraph#getCity(String)} to get the actual city object
     * first
     *
     * @param sourceCityName
     * @param destinationCityName
     * @param pointA
     * @param pointB
     * @return an List of City nodes representing the shortest path including point
     * A and point B.
     */
    @Override
    public List<ICity> getShortestPathDataWithRequiredEdge(String sourceCityName, String destinationCityName, String pointA, String pointB) {
        return new ArrayList<>();
    }

    /**
     * Calls the FlightGraph's getShortestPathDataWIthRequiredEdge which returns the
     * cost of the shortest path which pasts between two points (two cities) pointA
     * and pointB.
     *
     * @param sourceCityName
     * @param destinationCityName
     * @param pointA
     * @param pointB
     * @return double cost, the cost of the shortest flight sequence
     */
    @Override
    public double getShortestPathCostWithRequiredEdge(String sourceCityName, String destinationCityName, String pointA, String pointB) {
        return 0;
    }
}
