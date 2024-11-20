package Project;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Appointment {
    private String appointmentID;
    private Date date;
    private String time;
    private String patientID;  // Store Patient ID for CSV integration
    private String doctorID;   // Store Doctor ID for CSV integration
    private String status;     // e.g., "confirmed", "canceled", "completed"
    private AppointmentOutcomeRecord outcomeRecord;

    // Constructor for creating a new appointment
    public Appointment(String appointmentID, Date date, String time, String doctorID) {
        this.appointmentID = appointmentID;
        this.date = date;
        this.time = time;
        this.doctorID = doctorID;
        this.status = "Available";  // Default status
    }

    // Constructor for loading from CSV
    public Appointment(String appointmentID, String patientID, String doctorID, Date date, String time, String status) {
        this.appointmentID = appointmentID;
        this.date = date;
        this.time = time;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.status = status;
    }

    // Getters and Setters
    public String getAppointmentID() {
        return appointmentID;
    }

    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AppointmentOutcomeRecord getOutcomeRecord() {
        return outcomeRecord;
    }

    public void setOutcomeRecord(AppointmentOutcomeRecord outcomeRecord) {
        this.outcomeRecord = outcomeRecord;
    }

    // Display appointment details
    public void displayAppointmentDetails() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.print("Appointment ID: " + appointmentID + "; ");
        System.out.print("Date: " + sdf.format(date)+ "; ");
        System.out.print("Time: " + time+ "; ");
        System.out.print("Doctor ID: " + doctorID+ "; ");
        System.out.print("Status: " + status+ "; ");

        if (patientID != null && !patientID.isEmpty()) {
            System.out.println("Patient ID: " + patientID);
        } else {
            System.out.println("This slot is available.");
        }

        if (outcomeRecord != null) {
            outcomeRecord.displayOutcomeDetails();
        }
    }

    // Format appointment data for CSV
    public String toCSV() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return String.join(",",
                appointmentID,
                patientID == null ? "" : patientID,
                doctorID,
                sdf.format(date),
                time,
                status,
                outcomeRecord == null ? "" : outcomeRecord.toCSV()
        );
    }

    // Parse a date from CSV
    public static Date parseDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(date);
        } catch (Exception e) {
            System.err.println("Error parsing date: " + date);
            return null;
        }
    }

    // Create Appointment object from CSV row
    public static Appointment fromCSV(String csvRow) {
        String[] values = csvRow.split(",");
        return new Appointment(
                values[0].trim(),                           // AppointmentID
                values[1].trim().isEmpty() ? null : values[1].trim(),  // PatientID
                values[2].trim(),                           // DoctorID
                parseDate(values[3].trim()),                // Date
                values[4].trim(),                           // Time
                values[5].trim()                            // Status
        );
    }
}
