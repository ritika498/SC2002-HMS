package Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MedicalRecord {
    private String patientID;
    private List<String> diagnoses;
    private List<String> treatments;
    private List<String> medications;

    // Constructor
    public MedicalRecord(String patientID) {
        this.patientID = patientID;
        this.diagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
        this.medications = new ArrayList<>();
    }

    public MedicalRecord() {
        this.diagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
        this.medications = new ArrayList<>();
    }
    // Getters
    public String getPatientID() {
        return patientID;
    }

    public List<String> getDiagnoses() {
        return diagnoses;
    }

    public List<String> getTreatments() {
        return treatments;
    }

    public List<String> getMedications() {
        return medications;
    }

    // Method to add a diagnosis to the record
    public void addDiagnosis(String diagnosis) {
        diagnoses.add(diagnosis);
        System.out.println("Diagnosis added: " + diagnosis);
    }

    // Method to add a treatment to the record
    public void addTreatment(String treatment) {
        treatments.add(treatment);
        System.out.println("Treatment added: " + treatment);
    }

    // Method to add medication to the record
    public void addMedication(String medication) {
        medications.add(medication);
        System.out.println("Medication added: " + medication);
    }

    // Method to remove a diagnosis from the record
    public void removeDiagnosis(String diagnosis) {
        if (diagnoses.contains(diagnosis)) {
            diagnoses.remove(diagnosis);
            System.out.println("Diagnosis removed: " + diagnosis);
        } else {
            System.out.println("Diagnosis not found: " + diagnosis);
        }
    }

    // Method to remove a treatment from the record
    public void removeTreatment(String treatment) {
        if (treatments.contains(treatment)) {
            treatments.remove(treatment);
            System.out.println("Treatment removed: " + treatment);
        } else {
            System.out.println("Treatment not found: " + treatment);
        }
    }

    // Method to remove medication from the record
    public void removeMedication(String medication) {
        if (medications.contains(medication)) {
            medications.remove(medication);
            System.out.println("Medication removed: " + medication);
        } else {
            System.out.println("Medication not found: " + medication);
        }
    }


    // Convert to CSV format
    public String toCSV() {
        return String.join(";",
                patientID,
                String.join("|", diagnoses),
                String.join("|", treatments),
                String.join("|", medications)
        );
    }

    // Create from CSV string
    public static MedicalRecord fromCSV(String csvRow) {
        String[] values = csvRow.split(";");
        MedicalRecord record = new MedicalRecord();
        record.patientID = values[0].trim();

        // Ensure modifiable lists
        record.diagnoses = new ArrayList<>(Arrays.asList(values[1].split("\\|")));
        record.treatments = new ArrayList<>(Arrays.asList(values[2].split("\\|")));
        record.medications = new ArrayList<>(Arrays.asList(values[3].split("\\|")));

        return record;
    }
    // Method to display the entire medical record
    public void displayMedicalRecord() {
        System.out.println("Medical Record for Patient ID: " + patientID);
        System.out.println();

        System.out.println("Diagnoses:");
        for (String diagnosis : diagnoses) {
            System.out.println("\t- " + diagnosis);
        }

        System.out.println("Treatments:");
        for (String treatment : treatments) {
            System.out.println("\t- " + treatment);
        }

        System.out.println("Medications:");
        for (String medication : medications) {
            System.out.println("\t- " + medication);
        }
    }
}