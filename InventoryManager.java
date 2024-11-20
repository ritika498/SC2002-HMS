package Project.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Project.InventoryV2;
import java.util.Scanner;

public class InventoryManager {
    private List<InventoryV2> inventoryList;
    private static final String INVENTORY_FILE = "/Users/vijay/Documents/Code/Java/Project/CSV Files/Medicine_List.csv";

    public InventoryManager() {
        this.inventoryList = new ArrayList<>();
        loadInventoryFromCSV();
    }

    public List<InventoryV2> getInventoryList() {
        return inventoryList;
    }

    // Load inventory from CSV
    public void loadInventoryFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(INVENTORY_FILE))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                inventoryList.add(InventoryV2.fromCSV(line));
            }
            System.out.println("Inventory loaded successfully from CSV.");
        } catch (IOException e) {
            System.err.println("Error loading inventory: " + e.getMessage());
        }
    }

    // Save inventory to CSV
    public void saveInventoryToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(INVENTORY_FILE))) {
            bw.write("MedicineName,Stock,LowAlert,RefillRequest\n"); // CSV header
            for (InventoryV2 item : inventoryList) {
                bw.write(item.toCSV());
                bw.newLine();
            }
            System.out.println("Inventory saved successfully to CSV.");
        } catch (IOException e) {
            System.err.println("Error saving inventory: " + e.getMessage());
        }
    }


    public static void InventoryInterface(List<InventoryV2> inventory){
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("Inventory Interface\n");
            System.out.println("1: View Inventory");
            System.out.println("2: Request Refill");
            System.out.println("3: Exit");
            System.out.printf("Enter your Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.println("\n======================================");

            if (choice == 1) {
                System.out.println();
                for (InventoryV2 item : inventory) {
                    
                    item.displayInventory();
                    System.out.println();
                    
                }
                System.out.println("\n======================================");
            } else if (choice == 2){
                
                System.out.print("Enter the name of the medicine to request a refill: ");
                String medicineName = scanner.nextLine();
                
        
                boolean found = false;
                for (InventoryV2 item : inventory) {
                    if (item.getMedicinename().equalsIgnoreCase(medicineName)) {
                        item.requestRefill(medicineName);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Medicine with the name '" + medicineName + "' not found in the inventory.");
                }
                System.out.println("\n======================================");

            } else if(choice == 3){
                break;
            }


        }
    }
    public static void InvetroyAdminInterface(List<InventoryV2> inventory){
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("Inventory Interface\n");
            System.out.println("1: View Inventory");
            System.out.println("2. Update inventory");
            System.out.println("3: View Refill Requests");
            System.out.println("4: Exit");
            System.out.printf("Enter your Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.println("\n======================================");

            if (choice == 1) {
                System.out.println();
                for (InventoryV2 item : inventory) {
                    
                    item.displayInventory();
                    System.out.println();
                    
                }
                System.out.println("\n======================================");
                
            } else if (choice == 2){

                System.out.print("Enter the name of the medicine to refill: ");
                String medicineName = scanner.nextLine();

                System.out.print("Enter the refill amount: ");
                int refillAmount = scanner.nextInt();
        
                boolean found = false;
                for (InventoryV2 item : inventory) {
                    if (item.getMedicinename().equalsIgnoreCase(medicineName)) {
                        item.accpetRefill(medicineName, refillAmount);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Medicine with the name '" + medicineName + "' not found in the inventory.");
                }
                System.out.println("\nUpdated Inventory:");
                for (InventoryV2 item : inventory) {
                    item.displayInventory();
                    System.out.println();
                }   
            } else if (choice == 3){
                System.out.print("\nMedication requiring refill:\n ");
                System.out.println();
                boolean refillreq = false;
                for (InventoryV2 item : inventory) {
                    if(item.getreffilrequest()){
                        item.displayInventory();
                        System.out.println();
                        refillreq = true;
                    }   
                }
                if (refillreq){
                    System.out.print("Enter the name of the medicine to refill: ");
                    String medicineName = scanner.nextLine();

                    System.out.print("Enter the refill amount: ");
                    int refillAmount = scanner.nextInt();
            
                    boolean found = false;
                    for (InventoryV2 item : inventory) {
                        if (item.getMedicinename().equalsIgnoreCase(medicineName)) {
                            item.accpetRefill(medicineName, refillAmount);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Medicine with the name '" + medicineName + "' not found in the inventory.");
                    }
                    System.out.println("\nUpdated Inventory:");
                    System.out.println();
                    for (InventoryV2 item : inventory) {
                        item.displayInventory();
                        System.out.println();
                    }
                    System.out.println("\n======================================");
                }
                else{
                    System.out.println("No Refill Requests");
                    System.out.println("\n======================================");
                }

            } else if(choice == 4){
                break;
            }


        }
    }
   
}

