package Project.manager;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import Project.Appointment;
import Project.User.Doctor;
import Project.User.Patient;

public class AppointmentManager {
    private List<Appointment> appointments;
    private static final String APPOINTMENTS_FILE = "/Users/vijay/Documents/Code/Java/Project/CSV Files/Appointment_List.csv";

    public AppointmentManager() {
        this.appointments = new ArrayList<>();
        loadAppointmentsFromCSV();
        // Load existing appointments from CSV
    }

    public List<Appointment> getAppointments(){
        return this.appointments;
    }
    // Load appointments from CSV
    public void loadAppointmentsFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(APPOINTMENTS_FILE))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                appointments.add(Appointment.fromCSV(line));
            }
            System.out.println("Appointments loaded successfully from CSV.");
        } catch (FileNotFoundException e) {
            System.out.println("No appointments file found. A new one will be created.");
        } catch (IOException e) {
            System.err.println("Error loading appointments: " + e.getMessage());
        }
    }

    // Save appointments to CSV
    public void saveAppointmentsToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(APPOINTMENTS_FILE))) {
            bw.write("AppointmentID,PatientID,DoctorID,Date,Time,Status,\n");
            for (Appointment appointment : appointments) {
                bw.write(appointment.toCSV());
                bw.newLine();
            }
            System.out.println("Appointments saved successfully to CSV.");
        } catch (IOException e) {
            System.err.println("Error saving appointments: " + e.getMessage());
        }
    }

    // Schedule a new appointment
    public void scheduleAppointment(String appointmentID,String patientID) {
        try {
            for (Appointment appointment : appointments){
                if(appointment.getAppointmentID().equals(appointmentID) && appointment.getPatientID()==null){
                    appointment.setPatientID(patientID);
                    appointment.setStatus("Pending");
                    System.out.println("Appointment Scheduled successfully!\n");
                }
            }
        } catch (Exception e) {
            System.err.println("Error scheduling appointment: " + e.getMessage());
        }
    }

    // Cancel an appointment
    public void cancelAppointment(String appointmentID) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                appointment.setStatus("Available");
                appointment.setPatientID(null);
                System.out.println("Appointment canceled successfully.");
                return;
            }
        }
        System.out.println("Appointment not found.");
    }

    // View all appointments
    public void viewAllAppointments() {
        for (Appointment appointment : appointments) {
            appointment.displayAppointmentDetails();
            if (appointment.getStatus().equals("Declined")){
                appointment.setStatus("Available");
            }
        }
    }

    // View appointments by patient
    public List<Appointment> getAppointmentsByPatient(Patient patient) {
        return appointments.stream()
                .filter(appointment -> patient.getPatientID().equals(appointment.getPatientID()))
                .collect(Collectors.toList());
    }

    // View appointments by doctor
    public List<Appointment> getAppointmentsByDoctor(Doctor doctor) {
        return appointments.stream()
                .filter(appointment -> doctor.getDoctorID().equals(appointment.getDoctorID()))
                .collect(Collectors.toList());
    }

    // Update appointment status (e.g., Accept or Decline)
    public void updateAppointmentStatus(String appointmentID, String status) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                appointment.setStatus(status);
                System.out.println("Appointment status updated to: " + status);
                return;
            }
        }
        System.out.println("Appointment not found.");
    }

    // Remove an appointment by appointment ID
    public void removeAppointment(String appointmentID) {
        Iterator<Appointment> iterator = appointments.iterator();
        boolean removed = false;

        while (iterator.hasNext()) {
            Appointment appointment = iterator.next();
            if (appointment.getAppointmentID().equals(appointmentID)) {
                iterator.remove();
                removed = true;
                System.out.println("Appointment with ID " + appointmentID + " has been removed successfully.");
                break;
            }
        }

        if (!removed) {
            System.out.println("Appointment with ID " + appointmentID + " not found.");
        }
    }
    

    // View appointment outcome for a patient
    public void viewAppointmentOutcome(Patient patient) {
        List<Appointment> completedAppointments = appointments.stream()
                .filter(appointment -> patient.getPatientID().equals(appointment.getPatientID())
                        && "completed".equals(appointment.getStatus()))
                .collect(Collectors.toList());

        if (completedAppointments.isEmpty()) {
            System.out.println("No completed appointments found.");
        } else {
            for (Appointment appointment : completedAppointments) {
                appointment.displayAppointmentDetails();
            }
        }
    }

    // View available slots for a specific doctor
    public void viewAvailableSlots(String doctorID, String date) {
        List<Appointment> availableSlots = appointments.stream()
                .filter(appointment -> doctorID.equals(appointment.getDoctorID())
                        && "Available".equals(appointment.getStatus())
                        && date.equals(appointment.getDate())) // Filter by the given date
                .collect(Collectors.toList());

        if (availableSlots.isEmpty()) {
            System.out.println("No available slots on " + date);
        } else {
            for (Appointment appointment : availableSlots) {
                appointment.displayAppointmentDetails();
                System.out.println("\n======================================");
            }
        }
    }
    
    public void makeAppointments(Doctor doctor, int numberOfAppointments, String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateStr);
    
            // Determine the starting appointment ID
            int startingID = 1000; 
            if (!appointments.isEmpty()) {
                String lastID = appointments.get(appointments.size() - 1).getAppointmentID();
                startingID = Integer.parseInt(lastID.substring(1)) + 1; // Extract the number part and increment
            }
    
            Scanner scanner = new Scanner(System.in);
            for (int i = 0; i < numberOfAppointments; i++) {
                System.out.printf("Enter time slot for appointment " + (i + 1) + ": ");
                String timeSlot = scanner.nextLine();
    
                String appointmentID = "A" + (startingID + i); // Generate ID in the format A1000, A1001, etc.
                Appointment appointment = new Appointment(appointmentID, date, timeSlot, doctor.getDoctorID());
                appointment.setStatus("Available");
    
                appointments.add(appointment);
            }
            saveAppointmentsToCSV(); // Save to CSV after creating appointments
            System.out.println(numberOfAppointments + " appointments created successfully for Dr. " + doctor.getName() + " on " + dateStr);
        } catch (Exception e) {
            System.err.println("Error creating appointments: " + e.getMessage());
        }
    }
}
