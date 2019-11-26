/**
 * <h> Property Assessment </h>
 * The PropertyAssessment program analyzes the Property Assessment of Edmonton as provided by the City of Edmonton.
 * The program accepts user input and displays statistics and information about properties within the city.
 * @author	Lou Lieu
 * @course	CMPT 305-X02L, Fall 2019
 * @since	2019-09-10
 */
package ca.macewan.cmpt305;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;


public class Lab2Main{
	
	//main method
	public static void main (String[] args) {
		boolean found = false;
		//continue iteration until valid filename input.
		while (!found) {
			String data = getInput("Property_Assessment_Data_2019.csv", "CSV filename [default: Property_Assessment_Data_2019.csv]: ");
			try {
				//fill the propertyValues arraylist with data.
				List<Property> propertyValues = readFile(data);
				displayStats(propertyValues);
				//find by account number and display information about it.
				int accNum = Integer.parseInt(getInput("10006474", "\nFind a property assessment by account number [default: 10006474]: "));
				findByAcc(propertyValues, accNum);
				//fill the nbhValues arraylist with data and display information about it.
				String nbh = getInput("WEBBER GREENS", "\nNeighbourhood [default: WEBBER GREENS]: ").toUpperCase(); 
				List<Property> nbhValues = getNBHData(propertyValues, nbh);
				displayStats(nbhValues);
				//end the loop
				found = true;
			}
			catch (IOException e) {
				System.out.println("File not found! please try again.");
			}
		}
	}
	
	
    /*	@displayStats(List<Property> propertyValues)
     * 	purpose: gathers statistical data from methods in the class, given a PropertyAssessment array, and prints the output.
     * 	parameters: List <PropertyAssessment> - list in which displayStats gathers data.
     * 	pre: propertyValues is a valid List with Property objects.
     * 	returns: none 
     */
	public static void displayStats(List<Property> propertyValues) {
		//the List has no elements, so we display nothing and return.
		if (propertyValues.size() == 0) {
			System.out.println("Data not found.");
			return;
		}
		//display the statistics in a formatted print statement.
		int min = getMin(propertyValues);
		int max = getMax(propertyValues);
		long mean = getMean(propertyValues);
		System.out.println("n: " + getNum(propertyValues) + "\nmin: " + min + 
				"\nmax: " + max + "\nrange: " + getRange(max, min) +
				"\nmean: " + mean + "\nstandard deviation: " + getSD(propertyValues, mean) + 
				"\nmedian: " + getMedian(propertyValues));
	}
	
	
    /*	@getInput(String defaultCase, String inputMessage)
     * 	purpose: gathers input from the user, returning a default String if none is entered.
     * 	parameters: String defaultCase - the default String which will be returned upon empty input.
     * 				String inputMessage - a print statement which specifies intent of input.
     * 	pre: defaultCase, inputMessage are valid String parameters.
     * 	returns: retStr - the resulting String to be used as a parameter.
     */
	public static String getInput(String defaultCase, String inputMessage) {
		//gather user input, if invalid, then we use the default case.
		String retStr = defaultCase;
		Scanner s = new Scanner(System.in);
		System.out.print(inputMessage);
		String input = s.nextLine();
		//we have a valid input, so we change retStr to match.
		if (input != "" && input.length() != 0) {
			retStr = input;
		}
		return retStr;
	}

    /*	@findByAcc(List<Property> values, int accNum)
     * 	purpose: finds a property by account number in the list and displays information about it.
     * 	parameters: List<Property> values - the list in which pa will iterate through to find pa.
     * 				int accNum - account number to search for.
     * 	pre: values is a valid List with Property objects, accNum is a valid integer.
     * 	returns: none
     */
	public static void findByAcc(List<Property> values, int accNum) {
		for(Property pa: values) {
			if (accNum == pa.getAccountNum()) {
				System.out.println(pa);
				return;
			}
		}
		System.out.println("Account number not found!");
	}
	
    /*	@getNBHData(List<Property> values, String nbh)
     * 	purpose: creates a List of properties based on neighbourhood from the values List, and displays stats about it.
     * 	parameters: List<Property> values - the list in which pa will iterate through to fill acList.
     * 				String nbh - String to search for in the values List.
     * 	pre: values is a valid List with Property objects, nbh is a valid String
     * 	returns: none
     */
	public static List<Property> getNBHData(List<Property> values, String nbh){
		//iterate through the values arraylist, looking for objects that have the same class.
		List<Property> nbhList = new ArrayList<>();
		for(Property pa: values) {
			if (pa.nameEquals(nbh)) {
				//add the object to the neighbourhood arraylist.
				nbhList.add(new Property(pa.getAccountNum(), pa.getAddress(), pa.getAssessedVal(),
						pa.getAssessedClass(), pa.getNeighbourhood(), pa.getLocation()));
			}
		}
		Collections.sort(nbhList);
		return nbhList;
	}
	
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
	
    /*	@getNum(List<Property> values)
     * 	purpose: returns the number of Objects in the Property List.
     * 	parameters: List<Property> values - the list to gather size from.
     * 	pre: values is a proper List.
     * 	returns: int - List size.
     */
	public static int getNum(List<Property> values) {
		return values.size();
	}
	
    /*	@getMin(List<Property> values)
     * 	purpose: returns the smallest assessed value in the Property List.
     * 	parameters: List<Property> values - the list to gather min value from.
     * 	pre: values is a proper List.
     * 	returns: int - smallest value in the List.
     */
	public static int getMin(List<Property> values) {
		int min = 2147483647;
		for (Property pa: values) {
			if (pa.getAssessedVal() < min) {
				min = pa.getAssessedVal();
			}
		}
		return min;
	}
	
	/*	@getMax(List<Property> values)
     * 	purpose: returns the largest assessed value in the Property List.
     * 	parameters: List<Property> values - the list to gather max value from.
     * 	pre: values is a proper List.
     * 	returns: int - largest value in the List.
     */
	public static int getMax(List<Property> values) {
		int max = 0;
		for (Property pa: values) {
			if (pa.getAssessedVal() > max) {
				max = pa.getAssessedVal();
			}
		}
		return max;
	}
	
	/*	@getRange(int max, int min)
     * 	purpose: returns the range (max - min), given max, min.
     * 	parameters: int max - largest value.
     * 				int min - smallest value.
     * 	pre: int max, min proper integers. max > min.
     * 	returns: long - range.
     */
	//returns the range of the array
	public static long getRange(int max, int min) {
		return max - min;
	}
	
	/*	@getMean(List<Property> values)
     * 	purpose: returns the mean of the Property List
     * 	parameters: List<Property> values - the list to gather mean value from.
     * 	pre: values is a proper List.
     * 	returns: long - the mean of the List.
     */
	public static long getMean(List<Property> values) {
		long sum = 0;
		for (Property pa: values) {
			sum += pa.getAssessedVal();
		}
		long mean = sum/values.size();
		return mean;
	}
	
	/*	@getSD(List<Property> values, long mean)
     * 	purpose: returns the standard deviation of the values in the Property List.
     * 	parameters: List<Property> values - the list to gather SD value from.
     * 				long mean - the mean of the List.
     * 	pre: values is a proper List, mean is the valid mean of the List.
     * 	returns: int - standard deviation of the List
     */
	public static int getSD(List<Property> values, long mean) {
		int standardDev = 0;
		long sum = 0;
		for (Property pa: values) {
			sum += Math.pow(Math.abs(pa.getAssessedVal() - mean), 2);
		}
		standardDev = (int) Math.round(Math.sqrt(sum/values.size()));
		return standardDev;
	}
	
	/*	@getMedian(List<Property> values)
     * 	purpose: returns the median of the Property List.
     * 	parameters: List<Property> values - the list to gather median value from.
     * 	pre: values is a proper List.
     * 	returns: int - median of the List.
     */
	public static int getMedian(List<Property> values) {
		int median = values.get(values.size()/2).getAssessedVal();
		return median;
	}
}