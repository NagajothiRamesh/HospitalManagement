package dao;

import model.Doctor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    private Connection getConnection() throws SQLException {
        // Update this with your actual database URL, username, and password
        String url = "jdbc:mysql://localhost:3306/hospital";
        String username = "root";
        String password = "Jothi@2003";
        return DriverManager.getConnection(url, username, password);
    }

    public void addDoctor(Doctor doctor) throws SQLException {
        String sql = "INSERT INTO doctors (name, specialty, phone, email, office) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, doctor.getName());
            stmt.setString(2, doctor.getSpecialty());
            stmt.setString(3, doctor.getPhone());
            stmt.setString(4, doctor.getEmail());
            stmt.setString(5, doctor.getOffice());
            stmt.executeUpdate();
        }
    }

    public void updateDoctor(Doctor doctor) throws SQLException {
        String sql = "UPDATE doctors SET name = ?, specialty = ?, phone = ?, email = ?, office = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, doctor.getName());
            stmt.setString(2, doctor.getSpecialty());
            stmt.setString(3, doctor.getPhone());
            stmt.setString(4, doctor.getEmail());
            stmt.setString(5, doctor.getOffice());
            stmt.setInt(6, doctor.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteDoctor(int id) throws SQLException {
        String sql = "DELETE FROM doctors WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Doctor getDoctorById(int id) throws SQLException {
        String sql = "SELECT * FROM doctors WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Doctor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("specialty"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("office")
                );
            }
            return null;
        }
    }

    public List<Doctor> getAllDoctors() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                doctors.add(new Doctor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("specialty"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("office")
                ));
            }
        }
        return doctors;
    }
}
