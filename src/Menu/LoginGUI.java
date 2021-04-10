package Menu;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import Models.*;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;


/**
 *
 * @author Derek
 */
public class LoginGUI {
    Profesores[] prof = new Profesores[50];
    Cursos[] curso = new Cursos[300];
    Alumnos[] alumno = new Alumnos[300];
    int contProf;
    int contCursos;
    int contAlumnos;
    
    JFrame frame;
    JLabel labelCodigo,titulo,labelPass,test;
    JPanel panel;
    JTextField textFieldCodigo,textFieldPass;
    JButton loginButton;
    JPasswordField passwordField;
    JCheckBox passCheckBox;
    
    String contraseña;
    String codigo;
    
    public LoginGUI(){  
        deserializar();
        
        frame = new JFrame("Login Menu");
        
        panel = new JPanel();
        panel.setLayout(null);
//        panel.setBackground(new Color(115, 147, 179));
        
        titulo = new JLabel("DTT");
        titulo.setFont(new java.awt.Font("Arial", Font.BOLD, 36));
        panel.add(titulo);
        titulo.setBounds(160, 20, 80, 70);
      
        //Codigo del usuario
        labelCodigo = new JLabel("Codigo: ");
        labelCodigo.setFont(new java.awt.Font("Arial", Font.BOLD,14));
        panel.add(labelCodigo);
        labelCodigo.setBounds(80, 100, 70, 20);
        
        textFieldCodigo = new JTextField("");
        panel.add(textFieldCodigo);
        textFieldCodigo.setBounds(140, 100, 190, 24);
        
        //Contraseña del usuario
        labelPass = new JLabel("Contraseña: ");
        labelPass.setFont(new java.awt.Font("Arial", Font.BOLD,14));
        panel.add(labelPass);
        labelPass.setBounds(50, 145, 90, 16);
                
        passwordField = new JPasswordField("");
        panel.add(passwordField);
        passwordField.setBounds(140, 140, 190, 24);
        char pass = passwordField.getEchoChar();
        
        //Checkbox para mostrar/ocultar la contraseña
        passCheckBox = new JCheckBox("Mostrar Contraseña");
        panel.add(passCheckBox);
        passCheckBox.setBounds(140, 170, 190, 24);
        passCheckBox.addItemListener(new ItemListener(){
             public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    passwordField.setEchoChar((char) 0);
                } else {
                    
                    passwordField.setEchoChar(pass);
                }   
            }
        });
        
        test = new JLabel();
        labelCodigo.setFont(new java.awt.Font("Arial", Font.BOLD,14));
        panel.add(test);
        test.setBounds(80, 270, 150, 20);
        
        //Boton de inicio de sesion
        loginButton = new JButton("Iniciar Sesion");
        loginButton.setFont(new java.awt.Font("Arial", Font.PLAIN,14));
//        loginButton.setToolTipText("Iniciar sesion en la plataforma");
        panel.add(loginButton);
        loginButton.setBounds(83, 210, 250, 40);
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contraseña = passwordField.getText();
                codigo = textFieldCodigo.getText();
                login();
            }
        });
        
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.add(panel);
        frame.setSize(410,350);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        
    } 
    
    private void login(){
       
        String user = "";
        String password = "";
        String type = null;
        AdminGUI adminGUI = new AdminGUI();
        ProfesorGUI profGUI = new ProfesorGUI();
        AlumnoGUI alumGUI = new AlumnoGUI();
        boolean existe = false;
        boolean passCorrecta = false;
        
        for (int i = 0; i < contProf; i++) {
            if (this.codigo.equals(prof[i].getCodigo())) {
                type = "prof";
                existe = true;
                if (this.contraseña.equals(prof[i].getContraseña())) {
                    user = prof[i].getNombre();
                    i = 1000;
                    passCorrecta = true;
                }
            }
        }
        
        for (int i = 0; i < contAlumnos; i++) {
            if (this.codigo.equals(alumno[i].getCodigo())) {
                existe = true;
                type = "alumno";
                if (this.contraseña.equals(alumno[i].getContraseña())) {
                    user = alumno[i].getNombre();
                    passCorrecta = true;
                    i = 1000;
                }
            }
        }
        
        if(this.codigo.equals("admin")){
            type = "admin";
            existe = true;
            if (this.contraseña.equals("admin")) {
                passCorrecta = true;
            }
        }

        if(existe == true && passCorrecta == true){
            switch(type){
                case "prof":
//                    test.setText("Maestro: " + user + " " + contraseña);
                    profGUI.ProfesorGUI(this.codigo);
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    break;
                case "alumno":
//                    test.setText("Alumno: " + user + " " + contraseña);
                     alumGUI.AlumnoGUI(this.codigo);
                     frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    break;
                case "admin":
//                    test.setText("Admin: " + codigo + " " + contraseña);
                    adminGUI.AdminGUI();
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    break;
                default:
            }
        }else if(existe == true && passCorrecta != true){
            JOptionPane.showMessageDialog(frame,
                "Contraseña Incorrecta",
                "Error",
                JOptionPane.INFORMATION_MESSAGE);
        }else if(existe != true){
            JOptionPane.showMessageDialog(frame,
                "El usuario no existe",
                "Error",
                JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    public void deserializar(){
        try {
            ObjectInputStream profLoad = new ObjectInputStream(new FileInputStream("Saves\\profesores.ipc"));
            this.prof = (Profesores[]) profLoad.readObject();
            for (Profesores prof1 : this.prof) {
                if (prof1 == null) {break;}
                contProf++;
            }
            
            ObjectInputStream cursoLoad = new ObjectInputStream(new FileInputStream("Saves\\cursos.ipc"));
            this.curso = (Cursos[]) cursoLoad.readObject();
            for (Cursos curso1 : this.curso) {
                if (curso1  == null) {break;}
                contCursos++;
            }
            
            ObjectInputStream alumnoLoad = new ObjectInputStream(new FileInputStream("Saves\\alumnos.ipc"));
            this.alumno = (Alumnos[]) alumnoLoad.readObject();
            for (Alumnos alumno1 : this.alumno) {
                if (alumno1 == null) {break;}
                contAlumnos++;
            }
            
        } catch (IOException ex) {
//            Logger.getLogger(AdminGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(AdminGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
