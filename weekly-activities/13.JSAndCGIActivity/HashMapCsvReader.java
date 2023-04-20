import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.Reader;

/**
 * Class to read in a CSV file. Every row of the CSV is represented with a HashMap.
 */
public class HashMapCsvReader implements Iterable<HashMap<String,String>> {

	// field to store the header line of the csv file
	protected String headerString;
	// field to store the scanner for reading in the file
	protected Scanner csvFileScanner;

	/**
	 * Constructor the HashMapCsvReader that takes a reader for the input csv file as an argument.
	 * 
	 * @param inputFileReader the reader to read from the csv file
	 * @throws FileNotFoundException if the file to read from was not found
	 * @throws IOException if there was a problem reading from the csv file
	 * @throws DataFormatException if the csv data is not in the expected format
	 */
	public HashMapCsvReader(Reader inputFileReader) throws FileNotFoundException, IOException, DataFormatException {
		// create a scanner for the file
		this.csvFileScanner = new Scanner(inputFileReader);
		// reader the header
		this.headerString = this.csvFileScanner.nextLine();
	}

	/**
	 * Class to represent a row in a CSV file. Includes methods to
	 * interpret and process a line read from a CSV to a list of attributes.
	 */
	public static class CsvRow extends HashMap<String,String> {

		// the header names for the attributes of the row
		public List<String> header;
		// the values for the attributes of the row
		public List<String> row;

		/**
		 * Helper method that processes a line from a CSV into a list of
		 * attributes.
		 * @param csvLine a line read from a CSV file
		 * @return list of attributes from that line as Strings
		 * @throws DataFormatException when line cannot be processed 
		 */
		protected List<String> handleCsvRow(String csvLine) throws DataFormatException {
			// remove white space at beginning and end of line
			csvLine = csvLine.trim();
			// convert the line into an array of characters
			char[] charLine = csvLine.toCharArray();
			// variable to store the previous character while iterating through array
			char prevChar = '\0';
			// list of strings to add every column we extract to
			LinkedList<String> cellList = new LinkedList<>();
			// StringBuffer object that help us assemble the columns from single
			// characters back to strings
			StringBuffer currBuff = new StringBuffer();
			// variable to keep track of the number of quotation characters seen
			int numQuotes = 0;
			// iterative over all characters in the line
			for (int i = 0; i < charLine.length; i++) {
				// c is the current character
				char c = charLine[i];
				if (c == '\"') {
					// if the current character is a " then count it, and ...
					numQuotes++;
					if (prevChar == '\"') {
						// ... only add to output string if it is excaped by a
						// trailing "
						currBuff.append(c);
					}
				} else if (c == ',' && (numQuotes % 2) == 0) {
					// if the current character is a , and we have seen an even
					// number of ", start a new column string and add old one
					// to results list
					cellList.add(currBuff.toString().trim());
					currBuff = new StringBuffer();
				} else {
					// if we have seen an odd number of " add , to string as
					// regular character
					currBuff.append(c);
				}
				// set current character to previous character
				prevChar = c;
			}
			// handle the final column that is not terminated with a ,
			String lastCell = currBuff.toString().trim();
			cellList.add(lastCell);
			// and return the list of attributes
			return cellList;
		}
		
		/**
		 * Constructor that creates a new CSVRow object from a line in a CSV file
		 * and that same CSV's header line.
		 * @param headerLine the line with the header of the CSV
		 * @param rowLine the line with the row of the csv
		 * @throws DataFormatException if the header of row is not formatted as expected
		 */
		public CsvRow(String headerLine, String rowLine) throws DataFormatException {
			List<String> header = handleCsvRow(headerLine);
			List<String> row = handleCsvRow(rowLine);

			// both lists should have some length, otherwise there is an issue with reading the file
			if (header.size() != row.size()) {
				System.out.println(headerLine + "\n" + rowLine);
				throw new DataFormatException("Problem reading the following line (different number of columns then header line): " + rowLine);
			}

			Iterator<String> headerIter = header.iterator();
			Iterator<String> rowIter = row.iterator();
			while (headerIter.hasNext() && rowIter.hasNext()) {
				this.put(headerIter.next(), rowIter.next());
			}

		}

	}

	/**
	 * Iterator over the data items in the csv file. Every data item is
	 * returned as a HashMap<String,String> that maps every attribute
	 * with the name defined in the header of the csv file to the value
	 * for the data item.
	 */
	@Override
	public Iterator<HashMap<String,String>> iterator() {
		// create an iterator using an anonymouns class
		return new Iterator<HashMap<String,String>>() {

			/**
			 * The has next method returns true if there is more data items in the
			 * csv file to return and false otherwise.
			 */
			@Override
			public boolean hasNext() {
				return csvFileScanner.hasNextLine();
			}

			/**
			 * The next method returns the next data item in the csv file as a
			 * HashMap<String,String> object.
			 */
			@Override
			public HashMap<String,String> next() {
				if (this.hasNext()) {
					try {
						return new CsvRow(headerString, csvFileScanner.nextLine());
					} catch(DataFormatException e) {
						throw new NoSuchElementException("error reading line in csv", e);
					}
				} else {
					throw new NoSuchElementException("no more lines to read");
				}
			}
			
		};
	}

}
