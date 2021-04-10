package Models;


import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Derek
 */
public class Profesores implements Serializable{
    private String codigo;
    private String nombre;
    private String apellido;
    private String correo;
    private String contraseña;
    private String genero;
    private Cursos[] cursosAsignados;
    private int contadorCursos;
    

    public Profesores(String codigo, String nombre, String apellido, String correo, String contraseña, String genero) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contraseña = contraseña;
        this.genero = genero;
        this.cursosAsignados = new Cursos[300];
        this.contadorCursos = 0;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
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

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    public void agregarCurso(Cursos curso){
        this.cursosAsignados[contadorCursos] = curso;
        contadorCursos++;     
    }
    
    public void eliminarCurso(Cursos curso){
        int indexCurso = 0;
        boolean cursoExiste = false;
        for (int i = 0; i < contadorCursos; i++) {
            if (curso.equals(cursosAsignados[i])) {
                indexCurso = i;
                cursoExiste = true;
            }
        }
        if (cursoExiste) {
            for (int i = indexCurso; i < contadorCursos; i++) {
                cursosAsignados[i] = cursosAsignados[i+1];
                cursosAsignados[contadorCursos] = null;
            }
        contadorCursos--;
        }
    }
    
    public int getContador(){
        return this.contadorCursos;
    }
    
    public Cursos[] getCursos(){
        return this.cursosAsignados;
    }
    
}
