/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;

/**
 *
 * @author User 2
 */
public class ClinicData extends javax.swing.JFrame {

    /**
     * Creates new form ClinicData
     */
    public ClinicData() throws IOException {
        initComponents();
        displayData();
        nametxt.setEditable(false);
    }
    private static String data;
    ValidateFields valid = new ValidateFields();
    
    public String readData() throws IOException {
            String val = null;
            try {
                BufferedReader checkVal = new BufferedReader(new FileReader("recogdata.txt"));
                val = checkVal.readLine();
                System.out.println(val);
                checkVal.close();
            } catch (FileNotFoundException ex) {
            }
            return val;
        }
    
    public void displayData() throws IOException{
        String[] result = readData().split("\\s");
     palbl.setText(result[0]);
     nametxt.setText(result[1]);
    }
    
    
    public void writeData(String s) throws IOException {
            BufferedWriter writeval = new BufferedWriter(new FileWriter("clinicdata.txt"));//"C:\\Users\\User 2\\Desktop\\clinicdata.txt"));
            writeval.write(s);
            writeval.close();

        }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        palbl = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        temtxt = new javax.swing.JTextField();
        blotxt = new javax.swing.JTextField();
        weitxt = new javax.swing.JTextField();
        heitxt = new javax.swing.JTextField();
        bmitxt = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        nametxt = new javax.swing.JTextField();
        nametxt1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Clinic Data");
        setBackground(new java.awt.Color(221, 221, 221));
        setForeground(new java.awt.Color(221, 221, 221));
        setPreferredSize(new java.awt.Dimension(400, 320));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        palbl.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        palbl.setText("cc");
        jPanel1.add(palbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 7, 129, -1));

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(49, 150, 222));
        jLabel2.setText("Temperature (C)");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 100, -1));

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(49, 150, 222));
        jLabel3.setText("Patient ID");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, -1, -1));

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(49, 150, 222));
        jLabel4.setText("Height (m)");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, -1, -1));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(49, 150, 222));
        jLabel5.setText("Blood Pressure");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, -1, -1));

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(49, 150, 222));
        jLabel6.setText("Weight (kg)");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        jLabel7.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(49, 150, 222));
        jLabel7.setText("Body Mass Index (BMI)");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, -1, -1));

        temtxt.setBackground(new java.awt.Color(221, 221, 221));
        temtxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        temtxt.setBorder(null);
        temtxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                temtxtFocusLost(evt);
            }
        });
        temtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temtxtActionPerformed(evt);
            }
        });
        jPanel1.add(temtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 150, 30));

        blotxt.setBackground(new java.awt.Color(221, 221, 221));
        blotxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        blotxt.setBorder(null);
        blotxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                blotxtFocusLost(evt);
            }
        });
        blotxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blotxtActionPerformed(evt);
            }
        });
        jPanel1.add(blotxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 60, 150, 30));

        weitxt.setBackground(new java.awt.Color(221, 221, 221));
        weitxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        weitxt.setBorder(null);
        weitxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                weitxtFocusLost(evt);
            }
        });
        weitxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weitxtActionPerformed(evt);
            }
        });
        jPanel1.add(weitxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 150, 30));

        heitxt.setBackground(new java.awt.Color(221, 221, 221));
        heitxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        heitxt.setBorder(null);
        heitxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                heitxtFocusLost(evt);
            }
        });
        heitxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                heitxtActionPerformed(evt);
            }
        });
        jPanel1.add(heitxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, 150, 30));

        bmitxt.setBackground(new java.awt.Color(221, 221, 221));
        bmitxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        bmitxt.setBorder(null);
        bmitxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                bmitxtFocusLost(evt);
            }
        });
        bmitxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bmitxtActionPerformed(evt);
            }
        });
        jPanel1.add(bmitxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 150, 30));

        jSeparator1.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 150, 2));

        jSeparator2.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 150, 2));

        jSeparator3.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, 150, 2));

        jSeparator4.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, 150, 2));

        jSeparator5.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, 150, 2));

        jButton1.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(49, 150, 222));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/sa.PNG"))); // NOI18N
        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, -1, -1));

        jLabel8.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(49, 150, 222));
        jLabel8.setText("Patient Name");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        jSeparator6.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 150, 2));

        nametxt.setBackground(new java.awt.Color(221, 221, 221));
        nametxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        nametxt.setBorder(null);
        nametxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                nametxtFocusLost(evt);
            }
        });
        nametxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nametxtActionPerformed(evt);
            }
        });
        jPanel1.add(nametxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 150, 30));

        nametxt1.setBackground(new java.awt.Color(221, 221, 221));
        nametxt1.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        nametxt1.setBorder(null);
        nametxt1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                nametxt1FocusLost(evt);
            }
        });
        nametxt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nametxt1ActionPerformed(evt);
            }
        });
        jPanel1.add(nametxt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 150, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 420, 320));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void temtxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_temtxtFocusLost
        boolean tem = !valid.validateWH(temtxt.getText());
            if (tem){
                temtxt.setBorder(BorderFactory.createLineBorder(Color.red));
            }
            else{
                temtxt.setBorder(null);}
        
    }//GEN-LAST:event_temtxtFocusLost

    private void temtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_temtxtActionPerformed

    private void blotxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_blotxtFocusLost
        boolean tem = !valid.validateWH(blotxt.getText());
            if (tem){
                blotxt.setBorder(BorderFactory.createLineBorder(Color.red));
            }
            else{
                blotxt.setBorder(null);}
        
    }//GEN-LAST:event_blotxtFocusLost

    private void blotxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blotxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_blotxtActionPerformed

    private void weitxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_weitxtFocusLost
        boolean tem = !valid.validateWH(weitxt.getText());
            if (tem){
                weitxt.setBorder(BorderFactory.createLineBorder(Color.red));
            }
            else{
                weitxt.setBorder(null);}
        
    }//GEN-LAST:event_weitxtFocusLost

    private void weitxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weitxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_weitxtActionPerformed

    private void heitxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_heitxtFocusLost
        boolean tem = !valid.validateWH(heitxt.getText());
            if (tem){
                heitxt.setBorder(BorderFactory.createLineBorder(Color.red));
            }
            else{
                heitxt.setBorder(null);}
        
    }//GEN-LAST:event_heitxtFocusLost

    private void heitxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_heitxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_heitxtActionPerformed

    private void bmitxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bmitxtFocusLost
        boolean tem = !valid.validateWH(bmitxt.getText());
            if (tem){
                bmitxt.setBorder(BorderFactory.createLineBorder(Color.red));
            }
            else{
                bmitxt.setBorder(null);}
        
    }//GEN-LAST:event_bmitxtFocusLost

    private void bmitxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bmitxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bmitxtActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        data = palbl.getText()+" "+temtxt.getText()+" "+blotxt.getText()+" "+weitxt.getText()+" "+heitxt.getText()+" "+bmitxt.getText()+" "+nametxt.getText();
        System.out.println(data);
        try {
            writeData(data);
        } catch (IOException ex) {
            
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void nametxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nametxtFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_nametxtFocusLost

    private void nametxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nametxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nametxtActionPerformed

    private void nametxt1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nametxt1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_nametxt1FocusLost

    private void nametxt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nametxt1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nametxt1ActionPerformed

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
            java.util.logging.Logger.getLogger(ClinicData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClinicData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClinicData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClinicData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ClinicData().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(ClinicData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField blotxt;
    private javax.swing.JTextField bmitxt;
    private javax.swing.JTextField heitxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTextField nametxt;
    private javax.swing.JTextField nametxt1;
    private javax.swing.JLabel palbl;
    private javax.swing.JTextField temtxt;
    private javax.swing.JTextField weitxt;
    // End of variables declaration//GEN-END:variables
}
