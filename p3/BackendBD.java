import java.io.FileNotFoundException;
import java.io.IOException;
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
        if (!graph.containsNode(new CityBD(cityName))) {
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

    /**
     * Adds a city given by a valid DOT line.
     *
     * @param cityDOTFmtStr the DOT line to serialize into a City.
     * @throws IllegalArgumentException if the City with name {@code cityName}
     *                                  already exists in the FlightGraph
     *                                  or {@code cityDOTFmtStr} is malformed
     * @throws IOException              if an I/O error occurs while reading
     * @implNote Consider using
     *           {@link IFlightReader#readCsvLineIntoCity(String cityCsvFmtStr)} to
     *           create the City instance
     *           and then add it into the FlightGraph.
     * @see IFlightReader#readDOTLineIntoCity(String)
     * @see IFlightGraph#insertNode(Comparable)
     */
    @Override
    public void addCity(String cityDOTFmtStr) throws IllegalArgumentException, IOException {
        if (graph.containsNode(reader.readDOTLineIntoCity(cityDOTFmtStr))) {
            throw new IllegalArgumentException("The city already exists.");
        }
        // add the city to the graph if it doesn't already exist
        graph.insertNode(reader.readDOTLineIntoCity(cityDOTFmtStr));
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

}
