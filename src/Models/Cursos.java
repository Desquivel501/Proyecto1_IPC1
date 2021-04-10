
package Models;

import java.io.Serializable;

/**
 *
 * @author Derek
 */
public class Cursos implements Serializable{
    private String codigo;
    private String nombre;
    private String creditos;
    private int alumnos;
    private String profesor;
    private Alumnos[] alumnosAsignados = new Alumnos[300];
    private int contadorAlumnos;
    private int contActividad;
    Actividades[] actividad;
    private int acumulado;
    private String alumnosStr;

    public Cursos(String codigo, String nombre, String creditos, int alumnos, String profesor) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.creditos = creditos;
        this.alumnos = alumnos;
        this.profesor = profesor;
        this.contadorAlumnos = 0;
        this.contActividad = 0;
        this.actividad = new Actividades[50];
        this.acumulado = 0;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCreditos() {
        return creditos;
    }

    public void setCreditos(String creditos) {
        this.creditos = creditos;
    }

    public int getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(int alumnos) {
        this.alumnos = alumnos;
    }
    
    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        
        this.profesor = profesor;
    }
    
    public void asignarAlumno(Alumnos alumnoNuevo){
        this.alumnosAsignados[contadorAlumnos] = alumnoNuevo;
        contadorAlumnos++;
    }
    
    public Alumnos[] getAlumnosAsignados(){
        return this.alumnosAsignados;
    }
    
    public int getContador(){
        return this.contadorAlumnos;
    }
    
    public void setContador(int newContador){
        this.contadorAlumnos = newContador;
    } 
    
    public void nuevaActividad(Actividades newActividad){
        this.actividad[contActividad] = newActividad;
        contActividad++;
    }
    
    public Actividades[] getActividades(){
        return this.actividad;
    }
    
    public int getNoActividades(){
        return this.contActividad;
    }

    public String getAlumnosStr() {
        return alumnosStr;
    }

    public void setAlumnosStr(String alumnosStr) {
        this.alumnosStr = alumnosStr;
    }

   
}
