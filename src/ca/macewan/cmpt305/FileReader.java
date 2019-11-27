package ca.macewan.cmpt305;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FileReader {
	
    /*	@readFile(String filename)
     * 	purpose: reads through a file row by row and creating a Property List from the data gathered.
     * 	parameters: String filename - file to read from.
     * 	pre: filename is a valid String.
     * 	returns: List<Property> pa
     */
	public static List<Property> readFile(String filename) throws IOException {
		List<Property> pa = new ArrayList<>();
		//we begin reading through the file, skip the first line.
		Scanner file = new Scanner(Paths.get(filename));
		if (file.hasNextLine()) {
			file.nextLine();
		}
		//iterate through the file, separating by ','
		//we then add each element of the row to the arrayList index in each iteration
		while (file.hasNextLine()) {
			String currentLine = file.nextLine();
			Scanner tokenizer = new Scanner(currentLine).useDelimiter(",");
			String [] temp = new String[12];
			for (int i = 0; i < 12 && tokenizer.hasNext(); i++) {
				String test = tokenizer.next();
				temp[i] = test;
			}
			//construct address, location, neighbourhood classes and create the property object from them.
			Address addr = new Address(temp[1], temp[2], temp[3]); 
			Location loc = new Location(Double.parseDouble(temp[10]), Double.parseDouble(temp[11]));
			Neighbourhood nbh = new Neighbourhood(temp[6], temp[7], temp[8]);
			pa.add(new Property(Integer.parseInt(temp[0]), addr, Integer.parseInt(temp[4]), temp[5], nbh, loc));
		}
		Collections.sort(pa);
		return pa;
	}
	
	//Creates a List of Properties from the file.
	public static List<Property> getFileContents(String filename){
		List <Property> pa = new ArrayList<>();
		try {
			pa = FileReader.readFile(filename);
			return pa;
		}
		catch (IOException e) {
			return pa;
		}
	}
	
	//Returns the List of Properties
	public static List<Property> getTableData(String filename) {
		List <Property> propertyValues = FileReader.getFileContents(filename);
		return propertyValues;
	}
}
