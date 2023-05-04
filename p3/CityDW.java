import java.util.List;

/**
 * Interface for a destination city.
 * The data wrangler (DW) will be responsible for implementing this (named {@code CityDW}) during role code.
 */
public class CityDW implements ICity {
    private final String name; 
    private final List<IFlight> flightList;

    public CityDW(String name, List<IFlight> flightList) {
        this.name = name;
        this.flightList = flightList;
    }

    /**
     * @return the name of city of airport (e.g. <i>Delhi</i>).
     */
    public String getCityName() {
        return this.name;
    }
    
    /**
     * @return a list of all the flight of the airport(e.g. <i>Delhi</i>).
     */
    public List<IFlight> getFlightList() {
        return this.flightList;
    }
    
  }