import java.util.List;
public interface IFlightGraph<N, E extends Number> extends GraphADT<N, E> {
  
//public fightGraphROLE() { /* create an empty Graph. */ }
  
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
  
  /**
   * Gets the shortest path with a required edge.
   *
   * @return a list of ICity which goes through two given points (a given flight)
   */
  List<N> shortestPathDataWithRequiredEdge(N start, N end, N requiredEdgeStart, N requiredEdgeEnd);
  
  /**
   * Gets the cost of the shortest path with a required edge.
   *
   * @return the cost of traversing the path which includes the two given points.
   */
  double shortestPathCostWithRequiredEdge(N start, N end, N requiredEdgeStart, N requiredEdgeEnd); 
  
}
