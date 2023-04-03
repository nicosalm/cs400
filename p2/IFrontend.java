/**
 * Interface for the frontend.
 * The frontend developer (FD) will be responsible for implementing this (named
 * {@code FrontendFD}) during role code.
 */
public interface IFrontend {
    // public FrontendROLE(Scanner input, IBackend backend) { /* ... */ }
    // ^ This is the constructor everyone will need to make in their stub
    // (placeholder) implementation of this interface.

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
     * @implNote Consider calling {@link IBackend#loadDataFromFile(String)}. The
     *           frontend developer will need
     *           to catch each exception and print out an appropriate error message
     *           to the user if this command fails.
     */
    void loadDataCommand();

    /**
     * <p>
     * Prompts the user to search for a Pokemon into the backend. Information about
     * the Pokemon should be displayed to the user, but
     * the question of <i>"what information should be shown?"</i> is ultimately up
     * to the frontend developer to decide.
     * </p>
     * <p>
     * The {@link IPokemon} interface contains methods about what information can be
     * displayed.
     * </p>
     *
     * @implNote Consider calling {@link IBackend#searchFor(String)}. The frontend
     *           developer will need
     *           to catch each exception and print out an appropriate error message
     *           to the user if this command fails.
     */
    void searchCommand();

    /**
     * Prompts the user to enter a line of input, in CSV format, that represents a
     * Pokemon to add to the catalog.
     *
     * @implNote Consider calling {@link IBackend#addPokemon(String)}. The frontend
     *           developer will need
     *           to catch each exception and print out an appropriate error message
     *           to the user if this command fails.
     */
    void addCommand();

    /**
     * Prompts the user to enter the name of the Pokemon they wish to remove from
     * the catalog.
     *
     * @implNote Consider calling {@link IBackend#removePokemon(String)}. The
     *           frontend developer will need
     *           to catch each exception and print out an appropriate error message
     *           to the user if this command fails.
     */
    void deleteCommand();

    /**
     * Prompts the user to enter a filename, and attempts to write all Pokemon
     * contained by this catalog into that file.
     * 
     * @implNote Consider calling {@link IBackend#saveDataToFile(String)}. The
     *           frontend developer will need
     *           to catch each exception and print out an appropriate error message
     *           to the user if this command fails.
     */
    void saveDataCommand();

    /**
     * <p>
     * Displays information about the catalog as a whole. Information about the
     * catalog should be displayed to the user,
     * but the question of <i>"what information should be shown?"</i> is ultimately
     * up to the backend developer to decide.
     * </p>
     *
     * @implNote Consider making use of {@link IBackend#displayCatalogStats()}.
     */
    void displayCatalogStatsCommand();
}
