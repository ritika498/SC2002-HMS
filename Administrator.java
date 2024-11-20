package Project.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Administrator extends User {
    private String adminID;
    private List<User> staff;

    public Administrator(String userID, String password, String name, int age, String gender, String contactInfo, String adminID) {
        super(userID, password, name, age, gender, contactInfo);
        this.adminID = adminID;
    }

    public void setUsers(List<User> staff){
        this.staff = staff;
    }
    public String getAdminID() {
        return adminID;
    }

    // Add staff member
    public void addStaff(User user) {
        staff.add(user);
        System.out.println("Staff member added: " + user.getName());
    }

    // Update staff details
    public void updateStaff(String userID, String newName, String newContactInfo) {
        for (User user : staff) {
            if (user.getUserID().equals(userID)) {
                user.setName(newName);
                user.setContactInfo(newContactInfo);
                System.out.println("Staff details updated for: " + user.getName());
                return;
            }
        }
        System.out.println("Staff member not found.");
    }

    // Remove staff member
    public void removeStaff(String userID) {
        staff.removeIf(user -> user.getUserID().equals(userID));
        System.out.println("Staff member with ID " + userID + " removed.");
    }

    // View all staff
    public void viewAllStaff() {
        System.out.println("List of all staff:");
        for (User user : staff) {
            System.out.println("User ID: " + user.getUserID() + ", Name: " + user.getName() + ", Role: " + user.getClass().getSimpleName());
        }
    }

    // Filter staff by role
    public void filterStaffByRole(String role) {
        List<User> filteredStaff = staff.stream()
                .filter(user -> user.getClass().getSimpleName().equalsIgnoreCase(role))
                .collect(Collectors.toList());
        System.out.println("Filtered Staff by Role (" + role + "):\n");
        filteredStaff.forEach(user -> System.out.println(user.getName() + " - " + user.getUserID()));
    }

    // Filter staff by gender
    public void filterStaffByGender(String gender) {
        List<User> filteredStaff = staff.stream()
                .filter(user -> user.getGender().equalsIgnoreCase(gender))
                .collect(Collectors.toList());
        System.out.println("Filtered Staff by Gender (" + gender + "):\n");
        filteredStaff.forEach(user -> System.out.println(user.getName() + " - " + user.getUserID()));
    }
    public void filterStaffByAge() {
    List<User> filteredStaff = staff.stream()
                .sorted(Comparator.comparingInt(User::getAge)) // Sorts by age in ascending order
                .collect(Collectors.toList());

        System.out.println("Filtered Staff by Age (ascending order):\n");
        filteredStaff.forEach(user -> System.out.println(user.getName() + " - " + user.getAge()));
    }
}
