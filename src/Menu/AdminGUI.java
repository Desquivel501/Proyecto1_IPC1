
package Menu;

import Models.*;
import Save.Serializar;
import com.google.gson.*;
import com.itextpdf.text.BaseColor;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.WindowEvent;
import java.io.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.JFXPanel;
import javafx.scene.chart.PieChart;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.stream.Stream;
import javafx.scene.chart.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 *
 * @author Derek
 */
public class AdminGUI {
    Profesores[] prof = new Profesores[50];
    Cursos[] curso = new Cursos[300];
    Alumnos[] alumno = new Alumnos[300];
    boolean firstRun = true;
    int contProf;
    int contCursos;
    int contAlumnos;
    int contAsignados;
    JFrame frameBase;
    JTabbedPane tabbedPane;
    JLabel tituloProf,crearLabelCodigo, crearLabelNombre, crearLabelApellido, crearLabelCorreo, crearLabelGenero, crearLabelPass, crearTitulo,actLabelCodigo, actLabelNombre, actLabelApellido, actLabelCorreo, actLabelGenero, actLabelPass, actTitulo;
    JPanel panelProf, panelCursos, panelAlumnos,panelProfChart,actPanel,crearPanel;
    JTable tablaProfesores;
    JButton crearButton,cargaButton,actualizarButton,eliminarButton,exportarButton,crearCrearButton,actButton,selButton;
    JScrollPane scrollTablaProfesores;
    PieChart pieChartProf;
    JDialog crearWindow,actualizarWindow;
    JComboBox crearComboGenero,actGenero;
    JTextField crearTextCodigo, crearTextNombre, crearTextApellido, crearTextCorreo, crearTextPass,actTextCodigo, actTextNombre, actTextApellido, actTextCorreo, actTextPass;
    JFXPanel jfxPanelProf;
    ScrollPane sp;
    
    JTable tablaCursos;
    JLabel tituloCursos;
    JButton cursosCrearBtn,cursosCargaBtn,cursosActBtn,cursosExportarBtn, cursosEliminarBtn;
    JFXPanel jfxPanelCursos;
    JScrollPane scrollTablaCursos;
    
    JTable tablaAlum;
    JLabel tituloAlum;
    JButton AlumCargaBtn,AlumExportarBtn;
    JFXPanel jfxPanelAlum;
    JScrollPane scrollTablaAlum;
    DecimalFormat df;
    
    JPanel panelCursoChart;
    BarChart<String, Number> barChart;
    CategoryAxis xAxis;
    NumberAxis yAxis;
    XYChart.Series<String, Number> series;
    
    JFreeChart chartProf;
    PieDataset datasetProf;
    ChartPanel panelChartProf;
    
    JFreeChart chartCursos;
    DefaultCategoryDataset datasetCursos;
    ChartPanel panelChartCursos;
    
    JFreeChart chartAlumno;
    JPanel panelAlumnoChart;
    PieDataset datasetAlumno;
    ChartPanel panelChartAlumno;
    
    JButton logout;
    JLabel usuario;
    
    Serializar guardar;
    

    public void AdminGUI(){
        
        guardar = new Serializar();
        logout = new JButton("Cerrar Sesion");
        usuario = new JLabel();
        frameBase = new JFrame();
        panelProf = new JPanel();
        panelCursos = new JPanel();
        panelAlumnos = new JPanel();
        panelProfChart = new JPanel(new BorderLayout());
        panelCursoChart = new JPanel(new BorderLayout());
        panelAlumnoChart = new JPanel(new BorderLayout());
       
        panelAlumnos.add(panelAlumnoChart);
        panelProf.add(panelProfChart);
        panelCursos.add(panelCursoChart);
        scrollTablaProfesores = new JScrollPane();
        tabbedPane = new JTabbedPane();
        crearButton = new JButton();
        cargaButton = new JButton();
        actualizarButton = new JButton();
        eliminarButton = new JButton();
        exportarButton = new JButton();
        JLabel tituloProf = new JLabel();
        
        df = new DecimalFormat("#.##");
        
        if(firstRun)deserializar();
                
        panelProf.setLayout(null);
        tabbedPane.addTab("Profesores", panelProf);
        
        tituloProf.setText("Listado Oficial");
        panelProf.add(tituloProf);
        tituloProf.setBounds(10, 10, 140, 20);
        
        tablaProfesores = new JTable(new DefaultTableModel(new Object [] {"Codigo", "Nombre", "Apellido", "Correo", "Genero"},0));
        DefaultTableModel modelProf = (DefaultTableModel) tablaProfesores.getModel();
        tablaProfesores.setDefaultEditor(Object.class, null);
        
        for (int i = 0; i < contProf; i++) {
            Object row[] = {prof[i].getCodigo(),prof[i].getNombre(),prof[i].getApellido(),prof[i].getCorreo(),prof[i].getGenero()};
            ((DefaultTableModel)tablaProfesores.getModel()).addRow(row);
        }

        
        scrollTablaProfesores.setViewportView(tablaProfesores);
        panelProf.add(scrollTablaProfesores);
        scrollTablaProfesores.setBorder(BorderFactory.createLineBorder(Color.black));
        scrollTablaProfesores.setBounds(6, 35, 410, 410);
        
        crearButton.setText("Crear");
        panelProf.add(crearButton);
        crearButton.setBounds(440, 40, 140, 32);
        crearButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (contProf <= 50) {
                    crearProfGUI();
                }else{
                    JOptionPane.showMessageDialog(panelProf,
                        "Se llego al numero maximo de profesores",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                            
                }
                
            }
        });
                
        cargaButton.setText("Carga Masiva");
        panelProf.add(cargaButton);
        cargaButton.setBounds(600, 40, 140, 32);
        cargaButton.addActionListener(new java.awt.event.ActionListener() {
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
                        if(i > 50){
                            JOptionPane.showMessageDialog(panelProf,
                            "Se llego al numero maximo de profesores",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                            break;
                        }
                        JsonObject obj = array.get(i).getAsJsonObject();
                        String codigo = obj.get("codigo").getAsString();
                        String nombre = obj.get("nombre").getAsString();
                        String apellido = obj.get("apellido").getAsString();
                        String correo = obj.get("correo").getAsString();
                        String genero = obj.get("genero").getAsString();
                        
                        if(genero.contentEquals("f")){
                            genero = "Femenino";
                        }
                        if(genero.contentEquals("m")){
                            genero = "Masculino";
                        }
                        
                        prof[contProf] = new Profesores(codigo,nombre,apellido,correo,"1234",genero);
                        Object row[] = {codigo,nombre,apellido,correo,genero};
                        ((DefaultTableModel)tablaProfesores.getModel()).addRow(row);
                        contProf++;
                        
                        if(contProf>49){
                            i=10000;
                            contProf = 50;
                        }
                    }
                    }
                }catch(Exception e){
                    
                }

                newChartProf();
                panelChartProf.revalidate();
                panelChartProf.repaint();
                
                guardar.Serializar(prof, curso, alumno);

            }
        });
        
        actualizarButton.setText("Actualizar");
        panelProf.add(actualizarButton);
        actualizarButton.setBounds(440, 90, 140, 32);
        actualizarButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarProfGUI();
            }
        });
        
        eliminarButton.setText("Eliminar");
        panelProf.add(eliminarButton);
        eliminarButton.setBounds(600, 90, 140, 32);
        eliminarButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                
                try{
                    int numRows = tablaProfesores.getSelectedRows().length;
                    for (int i = tablaProfesores.getSelectedRow(); i < contProf; i++) {
                        prof[i] = prof[i+1];
                        prof[contProf] = null;
                    }
                    contProf--;

                    modelProf.setNumRows(0);
                    for (int i = 0; i < contProf; i++) {
                        Object row[] = {prof[i].getCodigo(),prof[i].getNombre(),prof[i].getApellido(),prof[i].getCorreo(),prof[i].getGenero()};
                        ((DefaultTableModel)tablaProfesores.getModel()).addRow(row);
                    }
                    
                    newChartProf();
                    panelChartProf.revalidate();
                    panelChartProf.repaint();
                    
                    guardar.Serializar(prof, curso, alumno);
                    
                }catch(Exception e){
                    JOptionPane.showMessageDialog(panelProf,
                        "No se ha seleccionado ninguna fila",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        exportarButton.setText("Exportar Listado a PDF");
        panelProf.add(exportarButton);
        exportarButton.setBounds(440, 140, 300, 32);
        exportarButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    exportarPdfProf();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(AdminGUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DocumentException ex) {
                    Logger.getLogger(AdminGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        
        panelProfChart.setBorder(BorderFactory.createLineBorder(Color.black));
        panelProfChart.setBounds(430, 190, 320, 250);    
    

        //-------------------------------------------------------------------------------------------------------------------------------
        //------------------------------Modulo Cursos------------------------------------------------------------------------------------
        //-------------------------------------------------------------------------------------------------------------------------------
        
        panelCursos.setLayout(null);
        tabbedPane.addTab("Cursos", panelCursos);
        
        tituloCursos = new JLabel();
        tituloCursos.setText("Listado Oficial");
        panelCursos.add(tituloCursos);
        tituloCursos.setBounds(10, 10, 140, 20);
        
                
        tablaCursos = new JTable(new DefaultTableModel(new Object [] {"Codigo", "Nombre", "Creditos", "Alumnos", "Profesor"},0));
        DefaultTableModel modelCursos = (DefaultTableModel) tablaCursos.getModel();
        tablaProfesores.setDefaultEditor(Object.class, null);
        tablaCursos.setDefaultEditor(Object.class, null);
        
       for (int i = 0; i < contCursos; i++) {
            String nombreProf = "";
            for (int j = 0; j < contProf; j++) {
                if (curso[i].getProfesor().equals(prof[j].getCodigo())) {
                    nombreProf = prof[j].getNombre() + " " + prof[j].getApellido();
                    j=1000;
                }
            }

            Object row[] = {curso[i].getCodigo(), curso[i].getNombre(), curso[i].getCreditos(), curso[i].getAlumnos() , nombreProf};
//            Object row[] = {curso[i].getCodigo(), curso[i].getNombre(), curso[i].getCreditos(), curso[i].getAlumnosStr() , nombreProf};
            ((DefaultTableModel)tablaCursos.getModel()).addRow(row);
        }

        scrollTablaCursos = new JScrollPane();
        scrollTablaCursos.setViewportView(tablaCursos);
        panelCursos.add(scrollTablaCursos);
        scrollTablaCursos.setBounds(6, 35, 410, 410);
        
        cursosCrearBtn = new JButton();
        cursosCrearBtn.setText("Crear");
        panelCursos.add(cursosCrearBtn);
        cursosCrearBtn.setBounds(440, 40, 140, 32);
        cursosCrearBtn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(contProf != 0){
                    crearCursoGUI();
                }else if(contProf == 0){
                    JOptionPane.showMessageDialog(crearWindow,
                        "No hay profesores disponibles",
                        "Error",
                        JOptionPane.INFORMATION_MESSAGE);
                }
                
            }
        });
        
        cursosCargaBtn = new JButton();
        cursosCargaBtn.setText("Carga Masiva");
        panelCursos.add(cursosCargaBtn);
        cursosCargaBtn.setBounds(600, 40, 140, 32);
        cursosCargaBtn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String contenido = "";
                try{
                    JFileChooser fc = new JFileChooser();
                    int op = fc.showOpenDialog(frameBase);
                    if(op == JFileChooser.APPROVE_OPTION){
                    
                    FileReader fr = new FileReader(fc.getSelectedFile());
                    BufferedReader bf = new BufferedReader(fr);
                    String linea;
                    
                    while ((linea = bf.readLine()) != null) {
                        contenido += linea;
                    }
                    
                    JsonParser parser = new JsonParser();
                    JsonArray array = parser.parse(contenido).getAsJsonArray();
                   
                    if (contProf != 0) {
                        for (int i = 0; i < array.size(); i++) {
                            
                            JsonObject obj = array.get(i).getAsJsonObject();
                            String codigo = obj.get("codigo").getAsString();
                            String nombre = obj.get("nombre").getAsString();
                            String creditos = obj.get("creditos").getAsString();
                            String profesor = obj.get("profesor").getAsString();

                            String nombreProfesor = "";
                            int indexProf = 0;
                            for (int j = 0; j < contProf; j++) {
                                if(prof[j].getCodigo().equals(profesor)){
                                    nombreProfesor = prof[j].getNombre() + " " + prof[j].getApellido();
                                    indexProf = j;
                                    j=100;
                                }
                            }

                            curso[contCursos] = new Cursos(codigo,nombre,creditos,0,profesor);
                            prof[indexProf].agregarCurso(curso[contCursos]);
                            Object row[] = {codigo,nombre,creditos,"0",nombreProfesor};
                            modelCursos.addRow(row);
                            contCursos++;
                        }
                    }else{
                        JOptionPane.showMessageDialog(panelCursos,
                            "No se han ingresado profesores",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                    
                    newChartCursos();
                    panelChartCursos.revalidate();
                    panelChartCursos.repaint();
                    
                    guardar.Serializar(prof, curso, alumno);
                    
                }
                }catch(NullPointerException e){
                    JOptionPane.showMessageDialog(panelCursos,
                        "Ha ocurrido un error al asignar el profesor",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
//                    System.out.println("Error");
//                    e.printStackTrace();
                } catch (FileNotFoundException ex) {
//                    Logger.getLogger(AdminGUI.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(panelCursos,
                        "No se ha encontrado el archivo.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(panelCursos,
                        "No se ha podido acceder al archivo.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
//                    Logger.getLogger(AdminGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
        cursosActBtn = new JButton();
        cursosActBtn.setText("Actualizar");
        panelCursos.add(cursosActBtn);
        cursosActBtn.setBounds(440, 90, 140, 32);
        cursosActBtn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarCursoGUI();
                
            }
        });
        
        cursosEliminarBtn = new JButton();
        cursosEliminarBtn.setText("Eliminar");
        panelCursos.add(cursosEliminarBtn);
        cursosEliminarBtn.setBounds(600, 90, 140, 32);
        cursosEliminarBtn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                
                try{
                    
                    int indexProf = 0;
                    int indexCurso = 0;
                    String codigoCurso = tablaCursos.getValueAt(tablaCursos.getSelectedRow(),1).toString();
                    String nombreProfesor = tablaCursos.getValueAt(tablaCursos.getSelectedRow(),4).toString();
                        
                    for (int j = 0; j < contProf; j++) {
                        if(prof[j].getNombre().equals(nombreProfesor)){
                            indexProf = j;
                            j=100;
                        }
                    }
                    for (int i = 0; i < contCursos; i++) {
                        if(curso[i].getCodigo().equals(codigoCurso)){
                            indexCurso = i;
                            i=100;
                        }
                    }
                    prof[indexProf].eliminarCurso(curso[indexCurso]);
                    

                    for (int i = tablaCursos.getSelectedRow(); i < contCursos; i++) {
                        curso[i] = curso[i+1];
                        curso[contCursos] = null;
                    }
                    contCursos--;

                    modelCursos.setNumRows(0);
                    
                    for (int i = 0; i < contCursos; i++) {
                        String nombreProf = "";
                        for (int j = 0; j < contProf; j++) {
                            if (curso[i].getProfesor().equals(prof[j].getCodigo())) {
                                nombreProf = prof[j].getNombre() + " " + prof[j].getApellido();
                                j=1000;
                            }
                        }
                        Object row[] = {curso[i].getCodigo(), curso[i].getNombre(), curso[i].getCreditos(), curso[i].getAlumnos(), nombreProf};
                        ((DefaultTableModel)tablaCursos.getModel()).addRow(row);
                    }
                    
                    newChartCursos();
                    panelChartCursos.repaint();
                    panelChartCursos.revalidate();
                    guardar.Serializar(prof, curso, alumno);

                }catch(Exception e){
                    JOptionPane.showMessageDialog(panelCursos,
                        "No se ha seleccionado ninguna fila",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        cursosExportarBtn = new JButton();
        cursosExportarBtn.setText("Exportar Listado a PDF");
        panelCursos.add(cursosExportarBtn);
        cursosExportarBtn.setBounds(440, 140, 300, 32);
        cursosExportarBtn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    exportarPdfCurso();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(AdminGUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DocumentException ex) {
                    Logger.getLogger(AdminGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        panelCursoChart.setBorder(BorderFactory.createLineBorder(Color.black));
        panelCursoChart.setBounds(430, 190, 320, 250);
        
         //------------------------------------------------------------------------------------------------------------------------------
         //--------------------------------------------Alumnos-----------------------------------------------------------------------------
         //------------------------------------------------------------------------------------------------------------------------------

        panelAlumnos.setLayout(null);
        tabbedPane.addTab("Alumnos", panelAlumnos);
        
        tituloAlum = new JLabel();
        tituloAlum.setText("Listado Oficial");
        panelAlumnos.add(tituloAlum);
        tituloAlum.setBounds(10, 10, 140, 20);
        
        AlumCargaBtn = new JButton();
        AlumCargaBtn.setText("Carga Masiva");
        panelAlumnos.add(AlumCargaBtn);
        AlumCargaBtn.setBounds(440, 65, 300, 32);
        AlumCargaBtn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String contenido = "";
                try{
                    JFileChooser fc = new JFileChooser();
                    int op = fc.showOpenDialog(frameBase);
                    if(op == JFileChooser.APPROVE_OPTION){
                    
                    FileReader fr = new FileReader(fc.getSelectedFile());
                    BufferedReader bf = new BufferedReader(fr);
                    String linea;
                    
                    while ((linea = bf.readLine()) != null) {
                        contenido += linea;
                    }
                    
                    JsonParser parser = new JsonParser();
                    JsonArray array = parser.parse(contenido).getAsJsonArray();
                    
                    for (int i = 0; i < array.size(); i++) {
                        if(i > 300){
                            JOptionPane.showMessageDialog(panelAlumnos,
                            "Se llego al numero maximo de alumnos",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                            break;
                        }
                        
                        JsonObject obj = array.get(i).getAsJsonObject();
                        String codigo = obj.get("codigo").getAsString();
                        String nombre = obj.get("nombre").getAsString();
                        String apellido = obj.get("apellido").getAsString();
                        String correo = obj.get("correo").getAsString();
                        String genero = obj.get("genero").getAsString();
                        
                        if(genero.contentEquals("f")){
                            genero = "Femenino";
                        }
                        if(genero.contentEquals("m")){
                            genero = "Masculino";
                        }
                        
                        alumno[contAlumnos] = new Alumnos(codigo,nombre,apellido,correo,"1234",genero);
                        Object row[] = {codigo,nombre,apellido,correo,genero};
                        ((DefaultTableModel)tablaAlum.getModel()).addRow(row);
                        contAlumnos++;

                        
                    }
                }
                }catch(Exception e){
                    
                }
                newChartAlumno();
                panelChartAlumno.revalidate();
                panelChartAlumno.repaint();
                
                
                guardar.Serializar(prof, curso, alumno);
            }
        });
        
        AlumExportarBtn = new JButton();
        AlumExportarBtn.setText("Exportar Listado a PDF");
        panelAlumnos.add(AlumExportarBtn);
        AlumExportarBtn.setBounds(440, 125, 300, 32);
        AlumExportarBtn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    exportarPdfAlumnos();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(AdminGUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DocumentException ex) {
                    Logger.getLogger(AdminGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        tablaAlum = new JTable(new DefaultTableModel(new Object [] {"Codigo", "Nombre", "Apellido", "Correo", "Genero"},0));
        DefaultTableModel modelAlum = (DefaultTableModel) tablaAlum.getModel();
        tablaAlum .setDefaultEditor(Object.class, null);
        
        for (int i = 0; i < contAlumnos; i++) {
            Object row[] = {alumno[i].getCodigo(),alumno[i].getNombre(),alumno[i].getApellido(),alumno[i].getCorreo(),alumno[i].getGenero()};
            ((DefaultTableModel)tablaAlum.getModel()).addRow(row);
        }

        scrollTablaAlum = new JScrollPane();
        scrollTablaAlum.setViewportView(tablaAlum);
        panelAlumnos.add(scrollTablaAlum);
        scrollTablaAlum.setBounds(6, 35, 410, 410);
        
        
        panelAlumnoChart.setBorder(BorderFactory.createLineBorder(Color.black));
        panelAlumnoChart.setBounds(430, 190, 320, 250);

//---------------------------------------------------------------------------------------------------------------------------------------------------
        
        frameBase.add(logout);
        logout.setBounds(635, 5, 125, 19);
        logout.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardar.Serializar(prof, curso, alumno);
                LoginGUI regresar = new LoginGUI();
                frameBase.dispatchEvent(new WindowEvent(frameBase, WindowEvent.WINDOW_CLOSING));
            }
        });
        
        frameBase.add(usuario);
        usuario.setText("Usuario: Administrador");
        usuario.setBounds(480, 5, 150, 19);
        
        frameBase.setTitle("Modulo Administrativo");
        frameBase.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frameBase.setResizable(false);
        frameBase.add(tabbedPane);
        frameBase.setSize(775, 519);
        frameBase.setVisible(true);
        frameBase.setLocationRelativeTo(null);

    }

    private void crearProfGUI(){
        
        crearWindow = new JDialog();
        crearPanel = new JPanel();
        crearComboGenero = new JComboBox();
        crearTitulo = new JLabel();
        crearLabelCodigo = new JLabel();
        crearLabelNombre = new JLabel();
        crearLabelApellido = new JLabel();
        crearLabelCorreo = new JLabel();
        crearLabelGenero = new JLabel();
        crearLabelPass = new JLabel();
        crearTextCodigo = new JTextField();
        crearTextNombre = new JTextField();
        crearTextApellido = new JTextField();
        crearTextCorreo = new JTextField();
        crearTextPass = new JTextField();
        crearCrearButton = new JButton();
        
        crearWindow.setTitle("Crear Nuevo");
        crearPanel.setLayout(null);
        
        crearTitulo.setFont(new java.awt.Font("Arial", Font.BOLD, 18));
        crearTitulo.setText("Agregar Nuevo Profesor");
        crearPanel.add(crearTitulo);
        crearTitulo.setBounds(20, 10, 230, 40);
        

        crearLabelCodigo.setText("Codigo");
        crearPanel.add(crearLabelCodigo);
        crearLabelCodigo.setBounds(40, 70, 80, 16);

        crearLabelNombre.setText("Nombre");
        crearPanel.add(crearLabelNombre);
        crearLabelNombre.setBounds(40, 110, 80, 16);

        crearLabelApellido.setText("Apellido");
        crearPanel.add(crearLabelApellido);
        crearLabelApellido.setBounds(40, 150, 80, 16);

        crearLabelCorreo.setText("Correo");
        crearPanel.add(crearLabelCorreo);
        crearLabelCorreo.setBounds(40, 190, 80, 16);

        crearLabelPass.setText("Contraseña");
        crearPanel.add(crearLabelPass);
        crearLabelPass.setBounds(40, 230, 80, 16);

        crearLabelGenero.setText("Genero");
        crearPanel.add(crearLabelGenero);
        crearLabelGenero.setBounds(40, 270, 80, 20);
        
        crearPanel.add(crearTextCodigo);
        crearTextCodigo.setBounds(130, 70, 130, 24);
        
        crearPanel.add(crearTextNombre);
        crearTextNombre.setBounds(130, 110, 280, 24);
        
        crearPanel.add(crearTextApellido);
        crearTextApellido.setBounds(130, 150, 280, 24);
        
        crearPanel.add(crearTextCorreo);
        crearTextCorreo.setBounds(130, 190, 280, 24);
        
        crearPanel.add(crearTextPass);
        crearTextPass.setBounds(130, 230, 280, 24);

        crearComboGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Femenino", "Masculino" }));
        crearPanel.add(crearComboGenero);
        crearComboGenero.setBounds(130, 270, 150, 26);
        
        crearCrearButton.setText("Crear");
        crearPanel.add(crearCrearButton);
        crearCrearButton.setBounds(80, 320, 290, 30);
        crearCrearButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(contProf<=50){
                    prof[contProf] = new Profesores(crearTextCodigo.getText(),crearTextNombre.getText(),crearTextApellido.getText(),crearTextCorreo.getText(),crearTextPass.getText(), crearComboGenero.getSelectedItem().toString());
                    Object row[] = {crearTextCodigo.getText(),crearTextNombre.getText(),crearTextApellido.getText(),crearTextCorreo.getText(),crearComboGenero.getSelectedItem().toString()};
                    ((DefaultTableModel)tablaProfesores.getModel()).addRow(row);
                    contProf++;
                }else{
                    JOptionPane.showMessageDialog(crearWindow,
                        "Se ha llegado al limite de profesores",
                        "Error",
                        JOptionPane.INFORMATION_MESSAGE);
                }
                
                
                newChartProf();
                panelChartProf.revalidate();
                panelChartProf.repaint();
                
                guardar.Serializar(prof, curso, alumno);
                
                crearWindow.dispatchEvent(new WindowEvent(crearWindow, WindowEvent.WINDOW_CLOSING));
            }
        });
        crearWindow.setResizable(false);
        crearWindow.add(crearPanel);
        crearWindow.setSize(460, 400);
        crearWindow.setVisible(true);
        crearWindow.setLocationRelativeTo(null);
    }
    
    private void actualizarProfGUI(){
        actualizarWindow = new JDialog();
        actPanel = new  JPanel();
        actButton = new JButton();
        selButton = new JButton();
        actGenero = new JComboBox();
        actLabelCodigo = new JLabel();
        actLabelNombre = new JLabel();
        actLabelApellido = new JLabel();
        actLabelCorreo = new JLabel();
        actLabelGenero = new JLabel();
        actLabelPass = new JLabel();
        actTitulo = new JLabel();
        actTextCodigo = new JTextField(); 
        actTextNombre = new JTextField();  
        actTextApellido = new JTextField(); 
        actTextCorreo = new JTextField(); 
        actTextPass = new JTextField(); 

        actualizarWindow.setTitle("Actualizar");
        actPanel.setLayout(null);

        actTitulo.setFont(new java.awt.Font("Arial", Font.BOLD, 18));
        actTitulo.setText("Actualizar Profesor");
        actPanel.add(actTitulo);
        actTitulo.setBounds(20, 10, 230, 40);  

        actLabelCodigo.setText("Codigo");
        actPanel.add(actLabelCodigo);
        actLabelCodigo.setBounds(40, 70, 80, 16);

        selButton.setText("Seleccionar");
        actPanel.add(selButton);
        selButton.setBounds(270, 70, 120, 24);
        selButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                
                if (Integer.parseInt(actTextCodigo.getText()) <= contProf) {
                    try{
                
                    String actCodigo = actTextCodigo.getText();
                    int intCodigo = 0;

                    for (int j = 0; j < contProf; j++) {
                        if(prof[j].getCodigo().equals(actCodigo)){
                            actCodigo = prof[j].getNombre();
                            intCodigo = j;
                            j=100;
                        }
                    }

                    actTextNombre.setText(prof[intCodigo].getNombre());
                    actTextApellido.setText(prof[intCodigo].getApellido());
                    actTextCorreo.setText(prof[intCodigo].getCorreo());
                    actTextPass.setText(prof[intCodigo].getContraseña());
                    actGenero.setSelectedItem(prof[intCodigo].getGenero());

                    }catch(Exception e){
                        JOptionPane.showMessageDialog(actualizarWindow,
                            "No existe ningun profesor con ese codigo",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(actualizarWindow,
                            "No existe ningun profesor con ese codigo",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                
            }
        });

        actLabelNombre.setText("Nombre");
        actPanel.add(actLabelNombre);
        actLabelNombre.setBounds(40, 110, 80, 16);

        actLabelApellido.setText("Apellido");
        actPanel.add(actLabelApellido);
        actLabelApellido.setBounds(40, 150, 80, 16);

        actLabelCorreo.setText("Correo");
        actPanel.add(actLabelCorreo);
        actLabelCorreo.setBounds(40, 190, 80, 16);

        actLabelPass.setText("Contraseña");
        actPanel.add(actLabelPass);
        actLabelPass.setBounds(40, 230, 80, 16);

        actLabelGenero.setText("Genero");
        actPanel.add(actLabelGenero);
        actLabelGenero.setBounds(40, 270, 80, 20);

        actPanel.add(actTextCodigo);
        actTextCodigo.setBounds(130, 70, 130, 24);

        actPanel.add(actTextNombre);
        actTextNombre.setBounds(130, 110, 280, 24);

        actPanel.add(actTextApellido);
        actTextApellido.setBounds(130, 150, 280, 24);

        actPanel.add(actTextCorreo);
        actTextCorreo.setBounds(130, 190, 280, 24);

        actPanel.add(actTextPass);
        actTextPass.setBounds(130, 230, 280, 24);

        actGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Femenino", "Masculino" }));
        actPanel.add(actGenero);
        actGenero.setBounds(130, 270, 150, 26);

        actButton.setText("Actualizar");
        actPanel.add(actButton);
        actButton.setBounds(80, 320, 290, 30);
        actButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (actTextCodigo.getText() != null) {
                    
                    try{
                        ((DefaultTableModel)tablaProfesores.getModel()).setRowCount(0);
                
                        int intCodigo = 0;
                        for (int j = 0; j < contProf; j++) {
                            if(prof[j].getCodigo().equals(actTextCodigo.getText())){
                                intCodigo = j;
                                j=100;
                            }
                        }

                        prof[intCodigo].setNombre(actTextNombre.getText());
                        prof[intCodigo].setApellido(actTextApellido.getText());
                        prof[intCodigo].setCorreo(actTextCorreo.getText());
                        prof[intCodigo].setContraseña(actTextPass.getText());
                        prof[intCodigo].setGenero(actGenero.getSelectedItem().toString());

                        for (int i = 0; i < contProf; i++) {
                            Object row[] = {prof[i].getCodigo(),prof[i].getNombre(),prof[i].getApellido(),prof[i].getCorreo(),prof[i].getGenero()};
                            ((DefaultTableModel)tablaProfesores.getModel()).addRow(row);
                        }
                        
                        panelChartProf.revalidate();
                        newChartProf();
                        panelChartProf.revalidate();
                        panelChartProf.repaint();
                
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(actualizarWindow,
                        "No existe ningun profesor con ese codigo ",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(actualizarWindow,
                        "No se selecciono ningun profesor",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
               
                
                guardar.Serializar(prof, curso, alumno);
                

                

                actualizarWindow.dispatchEvent(new WindowEvent(actualizarWindow, WindowEvent.WINDOW_CLOSING));
            }
        });
        


        actualizarWindow.setResizable(false);
        actualizarWindow.add(actPanel);
        actualizarWindow.setSize(460, 400);
        actualizarWindow.setVisible(true);
        actualizarWindow.setLocationRelativeTo(null);
    }
    
    private void crearCursoGUI(){
        JDialog crearCursoWindow = new JDialog();
        JPanel crearCursoPanel = new JPanel();
        JComboBox crearComboProfesor = new JComboBox();
        JLabel crearCursoTitulo = new JLabel();
        JLabel crearCursoLabelCodigo = new JLabel();
        JLabel crearCursoLabelNombre = new JLabel();
        JLabel crearCursoLabelCred = new JLabel();
        JLabel crearCursoLabelProf = new JLabel();
        JTextField crearCursoTextCodigo = new JTextField();
        JTextField crearCursoTextNombre = new JTextField();
        JTextField crearCursoTextCreds = new JTextField();
        JButton crearCursoButton2 = new JButton();
        
        crearCursoWindow.setTitle("Crear Nuevo");
        crearCursoPanel.setLayout(null);
        
        crearCursoTitulo.setFont(new java.awt.Font("Arial", Font.BOLD, 18));
        crearCursoTitulo.setText("Agregar Nuevo Cursor");
        crearCursoPanel.add(crearCursoTitulo);
        crearCursoTitulo.setBounds(20, 10, 230, 40);
        
        crearCursoLabelCodigo.setText("Codigo");
        crearCursoPanel.add(crearCursoLabelCodigo);
        crearCursoLabelCodigo.setBounds(40, 70, 80, 16);

        crearCursoLabelNombre.setText("Nombre");
        crearCursoPanel.add(crearCursoLabelNombre);
        crearCursoLabelNombre.setBounds(40, 110, 80, 16);

        crearCursoLabelCred.setText("Creditos");
        crearCursoPanel.add(crearCursoLabelCred);
        crearCursoLabelCred.setBounds(40, 150, 80, 16);

        crearCursoLabelProf.setText("Profesor");
        crearCursoPanel.add(crearCursoLabelProf);
        crearCursoLabelProf.setBounds(40, 190, 80, 20);
        
        crearCursoPanel.add(crearCursoTextCodigo);
        crearCursoTextCodigo.setBounds(130, 70, 130, 24);
        
        crearCursoPanel.add(crearCursoTextNombre);
        crearCursoTextNombre.setBounds(130, 110, 280, 24);
        
        crearCursoPanel.add(crearCursoTextCreds);
        crearCursoTextCreds.setBounds(130, 150, 280, 24);
        
        String[] profDisponibles = new String[contProf];
        for (int i = 0; i < contProf; i++) {
            profDisponibles[i] = prof[i].getNombre() + " " + prof[i].getApellido();
        }
        crearComboProfesor.setModel(new javax.swing.DefaultComboBoxModel<>(profDisponibles));
        crearCursoPanel.add(crearComboProfesor);
        crearComboProfesor.setBounds(130, 190, 280, 26);
        
        crearCursoButton2.setText("Crear");
        crearCursoPanel.add(crearCursoButton2);
        crearCursoButton2.setBounds(80, 240, 290, 30);
        crearCursoButton2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try{
                    if(contCursos<=50){
                        int codigo = 0;
                        String str = crearComboProfesor.getSelectedItem().toString();
                        String[] arrOfStr = str.split(" ");
                        for (int i = 0; i < contProf; i++) {
                            if (prof[i].getNombre().equals(arrOfStr[0]) && prof[i].getApellido().equals(arrOfStr[1])) {
                                codigo = i;
                            }
                        }
                        curso[contCursos] = new Cursos(crearCursoTextCodigo.getText(),crearCursoTextNombre.getText(),crearCursoTextCreds.getText(),0,prof[codigo].getCodigo());
                        prof[codigo].agregarCurso(curso[contCursos]);
                        
                        Object row[] = {crearCursoTextCodigo.getText(),crearCursoTextNombre.getText(),crearCursoTextCreds.getText(),0,crearComboProfesor.getSelectedItem().toString()};
                        ((DefaultTableModel)tablaCursos.getModel()).addRow(row);
                        contCursos++;
                        
                        newChartCursos();
                        panelChartCursos.repaint();
                        panelChartCursos.revalidate();
                        
                    }else{
                        JOptionPane.showMessageDialog(crearWindow,
                            "Se ha llegado al limite de profesores",
                            "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                }catch(NullPointerException npe){
                    JOptionPane.showMessageDialog(crearWindow,
                        "No se ha ingresado ningun valor",
                        "Error",
                        JOptionPane.INFORMATION_MESSAGE);
                }
                guardar.Serializar(prof, curso, alumno);

                crearCursoWindow.dispatchEvent(new WindowEvent(crearCursoWindow, WindowEvent.WINDOW_CLOSING));
            }
        });
        
        crearCursoWindow.setResizable(false);
        crearCursoWindow.add(crearCursoPanel);
        crearCursoWindow.setSize(460, 340);
        crearCursoWindow.setVisible(true);
        crearCursoWindow.setLocationRelativeTo(null);
    }
    
    private void actualizarCursoGUI(){
        JDialog actualizarCursoWindow = new JDialog();
        JPanel actCursoPanel = new  JPanel();
        JButton actCursoButton = new JButton();
        JButton selCursoButton = new JButton();
        JComboBox actCursoCombo = new JComboBox();
        JLabel actCursoLabelCodigo = new JLabel();
        JLabel actCursoLabelNombre = new JLabel();
        JLabel actCursoLabelCred = new JLabel();
        JLabel actCursoLabelProf = new JLabel();
        JLabel actCursoTitulo = new JLabel();
        JTextField actCursoTextCodigo = new JTextField(); 
        JTextField actCursoTextNombre = new JTextField();  
        JTextField actCursoTextCred = new JTextField(); 

        actualizarCursoWindow.setTitle("Actualizar");
        actCursoPanel.setLayout(null);

        actCursoTitulo.setFont(new java.awt.Font("Arial", Font.BOLD, 18));
        actCursoTitulo.setText("Actualizar Curso");
        actCursoPanel.add(actCursoTitulo);
        actCursoTitulo.setBounds(20, 10, 230, 40);  

        actCursoLabelCodigo.setText("Codigo");
        actCursoPanel.add(actCursoLabelCodigo);
        actCursoLabelCodigo.setBounds(40, 70, 80, 16);
        

        selCursoButton.setText("Seleccionar");
        actCursoPanel.add(selCursoButton);
        selCursoButton.setBounds(270, 70, 120, 24);
        selCursoButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try{
                
                    String actCodigo = actCursoTextCodigo.getText();
                    int intCodigo = 0;

                    for (int j = 0; j < contCursos; j++) {
                        if(curso[j].getCodigo().equals(actCodigo)){
                            actCodigo = curso[j].getNombre();
                            intCodigo = j;
                            j=100;
                        }
                    }
                    int indexProf = 0;

                    for (int i = 0; i < contProf; i++) {
                        if (prof[i].getCodigo().equals(curso[intCodigo].getProfesor())) {
                            indexProf = i;
                            i = 1000;
                        }
                    }

                    actCursoTextNombre.setText(curso[intCodigo].getNombre());
                    actCursoTextCred.setText(curso[intCodigo].getCreditos());
                    actCursoCombo.setSelectedItem(prof[indexProf].getNombre() + " " + prof[indexProf].getApellido());
                    
                }catch(Exception e){
                    JOptionPane.showMessageDialog(actualizarWindow,
                        "No existe ningun profesor con ese codigo",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        actCursoLabelNombre.setText("Nombre");
        actCursoPanel.add(actCursoLabelNombre);
        actCursoLabelNombre.setBounds(40, 110, 80, 16);

        actCursoLabelCred.setText("Creditos");
        actCursoPanel.add(actCursoLabelCred);
        actCursoLabelCred.setBounds(40, 150, 80, 16);

        actCursoLabelProf.setText("Profesor");
        actCursoPanel.add(actCursoLabelProf);
        actCursoLabelProf.setBounds(40, 190, 80, 20);

        actCursoPanel.add(actCursoTextCodigo);
        actCursoTextCodigo.setBounds(130, 70, 130, 24);

        actCursoPanel.add(actCursoTextNombre);
        actCursoTextNombre.setBounds(130, 110, 280, 24);
        
        actCursoPanel.add(actCursoTextCred);
        actCursoTextCred.setBounds(130, 150, 280, 24);
        
        String[] profDisponibles = new String[contProf];
        for (int i = 0; i < contProf; i++) {
            profDisponibles[i] = prof[i].getNombre() + " " + prof[i].getApellido();
        }
        actCursoCombo.setModel(new javax.swing.DefaultComboBoxModel<>(profDisponibles));
        actCursoPanel.add(actCursoCombo);
        actCursoCombo.setBounds(130, 190, 280, 26);


        actCursoButton.setText("Actualizar");
        actCursoPanel.add(actCursoButton);
        actCursoButton.setBounds(80, 320, 290, 30);
        actCursoButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int actCodigo = Integer.parseInt(actCursoTextCodigo.getText());
                ((DefaultTableModel)tablaCursos.getModel()).setRowCount(0);

                
                String nombreCompleto = actCursoCombo.getSelectedItem().toString();
                String[] nombreArr = nombreCompleto.split(" ");
                
                int codigoNuevo = 0;
                for (int i = 0; i < contProf; i++) {
                     if (prof[i].getNombre().equals(nombreArr[0]) && prof[i].getApellido().equals(nombreArr[1])) {
                        codigoNuevo = i;
                     }
                }
                
                String[] profViejo = curso[actCodigo-1].getProfesor().split(" ");
                int codigoViejo = 0;
                for (int i = 0; i < contCursos; i++) {
                    if (profViejo[0].equals(curso[i].getNombre()) && prof[i].getApellido().equals(nombreArr[1])) {
                        codigoViejo = i;
                    }
                }
                
                boolean cambio = false;
                if (!curso[actCodigo-1].getProfesor().equals(nombreArr[0] + nombreArr[1])) {
                    prof[codigoViejo].eliminarCurso(curso[actCodigo-1]);
                    cambio = true;
                }
                
                curso[actCodigo-1].setCodigo(actCursoTextCodigo.getText());
                curso[actCodigo-1].setNombre(actCursoTextNombre.getText());
                curso[actCodigo-1].setCreditos(actCursoTextCred.getText());
                curso[actCodigo-1].setProfesor(prof[codigoNuevo].getCodigo());
                
                if (cambio) {
                    prof[codigoNuevo].agregarCurso(curso[actCodigo-1]);
                }
                

                for (int i = 0; i < contCursos; i++) {
                    String nombreProf = "";
                    for (int j = 0; j < contProf; j++) {
                        if (curso[i].getProfesor().equals(prof[j].getCodigo())) {
                            nombreProf = prof[j].getNombre() + " " + prof[j].getApellido();
                            j=1000;
                        }
                    }
                    
                    Object row[] = {curso[i].getCodigo(), curso[i].getNombre(), curso[i].getCreditos(), curso[i].getAlumnos(), nombreProf};
                    ((DefaultTableModel)tablaCursos.getModel()).addRow(row);
                }
                
                newChartCursos();
                panelChartCursos.repaint();
                panelChartCursos.revalidate();
                
                guardar.Serializar(prof, curso, alumno);
                actualizarCursoWindow.dispatchEvent(new WindowEvent(actualizarCursoWindow, WindowEvent.WINDOW_CLOSING));
            }
        });

        actualizarCursoWindow.setResizable(false);
        actualizarCursoWindow.add(actCursoPanel);
        actualizarCursoWindow.setSize(460, 400);
        actualizarCursoWindow.setVisible(true);
        actualizarCursoWindow.setLocationRelativeTo(null);
    }  
    
    private void newChartProf(){
     chartProf = ChartFactory.createPieChart("Genero Profesores",  
        datasetProf(),  
        true,   
        true,  
        false);  
     
    panelChartProf = new ChartPanel(chartProf);
    panelProfChart.add(panelChartProf);  

    }
    
    private PieDataset datasetProf(){
        DefaultPieDataset dataset = new DefaultPieDataset();  
        
        double numeroF = 0;
        double numeroM = 0;
        for (int i = 0; i < contProf; i++) {
            if(prof[i].getGenero().contentEquals("Masculino")){
                numeroM++;
            }if(prof[i].getGenero().contentEquals("Femenino")){
                numeroF++;
            }
        }
        
        dataset.setValue("Masculino", numeroM);  
        dataset.setValue("Femenino", numeroF);  
        
        return dataset; 
    }
    
    public void newChartCursos(){
        chartCursos = ChartFactory.createBarChart("Cursos con mas Alumnos", null, "Alumnos", datasetCursos(), PlotOrientation.VERTICAL, true, true, false);
        
        panelChartCursos = new ChartPanel(chartCursos);
        panelCursoChart.add(panelChartCursos);
    }
    
    public DefaultCategoryDataset datasetCursos(){
        DefaultCategoryDataset datasetCursos = new DefaultCategoryDataset();
        
        int[][] topCursos = new int[contCursos][2];
            
            for (int i = 0; i < contCursos; i++) {
                topCursos[i][0] = Integer.parseInt(curso[i].getCodigo());
                topCursos[i][1] = curso[i].getAlumnos();
//                int al = 0;
//                if(!curso[i].getAlumnosStr().equals("")){
//                    al = Integer.parseInt(curso[i].getAlumnosStr());
//                    System.out.println(al);
//                }
//                topCursos[i][1] = al;
//                System.out.println(curso[i].getAlumnos());
            }
            int aux,aux2;
            for (int i = 0; i < topCursos.length - 1; i++) {//Bubble sort 
                for (int j = 0; j < topCursos.length - i - 1; j++) {
                    if (topCursos[j + 1][1] > topCursos[j][1]) {
                        aux = topCursos[j + 1][1];
                        aux2 = topCursos[j + 1][0];
                        topCursos[j + 1][1] = topCursos[j][1];
                        topCursos[j + 1][0] = topCursos[j][0];
                        topCursos[j][1] = aux;
                        topCursos[j][0] = aux2;
                    }
                }
            }
            
            int cont = 0;
            if(contCursos<3){cont = contCursos;}
            else{cont = 3;}

            String nombreClase = "";
            for (int i = 0; i < cont; i++) {
                for (int j = 0; j < contCursos; j++) {
                    if (Integer.parseInt(curso[j].getCodigo()) == topCursos[i][0]) {
                        nombreClase = curso[j].getNombre();
                        j=1000;
                    }
                }
                datasetCursos.addValue(topCursos[i][1], nombreClase, "Cursos");
            }
        
        
        return datasetCursos;
    }
    
    private void newChartAlumno(){
     
     chartAlumno = ChartFactory.createPieChart("Genero Alumnos",  
        datasetAlumno(),  
        true,   
        true,  
        false);  

     panelChartAlumno = new ChartPanel(chartAlumno);
     panelAlumnoChart.add(panelChartAlumno);  

    }
    
    private PieDataset datasetAlumno(){
        DefaultPieDataset dataset = new DefaultPieDataset();  
        
        double numeroF = 0;
        double numeroM = 0;
        for (int i = 0; i < contAlumnos; i++) {
            if(alumno[i].getGenero().contentEquals("Masculino")){
                numeroM++;
            }if(alumno[i].getGenero().contentEquals("Femenino")){
                numeroF++;
            }
        }
        
        dataset.setValue("Masculino", numeroM);  
        dataset.setValue("Femenino", numeroF);  
        
        return dataset; 
    }

    public void exportarPdfProf() throws FileNotFoundException, DocumentException{
        
        Document documento = new Document();

        FileOutputStream ficheroPdf = new FileOutputStream("Reportes\\ReporteProfesores.pdf");

        PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);
        
        documento.open();
        
        Paragraph parrafo = new Paragraph("Listado de profesores", FontFactory.getFont("arial", 18, Font.BOLD, BaseColor.BLACK));
        parrafo.setAlignment(Element.ALIGN_CENTER);
        documento.add(parrafo);
        documento.add(new Paragraph(" "));
        
        PdfPTable tabla = new PdfPTable(6);
            
        Stream.of("Codigo", "Nombre", "Apellido","Correo","Contraseña","Genero")
            .forEach(columnTitle -> {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setPhrase(new Phrase(columnTitle));
                tabla.addCell(header);
            });
            
        for (int i = 0; i < contProf; i++){
            tabla.addCell(prof[i].getCodigo());
            tabla.addCell(prof[i].getNombre());
            tabla.addCell(prof[i].getApellido());
            tabla.addCell(prof[i].getCorreo());
            tabla.addCell(prof[i].getContraseña());
            tabla.addCell(prof[i].getGenero());
            
        }
        documento.add(tabla);
        
        documento.close();
        
        File file = new File("Reportes\\ReporteProfesores.pdf");
        
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
    
    public void exportarPdfCurso() throws DocumentException, FileNotFoundException{
        Document documento = new Document();

        FileOutputStream ficheroPdf = new FileOutputStream("Reportes\\ReporteCursos.pdf");

        PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);
        
        documento.open();
        
        Paragraph parrafo = new Paragraph("Listado de cursos", FontFactory.getFont("arial", 18, Font.BOLD, BaseColor.BLACK));
        parrafo.setAlignment(Element.ALIGN_CENTER);
        documento.add(parrafo);
        documento.add(new Paragraph(" "));
        
        PdfPTable tabla = new PdfPTable(5);
            
            Stream.of("Codigo", "Nombre", "Creditos","Alumnos","Profesor")
                .forEach(columnTitle -> {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setPhrase(new Phrase(columnTitle));
                tabla.addCell(header);
            });
            
        for (int i = 0; i < contProf; i++){
            String nombreProf = "";
            for (int j = 0; j < contProf; j++) {
                if (curso[i].getProfesor().equals(prof[j].getCodigo())) {
                    nombreProf = prof[j].getNombre() + " " + prof[j].getApellido();
                    j=1000;
                }
            }
            tabla.addCell(curso[i].getCodigo());
            tabla.addCell(curso[i].getNombre());
            tabla.addCell(curso[i].getCreditos());
            tabla.addCell(Integer.toString(curso[i].getAlumnos()));
            tabla.addCell(nombreProf);
        }
        documento.add(tabla);
        
        documento.close();
        
        File file = new File("Reportes\\ReporteCursos.pdf");
        
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
    
    public void exportarPdfAlumnos() throws FileNotFoundException, DocumentException{
        
        Document documento = new Document();

        FileOutputStream ficheroPdf = new FileOutputStream("Reportes\\ReporteAlumnos.pdf");

        PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);
        
        documento.open();
        
        Paragraph parrafo = new Paragraph("Listado de Alumnos", FontFactory.getFont("arial", 18, Font.BOLD, BaseColor.BLACK));
        parrafo.setAlignment(Element.ALIGN_CENTER);
        documento.add(parrafo);
        documento.add(new Paragraph(" "));
        
        PdfPTable tabla = new PdfPTable(6);
            
        Stream.of("Codigo", "Nombre", "Apellido","Correo","Contraseña","Genero")
            .forEach(columnTitle -> {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setPhrase(new Phrase(columnTitle));
                tabla.addCell(header);
            });
            
        for (int i = 0; i < contAlumnos; i++){
            tabla.addCell(alumno[i].getCodigo());
            tabla.addCell(alumno[i].getNombre());
            tabla.addCell(alumno[i].getApellido());
            tabla.addCell(alumno[i].getCorreo());
            tabla.addCell(alumno[i].getContraseña());
            tabla.addCell(alumno[i].getGenero());
            
        }
        documento.add(tabla);
        
        documento.close();
        
        File file = new File("Reportes\\ReporteAlumnos.pdf");
        
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
    
    public void deserializar(){
        try {
            ObjectInputStream profLoad = new ObjectInputStream(new FileInputStream("Saves\\profesores.ipc"));
            this.prof = (Profesores[]) profLoad.readObject();
            for (Profesores prof1 : this.prof) {
                if (prof1 == null) {break;}
                contProf++;
            }
            newChartProf();
            
            ObjectInputStream cursoLoad = new ObjectInputStream(new FileInputStream("Saves\\cursos.ipc"));
            this.curso = (Cursos[]) cursoLoad.readObject();
            for (Cursos curso1 : this.curso) {
                if (curso1  == null) {break;}
                contCursos++;
            }
            newChartCursos();
            
            ObjectInputStream alumnoLoad = new ObjectInputStream(new FileInputStream("Saves\\alumnos.ipc"));
            this.alumno = (Alumnos[]) alumnoLoad.readObject();
            for (Alumnos alumno1 : this.alumno) {
                if (alumno1 == null) {break;}
                contAlumnos++;
            }
            newChartAlumno();
            
            firstRun = false;
            
        } catch (IOException ex) {
//            Logger.getLogger(AdminGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(AdminGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
