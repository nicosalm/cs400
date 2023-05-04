import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Frontend of the command-line application.
 * Calling {@link FrontendFD#runCommandLoop()} will run the application,
 * sending commands to the backend depending on user input.
 */
public class FrontendFD implements IFrontend {

    private final Scanner userInput;
    private final IBackend backend;

    public FrontendFD(Scanner userInput, IBackend backend) {
        this.userInput = userInput;
        this.backend = backend;
    }

    /**
     * Runs the application.
     */
    @Override
    public void runCommandLoop() {
        System.out.println("=== Welcome to Flight Planner ===");
        char command = '\0';
        while (command != 'Q') { // main loop continues until user chooses to quit
            command = this.mainMenuPrompt();
            switch (command) {
                case 'L':
                    loadDataCommand();
                    break;
                case 'S':
                    searchCommand();
                    break;
                case 'R':
                    searchRequiredEdgeCommand();
                    break;
                case 'A':
                    addCommand();
                    break;
                case 'T':
                    addCityCommand();
                    break;
                case 'C':
                    displayCityInformationCommand();
                    break;
                case 'Q':
                    break;
                default:
                    System.out.println("Unknown command. To run a command, type and enter one of the letters indicated in square brackets.");
                    break;
            }
        }
    }

    /**
     * Prompts the user to type in a letter, which should correspond to a command.
     *
     * @return the character corresponding to the user's command
     * @implNote {@link IFrontend#runCommandLoop()} will call this method.
     */
    @Override
    public char mainMenuPrompt() {
        System.out.println();
        System.out.println("What would you like to do?");
        System.out.println("[L] Load flight list from DOT");
        System.out.println("[S] Shortest route between two cities");
        System.out.println("[R] Shortest route between two cities w/ required edge");
        System.out.println("[A] Add new Flight");
        System.out.println("[T] Add new City");
        System.out.println("[C] Display info about a city's outbound flights");
        System.out.println("[Q] Quit");

        System.out.print("→ Your command: ");
        String input = userInput.nextLine().trim(); // trim/read user's choice

        if (input.length() == 0) return '\0'; // return null character if user's choice is blank
        System.out.println();

        // otherwise, return an uppercase version of the first character in input
        return Character.toUpperCase(input.charAt(0));
    }

    /**
     * Prompts the user for a filename and reads it into the backend.
     *
     * @implNote Consider calling {@link IBackend#loadDataFromFile(String)}. The frontend developer will need
     * to catch each exception and print out an appropriate error message to the user if this command fails.
     */
    @Override
    public void loadDataCommand() {
        System.out.print("Enter path: ");
        String path = userInput.nextLine().trim();
        try {
            backend.loadDataFromFile(path);
        } catch (FileNotFoundException e) {
            System.out.println("Error: The provided file path does not exist.");
            return;
        } catch(IllegalArgumentException e) {
            System.out.println("Error: The provided file is not in DOT format.");
            return;
        } catch(SecurityException e) {
            System.out.println("Error: The program doesn't have permission to read the provided file.");
            return;
        } catch(IOException e) {
            System.out.println("Error: An internal I/O error occurred while reading. Please try again.");
            return;
        }

        System.out.println("DOT loaded!");
    }

    /**
     * Prompts the user to search for a Flight Route (ICity source, ICity destination) into the backend. Information about the Flight Route should be displayed to the user.
     * The exact information that shows up ultimately to the frontend developer to decide.
     * The {@link ICity} interface contains methods about what information can be displayed.
     *
     * @implNote Consider calling {@link IBackend#searchForFlight(String, String)}. The frontend developer will need
     * to catch each exception and print out an appropriate error message to the user if this command fails.
     */
    @Override
    public void searchCommand() {
        System.out.print("Enter source city: ");
        var sourceStr = userInput.nextLine().trim();
        System.out.print("Enter destination city: ");
        var destStr = userInput.nextLine().trim();

        try {
            // var flight = backend.searchForFlight(sourceStr, destStr);
            var route = backend.getShortestPathData(sourceStr, destStr);
            var routeStr = route.stream().map(ICity::getCityName).collect(Collectors.joining(" -> "));

            System.out.println();
            System.out.println("The shortest route is: " + routeStr);
            // System.out.println("> flight: " + flight.flightCode());
            // System.out.println("> duration: " + flight.flightDuration());
            System.out.println();
        } catch(NoSuchElementException e) {
            System.out.println("Error: No flight matches the search key.");
        }
    }

    public void searchRequiredEdgeCommand() {
        System.out.print("Enter source city: ");
        var sourceStr = userInput.nextLine().trim();

        System.out.print("Enter destination city: ");
        var destStr = userInput.nextLine().trim();

        System.out.print("Enter the source city in the required flight: ");
        var sourceReqStr = userInput.nextLine().trim();

        System.out.print("Enter the destination city in the required flight: ");
        var destReqStr = userInput.nextLine().trim();

        try {
            var route = backend.getShortestPathDataWithRequiredEdge(sourceStr, destStr, sourceReqStr, destReqStr);
            var routeStr = route.stream().map(ICity::getCityName).collect(Collectors.joining(" -> "));

            System.out.println();
            System.out.println("The shortest route is: " + routeStr);
            System.out.println();
        } catch(NoSuchElementException e) {
            System.out.println("Error: No flight matches the search key.");
        }
    }

    /**
     * Prompts the user to enter a line of input, in DOT format, that represents a Flight Route to add to the catalog.
     *
     * @implNote Consider calling {@link IBackend#addCity(String)}. The frontend developer will need
     * to catch each exception and print out an appropriate error message to the user if this command fails.
     */
    @Override
    public void addCommand() {

        System.out.print("Enter flight's starting city: ");
        var srcCityStr = userInput.nextLine().trim();

        System.out.print("Enter flight's destination city: ");
        var destCityStr = userInput.nextLine().trim();

        System.out.print("Enter flight duration: ");
        var durationStr = userInput.nextLine().trim();

        try {
            backend.addFlight(srcCityStr, destCityStr, Double.parseDouble(durationStr));
            System.out.println("Flight added!");
        } catch(NumberFormatException e) {
            System.out.println("Error: Could not parse that number.");
        } catch(IllegalArgumentException iae) {
            System.out.println("Error: Flight already exists or has invalid source, destination, or duration arguments.");
        }
    }

    public void addCityCommand() {
        System.out.print("Enter city name: ");
        var cityStr = userInput.nextLine().trim();

        try {
            backend.addCity(cityStr);
            System.out.println("City added!");
        } catch(IllegalArgumentException iae) {
            System.out.println("Error: City already exists.");
        } catch(IOException e) {
            System.out.println("Error: An internal I/O error occurred while reading. Please try again.");
        }
    }

    /**
     * <p>Displays information about the flight list as a whole. Information about the list should be displayed to the user,
     * but the question of <i>"what information should be shown?"</i> is ultimately up to the backend developer to decide.</p>
     *
     * @implNote Consider making use of {@link IBackend#getStatisticsString(String)} ()}.
     */
    @Override
    public void displayCityInformationCommand() {
        System.out.print("Enter a city name to retrieve stats for: ");
        var cityName = userInput.nextLine().trim();

        try {
            String statsStr = backend.getStatisticsString(cityName);
            System.out.println(statsStr);
        } catch (NoSuchElementException e) {
            System.out.println("Error: No city with that name was found.");
        }
    }
}
