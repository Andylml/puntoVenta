
import ConectorBD.ConexionMySQL;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import javax.swing.*;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Productos extends JInternalFrame implements ActionListener {

    private Button nuevo, modificar, eliminar, promociones, guardar;
    JLabel codigo, descripcion, precio, cantidad;
    JTextField codigot, descripciont, preciot, cantidadt;
       
    private JPanel paneB;

    protected Productos() throws PropertyVetoException {

        this.setTitle("Crear nuevo Producto");
        this.setLayout(new BorderLayout());
        this.setSize(250, 200);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(250, 200);

        //panel de botones
        paneB = new JPanel();
        paneB.setLayout(new FlowLayout());

        modificar = new Button("Modificar Producto");
        paneB.add(modificar);

        eliminar = new Button("Eliminar Productos");
        paneB.add(eliminar);

        guardar = new Button("Guardar");
        paneB.add(guardar);
        guardar.addActionListener(guardardatos);
//***************************************************************************************Segundo Panel
        JPanel paneA = new JPanel();
        paneA.setLayout(new GridLayout(4, 2));

        codigo = new JLabel("Codigo");
        paneA.add(codigo);
        codigot = new JTextField();
        paneA.add(codigot);

        descripcion = new JLabel("Descripcion");
        paneA.add(descripcion);
        descripciont = new JTextField();
        paneA.add(descripciont);

        precio = new JLabel("Precio");
        paneA.add(precio);
        preciot = new JTextField();
        paneA.add(preciot);

        cantidad = new JLabel("Cantidad");
        paneA.add(cantidad);
        cantidadt = new JTextField();
        paneA.add(cantidadt);
        
//*****************************************************************************************************
        this.setClosable(true);
        this.toFront();
        this.setVisible(true);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        this.add(paneB, BorderLayout.NORTH);
        this.add(paneA, BorderLayout.CENTER);
        pack();
      
      
    }
       //INSERTAR PRODUCTOS EN LA TABLA
     ActionListener guardardatos=new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent evt)
            {
               try {
                        
            PreparedStatement ps = cn.prepareStatement("INSERT  INTO productos (codigo,descripcion,precio,cantidad) VALUES(?,?,?,?)");
            ps.setString(1,codigot.getText());
            ps.setString(2,descripciont.getText());
            ps.setString(3,preciot.getText());
            ps.setString(4,cantidadt.getText());
            ps.executeUpdate();            
            codigot.setText("");
            descripciont.setText("");
            preciot.setText("");
            cantidadt.setText("");
            
        } catch (SQLException ex) {
            
        }
            }
        };    
        
    public void actionPerformed(ActionEvent evento) {
        
             
    }
    
            ConexionMySQL mysql =new ConexionMySQL();
            Connection cn = mysql.Conectar();  
}
