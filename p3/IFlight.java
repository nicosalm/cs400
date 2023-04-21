/**
 * Interface for a Flight.
 * The data wrangler (DW) will be responsible for implementing this (named {@code FlightDW}) during role code.
 */
public interface IFlight {
 
  /**
   * 
   * @return the source city of the flight(e.g. <i>SpiceJet</i>).
   */
  ICity sourceCity();

  /**
   * 
   * @return the destination city of the flight (e.g. <i>SpiceJet</i>).
   */
  ICity destinationCity();
  
  
  /**
   *
   * @return the flightDuration from the airport to the destination (e.g. <i>Delhi</i>).
   */
  double flightDuration();

  /**
   * 
   * @return this returns the name of the airline 
   */
  String airline();
  
  /**
   * 
   * @return this returns the flightCode of the flight 
   */
  String flightCode();
  
  /**
   * 
   * @return this returns the departure time of the flight
   */
  String departureTime();
  
}