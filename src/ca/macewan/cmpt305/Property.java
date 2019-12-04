package ca.macewan.cmpt305;

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
