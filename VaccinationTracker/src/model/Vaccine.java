package model;

public class Vaccine {
	// attributes
	private String codeName;
	private String type;
	private String manufacturer;
	
	// list of recognized vaccine code names
	private static final String[] RECOGNIZED_CODES = {"mRNA-1273", "BNT162b2", "Ad26.COV2.S", "AZD1222"};
	
	
	/* 
	 * Initialize records of vaccines recognized in Canada. 
	 * Input values of the constructor consist of the codename, type, and manufacturer. 
	 * No error handling is necessary for these input values.
	 * See Section 2.3 of the lab manual for the list of recognized vaccines.
	 */ 
	public Vaccine(String codeName, String type, String manufacturer) {
		this.codeName = codeName;
		this.type = type;
		this.manufacturer = manufacturer;
	}
	
	// getters 
	public String getCodeName() {
		return this.codeName;
	}

	public String getType() {
		return this.type;
	}
	
	public String getManufacturer() {
		return this.manufacturer;
	}
	
	public boolean isRecognized(String codeName) {
		for (int i = 0; i < RECOGNIZED_CODES.length; i++) {
			if (RECOGNIZED_CODES[i].equals(codeName)) {
				return true;
			}
		}
		return false;
	}
	
	// setters
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	
	// toString method
	public String toString() {
		if (isRecognized(getCodeName()) == true) {
			return String.format("Recognized vaccine: %s (%s; %s)", getCodeName(), getType(), getManufacturer());
		}
		else {
			return String.format("Unrecognized vaccine: %s (%s; %s)", getCodeName(), getType(), getManufacturer());
		}
		
	}

	
	

	

	

	

	
}
