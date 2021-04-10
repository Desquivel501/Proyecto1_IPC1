/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;

/**
 *
 * @author Derek
 */
public class Actividades implements Serializable{
    private String nombre;
    private String descripcion;
    private int ponderacion;
    private double promedio;
    private String[][] notas;
    private int contNotas;

    public Actividades(String nombre, String descripcion, int ponderacion, double promedio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ponderacion = ponderacion;
        this.promedio = promedio;
        this.notas = new String[300][2];
        this.contNotas = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(int ponderacion) {
        this.ponderacion = ponderacion;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public String[][] getNotas() {
        return notas;
    }

    public void setNotas(String alumno, String nota) {
        this.notas[contNotas][0] = alumno;
        this.notas[contNotas][1] = nota;
        contNotas++;

    }
    public int getContador(){
        return this.contNotas;
    }

}
