import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Placeholder class. This class should be replaced with a real class.
 * 
 * @author Nico
 */
public class FlightReaderBD implements IFlightReader {

    @Override
    public List<ICity> readFromFile(String filename) {

        if (filename == null) {
            throw new IllegalArgumentException("Filename cannot be null");
        }

        if (filename.isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be empty");
        }

        if (filename.isBlank()) {
            throw new IllegalArgumentException("Filename cannot be blank");
        }

        if (filename.endsWith(".csv")) {
            throw new IllegalArgumentException("Filename must end with .dot, not .csv");
        }

        if (!filename.endsWith(".dot")) {
            throw new IllegalArgumentException("Filename must end with .dot");
        }
        // create a dummy list to return for now
        List<ICity> cities = new ArrayList<ICity>();
        cities.add(new CityBD("Atlanta"));
        cities.add(new CityBD("Boston"));
        cities.add(new CityBD("Chicago"));

        return cities;
    }

    @Override
    public List<ICity> readDOTLineIntoCity(String cityDOTFmtStr) throws IllegalArgumentException, IOException {
        // we don't use this
        if (cityDOTFmtStr == null) {
            throw new IllegalArgumentException("CityDOTFmtStr cannot be null");
        } else {
            List<ICity> lst = new ArrayList<>();
            lst.add(new CityBD(cityDOTFmtStr));
            return lst;
        }
    }

}
