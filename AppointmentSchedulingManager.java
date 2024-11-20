package Project.manager;


import java.util.List; // For using List in your methods
import java.util.Scanner; // For user input
import Project.Appointment; // For handling appointment objects
import Project.User.Patient; // For patient-related operations
import Project.User.Doctor; // For doctor-related operations
import Project.User.User;

public class AppointmentSchedulingManager {
    public void PatientAppoinmentScheduleInterface(Patient patient, AppointmentManager appointmentManager, List<User> users){
        Scanner scanner = new Scanner(System.in);

        int choice;
        while(true){
            System.out.println("\nAppointment Schedule Interface\n");
            System.out.println("1: View Appointments");
            System.out.println("2: Schedule Appointment");
            System.out.println("3: Reschedule Appointment");
            System.out.println("4: Cancel Appointment");
            System.out.println("5: Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 
            System.out.println("\n======================================");

            if (choice == 1) {
                List<Appointment> appointments = appointmentManager.getAppointmentsByPatient(patient);
                if (!appointments.isEmpty()){
                    for (Appointment appointment : appointments){
                        System.out.println();
                        appointment.displayAppointmentDetails();
                        System.out.println();
                        if (appointment.getStatus().equals("Declined")){
                            appointment.setPatientID(null);
                            appointment.setStatus("Available");
                        }
                    }
                    System.out.println("======================================");
                }
                else{                                           
                    System.out.println("\nNo appointments scheduled");
                    System.out.println("\n======================================");
                }
            } else if (choice == 2) {
                List<Appointment> appointments = appointmentManager.getAppointmentsByPatient(patient);
                if (appointments.isEmpty()){
                    System.out.printf("Enter DoctorID: ");
                    String doctorID = scanner.nextLine();
                    String doctorname = null;
                    for (User user: users){
                        if (user.getUserID().equals(doctorID)){
                            doctorname = user.getName();
                        }

                    }
                    System.out.printf("Enter Date(yyyy-mm-dd): ");
                    String date = scanner.nextLine();
                    System.out.println("\n======================================");
                    System.out.println("Available appointments for Doctor "+ doctorname);
                    appointmentManager.viewAvailableSlots(doctorID, date);   
                    System.out.printf("Choose AppointmentID: ");   
                    String appointmentID = scanner.nextLine();
                    
                    appointmentManager.scheduleAppointment(appointmentID, patient.getPatientID());
                    System.out.println("======================================");
                }
                else{
                    System.err.println("Appointment already booked");
                    System.out.println("\n======================================");
                }
                          
            } else if (choice == 3) {
                List<Appointment> appointments = appointmentManager.getAppointmentsByPatient(patient);
                if (!appointments.isEmpty()){
                    int count=0;
                    for (Appointment appointment : appointments){
                        if (!appointment.getStatus().equals("Declined")){
                            System.out.println();
                            appointment.displayAppointmentDetails();
                            System.out.println();
                            count++;
                        }
                        else{
                            appointment.setStatus("Available");
                            appointment.setPatientID(null);
                        }
                    }
                    if (count==0){
                        System.out.println("\nNo appointments scheduled");
                        System.out.println("\n======================================");
                    }
                    else{

                    
                        System.out.println("======================================");
                        System.out.printf("\nChoose AppointmentID to replace: ");   
                        String cancelappointmentID = scanner.nextLine();
                        System.out.printf("Enter Doctor name: ");
                        String doctorID = scanner.nextLine();
                        System.out.printf("Enter Date(yyyy-mm-dd): ");
                        System.out.println();
                        String date = scanner.nextLine();
                        appointmentManager.viewAvailableSlots(doctorID, date);   
                        System.out.printf("Choose AppointmentID: ");   
                        String appointmentID = scanner.nextLine();
                        appointmentManager.scheduleAppointment(appointmentID, patient.getPatientID());
                        appointmentManager.cancelAppointment(cancelappointmentID);
                        System.out.println("\n======================================");
                    }

                }
                else{                                           
                    System.out.println("\nNo appointments scheduled");
                    System.out.println("\n======================================");
                }


            } else if (choice == 4) {
                List<Appointment> appointments = appointmentManager.getAppointmentsByPatient(patient);
                if (!appointments.isEmpty()){
                    int count=0;
                    for (Appointment appointment : appointments){
                        if (!appointment.getStatus().equals("Declined")){
                            System.out.println();
                            appointment.displayAppointmentDetails();
                            System.out.println();
                            count++;
                        }
                        else{
                            appointment.setStatus("Available");
                            appointment.setPatientID(null);
                        }
                    }
                    if (count==0){
                        System.out.println("\nNo appointments scheduled");
                        System.out.println("\n======================================");
                    }
                    else{
                         System.out.println("======================================");
                        System.out.printf("\nChoose AppointmentID to replace: ");   
                        String cancelappointmentID = scanner.nextLine();
                        appointmentManager.cancelAppointment(cancelappointmentID);
                        System.out.println("\n======================================");
                    }
                }
                else{                                           
                    System.out.println("\nNo appointments scheduled");
                    System.out.println("\n======================================");
                }
            } else if (choice == 5) {
                break;
            } else {
                System.out.println("Invalid option");
            }

        }
    }

    public void DoctorAppointmentScheduleInterface(Doctor doctor, AppointmentManager appointmentManager){
        Scanner scanner = new Scanner(System.in);
        
        int choice;
        while(true){
            System.out.println("\nAppointment Schedule Interface\n");
            System.out.println("1: View Schedule");
            System.out.println("2: Set Appointment Availability");
            System.out.println("3: Appointment Requests");
            System.out.println("4: View Appointments");
            System.out.println("5: Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 
            System.out.println("\n======================================");

            if (choice == 1) {
                List<Appointment> appointments = appointmentManager.getAppointmentsByDoctor(doctor);
                if (!appointments.isEmpty()){
                    for (Appointment appointment : appointments){
                        System.out.println();
                        appointment.displayAppointmentDetails();
                        System.out.println();
                    }
                    System.out.println("\n======================================");
                }
            } else if (choice == 2) {
                System.out.print("Enter number of Appointments: ");
                int numberOfAppointments = scanner.nextInt();  
                scanner.nextLine();
                System.out.print("Enter Date(yyyy-mm-dd): ");
                String dateString = scanner.nextLine();
                appointmentManager.makeAppointments(doctor, numberOfAppointments, dateString);
            } else if (choice == 3) {
                List<Appointment> appointments = appointmentManager.getAppointmentsByDoctor(doctor);
                boolean found = false;
                if (!appointments.isEmpty()){
                    for (Appointment appointment : appointments){
                        if (appointment.getStatus().equals("Pending")){
                            found = true;
                            System.out.println();
                            appointment.displayAppointmentDetails();
                            System.out.println();
                        }
                    }
                    if (found){
                        System.out.println("======================================");
                        System.out.print("Enter Appointment ID to update status: ");
                        String appointmentID = scanner.nextLine();
                        System.out.print("Enter new status (Accepted/Declined): ");
                        String status = scanner.nextLine();
            
                        if (status.equals("Accepted") || status.equals("Declined")) {
                            appointmentManager.updateAppointmentStatus(appointmentID, status);
                        } else {
                            System.out.println("Invalid status. Please enter 'Accepted' or 'Declined'.");
                        }
                        System.out.println("\n======================================");
                    }
                    else{
                        System.out.println("\nNo appointments scheduled");
                        System.out.println("\n======================================");
                    } 
                }
                else{
                    System.out.println("\nNo appointments scheduled");
                    System.out.println("\n======================================");
                }

            } else if (choice == 4) {
                List<Appointment> appointments = appointmentManager.getAppointmentsByDoctor(doctor);
                if (!appointments.isEmpty()){
                    int count = 0;
                    for (Appointment appointment : appointments){
                        if (appointment.getStatus().equals("Accepted")){
                            System.out.println();
                            appointment.displayAppointmentDetails();
                            count++;
                        }
                    }
                    if (count==0){
                        System.out.println("\nNo appointments scheduled");
                    }
                    System.out.println("\n======================================");
                }
                else{
                    System.out.println("\nNo appointments scheduled");
                    System.out.println("\n======================================");
                }
            } else if (choice == 5) {
                break;
            } else {
                System.out.println("Invalid option");
            }

        }
    }
}
