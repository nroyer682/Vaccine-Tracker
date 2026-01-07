package model;

public class VaccinationSite {
	// attributes
	private String name;
	private int limit;
	private VaccineDistribution[] distributions;
	private int numberOfDoses;
	private int numberOfDistributions;
	private final int MAX_APPOINTMENTS = 200;
	private final int MAX_VACCINE_KINDS = 4;
	private int dosesReserved;
	private HealthRecord[] records;
	
	
	/* 
	 * Create a vaccination site with its name and
	 * the limit on the number of doses accumulated from the added distributions.
	 */
	public VaccinationSite(String name, int limit) {
		this.name = name;
		this.limit = limit;
		this.distributions = new VaccineDistribution[this.MAX_VACCINE_KINDS]; // Array of vaccine distribution objects
		this.records = new HealthRecord[this.MAX_APPOINTMENTS]; // Array of health record objects
	}
	
	// getters
	public String getName() {
		return this.name;
	}
	
	public int getLimit() {
		return this.limit;
	}
	
	public int getNumberOfDistributions() {
		return this.numberOfDistributions;
	}
	
	// FIXME
	public HealthRecord[] getRecords() {
		return this.records;
	}
	
	public VaccineDistribution[] getPrivateDistributionsArray() {
		return this.distributions;
	}
	
	public VaccineDistribution[] getDistributions() {
		VaccineDistribution[] nvd = new VaccineDistribution[this.numberOfDistributions];
		for (int i = 0; i < this.numberOfDistributions; i++) {
			nvd[i] = this.distributions[i];
		}
		return nvd;
	}
	
	// setters
	public void setName(String name) {
		this.name = name;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	/* Retrieve the number of available doses at the vaccination site */
	public int getNumberOfAvailableDoses() {
		return this.numberOfDoses;
	}
	
	/* 
	 * When inquiring about the supply of a kind of vaccines,
	 * 	it is assumed that the codename is used. 
	 */
	public int getNumberOfAvailableDoses(String codeName) {
		int index = -1;
		for (int i = 0; i < this.numberOfDistributions; i++) {
			VaccineDistribution vd = this.distributions[i]; // get vaccine distribution object at index i
			// if the code name associated with the current vaccine distribution object equals the argument code name, ...
			if (vd.getVaccine().getCodeName().equals(codeName)) {
				index = i; // ... then assign index with the index of the current vaccine distribution object.
			}
		}
		// if the index variable is unchanged (i.e., no vaccine distribution object satisfies the search criteria, ...
		if (index < 0) {
			return 0; // ... then there are 0 available doses with the specified code name
		}
		// otherwise...
		else {
			return this.distributions[index].getDoses(); // ... return the number of available doses with the specified code name
		}
	}
	
	public void addDistribution(Vaccine v, int doses) 
		throws UnrecognizedVaccineCodeNameException, TooMuchDistributionException {
		if (!v.isRecognized(v.getCodeName())) {
			throw new UnrecognizedVaccineCodeNameException(
					"Vaccine code name not recognized: " + v.getCodeName()
					);
		}
		
		if ((this.numberOfDoses + doses) > this.limit) {
			throw new TooMuchDistributionException(
					"Adding " + doses + " doses exceeds limit " + this.limit
					);
		}
			
		boolean found = false; 
		VaccineDistribution vd = new VaccineDistribution(v, doses); // create a new vaccine distribution object with parameters v and doses
		// loop through the distributions array
		for (int i = 0; i < this.numberOfDistributions; i++) {
			// if the manufacturer of the new vaccine distribution object is the same as a previous vaccine distribution object, ...
			if (vd.getVaccine().getManufacturer().equals(this.distributions[i].getVaccine().getManufacturer())) {
				found = true;
				int currDoses = this.distributions[i].getDoses(); // get the current number of doses for the vaccine with the same manufacturer
				this.distributions[i].setDoses(currDoses + doses); // ... then add the number of doses of the new vaccine distribution object to the previous one 
			}
		}
		this.numberOfDoses += doses; // increment the total number of doses by the specified doses
		// if there is no previous vaccine distribution with the same manufacturer, ...
		if (found == false) {
			this.distributions[this.numberOfDistributions] = vd; // ... then add the new vaccine distribution object to the array
			this.numberOfDistributions++; // increment the total number of distributions in the array
		}
	}
	
	public void bookAppointment(HealthRecord record) 
		throws InsufficientVaccineDosesException {
		if (this.numberOfDoses == 0 || this.dosesReserved == this.numberOfDoses) {
			record.setAppointmentStatus("fail"); // appointment cannot be booked and appointment status is updated accordingly
			record.setAppointmentSite(name);
			throw new InsufficientVaccineDosesException(
					"There are 0 doses available"
					);
		}
		record.setAppointmentStatus("success"); // appointment is successfully booked and appointment status is updated accordingly
		record.setAppointmentSite(name); // the vaccination sit of the appointment is recorded so that it can be accesses within the HealthRecord class
		this.records[dosesReserved] = record; // add the health record object the array
		this.dosesReserved++; // increment the number of doses reserved
	}
	
	/* 
	 * When administering vaccines doses for appointments,
	 * 	the order in which doses are consumed corresponds to 
	 * 	the chronological order in which their first-distributions were added. 
	 * 	(That is, for the purpose of this lab, we do not worry about receiving doses from the same kind of vaccine for each patient).
	 * 
	 * From the above example, AZ will be consumed first, then Moderna, and finally Pfizer. 
	 */
	public void administer(String dateOfDose) {
		// Loop through the records array for appointments booked
		for (int i = 0; i < this.dosesReserved; i++) {
			HealthRecord record = this.records[i]; // get the health record object at the current index
			this.numberOfDoses--; // decrement the total number of doses accumulated by adding distributions
			
			// find the index of the first vaccine with at least 1 dose available
			int index = -1;
			for (int j = 0; j < this.numberOfDistributions; j++) {
				if (this.distributions[j].getDoses() > 0) {
					index = j;
					break;
				}
			}
			Vaccine v = this.distributions[index].getVaccine(); // first vaccine in the distributions array with at least 1 dose available
			int currDoses = this.distributions[index].getDoses(); // get the current number of doses of the vaccine
			this.distributions[index].setDoses(currDoses - 1); // set the doses to decrement the current number of doses for the vaccine
			record.addRecord(v, getName(), dateOfDose); // add record for the administration of the vaccine for the specific patient
		}
		this.dosesReserved = 0;
	}
	
	
	/* 
	 * The string description of a vaccination site includes:
	 * 	1) its name
	 * 	2) total supply
	 * 	3) supplies of available kinds of vaccines (each displayed with their manufacturer; see below)
	 * 	
	 * 	Note. For 3), the order in which these supplies are reported corresponds to 
	 * 			the chronological order of their first-added distributions.
	 * 		  See the remaining test for an example. See the next test method for contrast.
	 */
	public String toString() {
		String manufacturer = "";
		for (int i = 0; i < this.numberOfDistributions; i++) {
			manufacturer += this.distributions[i].getDoses();
			manufacturer += " doses of ";
			manufacturer += this.distributions[i].getVaccine().getManufacturer();
			
			if (i != this.numberOfDistributions - 1) {
				manufacturer += ", ";
			}
		}
		
		return String.format("%s has %d available doses: <%s>", getName(), getNumberOfAvailableDoses(), manufacturer);
	}
}
