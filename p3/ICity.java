import java.util.List;

/**
 * Interface for a destination city.
 * The data wrangler (DW) will be responsible for implementing this (named
 * {@code AirportDW}) during role code.
 */
public interface ICity {

  /**
   * @return the name of city of airport (e.g. <i>Delhi</i>).
   */
  String getCityName();

  /**
   * @return a list of all the flight of the airport(e.g. <i>Delhi</i>).
   */
  List<IFlight> getFlightList();

}
