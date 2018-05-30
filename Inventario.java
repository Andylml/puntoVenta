
import ConectorBD.ConexionMySQL;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Inventario extends JInternalFrame implements ActionListener {
    
    JLabel codigo,descripcion,cantidad,cantidadAct,cant,nom;
    String nombre="--";
    float cantida=0;
    JTextField codigot,cantidadt;
    JButton agrega,Reporte;

    private JPanel paneB;
    protected Inventario() throws PropertyVetoException{
        this.setTitle("Inventario");
        this.setLayout(new BorderLayout());
        this.setSize(250,200);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(250,200);

        //panel de botones
        paneB = new JPanel();
        paneB.setLayout(new FlowLayout());

        agrega= new JButton("Guardar");
        paneB.add(agrega);
        Reporte= new JButton("Reporte de inventario");
        Reporte.addActionListener(this);
        paneB.add(Reporte);
        
        


        JPanel paneA = new JPanel();
        paneA.setLayout(new GridLayout(4,2));

        codigo=new JLabel("Codigo");
        paneA.add(codigo);
        codigot=new JTextField();
        paneA.add(codigot);


        descripcion=new JLabel("Descripcion de producto");
        paneA.add(descripcion);
        nom=new JLabel(nombre);
        paneA.add(nom);


       cantidadAct=new JLabel("Cantidad Actual");
        paneA.add(cantidadAct);
        String s;
        cant=new JLabel(s = Float.toString(cantida));
        paneA.add(cant);


        cantidad=new JLabel("Cantidad que agregara");
        paneA.add(cantidad);
        cantidadt=new JTextField();
        paneA.add(cantidadt);

        this.add(paneB,BorderLayout.NORTH);
        this.add(paneA,BorderLayout.CENTER);
        this.setClosable(true);
        this.toFront();
        this.setVisible(true);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        pack();


    }
    

public void actionPerformed (ActionEvent evento){
    if(evento.getSource()==Reporte){
     
     ReporteInv c1= new ReporteInv();  
 
   
}
}
ConexionMySQL mysql =new ConexionMySQL();
     Connection cn = mysql.Conectar();
}
