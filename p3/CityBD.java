import java.util.List;

/**
 * Placeholder class. This class should be replaced with a real class.
 * 
 * @author Nico
 */
public class CityBD implements ICity {

    private String sourceCityName;
    private List<IFlight> flightList;

    public CityBD(String sourceCityName) {
        this.sourceCityName = sourceCityName;
        if (sourceCityName == null) {
            throw new IllegalArgumentException("City name cannot be null");
        }
        this.flightList = List.of(new FlightBD("Chicago", 3.0), new FlightBD("Houston", 6.0));
    }

    @Override
    public String getCityName() {
        return sourceCityName;
    }

    @Override
    public List<IFlight> getFlightList() {
        return flightList;
    }

}
