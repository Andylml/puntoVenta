import ConectorBD.ConexionMySQL;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class ReporteInv extends JFrame {


    JTable tablaInv = new JTable();
    public ReporteInv(){
        
        //propiedades del frame
      this.setTitle("Reporte de inventario");
        this.setLayout(new BorderLayout());
        this.setSize(800,400);
        this.setVisible(true);
       this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(250,200);
        //panel de botones

        //panel de lista (scroll pane)
        
        JScrollPane panel =new JScrollPane(tablaInv);
        panel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //añadir los paneles al frame
        this.add(panel,BorderLayout.CENTER);

        revalidate();
        repaint();
        mostrardatos();
     
    }
    ConexionMySQL mysql =new ConexionMySQL();
     Connection cn = mysql.Conectar();
     
     void mostrardatos (){
         
         DefaultTableModel modelo = new DefaultTableModel();
         modelo.addColumn("Codigo");
         modelo.addColumn("Descripcion");
         modelo.addColumn("Cantidad");
         modelo.addColumn("Precio");
         tablaInv.setModel(modelo);  
         String [] datos = new String [4];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM productos");
          while(rs.next()){
              datos [0]=rs.getString(1);
              datos [1]=rs.getString(2);
              datos [2]=rs.getString(3);
              datos [3]=rs.getString(4);
              modelo.addRow(datos);


          }  
          tablaInv.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(ReporteInv.class.getName()).log(Level.SEVERE, null, ex);
        }
                     
         };
}
       

    
  

