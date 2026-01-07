package model;

public class HealthRecord {
	// attributes
	private String patient;
	private int limit;
	private Vaccine[] vaccines;
	private int numberOfVaccines;
	private String appointmentStatus;
	private String appointmentSite;
	private String[] records;
	
	// constructor
	/* 
	 * Create a health record with the patient name and 
	 * 	the limit on the number of doses appearing on their vaccination history.
	 */
	public HealthRecord(String patient, int limit) {
		this.patient = patient;
		this.limit = limit;
		this.vaccines = new Vaccine[this.limit];
		this.records = new String[this.limit];
	}
	
	// getters
	public String getPatient() {
		return this.patient;
	}
	
	public int getLimit() {
		return this.limit;
	}
	
	public Vaccine[] getPrivateVaccinesArray() {
		return this.vaccines;
	}
	
	public Vaccine[] getVaccines() {
		Vaccine[] nv = new Vaccine[this.numberOfVaccines];
		for (int i = 0; i < this.numberOfVaccines; i++) {
			nv[i] = this.vaccines[i];
		}
		return nv;
	}
	
	public String[] getPrivateRecordsArray() {
		return this.records;
	}
	
	public String[] getRecords() {
		String[] nr = new String[this.numberOfVaccines];
		for (int i = 0; i < this.numberOfVaccines; i++) {
			nr[i] = this.records[i];
		}
		return nr;
	}
	

	// setters
	public void setPatient(String patient) {
		this.patient = patient;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public void setAppointmentStatus(String appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}
	
	public void setAppointmentSite(String name) {
		this.appointmentSite = name;
	}

	/* 
	 * Retrieve vaccination history for the patient.
	 * A vaccination receipt contains:
	 * 	- The number of doses the patient has received
	 * 	- A semi-colon-separated list, where each item gives information about: 
	 * 		+ the vaccine info
	 * 		+ the name of vaccination site
	 * 		+ the date of vaccination
	 */
	public String getVaccinationReceipt() {
		if (this.numberOfVaccines == 0) {
			return String.format("%s has not yet received any doses.", getPatient());
		}
		else {
			String s = "";
			for (int i = 0; i < this.numberOfVaccines; i++) {
				if (i > 0) {
					s += "; ";
				}
				s += this.records[i];
			}
			return String.format("Number of doses %s has received: %d [%s]", getPatient(), this.numberOfVaccines, s);
		}
	}
	
	/* Retrieve appointments booked for the patient */
	/* 
	 * Patient's appointment status does not get changed by adding records.
	 * It's only changed when the `bookAppointment` method is invoked on a VaccinationSite object.
	 * (See below.) 
	 */
	public String getAppointmentStatus() {
		if (this.appointmentStatus == null) {
			return String.format("No vaccination appointment for %s yet", getPatient());
		}
		else if (this.appointmentStatus.equals("success")) {
			return String.format("Last vaccination appointment for %s with %s succeeded", getPatient(), this.appointmentSite);
		}
		else {
			return String.format("Last vaccination appointment for %s with %s failed", getPatient(), this.appointmentSite);
		}
	}
	
	/* 
	 * Add a record for the patient's 1st received dose.
	 * Each record contains:
	 * 	- the vaccine reference
	 * 	- the name of vaccination site
	 * 	- the date of receiving the dose  
	 */
	public void addRecord(Vaccine v, String vaccinationSite, String dateOfDose) {
		this.vaccines[this.numberOfVaccines] = v;
		this.records[this.numberOfVaccines] = String.format("%s in %s on %s", v.toString(), vaccinationSite, dateOfDose);
		this.numberOfVaccines++;
	}
}
