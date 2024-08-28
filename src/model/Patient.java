package model;

public class Patient {
    private int id;
    private String name;
    private int age;
    private String gender;
    private String phone;
    private String email;
    private String address;
    private String medicalHistoryArea;

    public Patient() {}

    public Patient(int id, String name, int age, String gender, String phone, String email, String address, String medicalHistoryArea) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.medicalHistoryArea=medicalHistoryArea;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getmedicalHistoryArea() { return address; }
    public void setmedicalHistoryArea(String address) { this.address = address; }
}
