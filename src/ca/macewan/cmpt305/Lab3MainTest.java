package ca.macewan.cmpt305;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Lab3MainTest {
	private List <Property> correctTestValues;
	private List <Property> correctResValues;
	
	private String correctFile = "Property_Assessment_Data_2019.csv";
	
	@BeforeEach
	void testReadFile() throws Exception {
		//try to read the correct filename
		correctTestValues = Lab3Main.readFile(correctFile);
		correctResValues = Lab3Main.getClassData(correctTestValues, "residential");
	}

	@Test
	void testGetNum() {
		int numTestValues = Statistics.getNum(correctTestValues);
		int numResValues = Statistics.getNum(correctResValues);
		
		//test for correct for full data and residential data
		assertEquals(400541, numTestValues);
		assertEquals(373638, numResValues);
		
		//test for false for full data and residential data
		assertNotEquals(0, numTestValues);
		assertNotEquals(0, numResValues);
		
		//make sure they equal themselves
		assertEquals(numTestValues, numTestValues);
		assertEquals(numResValues, numResValues);
	}
	
	@Test
	void testGetMin() {
		int minTestValues = Statistics.getMin(correctTestValues);
		int minResValues = Statistics.getMin(correctResValues);
		
		//test for correct for full data and residential data
		assertEquals(0, minTestValues);
		assertEquals(0, minResValues);
		
		//test for false for full data and residential data
		assertNotEquals(1, minTestValues);
		assertNotEquals(1, minResValues);
		
		//make sure they equal themselves.
		assertEquals(minTestValues, minTestValues);
		assertEquals(minResValues, minResValues);
	}
	
	@Test
	void testGetMax() {
		int maxTestValues = Statistics.getMax(correctTestValues);
		int maxResValues = Statistics.getMax(correctResValues);
		
		//test for correct for full data and residential data
		assertEquals(1338809500, maxTestValues);
		assertEquals(42416000, maxResValues);
		
		//test for false for full data and residential data
		assertNotEquals(0, maxTestValues);
		assertNotEquals(0, maxResValues);
		
		//make sure they equal themselves.
		assertEquals(maxTestValues, maxTestValues);
		assertEquals(maxResValues, maxResValues);
	}
	
	@Test
	void testGetRange() {
		//test for correct for full data and residential data
		long rangeTestValues = Statistics.getRange(Statistics.getMax(correctTestValues), Statistics.getMin(correctTestValues));
		long rangeResValues = Statistics.getRange(Statistics.getMax(correctResValues), Statistics.getMin(correctResValues));
		
		assertEquals(1338809500, rangeTestValues);
		assertEquals(42416000, rangeResValues);
		
		//test for false for full data and residential data
		assertNotEquals(0, rangeTestValues);
		assertNotEquals(0, rangeResValues);
		
		//make sure they equal themselves.
		assertEquals(rangeTestValues, rangeTestValues);
		assertEquals(rangeResValues, rangeResValues);
	}
	
	@Test
	void testGetMean() {
		long meanTestValues = Statistics.getMean(correctTestValues);
		long meanResValues = Statistics.getMean(correctResValues);
		
		//test for correct for full data and residential data
		assertEquals(464922, meanTestValues);
		assertEquals(322185, meanResValues);
		
		//test for false for full data and residential data
		assertNotEquals(0, meanTestValues);
		assertNotEquals(0, meanResValues);
		
		//make sure they equal themselves.
		assertEquals(meanTestValues, meanTestValues);
		assertEquals(meanResValues, meanResValues);
	}
	
	@Test
	void testGetSD() {
		long sdTestValues = Statistics.getSD(correctTestValues, Statistics.getMean(correctTestValues));
		long sdResValues = Statistics.getSD(correctResValues, Statistics.getMean(correctResValues));
		
		//test for correct for full data and residential data
		assertEquals(3683363, sdTestValues);	
		assertEquals(299741, sdResValues);
		
		//test for false for full data and residential data
		assertNotEquals(0, sdTestValues);
		assertNotEquals(0, sdResValues);
		
		//make sure they equal themselves.
		assertEquals(sdTestValues, sdTestValues);
		assertEquals(sdResValues, sdResValues);
	}
	
	@Test
	void testGetMedian() {
		int medianTestValues = Statistics.getMedian(correctTestValues);
		int medianResValues = Statistics.getMedian(correctResValues);
		
		//test for correct for full data and residential data
		assertEquals(323000, medianTestValues);
		assertEquals(322000, medianResValues);
		
		//test for false for full data and residential data
		assertNotEquals(0, medianTestValues);
		assertNotEquals(0, medianResValues);
		
		//make sure they equal themselves.
		assertEquals(medianTestValues, medianTestValues);
		assertEquals(medianResValues, medianResValues);
	}
}