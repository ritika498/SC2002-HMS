package Project;
import java.util.List;
import java.util.Scanner;
import Project.manager.*;
public class HospitalManagementSystem {
    public static void main(String[] args) {
        //constructing objects

        LoginManager loginManager = new LoginManager();
        InventoryManager inventoryManager = new InventoryManager();
        List <InventoryV2> inventory= inventoryManager.getInventoryList();
        AppointmentManager appointmentManager = new AppointmentManager();
        AppointmentOutcomeRecordManager appointmentOutcomeRecordManager = new AppointmentOutcomeRecordManager();
        MedicalRecordManager medicalRecordManager = new MedicalRecordManager();
        AppointmentSchedulingManager appointmentSchedulingManager = new AppointmentSchedulingManager();
        //loading from objects

        loginManager.loadUsersFromCSV();

        //interface
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n======================================");

        loginManager.startLoginInterface(inventory,appointmentManager,appointmentOutcomeRecordManager,medicalRecordManager, appointmentSchedulingManager);
        
        //appointmentManager.saveAppointmentsToCSV();
        //appointmentOutcomeRecordManager.saveAppointmentOutcomeRecordsToCSV();
        //medicalRecordManager.writeToCSV();
        //inventoryManager.saveInventoryToCSV();
        scanner.close();

    }
}
