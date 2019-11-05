package ca.macewan.cmpt305;
//Account Number,Suite,House Number,Street Name,Assessed Value,Assessment Class,Neighbourhood ID,Neighbourhood,Ward,Garage,Latitude,Longitude
//10202374,,455,MCCONACHIE WAY NW,381500,Residential,2521,MCCONACHIE AREA,Ward 3,Y,53.6308420714181,-113.428512065557
//9969312,,15519,103 STREET NW,463500,Residential,3040,BEAUMARIS,Ward 2,Y,53.6168355459535,-113.50036842843
public class Property implements Comparable<Property> {
	private int accountNum;
	private Address address;
	private int assessedVal;
	private String assessedClass;
	private Neighbourhood neighbourhood;
	private Location location;
	
	public Property (int accountNum, Address address, int assessedVal,
						String assessedClass, Neighbourhood neighbourhood, Location location) {
		this.accountNum = accountNum;
		this.address = address;
		this.assessedVal = assessedVal;
		this.assessedClass = assessedClass;
		this.neighbourhood = neighbourhood;
		this.location = location;
	}
	
	public int getAccountNum() {
		return accountNum;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public int getAssessedVal() {
		return assessedVal;
	}
	
	public String getAssessedClass() {
		return assessedClass;
	}
	
	public boolean classEquals(String other) {
		if (assessedClass.toUpperCase().equals(other.toUpperCase())) {
			return true;
		}
		return false;
	}
	
	public Neighbourhood getNeighbourhood() {
		return neighbourhood;
	}
	
	public String getNBHName() {
		return neighbourhood.getNBHName();
	}
	
	public boolean nameEquals(String other) {
		if (neighbourhood.getNBHName().equals(other)) {
			return true;
		}
		return false;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public String toString() {
		return "Account number = " + accountNum + "\nAddress = " + address +
				"\nAssessed Value = $" + assessedVal + "\nAssessment Class = " + assessedClass +
				"\nNeighbourhood = " + neighbourhood + "\nLocation = " + location;
	}
	
	//what to compare: assessedVal
	public int compareTo (Property other) {
		if (this.assessedVal > other.assessedVal) {
			return 1;
		}
		else if (this.assessedVal < other.assessedVal) {
			return -1;
		}
		return 0;
	}
}
