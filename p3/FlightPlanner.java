import java.util.Scanner;

public class FlightPlanner {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var graph = new FlightGraphAE<ICity, Double>();
        var reader = new FlightReaderDW();
        var backend = new BackendBD(graph, reader);
        var frontend = new FrontendFD(scanner, backend);

        frontend.runCommandLoop();
    }
}
