# Vaccination-Tracker

A Java-based vaccination management system that tracks COVID-19 vaccines, vaccination sites, patient health records, and appointment bookings.

## Overview

This application provides a comprehensive solution for managing COVID-19 vaccination programs. It supports tracking of vaccine distributions across multiple sites, patient health records, appointment scheduling, and vaccine administration.

## Features

### Vaccine Management
- **Recognized Vaccines**: Tracks vaccines approved in Canada
  - mRNA-1273 (RNA; Moderna)
  - BNT162b2 (RNA; Pfizer/BioNTech)
  - Ad26.COV2.S (Non Replicating Viral Vector; Janssen)
  - AZD1222 (Non Replicating Viral Vector; Oxford/AstraZeneca)
- **Unrecognized Vaccines**: Can also track other vaccines not officially recognized
- **Vaccine Properties**: Code name, type, and manufacturer information

### Vaccination Site Management
- Create and manage multiple vaccination sites
- Set dose limits for each site
- Track vaccine distributions by manufacturer
- Support up to 4 different vaccine types per site
- Handle up to 200 appointments per site

### Health Records
- Track individual patient vaccination history
- Record vaccine doses received with date and location
- Support multiple doses per patient (configurable limit)
- Generate vaccination receipts

### Appointment Booking
- Book vaccination appointments for patients
- Automatic dose reservation upon booking
- Appointment status tracking (success/failure)
- Exception handling for insufficient vaccine doses

### Vaccine Distribution
- Add vaccine distributions to sites
- Track doses by manufacturer
- Automatic consolidation of doses from the same manufacturer
- Chronological tracking of distribution additions
- Exception handling for:
  - Unrecognized vaccine codes
  - Exceeding site dose limits

### Administration
- Administer vaccines to patients with booked appointments
- Automatic dose deduction from site inventory
- FIFO (First-In-First-Out) consumption of vaccine doses
- Record keeping with vaccine type, site, and date

## Project Structure

```
Vaccination-Tracker/
├── VaccinationTracker/
│   ├── src/
│   │   ├── model/
│   │   │   ├── Vaccine.java                              # Vaccine entity class
│   │   │   ├── VaccineDistribution.java                  # Distribution tracking
│   │   │   ├── VaccinationSite.java                      # Site management
│   │   │   ├── HealthRecord.java                         # Patient records
│   │   │   ├── UnrecognizedVaccineCodeNameException.java # Custom exception
│   │   │   ├── TooMuchDistributionException.java         # Custom exception
│   │   │   └── InsufficientVaccineDosesException.java    # Custom exception
│   │   └── junit_tests/
│   │       └── StarterTests.java                         # Unit tests
│   └── bin/                                              # Compiled classes
├── .gitignore                                            # Git ignore rules
└── README.md                                             # This file
```

## Requirements

- Java Development Kit (JDK) 8 or higher
- JUnit 4 for running tests

## Getting Started

### Compilation

To compile the project, navigate to the `VaccinationTracker/src` directory and run:

```bash
javac -d ../bin model/*.java junit_tests/*.java
```

This compiles the source files and places the compiled classes in the `bin` directory.

### Running Tests

To run the unit tests (from the `VaccinationTracker/src` directory):

**On Unix/Linux/macOS:**
```bash
java -cp ../bin:path/to/junit.jar:path/to/hamcrest.jar org.junit.runner.JUnitCore junit_tests.StarterTests
```

**On Windows:**
```bash
java -cp ..\bin;path\to\junit.jar;path\to\hamcrest.jar org.junit.runner.JUnitCore junit_tests.StarterTests
```

Note: Replace `path/to/junit.jar` and `path/to/hamcrest.jar` with the actual paths to your JUnit and Hamcrest JAR files.

## Usage Examples

### Creating a Vaccine

```java
Vaccine moderna = new Vaccine("mRNA-1273", "RNA", "Moderna");
Vaccine pfizer = new Vaccine("BNT162b2", "RNA", "Pfizer/BioNTech");
```

### Creating a Vaccination Site

```java
VaccinationSite site = new VaccinationSite("Downtown Clinic", 10000);
```

### Adding Vaccine Distributions

```java
try {
    site.addDistribution(moderna, 5000);
    site.addDistribution(pfizer, 3000);
} catch (UnrecognizedVaccineCodeNameException | TooMuchDistributionException e) {
    System.out.println(e.getMessage());
}
```

### Creating a Health Record

```java
HealthRecord patientRecord = new HealthRecord("John Doe", 5);
```

### Booking an Appointment

```java
try {
    site.bookAppointment(patientRecord);
    System.out.println(patientRecord.getAppointmentStatus());
} catch (InsufficientVaccineDosesException e) {
    System.out.println(e.getMessage());
}
```

### Administering Vaccines

```java
site.administer("2024-01-15");
System.out.println(patientRecord.getVaccinationReceipt());
```

## Exception Handling

The application includes three custom exceptions:

1. **UnrecognizedVaccineCodeNameException**: Thrown when attempting to add a distribution with an unrecognized vaccine code. Only vaccines with recognized code names (mRNA-1273, BNT162b2, Ad26.COV2.S, AZD1222) can be distributed to vaccination sites
2. **TooMuchDistributionException**: Thrown when adding a distribution would exceed the site's dose limit
3. **InsufficientVaccineDosesException**: Thrown when attempting to book an appointment with no available doses

## Key Design Features

- **Automatic Dose Consolidation**: When adding distributions from the same manufacturer, doses are automatically combined
- **FIFO Dose Consumption**: Vaccines are administered in the chronological order of their first distribution
- **Flexible Patient Limits**: Each health record can specify a maximum number of doses to track
- **Site Capacity Management**: Vaccination sites have configurable dose limits to prevent over-distribution

## License

This project is an educational implementation for learning Java and object-oriented programming concepts.
