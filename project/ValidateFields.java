/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author User 2
 */
public class ValidateFields {

    PreparedStatement ps;
    public boolean validateDate(String test){
        return test.matches("\\d{4}-\\d{2}-\\d{2}");    
            }
    public boolean validateWH(String test){
        return test.matches("[1-9][0-9]+| [0-9]+[0-9]*.[0-9]");    
            }
    
public boolean validateAdd(String test){
        return test.matches("[a-zA-Z]+|[0-9]+");    
            }

     public boolean validateFirstName(String firstName)
 {
     return firstName.matches("[A-Z][a-zA-Z]+");
 }
    public boolean validateMat(String stdid)
 {
    return stdid.matches("[A-Z]{3}\\d{2}[A-Z]{3}\\d{3}");
}
    public boolean validateMatNum(String id) throws ClassNotFoundException{

       Connection conn = DBConn.dataBase();     
       try{    
       String sql = "SELECT MATRICNUM FROM Student";
            ps=conn.prepareStatement (sql); 
            ResultSet rs = ps.executeQuery();
            if( rs.next()){
               // if(rs.getString("MATRICNUM")!= null)
            return id.equalsIgnoreCase(rs.getString("MATRICNUM"));
               
            } }
       catch(SQLException e){
           System.out.println(e.getMessage());
       }
       return false;
    }
 
}
