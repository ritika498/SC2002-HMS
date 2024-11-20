package Project;

import java.util.Date;

public class TimeSlot {
    private Date date;
    private String startTime;  // e.g., "10:00 AM"
    private String endTime;    // e.g., "10:30 AM"
    private boolean isAvailable;

    // Constructor
    public TimeSlot(Date date, String startTime, String endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAvailable = true;  // Default to available
    }

    // Getters and Setters
    public Date getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    // Method to display the time slot details
    public void displayTimeSlot() {
        System.out.println("Date: " + date);
        System.out.println("Start Time: " + startTime);
        System.out.println("End Time: " + endTime);
        System.out.println("Availability: " + (isAvailable ? "Available" : "Booked"));
    }

    // Method to check if this timeslot conflicts with another
    public boolean conflictsWith(TimeSlot other) {
        return this.date.equals(other.date) &&
               this.startTime.equals(other.startTime) &&
               this.endTime.equals(other.endTime);
    }
}
