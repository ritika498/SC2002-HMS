package Project.manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.*;

import Project.MedicalRecord;

public class MedicalRecordManager {
    private List<MedicalRecord> medicalRecords;
    private static final String MEDICAL_RECORDS_FILE = "/Users/vijay/Documents/Code/Java/Project/CSV Files/Medical_Record.csv";

    public MedicalRecordManager() {
        this.medicalRecords = new ArrayList<>();
        readFromCSV(MEDICAL_RECORDS_FILE);
    }

    // Write a list of MedicalRecord objects to a CSV file
    public void writeToCSV() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(MEDICAL_RECORDS_FILE))) {
            // Write the header row
            writer.write("PatientID;Diagnoses;Treatment;Medication");
            writer.newLine();  // Add a newline after the header
    
            // Write the medical records
            for (MedicalRecord record : medicalRecords) {
                System.out.println("Saving Medical Record");
                // Convert the MedicalRecord object to CSV format and write it to the file
                writer.write(record.toCSV());
                writer.newLine();  // Add a newline after each record
            }
    
            System.out.println("Data successfully written to file");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public void readFromCSV(String filePath) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            boolean isFirstLine = true; // To skip the header
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip the header
                    continue;
                }
                medicalRecords.add(MedicalRecord.fromCSV(line));
            }
            System.out.println("Data successfully read from " + filePath);
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }


    public List<MedicalRecord> getMedicalRecords(){
        return this.medicalRecords;
    }
    public void medicalRecordDisplay(String PatientID){
        boolean found = false;
        for (MedicalRecord medicalRecord : medicalRecords){
            if (medicalRecord.getPatientID().equals(PatientID)){
                found = true;
                medicalRecord.displayMedicalRecord();
            }
        }
        if (!found){
            System.err.println("\nMedical Record does not Exist");
            System.out.println("\n======================================");
            return;
        }
        System.out.println("\n======================================");
    }

public void updateMedicalRecord(String patientID) {
    Scanner scanner = new Scanner(System.in);

    // Find the correct medical record for the given patient ID
    for (MedicalRecord medicalRecord : getMedicalRecords()) {
        if (medicalRecord.getPatientID().equals(patientID)) {

            // Display existing data
            System.out.println("Existing Diagnoses: " + medicalRecord.getDiagnoses());
            System.out.println("Existing Treatments: " + medicalRecord.getTreatments());
            System.out.println("Existing Medications: " + medicalRecord.getMedications());

            // Clear the existing data
            medicalRecord.getDiagnoses().clear();
            medicalRecord.getTreatments().clear();
            medicalRecord.getMedications().clear();

            // Collect new data
            System.out.print("Enter new Diagnosis: ");
            String diagnosis = scanner.nextLine();
            System.out.print("Enter new Treatment: ");
            String treatment = scanner.nextLine();
            System.out.print("Enter new Medication: ");
            String medication = scanner.nextLine();

            // Update the record
            medicalRecord.addDiagnosis(diagnosis);
            medicalRecord.addTreatment(treatment);
            medicalRecord.addMedication(medication);

            System.out.println("Medical record updated successfully!");
            System.out.println("\n======================================");
            return; // Exit after updating the relevant record
        }
    }

    // If no record found for the given Patient ID
    System.out.println("No medical record found for Patient ID: " + patientID);
}


    
    // Create a new medical record
    public void createMedicalRecord() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Patient ID: ");
        String patientID = scanner.nextLine();

        for (MedicalRecord record : medicalRecords) {
            if (record.getPatientID().equals(patientID)) {
                System.out.println("A medical record already exists for this Patient ID.");
                System.out.println("\n======================================");
                return;
            }
        }

        MedicalRecord newRecord = new MedicalRecord(patientID);

        System.out.println("Enter Diagnoses (separated by commas): ");
        String diagnosesInput = scanner.nextLine();
        newRecord.getDiagnoses().addAll(Arrays.asList(diagnosesInput.split(",")));

        System.out.println("Enter Treatments (separated by commas): ");
        String treatmentsInput = scanner.nextLine();
        newRecord.getTreatments().addAll(Arrays.asList(treatmentsInput.split(",")));

        System.out.println("Enter Medications (separated by commas): ");
        String medicationsInput = scanner.nextLine();
        newRecord.getMedications().addAll(Arrays.asList(medicationsInput.split(",")));

        medicalRecords.add(newRecord);
        System.out.println("New medical record created successfully!");
        System.out.println("\n======================================");
    }


    public void doctorInterface(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nMedical Record Interface\n");
        System.out.println("1: View all Medical Record");
        System.out.println("2: Update Medical Record");
        System.out.println("3: Create Medical Record");
        System.out.println("4: Exit");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println("\n======================================");

        if (choice == 1) {
            System.out.print("Enter PatientID to view: ");
            String patientID = scanner.nextLine();
            medicalRecordDisplay(patientID);

        } else if (choice == 2) {
            System.out.print("Enter PatientID to view: ");
            String patientID = scanner.nextLine();
            updateMedicalRecord(patientID);
        } else if (choice == 3) {
            createMedicalRecord();
        } else if (choice == 4) {
            return;
        }else {
            System.out.println("Invalid option");
        }
    }
}
