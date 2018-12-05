package com.whereisevery1.database.model;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/** Creates json from scraped data
*/
public class DatabaseCreator {

	public static void main(final String[] args) {
		 DatabaseCreator d = new DatabaseCreator();
		 d.runScrape();
		 d.writeToJSON();
	}
	
	/** Contains all the information about each building, classroom, and
	* days/times of classes.
	*/
	private SerializableBuildingList data;

	/**Inflates all the java objects. 
	*The scraper returns a hashmap of buildings, which is put into the wrapper
	*class SerializableBuildingList. This makes
	* it easier to serialize with jackson.
	*/
	public void runScrape() {
		// The scraper returns a hashmap of buildings, which is put into the wrapper
		// class SerializableBuildingList. This makes
		// it easier to serialize with jackson.
		data = new SerializableBuildingList(Scraper.scrapeUCSB());
	}

	/** Using the inflated objects from runScrape, converts data into json.
	*/
	public void writeToJSON() {
		// Object mapper class can convert java objects to json. Be sure that the java
		// objects have getters and setters for all variables.
		ObjectMapper mapper = new ObjectMapper();

		try {
			// Convert object to JSON string and save into a file directly
			mapper.writeValue(new File("catalog.json"), data);

			// Convert object to JSON string
			// String jsonInString =
			// mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
			mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
			// System.out.println(jsonInString);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**For testing out handmade json databases.
	*@param data SerializableBuildingList containing all our data
	*/
	public void setData(SerializableBuildingList data) {
		this.data = data;
	}
}
