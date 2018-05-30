
import ConectorBD.ConexionMySQL;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Principal extends JFrame {
    
    JTabbedPane Principal;

 

    public Principal() throws PropertyVetoException {
        // La accion para cerrar la ventana 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tamaño ***********************************************
        setSize(1080,1080);
        setLocationRelativeTo(null);

        //Titulo del Frame***************************************
        setTitle("Birrieria ITE");

        Principal = new JTabbedPane();
        
        
        Principal.addTab("Mesas", null, new Mesas());
        Principal.addTab("Creditos",null,new Creditos());
        Principal.addTab("Inventario",null,new Inventario());
        Principal.addTab("Productos",null,new Productos());
        Principal.addTab("Facturas",null,new Facturacion());
        
        add(Principal);
        setVisible(true);

    }
    ConexionMySQL mysql =new ConexionMySQL();
     Connection cn = mysql.Conectar();
}
