

import ConectorBD.ConexionMySQL;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class Clientes extends JFrame {
    private Button modificar,guardar;
    JLabel idcliente,nombre,telefono,correo,adeudo,fechavisita;
    JTextField idclientet,nombret,telefonot,correot, adeudot,fechavisitat;
//nombre-numero de telefono,correo electronico
    private JPanel paneB;


    protected  Clientes(){

        this.setTitle("Crear nuevo Cliente");
        this.setLayout(new BorderLayout());
        this.setSize(500,200);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(500,200);

        //panel de botones
        paneB = new JPanel();
        paneB.setLayout(new FlowLayout());
        modificar=new Button("Modificar Cliente");
        paneB.add(modificar);
        guardar=new Button("Guardar");
        paneB.add(guardar);
        guardar.addActionListener(GuardaDatosCliente);
//***************************************************************************************Segundo Panel
        JPanel paneA = new JPanel();
        paneA.setLayout(new GridLayout(6,4));

        idcliente=new JLabel("IdCliente: ");
        paneA.add(idcliente);
        idclientet=new JTextField();
        paneA.add(idclientet);
       
        nombre=new JLabel("Nombre: ");
        paneA.add(nombre);
       nombret =new JTextField();
        paneA.add(nombret);
        
        
        telefono=new JLabel("Telefono: ");
        paneA.add(telefono);
        telefonot=new JTextField();
        paneA.add(telefonot);


        correo=new JLabel("Correo electronico: ");
        paneA.add(correo);
        correot=new JTextField();
        paneA.add(correot);

        adeudo=new JLabel("Adeudo: ");
        paneA.add(adeudo);
        adeudot=new JTextField();
        paneA.add(adeudot);

        fechavisita=new JLabel("Fechavisita: ");
        paneA.add(fechavisita);
        fechavisitat=new JTextField();
        paneA.add(fechavisitat);

//*****************************************************************************************************


        this.add(paneB,BorderLayout.NORTH);
        this.add(paneA,BorderLayout.CENTER);
        
        revalidate();
        repaint();



    }
   ActionListener GuardaDatosCliente = new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent evt)
            {
               try {
                        
            PreparedStatement pst = cn.prepareStatement("INSERT  INTO cliente (idCliente,Nombre,telefono,correo,Adeudo_Generado,Fecha_Visita) VALUES(?,?,?,?,?,?)");
            pst.setInt(1,Integer.valueOf(idclientet.getText()));
            pst.setString(2,nombret.getText());
            pst.setInt(3,Integer.valueOf(telefonot.getText()));
            pst.setString(4,correot.getText());
            pst.setString(5,adeudot.getText());
            pst.setString(6,fechavisitat.getText());
            pst.executeUpdate();
        } catch (SQLException ex) {
            
        }
            }
            
           
    };
  

ConexionMySQL mysql =new ConexionMySQL();
        Connection cn = mysql.Conectar();

}
