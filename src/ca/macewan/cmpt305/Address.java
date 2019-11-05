package ca.macewan.cmpt305;

public class Address {
	private String suite;
	private String houseNum;
	private String streetName;
	
	public Address(String suite, String houseNum, String streetName) {
		this.suite = suite;
		this.houseNum = houseNum;
		this.streetName = streetName;
	}
	
	public String toString() {
		if (suite.length() > 0) {
			return suite + " " + houseNum + " " + streetName;
		}
		return houseNum + " " + streetName;
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof Address))
			return false;
		
		Address other = (Address) obj;
		return (this.streetName == other.streetName && this.houseNum == other.houseNum && this.suite == other.suite);
	}
	
	public int hashcode() {
		int result = (31 * streetName.hashCode());
		result = (31 * result * houseNum.hashCode());
		result = (31 * result * suite.hashCode());
		return result;
	}

}
