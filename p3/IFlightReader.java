import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Interface for a class that reads City from, and writes city to, a CSV file.
 * The data wrangler (DW) will be responsible for implementing this (named {@code flightReaderDW}) during role code.
 */
public interface IFlightReader {

  /**
   * @param filename the name of the {@code .csv} file to read from
   * @return a list of City, serialized (i.e. read) from the file
   * @throws FileNotFoundException if the file path doesn't exist, or points to a directory rather than a regular file
   * @throws IllegalArgumentException if the file is not in CSV format
   * @throws SecurityException if the program doesn't have permission to read the file
   * @throws IOException if an I/O error occurs while reading
   */
  List<ICity> readFromFile(String filename) throws FileNotFoundException, IllegalArgumentException, 
    SecurityException, IOException;

  /**
   * Converts a node of DOT into an {@link ICity} object, if it is in the correct form.
   * @param cityDOTFmtStr the CSV line to serialize into a Pokemon
   * @return a Pokemon represented by {@code cityDOTFmtStr}.
   * @throws IllegalArgumentException if {@code cityDOTFmtStr} is malformed (in a bad format that cannot be parsed)
   * @throws IOException if an I/O error occurs while reading
   */
  ICity readDOTLineIntoCity(String cityDOTFmtStr) throws IllegalArgumentException, IOException;

}