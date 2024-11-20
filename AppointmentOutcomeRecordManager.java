package Project.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Project.AppointmentOutcomeRecord;
import Project.InventoryV2;

public class AppointmentOutcomeRecordManager {
    private List<AppointmentOutcomeRecord> appointmentOutcomeRecords;
    private static final String OUTCOME_RECORDS_FILE = "/Users/vijay/Documents/Code/Java/Project/CSV Files/Appointment_Outcome_Record.csv";

    public AppointmentOutcomeRecordManager() {
        this.appointmentOutcomeRecords = new ArrayList<>();
        loadAppointmentOutcomeRecordsFromCSV();
    }

    public List<AppointmentOutcomeRecord> getAppointmentOutcomeRecords() {
        return this.appointmentOutcomeRecords;
    }

    // Load AppointmentOutcomeRecords from CSV
    public void loadAppointmentOutcomeRecordsFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(OUTCOME_RECORDS_FILE))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                appointmentOutcomeRecords.add(AppointmentOutcomeRecord.fromCSV(line));
            }
            System.out.println("Appointment outcome records loaded successfully from CSV.");
        } catch (FileNotFoundException e) {
            System.out.println("No outcome records file found. A new one will be created.");
        } catch (IOException e) {
            System.err.println("Error loading outcome records: " + e.getMessage());
        }
    }

    // Save AppointmentOutcomeRecords to CSV
    public void saveAppointmentOutcomeRecordsToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(OUTCOME_RECORDS_FILE))) {
            bw.write("AppointmentID,Date,TypeOfService,Prescription,PrescriptionStatus,PatientID\n");
            for (AppointmentOutcomeRecord record : appointmentOutcomeRecords) {
                bw.write(record.toCSV());
                bw.newLine();
            }
            System.out.println("Appointment outcome records saved successfully to CSV.");
        } catch (IOException e) {
            System.err.println("Error saving outcome records: " + e.getMessage());
        }
    }

    public void recordAppointmentOutcome(AppointmentManager appointmentManager){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter AppointmentID: ");
        String appointmentID = scanner.nextLine();
        System.out.print("Enter PatientID: ");
        String patientID = scanner.nextLine();
        System.out.print("Enter Date(yyyy-mm-dd): ");
        String date = scanner.nextLine();
        System.out.print("Enter Type of Service: ");
        String tos = scanner.nextLine();
        System.out.print("Enter Prescription: ");
        String prespriction = scanner.nextLine();
        
        AppointmentOutcomeRecord appointmentOutcomeRecord = new AppointmentOutcomeRecord(appointmentID, date, tos, prespriction, patientID);
        appointmentOutcomeRecords.add(appointmentOutcomeRecord);
        appointmentManager.removeAppointment(appointmentID);
        System.out.println("Appointment outcome recorded successfully.");
        System.out.println("\n======================================");
    }

    public void patientAppointmentOutcome(String patientID){
        boolean found = false; 
        for (AppointmentOutcomeRecord a : appointmentOutcomeRecords) {
            if (a.getPatientID().equals(patientID)) {
                a.displayOutcomeDetails();
                System.out.println();
                found = true; 
            }
        }
        if (!found) {
            System.out.println("Patient has no appointment outcome records.");
        }
        System.out.println("\n======================================");
    }

    public void pharAppointmentOutcomeDisplay(String patientID) {
        boolean found = false; 
        for (AppointmentOutcomeRecord a : appointmentOutcomeRecords) {
            if (a.getPatientID().equals(patientID)) {
                a.displayOutcomeDetails();
                System.out.println();
                found = true; 
            }
        }
        if (!found) {
            System.out.println("Patient has no appointment outcome records.");
        }
        System.out.println("\n======================================");
    }

    public void pharAppointmentOutcomeUpdate(String patientID, List<InventoryV2> inventory) {
        Scanner scanner = new Scanner(System.in); 

        boolean hasRecords = false;
        for (AppointmentOutcomeRecord a : appointmentOutcomeRecords) {
            if (a.getPatientID().equals(patientID) && !(a.isPrescriptionStatus())) {
                hasRecords = true;
                a.displayOutcomeDetails();
                break;
            }
        }
        System.out.println("\n======================================");
        if (!hasRecords) {
            System.out.println("No prescriptions needed for Patient");
            System.out.println("\n======================================");
            return; 
        }
    
        System.out.print("Enter AppointmentID to complete prescription: ");
        String appointmentID = scanner.nextLine();
        
    
        String medicine;
        for (AppointmentOutcomeRecord a : appointmentOutcomeRecords) {
            if (a.getAppointmentID().equals(appointmentID)) {
                medicine = a.getPrescription();
                for (InventoryV2 medicineName : inventory) {
                    if (medicineName.getMedicinename().equalsIgnoreCase(medicine)) {
                        System.out.print("Choose amount of medication: ");
                        int choice = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline
                        if (medicineName.getStock() > choice) {
                            medicineName.setStock(medicineName.getStock() - choice);
                            System.out.println("Medication dispensed successfully.");
                            a.setPrescriptionStatus(true);
                            System.out.println("\n======================================");
                        } else {
                            System.out.println("Not enough stock.");
                            System.out.println("\n======================================");
                        }
                        return; // Exit the loop once the medication is processed
                    }
                }
                System.out.println("Medication not found in inventory.");
            }
        }
        System.out.println("Appointment ID not found.");
    }
    

    public void display(){
        for (AppointmentOutcomeRecord a : appointmentOutcomeRecords){
            a.displayOutcomeDetails();
        }
    }
}
