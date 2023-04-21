public interface IFlightGraph<N, E extends Number> extends GraphADT<N, E> {
  
//public fightGrpahROLE() { /* create an empty Graph. */ }
  
  // ^ These are the constructors everyone will need to make in their stub (placeholder) implementation of this interface.
  // Depends on how many code is provided by the stuff, we might consider adding more methods 
  
  /**
   * Clears the Graph.
   */
  void clear();
  
  /**
   * This method returns the city with the given name
   * 
   * @param cityName - name of the city
   * @return city with the given name 
   */
  ICity getCity(String cityName);
  
  /**
   * 
   * @param airlineName - name of the airline
   * @return flight with the given fightCode
   */
  IFlight getFlight(String flightCode);
  
}
