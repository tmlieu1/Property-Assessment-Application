package ca.macewan.cmpt305;

public class Location {
	private double latitude;
	private double longitude;
	
	public Location (double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public String toString() {
		return "(" + latitude + ", " + longitude + ")";
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof Location))
			return false;
		
		Location otherLoc = (Location) obj;
		return (this.latitude == otherLoc.latitude &&
				this.longitude == otherLoc.longitude);
	}
	
}
//hash over
