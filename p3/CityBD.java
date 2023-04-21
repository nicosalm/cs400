import java.util.List;

public class CityBD implements ICity {

    private String sourceCityName;
    private List<IFlight> flightList;

    public CityBD(String sourceCityName) {
        this.sourceCityName = sourceCityName;
        this.flightList = List.of(new FlightBD("destinationCityName", 0.0));
    }

    @Override
    public String getCityName() {
        return "City name";
    }

    @Override
    public List<IFlight> getFlightList() {
        return flightList;
    }

}
