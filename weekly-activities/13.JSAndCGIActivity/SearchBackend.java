import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.zip.DataFormatException;

/**
 * This is the main class our the cereals search web program backend.
 */
public class SearchBackend {

	public static void main(String[] args) {
		// create a list of the attributes of each cereal we need to send to frontend
		List<String> attributesList = new LinkedList<>();
		String filterString = null;
		// new split the query string that the cgi script passes in as arg[0]
		for (String element : args[0].trim().split("&")) {
			if (element.startsWith("name")) {
				filterString = element.split("=")[1].trim();
			} else {
				attributesList.add(element.split("=")[0].trim());
			}
		}
		// call helper method to get search results from data set
		List<HashMap<String, String>> results = getAndFilterResults(filterString);
		// then start assembling the JSON representation
		String json = "[ ";
		boolean isFirst = true;
		// this loop adds all data items in the result set to the JSON array
		for (HashMap<String, String> item : results) {
			if (isFirst) {
				json += "{";
			} else {
				json += ",{";
			}
			isFirst = false;
			// TODO: Add a loop that adds all the attributes on the
			// attributes list to the JSON representation of the
			// data item.
			for (String attribute : attributesList) {
				json += "\"" + attribute + "\" : \"" + item.get(attribute) + "\",";
			}
			json += "\"name\" : \"" + item.get("name") + "\"";
			json += " }";
		}
		json += " ]";
		// new return the results by printing the JSON representation we just created
		System.out.println(json);
	}

	/**
	 * This method uses HashMapCsvReader to search through a CSV file and returns
	 * results
	 * as a list of HashMap<String,String> that maps attributes for each data item
	 * to the
	 * value of the attribute.
	 * 
	 * @param filterString the string to search for in the names of hte data items
	 * @return a list of HashMap<String,String> that represents the data items
	 */
	public static List<HashMap<String, String>> getAndFilterResults(String filterString) {
		// create list for results
		List<HashMap<String, String>> results = new LinkedList<>();
		try {
			// use HashMapCsvReader to read in csv file
			HashMapCsvReader reader = new HashMapCsvReader(new FileReader("cereal.csv"));
			for (HashMap<String, String> csvRow : reader) {
				if (csvRow.get("name").toLowerCase().contains(filterString.toLowerCase())) {
					// add data item that matches criteria to results list
					results.add(csvRow);
				}
			}
		} catch (IOException | DataFormatException e) {
			// throw an exception if there is a problem reading in the csv file
			new RuntimeException("exception while reading file cereals.csv", e);
		}
		return results;
	}

}
