import java.util.LinkedList;
import java.util.List;

public class CityAE implements ICity 
{
	private String name;
	/**
	   * @return A new CityAE object.
	   */
	public CityAE(String name)
	{
		this.name = name;
	}
	
	
	/**
	   * @return the name of city of airport (e.g. <i>Delhi</i>).
	   */
	  public String getCityName()
	  {
		  return name;
	  }
	  
	  /**
	   * @return a list of all the flight of the airport(e.g. <i>Delhi</i>).
	   */
	  public List<IFlight> getFlightList()
	  {
		  return new LinkedList<IFlight>();
	  }

}
