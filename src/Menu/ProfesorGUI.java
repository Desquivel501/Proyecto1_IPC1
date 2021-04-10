
package Menu;

import Models.*;
import Save.Serializar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Derek
 */
public class ProfesorGUI {

    Profesores profActual;
    Cursos[] cursosAsignados;
    Alumnos[] alumnosAsignados;
    
    Profesores[] prof = new Profesores[300];
    Cursos[] curso = new Cursos[300];
    Alumnos[] alumno = new Alumnos[300];
    String[][] notasTemp = new String[300][2];
    Actividades[] actividadCurso = new Actividades[300];
    int indexCurso;
    ;
    
    int contProf;
    int contCursos;
    int contAlumnos;
    int contActividades;
    String codigoProf;
    
    JFrame frameBase;
    JButton[] buttonArray = new JButton[50];
    int contButton;
    JLabel titulo;
    JPanel panel;
    JButton actualizarButton,logoutButton;
    JScrollPane scrollPane;
    
    ActionListener listener;
    Cursos cursoSeleccionado;
    
    JDialog cursoWindow;
    JPanel panelCursos;
    JButton top5Btn, down5Btn, cargaAlumnosBtn, cargaNotasBtn, crearActividadBtn;
    JLabel tituloCurso, tituloAlumnos,tituloActividades,crearActividadlLbl, acumuladoLbl, nombreLbl, descripcionLbl, ponderacionLbl, notasLbl, reportesLbl, info;
    JTable tablaAlumnos, tablaActividades;
    JScrollPane scrollAlumnos, scrollActividades;
    JTextField nombreTxt, descripcionTxt, ponderacionTxt;
    
    int contAsignados;
    int contCursosProf;
    
    Serializar guardar = new Serializar();
    DecimalFormat f = new DecimalFormat("##.00");
    DecimalFormat f2 = new DecimalFormat("##");
    
    JDialog actualizarWdw;
    JPanel actPane;
    JLabel actTituloLbl, actNombreLbl, actApellidoLbl, actCorreoLbl, actPassLbl, actGeneroLbl;
    JTextField actTituloTxt, actNombreTxt, actApellidoTxt, actCorreoTxt, actPassTxt;
    JComboBox actGeneroCombo;
    JButton actBtn;
    
    JDialog datosAlWdw;
    JPanel datosAlPane;
    JLabel datosAlTituloLbl, datosAlNombreLbl, datosAlApellidoLbl, datosAlCorreoLbl, datosAlPassLbl, datosAlGeneroLbl, datosAlImagen, datosAlFotoLbl;
    JTextField datosAlTituloTxt, datosAlNombreTxt, datosAlApellidoTxt, datosAlCorreoTxt, datosAlPassTxt, datosAlGeneroTxt;
    JButton datosAlEliminarBtn, datosAlBackBtn;
    
    DefaultTableModel modelAlumnos;
    JLabel user;
    
    
   public void ProfesorGUI(String codigo){
        
        this.codigoProf = codigo;
        deserializar();
        
        profActual = getProf(codigo);
        cursosAsignados = profActual.getCursos();
        contCursosProf = profActual.getContador();
        indexCurso = 0;
                
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
        
        
        
        user.setText("Usuario: " + profActual.getNombre() + " " + profActual.getApellido());
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
                actualizarProf();
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
//                    JOptionPane.showMessageDialog(null, text);
                    cursoGUI(text);
                }
            }
        };
        
        crearBotones(profActual.getContador());
        
        frameBase.setTitle("Modulo Profesores");
        frameBase.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frameBase.setLayout(null);
        frameBase.setResizable(false);
        frameBase.setSize(695, 525);
        frameBase.setVisible(true);
        frameBase.setLocationRelativeTo(null);
        
    }
   
   public void crearBotones(int numero){
       for (int i = 0; i < numero; i++) {
           buttonArray[0] = new JButton(cursosAsignados[i].getNombre());
           buttonArray[0].setToolTipText("Estudiantes: " + Integer.toString(cursosAsignados[i].getAlumnos()));
           panel.add(buttonArray[0]);
           buttonArray[0].addActionListener(listener);
       }
   }
   
   public void cursoGUI(String nombreCurso){
        
        cursoSeleccionado = getCurso(nombreCurso);
        
        alumnosAsignados = cursoSeleccionado.getAlumnosAsignados();
        contAsignados = cursoSeleccionado.getContador();
        actividadCurso = cursoSeleccionado.getActividades();
        contActividades = cursoSeleccionado.getNoActividades();
        
        int acumulado = getAcumulado();
        
        cursoWindow = new JDialog();
        panelCursos = new JPanel();
        
        top5Btn = new JButton();
        down5Btn = new JButton();
        cargaAlumnosBtn = new JButton();
        cargaNotasBtn = new JButton();
        crearActividadBtn = new JButton();
        
        tituloCurso = new JLabel();
        tituloAlumnos = new JLabel();
        tituloActividades = new JLabel();
        crearActividadlLbl = new JLabel();
        acumuladoLbl = new JLabel();
        nombreLbl = new JLabel();
        descripcionLbl = new JLabel();
        ponderacionLbl = new JLabel();
        notasLbl = new JLabel();
        reportesLbl = new JLabel();
        
        nombreTxt = new JTextField();
        ponderacionTxt = new JTextField();
        descripcionTxt = new JTextField();
        
        scrollAlumnos = new JScrollPane();
        scrollActividades = new JScrollPane();
        
        panelCursos.setLayout(null);
        panelCursos.setBounds(10, 10, 700, 700);

        tituloCurso.setFont(new java.awt.Font("Dialog", 1, 24)); 
        tituloCurso.setText(cursoSeleccionado.getNombre());
        panelCursos.add(tituloCurso);
        tituloCurso.setBounds(20, 10, 400, 50);

        tituloActividades.setText("Actividades");
        tituloActividades.setFont(new java.awt.Font("Dialog", 1, 13)); 
        panelCursos.add(tituloActividades);
        tituloActividades.setBounds(365, 60, 190, 20);
        
        tituloAlumnos.setText("Listado Alumnos");
        tituloAlumnos.setFont(new java.awt.Font("Dialog", 1, 13)); 
        panelCursos.add(tituloAlumnos);
        tituloAlumnos.setBounds(30, 60, 190, 20);

        reportesLbl.setText("Reportes");
        reportesLbl.setFont(new java.awt.Font("Dialog", 1, 13)); 
        panelCursos.add(reportesLbl);
        reportesLbl.setBounds(30, 440, 300, 16);

        acumuladoLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        acumuladoLbl.setText("Acumulado: " + acumulado + "/100");
        acumuladoLbl.setFont(new java.awt.Font("Dialog", 1, 13)); 
        panelCursos.add(acumuladoLbl);
        acumuladoLbl.setBounds(380, 270, 250, 16);

        crearActividadlLbl.setText("Crear Actividad");
        crearActividadlLbl.setFont(new java.awt.Font("Dialog", 1, 13)); 
        panelCursos.add(crearActividadlLbl);
        crearActividadlLbl.setBounds(370, 310, 190, 16);

        notasLbl.setText("Notas");
        panelCursos.add(notasLbl);
        notasLbl.setBounds(370, 460, 80, 16);

        nombreLbl.setText("Nombre");
        panelCursos.add(nombreLbl);
        nombreLbl.setBounds(370, 340, 70, 20);

        descripcionLbl.setText("Descripcion");
        panelCursos.add(descripcionLbl);
        descripcionLbl.setBounds(370, 380, 80, 20);

        ponderacionLbl.setText("Ponderacion");
        panelCursos.add(ponderacionLbl);
        ponderacionLbl.setBounds(370, 420, 80, 20);
        
        nombreTxt.setText(""); //Nombre TextField
        panelCursos.add(nombreTxt);
        nombreTxt.setBounds(470, 340, 210, 24);

        descripcionTxt.setText(""); //Descripcion TextField
        panelCursos.add(descripcionTxt);
        descripcionTxt.setBounds(470, 380, 210, 24);

        ponderacionTxt.setText(""); //Ponderacion TextField
        panelCursos.add(ponderacionTxt);
        ponderacionTxt.setBounds(470, 420, 210, 24);

        cargaNotasBtn.setText("Seleccionar Archivo CSV");
        panelCursos.add(cargaNotasBtn);
        cargaNotasBtn.setBounds(470, 460, 210, 26);
        cargaNotasBtn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               BufferedReader br = null;
               try{
                   
                   JFileChooser fc = new JFileChooser();
                    int op = fc.showOpenDialog(frameBase);
                    if(op == JFileChooser.APPROVE_OPTION){
                        
                        int contN = 0;
                        br = new BufferedReader(new FileReader(fc.getSelectedFile()));
                        String linea = br.readLine();
                        linea = br.readLine();
                        
                         while (linea != null) {
                            String[] parametros = linea.split(",");
                            notasTemp[contN][0] = parametros[0];
                            notasTemp[contN][1] = parametros[1];
                            contN++;
                            linea = br.readLine();
                         }
                    }      
                    guardar.Serializar(prof, curso, alumno);
               }catch(Exception e){
                   e.printStackTrace();
               }
            }
        });
            
        crearActividadBtn.setText("Crear Actividad");
        panelCursos.add(crearActividadBtn);
        crearActividadBtn.setBounds(360, 510, 320, 32);
        crearActividadBtn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               String nombreActividad = nombreTxt.getText();
               String descActividad = descripcionTxt.getText();
               int ponActividad = Integer.parseInt(ponderacionTxt.getText());
               
               
               int numNotas = 0;
               double suma = 0;
               while(notasTemp[numNotas][0] != null){
                   suma = suma + Double.parseDouble(notasTemp[numNotas][1]);
                   numNotas++;
               }
               double promedio = suma/numNotas;
                
                
                Actividades actividad = new Actividades(nombreActividad,descActividad,ponActividad,promedio);
                for (int i = 0; i < numNotas; i++) {
                    actividad.setNotas(notasTemp[i][0], notasTemp[i][1]);
                }
                
                cursoSeleccionado.nuevaActividad(actividad);
                
                Object row[] = {actividad.getNombre(), actividad.getDescripcion(), actividad.getPonderacion(),f.format(actividad.getPromedio())};
                ((DefaultTableModel)tablaActividades.getModel()).addRow(row);
                
                
                int newAcumulado = acumulado + actividad.getPonderacion();
                acumuladoLbl.setText("Acumulado: " + newAcumulado + "/100");
                guardar.Serializar(prof, curso, alumno);
                               
            }
        });
        
        cargaAlumnosBtn.setText("Carga Masiva de Alumnos");
        panelCursos.add(cargaAlumnosBtn);
        cargaAlumnosBtn.setBounds(20, 380, 320, 32);
        cargaAlumnosBtn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String contenido = "";
                try{
                    JFileChooser fc = new JFileChooser();
                    int op = fc.showOpenDialog(frameBase);
                    if(op == JFileChooser.APPROVE_OPTION){
//                    System.out.println(fc.getSelectedFile());
                    
                        FileReader fr = new FileReader(fc.getSelectedFile());
                        BufferedReader bf = new BufferedReader(fr);
                        String linea;

                        while ((linea = bf.readLine()) != null) {
                            contenido += linea;
                        }

                        JsonParser parser = new JsonParser();
                        JsonArray array = parser.parse(contenido).getAsJsonArray();

                        for (int i = 0; i < array.size(); i++) {
                            JsonObject obj = array.get(i).getAsJsonObject();
                            String codigo = obj.get("codigo").getAsString();
//                            System.out.println(codigo);
                            int index = 0;

                            for (int j = 0; j < contAlumnos; j++) {
                                if (alumno[j].getCodigo().equals(codigo)) {
                                    index = j;
                                    j=1000;
                                }
                            }
                            
                            cursoSeleccionado.asignarAlumno(alumno[index]);
                            cursosAsignados[indexCurso].setAlumnos(i+1);
                            alumno[index].asignarCurso(cursoSeleccionado);

                            Object row[] = {alumno[index].getCodigo(),alumno[index].getNombre(),alumno[index].getApellido(), "Info"};
                            ((DefaultTableModel)tablaAlumnos.getModel()).addRow(row);

                            alumnosAsignados = cursoSeleccionado.getAlumnosAsignados();
                            
                            contAsignados = cursoSeleccionado.getContador();
                            
                            cursoSeleccionado.setAlumnosStr(Integer.toString(contAsignados));

                        }
                        guardar.Serializar(prof, curso, alumno);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        
        info.setText("Mas Informacion");
        info.setForeground(Color.BLUE);
        
        tablaAlumnos = new JTable(new DefaultTableModel(new Object [] {"Codigo", "Nombre", "Apellido", "Acciones"},0));
        modelAlumnos = (DefaultTableModel) tablaAlumnos.getModel();
        tablaAlumnos.setDefaultEditor(Object.class, null);
        
        
        for (int i = 0; i < contAsignados; i++) {
            Object row[] = {alumnosAsignados[i].getCodigo(),alumnosAsignados[i].getNombre(), alumnosAsignados[i].getApellido(), "Mas Info."};
            ((DefaultTableModel)tablaAlumnos.getModel()).addRow(row);
        }
        
        tablaAlumnos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                  JTable target = (JTable)e.getSource();
                  int row = target.getSelectedRow();
                  int column = target.getSelectedColumn();
                    if (column == 3) {
                      try {
                          String codigoAlumno = tablaAlumnos.getValueAt(tablaAlumnos.getSelectedRow(),0).toString();
                          int indexAlumno=0;
                          for (int i = 0; i < contAsignados; i++) {
                              if (alumnosAsignados[i].getCodigo().equals(codigoAlumno)) {
                                  indexAlumno = i;
                                  i=1000;
                              }
                          }
//                          System.out.println(alumnosAsignados[indexAlumno].getNombre());
                          datosAlumnosGUI(indexAlumno,tablaAlumnos.getSelectedRow());
                      } catch (IOException ex) {
                          Logger.getLogger(ProfesorGUI.class.getName()).log(Level.SEVERE, null, ex);
                      }
                    }
                }
            }
        });
        scrollAlumnos.setViewportView(tablaAlumnos);
        panelCursos.add(scrollAlumnos);
        scrollAlumnos.setBorder(BorderFactory.createLineBorder(Color.black));
        scrollAlumnos.setBounds(20, 90, 320, 280);


        tablaActividades = new JTable(new DefaultTableModel(new Object [] {"Nombre", "Descripcion", "Ponderacion", "Promedio"},0));
        DefaultTableModel modelActividades = (DefaultTableModel) tablaActividades.getModel();
        tablaActividades.setDefaultEditor(Object.class, null);

        
        for (int i = 0; i < contActividades; i++) {
            Object row[] = {actividadCurso[i].getNombre(), actividadCurso[i].getDescripcion(), actividadCurso[i].getPonderacion(),f.format(actividadCurso[i].getPromedio())};
            ((DefaultTableModel)tablaActividades.getModel()).addRow(row);
        }

        scrollActividades.setViewportView(tablaActividades);
        panelCursos.add(scrollActividades);
        scrollActividades.setBorder(BorderFactory.createLineBorder(Color.black));
        scrollActividades.setBounds(360, 90, 320, 170);
        
        
        top5Btn.setText("Top 5 - Estudiantes con Mejor Rendimiento");
        panelCursos.add(top5Btn);
        top5Btn.setBounds(20, 470, 320, 32);
        top5Btn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String[][] arrayTop = getNotasTotales();
                topAlumnos(arrayTop);
            }
        });

        down5Btn.setText("Top 5 - Estudiantes con Peor Rendimiento");
        panelCursos.add(down5Btn);
        down5Btn.setBounds(20, 510, 320, 32);
        down5Btn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    String[][] arrayDown = getNotasTotales();
                    downAlumnos(arrayDown);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ProfesorGUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DocumentException ex) {
                    Logger.getLogger(ProfesorGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        cursoWindow.setResizable(false);
        cursoWindow.add(panelCursos);
        cursoWindow.setSize(710, 600);
        cursoWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cursoWindow.setVisible(true);
        cursoWindow.setLocationRelativeTo(null);
    
   }
   
   public void actualizarProf(){
        actualizarWdw = new JDialog();
        actPane = new JPanel();
        
        actTituloLbl = new JLabel();
        actNombreLbl = new JLabel();
        actApellidoLbl = new JLabel();
        actCorreoLbl = new JLabel();
        actPassLbl = new JLabel();
        
        actGeneroLbl = new JLabel();
        actNombreTxt = new JTextField();
        actApellidoTxt = new JTextField();
        actCorreoTxt = new JTextField();
        actPassTxt = new JTextField();
        
        actGeneroCombo = new JComboBox();
        actBtn = new JButton();
       
        actPane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        actPane.setLayout(null);
        actPane.setBounds(10, 10, 450, 400);

        actTituloLbl.setFont(new java.awt.Font("Dialog", 1, 20));
        actTituloLbl.setText("Actualizar Datos");
        actPane.add(actTituloLbl);
        actTituloLbl.setBounds(20, 20, 250, 40);

        actGeneroCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino" }));
        actPane.add(actGeneroCombo);
        actGeneroCombo.setBounds(130, 280, 200, 26);
        actGeneroCombo.setSelectedItem(profActual.getGenero());

        actGeneroLbl.setText("Genero");
        actPane.add(actGeneroLbl);
        actGeneroLbl.setBounds(30, 280, 80, 20);

        actNombreLbl.setText("Nombre");
        actPane.add(actNombreLbl);
        actNombreLbl.setBounds(30, 80, 80, 20);

        actApellidoLbl.setText("Apellido");
        actPane.add(actApellidoLbl);
        actApellidoLbl.setBounds(30, 130, 80, 20);

        actCorreoLbl.setText("Correo");
        actPane.add(actCorreoLbl);
        actCorreoLbl.setBounds(30, 180, 80, 20);

        actPassLbl.setText("Contraseña");
        actPane.add(actPassLbl);
        actPassLbl.setBounds(30, 230, 80, 20);
        
        actNombreTxt.setText(profActual.getNombre());
        actPane.add(actNombreTxt);
        actNombreTxt.setBounds(130, 80, 280, 24);

        actApellidoTxt.setText(profActual.getApellido());
        actPane.add(actApellidoTxt);
        actApellidoTxt.setBounds(130, 130, 280, 24);

        actCorreoTxt.setText(profActual.getCorreo());
        actPane.add(actCorreoTxt);
        actCorreoTxt.setBounds(130, 180, 280, 24);

        actPassTxt.setText(profActual.getContraseña());
        actPane.add(actPassTxt);
        actPassTxt.setBounds(130, 230, 280, 24);
        
        actBtn.setText("Actualizar");
        actPane.add(actBtn);
        actBtn.setBounds(60, 330, 330, 32);
        actBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                profActual.setNombre(actNombreTxt.getText());
                profActual.setApellido(actApellidoTxt.getText());
                profActual.setCorreo(actCorreoTxt.getText());
                profActual.setContraseña(actPassTxt.getText());
                profActual.setGenero(actGeneroCombo.getSelectedItem().toString());
                guardar.Serializar(prof, curso, alumno);
                user.setText("Usuario: " + profActual.getNombre() + " " + profActual.getApellido());
                actualizarWdw.dispatchEvent(new WindowEvent(actualizarWdw, WindowEvent.WINDOW_CLOSING));
            }
        });
        
        actualizarWdw.add(actPane);
        actualizarWdw.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        actualizarWdw.setLayout(null);
        actualizarWdw.setResizable(false);
        actualizarWdw.setSize(480, 455);
        actualizarWdw.setVisible(true);
        actualizarWdw.setLocationRelativeTo(null);
   }
   
   public Profesores getProf(String codigo){
       int indexProf = 0;
       for (int i = 0; i < contProf; i++) {
           if (prof[i].getCodigo().equals(codigo)) {
               indexProf = i;
               i=1000;
           }
       }
       return prof[indexProf];
   }
   
   public Cursos getCurso(String nombreCurso){
       
       
       for (int i = 0; i < contCursosProf; i++) {
//           System.out.println(cursosAsignados[i].getNombre());
           if (cursosAsignados[i].getNombre().equals(nombreCurso)) {
               indexCurso = i;
           }
       }
       return cursosAsignados[indexCurso];
   }
   
   public void datosAlumnosGUI(int index, int fila) throws IOException{
       
        datosAlWdw = new JDialog();
        datosAlPane = new JPanel();
        
        datosAlTituloLbl = new JLabel();
        datosAlNombreLbl = new JLabel();
        datosAlApellidoLbl = new JLabel();
        datosAlCorreoLbl = new JLabel();
        datosAlPassLbl = new JLabel();
        datosAlGeneroLbl = new JLabel();
        datosAlImagen = new JLabel(getImage(alumnosAsignados[index].getCodigo()));

        datosAlFotoLbl = new JLabel();
        
        datosAlNombreTxt = new JTextField();
        datosAlApellidoTxt = new JTextField();
        datosAlCorreoTxt = new JTextField();
        datosAlPassTxt = new JTextField();
        datosAlGeneroTxt = new JTextField();

        datosAlEliminarBtn = new JButton();
        datosAlBackBtn = new JButton();
        
        datosAlPane.setLayout(null);
        datosAlPane.setBounds(10, 10, 450, 600);
        
        datosAlTituloLbl.setFont(new java.awt.Font("Dialog", 1, 20));
        datosAlTituloLbl.setText("Mas Información");
        datosAlPane.add(datosAlTituloLbl);
        datosAlTituloLbl.setBounds(20, 20, 250, 40);
        
        datosAlNombreTxt.setText(alumnosAsignados[index].getNombre());
        datosAlPane.add(datosAlNombreTxt);
        datosAlNombreTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        datosAlNombreTxt.setEditable(false);
        datosAlNombreTxt.setBounds(130, 290, 280, 24);

        datosAlApellidoTxt.setText(alumnosAsignados[index].getApellido());
        datosAlApellidoTxt.setEditable(false);
        datosAlPane.add(datosAlApellidoTxt);
        datosAlApellidoTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        datosAlApellidoTxt.setBounds(130, 340, 280, 24);
        
        datosAlCorreoTxt.setText(alumnosAsignados[index].getCorreo());
        datosAlCorreoTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        datosAlCorreoTxt.setEditable(false);
        datosAlPane.add(datosAlCorreoTxt);
        datosAlCorreoTxt.setBounds(130, 390, 280, 24);

        datosAlPassTxt.setText(alumnosAsignados[index].getContraseña());
        datosAlPassTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        datosAlPassTxt.setEditable(false);
        datosAlPane.add(datosAlPassTxt);
        datosAlPassTxt.setBounds(130, 440, 280, 24);
        
        datosAlGeneroTxt.setText(alumnosAsignados[index].getGenero());
        datosAlGeneroTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        datosAlGeneroTxt.setEditable(false);
        datosAlPane.add(datosAlGeneroTxt);
        datosAlGeneroTxt.setBounds(130, 490, 200, 24);

        datosAlNombreLbl.setText("Nombre");
        datosAlPane.add(datosAlNombreLbl);
        datosAlNombreLbl.setBounds(40, 290, 80, 20);
        
        datosAlGeneroLbl.setText("Genero");
        datosAlPane.add(datosAlGeneroLbl);
        datosAlGeneroLbl.setBounds(40, 490, 80, 20);

        datosAlFotoLbl.setText("Fotografia");
        datosAlPane.add(datosAlFotoLbl);
        datosAlFotoLbl.setBounds(40, 80, 70, 20);

        datosAlApellidoLbl.setText("Apellido");
        datosAlPane.add(datosAlApellidoLbl);
        datosAlApellidoLbl.setBounds(40, 340, 80, 20);

        datosAlCorreoLbl.setText("Correo");
        datosAlPane.add(datosAlCorreoLbl);
        datosAlCorreoLbl.setBounds(40, 390, 80, 20);

        datosAlPassLbl.setText("Contraseña");
        datosAlPane.add(datosAlPassLbl);
        datosAlPassLbl.setBounds(40, 440, 80, 20);

        datosAlImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        datosAlImagen.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        datosAlPane.add(datosAlImagen);
        datosAlImagen.setBounds(130, 80, 200, 180);

        datosAlEliminarBtn.setBackground(new java.awt.Color(255, 0, 0));
        datosAlEliminarBtn.setText("Elimina Estudiante del Curso");
        datosAlEliminarBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        datosAlPane.add(datosAlEliminarBtn);
        datosAlEliminarBtn.setBounds(40, 540, 200, 32);
        datosAlEliminarBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                
                Object[] options = {"Si",
                    "No"};
                
                JOptionPane borrar = new JOptionPane();
                int resultado = borrar.showOptionDialog(null,
                 "¿Esta Seguro que desea eliminar este alumno del curso?",
                 "Atencion",
                 JOptionPane.YES_NO_OPTION,
                 JOptionPane.PLAIN_MESSAGE,
                 null,
                 options,
                 null);
                
                alumnosAsignados[fila].eliminarCurso(cursoSeleccionado);
                
                if (resultado == JOptionPane.YES_OPTION) {
                    for (int i = fila; i < contAsignados; i++) {
                        alumnosAsignados[i] = alumnosAsignados[i+1];
                        alumnosAsignados[contAsignados] = null;
                    }
                    contAsignados--;
                    cursoSeleccionado.setContador(contAsignados);
                    

                    modelAlumnos.setNumRows(0);
                    for (int i = 0; i < contAsignados; i++) {
                        Object row[] = {alumnosAsignados[i].getCodigo(),alumnosAsignados[i].getNombre(),alumnosAsignados[i].getApellido(), "Mas Info."};
                        ((DefaultTableModel)tablaAlumnos.getModel()).addRow(row);
                    }
                    guardar.Serializar(prof, curso, alumno);
                    datosAlWdw.dispatchEvent(new WindowEvent(datosAlWdw, WindowEvent.WINDOW_CLOSING));
                } else {
//                    System.out.println("No");                
                }            
            }
        });
        
        datosAlBackBtn.setText("Regresar");
        datosAlBackBtn.setToolTipText("");
        datosAlPane.add(datosAlBackBtn);
        datosAlBackBtn.setBounds(250, 540, 160, 32);
        datosAlBackBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                datosAlWdw.dispatchEvent(new WindowEvent(datosAlWdw, WindowEvent.WINDOW_CLOSING));
            }
        });
        
        datosAlWdw.add(datosAlPane);
        datosAlWdw.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        datosAlWdw.setLayout(null);
        datosAlWdw.setResizable(false);
        datosAlWdw.setSize(480, 640);
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
   
   public int getAcumulado(){
       int acu = 0;
       for (int i = 0; i < contActividades; i++) {
           acu = acu + actividadCurso[i].getPonderacion();
       }
       return acu;
   }
   
   public String[][] getNotasTotales(){
        String[][] arrayNotas = new String[contAsignados][2];
//        System.out.println(contAsignados);
        double nota = 0;
        for (int i = 0; i < contAsignados; i++) {
            arrayNotas[i][0] = alumnosAsignados[i].getCodigo();
            arrayNotas[i][1] = f.format(getNota(i));
        }
        return arrayNotas;
   }
   
   public double getNota(int index){
        double nota = 0;
        for (int i = 0; i < contActividades; i++) {
            String[][] array = actividadCurso[i].getNotas();
            for (int j = 0; j < contAsignados; j++) {
                if (array[j][0].equals(alumnosAsignados[index].getCodigo())) {
                    nota += Double.parseDouble(array[j][1])*actividadCurso[i].getPonderacion()/100;
                }
            }
       }
        guardar.Serializar(prof, curso, alumno);
        return nota;
    }

   public void topAlumnos(String[][] arrayNotas){

       FileOutputStream ficheroPdf = null;
        try {
            double aux,aux2;
            for (int i = 0; i < arrayNotas.length - 1; i++) {
                for (int j = 0; j < arrayNotas.length - i - 1; j++) {
                    if ( Double.parseDouble(arrayNotas[j + 1][1]) > Double.parseDouble(arrayNotas[j][1])) {
                        aux = Double.parseDouble(arrayNotas[j + 1][1]);
                        aux2 = Double.parseDouble(arrayNotas[j + 1][0]);
                        arrayNotas[j + 1][1] = arrayNotas[j][1];
                        arrayNotas[j + 1][0] = arrayNotas[j][0];
                        arrayNotas[j][1] = Double.toString(aux);
                        arrayNotas[j][0] = Double.toString(aux2);
                    }
                }
            }
            //------------------------------------------------------------------------------------------------------------------------------------
            //------------------------------------------------------------------------------------------------------------------------------------
            //------------------------------------------------------------------------------------------------------------------------------------
            Document documento = new Document();
            ficheroPdf = new FileOutputStream("Reportes\\Reporte_Mejor_Rendimiento.pdf");
            PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);
            documento.open();
            Paragraph parrafo = new Paragraph("Estudiantes con Mejor Rendimiento", FontFactory.getFont("arial", 18, Font.BOLD, BaseColor.BLACK));
            parrafo.setAlignment(Element.ALIGN_CENTER);
            documento.add(parrafo);
            documento.add(new Paragraph(" "));
            
            
            int numeroCol = 6 + contActividades;

            PdfPTable tabla = new PdfPTable(numeroCol);

            tabla.addCell("Posicion");
            tabla.addCell("Codigo");
            tabla.addCell("Nombre");
            tabla.addCell("Apellido");
            tabla.addCell("Correo");
                
            for (int j = 0; j < contActividades; j++) {
                tabla.addCell(actividadCurso[j].getNombre());
            }
                
            tabla.addCell("Nota Total");
            
            
            
            for (int i = 0; i < 5; i++){
                int index = 0;
                for (int j = 0; j < contAsignados; j++) {
                    double aux3 = Double.parseDouble(arrayNotas[i][0]);
                    if (f2.format(aux3).equals(alumnosAsignados[j].getCodigo())) {
                        index = j;
                        j = 1000;
                    }
                }
                
                tabla.addCell("#" + Integer.toString(i+1));
                tabla.addCell(alumnosAsignados[index].getCodigo());
                tabla.addCell(alumnosAsignados[index].getNombre());
                tabla.addCell(alumnosAsignados[index].getApellido());
                tabla.addCell(alumnosAsignados[index].getCorreo());
                
                for (int j = 0; j < contActividades; j++) {
                    tabla.addCell(f.format(getNotaIn(index,j)));
                }
                
                tabla.addCell(arrayNotas[i][1]);
                
            }   documento.add(tabla);
            documento.close();
            File file = new File("Reportes\\Reporte_Mejor_Rendimiento.pdf");
            if(!Desktop.isDesktopSupported()){
                JOptionPane.showMessageDialog(frameBase,
                        "No se pudo abrir el archivo automaticamente",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            Desktop desktop = Desktop.getDesktop();
            if(file.exists()) try {
                desktop.open(file);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frameBase,
                        "No se pudo abrir el archivo automaticamente",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProfesorGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(ProfesorGUI.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ficheroPdf.close();
            } catch (IOException ex) {
                Logger.getLogger(ProfesorGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
   }
   
   public void downAlumnos(String[][] arrayNotas) throws FileNotFoundException, DocumentException{

        double aux,aux2;
        for (int i = 0; i < arrayNotas.length - 1; i++) { 
            for (int j = 0; j < arrayNotas.length - i - 1; j++) {
                if ( Double.parseDouble(arrayNotas[j + 1][1]) < Double.parseDouble(arrayNotas[j][1])) {
                    aux = Double.parseDouble(arrayNotas[j + 1][1]);
                    aux2 = Double.parseDouble(arrayNotas[j + 1][0]);
                    arrayNotas[j + 1][1] = arrayNotas[j][1];
                    arrayNotas[j + 1][0] = arrayNotas[j][0];
                    arrayNotas[j][1] = Double.toString(aux);
                    arrayNotas[j][0] = Double.toString(aux2);
                }
            }
        }
        //------------------------------------------------------------------------------------------------------------------------------------
        //------------------------------------------------------------------------------------------------------------------------------------
        //------------------------------------------------------------------------------------------------------------------------------------
        
        Document documento = new Document();

        FileOutputStream ficheroPdf = new FileOutputStream("Reportes\\Reporte_Peor_Rendimiento.pdf");

        PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);
        
        documento.open();
        
        Paragraph parrafo = new Paragraph("Estudiantes con Peor Rendimiento", FontFactory.getFont("arial", 18, Font.BOLD, BaseColor.BLACK));
        parrafo.setAlignment(Element.ALIGN_CENTER);
        documento.add(parrafo);
        documento.add(new Paragraph(" "));
        
        int numeroCol = 6 + contActividades;

            PdfPTable tabla = new PdfPTable(numeroCol);
           
            tabla.addCell("Posicion");
            tabla.addCell("Codigo");
            tabla.addCell("Nombre");
            tabla.addCell("Apellido");
            tabla.addCell("Correo");
                
            for (int j = 0; j < contActividades; j++) {
                tabla.addCell(actividadCurso[j].getNombre());
            }
                
            tabla.addCell("Nota Total");
            
            
            
            for (int i = 0; i < 5; i++){
                int index = 0;
                for (int j = 0; j < contAsignados; j++) {
                    double aux3 = Double.parseDouble(arrayNotas[i][0]);
                    if (f2.format(aux3).equals(alumnosAsignados[j].getCodigo())) {
                        index = j;
                        j = 1000;
                    }
                }
                
                tabla.addCell("#" + Integer.toString(i+1));
                tabla.addCell(alumnosAsignados[index].getCodigo());
                tabla.addCell(alumnosAsignados[index].getNombre());
                tabla.addCell(alumnosAsignados[index].getApellido());
                tabla.addCell(alumnosAsignados[index].getCorreo());
                
                for (int j = 0; j < contActividades; j++) {
                    tabla.addCell(f.format(getNotaIn(index,j)));
                }
                
                tabla.addCell(arrayNotas[i][1]);
                
            }   documento.add(tabla);
        
        documento.close();
        
        File file = new File("Reportes\\Reporte_Peor_Rendimiento.pdf");
        
        if(!Desktop.isDesktopSupported()){
                JOptionPane.showMessageDialog(frameBase,
                    "No se pudo abrir el archivo automaticamente",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        Desktop desktop = Desktop.getDesktop();
        if(file.exists()) try {
            desktop.open(file);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frameBase,
                    "No se pudo abrir el archivo automaticamente",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        
        
   }
   
   public double getNotaIn(int inAlumno, int inAct){
        String[][] notas = actividadCurso[inAct].getNotas();
        int cont = actividadCurso[inAct].getContador();
        double nota = 0;
        for (int i = 0; i < cont; i++) {
            if (notas[i][0].equals(alumnosAsignados[inAlumno].getCodigo())) {
                nota = Double.parseDouble(notas[i][1]);
                i = 1000;
            }
        }
        return nota;
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
