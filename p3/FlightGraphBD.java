import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Placeholder class. This class should be replaced with a real class.
 * 
 * @author Nico
 */
public class FlightGraphBD implements IFlightGraph<ICity, Double> {

    @Override
    public boolean insertNode(ICity data) {
        // ternary
        return data != null ? true : false;
    }

    @Override
    public boolean removeNode(ICity data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeNode'");
    }

    @Override
    public boolean containsNode(ICity data) {
        if (data.getCityName().equals("Madison") || data.getCityName().equals("Chicago")
                || data.getCityName().equals("New York") || data.getCityName().equals("London")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getNodeCount() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNodeCount'");
    }

    @Override
    public boolean insertEdge(ICity pred, ICity succ, Double weight) {
        if (pred.getCityName().equals("Madison") && succ.getCityName().equals("Chicago")) {
            return true;
        } else if (pred.getCityName().equals("Chicago") && succ.getCityName().equals("New York")) {
            return true;
        } else if (pred.getCityName().equals("New York") && succ.getCityName().equals("London")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeEdge(ICity pred, ICity succ) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeEdge'");
    }

    @Override
    public boolean containsEdge(ICity pred, ICity succ) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'containsEdge'");
    }

    @Override
    public Double getEdge(ICity pred, ICity succ) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEdge'");
    }

    @Override
    public int getEdgeCount() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEdgeCount'");
    }

    @Override
    public List<ICity> shortestPathData(ICity start, ICity end) {
        List<ICity> path = new ArrayList<ICity>();
        // add the following cities to the path: Madison, Chicago, New York, London (in
        // that order)
        path.addAll(Arrays.asList(new CityBD("Madison"), new CityBD("Chicago"), new CityBD("New York"),
                new CityBD("London")));
        return path;
    }

    @Override
    public double shortestPathCost(ICity start, ICity end) {
        return 21.0;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }

    @Override
    public ICity getCity(String cityName) {
        return new CityBD(cityName);
    }

    @Override
    public IFlight getFlight(String flightCode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFlight'");
    }

}
