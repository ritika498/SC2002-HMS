package Project.User;

import java.util.List; // For handling lists of appointments
import Project.manager.AppointmentManager; // For managing appointments
import Project.Appointment; // For handling Appointment objects
import Project.MedicalRecord; // For managing patient's medical records



public class Patient extends User {
    private String patientID;
    private String dob;
    private String bloodtype;
    private MedicalRecord medicalRecord;

    public Patient(String userID, String password, String name, int age, String gender, String contactInfo, String dob, String patientID, String bloodType, MedicalRecord medicalRecord) {
        super(userID, password, name, age, gender, contactInfo);
        this.patientID = patientID;
        this.dob = dob;
        this.bloodtype = bloodType;
        this.medicalRecord = medicalRecord;
    }

    public Patient(String patientID2, String name, int age, String gender, String bloodType2, MedicalRecord record) {
        this.patientID = patientID2;
        super(name,age,gender);
        this.bloodtype = bloodType2;
        this.medicalRecord=record;
    }

    public Patient(String userID, String password, String name, int age, String gender, String contactInfo, String dob, String patientID, String bloodType) {
                super(userID, password, name, age, gender, contactInfo);
                this.patientID = patientID;
                this.dob = dob;
                this.bloodtype = bloodType;
    }

    public void dispayDetails(){
        System.out.printf("\nPatientID:\t"+this.patientID);
        System.out.printf("\nName:\t\t"+super.getName());
        System.out.printf("\nAge:\t\t"+super.getAge());
        System.out.printf("\nGender:\t\t"+super.getGender());
        System.out.printf("\nContactinfo:\t"+super.getContactInfo());
        System.out.printf("\nDate of Birth:\t"+this.dob);
        System.out.printf("\nBlood Type:\t"+this.bloodtype+"\n");
    }

    public String getPatientID() {
        return patientID;
    }
    
    public void setContactInfo(){
        
    }
    public String getDob() {
        return dob;
    }

    public MedicalRecord getMedicalRecord(){
        return this.medicalRecord;
    }

    public String getBloodType(){
        return bloodtype;
    }

    // Method to view appointments via AppointmentManager
    public void viewAppointments(AppointmentManager appointmentManager) {
        List<Appointment> patientAppointments = appointmentManager.getAppointmentsByPatient(this);
        for (Appointment appointment : patientAppointments) {
            appointment.displayAppointmentDetails();
        }
    }
}
