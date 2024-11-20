package Project;

public class AppointmentOutcomeRecord {
    private String appointmentID;
    private String date;              // Appointment date
    private String typeofservice;     // Type of service provided
    private String prescription;      // Prescribed medications or treatments
    private boolean prescriptionStatus; // Whether the prescription is issued
    private String patientID;         // Patient's ID

    // Constructor for initialization
    public AppointmentOutcomeRecord(String appointmentID, String date, String typeofservice, String prescription, String patientID) {
        this.appointmentID = appointmentID;
        this.date = date;
        this.typeofservice = typeofservice;
        this.prescription = prescription;
        this.prescriptionStatus = false;
        this.patientID = patientID;
    }

    // Constructor for loading from CSV
    public AppointmentOutcomeRecord(String appointmentID,String date,String typeofservice,String prescription,boolean prescriptionStatus,String patientID) {
        this.appointmentID = appointmentID;
        this.date = date;
        this.typeofservice = typeofservice;
        this.prescription = prescription;
        this.prescriptionStatus = prescriptionStatus;
        this.patientID = patientID;
    }

    // Getters and Setters
    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTypeofservice() {
        return typeofservice;
    }

    public void setTypeofservice(String typeofservice) {
        this.typeofservice = typeofservice;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public boolean isPrescriptionStatus() {
        return prescriptionStatus;
    }

    public void setPrescriptionStatus(boolean prescriptionStatus) {
        this.prescriptionStatus = prescriptionStatus;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    // Format outcome record for CSV
    public String toCSV() {
        return String.join(",",
                appointmentID,
                date,
                typeofservice,
                prescription,
                Boolean.toString(prescriptionStatus),
                patientID
        );
    }
    
    public static AppointmentOutcomeRecord fromCSV(String csvRow) {
        String[] values = csvRow.split(",");
        return new AppointmentOutcomeRecord(
                values[0].trim(),                           // AppointmentID
                values[1].trim(),                           // Date
                values[2].trim(),                           // Type of service
                values[3].trim(),                           // Prescription
                Boolean.parseBoolean(values[4].trim()),     // PrescriptionStatus
                values[5].trim()                            // PatientID
        );
    }
    

    // Display details of the outcome
    public void displayOutcomeDetails() {
        System.out.println("Appointment ID: " + appointmentID);
        System.out.println("Date: " + date);
        System.out.println("Type of Service: " + typeofservice);
        System.out.println("Prescription: " + prescription);
        System.out.println("Prescription Issued: " + (prescriptionStatus ? "Yes" : "No"));
        System.out.println("Patient ID: " + patientID);
    }
}
