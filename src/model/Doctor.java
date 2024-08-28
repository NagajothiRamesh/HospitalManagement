package model;

public class Doctor {
    private int id;
    private String name;
    private String address;
    private String specialty;
    private String phone;
    private String email;
    private String office;

    // Default constructor
    public Doctor() {}

    // Parameterized constructor
    public Doctor(int id, String name, String specialty, String phone, String email, String office) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.phone = phone;
        this.email = email;
        this.office = office;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String address){this.address=address;}
    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialization(String specialty) {
        this.specialty = specialty;
    }

    public String getPhone() {
        return phone;
    }
    public String getAddress() {
        return address;
    }
    public String getSpecialization() {
        return specialty;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specialty='" + specialty + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", office='" + office + '\'' +
                '}';
    }
}
