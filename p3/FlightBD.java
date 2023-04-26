/**
 * Placeholder class. This class should be replaced with a real class.
 * 
 * @author Nico
 */
public class FlightBD implements IFlight {

    private String destinationCityName;
    private double flightDuration;

    public FlightBD(String destinationCityName, double flightDuration) {
        this.destinationCityName = destinationCityName;
        this.flightDuration = flightDuration;
    }

    @Override
    public ICity sourceCity() {
        return new CityBD("Atlanta");
    }

    @Override
    public ICity destinationCity() {
        return new CityBD(destinationCityName);
    }

    @Override
    public double flightDuration() {
        return 2.0;
    }

    @Override
    public String airline() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'airline'");
    }

    @Override
    public String flightCode() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'flightCode'");
    }

    @Override
    public String departureTime() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'departureTime'");
    }

    @Override
    public String toString() {
        return "FlightBD [destinationCityName=" + destinationCityName + ", flightDuration=" + flightDuration + "]";
    }

}
