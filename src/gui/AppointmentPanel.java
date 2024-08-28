package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import dao.AppointmentDAO;
import model.Appointment;

public class AppointmentPanel extends JPanel {

    private JTextField idField, patientIdField, doctorIdField, dateField;
    private JTextArea detailsArea;
    private JButton addButton, updateButton, deleteButton;
    private JTable appointmentTable;
    private AppointmentDAO appointmentDAO;

    public AppointmentPanel() {
        // Initialize DAO
        appointmentDAO = new AppointmentDAO();

        // Set layout and create components
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));

        inputPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Patient ID:"));
        patientIdField = new JTextField();
        inputPanel.add(patientIdField);

        inputPanel.add(new JLabel("Doctor ID:"));
        doctorIdField = new JTextField();
        inputPanel.add(doctorIdField);

        inputPanel.add(new JLabel("Date (yyyy-mm-dd):"));
        dateField = new JTextField();
        inputPanel.add(dateField);

        inputPanel.add(new JLabel("Details:"));
        detailsArea = new JTextArea(3, 20);
        inputPanel.add(new JScrollPane(detailsArea));

        add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();

        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Initialize table and add to panel
        appointmentTable = new JTable();
        add(new JScrollPane(appointmentTable), BorderLayout.CENTER);

        // Load appointment data into table
        loadAppointmentData();

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAppointment();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAppointment();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAppointment();
            }
        });
    }

    private void addAppointment() {
        try {
            // Create a new Appointment object
            Appointment appointment = new Appointment();
            appointment.setPatientId(Integer.parseInt(patientIdField.getText()));
            appointment.setDoctorId(Integer.parseInt(doctorIdField.getText()));
            appointment.setAppointmentDate(java.time.LocalDate.parse(dateField.getText()));
            appointment.setDetails(detailsArea.getText());

            // Use DAO to add the appointment
            appointmentDAO.addAppointment(appointment);

            JOptionPane.showMessageDialog(this, "Appointment added successfully!");
            loadAppointmentData();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding appointment: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid format.", "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateAppointment() {
        try {
            // Retrieve ID and create a new Appointment object with updated information
            int id = Integer.parseInt(idField.getText());
            Appointment appointment = new Appointment();
            appointment.setId(id);
            appointment.setPatientId(Integer.parseInt(patientIdField.getText()));
            appointment.setDoctorId(Integer.parseInt(doctorIdField.getText()));
            appointment.setAppointmentDate(java.time.LocalDate.parse(dateField.getText()));
            appointment.setDetails(detailsArea.getText());

            // Use DAO to update the appointment
            appointmentDAO.updateAppointment(appointment);

            JOptionPane.showMessageDialog(this, "Appointment updated successfully!");
            loadAppointmentData();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating appointment: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid format.", "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteAppointment() {
        try {
            // Retrieve ID and use DAO to delete the appointment
            int id = Integer.parseInt(idField.getText());
            appointmentDAO.deleteAppointment(id);

            JOptionPane.showMessageDialog(this, "Appointment deleted successfully!");
            loadAppointmentData();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting appointment: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID format.", "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAppointmentData() {
        try {
            // Retrieve all appointments and update the table
            List<Appointment> appointments = appointmentDAO.getAllAppointments();
            String[] columnNames = {"ID", "Patient ID", "Doctor ID", "Date", "Details"};
            Object[][] data = new Object[appointments.size()][5];

            for (int i = 0; i < appointments.size(); i++) {
                Appointment appointment = appointments.get(i);
                data[i] = new Object[]{
                        appointment.getId(),
                        appointment.getPatientId(),
                        appointment.getDoctorId(),
                        appointment.getAppointmentDate(),
                        appointment.getDetails()
                };
            }

            appointmentTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading appointment data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
