package Project.manager;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import Project.User.User;  // Import User class
import Project.User.Doctor;  // Import Doctor class
import Project.User.Administrator;  // Import Administrator class
import Project.User.Pharmacist;  // Import Pharmacist class
import Project.User.Patient;  // Import Patient class
import Project.InventoryV2;  // Import InventoryV2 class
import Project.CSVUtils;  // Import CSVUtils class (for loading CSV data)

public class LoginManager {
    private List<User> users;
    private List<Patient> patients;
    private Map<String, String> userCredentials; // Maps Name to Password
    private Map<String, String> userIDs; // Maps Name to UserID

    private static String ADMIN_VERIFICATION_CODE = "1234"; // Default verification code
    private static String ADMIN_USER_ID = "A001"; // Admin userID (for authentication)
    private static String ADMIN_PASSWORD = "password"; // Admin password (for authentication)

    public LoginManager() {
        this.users = new ArrayList<>();
        this.userCredentials = new HashMap<>();
        this.userIDs = new HashMap<>();
        this.patients = new ArrayList<>();
    }

    private String userfile = "/Users/vijay/Documents/Code/Java/Project/CSV Files/User.csv";
    private String stafffile = "/Users/vijay/Documents/Code/Java/Project/CSV Files/Staff_List.csv";
    private String patientfile = "/Users/vijay/Documents/Code/Java/Project/CSV Files/Patient_List.csv";

    public void loadUsersFromCSV() {
        CSVUtils.loadUserCredentials(userfile, userCredentials, userIDs); // Load UserID, Password, and Name from User.csv
        CSVUtils.loadStaffDetails(stafffile, userCredentials, userIDs, users);
        CSVUtils.loadPatientDetails(patientfile, userIDs, userCredentials, users); // Load other details from Patient.csv
    }
    
    public User login(String userID, String password) {
        for (User user : users) {
            if (user.getUserID().equals(userID) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public List<Patient> getPatients(){
        return this.patients;
    }

    // Method to change the verification code for the admin
    public void changeVerificationCode(Scanner scanner) {
        // Admin authentication
        System.out.print("Enter Admin UserID: ");
        String adminUserID = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String adminPassword = scanner.nextLine();

        // Check if the provided credentials match the admin's
        if (adminUserID.equals(ADMIN_USER_ID) && adminPassword.equals(ADMIN_PASSWORD)) {
            // Admin is authenticated
            System.out.print("Enter new verification code: ");
            String newVerificationCode = scanner.nextLine();

            // Update the verification code
            ADMIN_VERIFICATION_CODE = newVerificationCode;
            System.out.println("Verification code updated successfully.");
        } else {
            System.out.println("Invalid Admin credentials. Unable to change verification code.");
        }
    }

    public void startLoginInterface(List<InventoryV2> inventory, AppointmentManager appointmentManager, 
                                    AppointmentOutcomeRecordManager appointmentOutcomeRecordManager, 
                                    MedicalRecordManager medicalRecordManager, 
                                    AppointmentSchedulingManager appointmentSchedulingManager) {
        System.out.println("\n======================================");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("User Interface\n\n1: Login");
            System.out.println("2. Register");
            System.out.println("3: Admin Change Verification Code");
            System.out.println("4: Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.println("\n======================================");

            if (choice == 1) {
                // Login attempt limit (3 attempts)
                int attempts = 0;
                boolean loggedIn = false;

                String userID = ""; // Declare userID outside the loop to use it later

                // Check if the user has a suspicious login flag
                System.out.print("Enter UserID: ");
                userID = scanner.nextLine(); // Store userID for later use
                User user = getUserByID(userID);
                if (user != null && user.getSussyLogin()) {
                    // If the user has a suspicious login flag, prompt for verification code
                    System.out.println("Suspicious login detected. Please enter the verification code.");

                    String enteredCode = scanner.nextLine();
                    if (enteredCode.equals(ADMIN_VERIFICATION_CODE)) {
                        // Reset the suspicious flag
                        user.unsetSussyLogin();
                        System.out.println("Verification successful! You can now try logging in.");
                    } else {
                        // If incorrect code, deny login and exit the login attempt
                        System.out.println("Incorrect verification code. You cannot attempt login.");
                        continue; // Skip the rest of the login attempt logic
                    }
                }

                // If no suspicious login flag, proceed with login attempts
                while (attempts < 3 && !loggedIn) {
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();

                    user = login(userID, password);

                    if (user != null) {
                        System.out.println("\nLogin successful!");
                        System.out.println("Welcome, " + user.getName() + "!");
                        System.out.println("\n======================================");
                        System.out.println();

                        // Prompt for password change or proceed to the interface
                        boolean passwordChange = true;
                        while (passwordChange) {
                            System.out.println("1. Change Password");
                            System.out.println("2. Continue to interface ");
                            System.out.print("Enter Choice: ");
                            int choice2 = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("\n======================================");
                            if (choice2 == 1) {
                                System.out.print("Enter new password: ");
                                String newPassword = scanner.nextLine();
                                user.setPassword(newPassword);
                                passwordChange = false;
                                System.out.println("\n======================================");
                            } else if (choice2 == 2) {
                                System.out.println();
                                passwordChange = false;
                            }
                        }

                        // Switch based on user type
                        switch ((user instanceof Doctor ? "Doctor" : user instanceof Administrator ? "Administrator" : user instanceof Pharmacist ? "Pharmacist" : "Staff")) {
                            case "Doctor":
                                DoctorManager.DocInterface(user, appointmentManager, appointmentOutcomeRecordManager, appointmentSchedulingManager, medicalRecordManager);
                                break;
                            case "Administrator":
                                AdminManager.AdminInterface(user, inventory, appointmentManager, users);
                                break;
                            case "Pharmacist":
                                PharmacistManager.PharInterface(user, inventory, appointmentOutcomeRecordManager);
                                break;
                            default:
                                PatientManager.PatInterface(user, appointmentManager, appointmentOutcomeRecordManager, medicalRecordManager, users, appointmentSchedulingManager);
                                CSVUtils.savePatientsToCSV(patientfile, getUsers());
                                break;
                        }
                        loggedIn = true; // Successful login
                    } else {
                        attempts++;
                        System.out.println("Login failed. Please check your credentials.");
                        System.out.println("Remaining attempts: " + (3 - attempts));
                        System.err.println("\n======================================\n");
                    }
                }

                if (attempts == 3) {
                    // After 3 failed login attempts, ask for the verification code
                    System.out.println("You have reached the maximum number of login attempts.");
                    System.out.print("Please enter the verification code provided by the administrator: ");
                    String enteredCode = scanner.nextLine();

                    if (enteredCode.equals(ADMIN_VERIFICATION_CODE)) {
                        // If the code is correct, reset attempts and allow login again
                        System.out.println("Verification successful! You can now try logging in again.");
                        attempts = 0; // Reset attempts
                    } else {
                        // If the code is incorrect, set the "suspicious login" flag and warn the user
                        System.out.println("Incorrect verification code. Further attempts will be monitored.");
                        System.out.println("A warning has been sent to the user.");
                        for (User staff : users) {
                            if (staff.getUserID().equals(userID)) {
                                staff.setSussyLogin(); // Set suspicious login flag
                                break; // Stop searching once the user is found
                            }
                        }
                        // Exit the login process
                        System.out.println("Please contact support if you need further assistance.");
                    }
                }

            } else if (choice == 2) {
                registerPatient(scanner);
                System.out.println("\n======================================\n");
            } else if (choice == 3) {
                // Admin change verification code
                changeVerificationCode(scanner);
            } else if (choice == 4) {
                // Exit
                System.out.println("Exiting the program.");
                break;
            } else {
                System.out.println("Invalid option. Please choose 1, 2, 3, or 4.");
            }
        }
    }

    public User getUserByID(String userID) {
        for (User user : users) {
            if (user.getUserID().equals(userID)) {
                return user;
            }
        }
        return null;
    }

    public void registerPatient(Scanner scanner) {
        // Collect patient registration information
        System.out.println("Register a new Patient:");
        System.out.print("Enter userID: ");
        String userID = scanner.nextLine();

        // Check if userID already exists
        if (userCredentials.containsKey(userID)) {
            System.out.println("UserID already exists. Please choose a different one.");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter date of birth (yyyy-MM-dd): ");
        String dob = scanner.nextLine();

        System.out.print("Enter gender: ");
        String gender = scanner.nextLine();

        System.out.print("Enter blood type: ");
        String bloodType = scanner.nextLine();

        System.out.print("Enter contact info: ");
        String contactInfo = scanner.nextLine();

        // Calculate age from dob
        LocalDate birthDate = LocalDate.parse(dob);
        int age = Period.between(birthDate, LocalDate.now()).getYears();

        // Create a new Patient object
        Patient newPatient = new Patient(userID, password, name, age, gender, contactInfo, dob, userID, bloodType);

        // Add the new patient to the list
        users.add(newPatient); 

        System.out.println("Patient registered successfully: " + newPatient.getName());
    }
}
