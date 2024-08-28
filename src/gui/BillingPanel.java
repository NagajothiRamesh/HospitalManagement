package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import dao.BillingDAO;
import model.Billing;

public class BillingPanel extends JPanel {

    private JTextField idField, patientIdField, amountField, dateField;
    private JTextArea detailsArea;
    private JButton addButton, updateButton, deleteButton;
    private JTable billingTable;
    private BillingDAO billingDAO;

    public BillingPanel() {
        // Initialize DAO
        billingDAO = new BillingDAO();

        // Set layout and create components
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));

        inputPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Patient ID:"));
        patientIdField = new JTextField();
        inputPanel.add(patientIdField);

        inputPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        inputPanel.add(amountField);

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
        billingTable = new JTable();
        add(new JScrollPane(billingTable), BorderLayout.CENTER);

        // Load billing data into table
        loadBillingData();

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBilling();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBilling();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBilling();
            }
        });
    }

    private void addBilling() {
        try {
            // Create a new Billing object
            Billing billing = new Billing();
            billing.setPatientId(Integer.parseInt(patientIdField.getText()));
            billing.setAmount(Double.parseDouble(amountField.getText()));
            billing.setBillingDate(java.time.LocalDate.parse(dateField.getText()));
            billing.setDetails(detailsArea.getText());

            // Use DAO to add the billing record
            billingDAO.addBilling(billing);

            JOptionPane.showMessageDialog(this, "Billing added successfully!");
            loadBillingData();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding billing: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid format.", "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBilling() {
        try {
            // Retrieve ID and create a new Billing object with updated information
            int id = Integer.parseInt(idField.getText());
            Billing billing = new Billing();
            billing.setId(id);
            billing.setPatientId(Integer.parseInt(patientIdField.getText()));
            billing.setAmount(Double.parseDouble(amountField.getText()));
            billing.setBillingDate(java.time.LocalDate.parse(dateField.getText()));
            billing.setDetails(detailsArea.getText());

            // Use DAO to update the billing record
            billingDAO.updateBilling(billing);

            JOptionPane.showMessageDialog(this, "Billing updated successfully!");
            loadBillingData();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating billing: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid format.", "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteBilling() {
        try {
            // Retrieve ID and use DAO to delete the billing record
            int id = Integer.parseInt(idField.getText());
            billingDAO.deleteBilling(id);

            JOptionPane.showMessageDialog(this, "Billing deleted successfully!");
            loadBillingData();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting billing: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID format.", "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadBillingData() {
        try {
            // Retrieve all billing records and update the table
            List<Billing> billings = billingDAO.getAllBillings();
            String[] columnNames = {"ID", "Patient ID", "Amount", "Date", "Details"};
            Object[][] data = new Object[billings.size()][5];

            for (int i = 0; i < billings.size(); i++) {
                Billing billing = billings.get(i);
                data[i] = new Object[]{
                        billing.getId(),
                        billing.getPatientId(),
                        billing.getAmount(),
                        billing.getBillingDate(),
                        billing.getDetails()
                };
            }

            billingTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading billing data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
