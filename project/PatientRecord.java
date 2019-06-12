/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author User 2
 */
public class PatientRecord extends javax.swing.JFrame {

    /**
     * Creates new form PatientRecord
     */
    public PatientRecord() throws IOException {
        initComponents();
        displayText();
        displayText1();
        updateTable();
        usertf.setEditable(false);
        usertf1.setEditable(false);
        usertf2.setEditable(false);
        usertf3.setEditable(false);
    }
    String getTableVal1, getTableVal3;
 
    void deleteRec(){
        try{
           Connection conn = DBConn.dataBase();
       
            String sql1 = "DELETE FROM diagnosis WHERE patientID = ? AND doctorID = ?";
            PreparedStatement pst = conn.prepareStatement(sql1);
         
            pst.setString(1,getTableVal1);
            pst.setString(2,getTableVal3);
            
            int RecDeleted = pst.executeUpdate();
            if (RecDeleted > 0){
            JOptionPane.showMessageDialog(null, "Deleted from the Database");
            updateTable();
            }
       }
       catch(SQLException e){} catch (ClassNotFoundException ex) {
             }
        
    
    }
    
     public void updateDB(){
         if(!(usertf4.getText().length()==0 || usertf5.getText().length()==0 ||((JTextField) diagDate.getDateEditor().getUiComponent()).getText().length()==0 )){
  try
        {
            Connection conn = DBConn.dataBase();
            String sql = "UPDATE diagnosis SET diagnosisdate=?, patientdiagnosis = ?, treatment = ? WHERE doctorID = ? AND patientID = ?";
            PreparedStatement pss;
            pss=conn.prepareStatement (sql); 
            pss.setString(1, ((JTextField) diagDate.getDateEditor().getUiComponent()).getText());
            pss.setString(2,usertf5.getText());
            pss.setString (3,usertf4.getText());
             pss.setString(4,usertf2.getText());
             pss.setString(5,usertf.getText());
            
            int RecUpdate = pss.executeUpdate();
            if(RecUpdate > 0){
                JOptionPane.showMessageDialog(null, "Updated");
                updateTable();
            }
            else
                JOptionPane.showMessageDialog(null, "Record not found");
         }
        catch(SQLException ex){
        System.out.println(ex.getMessage());} 
        catch (ClassNotFoundException ex) {
            }
         
 }
         else
             JOptionPane.showMessageDialog(null, "Empty fields detected.");
     }
    
    public String readData() throws IOException {
            String val = null;
            try {
                BufferedReader checkVal = new BufferedReader(new FileReader("patInfo.txt"));
                val = checkVal.readLine();
                System.out.println(val);
                checkVal.close();
            } catch (FileNotFoundException ex) {
            }
            return val;
        }
    public String readData1() throws IOException {
            String val = null;
            try {
                BufferedReader checkVal = new BufferedReader(new FileReader("docInfo.txt"));
                val = checkVal.readLine();
                System.out.println(val);
                checkVal.close();
            } catch (FileNotFoundException ex) {
            }
            
            return val;
        }
    
    void displayText() throws IOException{
        String s = readData();
     String[] result = s.split("\\s");
     usertf.setText(result[0]);
     usertf1.setText(result[1]);
     
 }
    void displayText1() throws IOException{
        String s = readData1();
     String[] result = s.split("\\s");
     usertf2.setText(result[0]);
     usertf3.setText(result[1]);
     
 }
    
 public void saveData() throws ClassNotFoundException {
        System.out.println("DBDBDBDBDDBDBDBDBDBDBBDDBDBBDBDBDBDDB");
        try {
            PreparedStatement ps;
            Connection conn = DBConn.dataBase();
            String sql = "INSERT INTO DIAGNOSIS (patientID,patientname,doctorID,doctorname,diagnosisdate,patientdiagnosis,treatment) VALUES (?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, usertf.getText());
            ps.setString(2, usertf1.getText());
            ps.setString(3, usertf2.getText());
            ps.setString(4, usertf3.getText());
            ps.setString(5, ((JTextField) diagDate.getDateEditor().getUiComponent()).getText());
            ps.setString(6, usertf5.getText());
            ps.setString(7, usertf4.getText());
            
            
            int InsertRec = ps.executeUpdate();
            if (InsertRec > 0 ) {
                System.out.println("DBDBDBDBDDBDBDBDBDBDBBDDBDBBDBDBDBDDB");
                JOptionPane.showMessageDialog(null, "Inserted into the Database");
                updateTable();
                usertf.setText("");
                usertf1.setText("");
                usertf2.setText("");
                usertf3.setText("");
                usertf4.setText("");
                usertf5.setText("");
                
            } else {
                JOptionPane.showMessageDialog(null, "Inserted not into  the Database");
            }

        } catch (SQLException f) {
            f.getMessage();}
        
    }   
       public void updateTable(){
        try{
        
       Connection con = DBConn.dataBase();  
       String sqll = "SELECT * FROM DIAGNOSIS";
       Statement stm = con.createStatement();
       
       ResultSet rset = stm.executeQuery(sqll);
       dbtab.setModel(DbUtils.resultSetToTableModel(rset));}
      catch(SQLException e){} catch (ClassNotFoundException ex) { 
            
        } 
            
   }
  public void searchDB() throws ClassNotFoundException, IOException, SQLException{
       
       Connection conn = DBConn.dataBase();    
       Statement st = conn.createStatement();
       try{    
       //String sql ="SELECT * FROM DIAGNOSIS WHERE doctorID LIKE '"+"%"+setxt.getText()+"%"+"' ";
       //Start searching with initials from the textfield
       String sql ="SELECT * FROM DIAGNOSIS WHERE doctorID LIKE '"+setxt.getText()+"%"+"' OR patientID LIKE '"+setxt.getText()+"%"+"' ";
           ResultSet rs = st.executeQuery(sql);
            DefaultTableModel model = (DefaultTableModel)dbtab.getModel();
           
    int i = 0;
             while (rs.next()){
                 
         model.setNumRows(i+1);
         model.setColumnCount(7);
         
         model.setValueAt(rs.getString("patientID"), i, 0);
         model.setValueAt(rs.getString("patientname"), i, 1);
         model.setValueAt(rs.getString("doctorID"), i, 2);
         model.setValueAt(rs.getString("doctorname"), i, 3);
         model.setValueAt(rs.getDate("diagnosisdate"), i, 4);
         model.setValueAt(rs.getString("patientdiagnosis"), i, 5);
         model.setValueAt(rs.getString("treatment"), i, 6);
         
 i++;
             }}
       catch(SQLException r){System.out.println(r.getMessage());}
   }
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        setxt = new javax.swing.JTextField();
        usertf = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        dbtab = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        inBtn = new javax.swing.JButton();
        upBtn = new javax.swing.JButton();
        deBtn = new javax.swing.JButton();
        clBtn = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        usertf1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        usertf2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        usertf3 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        usertf4 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        usertf5 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        diagDate = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Diagnosis Record");
        setMinimumSize(new java.awt.Dimension(1100, 600));
        setResizable(false);
        setSize(new java.awt.Dimension(1100, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator1.setBackground(new java.awt.Color(49, 150, 222));
        jSeparator1.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222), 2));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 50, 200, 2));

        setxt.setForeground(new java.awt.Color(221, 221, 221));
        setxt.setText("Search with patiendID or doctorID");
        setxt.setBorder(null);
        setxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                setxtFocusGained(evt);
            }
        });
        setxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                setxtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                setxtKeyTyped(evt);
            }
        });
        getContentPane().add(setxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 26, 200, 25));

        usertf.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        usertf.setBorder(null);
        usertf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                usertfFocusGained(evt);
            }
        });
        getContentPane().add(usertf, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 125, 150, 25));

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        dbtab.setAutoCreateRowSorter(true);
        dbtab.setBorder(null);
        dbtab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "<html><center><font  color = #3196de ><b>Patient ID</font></center>", "<html><center><font color = #3196de><b>Patient Name</font></center>", "<html><center><font color = #3196de><b>Doctor ID</font></center>", "<html><center><font color = #3196de><b>Doctor Name</font></center>", "<html><center><font color = #3196de><b>Date</font></center>", "<html><center><font color = #3196de><b>Diagnosis</font></center>", "<html><center><font color = #3196de><b>Treatment</font></center>"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        dbtab.setGridColor(new java.awt.Color(255, 255, 255));
        dbtab.setSelectionBackground(new java.awt.Color(49, 150, 222));
        dbtab.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dbtabFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dbtabFocusLost(evt);
            }
        });
        dbtab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dbtabMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(dbtab);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 767, 580));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/patW.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 775, 588));

        inBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/inW.png"))); // NOI18N
        inBtn.setBorder(null);
        inBtn.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inBtnFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                inBtnFocusLost(evt);
            }
        });
        inBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inBtnActionPerformed(evt);
            }
        });
        getContentPane().add(inBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(793, 60, 73, 30));

        upBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/upW.png"))); // NOI18N
        upBtn.setBorder(null);
        upBtn.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                upBtnFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                upBtnFocusLost(evt);
            }
        });
        upBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upBtnActionPerformed(evt);
            }
        });
        getContentPane().add(upBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(868, 60, 73, 30));

        deBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/deW.png"))); // NOI18N
        deBtn.setBorder(null);
        deBtn.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                deBtnFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                deBtnFocusLost(evt);
            }
        });
        deBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deBtnActionPerformed(evt);
            }
        });
        getContentPane().add(deBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(943, 60, 73, 30));

        clBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/clW.png"))); // NOI18N
        clBtn.setBorder(null);
        clBtn.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                clBtnFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                clBtnFocusLost(evt);
            }
        });
        clBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clBtnActionPerformed(evt);
            }
        });
        getContentPane().add(clBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1018, 60, 73, 30));

        jSeparator2.setBackground(new java.awt.Color(49, 150, 222));
        jSeparator2.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222), 2));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 150, 150, 2));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(49, 150, 222));
        jLabel5.setText("<html><center>Patient<br>Name</center>");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 170, -1, -1));

        jSeparator3.setBackground(new java.awt.Color(49, 150, 222));
        jSeparator3.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222), 2));
        getContentPane().add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 190, 150, 2));

        usertf1.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        usertf1.setBorder(null);
        usertf1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                usertf1FocusGained(evt);
            }
        });
        getContentPane().add(usertf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 163, 150, 25));

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(49, 150, 222));
        jLabel6.setText("Doctor ID");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 220, -1, -1));

        jSeparator4.setBackground(new java.awt.Color(49, 150, 222));
        jSeparator4.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222), 2));
        getContentPane().add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 240, 150, 2));

        usertf2.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        usertf2.setBorder(null);
        usertf2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                usertf2FocusGained(evt);
            }
        });
        getContentPane().add(usertf2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 213, 150, 25));

        jLabel7.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(49, 150, 222));
        jLabel7.setText("<html><center>Doctor<br>Name</center>");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 265, -1, -1));

        jSeparator5.setBackground(new java.awt.Color(49, 150, 222));
        jSeparator5.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222), 2));
        getContentPane().add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 290, 150, 2));

        usertf3.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        usertf3.setBorder(null);
        usertf3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                usertf3FocusGained(evt);
            }
        });
        getContentPane().add(usertf3, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 263, 150, 25));

        jLabel8.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(49, 150, 222));
        jLabel8.setText("Treatment");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 440, -1, -1));

        jSeparator6.setBackground(new java.awt.Color(49, 150, 222));
        jSeparator6.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222), 2));
        getContentPane().add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(875, 460, 150, 2));

        usertf4.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        usertf4.setBorder(null);
        usertf4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                usertf4FocusGained(evt);
            }
        });
        getContentPane().add(usertf4, new org.netbeans.lib.awtextra.AbsoluteConstraints(875, 433, 150, 25));

        jLabel9.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(49, 150, 222));
        jLabel9.setText("<html><center>Patient<br>Diagnosis</center>");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 380, -1, -1));

        jSeparator7.setBackground(new java.awt.Color(49, 150, 222));
        jSeparator7.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222), 2));
        getContentPane().add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 400, 150, 2));

        usertf5.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        usertf5.setBorder(null);
        usertf5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                usertf5FocusGained(evt);
            }
        });
        getContentPane().add(usertf5, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 373, 150, 25));

        jLabel10.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(49, 150, 222));
        jLabel10.setText("Diagnosis Date");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 330, -1, -1));

        diagDate.setDateFormatString("yyyy-MM-dd");
        getContentPane().add(diagDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 320, 140, -1));

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(49, 150, 222));
        jLabel4.setText("Patient ID");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 130, -1, -1));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/bgr1.png"))); // NOI18N
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(795, 100, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/paW.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 6, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/bgr.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void dbtabFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dbtabFocusLost
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/pics/patW.png")));
    }//GEN-LAST:event_dbtabFocusLost

    private void dbtabFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dbtabFocusGained
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/pics/patB.png")));
    }//GEN-LAST:event_dbtabFocusGained

    private void setxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_setxtFocusGained
        setxt.setText("");
    }//GEN-LAST:event_setxtFocusGained

    private void inBtnFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_inBtnFocusGained
        inBtn.setIcon(new ImageIcon(getClass().getResource("/pics/inB.png")));
    }//GEN-LAST:event_inBtnFocusGained

    private void inBtnFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_inBtnFocusLost
        inBtn.setIcon(new ImageIcon(getClass().getResource("/pics/inW.png")));
    }//GEN-LAST:event_inBtnFocusLost

    private void usertfFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usertfFocusGained
        if(setxt.getText().length()== 0){
            setxt.setForeground(Color.decode("#dddddd"));
            setxt.setText("Search");
        }
    }//GEN-LAST:event_usertfFocusGained

    private void usertf1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usertf1FocusGained
        if(setxt.getText().length()== 0){
            setxt.setForeground(Color.decode("#dddddd"));
            setxt.setText("Search");
        }
    }//GEN-LAST:event_usertf1FocusGained

    private void usertf2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usertf2FocusGained
        if(setxt.getText().length()== 0){
            setxt.setForeground(Color.decode("#dddddd"));
            setxt.setText("Search");
        }
    }//GEN-LAST:event_usertf2FocusGained

    private void usertf3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usertf3FocusGained
        if(setxt.getText().length()== 0){
            setxt.setForeground(Color.decode("#dddddd"));
            setxt.setText("Search");
        }
    }//GEN-LAST:event_usertf3FocusGained

    private void usertf4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usertf4FocusGained
        if(setxt.getText().length()== 0){
            setxt.setForeground(Color.decode("#dddddd"));
            setxt.setText("Search");
        }
    }//GEN-LAST:event_usertf4FocusGained

    private void usertf5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usertf5FocusGained
        if(setxt.getText().length()== 0){
            setxt.setForeground(Color.decode("#dddddd"));
            setxt.setText("Search");
        }
    }//GEN-LAST:event_usertf5FocusGained

    private void upBtnFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_upBtnFocusGained
        upBtn.setIcon(new ImageIcon(getClass().getResource("/pics/upB.png")));
    }//GEN-LAST:event_upBtnFocusGained

    private void upBtnFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_upBtnFocusLost
        upBtn.setIcon(new ImageIcon(getClass().getResource("/pics/upW.png")));
    }//GEN-LAST:event_upBtnFocusLost

    private void deBtnFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_deBtnFocusGained
        deBtn.setIcon(new ImageIcon(getClass().getResource("/pics/deB.png")));
    }//GEN-LAST:event_deBtnFocusGained

    private void deBtnFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_deBtnFocusLost
        deBtn.setIcon(new ImageIcon(getClass().getResource("/pics/deW.png")));
    }//GEN-LAST:event_deBtnFocusLost

    private void clBtnFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_clBtnFocusGained
        clBtn.setIcon(new ImageIcon(getClass().getResource("/pics/clB.png")));
    }//GEN-LAST:event_clBtnFocusGained

    private void clBtnFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_clBtnFocusLost
        clBtn.setIcon(new ImageIcon(getClass().getResource("/pics/clW.png")));
    }//GEN-LAST:event_clBtnFocusLost

    private void setxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_setxtKeyTyped
        setxt.setForeground(Color.BLACK);
        
        
    }//GEN-LAST:event_setxtKeyTyped

    private void inBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inBtnActionPerformed
        try {
            saveData();
        } catch (ClassNotFoundException ex) {
            
        }
    }//GEN-LAST:event_inBtnActionPerformed

    private void upBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upBtnActionPerformed
        updateDB();
    }//GEN-LAST:event_upBtnActionPerformed

    private void dbtabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dbtabMouseClicked
        DefaultTableModel model = (DefaultTableModel)dbtab.getModel();
        getTableVal1 = model.getValueAt(dbtab.getSelectedRow(),0).toString();
        getTableVal3 = model.getValueAt(dbtab.getSelectedRow(),2).toString();
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = model.getValueAt(dbtab.getSelectedRow(),4).toString();
        try {
            Date date = formatter.parse(dateInString);
            usertf.setText(model.getValueAt(dbtab.getSelectedRow(),0).toString());
        usertf1.setText(model.getValueAt(dbtab.getSelectedRow(),1).toString());
        usertf2.setText(model.getValueAt(dbtab.getSelectedRow(),2).toString());
        usertf3.setText(model.getValueAt(dbtab.getSelectedRow(),3).toString());
        usertf4.setText(model.getValueAt(dbtab.getSelectedRow(),6).toString());
        usertf5.setText(model.getValueAt(dbtab.getSelectedRow(),5).toString());
        
        diagDate.setDate(date);
        
        } catch (ParseException ex) {
            Logger.getLogger(PatientRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_dbtabMouseClicked

    private void deBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deBtnActionPerformed
        deleteRec();
       
    }//GEN-LAST:event_deBtnActionPerformed

    private void clBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clBtnActionPerformed
                
        usertf4.setText("");
        usertf5.setText("");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = "0000-00-00";
        try {
            Date date = formatter.parse(dateInString);
        
        diagDate.setDate(date);
        
        } catch (ParseException ex) {
            Logger.getLogger(PatientRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
        
                
    }//GEN-LAST:event_clBtnActionPerformed

    private void setxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_setxtKeyReleased
        try {
            searchDB();
        } catch (ClassNotFoundException ex) {
            } catch (IOException ex) {
            } catch (SQLException ex) {
            Logger.getLogger(PatientRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_setxtKeyReleased

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        new HomePage().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PatientRecord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PatientRecord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PatientRecord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PatientRecord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new PatientRecord().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(PatientRecord.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clBtn;
    private javax.swing.JTable dbtab;
    private javax.swing.JButton deBtn;
    private com.toedter.calendar.JDateChooser diagDate;
    private javax.swing.JButton inBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTextField setxt;
    private javax.swing.JButton upBtn;
    private javax.swing.JTextField usertf;
    private javax.swing.JTextField usertf1;
    private javax.swing.JTextField usertf2;
    private javax.swing.JTextField usertf3;
    private javax.swing.JTextField usertf4;
    private javax.swing.JTextField usertf5;
    // End of variables declaration//GEN-END:variables
}
