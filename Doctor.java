package Project.User;

import java.util.List; // For handling the List interface
import Project.TimeSlot; // For availability slots
import Project.MedicalRecord; // For handling medical records
import Project.Appointment; // For appointment management
import Project.manager.AppointmentManager; // For managing appointments


public class Doctor extends User {
    private String doctorID;
    private List<TimeSlot> availabilitySlots;

    public Doctor(String userID, String password, String name, int age, String gender, String contactInfo, String doctorID) {
        super(userID, password, name, age, gender, contactInfo);
        this.doctorID = doctorID;
    }

    public void viewMedicalRecord(MedicalRecord record) {
        record.displayMedicalRecord();
    }

    // Update Medical Record
    public void updateMedicalRecord(MedicalRecord record, String diagnosis, String treatment) {
        record.addDiagnosis(diagnosis);
        record.addTreatment(treatment);
        System.out.println("Medical record updated.");
    }

    // Delete a diagnosis from Medical Record
    public void deleteDiagnosis(MedicalRecord record, String diagnosis) {
        record.removeDiagnosis(diagnosis);
        System.out.println("Diagnosis removed from medical record.");
    }

    // Getters and Setters
    public String getDoctorID() {
        return doctorID;
    }

    public List<TimeSlot> getAvailabilitySlots() {
        return availabilitySlots;
    }

    public void setAvailability(List<TimeSlot> newAvailabilitySlots) {
        this.availabilitySlots = newAvailabilitySlots;
    }

    // Method to view appointments via AppointmentManagerƒ
    public void viewAppointments(AppointmentManager appointmentManager) {
        List<Appointment> doctorAppointments = appointmentManager.getAppointmentsByDoctor(this);
        for (Appointment appointment : doctorAppointments) {
            appointment.displayAppointmentDetails();
        }
    }

    // Record outcome of a completed appointment

    

    public void viewAppointmentOutcomes(AppointmentManager manager) {
        List<Appointment> doctorAppointments = manager.getAppointmentsByDoctor(this);
        for (Appointment appointment : doctorAppointments) {
            if (appointment.getOutcomeRecord() != null) {
                System.out.println("Outcome for Appointment ID: " + appointment.getAppointmentID());
                appointment.getOutcomeRecord().displayOutcomeDetails();
            }
        }
    }
    
}
