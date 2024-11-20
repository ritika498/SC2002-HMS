package Project.User;

import java.util.List; // For handling lists of prescriptions
import Project.InventoryV2;




public class Pharmacist extends User {
    private String pharmacistID;

    public Pharmacist(String userID, String password, String name, int age, String gender, String contactInfo, String pharmacistID) {
        super(userID, password, name, age, gender, contactInfo);
        this.pharmacistID = pharmacistID;
    }

    public String getPharmacistID() {
        return pharmacistID;
    }

    // View medication inventory
    public void viewInventory(InventoryV2 inventory) {
        inventory.displayInventory();
    }

    // Request inventory replenishment
    public void requestInventoryReplenishment(InventoryV2 inventory, String medicationName, int quantity) {
        inventory.requestRefill(medicationName);
    }
}
