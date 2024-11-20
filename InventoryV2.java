package Project;

public class InventoryV2 {
    private String medicinename;
    private int stock;
    private int lowalert;
    private boolean refillrequest;

    public InventoryV2(String medicinename,int stock,int lowalert, boolean refillrequest){
        this.medicinename = medicinename;
        this.stock = stock;
        this.lowalert = lowalert;
        this.refillrequest = refillrequest;
    }
    
    public void displayInventory() {
        System.out.printf("%s\t%d\t%d\t%b", medicinename, stock, lowalert, refillrequest);
    }
    
    public boolean getreffilrequest(){
        return this.refillrequest;
    }
    public String getMedicinename() {
        return this.medicinename;
    }
    public int getStock(){
        return this.stock;
    }
    public void setStock(int stock){
        this.stock = stock;
    }
    public int getLowAlert(){
        return this.lowalert;
    }

    public void setrefillrequest(boolean refillrequest) {
        this.refillrequest = refillrequest;
    }
    public void requestRefill(String medicineName) {
        if (this.medicinename.equalsIgnoreCase(medicineName)) {
            this.refillrequest = true;
            System.out.println("\nRefill request for " + medicinename + " has been submitted.\n");
        }
    }
    public void accpetRefill(String medicineName, int refillAmount){
        if (this.medicinename.equalsIgnoreCase(medicineName)) {
            this.stock+=refillAmount;
            refillrequest = false;
            System.out.println("\nRefill Completed\n");
        }
    }

    // Converts a CSV row into an InventoryV2 object
public static InventoryV2 fromCSV(String csvRow) {
    String[] values = csvRow.split(",");
    return new InventoryV2(
        values[0].trim(),                      // MedicineName
        Integer.parseInt(values[1].trim()),    // Stock
        Integer.parseInt(values[2].trim()),    // LowAlert
        Boolean.parseBoolean(values[3].trim()) // RefillRequest
    );
}

// Converts an InventoryV2 object into a CSV-formatted string
public String toCSV() {
    return String.join(",",
        medicinename,
        String.valueOf(stock),
        String.valueOf(lowalert),
        String.valueOf(refillrequest)
    );
}
}



