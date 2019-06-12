/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author User 2
 */
public class DBConn {
    static String url1 = "jdbc:mysql://localhost:3306/Health Screening";
    static String username1 = "root";
    static String password1 = "";
    
    
    
    public static Connection dataBase() throws ClassNotFoundException{
       try{ 
           Class.forName("com.mysql.jdbc.Driver");
    Connection conn = DriverManager.getConnection(url1,username1,password1);
    
    
    
    return conn;
       }
       catch(SQLException e){
       return null;
       }
    }
    
    public static Connection dataBase1() throws ClassNotFoundException{
       try{ 
           Class.forName("org.sqlite.JDBC");
    Connection conn = DriverManager.getConnection("jdbc:sqlite:Hospital.db");
    String tableName = "Doctor";
    if(checkTable(conn,tableName)){
       System.out.println("Table octor already exist");
    }
    else{
        Statement st = conn.createStatement();
        boolean rs = st.execute("CREATE TABLE Doctor (doctorID varchar(15),password varchar(50),firstname varchar(30),lastname varchar(30),primary key(doctorID));");
        if(!rs)
            System.out.println("Doctor table created");
        else
           System.out.println("Doctor not table created"); 
            
    }
    String tableName1 = "Nurse";
    if(checkTable(conn,tableName1)){
       System.out.println("Table nurse already exist");
    }
    else{
        Statement st = conn.createStatement();
        boolean rs = st.execute("CREATE TABLE Nurse (nurseID varchar(15),password varchar(50),firstname varchar(30),lastname varchar(30),primary key(nurseID));");
        if(!rs)
            System.out.println("Nurse table created");
        else
           System.out.println("Nurse not table created"); 
            
    }
    
     String tableName2 = "Student";
    if(checkTable(conn,tableName2)){
       System.out.println("Table student already exist");
    }
    else{
        Statement st = conn.createStatement();
        boolean rs = st.execute("CREATE TABLE Student (MATRICNUM varchar(15),LABEL varchar(5),FIRSTNAME varchar(30),LASTNAME varchar(30),GENDER varchar(10),FACULTY varchar(30),"+
                "DEPARTMENT varchar(30),DOB date,ADDRESS varchar(50),PASSPORT blob, primary key(LABEL));");
        if(!rs)
            System.out.println("Student table created");
        else
           System.out.println("Student not table created"); 
            
    }
    
     String tableName3 = "Patient";
    if(checkTable(conn,tableName3)){
       System.out.println("Table Patient already exist");
    }
    else{
        Statement st = conn.createStatement();
        boolean rs = st.execute("CREATE TABLE Patient (PATIENTID varchar(15),LABEL varchar(5),WEIGHT varchar(3),HEIGHT varchar(3),BLOODGROUP varchar(3),GENOTYPE varchar(3),"
                + "primary key(PATIENTID), FOREIGN KEY (LABEL) REFERENCES Student(LABEL) );");
        if(!rs)
            System.out.println("Patient table created");
        else
           System.out.println("Patient not table created"); 
            
    }
    
    return conn;
       }
       catch(SQLException e){
       return null;
       }
    }

    //private static boolean checkTable(SQLiteDatabase db, String tableName ) {
        private static boolean checkTable(Connection con,String tableName ) throws SQLException {

DatabaseMetaData dbm = con.getMetaData();
// check if "employee" table is there
ResultSet tables = dbm.getTables(null, null, tableName, null);
        return tables.next();
    
        //Statement st = con.createStatement();
        //ResultSet rs = st.executeQuery("");
        
        }

    
}
