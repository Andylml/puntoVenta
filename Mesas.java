
import ConectorBD.ConexionMySQL;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;


public class Mesas extends JInternalFrame implements ActionListener {

    JFrame Principal;
    JPanel P1, P2;
    JButton Boton[], Agregar;
    JTextField Can, Cantidad, Total;
    JTable tabla = new JTable();
    DefaultTableModel modelo = new DefaultTableModel();
    double sumaprecio=0;
    
    private void ingresarData(DefaultTableModel modelo, String cant, String precio, String nombre){
        
        String [] datos = new String [3];
        datos [1]= nombre;
        datos [2]= precio;
        datos [0]= cant;
        modelo.addRow(datos);
        tabla.setModel(modelo);
    }
    
    public Mesas() throws PropertyVetoException {
        // La accion para cerrar la ventana 

        // Tamaño  
        setTitle("Birrieria Vallarta");
        getContentPane().setLayout(new BorderLayout());
        //Cuenta de la mesa*********************************************************************
         modelo.addColumn("Nombre");
         modelo.addColumn("Cantidad");
         modelo.addColumn("Precio");
         tabla.setModel(modelo);  
        JScrollPane panel = new JScrollPane(tabla);
        
        P1 = new JPanel();
        P1.setLayout(new GridLayout(6, 3));
        
        

        //Textos Total cuentas***********************************************************

        Total = new JTextField("Total..............");
        Total.setEditable(false);
        
        //Boton para agregar Articulos a la cuenta
        Agregar = new JButton("Cobrar");
        Agregar.setSize(2,1);
        Agregar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                AgregarEvento();
            }
        });
        
        //Campo de texto para agregar la cantidad de productos*****************************
        Cantidad = new JTextField(10);
        Cantidad.setSize(1,2);
        Can = new JTextField("Precio total: "+Double.toString(sumaprecio));
        Can.setFont(new java.awt.Font("Tahoma", 1, 30));
        Can.revalidate();
        Can.repaint();
        Can.setEditable(false);
   //Boton para subtotalizar
    
    
    
        
        
        panel.setSize(10,10);
        panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        P2 = new JPanel();
        P2.setLayout(new BorderLayout());
        P2.add(Total);
        P2.add(Can,BorderLayout.CENTER);
        P2.add(Cantidad,BorderLayout.NORTH);
        P2.add(Agregar,BorderLayout.SOUTH);
        add(P2, BorderLayout.SOUTH);

        this.add(panel, BorderLayout.CENTER);

        this.setClosable(true);
        this.toFront();
        this.setVisible(true);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        revalidate();
        repaint();
        add(P1, BorderLayout.EAST);
        pack();
        Evento();
    }
    
    
    
    private void AgregarEvento(){
        Double pago = 0.0;
        
        Object[] options = {"Credito", "Contado", "Cancelar pedido"};
        
        if(sumaprecio > 0){
            int opc = JOptionPane.showOptionDialog(this, "¿Forma de Pago?", "Forma de pago", 
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

            //Es a Credito
            if(opc==0){
                CobrarCredito cb = new CobrarCredito(sumaprecio);
                
            }else if(opc == 1){ //Es a Contado
                while(pago < sumaprecio){
                    pago = Double.parseDouble(JOptionPane.showInputDialog(this, "¿Con cuanto va a pagar?", null));

                    if(pago < sumaprecio){
                        JOptionPane.showMessageDialog(this, "El pago es menor al costo del consumo!!");
                    }
                }

                Double cambio = pago - sumaprecio;

                JOptionPane.showMessageDialog(this, "El cambio es: " + cambio);
            }
        }else{
            JOptionPane.showMessageDialog(this, "No hay nada que cobrar!");
        }
    }
    
    
    
    
    private void Evento() {
       
       try {
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM menu;");
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                P1.add(crearBoton(rs.getString("Nombre_Plato"), rs.getString("Precio")));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Mesas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
       
       
       
   
    }
    
    private JButton crearBoton(String nombre, String precio){
        JButton boton = new JButton(nombre);
        boton.setPreferredSize(new Dimension(250,75));
        
        boton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                agregarFila(nombre, precio);
                sumaprecio=sumaprecio+Integer.parseInt(precio);
                Can.setText("Precio total: "+Double.toString(sumaprecio));
                  Can.revalidate();
        Can.repaint();
            }
            
        });
        
        return boton;
    }
    
    
     private int agregarFila(String nombre, String precio){
        int cantidad, precioFinal;
        String[] fila = {nombre, "1", precio};
        
        for(int i = 0; i < modelo.getRowCount(); i++){
            if(modelo.getValueAt(i, 0).equals(nombre)){
                
                cantidad = Integer.valueOf((String)modelo.getValueAt(i, 1)) + 1;
                precioFinal = Integer.valueOf(precio) * cantidad;
                
                modelo.setValueAt(String.valueOf(cantidad), i, 1);
                modelo.setValueAt(String.valueOf(precioFinal), i, 2);
                
                return 0;
            }
        }
        
        modelo.addRow(fila);
        
        return 0;
    }
     
     
     
     
     
    public void actionPerformed(ActionEvent evento) {

    }
    ConexionMySQL mysql =new ConexionMySQL();
     Connection cn = mysql.Conectar();
}

