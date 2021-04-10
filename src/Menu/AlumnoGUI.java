package Menu;

import Models.*;
import Save.Serializar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.io.FileUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Derek
 */
public class AlumnoGUI {
    
    Alumnos alumnoActual;
    String codigoAlumno;
    Cursos[] cursosAsignados;
    Serializar guardar = new Serializar();
    DecimalFormat f = new DecimalFormat("##.00");
    
    Profesores[] prof = new Profesores[300];
    Cursos[] curso = new Cursos[300];
    Alumnos[] alumno = new Alumnos[300];
    Actividades[] actividadesAl = new Actividades[200];
    int contCursosAl;
    int contProf;
    int contCursos;
    int contAlumnos;
    int contActividades;
    
    
    JFrame frameBase;
    JButton[] buttonArray = new JButton[50];
    int contButton;
    JLabel titulo,info;
    JPanel panel;
    JLabel user;
    
    JButton actualizarButton,logoutButton;
    JScrollPane scrollPane;
    
    ActionListener listener;
    Cursos cursoSeleccionado;
    
    JDialog datosAlWdw;
    JPanel datosAlPane;
    JLabel datosAlTituloLbl, datosAlNombreLbl, datosAlApellidoLbl, datosAlCorreoLbl, datosAlPassLbl, datosAlGeneroLbl, datosAlImagen, datosAlFotoLbl;
    JTextField datosAlTituloTxt, datosAlNombreTxt, datosAlApellidoTxt, datosAlCorreoTxt, datosAlPassTxt, datosAlGeneroTxt;
    JButton datosAlEliminarBtn, cambiarFotoBtn, actualizarBtn;
    JComboBox generoCb;
    
    JDialog cursoWindow;
    JPanel panelCursos, panelGraf;
    JLabel tituloCurso, tituloAct;
    JTable tablaActividades;
    JScrollPane scrollTabla;
    JTextField ponderacionTxt, notaTxt;
    
    JFreeChart chartAct;
    DefaultCategoryDataset datasetAct;
    ChartPanel panelChartAct;
    

    public void AlumnoGUI(String codigo){
        this.codigoAlumno = codigo;
        deserializar();
        
        alumnoActual = getAlumno(codigo);
        cursosAsignados = alumnoActual.getCursos();
        contCursosAl = alumnoActual.getContador();
        
                
        scrollPane = new JScrollPane();
        frameBase = new JFrame();
        titulo = new JLabel();
        panel = new JPanel();
        actualizarButton = new JButton();
        logoutButton = new JButton();
        info = new JLabel();
        user = new JLabel();
        
        GridLayout panelLayout = new GridLayout(0,2,20,20);
        panel.setLayout(panelLayout);
        frameBase.add(panel);
        panel.setBounds(20, 70, 650, 410);
        
        user.setText("Usuario: " + alumnoActual.getNombre() + " " + alumnoActual.getApellido());
        frameBase.add(user);
        user.setBounds(450, 3, 200, 20);
        
        logoutButton.setText("Cerrar Sesion");
        frameBase.add(logoutButton);
        logoutButton.setBounds(570, 27, 100, 30);
        logoutButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                LoginGUI regresar = new LoginGUI();
                frameBase.dispatchEvent(new WindowEvent(frameBase, WindowEvent.WINDOW_CLOSING));
            }
        });
        
        actualizarButton.setText("Actualizar Datos");
        frameBase.add(actualizarButton);
        actualizarButton.setBounds(445, 27, 115, 30);
        actualizarButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    datosAlumnosGUI();
                } catch (IOException ex) {
                    Logger.getLogger(AlumnoGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        titulo.setText("Cursos Asignados");
        titulo.setFont(new java.awt.Font("Arial", 1, 24));
        frameBase.add(titulo);
        titulo.setBounds(20, 10, 260, 40);
        
        listener = new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JButton) {
                    String text = ((JButton) e.getSource()).getText();
                    cursoGUI(text);
                }
            }
        };
        
        crearBotones(alumnoActual.getContador());
        
        frameBase.setTitle("Modulo Estudiante");
        frameBase.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frameBase.setLayout(null);
        frameBase.setResizable(false);
        frameBase.setSize(695, 525);
        frameBase.setVisible(true);
        frameBase.setLocationRelativeTo(null);
        
    }
    
    public Alumnos getAlumno(String codigo){
       int indexAlumno = 0;
       for (int i = 0; i < contAlumnos; i++) {
           if (alumno[i].getCodigo().equals(codigo)) {
               indexAlumno = i;
               i=1000;
           }
       }
       return alumno[indexAlumno];
   }
    
    public void crearBotones(int numero){
       for (int i = 0; i < numero; i++) {
           buttonArray[0] = new JButton(cursosAsignados[i].getNombre());
           String profesor = "";
           for (int j = 0; j < contProf; j++) {
               if (cursosAsignados[i].getProfesor().equals(prof[j].getCodigo())) {
                   profesor = prof[j].getNombre() + " " + prof[j].getApellido();
                   j = 10000;
                }
           }
           
           buttonArray[0].setToolTipText("Catedratico: " + profesor);
           panel.add(buttonArray[0]);
           buttonArray[0].addActionListener(listener);
       }
   }
    
    public void datosAlumnosGUI() throws IOException{
       
        datosAlWdw = new JDialog();
        datosAlPane = new JPanel();
        
        datosAlTituloLbl = new JLabel();
        datosAlNombreLbl = new JLabel();
        datosAlApellidoLbl = new JLabel();
        datosAlCorreoLbl = new JLabel();
        datosAlPassLbl = new JLabel();
        datosAlGeneroLbl = new JLabel();
        datosAlImagen = new JLabel();

        datosAlFotoLbl = new JLabel();
        
        datosAlNombreTxt = new JTextField();
        datosAlApellidoTxt = new JTextField();
        datosAlCorreoTxt = new JTextField();
        datosAlPassTxt = new JTextField();
        datosAlGeneroTxt = new JTextField();

        datosAlEliminarBtn = new JButton();
        actualizarBtn = new JButton();
        cambiarFotoBtn = new JButton();
        
        generoCb = new JComboBox();
        
        datosAlPane.setLayout(null);
        datosAlPane.setBounds(10, 10, 450, 616);
        datosAlPane.setBorder(new javax.swing.border.LineBorder(Color.BLACK, 1, true));
        
        datosAlTituloLbl.setFont(new java.awt.Font("Dialog", 1, 20));
        datosAlTituloLbl.setText("Mas Información");
        datosAlPane.add(datosAlTituloLbl);
        datosAlTituloLbl.setBounds(20, 20, 250, 40);
        
        datosAlImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        datosAlImagen.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        datosAlPane.add(datosAlImagen);
        datosAlImagen.setBounds(130, 80, 200, 180);
        datosAlImagen.setIcon(getImage(alumnoActual.getCodigo()));
        
        cambiarFotoBtn.setText("Cambiar Foto");
        cambiarFotoBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        datosAlPane.add(cambiarFotoBtn);
        cambiarFotoBtn.setBounds(130, 260, 200, 30);
        cambiarFotoBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                datosAlImagen.setIcon(cambiarImage());
            }
        });
        
        datosAlNombreTxt.setText(alumnoActual.getNombre());
        datosAlPane.add(datosAlNombreTxt);
        datosAlNombreTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        datosAlNombreTxt.setEditable(false);
        datosAlNombreTxt.setBounds(130, 320, 280, 24);

        datosAlApellidoTxt.setText(alumnoActual.getApellido());
//        datosAlApellidoTxt.setEditable(false);
        datosAlPane.add(datosAlApellidoTxt);
        datosAlApellidoTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        datosAlApellidoTxt.setBounds(130, 370, 280, 24);
        
        datosAlCorreoTxt.setText(alumnoActual.getCorreo());
        datosAlCorreoTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        datosAlCorreoTxt.setEditable(false);
        datosAlPane.add(datosAlCorreoTxt);
        datosAlCorreoTxt.setBounds(130, 420, 280, 24);

        datosAlPassTxt.setText(alumnoActual.getContraseña());
        datosAlPassTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        datosAlPassTxt.setEditable(false);
        datosAlPane.add(datosAlPassTxt);
        datosAlPassTxt.setBounds(130, 470, 280, 24);
        
        generoCb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Femenino", "Masculino" }));
        generoCb.setSelectedItem(alumnoActual.getGenero());
        datosAlPane.add(generoCb);
        generoCb.setBounds(130, 520, 200, 24);

        datosAlNombreLbl.setText("Nombre");
        datosAlPane.add(datosAlNombreLbl);
        datosAlNombreLbl.setBounds(40, 320, 80, 20);
        
        datosAlGeneroLbl.setText("Genero");
        datosAlPane.add(datosAlGeneroLbl);
        datosAlGeneroLbl.setBounds(40, 520, 80, 20);

        datosAlFotoLbl.setText("Fotografia");
        datosAlPane.add(datosAlFotoLbl);
        datosAlFotoLbl.setBounds(40, 80, 70, 20);

        datosAlApellidoLbl.setText("Apellido");
        datosAlPane.add(datosAlApellidoLbl);
        datosAlApellidoLbl.setBounds(40, 370, 80, 20);

        datosAlCorreoLbl.setText("Correo");
        datosAlPane.add(datosAlCorreoLbl);
        datosAlCorreoLbl.setBounds(40, 420, 80, 20);

        datosAlPassLbl.setText("Contraseña");
        datosAlPane.add(datosAlPassLbl);
        datosAlPassLbl.setBounds(40, 470, 80, 20);

        actualizarBtn.setText("Actualizar");
        actualizarBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        datosAlPane.add(actualizarBtn);
        actualizarBtn.setBounds(60, 570, 340, 32);
        actualizarBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                alumnoActual.setNombre(datosAlNombreTxt.getText());
                alumnoActual.setApellido(datosAlApellidoTxt.getText());
                alumnoActual.setContraseña(datosAlPassTxt.getText());
                alumnoActual.setCorreo(datosAlCorreoTxt.getText());
                alumnoActual.setGenero(generoCb.getSelectedItem().toString());
                user.setText("Usuario: " + alumnoActual.getNombre() + " " + alumnoActual.getApellido());
                guardar.Serializar(prof, curso, alumno);
                datosAlWdw.dispatchEvent(new WindowEvent(datosAlWdw, WindowEvent.WINDOW_CLOSING));
            }
        });
        
        datosAlWdw.add(datosAlPane);
        datosAlWdw.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        datosAlWdw.setLayout(null);
        datosAlWdw.setResizable(false);
        datosAlWdw.setSize(480, 670);
        datosAlWdw.setVisible(true);
        datosAlWdw.setLocationRelativeTo(null);
        
   }
    
    public ImageIcon getImage(String codigo) throws IOException{

        String imageName = codigo;
        String imagePath = "images\\"+imageName+".png";
        File image = new File(imagePath);
        
        if (image.isFile() != true) {
            imagePath = "images\\placeholder.png";
        }
        
       BufferedImage myPicture = ImageIO.read(new File(imagePath));
       
       Image dim = myPicture.getScaledInstance(200, 180,
       Image.SCALE_SMOOTH);
       
       ImageIcon imageIcon = new ImageIcon(dim);
       
       return imageIcon;
   }
    
    public ImageIcon cambiarImage(){
        JFileChooser fc = new JFileChooser();
        ImageIcon imageIcon = null;
        int op = fc.showOpenDialog(frameBase);
        
        if(op == JFileChooser.APPROVE_OPTION){
            
            String imageName = alumnoActual.getCodigo();
            String imagePath = "images\\"+imageName+".png";
            File imageOld = new File(imagePath);
            if (imageOld.isFile() != true) {
                try {
                    Files.deleteIfExists(Paths.get(imagePath));
                } catch (IOException ex) {
                    Logger.getLogger(AlumnoGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            File image = fc.getSelectedFile();
            File imageNew = new File(imagePath);
            
            try {
                FileUtils.copyFile(image, imageNew);
            } catch (IOException ex) {
                Logger.getLogger(AlumnoGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                BufferedImage myPicture = ImageIO.read(imageNew);
                Image dim = myPicture.getScaledInstance(200, 180, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(dim);
                
            } catch (IOException ex) {
                Logger.getLogger(AlumnoGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return imageIcon;
    }
    
    public Cursos getCurso(String nombreCurso){
       int indexCurso = 0;
       
       for (int i = 0; i < contCursosAl; i++) {
           if (cursosAsignados[i].getNombre().equals(nombreCurso)) {
               indexCurso = i;
           }
       }
       return cursosAsignados[indexCurso];
   }
    
    public double getNotaObtenida(){
        double nota = 0;
        for (int i = 0; i < contActividades; i++) {
            nota += actividadesAl[i].getPonderacion()*getNota(i)/100;
        }
        return nota;
    }
    
    public double getPonderacion(){
        double pon = 0;
        for (int i = 0; i < contActividades; i++) {
            pon += actividadesAl[i].getPonderacion();
        }
        return pon;
    }
    
    public void cursoGUI(String nombreCurso){
        cursoSeleccionado = getCurso(nombreCurso);
        actividadesAl = cursoSeleccionado.getActividades();
        contActividades = cursoSeleccionado.getNoActividades();
       
        cursoWindow = new JDialog();
        panelCursos = new JPanel();
        tituloCurso = new JLabel();
        tituloAct = new JLabel();
        tablaActividades = new JTable();
        scrollTabla = new JScrollPane();
        panelGraf = new JPanel(new BorderLayout());
        ponderacionTxt = new JTextField();
        notaTxt = new JTextField();
        
        
        panelCursos.setLayout(null);
        panelCursos.setBounds(10,10,700,700);
       

        tituloCurso.setFont(new java.awt.Font("Dialog", 1, 24)); 
        tituloCurso.setText(cursoSeleccionado.getNombre());
        panelCursos.add(tituloCurso);
        tituloCurso.setBounds(20, 10, 178, 54);
       
        tituloAct.setFont(new java.awt.Font("Dialog", 1, 14)); 
        tituloAct.setText("Actividades");
        panelCursos.add(tituloAct);
        tituloAct.setBounds(30, 70, 100, 30);
        
        tablaActividades = new JTable(new DefaultTableModel(new Object [] {"Nombre", "Descripcion", "Ponderacion", "Nota Obtenida"},0));
        DefaultTableModel modelActividades = (DefaultTableModel) tablaActividades.getModel();
        tablaActividades.setDefaultEditor(Object.class, null);
        
        for (int i = 0; i < contActividades; i++) {
            Object row[] = {actividadesAl[i].getNombre(), actividadesAl[i].getDescripcion(), actividadesAl[i].getPonderacion(),f.format(getNota(i))};
            ((DefaultTableModel)tablaActividades.getModel()).addRow(row);
//            System.out.println(actividadesAl[i].getNombre());
        }

        scrollTabla.setViewportView(tablaActividades);
        panelCursos.add(scrollTabla);
        scrollTabla.setBorder(BorderFactory.createLineBorder(Color.black));
        scrollTabla.setBounds(30, 100, 640, 130);
        
        ponderacionTxt.setText(f.format(getPonderacion()));
        panelCursos.add(ponderacionTxt);
        ponderacionTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ponderacionTxt.setEditable(false);
        ponderacionTxt.setBounds(350, 230, 160, 24);
        
        notaTxt.setText(f.format(getNotaObtenida()));
        panelCursos.add(notaTxt);
        notaTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        notaTxt.setEditable(false);
        notaTxt.setBounds(510, 230, 160, 24);
        
        panelGraf.setBorder(BorderFactory.createLineBorder(Color.black));
        panelGraf.setBounds(200, 300, 300, 230);
        panelCursos.add(panelGraf);
        newChartActividades();
        
        cursoWindow.setResizable(false);
        cursoWindow.add(panelCursos);
        cursoWindow.setSize(710, 600);
        cursoWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cursoWindow.setVisible(true);
        cursoWindow.setLocationRelativeTo(null);
    
   }
    
    public double getNota(int index){
        String[][] notas = actividadesAl[index].getNotas();
        int cont = actividadesAl[index].getContador();
        double nota = 0;
        for (int i = 0; i < cont; i++) {
            if (notas[i][0].equals(alumnoActual.getCodigo())) {
                nota = Double.parseDouble(notas[i][1]);
                i = 1000;
            }
        }
        return nota;
    }
    
    public void newChartActividades(){

        chartAct = ChartFactory.createBarChart("Nota Actividades", null, "Punteo", datasetAct(), PlotOrientation.VERTICAL, true, true, false);
        
        panelChartAct = new ChartPanel(chartAct);
        panelGraf.add(panelChartAct);
    }
    
    public DefaultCategoryDataset datasetAct(){
        DefaultCategoryDataset datasetAct = new DefaultCategoryDataset();

        for (int i = 0; i < contActividades; i++) {
            String[][] notas = actividadesAl[i].getNotas();
            int cont = actividadesAl[i].getContador();
            double nota = 0;
            
            for (int j = 0; j < cont; j++) {
                if (notas[j][0].equals(alumnoActual.getCodigo())) {
                    nota = Double.parseDouble(notas[j][1]);
                    j = 1000;
                }
            }
            datasetAct.addValue(nota, actividadesAl[i].getNombre(), "Cursos");
        }
        
        
        return datasetAct;
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

