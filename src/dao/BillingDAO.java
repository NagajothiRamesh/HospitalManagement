package dao;

import model.Billing;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillingDAO {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "Jothi@2003");
    }

    public void addBilling(Billing billing) throws SQLException {
        String query = "INSERT INTO billing (patient_id, amount, billing_date, details) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, billing.getPatientId());
            pstmt.setDouble(2, billing.getAmount());
            pstmt.setDate(3, Date.valueOf(billing.getBillingDate()));
            pstmt.setString(4, billing.getDetails());
            pstmt.executeUpdate();
        }
    }

    public void updateBilling(Billing billing) throws SQLException {
        String query = "UPDATE billing SET patient_id = ?, amount = ?, billing_date = ?, details = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, billing.getPatientId());
            pstmt.setDouble(2, billing.getAmount());
            pstmt.setDate(3, Date.valueOf(billing.getBillingDate()));
            pstmt.setString(4, billing.getDetails());
            pstmt.setInt(5, billing.getId());
            pstmt.executeUpdate();
        }
    }

    public void deleteBilling(int id) throws SQLException {
        String query = "DELETE FROM billing WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public Billing getBillingById(int id) throws SQLException {
        String query = "SELECT * FROM billing WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Billing(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        rs.getDouble("amount"),
                        rs.getDate("billing_date").toLocalDate(),
                        rs.getString("details")
                );
            }
        }
        return null;
    }

    public List<Billing> getAllBillings() throws SQLException {
        String query = "SELECT * FROM billing";
        List<Billing> billings = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                billings.add(new Billing(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        rs.getDouble("amount"),
                        rs.getDate("billing_date").toLocalDate(),
                        rs.getString("details")
                ));
            }
        }
        return billings;
    }
}
