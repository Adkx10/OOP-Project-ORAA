package MotorPH;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class EmployeeData extends javax.swing.JFrame implements DataReader {

    private String empNo;
    private String[] nextLine;
    private HomePage homePage;
    private Employee currentUser;

    //Constructor for Testing
    public EmployeeData() throws CsvValidationException, IOException {
        this(null, null);
    }

    // Constructor to show specific employee's details
    public EmployeeData(HomePage homePage, String empNo) throws CsvValidationException, IOException {
        this.homePage = homePage;
        this.currentUser = (homePage != null) ? homePage.getCurrentUser() : null;
        this.empNo = (currentUser != null) ? currentUser.getEmployeeNo() : null;
        initComponents();
        showDate();

        //This displays the entire table for Admin and Manager, while Regular Employees can only see their own information.
        try {
            if (currentUser instanceof Admin || currentUser instanceof Manager) {
                readData(null);
            } else if (currentUser instanceof RegularEmployee) {
                readData(empNo);
            }
        } catch (CsvValidationException ex) {
            Logger.getLogger(ViewRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Show the current date on the UI
    private void showDate() {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        jLabel3.setText(s.format(d));
    }

    // Reads employee data from CSV and filters by empNo if provided
    @Override
    public boolean readData(String empNo, String... params) throws IOException, CsvValidationException {
        boolean empFound = false;
        try {
            String filename = "Employee Data.csv";
            try (CSVReader reader = new CSVReader(new FileReader(filename))) {
                String[] header = reader.readNext();
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setRowCount(0);
                jTable1.setAutoCreateRowSorter(true);

                // Read and add data rows
                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {
                    if (nextLine.length > 0 && empNo != null && nextLine[0].equals(empNo)) {
                        empFound = true;

                        if (currentUser instanceof Admin || currentUser instanceof Manager) {
                            // Admin & Manager: Show all employee details
                            //if ((empNo == null || empNo.isEmpty() || nextLine[0].equals(empNo))) {
                            model.addRow(nextLine);
                            //empFound = true;
                            //}
                        } else if (currentUser instanceof RegularEmployee) {
                            // Regular Employee: Show only their own details
                            if (/*nextLine.length > 0 &&*/nextLine[0].equals(empNo)) {
                                model.addRow(nextLine);
                                //empFound = true;
                            }
                        }
                        break; // Exit loop early since only one record is relevant
                    }

                    if (empNo == null) {
                        if (currentUser instanceof Admin || currentUser instanceof Manager) {
                            model.addRow(nextLine);
                        } else if (currentUser instanceof RegularEmployee) {
                            // Regular Employee should only see their own data
                            if (nextLine[0].equals(currentUser.getEmployeeNo())) {
                                model.addRow(nextLine);
                            }
                        }
                    }
                }
                model.fireTableDataChanged(); // Refresh the table UI
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return empFound;
    }

    public void setReadOnly() {
        addButton.setVisible(false);
        deleteButton.setVisible(false);
        updateButton.setVisible(false);
    }

    public void viewOwnInfo() {
        addButton.setVisible(false);
        deleteButton.setVisible(false);
        updateButton.setVisible(false);
        refreshButton.setVisible(false);
        searchButton.setVisible(false);
        employeeNo.setText(empNo);
        employeeNo.setEditable(false);

        // Make table read-only
        jTable1.setDefaultEditor(Object.class, null);

        // Ensure only the current user's details are visible
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            if (!Objects.equals(model.getValueAt(i, 0), empNo)) { // Check if not the current employee
                model.removeRow(i);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        backButton = new javax.swing.JButton();
        employeeNo = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Employee Data");
        setMinimumSize(new java.awt.Dimension(700, 540));

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(150, 100));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Employee ID: " + empNo);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("jLabel3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        backButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        backButton.setText("< Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        searchButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Search: (Emp #)");

        addButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        deleteButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        updateButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        refreshButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(searchButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(11, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(employeeNo)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(47, 47, 47)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(updateButton)
                                            .addComponent(deleteButton)
                                            .addComponent(addButton)))
                                    .addComponent(backButton)
                                    .addComponent(jLabel1))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addButton, deleteButton, refreshButton, searchButton, updateButton});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeeNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(refreshButton)
                    .addComponent(searchButton))
                .addGap(19, 19, 19)
                .addComponent(addButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deleteButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(updateButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 504, Short.MAX_VALUE)
                .addComponent(backButton)
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {refreshButton, searchButton});

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee #", "Last Name", "First Name", "Birthday", "Address", "Phone #", "SSS #", "PhilHealth #", "TIN", "Pag-Ibig #", "Status", "Position", "Supervisor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setColumnSelectionAllowed(true);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1002, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 799, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 727, Short.MAX_VALUE)
                        .addGap(1, 1, 1)))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        homePage.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        this.empNo = employeeNo.getText();
        try {
            if (!readData(this.empNo.isEmpty() ? null : this.empNo)) {
                JOptionPane.showMessageDialog(this, "Employee not found!", "Search Result", JOptionPane.WARNING_MESSAGE);
            }
        } catch (CsvValidationException | IOException ex) {
            Logger.getLogger(EmployeeData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_searchButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        AddEmployee add = new AddEmployee();
        add.setVisible(true);
    }//GEN-LAST:event_addButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        String empDelete = employeeNo.getText();
        boolean empFound = false; //Flag to track if the employee number is found

        if (empDelete.isEmpty()) { // If no employee number is entered, show a warning message
            JOptionPane.showMessageDialog(this, "Please enter an employee number to delete.", "Delete Employee", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Read the CSV file
            String filename = "Employee Data.csv";
            List<String[]> csvData = new ArrayList<>();

            try (CSVReader reader = new CSVReader(new FileReader(filename))) {
                //String[] header = reader.readNext();
                while ((this.nextLine = reader.readNext()) != null) {
                    // Check if the current row's employee number matches the one to be deleted
                    if (this.nextLine.length > 0 && this.nextLine[0].equals(empDelete)) {
                        empFound = true; //Set flag to true if the employee number is found
                        // Skip this row if it matches the employee number to be deleted
                        continue;
                    }
                    // Add the row to the list if it doesn't match the employee number to be deleted
                    csvData.add(nextLine);
                }
            }
            // If employee number is not found, display warning message and return
            if (!empFound) {
                JOptionPane.showMessageDialog(this, "Employee number does not exist!", "Delete Employee", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the data?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirmation != JOptionPane.YES_OPTION) {
                return;
            }

            // Write the updated data back to the CSV file
            try (CSVWriter writer = new CSVWriter(new FileWriter(filename))) {
                writer.writeAll(csvData);
            }

            // Remove the corresponding row from the jTable1
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 0).equals(empDelete)) {
                    model.removeRow(i);
                    break; // Stop searching after the row is deleted
                }
            }

            JOptionPane.showMessageDialog(this, "Employee data deleted successfully.", "Delete Employee", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException | CsvValidationException ex) {
            Logger.getLogger(EmployeeData.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error deleting employee: " + ex.getMessage(), "Delete Employee", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        // Get the employee number from the text field
        this.empNo = employeeNo.getText().trim();

        // Check if the employee number is empty
        if (this.empNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an employee number.", "Update Employee", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Search for the employee data in the CSV file
        try {
            String[] employeeData = searchEmployee(empNo);
            if (employeeData == null) {
                JOptionPane.showMessageDialog(this, "Employee not found!", "Update Employee", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Create an instance of UpdateEmployee
            UpdateEmployee updateEmployeeFrame = new UpdateEmployee();

            // Set the text fields with the retrieved employee information
            updateEmployeeFrame.setEmployeeData(employeeData);

            // Display the UpdateEmployee frame
            updateEmployeeFrame.setVisible(true);
        } catch (IOException | CsvValidationException ex) {
            JOptionPane.showMessageDialog(this, "Error searching for employee: " + ex.getMessage(), "Update Employee", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String[] searchEmployee(String empNo) throws IOException, CsvValidationException {
        // Read the CSV file and search for the employee data
        String filename = "Employee Data.csv";
        try (CSVReader reader = new CSVReader(new FileReader(filename))) {
            String[] header = reader.readNext();
            while ((this.nextLine = reader.readNext()) != null) {
                if (this.nextLine.length > 0 && this.nextLine[0].equals(empNo)) {
                    // Return the employee data if found
                    return this.nextLine;
                }
            }
        }
        // Return null if employee not found asdfga
        return null;
    }//GEN-LAST:event_updateButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        try {
            readData(null);
            employeeNo.setText(null);
        } catch (CsvValidationException | IOException ex) {
            Logger.getLogger(EmployeeData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_refreshButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton backButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField employeeNo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton searchButton;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
