import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface for a class that reads City from, and writes city to, a DOT file.
 * The data wrangler (DW) will be responsible for implementing this (named {@code FlightReaderDW}) during role code.
 */
public class FlightReaderDW implements IFlightReader {

  /**
   * @param filename the name of the {@code .dot} file to read from
   * @return a list of City, serialized (i.e. read) from the file
   * @throws FileNotFoundException if the file path doesn't exist, or points to a directory rather than a regular file
   * @throws IllegalArgumentException if the file is not in DOT format
   * @throws SecurityException if the program doesn't have permission to read the file
   * @throws IOException if an I/O error occurs while reading
   */
    public List<ICity> readFromFile(String filename) throws FileNotFoundException, IllegalArgumentException, SecurityException, IOException {
        // check if file is in dot format 
        if (filename.isEmpty() || filename == null || !filename.contains(".dot")) {
            throw new IllegalArgumentException("File name is empty, null, or invalid.");
        }
        // create new city list
        List<ICity> cityList = new ArrayList<>();
        // create new city names list
        List<String> cityNames = new ArrayList<>();
        // get absolute file path
        File file = new File(filename);
        String absolutePath = file.getAbsolutePath();
        // create new BufferedReader object with file name input
        try (BufferedReader reader = new BufferedReader(new FileReader(absolutePath))) {
          // Skip the first line
          reader.readLine();
          String line;
          while ((line = reader.readLine()) != null) {
            if (line.equals("}")) {
                // skip the last line
                break;
            }
            // read each line using the readDOTLineIntoCity function
            List<ICity> city = readDOTLineIntoCity(line);
            ICity source = city.get(0);
            ICity destination = city.get(1);
            
            // check if source city name doesn't already exists in the city names list
            // if (!cityNames.contains(source.getCityName())) {
                // adds the city to the city list array 
                cityList.add(source);
                // adds the city to the city names array 
                // cityNames.add(source.getCityName());
            // }

            // check if destination city name doesn't already exists in the city names list
            // if (!cityNames.contains(destination.getCityName())) {
                // adds the city to the city list array 
                cityList.add(destination);
                // adds the city to the city names array 
                // cityNames.add(destination.getCityName());
            // }
          }
        } catch(FileNotFoundException e) { // catch specified exceptions
            throw new FileNotFoundException("File path doesn't exist or is inaccessible");
        } catch(SecurityException e) {
            throw new SecurityException("Program doesn't have permission to read the file");
        } catch(IOException e) {
            throw new IOException("An I/O error occured while reading");
        }
        // return cityList
        return cityList;
    }

  /**
   * Converts a node of DOT into an {@link ICity} object, if it is in the correct form.
   * @param cityDOTFmtStr the DOT line to serialize into a City
   * @return List<ICity> represented by {@code cityDOTFmtStr}.
   * @throws IllegalArgumentException if {@code cityDOTFmtStr} is malformed (in a bad format that cannot be parsed)
   * @throws IOException if an I/O error occurs while reading
   */
    public List<ICity> readDOTLineIntoCity(String cityDOTFmtStr) throws IllegalArgumentException, IOException {
        // DOT Format "<SOURCE>-><DESTINATION>[label=<DURATION>-<FLIGHT CODE>]"

        // convert dot format line into ICity object
        String[] parts = cityDOTFmtStr.split("->");
        // check if invalid format
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid DOT format: " + cityDOTFmtStr);
        }
        // get source city
        String sourceCity = parts[0].trim();
        // get destination city
        String destinationCity = parts[1].substring(0, parts[1].indexOf("[")).trim();
        // get the label information
        String label = cityDOTFmtStr.substring(cityDOTFmtStr.indexOf("\"") + 1, cityDOTFmtStr.lastIndexOf("\""));
        // separate the label parts
        String[] labelParts = label.split("-");
        if (labelParts.length != 2) {
            throw new IllegalArgumentException("Invalid DOT format: " + cityDOTFmtStr);
        }
        // get the duration
        double duration = Double.parseDouble(labelParts[0]);

        // create a new array of IFlight objects
        List<IFlight> flights = new ArrayList<>();

        // create new list for source and desination cities
        List<ICity> cities = new ArrayList<>();

        // create destinationDW object and add to cities list
        CityDW destinationDW = new CityDW(destinationCity, new ArrayList<IFlight>());
        cities.add(destinationDW);

        // create sourceDW object and add to cities list
        CityDW sourceDW = new CityDW(sourceCity, flights);
        flights.add(new FlightDW(duration, sourceDW, destinationDW));
        cities.add(0, sourceDW);

        // return a CityDW object 
        return cities;
    }

}
