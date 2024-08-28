package model;

import java.time.LocalDate;

public class Billing {
    private int id;
    private int patientId;
    private double amount;
    private LocalDate billingDate;
    private String details;

    // Default constructor
    public Billing() {}

    // Parameterized constructor
    public Billing(int id, int patientId, double amount, LocalDate billingDate, String details) {
        this.id = id;
        this.patientId = patientId;
        this.amount = amount;
        this.billingDate = billingDate;
        this.details = details;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(LocalDate billingDate) {
        this.billingDate = billingDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Billing{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", amount=" + amount +
                ", billingDate=" + billingDate +
                ", details='" + details + '\'' +
                '}';
    }
}
