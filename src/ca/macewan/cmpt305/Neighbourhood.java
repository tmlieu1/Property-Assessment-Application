package ca.macewan.cmpt305;

public class Neighbourhood {
	private String nbhID;
	private String nbhName;
	private String nbhWard;
	
	public Neighbourhood(String nbhID, String nbhName, String nbhWard) {
		this.nbhID = nbhID;
		this.nbhName = nbhName;
		this.nbhWard = nbhWard;
	}
	
	public String toString() {
		return nbhName + " (" + nbhWard + ")";
	}
	
	public String getNBHName() {
		return nbhName;
	}
	public String getNBHWard() {
		return nbhWard;
	}
	public boolean equals(Object obj) {
		if (!(obj instanceof Neighbourhood))
			return false;
		
		Neighbourhood other = (Neighbourhood) obj;
		return (this.nbhName.toUpperCase() == other.nbhName.toUpperCase());
	}
	
	public int hashcode() {
		int result = (31 * nbhID.hashCode());
		result = (31 * result * nbhName.hashCode());
		result = (31 * result * nbhWard.hashCode());
		return result;
	}
}
