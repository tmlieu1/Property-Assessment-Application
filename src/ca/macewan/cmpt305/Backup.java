/* Student Name: Lou Lieu
 * Property Assessment Data 2019
 * Lab Section: CMPT 305-X02L
 * CMPT 305, Fall 2019
 */

/*
package ca.macewan.cmpt305;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

public class Property{
	//main method
	public static void main (String[] args) {
		String data = getInput("Property_Assessment_Data_2019.csv", "CSV filename [press enter for default data]: ");
		try {
			PropertyAssessment [] propertyValues = readFile(data);
			displayStats(propertyValues);
			findByAcc(propertyValues);
			PropertyAssessment [] nbhValues = getNBHData(propertyValues);
			displayStats(nbhValues);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//gathers the data from methods given a PropertyAssessment array and displays the output
	public static void displayStats(PropertyAssessment [] propertyValues) {
		int n = getNum(propertyValues);
		int min = getMin(propertyValues);
		int max = getMax(propertyValues);
		long range = getRange(max, min);
		long mean = getMean(propertyValues);
		int sd = getSD(propertyValues, mean);
		int median = getMedian(propertyValues);
		System.out.println("n: " + n +
				"\nmin: " + min + 
				"\nmax: " + max +
				"\nrange: " + range +
				"\nmean: " + mean +
				"\nstandard deviation: " + sd +
				"\nmedian: " + median);
	}
	
	//finds a property by account number in the array and displays information about it
	public static void findByAcc(PropertyAssessment [] values) {
		int n = 0; 
		int accNum = Integer.parseInt(getInput("10202374", "\nFind a property assessment by account number: "));
		while (n < values.length) {
			if (accNum == values[n].getAccountNum()) {
				System.out.println("Account number = " + values[n].getAccountNum() + 
						"\nAddress = " + values[n].getAddress() +
						"\nAssessed Value = $" + values[n].getAssessedVal() +
						"\nAssessment Class = " + values[n].getAssessedClass() +
						"\nNeighbourhood = " + values[n].getNeighbourhood() +
						"\nLocation = " + values[n].getLocation());
				return;
			}
			n++;
		}
	}
	
	//gets the number of records of a neighbourhood
	public static int getNBHNum(PropertyAssessment[] values, String nbh) {
		int n = 0;
		for(int i = 0; i < values.length; i++) {
			if (values[i].getNeighbourhood().getNBHName().equals(nbh)) {
				n++;
			}
		}
		return n;
	}
	
	public static int getACNum(PropertyAssessment[] values, String assessmentClass) {
		int n = 0 ;
		for (int i = 0; i < values.length; i++) {
			if (values[i].getAssessedClass().equals(assessmentClass)) {
				n++;
			}
		}
		return n;
	}
	
	//gets the data of a neighbourhood, returning it as an array of PropertyAssessment
	public static PropertyAssessment [] getNBHData(PropertyAssessment[] values){
		String nbh = getInput("WEBBER GREENS", "\nNeighbourhood: "); 
		int numRecords = getNBHNum(values, nbh);
		int n = 0;
		PropertyAssessment [] pa = new PropertyAssessment[numRecords];
		for(int i = 0; i < values.length; i++) {
			if (values[i].getNeighbourhood().getNBHName().equals(nbh)) {
				pa[n] = new PropertyAssessment(values[i].getAccountNum(), values[i].getAddress(), values[i].getAssessedVal(),
						values[i].getAssessedClass(), values[i].getNeighbourhood(), values[i].getLocation());
				n++;
			}
		}
		Arrays.sort(pa);
		return pa;
	}
	
	//combine all input methods to come from the same method.
	public static String getInput(String defaultCase, String inputMessage) {
		Scanner s = new Scanner(System.in);
		System.out.print(inputMessage);
		String input = s.nextLine();
		if (input != "" && input.length() != 0) {
			defaultCase = input;
		}
		return defaultCase;
	}
	
	//gets number of records in the csv file
	public static int getNumRecords(String filename) throws IOException{
		Scanner file = new Scanner(Paths.get(filename));
		if (file.hasNextLine()) {
			file.nextLine(); //ignore the header
		}
		int n = 0;
		while (file.hasNextLine()) {
			file.nextLine();
			n++;
		}
		file.close();
		return n;
	}
	
	//reads through the file and returns the data as a PropertyAssessment array
	public static PropertyAssessment [] readFile(String filename) throws IOException {
		int numRecords = getNumRecords(filename);
		PropertyAssessment [] pa = new PropertyAssessment[numRecords];
		int n = 0;
		Scanner file = new Scanner(Paths.get(filename));
		if (file.hasNextLine()) {
			file.nextLine();
		}
		while (file.hasNextLine()) {
			String currentLine = file.nextLine();
			Scanner tokenizer = new Scanner(currentLine).useDelimiter(",");
			String [] temp = new String[12];
			for (int i = 0; i < 12 && tokenizer.hasNext(); i++) {
				String test = tokenizer.next();
				temp[i] = test;
			}
			Address addr = new Address(temp[1], temp[2], temp[3]); 
			Location loc = new Location(Double.parseDouble(temp[10]), Double.parseDouble(temp[11]));
			Neighbourhood nbh = new Neighbourhood(temp[6], temp[7], temp[8]);
			pa[n] = new PropertyAssessment(Integer.parseInt(temp[0]), addr, Integer.parseInt(temp[4]), temp[5], nbh, loc);
			n++;	
		}
		Arrays.sort(pa);
		return pa;
	}
	
	//returns the number of entries in the array
	public static int getNum(PropertyAssessment [] values) {
		return values.length;
	}
	
	//returns the smallest value in the array
	public static int getMin(PropertyAssessment [] values) {
		int min = 2147483647;
		for(int i = 0; i < values.length; i++) {
			if (values[i].getAssessedVal() < min)
				min = values[i].getAssessedVal();
		}
		return min;
	}
	
	//returns the largest value in the array
	public static int getMax(PropertyAssessment [] values) {
		int max = 0;
		for(int i = 0; i < values.length; i++) {
			if (values[i].getAssessedVal() > max)
				max = values[i].getAssessedVal();
		}
		return max;
	}
	
	//returns the range of the array
	public static long getRange(int max, int min) {
		return max - min;
	}
	
	//returns the mean of the array
	public static long getMean(PropertyAssessment [] values) {
		long sum = 0;
		for(int i = 0; i < values.length; i++) {
			sum += values[i].getAssessedVal();
		}
		long mean = (sum/values.length);
		return mean;
	}
	
	//returns the standard deviation of the array
	public static int getSD(PropertyAssessment [] values, long mean) {
		int standardDev = 0;
		long sum = 0;
		for(int i = 0; i < values.length; i++) {
			sum += Math.pow(Math.abs(values[i].getAssessedVal() - mean), 2);
		}
		standardDev = (int) Math.round((Math.sqrt(sum/values.length)));
		return standardDev;
	}
	
	//returns the median of the array
	public static int getMedian(PropertyAssessment [] values) {
		int median = values[(values.length/2)].getAssessedVal();
		return median;
	}
}

*/