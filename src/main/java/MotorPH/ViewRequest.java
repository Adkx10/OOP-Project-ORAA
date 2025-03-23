package MotorPH;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ViewRequest extends javax.swing.JFrame implements DataReader {

    private String empNo;
    private String date;
    private String[] rowValue;
    private String[] colValue;
    private HomePage homePage;
    private Employee currentUser;

    public ViewRequest() throws IOException {
        this(null);
    }

    public ViewRequest(HomePage homePage) throws IOException {
        this.homePage = homePage;
        this.currentUser = (homePage != null) ? homePage.getCurrentUser() : null;
        this.empNo = (currentUser != null) ? currentUser.getEmployeeNo() : null;

        initComponents();
        showDate();

        try {
            if (currentUser instanceof Admin || currentUser instanceof Manager) {
                readData(null, (String) null);
            } else if (currentUser instanceof RegularEmployee) {
                readData(empNo, (String) null);
            }
        } catch (CsvValidationException ex) {
            Logger.getLogger(ViewRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showDate() {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        jLabel4.setText(s.format(d));
    }

    @Override
    public boolean readData(String empNo, String... params) throws IOException, CsvValidationException {
        boolean empFound = false;
        try {
            String filename = "Leave Request.csv";
            try (CSVReader reader = new CSVReader(new FileReader(filename))) {
                String[] header = reader.readNext(); // Read CSV header
                DefaultTableModel model = (DefaultTableModel) requestTable.getModel();
                model.setRowCount(0); // Clear table before loading new data
                requestTable.setAutoCreateRowSorter(true);

                //System.out.println("Reading requests for Employee No: " + empNo + " Date: " + date); //Debugging
                while ((rowValue = reader.readNext()) != null) {
                    if (currentUser instanceof Admin || currentUser instanceof Manager) {
                        // Admin & Manager: Show all requests
                        if ((empNo == null || empNo.isEmpty() || rowValue[0].equals(empNo)) && (date == null || date.isEmpty() || rowValue[5].equals(date))) {
                            model.addRow(rowValue);
                            empFound = true;
                        }
                    } else if (currentUser instanceof RegularEmployee) {
                        // Regular Employee: Show only their own requests
                        if (rowValue.length > 0 && rowValue[0].equals(empNo)) {
                            if (date == null || rowValue[5].equals(date)) { // Optional date filter
                                model.addRow(rowValue);
                                empFound = true;
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

    public void updateRequest(String empNo, String date, String newStatus, String newRemarks) throws IOException, CsvValidationException {
        // Read the CSV file
        String filename = "Leave Request.csv";
        List<String[]> lines = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filename))) {
            while ((colValue = reader.readNext()) != null) {
                if (colValue.length > 0 && colValue[0].equals(empNo) && colValue[5].equals(date)) {
                    colValue[6] = newStatus;
                    colValue[7] = newRemarks;
                }
                lines.add(colValue);
            }
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(filename))) {
            writer.writeAll(lines);
            readData(empNo, date);
            JOptionPane.showMessageDialog(this, "Request updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(ViewRequest.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "An error occurred while writing to the file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void leaveProcessor() {
        jLabel5.setVisible(false);
        leaveReason.setVisible(false);
        dateField1.setVisible(false);
        jLabel6.setVisible(false);
        leaveType.setVisible(false);
        submitRequest.setVisible(false);
        refreshButton1.setVisible(false);
    }

    public void leaveRequestor() {
        jLabel1.setVisible(false);
        empNoField.setVisible(false);
        dateField.setVisible(false);
        viewRequest.setVisible(false);
        refreshButton.setVisible(false);
        approveRequest.setVisible(false);
        rejectRequest.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        approveRequest1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        requestTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        empNoField = new javax.swing.JTextField();
        viewRequest = new javax.swing.JButton();
        approveRequest = new javax.swing.JButton();
        rejectRequest = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        refreshButton = new javax.swing.JButton();
        dateField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();
        leaveReason = new javax.swing.JTextField();
        dateField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        submitRequest = new javax.swing.JButton();
        leaveType = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        refreshButton1 = new javax.swing.JButton();

        approveRequest1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        approveRequest1.setText("Approve");
        approveRequest1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                approveRequest1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("View Leave Request");
        setMinimumSize(new java.awt.Dimension(500, 500));

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 100));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText(currentUser.getEmployeeNo());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("jLabel4");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        requestTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee #", "Last Name", "FIrst Name", "Leave Request", "Type", "Date", "Status", "Remarks"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        requestTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(requestTable);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        viewRequest.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        viewRequest.setText("View");
        viewRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewRequestActionPerformed(evt);
            }
        });

        approveRequest.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        approveRequest.setText("Approve");
        approveRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                approveRequestActionPerformed(evt);
            }
        });

        rejectRequest.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rejectRequest.setText("Reject");
        rejectRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rejectRequestActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Enter Employee No. :");

        refreshButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Date (mm/dd/yyyy) :");

        backButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        backButton.setText("< Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Leave Reason:");

        submitRequest.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        submitRequest.setText("Submit");
        submitRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitRequestActionPerformed(evt);
            }
        });

        leaveType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vacation Leave", "Leave of Absence", "Mental Wellness Leave" }));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Type of Leave:");

        refreshButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        refreshButton1.setText("Refresh");
        refreshButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(dateField)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(viewRequest)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(rejectRequest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(approveRequest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel6)
                                .addComponent(jLabel5)
                                .addComponent(leaveReason)
                                .addComponent(dateField1)
                                .addComponent(leaveType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(submitRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(refreshButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(empNoField, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {approveRequest, refreshButton, rejectRequest, viewRequest});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(empNoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(leaveReason, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewRequest)
                    .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(approveRequest)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rejectRequest))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(leaveType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(submitRequest)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refreshButton1)))
                .addGap(70, 70, 70)
                .addComponent(backButton)
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {refreshButton, viewRequest});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {dateField, empNoField});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1157, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 812, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void approveRequest1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_approveRequest1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_approveRequest1ActionPerformed

    private void refreshButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButton1ActionPerformed
        try {
            currentUser.getEmployeeNo();
            if (currentUser instanceof RegularEmployee) {
                readData(currentUser.getEmployeeNo(), (String) null); // Show only their requests
            }
        } catch (CsvValidationException | IOException ex) {
            Logger.getLogger(ViewRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_refreshButton1ActionPerformed

    private void submitRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitRequestActionPerformed
        try {
            currentUser.readData(empNo);//currentUser.readEmployee(empNo);
        } catch (IOException | CsvValidationException ex) {
            Logger.getLogger(ViewRequest.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        String empNoValue = currentUser.getEmployeeNo();
        String empLNValue = currentUser.getEmployeeLN();
        String empFNValue = currentUser.getEmployeeFN();
        String empLeaveReason = leaveReason.getText();
        String empLeaveDate = dateField1.getText();
        String empLeaveType = (leaveType.getSelectedItem() != null) ? leaveType.getSelectedItem().toString() : "N/A";
        String empLeaveStatus = "Pending";
        String empLeaveRemarks = "";

        // Validate date format
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        sdf.setLenient(false); // Prevents invalid dates like 02/30/2024
        
        try {
            sdf.parse(empLeaveDate); // Attempt to parse the date
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please enter the date as MM/DD/YYYY.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //Check for empty fields
        if (empLeaveReason.isEmpty() || empLeaveDate.isEmpty() || empLeaveType.equals("Unknown")) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Prepare CSV row
        String[] rowData = {empNoValue, empLNValue, empFNValue, empLeaveReason, empLeaveType, empLeaveDate, empLeaveStatus, empLeaveRemarks};

        // Write to CSV
        String filename = "Leave Request.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(filename, true))) {
            writer.writeNext(rowData);
            JOptionPane.showMessageDialog(this, "Leave request submitted successfully.", "Leave Request", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(AddEmployee.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Failed to submit leave request.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_submitRequestActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        homePage.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        this.empNo = empNoField.getText();
        this.date = dateField.getText();
        try {
            readData(null, (String) null);
            empNoField.setText(null);
            dateField.setText(null);
        } catch (CsvValidationException | IOException ex) {
            Logger.getLogger(ViewRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void rejectRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rejectRequestActionPerformed
        this.empNo = empNoField.getText();
        this.date = dateField.getText();
        if (!this.empNo.isEmpty() && !this.date.isEmpty()) {
            try {
                if (!readData(this.empNo, this.date)) {
                    JOptionPane.showMessageDialog(this, "Request not found!", "Search Request", JOptionPane.WARNING_MESSAGE);
                } else {
                    Remarks remarks = new Remarks(this.empNo, this.date, this);
                    remarks.setVisible(true);
                }
            } catch (CsvValidationException | IOException ex) {
                Logger.getLogger(ViewRequest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //JOptionPane.showMessageDialog(this, "Request status and remarks updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_rejectRequestActionPerformed

    private void approveRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_approveRequestActionPerformed
        this.empNo = empNoField.getText();
        this.date = dateField.getText();
        if (!this.empNo.isEmpty() && !this.date.isEmpty()) {
            try {
                if (!readData(this.empNo, this.date)) {
                    JOptionPane.showMessageDialog(this, "Request not found!", "Search Request", JOptionPane.WARNING_MESSAGE);
                } else {
                    String newStatus = "Approved";
                    String newRemarks = "";
                    updateRequest(this.empNo, this.date, newStatus, newRemarks);
                }
            } catch (IOException | CsvValidationException ex) {
                Logger.getLogger(ViewRequest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_approveRequestActionPerformed

    private void viewRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewRequestActionPerformed
        this.empNo = empNoField.getText();
        this.date = dateField.getText();
        try {
            if (!readData(this.empNo.isEmpty() ? null : this.empNo, this.date.isEmpty() ? null : this.date)) {
                JOptionPane.showMessageDialog(this, "Request not found!", "Search Request", JOptionPane.WARNING_MESSAGE);
            }
        } catch (CsvValidationException | IOException ex) {
            Logger.getLogger(ViewRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_viewRequestActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton approveRequest;
    private javax.swing.JButton approveRequest1;
    private javax.swing.JButton backButton;
    private javax.swing.JTextField dateField;
    private javax.swing.JTextField dateField1;
    private javax.swing.JTextField empNoField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField leaveReason;
    private javax.swing.JComboBox<String> leaveType;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton refreshButton1;
    private javax.swing.JButton rejectRequest;
    private javax.swing.JTable requestTable;
    private javax.swing.JButton submitRequest;
    private javax.swing.JButton viewRequest;
    // End of variables declaration//GEN-END:variables
}
