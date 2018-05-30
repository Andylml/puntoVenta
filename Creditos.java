
import ConectorBD.ConexionMySQL;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class Creditos extends JInternalFrame implements ActionListener{
    private Button cuenta,nvocliente,mdcrcliente,elimcliente;

    private JPanel paneB; 
    JTable tablaClientes = new JTable();
    public Creditos() throws PropertyVetoException{

        //propiedades del frame
        this.setTitle("Creditos de clientes");
        this.setLayout(new BorderLayout());
        this.setSize(800,400);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(250,200);

        //panel de botones
        paneB = new JPanel();
        paneB.setLayout(new FlowLayout());

        cuenta=new Button("Estado de cuenta");
        paneB.add(cuenta);

        nvocliente=new Button("Nuevo cliente");
        paneB.add(nvocliente);
        nvocliente.addActionListener(this);

        mdcrcliente=new Button("Modificar cliente");
        paneB.add(mdcrcliente);

        elimcliente=new Button("Eliminar cliente");
        paneB.add(elimcliente);
        //****************************************************************************************
       

        //panel de lista (scroll pane)
       
        JScrollPane panel =new JScrollPane(tablaClientes);
        panel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //añadir los paneles al frame
        this.add(paneB,BorderLayout.NORTH);
        this.add(panel,BorderLayout.CENTER);
        
        this.setClosable(true);
        this.toFront();
        this.setVisible(true);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        revalidate();
        repaint();
        verClientes();
        //************************************************************************************
    }
    ConexionMySQL mysql =new ConexionMySQL();
     Connection cn = mysql.Conectar();
     void verClientes (){
         DefaultTableModel modelo = new DefaultTableModel();
         modelo.addColumn("ID Cliente");
         modelo.addColumn("Nombre");
         modelo.addColumn("Telefono");
         modelo.addColumn("Correo");
         modelo.addColumn("Adeudo");
         modelo.addColumn("Fecha Visita");
         tablaClientes.setModel(modelo);
         String [] datos = new String [6];
          try{
              Statement st = cn.createStatement();
              ResultSet rs = st.executeQuery("SELECT * FROM cliente");
              while(rs.next()){
                  datos[0]=rs.getString(1);
                  datos[1]=rs.getString(2);
                  datos[2]=rs.getString(3);
                  datos[3]=rs.getString(4);
                  datos[4]=rs.getString(5);
                  datos[5]=rs.getString(6);
                  modelo.addRow(datos);
              }
              tablaClientes.setModel(modelo);
          }catch (SQLException ex){
              
          }
     }
public void actionPerformed (ActionEvent evento){
    if(evento.getSource() == nvocliente){
        Clientes e = new Clientes();
        
    }
}
}