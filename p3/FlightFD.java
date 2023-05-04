public class FlightFD implements IFlight {
    /**
     * @return the source city of the flight(e.g. <i>SpiceJet</i>).
     */
    @Override
    public ICity sourceCity() {
        return null;
    }

    /**
     * @return the destination city of the flight (e.g. <i>SpiceJet</i>).
     */
    @Override
    public ICity destinationCity() {
        return null;
    }

    /**
     * @return the flightDuration from the airport to the destination (e.g. <i>Delhi</i>).
     */
    @Override
    public double flightDuration() {
        return 0;
    }

    /**
     * @return this returns the flightCode of the flight
     */
    @Override
    public String flightCode() {
        return "placeholder_flight_code";
    }
}
