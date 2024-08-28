package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import dao.DoctorDAO;
import model.Doctor;

public class DoctorPanel extends JPanel {

    private JTextField idField, nameField, specializationField, phoneField, emailField, addressField;
    private JButton addButton, updateButton, deleteButton;
    private JTable doctorTable;
    private DoctorDAO doctorDAO;

    public DoctorPanel() {
        // Initialize DAO
        doctorDAO = new DoctorDAO();

        // Set layout and create components
        setLayout(new BorderLayout());

        // Create input panel for form fields
        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Specialization:"));
        specializationField = new JTextField();
        inputPanel.add(specializationField);

        inputPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        inputPanel.add(addressField);

        add(inputPanel, BorderLayout.NORTH);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Initialize table and add to panel
        doctorTable = new JTable();
        add(new JScrollPane(doctorTable), BorderLayout.CENTER);

        // Load doctor data into table
        loadDoctorData();

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDoctor();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDoctor();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteDoctor();
            }
        });
    }

    private void addDoctor() {
        try {
            // Create a new Doctor object
            Doctor doctor = new Doctor();
            doctor.setName(nameField.getText());
            doctor.setSpecialization(specializationField.getText());
            doctor.setPhone(phoneField.getText());
            doctor.setEmail(emailField.getText());
            doctor.setAddress(addressField.getText());

            // Use DAO to add the doctor
            doctorDAO.addDoctor(doctor);

            JOptionPane.showMessageDialog(this, "Doctor added successfully!");
            loadDoctorData();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding doctor: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateDoctor() {
        try {
            // Retrieve ID and create a new Doctor object with updated information
            int id = Integer.parseInt(idField.getText());
            Doctor doctor = new Doctor();
            doctor.setId(id);
            doctor.setName(nameField.getText());
            doctor.setSpecialization(specializationField.getText());
            doctor.setPhone(phoneField.getText());
            doctor.setEmail(emailField.getText());
            doctor.setAddress(addressField.getText());

            // Use DAO to update the doctor
            doctorDAO.updateDoctor(doctor);

            JOptionPane.showMessageDialog(this, "Doctor updated successfully!");
            loadDoctorData();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating doctor: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid format.", "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteDoctor() {
        try {
            // Retrieve ID and use DAO to delete the doctor
            int id = Integer.parseInt(idField.getText());
            doctorDAO.deleteDoctor(id);

            JOptionPane.showMessageDialog(this, "Doctor deleted successfully!");
            loadDoctorData();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting doctor: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID format.", "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadDoctorData() {
        try {
            // Retrieve all doctors and update the table
            List<Doctor> doctors = doctorDAO.getAllDoctors();
            String[] columnNames = {"ID", "Name", "Specialization", "Phone", "Email", "Address"};
            Object[][] data = new Object[doctors.size()][6];

            for (int i = 0; i < doctors.size(); i++) {
                Doctor doctor = doctors.get(i);
                data[i] = new Object[]{
                        doctor.getId(),
                        doctor.getName(),
                        doctor.getSpecialization(),
                        doctor.getPhone(),
                        doctor.getEmail(),
                        doctor.getAddress()
                };
            }

            doctorTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading doctor data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
