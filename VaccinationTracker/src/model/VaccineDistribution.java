package model;

public class VaccineDistribution {
	// attributes
	private Vaccine vaccine;
	private int doses;
	
	// constructors
	public VaccineDistribution(Vaccine vaccine, int doses) {
		this.vaccine = vaccine;
		this.doses = doses;
	}
	
	// getters
	public Vaccine getVaccine() {
		return this.vaccine;
	}

	public int getDoses() {
		return this.doses;
	}
	
	// setters
	public void setVaccine(Vaccine vaccine) {
		this.vaccine = vaccine;
	}
	
	public void setDoses(int doses) {
		this.doses = doses;
	}
	
	// toString method
	public String toString() {
		return String.format("%d doses of %s by %s", getDoses(), getVaccine().getCodeName(), getVaccine().getManufacturer());
	}
}
