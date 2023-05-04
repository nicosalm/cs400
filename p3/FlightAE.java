public class FlightAE implements IFlight
{
	
	private double flightDuration;
	private ICity sourceCity;
	private ICity destinationCity;
	
	public FlightAE(double flightDuration, ICity sourceCity, ICity destinationCity)
	{
		this.flightDuration = flightDuration;
		this.sourceCity = sourceCity;
		this.destinationCity = destinationCity;
		
	}

	/**
	   * 
	   * @return the source city of the flight(e.g. <i>SpiceJet</i>).
	   */
	  public ICity sourceCity()
	  {
		  return sourceCity;
	  }

	  /**
	   * 
	   * @return the destination city of the flight (e.g. <i>SpiceJet</i>).
	   */
	  public ICity destinationCity()
	  {
		  return destinationCity;
	  }
	  
	  
	  /**
	   *
	   * @return the flightDuration from the airport to the destination (e.g. <i>Delhi</i>).
	   */
	  public double flightDuration()
	  {
		  return flightDuration;
	  }
	  
	  /**
	   * 
	   * @return this returns the flightCode of the flight 
	   */
	  public String flightCode()
	  {
		  return "code";
	  }

}
