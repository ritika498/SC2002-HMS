package Project.manager;

import Project.User.Patient;
import Project.User.User;
import Project.MedicalRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class PatientManager {

    private static final String APPOINTMENT_OUTCOME_CSV = "/Users/vijay/Documents/Code/Java/Project/CSV Files/Appointment_Outcome_Record.csv";
    private static final String MEDICAL_RECORD_CSV = "/Users/vijay/Documents/Code/Java/Project/CSV Files/Medical_Record.csv";

    // Load unique patient IDs from AppointmentOutcomeRecords.csv
    public List<String> getPatientIDsFromAppointmentRecords() {
        List<String> patientIDs = new ArrayList<>();
        Set<String> uniquePatientIDs = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_OUTCOME_CSV))) {
            br.readLine(); // Skip header
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 1) {
                    String patientID = values[0].trim();

                    if (uniquePatientIDs.add(patientID)) {
                        patientIDs.add(patientID);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Appointment Outcome CSV: " + e.getMessage());
        }

        return patientIDs;
    }

    // Load detailed patient information from MedicalRecords.csv
    public List<Patient> getPatientsFromMedicalRecords() {
        List<Patient> patients = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(MEDICAL_RECORD_CSV))) {
            br.readLine(); // Skip header
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 5) {
                    String patientID = values[0];
                    String name = values[1];
                    int age = Integer.parseInt(values[2]);
                    String gender = values[3];
                    String bloodType = values[4];

                    MedicalRecord record = new MedicalRecord(patientID);
                    Patient patient = new Patient("dummyUserID", "dummyPassword", name, age, gender, "dummyContactInfo", "dummyDOB", patientID, bloodType, record);
                    patients.add(patient);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading medical records: " + e.getMessage());
        }

        return patients;
    }

    // Patient Interface
    public static void PatInterface(User user, AppointmentManager appointmentManager, AppointmentOutcomeRecordManager appointmentOutcomeRecordManager, MedicalRecordManager medicalRecordManager, List<User> users, AppointmentSchedulingManager appointmentSchedulingManager) {
        Scanner scanner = new Scanner(System.in);
        Patient patient = (Patient) user;      

        int choice;
        while (true) {
            System.out.println("\nPatient Interface\n");
            System.out.println("1: View Medical Record");
            System.out.println("2: View Personal Information");
            System.out.println("3: Update Personal Information");
            System.out.println("4: Manage Appointments");
            System.out.println("5. View Appointment Outcome Record");
            System.out.println("6: LogOut");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            System.out.println("\n======================================");

            switch (choice) {
                case 1 -> {
                    medicalRecordManager.medicalRecordDisplay(patient.getPatientID());
                }
                case 2 ->{
                    patient.dispayDetails();
                    System.out.println("\n======================================");
                }
                case 3 -> {
                    updatePatientDetails(patient, scanner);
                    System.out.println("\n======================================");
                
                }
                case 4 -> appointmentSchedulingManager.PatientAppoinmentScheduleInterface(patient, appointmentManager, users);
                case 5 -> {
                    appointmentOutcomeRecordManager.patientAppointmentOutcome(patient.getPatientID());
                    
                }
                case 6 -> {
                    return;  // Exit the loop and log out
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    // Update Patient Details
    private static void updatePatientDetails(Patient patient, Scanner scanner) {
        System.out.println("Enter new contact information: ");
        String newContact = scanner.nextLine();
        patient.setContactInfo(newContact);
        System.out.println("Contact information updated successfully.");
    }
}

