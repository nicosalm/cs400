import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class implements the {@link IBackend} interface. It uses a FlightGraph
 * to store the cities and flights, and a FlightReader to read in the data from
 * a file. It also implements the methods to add a city, search for a flight,
 * and get the shortest path between two cities.
 * 
 * @author Nico
 */
public class BackendBD implements IBackend {

    private IFlightGraph<ICity, Double> graph;
    private IFlightReader reader;

    public BackendBD(IFlightGraph<ICity, Double> graph, IFlightReader reader) {
        this.graph = graph;
        this.reader = reader;
    }

    /**
     * Calls {@link IFlightReader#readFromFile(String)} to load the Cities into the
     * backend.
     *
     * @param filename the name of the {@code .csv} file to read from
     * @throws FileNotFoundException    if the file path doesn't exist, or points to
     *                                  a directory rather than a regular file
     * @throws IllegalArgumentException if the file is not in DOT format
     * @throws SecurityException        if the program doesn't have permission to
     *                                  read the file
     * @throws IOException              if an I/O error occurs while reading
     * @see IFlightReader#readFromFile(String)
     */
    @Override
    public void loadDataFromFile(String filename)
            throws FileNotFoundException, IllegalArgumentException, SecurityException, IOException {

        // for each city in the list of cities, add it to the graph
        List<ICity> cities = reader.readFromFile(filename);
        for (ICity city : cities) {
            graph.insertNode(city);
        }

        // for each city in the list of cities, add its flights to the graph
        for (ICity city : cities) {
            for (IFlight flight : city.getFlightList()) {
                graph.insertEdge(city, flight.destinationCity(), flight.flightDuration());
            }
        }
    }

    /**
     * Calls {@link IFlightGraph#getEdge(String)} to search for a flight (an edge)
     * between two cities in the backend's flightGraph.
     *
     * @return flight - the flight between two cities in the backend's flightGraph
     * @throws NoSuchElementException if no element matches the search key
     * @see IFlightGraph#getEdge(NodeType pred, NodeType succ),
     *      IFlightGraph#containsEdge(NodeType pred, NodeType succ)
     */
    @Override
    public IFlight searchForFlight(String sourceCityName, String destinationCityName) throws NoSuchElementException {

        // make the input strings into ICity objects from the graph
        ICity sourceCity = graph.getCity(sourceCityName);
        ICity destinationCity = graph.getCity(destinationCityName);

        // for each flight in the source city, check if it goes to the destination city;
        // return the earliest flight if there are multiple
        for (IFlight flight : sourceCity.getFlightList()) {
            if (flight.destinationCity().getCityName().equals(destinationCity.getCityName())) {
                return flight;
            }
        }

        // if no flight is found, throw an exception
        throw new NoSuchElementException("No flight between " + sourceCityName + " and " + destinationCityName);
    }

    /**
     * Statistics string for the Frontend to parse and display.
     * 
     * @param ICity, the city to get statistics for.
     * @throws NoSuchElementException if the provided ICity object does not exist.
     * 
     * @return a String of relevant statistics array about a city for the Frontend
     *         to display.
     */
    @Override
    public String getStatisticsString(String cityName) throws NoSuchElementException {

        // check to see if the city exists
        if (!graph.containsNode(new CityDW(cityName, new ArrayList<>()))) {
            throw new NoSuchElementException("The city " + cityName + " does not exist.");
        }

        // get the city
        ICity city = graph.getCity(cityName);

        StringBuilder sb = new StringBuilder();

        // add the city name
        sb.append("Statistics for " + cityName + ":\n");

        // get the number of flights
        sb.append("Number of flights: " + city.getFlightList().size() + "\n");
        sb.append("Flights:\n");

        // get the flights and add them to the string
        city.getFlightList().forEach(flight -> {
            sb.append(flight.toString() + "\n");
        });

        return sb.toString();
    }

    @Override
    public void addCity(String cityStr) {
        graph.insertNode(new CityDW(cityStr, new ArrayList<>()));
    }

    /**
     * Adds a flight between two cities. If the cities do not exist in the graph, it
     * adds them.
     * 
     * @param sourceCity
     * @param destinationCity
     * @param flightDuration
     */
    public void addFlight(String sourceCity, String destinationCity, double flightDuration) {

        // check for bad things
        if (sourceCity == null || destinationCity == null) {
            throw new IllegalArgumentException("The source and destination cities cannot be null.");
        }

        if (flightDuration < 0) {
            throw new IllegalArgumentException("The flight duration cannot be negative.");
        }

        if (sourceCity.equals(destinationCity)) {
            throw new IllegalArgumentException("The source and destination cities cannot be the same.");
        }

        if (graph.containsEdge(graph.getCity(sourceCity), graph.getCity(destinationCity))) {
            throw new IllegalArgumentException("The flight already exists.");
        }

        ICity source = graph.getCity(sourceCity);
        ICity destination = graph.getCity(destinationCity);

        // check to see if the cities exist in the graph, if not add them
        if (!graph.containsNode(source)) {
            graph.insertNode(source);
        }
        if (!graph.containsNode(destination)) {
            graph.insertNode(destination);
        }

        // add the flight to the source city
        graph.insertEdge(source, destination, flightDuration);
    }

    /**
     * Calls FlightGraph#getShortestPathData which invokes Dijkstra's shortest path
     * algorithm to determine the shortest path.
     * Calls {@link IFlightGraph#getCity(String)} to get the actual city object
     * first
     * 
     * @return an List of City nodes representing the shortest path.
     */
    @Override
    public List<ICity> getShortestPathData(String sourceCityName, String destinationCityName) {
        return graph.shortestPathData(graph.getCity(sourceCityName), graph.getCity(destinationCityName));
    }

    /**
     * Calls the FlightGraph's getShortestPathCost which returns the cost of the
     * shortest path as calculated in FlightGraph#shortestPathData
     * Calls {@link IFlightGraph#getCity(String)} to get the actual city object
     * first
     * 
     * @return doube cost, the cost of the shortest flight sequence.
     */
    @Override
    public double getShortestPathCost(String sourceCityName, String destinationCityName) {
        return graph.shortestPathCost(graph.getCity(sourceCityName), graph.getCity(destinationCityName));
    }

    /**
     * Calls FlightGraph#getShortestPathDataWithRequiredEdge, additional
     * functionality added by the AE.
     */
    @Override
    public List<ICity> getShortestPathDataWithRequiredEdge(String sourceCityName, String destinationCityName,
            String pointA, String pointB) {
        return graph.shortestPathDataWithRequiredEdge(graph.getCity(sourceCityName), graph.getCity(destinationCityName),
                graph.getCity(pointA), graph.getCity(pointB));
    }

    /**
     * Calls FlightGraph#getShortestPathCostWithRequiredEdge, additional
     * functionality added by the AE.
     */
    @Override
    public double getShortestPathCostWithRequiredEdge(String sourceCityName, String destinationCityName, String pointA,
            String pointB) {
        return graph.shortestPathCostWithRequiredEdge(graph.getCity(sourceCityName), graph.getCity(destinationCityName),
                graph.getCity(pointA), graph.getCity(pointB));
    }

}
