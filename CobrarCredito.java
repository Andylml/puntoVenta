/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ConectorBD.ConexionMySQL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DARK_
 */
public class CobrarCredito extends JFrame{
    private double sumaprecio;
    private JTable tabla = new JTable();
    private JTextField txtBuscar;
    private JLabel lblBuscar;
    private DefaultTableModel modelo = new DefaultTableModel();
    private JButton Aumento, Cancelar, Buscar;
    private Connection conn;
    private JFrame frame;
    
    public CobrarCredito(double sumaprecio){
        this.sumaprecio = sumaprecio;
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Cobrar Credito");
        setSize(600, 600);
        this.frame = this;
        
        initComponents();
        cargarClientes();
        
        setVisible(true);
    }
    
    private void cargarClientes(){
        ConexionMySQL con = new ConexionMySQL();
        conn = con.Conectar();
        
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM cliente;");
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                String[] fila = {rs.getString("idCliente"), rs.getString("Nombre"), rs.getString("Adeudo_Generado"), rs.getString("Fecha_Visita")};
                
                modelo.addRow(fila);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CobrarCredito.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void initComponents(){
        setLayout(new BorderLayout());
        
        add(NorteBuscar(), BorderLayout.NORTH);
        add(CentroTabla(), BorderLayout.CENTER);
        add(DerechaBotones(), BorderLayout.EAST);
    }
    
    private void buscarEvento(){
        try {
            Statement st = conn.createStatement();
            
            ResultSet rs = st.executeQuery("SELECT * FROM cliente WHERE Nombre LIKE '%" + txtBuscar.getText().toString() + "%';");
            
            borrarTabla();
                
            while(rs.next()){
                String[] fila = {rs.getString("idCliente"), rs.getString("Nombre"), rs.getString("Adeudo_Generado"), rs.getString("Fecha_Visita")};
                
                modelo.addRow(fila);
                    
            }
          
            if(tabla.getRowCount() == 0){
                cargarClientes();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CobrarCredito.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private JPanel NorteBuscar(){
        JPanel panel = new JPanel();
        
        lblBuscar = new JLabel("Buscar cliente: ");
        txtBuscar = new JTextField(20);
        Buscar = new JButton("Buscar Cliente");
        Buscar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarEvento();
            }
            
        });
        
        panel.add(lblBuscar);
        panel.add(txtBuscar);
        panel.add(Buscar);
        
        return panel;
    }
    private JPanel CentroTabla(){
        JPanel panel = new JPanel();
        
        modelo.addColumn("ID_Cliente");
        modelo.addColumn("Nombre");
        modelo.addColumn("Deuda");
        modelo.addColumn("Ult. Visita");
        tabla.setModel(modelo);  
        JScrollPane scroll = new JScrollPane(tabla);
        
        panel.add(scroll);
        
        return panel;
    }
    private JPanel DerechaBotones(){
        JPanel panel = new JPanel();
        
        Aumento = new JButton("Credito");
        Aumento.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarCredito();
            }
        });

        Cancelar = new JButton("Cancelar");
        Cancelar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
            
        });
        
        panel.setLayout(new GridLayout(10,1));
        
        panel.add(Aumento);
        panel.add(Cancelar);
        
        return panel;
    }
    private void cargarCredito(){
        if(tabla.getSelectedRow() >= 0){
            int id = Integer.valueOf(String.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 0)));
            
            
            try {
                PreparedStatement ps = conn.prepareStatement("SELECT Adeudo_Generado debe FROM cliente WHERE idCliente = ?;");
                ps.setInt(1, id);
                
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    Double debe = Double.valueOf(rs.getString("debe")) + sumaprecio;

                    ps = conn.prepareStatement("UPDATE cliente SET Adeudo_Generado = ? WHERE idCliente = ?;");
                    
                    ps.setString(1, String.valueOf(debe));
                    ps.setInt(2, id);
                    
                    
                    ps.execute();
                    actualizarTabla();
                    
                    JOptionPane.showMessageDialog(this, "Cobro realizado!!");
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(CobrarCredito.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Ningun Cliente seleccionado!!");
        }
    }
    
    private void actualizarTabla(){
        
        borrarTabla();
        
        cargarClientes();
        
    }
    
    private void borrarTabla(){
        int filas = tabla.getRowCount();
        
        for(int i = 0; i < filas; i++){
            modelo.removeRow(0);
        }
    }
}
