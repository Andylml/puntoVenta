
import ConectorBD.ConexionMySQL;
import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.awt.Panel.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author DARK_
 */
public class VentanaLogin extends JFrame
{       
        private JTextField txtUser, txtPass;
        private JLabel lblUser, lblPass, lblimagen;
        private JButton btnAceptar, btnCancelar;
        String usuario, elPassword;
        
        
    VentanaLogin()
    {
        setLayout(new BorderLayout());
        
        JPanel imagen = new JPanel();
        
        imagen.setMinimumSize(new Dimension(200,200));
        
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BorderLayout());
        
        JPanel textboxs = new JPanel();
        
        textboxs.setLayout(new GridLayout(2,1));
        
        JPanel user = new JPanel();
        JPanel passw = new JPanel();
        
        JPanel botones = new JPanel();
        
        // crear etiqueta y cuadro de texxto del usuario
        txtUser = new JTextField(10);
        lblUser = new JLabel("Usuario: ");
        ImageIcon icon = new ImageIcon("‪C:\\Users\\andre\\Desktop\\ProyectoBVallarta\\BirrieriaITE.png");
        Icon ic = new ImageIcon(icon.getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
        lblimagen = new JLabel(ic);
        txtUser.setToolTipText("Escriba su nombre de usuario");        
        user.add( Box.createVerticalStrut(50) );
        user.add(lblUser);
        user.add(txtUser);
        
        textboxs.add(user);
        
        imagen.add(lblimagen);
       
        //crear etiqueta y cuadro de texxto del pw
        txtPass = new JPasswordField(10);
        lblPass = new JLabel("Contraseña: ");
        txtPass.setToolTipText("Escriba su contraseña");
        passw.add(lblPass);
        passw.add(txtPass);
        
        textboxs.add(passw);
        
        //Crear y agregar los botones 
        btnAceptar = new JButton("Aceptar");
        //establecer Boton aceptar por defecto
        getRootPane().setDefaultButton(btnAceptar);
 
        btnCancelar = new JButton("Cancelar");
        botones.add(btnAceptar);
        botones.add(btnCancelar);
 
        contenedor.add(textboxs, BorderLayout.CENTER);
        contenedor.add(botones, BorderLayout.SOUTH);
 
      // Crear un escuchador al boton Aceptar 
        ActionListener escuchadorbtnAceptar = new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                try
                {                    
                    //*chekar si el usuario escrbio el nombre de usuario y pw
                    if (txtUser.getText().length() > 0 && txtPass.getText().length() > 0 )
                    {
                        // Si el usuario si fue validado correctamente
                        if( validarUsuario( txtUser.getText(), txtPass.getText() ) )    //enviar datos a validar
                        {
                            // Codigo para mostrar la ventana principal aqui llamas a la principal*****************
                            Principal c = new Principal();
                            setVisible(false);
                        
 
                        
                        }
                        else
                        {
                            //JOptionPane.showMessageDialog(null, "El nombre de usuario y/o contrasenia no son validos.");
                            //JOptionPane.showMessageDialog(null, txtUser.getText()+" " +txtPass.getText() );
                            txtUser.setText("");    //limpiar campos
                            txtPass.setText("");        
                             
                            txtUser.requestFocusInWindow();
                        }
 
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Debe escribir nombre de usuario y contrasenia.\n" +
                            "NO puede dejar ningun campo vacio");
                    }
 
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                 
            }

       
        };
        btnAceptar.addActionListener(escuchadorbtnAceptar);      // Asociar escuchador para el boton Aceptar
 
 
      // Agregar escuchador al dboton Cancelar
        ActionListener escuchadorbtnCancelar=new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                System.exit(0);         // terminar el programa
            }
        };
        btnCancelar.addActionListener(escuchadorbtnCancelar);      // Asociar escuchador para el boton Cancelar
        setTitle("Autentificacion de usuarios");
        setSize(300,400);           // Tamanio del Frame 
        setResizable(false);       // que no se le pueda cambiar el tamanio 
        
        add(imagen, BorderLayout.CENTER);
        add(contenedor, BorderLayout.SOUTH);
        
        //Centrar la ventana de autentificacion en la pantalla
        Dimension tamFrame=this.getSize();//para obtener las dimensiones del frame
        Dimension tamPantalla=Toolkit.getDefaultToolkit().getScreenSize();      //para obtener el tamanio de la pantalla
        setLocation((tamPantalla.width-tamFrame.width)/2, (tamPantalla.height-tamFrame.height)/2);  //para posicionar
        setVisible(true);           // Hacer visible al frame 
 
    }   // fin de constructor

    // Metodo que conecta con el servidor para autentificar usuarios
    Boolean validarUsuario(String usuario, String elPassword)  
    {
        usuario = txtUser.getText();
        elPassword = txtPass.getText();
        
        PreparedStatement ps;
            try {
                ps = cn.prepareStatement("SELECT * FROM usuario WHERE username = ?;");
                
                ps.setString(1, usuario);
                
                ResultSet rs = ps.executeQuery();
                 
                while(rs.next()){
                    int intentos = rs.getInt("intentos");
                    if(intentos < 3){
                        if(rs.getString("password").equals(elPassword)){
                            return true;
                        }else{
                            int id = rs.getInt("Id");
                            PreparedStatement pU = cn.prepareStatement("UPDATE usuario SET intentos = intentos + 1, fecha = NOW() WHERE Id = ?;");

                            pU.setInt(1, id);

                            pU.execute();
                     
                            JOptionPane.showMessageDialog(this, "Contraseña Incorrecta!!");
                        }
                    }else{
                        PreparedStatement pF = cn.prepareStatement("SELECT TIMESTAMPDIFF(MINUTE, (SELECT fecha FROM usuario WHERE Id=?), NOW()) tiempo;");
                        
                        pF.setInt(1, rs.getInt("Id"));
                        
                        ResultSet rF = pF.executeQuery();
                        
                        while(rF.next()){
                            int minutos = rF.getInt("tiempo");
                            
                            //Aqui definimos el tiempo que la cuenta se encontrada bloqueada
                            if(minutos > 15){
                                PreparedStatement pD = cn.prepareStatement("UPDATE usuario SET intentos = 0 WHERE Id = ?;");
                                
                                pD.setInt(1, rs.getInt("Id"));
                                
                                pD.execute();
                            }else{
                                JOptionPane.showMessageDialog(this, "Su cuenta esta bloquiada por 15 minuto!!");
                            }
                        }
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(VentanaLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        
       
        
        
        return true;
    }
     
    
     
    
    public static void main(String[] args)
    {
                VentanaLogin prueba = new VentanaLogin();
                prueba.setDefaultCloseOperation(prueba.EXIT_ON_CLOSE);
                //Principal m = new Principal();
    }   
        
         ConexionMySQL mysql =new ConexionMySQL();
        Connection cn = mysql.Conectar();

}
