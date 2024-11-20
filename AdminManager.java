package Project.manager;
import java.util.List;
import java.util.Scanner;

import Project.User.Administrator;
import Project.User.Doctor;
import Project.User.Pharmacist;
import Project.User.User;
import Project.InventoryV2;

public class AdminManager {

    public static void AdminInterface(User user,List<InventoryV2>  inventory, AppointmentManager appointmentManager, List<User> users){
        Scanner scanner = new Scanner(System.in);
        int choice;
        Administrator admin = (Administrator) user;
        admin.setUsers(users);

        while(true){

            System.out.println("Admin Interface\n");
            System.out.println("1: Staff Manaagement");
            System.out.println("2: Medication Inventory");
            System.out.println("3: View Appointments");
            System.out.println("4: Logout");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 
            System.out.println("\n======================================\n");

            if (choice == 1) {
                while (true){
                    System.out.println("Staff Details\n");
                    System.out.println("1: Filter by Role");
                    System.out.println("2: Filter by Gender");
                    System.out.println("3: Filter by Age");
                    System.out.println("4. Add new Staff");
                    System.out.println("5. Update Staff");
                    System.out.println("6. Delete Staff");
                    System.out.println("7. Exit");
                    System.out.print("Choose an option: ");
                    choice = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.println("\n======================================\n");

                    if (choice == 1){
                        System.out.print("Enter role: ");
                        String role = scanner.nextLine();
                        System.out.println();
                        admin.filterStaffByRole(role);
                        System.out.println("\n======================================\n");
                    }
                    else if (choice == 2)
                    {
                        System.out.print("Enter gender: ");
                        String gender = scanner.nextLine();
                        System.out.println();
                        admin.filterStaffByGender(gender);
                        System.out.println("\n======================================\n");
                    }
                    else if (choice == 3)
                    {
                        admin.filterStaffByAge();
                        System.out.println("\n======================================\n");
                    }
                    else if (choice == 4)
                    {
                        System.out.print("Enter User ID: ");
                        String userID = scanner.nextLine();
                        System.out.print("Enter Password: ");
                        String password = scanner.nextLine();
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Age: ");
                        int age = scanner.nextInt();
                        scanner.nextLine(); 
                        System.out.print("Enter Gender: ");
                        String gender = scanner.nextLine();
                        System.out.print("Enter Contact Info: ");
                        String contactInfo = scanner.nextLine();
                        System.out.print("Enter Role (Doctor/Administrator/Pharmacist): ");
                        String role = scanner.nextLine();
                        createStaff(users, userID, password, name, age, gender, contactInfo, role, userID);
                        System.out.println("\n======================================\n");
                    }
                    else if (choice == 5){
                        System.out.print("Enter Staff ID to update: ");
                        String staffID = scanner.nextLine();
                        String role = " ";
                        if (staffID.charAt(0)=='D'){
                            role = "Doctor";
                        }
                        else if (staffID.charAt(0)=='P'){
                            role = "Pharmacist";
                        }
                        if (role.equals(" ")){
                            System.out.println("Cannot update Admin");
                            System.out.println("\n======================================");
                        }
                        else{
                            while (true){
                                System.out.println("What would you like to change\n");
                                System.out.println("1: Name");
                                System.out.println("2: Age");
                                System.out.println("3: Gender");
                                System.out.println("4. Contact Info");
                                System.out.println("5. Exit");
                                System.out.print("Choose an option: ");
                                choice = scanner.nextInt();
                                scanner.nextLine(); 
                                System.out.println("\n======================================\n");
                                if (choice ==1){
                                    System.out.print("Enter new Name: ");
                                    String name = scanner.nextLine();
                                    for (User staff: users){
                                        if (staff.getUserID().equals(staffID)){
                                            staff.setName(name);
                                            staff.getName();
                                        }
                                    }
                                } else if (choice == 2){
                                    System.out.print("Enter new Age: ");
                                    int age = scanner.nextInt();
                                    scanner.nextLine();
                                    for (User staff: users){
                                        if (staff.getUserID().equals(staffID)){
                                            staff.setAge(age);
                                        }
                                    }
                                } else if (choice == 3){
                                    System.out.print("Enter Gender: ");
                                    String gender = scanner.nextLine();
                                    for (User staff: users){
                                        if (staff.getUserID().equals(staffID)){
                                            staff.setGender(gender);
                                        }
                                    }
                                } else if (choice == 4){
                                    System.out.print("Enter new ContactInfo: ");
                                    String contactInfo = scanner.nextLine();
                                    for (User staff: users){
                                        if (staff.getUserID().equals(staffID)){
                                            staff.setContactInfo(contactInfo);
                                        }
                                    }
                                } else if (choice == 5){
                                    break;
                                } else {
                                    System.out.println("Invalid choice");
                                    System.out.println("\n======================================\n");
                                }
                            }
                        }

                    }
                    else if (choice == 6)
                    {
                        System.out.println("Enter Staff ID to delete:");
                        String staffIDToDelete = scanner.nextLine();
                        if (admin.getAdminID().equals(staffIDToDelete)){
                            System.out.println("Cannot delete Staff");
                            System.out.println("\n======================================\n");
                        }
                        else{
                            deleteStaff(users, staffIDToDelete);
                            System.out.println("\n======================================\n");
                        }
                    }
                    else if (choice == 7)
                    {
                        break;
                    }
                    else{
                        System.out.println("Invalid option");
                    }
                }
            } else if (choice == 2) {
                InventoryManager.InvetroyAdminInterface(inventory);

            } else if (choice == 3) {
                appointmentManager.viewAllAppointments();
                System.out.println("\n======================================");
            } else if (choice == 4) {
                break;
            }else {
                System.out.println("Invalid option");
            }

        }
    }
    // Create a new staff entity
    public static void createStaff(List<User> staffList, String userID, String password, String name, int age, String gender, String contactInfo, String role, String staffID) {
        User newStaff = null;
        for (User user: staffList){
            if (userID.equals(user.getUserID())){
                System.out.println("\nStaffID already exists");
                return;
            }
        }

        // Determine the type of staff and create accordingly
        switch (role.toLowerCase()) {
            case "doctor":
                newStaff = new Doctor(userID, password, name, age, gender, contactInfo, staffID);
                break;
            case "administrator":
                newStaff = new Administrator(userID, password, name, age, gender, contactInfo, staffID);
                break;
            case "pharmacist":
                newStaff = new Pharmacist(userID, password, name, age, gender, contactInfo, staffID);
                break;
            default:
                System.err.println("Invalid role specified: " + role);
                return;
        }

        // Add the new staff entity to the list
        staffList.add(newStaff);
        System.out.println("New staff created: " + newStaff.getName());
    }

    // Delete an existing staff entity by staffID
    public static void deleteStaff(List<User> staffList, String staffID) {
        boolean isDeleted = staffList.removeIf(user -> {
            if (user instanceof Doctor) {
                return ((Doctor) user).getDoctorID().equals(staffID);
            } else if (user instanceof Administrator) {
                return ((Administrator) user).getAdminID().equals(staffID);
            } else if (user instanceof Pharmacist) {
                return ((Pharmacist) user).getPharmacistID().equals(staffID);
            }
            return false;
        });

        if (isDeleted) {
            System.out.println("Staff with ID " + staffID + " has been deleted.");
        } else {
            System.out.println("No staff found with ID " + staffID);
        }
    }


}
