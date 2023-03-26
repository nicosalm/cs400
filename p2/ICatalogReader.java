import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.List;

/**
 * Interface for a class that reads Pokemon from, and writes Pokemon to, a CSV file.
 * The data wrangler (DW) will be responsible for implementing this (named {@code CatalogReaderDW}) during role code.
 */
public interface ICatalogReader {

    /**
     * @param filename the name of the {@code .csv} file to read from
     * @return a list of Pokemon, serialized (i.e. read) from the file
     * @throws FileNotFoundException if the file path doesn't exist, or points to a directory rather than a regular file
     * @throws IllegalArgumentException if the file is not in CSV format
     * @throws SecurityException if the program doesn't have permission to read the file
     * @throws IOException if an I/O error occurs while reading
     */
    List<IPokemon> readFromFile(String filename) throws FileNotFoundException, IllegalArgumentException, SecurityException, IOException;

    /**
     * Deserializes (stores) a list of Pokemon to a {@code .csv} file.
     * @param filename the name of the file to write to.
     *                 If the file already exists, its contents will be overridden.
     * @param listToStore the list of Pokemon to write
     * @throws FileNotFoundException if the file path doesn't exist, or points to a directory rather than a regular file
     * @throws SecurityException if the program doesn't have permission to create and write to the file
     * @throws IOException if an I/O error occurs while writing
     */
    void writeToFile(String filename, List<IPokemon> listToStore) throws FileNotFoundException, SecurityException, IOException;

    /**
     * Converts a line of CSV into an {@link IPokemon} object, if it is in the correct form.
     * @param pokemonCsvFmtStr the CSV line to serialize into a Pokemon
     * @return a Pokemon represented by {@code pokemonCsvFmtStr}.
     * @throws IllegalArgumentException if {@code pokemonCsvFmtStr} is malformed (in a bad format that cannot be parsed)
     * @throws IOException if an I/O error occurs while reading
     */
    IPokemon readCsvLineIntoPokemon(String pokemonCsvFmtStr) throws IllegalArgumentException, IOException;
}
