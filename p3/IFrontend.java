/**
 * Interface for the frontend.
 * The frontend developer (FD) will be responsible for implementing this (named {@code FrontendFD}) during role code.
 */
public interface IFrontend { 
    // public FrontendROLE(Scanner input, IBackend backend) { /* ... */ }
    // ^ This is the constructor everyone will need to make in their stub (placeholder) implementation of this interface.

    /**
     * Runs the application.
     */
    void runCommandLoop();

    /**
     * Prompts the user to type in a letter, which should correspond to a command.
     *
     * @return the character corresponding to the user's command
     * @implNote {@link IFrontend#runCommandLoop()} will call this method.
     */
    char mainMenuPrompt();

    /**
     * Prompts the user for a filename and reads it into the backend.
     *
     * @implNote Consider calling {@link IBackend#loadDataFromFile(String)}. The frontend developer will need
     * to catch each exception and print out an appropriate error message to the user if this command fails.
     */
    void loadDataCommand();

    /**
     * Prompts the user to search for a Flight Route (ICity source, ICity destination) into the backend. Information about the Flight Route should be displayed to the user.
     * The exact information that shows up ultimately to the frontend developer to decide.
     * The {@link ICity} interface contains methods about what information can be displayed.
     *
     * @implNote Consider calling {@link IBackend#searchFor(String)}. The frontend developer will need
     * to catch each exception and print out an appropriate error message to the user if this command fails.
     */
    void searchCommand();

    /**
     * Prompts the user to enter a line of input, in CSV format, that represents a Flight Route to add to the catalog.
     *
     * @implNote Consider calling {@link IBackend#addCity(String)}. The frontend developer will need
     * to catch each exception and print out an appropriate error message to the user if this command fails.
     */
    void addCommand();

    /**
     * <p>Displays information about the flight list as a whole. Information about the list should be displayed to the user,
     * but the question of <i>"what information should be shown?"</i> is ultimately up to the backend developer to decide.</p>
     *
     * @implNote Consider making use of {@link IBackend#displayCatalogStats()}.
     */
    void displayFlightStatsCommand();
}