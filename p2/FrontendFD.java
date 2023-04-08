import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Interface for the frontend.
 * The frontend developer (FD) will be responsible for implementing this (named {@code FrontendFD}) during role code.
 */
public class FrontendFD implements IFrontend {
    public Scanner userInput;
    public IBackend backend;

    public FrontendFD(Scanner userInput, IBackend backend) {
        this.userInput = userInput;
        this.backend = backend;
    }

    // ^ This is the constructor everyone will need to make in their stub (placeholder) implementation of this interface.

    /**
     * Runs the application.
     */
    public void runCommandLoop() {
        System.out.println("=== Welcome to Pokeylog™, Trainer! ===");
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
                case 'A':
                    addCommand();
                    break;
                case 'D': 
                    deleteCommand();
                    break;
                case 'E': 
                    saveDataCommand();
                    break;
                case 'C': 
                    displayCatalogStatsCommand();
                    break;
                case 'Q': 
                    break;
                default:
                    System.out.println("Command not recognized. Please type one of the letters presented within []s to identify the command you would like to choose.");
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
    public char mainMenuPrompt() {
        // display menu of choices
        System.out.println();
        System.out.println("What would you like to do?");
        System.out.println("[L] Load catalog from CSV");
        System.out.println("[S] Search Pokemon");
        System.out.println("[A] Add new Pokemon");
        System.out.println("[D] Delete Pokemon");
        System.out.println("[E] Export changes to CSV");
        System.out.println("[C] Display catalog stats");
        System.out.println("[Q] Quit");

        // read in user's choice, and trim away any leading or trailing whitespace
        System.out.print("... Your command: ");
        String input = userInput.nextLine().trim();
        if (input.length() == 0) // if user's choice is blank, return null character
            return '\0';
        // otherwise, return an uppercase version of the first character in input
        System.out.println();
        return Character.toUpperCase(input.charAt(0));
    }

    /**
     * Prompts the user for a filename and reads it into the backend.
     *
     * @implNote Consider calling {@link IBackend#loadDataFromFile(String)}. The frontend developer will need
     * to catch each exception and print out an appropriate error message to the user if this command fails.
     */
    public void loadDataCommand() {
        // Read user input
        System.out.print("Enter path: ");
        String filename = userInput.nextLine().trim();
        // Call loadDataFromFile function and catch exceptions
        try {
            backend.loadDataFromFile(filename);
        } catch (FileNotFoundException e1) {
            System.out.println("Error: File path doesn't exist");
            return;
        } catch(IllegalArgumentException e2) { 
            System.out.println("Error: File is not in CSV format");
            return;
        } catch(SecurityException e3) {
            System.out.println("Error: Program doesn't have permission to read the file");
            return;
        } catch(IOException e4) {
            System.out.println("Error: I/O error occurs while reading");
            return;
        }
        // Print if no exceptions thrown
        System.out.println("CSV loaded!");
    }

    /**
     * <p>Prompts the user to search for a Pokemon into the backend. Information about the Pokemon should be displayed to the user, but
     * the question of <i>"what information should be shown?"</i> is ultimately up to the frontend developer to decide.</p>
     * <p>The {@link IPokemon} interface contains methods about what information can be displayed.</p>
     *
     * @implNote Consider calling {@link IBackend#searchFor(String)}. The frontend developer will need
     * to catch each exception and print out an appropriate error message to the user if this command fails.
     */
    public void searchCommand() {
        // Read user input
        System.out.print("Enter name: ");
        String name = userInput.nextLine().trim();
        try {
            IPokemon pokemon = backend.searchFor(name);
            // => NAME (id: ID) <=
            System.out.println("=> " + pokemon.getName() + " (id: " + pokemon.getPokedexNumber() + ") <=");
            // Type: PRIMARYTYPE, SECONDARYTYPE
            System.out.print("> Type: " + pokemon.getPrimaryType());
            if(pokemon.getSecondaryType().isPresent()) {
                System.out.print(", " + pokemon.getSecondaryType().get());
            }
            System.out.println();
            // > ATK: ATK
            System.out.println("> ATK: " + pokemon.getAttackStat());
            // > DEF: DEF
            System.out.println("> DEF: " + pokemon.getDefenseStat());
            // > SPA: SPA
            System.out.println("> SPA: " + pokemon.getSpecialAttackStat());
            // > SPD: SPD
            System.out.println("> SPD: " + pokemon.getSpecialDefenseStat());
            // > SPE: SPE
            System.out.println("> SPE: " + pokemon.getSpeedStat());
            // > Abilities: ABILITY, ABILITY
            String[] abilities = pokemon.getAbilityNames();
            System.out.print("> Abilities: " + abilities[0]);
            for(int i = 1; i < abilities.length; i++) {
                System.out.print(", " + abilities[i]);
            }
            System.out.println();
        } catch(NoSuchElementException e) {
            System.out.println("Error: No element matches the search key");
        }
    }


    /**
     * Prompts the user to enter a line of input, in CSV format, that represents a Pokemon to add to the catalog.
     *
     * @implNote Consider calling {@link IBackend#addPokemon(String)}. The frontend developer will need
     * to catch each exception and print out an appropriate error message to the user if this command fails.
     */
    public void addCommand() {
        // Read user input
        System.out.print("Enter input in CSV format: ");
        String pokemonCsvFmtStr = userInput.nextLine().trim();
        // Call addPokemon function and catch exceptions
        try {
            backend.addPokemon(pokemonCsvFmtStr);
        } catch(IllegalArgumentException e1) {
            System.out.println("Error: Pokemon already exists in red-black tree or input is malformed");
            return;
        } catch(IOException e2) {
            System.out.println("Error: I/O error occured while reading");
            return;
        }
        // Print if no exceptions thrown
        System.out.println("Pokemon added!");
    }

    /**
     * Prompts the user to enter the name of the Pokemon they wish to remove from the catalog.
     *
     * @implNote Consider calling {@link IBackend#removePokemon(String)}. The frontend developer will need
     * to catch each exception and print out an appropriate error message to the user if this command fails.
     */
    public void deleteCommand() {
        // Read user input
        System.out.print("Enter name: ");
        String name = userInput.nextLine().trim();
        // Call removePokemon function and catch exceptions
        try {
            backend.removePokemon(name);
        } catch(IllegalArgumentException e) {
            System.out.println("Error: No Pokemon with name " + name + " was found in the red-black tree");
            return;
        }
        // Print if no exceptions thrown
        System.out.println("Pokemon deleted!");
    }

    /**
     * Prompts the user to enter a filename, and attempts to write all Pokemon contained by this catalog into that file.
     * @implNote Consider calling {@link IBackend#saveDataToFile(String)}. The frontend developer will need
     * to catch each exception and print out an appropriate error message to the user if this command fails.
     */
    public void saveDataCommand() {
        // Read user input
        System.out.print("Enter filename: ");
        String filename = userInput.nextLine().trim();
        // Call saveDataToFile function and catch exceptions
        try {
            backend.saveDataToFile(filename);
        } catch (FileNotFoundException e1) {
            System.out.println("Error: File path doesn't exist");
            return;
        } catch(SecurityException e3) {
            System.out.println("Error: Program doesn't have permission to read the file");
            return;
        } catch(IOException e4) {
            System.out.println("Error: I/O error occured while reading");
            return;
        }
        // Write to file and report success if no exceptions thrown
        System.out.println("Exported to " + filename + "!");
    }

    /**
     * <p>Displays information about the catalog as a whole. Information about the catalog should be displayed to the user,
     * but the question of <i>"what information should be shown?"</i> is ultimately up to the backend developer to decide.</p>
     *
     * @implNote Consider making use of {@link IBackend#displayCatalogStats()}.
     */
    public void displayCatalogStatsCommand() {
        // Print displayCatalogStats function
        System.out.println(backend.displayCatalogStats());
    }
}
