package Project;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

import Project.User.User;  // Import User class
import Project.User.Doctor;  // Import Doctor class
import Project.User.Administrator;  // Import Administrator class
import Project.User.Pharmacist;  // Import Pharmacist class
import Project.User.Patient;  // Import Patient class
import Project.CSVUtils;  // Import CSVUtils class (for loading CSV data)

public class CSVUtils {

    // Load User Credentials
    public static void loadUserCredentials(String fileName, Map<String, String> userCredentials, Map<String, String> userIDs) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 3) {
                    String userID = values[0];
                    String password = values[1];
                    String name = values[2];
                    userCredentials.put(userID, password);  // Store credentials by userID
                    userIDs.put(name, userID);  // Store userID by name
                    System.out.printf(userID,password,name,"\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading User CSV file: " + e.getMessage());
        }
    }

    //Save USer Credentials
    public static void saveUserCredentialsToCSV(String filePath, List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            // Write CSV header
            bw.write("UserID,Password,Name");
            bw.newLine();
    
            // Write each user's credentials to the CSV
            for (User user : users) {
                bw.write(String.join(",",
                        user.getUserID(),
                        user.getPassword(),
                        user.getName()
                ));
                bw.newLine();
            }
    
            System.out.println("User credentials saved successfully to CSV.");
        } catch (IOException e) {
            System.err.println("Error saving user credentials to CSV: " + e.getMessage());
        }
    }
    
    // Load Staff Details
    public static void loadStaffDetails(String fileName, Map<String, String> userCredentials, Map<String, String> userIDs, List<User> users) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6) {
                    String staffID = values[0];
                    String name = values[1];
                    String role = values[2];
                    String gender = values[3];
                    int age = Integer.parseInt(values[4]);
                    String contactInfo = values[5];

                    String userID = userIDs.get(name);
                    String password = userCredentials.get(userID);

                    if (userID != null && password != null) {
                        User user = createStaffUser(role, userID, password, name, age, gender, contactInfo, staffID);
                        if (user != null) {
                            users.add(user);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Staff List CSV file: " + e.getMessage());
        }
    }

    // Save Staff to CSV
    public static void saveStaffToCSV(String filePath, List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            // Write CSV header
            bw.write("StaffID,Name,Role,Gender,Age,ContactInfo");
            bw.newLine();

            // Iterate over users and write staff details
            for (User user : users) {
                if (user instanceof Doctor || user instanceof Administrator || user instanceof Pharmacist) {
                    String staffID = null;
                    String role = null;

                    if (user instanceof Doctor) {
                        role = "Doctor";
                        staffID = ((Doctor) user).getDoctorID();
                    } else if (user instanceof Administrator) {
                        role = "Administrator";
                        staffID = ((Administrator) user).getAdminID();
                    } else if (user instanceof Pharmacist) {
                        role = "Pharmacist";
                        staffID = ((Pharmacist) user).getPharmacistID();
                    }

                    // Write staff details to CSV
                    bw.write(String.join(",",
                            staffID,
                            user.getName(),
                            role,
                            user.getGender(),
                            String.valueOf(user.getAge()),
                            user.getContactInfo()
                    ));
                    bw.newLine();
                }
            }

            System.out.println("Staff details saved successfully to CSV.");
        } catch (IOException e) {
            System.err.println("Error saving staff details to CSV: " + e.getMessage());
        }
    }

    private static User createStaffUser(String role, String userID, String password, String name, int age, String gender, String contactInfo, String staffID) {
        switch (role.toLowerCase()) {
            case "doctor":
                return new Doctor(userID, password, name, age, gender, contactInfo, staffID);
            case "administrator":
                return new Administrator(userID, password, name, age, gender, contactInfo, staffID);
            case "pharmacist":
                return new Pharmacist(userID, password, name, age, gender, contactInfo, staffID);
            default:
                System.err.println("Unknown role: " + role);
                return null;
        }
    }

    // Load Patient Details
    public static void loadPatientDetails(String fileName, Map<String, String> userIDs, Map<String, String> userCredentials, List<User> users) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 6) { // Ensure correct number of fields
                    String patientID = values[0];
                    String name = values[1];
                    String dob = values[2];
                    String gender = values[3];
                    String bloodType = values[4];
                    String contactInfo = values[5];

                    // Get userID and password from userCredentials map by name
                    String userID = userIDs.get(name);
                    String password = userCredentials.get(userID);

                    int age;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate birthDate = LocalDate.parse(dob, formatter);
                    LocalDate currentDate = LocalDate.now();

                    MedicalRecord medicalRecord = new MedicalRecord(patientID);
                    
                    age = Period.between(birthDate, currentDate).getYears();

                    if (userID != null && password != null) { // Ensure credentials exist
                        Patient patient = new Patient(userID, password, name, age, gender, contactInfo, dob, patientID, bloodType, medicalRecord);
                        users.add(patient); // Add patient to the user list
                    } else {
                        System.out.println("No matching credentials for Patient Name: " + name + userID +password);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the Patient CSV file: " + e.getMessage());
        }
    }

    // Save Patients to CSV
    public static void savePatientsToCSV(String filePath, List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("PatientID,Name,DOB,Gender,BloodType,ContactInfo");
            bw.newLine();

            for (User user : users) {
                if (user instanceof Patient){
                    Patient patient = (Patient) user;
                    bw.write(String.join(",",
                        patient.getPatientID(),
                        patient.getName(),
                        patient.getDob(),
                        patient.getGender(),
                        patient.getBloodType(),
                        patient.getContactInfo()
                    ));
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving Patients to CSV: " + e.getMessage());
        }
    }

    // Load Medical Records from CSV
}
