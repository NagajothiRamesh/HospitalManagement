package dao;

import model.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    private Connection getConnection() throws SQLException {
        // Update this with your actual database URL, username, and password
        String url = "jdbc:mysql://localhost:3306/hospital";
        String username = "root";
        String password = "Jothi@2003";
        return DriverManager.getConnection(url, username, password);
    }

    public void addPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (name, age, gender, phone, email, address, medicalHistoryArea) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patient.getName());
            stmt.setInt(2, patient.getAge());
            stmt.setString(3, patient.getGender());
            stmt.setString(4, patient.getPhone());
            stmt.setString(5, patient.getEmail());
            stmt.setString(6, patient.getAddress());
            stmt.setString(7, patient.getmedicalHistoryArea());
            stmt.executeUpdate();
        }
    }

    public void updatePatient(Patient patient) throws SQLException {
        String sql = "UPDATE patients SET name = ?, age = ?, gender = ?, phone = ?, email = ?, address = ?, medicalHistoryArea = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patient.getName());
            stmt.setInt(2, patient.getAge());
            stmt.setString(3, patient.getGender());
            stmt.setString(4, patient.getPhone());
            stmt.setString(5, patient.getEmail());
            stmt.setString(6, patient.getAddress());
            stmt.setString(7, patient.getmedicalHistoryArea());
            stmt.setInt(8, patient.getId());
            stmt.executeUpdate();
        }
    }

    public void deletePatient(int id) throws SQLException {
        String sql = "DELETE FROM patients WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Patient getPatientById(int id) throws SQLException {
        String sql = "SELECT * FROM patients WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Patient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("medicalHistoryArea")
                );
            }
            return null;
        }
    }

    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                patients.add(new Patient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("medicalHistoryArea")
                ));
            }
        }
        return patients;
    }
}
