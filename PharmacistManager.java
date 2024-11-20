package Project.manager;
import java.util.List;
import java.util.Scanner;
import Project.User.User;
import Project.InventoryV2;


public class PharmacistManager {
    public static void PharInterface(User user,List<InventoryV2>  inventory, AppointmentOutcomeRecordManager appointmentOutcomeRecordManager){
        
        Scanner scanner = new Scanner(System.in);
        int choice;
        while(true){
            System.out.println("\nPharmacist Interface\n");
            System.out.println("1: View Appointment outcome record");
            System.out.println("2: Complete Prescription");
            System.out.println("3: Inventory");
            System.out.println("4: LogOut");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 
            System.out.println("\n======================================");

            if (choice == 1) {
                System.out.print("Enter PatientID: ");
                String patientID = scanner.nextLine();
                System.out.println();
                appointmentOutcomeRecordManager.pharAppointmentOutcomeDisplay(patientID);
            } else if (choice == 2) {
                System.out.print("Enter PatientID: ");
                String patientID = scanner.nextLine();
                System.out.println();
                appointmentOutcomeRecordManager.pharAppointmentOutcomeUpdate(patientID, inventory);
                
            } else if (choice == 3) {
                InventoryManager.InventoryInterface(inventory);
            } else if (choice == 4) {
                break;
            }else {
                System.out.println("Invalid option");
            }

        }
    }
}
