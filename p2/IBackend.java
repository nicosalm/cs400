import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Interface for the program backend.
 * The backend developer (BD) will be responsible for implementing this (named
 * {@code BackendBD}) during role code.
 */
public interface IBackend {

    // public BackendROLE(IRbt<IPokemon> rbt, ICatalogReader reader);
    // ^ This is the constructor everyone will need to make in their stub
    // (placeholder) implementation of this interface.

    /**
     * Calls {@link ICatalogReader#readFromFile(String)} to load the Pokemon into
     * the backend.
     *
     * @param filename the name of the {@code .csv} file to read from
     * @throws FileNotFoundException    if the file path doesn't exist, or points to
     *                                  a directory rather than a regular file
     * @throws IllegalArgumentException if the file is not in CSV format
     * @throws SecurityException        if the program doesn't have permission to
     *                                  read the file
     * @throws IOException              if an I/O error occurs while reading
     * @see ICatalogReader#readFromFile(String)
     */
    void loadDataFromFile(String filename)
            throws FileNotFoundException, IllegalArgumentException, SecurityException, IOException;

    /**
     * Calls {@link IRbt#get(String)} to search for a Pokemon in the backend's
     * red-black tree.
     *
     * @throws NoSuchElementException if no element matches the search key
     * @see IRbt#get(String)
     */
    IPokemon searchFor(String nameToSearch) throws NoSuchElementException;

    /**
     * Adds a Pokemon given by a valid CSV line.
     *
     * @param pokemonCsvFmtStr the CSV line to serialize into a Pokemon
     * @throws IllegalArgumentException if the Pokemon with name {@code pokemonName}
     *                                  already exists in the red-black tree
     *                                  or {@code pokemonCsvFmtStr} is malformed
     * @throws IOException              if an I/O error occurs while reading
     * @implNote Consider using
     *           {@link ICatalogReader#readCsvLineIntoPokemon(String pokemonCsvFmtStr)}
     *           to create the Pokemon instance
     *           and then add it into the red-black tree.
     * @see ICatalogReader#readCsvLineIntoPokemon(String)
     * @see IRbt#insert(Comparable)
     */
    void addPokemon(String pokemonCsvFmtStr) throws IllegalArgumentException, IOException;

    /**
     * Removes a Pokemon with name {@code pokemonName} from the catalog.
     *
     * @param pokemonName the name of the Pokemon to remove, if it exists
     * @throws IllegalArgumentException if no Pokemon with name {@code pokemonName}
     *                                  was found in the red-black tree
     */
    void removePokemon(String pokemonName) throws IllegalArgumentException;

    /**
     * Calls {@link ICatalogReader#writeToFile(String, List)} to write Pokemon from
     * the backend to a {@code .csv} file.
     *
     * @param filename the name of the file to write to. If the file already exists,
     *                 its contents will be overridden.
     * @throws FileNotFoundException if the file path doesn't exist, or points to a
     *                               directory rather than a regular file
     * @throws SecurityException     if the program doesn't have permission to
     *                               create and write to the file
     * @throws IOException           if an I/O error occurs while writing
     * @see ICatalogReader#writeToFile(String, List)
     */
    void saveDataToFile(String filename) throws FileNotFoundException, SecurityException, IOException;

    /**
     * Displays information about the Pokemon catalog loaded in the backend.
     *
     * <p>
     * You can use a foreach (aka enhanced for loop) to iterate through the RBT as a
     * list of Pokemon. Some interesting information
     * could be aggregated this way:
     * </p>
     *
     * <pre>
     * <code>
     *     for(IPokemon pkm : myRbt) { // calls myRbt.iterator() under the hood
     *         // do something here
     *     }
     * </code>
     * </pre>
     *
     * @return a formatted String that displays information that the backend
     *         developer finds useful to know over the entire dataset.
     * @implNote Don't worry too much about the correctness of this method until the
     *           integration phase of the project.
     *           You will need to implement a dummy/placeholder {@code iterator()}
     *           method to make use of the foreach loop.
     *           The algorithm engineer will have a complete {@code iterator()}
     *           method for the backend developer to use.
     */
    String displayCatalogStats();
}
