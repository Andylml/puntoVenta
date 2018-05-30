
import ConectorBD.ConexionMySQL;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego García Montejo
 */
public class Facturacion extends JInternalFrame implements ActionListener {

    Button modificar, eliminar, guardar;
    JLabel RFC, Nombre, Correo, Telefono;
    JTextField TextRFC, TextNombre, TextCorreo, TextTelefono;
    private JPanel paneB;

    public Facturacion() throws PropertyVetoException {

        this.setTitle("Crear nuevo Producto");
        this.setLayout(new BorderLayout());
        this.setSize(250, 200);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(250, 200);

        //panel de botones
        
        paneB = new JPanel();
        paneB.setLayout(new FlowLayout());

        modificar = new Button("Modificar Cliente");
        paneB.add(modificar);

        eliminar = new Button("Eliminar Cliente");
        paneB.add(eliminar);

        guardar = new Button("Guardar");
        paneB.add(guardar);
        guardar.addActionListener(guardaFactura);
//***************************************************************************************Segundo Panel
        JPanel paneA = new JPanel();
        paneA.setLayout(new GridLayout(4, 2));

        RFC = new JLabel("RFC");
        paneA.add(RFC);
        TextRFC = new JTextField();
        paneA.add(TextRFC);

        Nombre = new JLabel("Nombre del cliente");
        paneA.add(Nombre);
        TextNombre = new JTextField();
        paneA.add(TextNombre);

        Correo = new JLabel("Correo Electronico");
        paneA.add(Correo);
        TextCorreo = new JTextField();
        paneA.add(TextCorreo);

        Telefono = new JLabel("Numero de Telefono");
        paneA.add(Telefono);
        TextTelefono = new JTextField();
        paneA.add(TextTelefono);

//*****************************************************************************************************
        this.setClosable(true);
        this.toFront();
        this.setVisible(true);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        this.add(paneB, BorderLayout.NORTH);
        this.add(paneA, BorderLayout.CENTER);
        pack();

        this.setClosable(true);
        this.toFront();
        this.setVisible(true);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
    }
    ActionListener guardaFactura = new ActionListener()
    {
        public void actionPerformed(ActionEvent event)
        {
            try{
                
                PreparedStatement fac = cn.prepareStatement(" INSERT INTO factura(RFC,NombreCliente,correo,NumeroTel)VALUES(?,?,?,?)");
                fac.setString(1,TextRFC.getText());
                fac.setString(2,TextNombre.getText());
                fac.setString(3, TextCorreo.getText());
                fac.setInt(4,Integer.valueOf(TextTelefono.getText()));
                fac.executeUpdate();
            } catch (SQLException ex){
                
            }
        }
        
    };
            ConexionMySQL mysql =new ConexionMySQL();
        Connection cn = mysql.Conectar();
    public void actionPerformed(ActionEvent evento) {

    }
}
