package ca.macewan.cmpt305;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Lab2MainTest {
	private List <Property> correctTestValues;
	private List <Property> correctNBHValues;
	
	private String correctFile = "Property_Assessment_Data_2019.csv";
	
	@BeforeEach
	void testReadFile() throws Exception {
		//try to read the correct filename
		correctTestValues = Lab2Main.readFile(correctFile);
		correctNBHValues = Lab2Main.getNBHData(correctTestValues, "WEBBER GREENS");
	}
	
	@Test
	void testFindByAcc() {
		//testing output, should output correctly
		Lab2Main.findByAcc(correctTestValues, 10202374);
		
		//testing incorrect input, should output "Account number not found!"
		Lab2Main.findByAcc(correctTestValues, 1234);
	}
	
	@Test
	void testGetNum() {
		int numTestValues = Lab2Main.getNum(correctTestValues);
		int numNBHValues = Lab2Main.getNum(correctNBHValues);
		
		//test for correct for full data and neighbourhood data
		assertEquals(400541, numTestValues);
		assertEquals(993, numNBHValues);
		
		//test for false for full data and neighbourhood data
		assertNotEquals(0, numTestValues);
		assertNotEquals(0, numNBHValues);
		
		//make sure they equal themselves
		assertEquals(numTestValues, numTestValues);
		assertEquals(numNBHValues, numNBHValues);
	}
	
	@Test
	void testGetMin() {
		int minTestValues = Lab2Main.getMin(correctTestValues);
		int minNBHValues = Lab2Main.getMin(correctNBHValues);
		
		//test for correct for full data and neighbourhood data
		assertEquals(0, minTestValues);
		assertEquals(3500, minNBHValues);
		
		//test for false for full data and neighbourhood data
		assertNotEquals(1, minTestValues);
		assertNotEquals(1, minNBHValues);
		
		//make sure they equal themselves.
		assertEquals(minTestValues, minTestValues);
		assertEquals(minNBHValues, minNBHValues);
	}
	
	@Test
	void testGetMax() {
		int maxTestValues = Lab2Main.getMax(correctTestValues);
		int maxNBHValues = Lab2Main.getMax(correctNBHValues);
		
		//test for correct for full data and neighbourhood data
		assertEquals(1338809500, maxTestValues);
		assertEquals(38782500, maxNBHValues);
		
		//test for false for full data and neighbourhood data
		assertNotEquals(0, maxTestValues);
		assertNotEquals(0, maxNBHValues);
		
		//make sure they equal themselves.
		assertEquals(maxTestValues, maxTestValues);
		assertEquals(maxNBHValues, maxNBHValues);
	}
	
	@Test
	void testGetRange() {
		//test for correct for full data and neighbourhood data
		long rangeTestValues = Lab2Main.getRange(Lab2Main.getMax(correctTestValues), Lab2Main.getMin(correctTestValues));
		long rangeNBHValues = Lab2Main.getRange(Lab2Main.getMax(correctNBHValues), Lab2Main.getMin(correctNBHValues));
		
		assertEquals(1338809500, rangeTestValues);
		assertEquals(38779000, rangeNBHValues);
		
		//test for false for full data and neighbourhood data
		assertNotEquals(0, rangeTestValues);
		assertNotEquals(0, rangeNBHValues);
		
		//make sure they equal themselves.
		assertEquals(rangeTestValues, rangeTestValues);
		assertEquals(rangeNBHValues, rangeNBHValues);
	}
	
	@Test
	void testGetMean() {
		long meanTestValues = Lab2Main.getMean(correctTestValues);
		long meanNBHValues = Lab2Main.getMean(correctNBHValues);
		
		//test for correct for full data and neighbourhood data
		assertEquals(464922, meanTestValues);
		assertEquals(491730, meanNBHValues);
		
		//test for false for full data and neighbourhood data
		assertNotEquals(0, meanTestValues);
		assertNotEquals(0, meanNBHValues);
		
		//make sure they equal themselves.
		assertEquals(meanTestValues, meanTestValues);
		assertEquals(meanNBHValues, meanNBHValues);
	}
	
	@Test
	void testGetSD() {
		long sdTestValues = Lab2Main.getSD(correctTestValues, Lab2Main.getMean(correctTestValues));
		long sdNBHValues = Lab2Main.getSD(correctNBHValues, Lab2Main.getMean(correctNBHValues));
		
		//test for correct for full data and neighbourhood data
		assertEquals(3683363, sdTestValues);	
		assertEquals(1339109, sdNBHValues);
		
		//test for false for full data and neighbourhood data
		assertNotEquals(0, sdTestValues);
		assertNotEquals(0, sdNBHValues);
		
		//make sure they equal themselves.
		assertEquals(sdTestValues, sdTestValues);
		assertEquals(sdNBHValues, sdNBHValues);
	}
	
	@Test
	void testGetMedian() {
		int medianTestValues = Lab2Main.getMedian(correctTestValues);
		int medianNBHValues = Lab2Main.getMedian(correctNBHValues);
		
		//test for correct for full data and neighbourhood data
		assertEquals(323000, medianTestValues);
		assertEquals(411000, medianNBHValues);
		
		//test for false for full data and neighbourhood data
		assertNotEquals(0, medianTestValues);
		assertNotEquals(0, medianNBHValues);
		
		//make sure they equal themselves.
		assertEquals(medianTestValues, medianTestValues);
		assertEquals(medianNBHValues, medianNBHValues);
	}
}