package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PatientPanel extends JPanel {
    private JTable patientTable;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField genderField;
    private JTextField addressField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextArea medicalHistoryArea;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    public PatientPanel() {
        setLayout(new BorderLayout());

        // Panel for table
        JPanel tablePanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Patient Records");
        tablePanel.add(label, BorderLayout.NORTH);

        // Create table with dummy data and column names
        String[] columnNames = {"ID", "Name", "Age", "Gender", "Address", "Phone", "Email", "Medical History"};
        patientTable = new JTable(new Object[][]{}, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(patientTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Panel for form input
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(8, 2, 10, 10));

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        formPanel.add(ageField);

        formPanel.add(new JLabel("Gender:"));
        genderField = new JTextField();
        formPanel.add(genderField);

        formPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        formPanel.add(addressField);

        formPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Medical History:"));
        medicalHistoryArea = new JTextArea(3, 20);
        formPanel.add(new JScrollPane(medicalHistoryArea));

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Patient");
        updateButton = new JButton("Update Patient");
        deleteButton = new JButton("Delete Patient");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Add panels to main panel
        add(tablePanel, BorderLayout.CENTER);
        add(formPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPatient();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePatient();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePatient();
            }
        });

        // Refresh table with initial data
        refreshPatientTable();
    }

    private void refreshPatientTable() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "Jothi@2003");
             Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = stmt.executeQuery("SELECT * FROM patients")) {

            // Get metadata to determine column count
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // Create data array for the table
            Object[][] data = new Object[getRowCount(rs)][columnCount];
            int row = 0;

            // Populate data array
            while (rs.next()) {
                for (int col = 1; col <= columnCount; col++) {
                    data[row][col - 1] = rs.getObject(col);
                }
                row++;
            }

            // Update the table model with the new data
            patientTable.setModel(new javax.swing.table.DefaultTableModel(
                    data,
                    new String[]{"ID", "Name", "Age", "Gender", "Phone",  "Email", "Address", "Medical History"}
            ));

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching patient data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getRowCount(ResultSet rs) throws SQLException {
        int rowCount = 0;
        if (rs.last()) {
            rowCount = rs.getRow();
            rs.beforeFirst(); // Reset cursor position
        }
        return rowCount;
    }

    private void addPatient() {
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String gender = genderField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String medicalHistory = medicalHistoryArea.getText();
        String query = "INSERT INTO patients (name, age, gender, address, phone, email, medicalHistoryArea) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "Jothi@2003");
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, gender);
            pstmt.setString(4, address);
            pstmt.setString(5, phone);
            pstmt.setString(6, email);
            pstmt.setString(7, medicalHistory);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Patient added successfully!");
            refreshPatientTable(); // Refresh table data

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding patient: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePatient() {
        int selectedRow = patientTable.getSelectedRow();

        if (selectedRow >= 0) {
            int patientId = (Integer) patientTable.getValueAt(selectedRow, 0); // Assuming ID is in the first column

            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String gender = genderField.getText().trim();
            String address = addressField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String medicalHistory = medicalHistoryArea.getText().trim();

            if (name.isEmpty() || ageText.isEmpty() || gender.isEmpty() || address.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill out all required fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Age must be a number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String query = "UPDATE patients SET name = ?, age = ?, gender = ?, address = ?, phone = ?, email = ?, medicalHistoryArea = ? WHERE id = ?";

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "Jothi@2003");
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, name);
                pstmt.setInt(2, age);
                pstmt.setString(3, gender);
                pstmt.setString(4, address);
                pstmt.setString(5, phone);
                pstmt.setString(6, email);
                pstmt.setString(7, medicalHistory);
                pstmt.setInt(8, patientId);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Patient updated successfully!");
                    refreshPatientTable(); // Refresh table data
                } else {
                    JOptionPane.showMessageDialog(this, "No patient found with the specified ID.", "Update Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating patient: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No patient selected for update.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deletePatient() {
        int selectedRow = patientTable.getSelectedRow();

        if (selectedRow >= 0) {
            int patientId = (Integer) patientTable.getValueAt(selectedRow, 0); // Assuming ID is in the first column

            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this patient?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String query = "DELETE FROM patients WHERE id = ?";

                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "Jothi@2003");
                     PreparedStatement pstmt = conn.prepareStatement(query)) {

                    pstmt.setInt(1, patientId);
                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Patient deleted successfully!");
                        refreshPatientTable(); // Refresh table data
                    } else {
                        JOptionPane.showMessageDialog(this, "No patient found with the specified ID.", "Delete Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error deleting patient: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No patient selected for deletion.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}
