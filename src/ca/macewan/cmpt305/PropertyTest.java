package ca.macewan.cmpt305;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PropertyTest {
	private Property p1;
	private Address a1;
	private Neighbourhood n1;
	private Location l1;
	
	private Property p2;
	private Address a2;
	private Neighbourhood n2;
	private Location l2;
	@BeforeEach
	void setUp() throws Exception {
		a1 = new Address("", "455", "MCCONACHIE WAY NW");
		n1 = new Neighbourhood("2521","MCCONACHIE AREA","Ward 3");
		l1 = new Location(53.6308420714181,-113.428512065557);
		p1 = new Property(10202374, a1, 381500, "Residential", n1, l1);
				
		a2 = new Address("", "15519", "103 STREET NW");
		n2 = new Neighbourhood("3040", "BEAUMARIS", "Ward 2");
		l2 = new Location(53.6168355459535,-113.50036842843);
		p2 = new Property(9969312, a2, 463500, "Residential", n2, l2);
	}

	@Test
	void testGetAccountNum() {
		//test that we get the correct value for accountNum
		assertEquals(10202374, p1.getAccountNum());
		//test that p1 and p2 have different accountNums
		assertNotEquals(p2.getAccountNum(),p1.getAccountNum());
		//test that accountnum is reflexive
		assertEquals(p1.getAccountNum(), p1.getAccountNum());
		
	}

	@Test
	void testGetAddress() {
		//test that addresses are equal.
		assertEquals(new Address("", "455", "MCCONACHIE WAY NW"), p1.getAddress());
		//test that p1 and p2 have different addresses
		assertNotEquals(p2.getAddress(), p1.getAddress());
		//test that address is reflexive
		assertEquals(p1.getAddress(),p1.getAddress());
	}

	@Test
	void testGetAssessedVal() {
		//test that our assessed value is correct
		assertEquals(381500, p1.getAssessedVal());
		//test that p2 and p1 have different assessed values
		assertNotEquals(p2.getAssessedVal(), p1.getAssessedVal());
		//test our assessed value is reflexive
		assertEquals(p1.getAssessedVal(), p1.getAssessedVal());
	}

	@Test
	void testGetAssessedClass() {
		//test that our assessed class is correct
		assertEquals("Residential", p1.getAssessedClass());
		//test that our assessed class does not match with Non Residential
		assertNotEquals("Non Residential", p1.getAssessedClass());
		//test that our two properties have the same assessed class
		assertEquals(p2.getAssessedClass(), p1.getAssessedClass());
		
	}

	@Test
	void testClassEquals() {
		//check our equals for residential class
		assertTrue(p1.classEquals("Residential"));
		//check our equals for residential class in false case
		assertFalse(p1.classEquals("Non Residential"));
	}

	@Test
	void testGetNeighbourhood() {
		//test that our neighbourhood equals is correct
		assertEquals(new Neighbourhood("2521","MCCONACHIE AREA","Ward 3"), p1.getNeighbourhood());
		//test that our neighbourhood equals is correct in false case
		assertNotEquals(p2.getNeighbourhood(), p1.getNeighbourhood());
		//test neighbourhood reflexivity
		assertEquals(p1.getNeighbourhood(), p1.getNeighbourhood());
	}

	@Test
	void testGetNBHName() {
		//test that our neighbourhood name is correct
		assertEquals("MCCONACHIE AREA", p1.getNBHName());
		//test our neighbourhood name is incorrect
		assertNotEquals("DOWNTOWN", p1.getNBHName());
		//test neighbourhood name reflexivity
		assertEquals(p1.getNBHName(), p1.getNBHName());
	}

	@Test
	void testNameEquals() {
		//test that our neighbourhood name equals is working
		assertTrue(p1.nameEquals("MCCONACHIE AREA"));
		//test that our neighbourhood name equals is working for false case
		assertFalse(p1.nameEquals("DOWNTOWN"));
	}

	@Test
	void testGetLocation() {
		//test that our location equals is working
		assertEquals(new Location(53.6308420714181, -113.428512065557), p1.getLocation());
		//test that our locations shouldn't match
		assertNotEquals(p2.getLocation(), p1.getLocation());
		//test location reflexivity
		assertEquals(p1.getLocation(), p1.getLocation());
	}

	@Test
	void testToString() {
		//test that our toString returns the right values altogether.
		String corrString = "Account number = " + 10202374 + "\nAddress = " + "455 MCCONACHIE WAY NW" +
		"\nAssessed Value = $" + 381500 + "\nAssessment Class = Residential" + 
		"\nNeighbourhood = MCCONACHIE AREA (Ward 3)" + "\nLocation = (53.6308420714181, -113.428512065557)";
		assertEquals(corrString, p1.toString());
		//test that our toString for p2 doesn't match p1
		assertNotEquals(p2.toString(), p1.toString());
		//test reflexivity
		assertEquals(p1.toString(), p1.toString());
	}
}
