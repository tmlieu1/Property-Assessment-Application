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
				List<Property> propertyValues = FileReader.readFile(data);
				Statistics.printStats(propertyValues);
				//find by account number and display information about it.
				int accNum = Integer.parseInt(getInput("10006474", "\nFind a property assessment by account number [default: 10006474]: "));
				findByAcc(propertyValues, accNum);
				//fill the nbhValues arraylist with data and display information about it.
				String nbh = getInput("WEBBER GREENS", "\nNeighbourhood [default: WEBBER GREENS]: ").toUpperCase(); 
				List<Property> nbhValues = getNBHData(propertyValues, nbh);
				Statistics.printStats(nbhValues);
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
}