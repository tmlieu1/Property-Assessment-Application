package ca.macewan.cmpt305;

import java.util.List;

import javafx.collections.transformation.FilteredList;

/* Refactor existing code by moving statistics functions into this class
 * 
 * */
public class Statistics {
	
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
		int median;
		if (values.size() % 2 == 0) {
			median = ((values.get(values.size()/2).getAssessedVal() + 
					values.get(values.size()/2 - 1).getAssessedVal())/2);
		}
		else {
			median = values.get(values.size()/2).getAssessedVal();
		}
		return median;
	}
	
    /*	@displayStats(List<Property> propertyValues)
     * 	purpose: gathers statistical data from methods in the class, given a PropertyAssessment array, and prints the output.
     * 	parameters: List <PropertyAssessment> - list in which displayStats gathers data.
     * 	pre: propertyValues is a valid List with Property objects.
     * 	returns: none 
     */
	public static void printStats(List<Property> propertyValues) {
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
}
