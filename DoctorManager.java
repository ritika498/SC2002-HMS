package Project.manager;
import java.util.Scanner;
import Project.User.User;

import Project.User.Doctor;  // Import Patient class


public class DoctorManager {

    public static void DocInterface(User user,AppointmentManager appointmentManager,AppointmentOutcomeRecordManager appointmentOutcomeRecordManager, AppointmentSchedulingManager appointmentSchedulingManager, MedicalRecordManager medicalRecordManager){
        Scanner scanner = new Scanner(System.in);
        Doctor doctor = (Doctor) user;
        while(true){

            System.out.println("\nDoctor Interface\n");
            System.out.println("1: Medical Record Management ");
            System.out.println("2: Appointment Management ");
            System.out.println("3: Record Appointment Outcome  ");
            System.out.println("4: Logout ");
            System.out.printf("Enter your Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            System.out.println("\n======================================");

            if (choice == 1) {
                medicalRecordManager.doctorInterface();
            } else if (choice == 2) {
                appointmentSchedulingManager.DoctorAppointmentScheduleInterface(doctor,appointmentManager);
            } else if (choice == 3) {
                appointmentOutcomeRecordManager.recordAppointmentOutcome(appointmentManager);
            }else if (choice == 4) {
                break;
            } else {
                System.out.println("Invalid option");
            }

        }
    }
}
