package Project.User;

public class User {
    private String userID;
    private String password;
    private String name;
    private int age;
    private String gender;
    private String contactInfo;
    private boolean sussyLogin = false;

    public User(String userID, String password, String name, int age, String gender, String contactInfo) {
        this.userID = userID;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contactInfo = contactInfo;
    }

    public User(String name, int age, String gender){
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
    public void setSussyLogin(){
        this.sussyLogin = true;
    }
    public void unsetSussyLogin(){
        this.sussyLogin = false;
    }
    public boolean getSussyLogin(){
        return this.sussyLogin;
    }
    // Getters and Setters
    public String getUserID() {
        return userID;
    }

    public String getPassword(){
        return password;
    }
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
    public void setAge(int age){
        this.age=age;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setName(String name){
        this.name = name;
    }
}