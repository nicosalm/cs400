
public class FlightBD implements IFlight {

    private String destinationCityName;
    private double flightDuration;

    public FlightBD(String destinationCityName, double flightDuration) {
        this.destinationCityName = destinationCityName;
        this.flightDuration = flightDuration;
    }

    @Override
    public ICity sourceCity() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sourceCity'");
    }

    @Override
    public ICity destinationCity() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'destinationCity'");
    }

    @Override
    public double flightDuration() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'flightDuration'");
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

}
