
package Models;

import java.io.Serializable;

/**
 *
 * @author Derek
 */
public class Alumnos implements Serializable{
    private String codigo;
    private String nombre;
    private String apellido;
    private String correo;
    private String contraseña;
    private String genero;
    Cursos[] cursosAsignados;
    int contCursos;



    public Alumnos(String codigo, String nombre, String apellido, String correo, String contraseña, String genero) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.genero = genero;
        this.contraseña = contraseña;
        this.cursosAsignados = new Cursos[100];
        this.contCursos = 0;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public void asignarCurso(Cursos newCurso){
        this.cursosAsignados[contCursos] = newCurso;
        contCursos++;
    }
    
    public void eliminarCurso(Cursos curso){
        int indexCurso = 0;
        boolean cursoExiste = false;
        for (int i = 0; i < contCursos; i++) {
            if (curso.equals(cursosAsignados[i])) {
                indexCurso = i;
                cursoExiste = true;
            }
        }
        if (cursoExiste) {
            for (int i = indexCurso; i < contCursos; i++) {
                cursosAsignados[i] = cursosAsignados[i+1];
                cursosAsignados[contCursos] = null;
            }
        contCursos--;
        }
    }
    
    public Cursos[] getCursos(){
        return this.cursosAsignados;
    }
    
    public int getContador(){
        return this.contCursos;
    }

}
