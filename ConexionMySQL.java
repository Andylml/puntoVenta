/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConectorBD;

import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author andre
 */
public class ConexionMySQL {
     public String db = "puntoVenta";
    public String url = "jdbc:mysql://localhost/"+db;
    public String user = "root";
    public String pass = "";
    Connection link = null;

   public Connection Conectar(){

       Connection link = null;

       try{

           Class.forName("org.gjt.mm.mysql.Driver");

           link = DriverManager.getConnection(this.url, this.user, this.pass);

       }catch(Exception ex){

           JOptionPane.showMessageDialog(null, ex);

       }


       return link;

   }
    
    
}
