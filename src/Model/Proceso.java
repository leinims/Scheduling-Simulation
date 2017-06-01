/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Color;

/**
 *
 * @author RIOSLAL
 */
public class Proceso {
    private int nroproceso;
    private int inicio;
    private int duracion;
    private int prioridad;
    private Color color;
    private int T; //Tiempo total
    private int E; //Tiempo de Espera
    private float P; //Proporcion de Penalización
    private float R; //Proporcion de Respuesta

    public int getT() {
        return T;
    }

    public void setT(int T) {
        this.T = T;
    }

    public int getE() {
        return E;
    }

    public void setE(int E) {
        this.E = E;
    }

    public float getP() {
        return P;
    }

    public void setP(float P) {
        this.P = P;
    }

    public float getR() {
        return R;
    }

    public void setR(float R) {
        this.R = R;
    }
    
    

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getNroproceso() {
        return nroproceso;
    }

    public void setNroproceso(int nroproceso) {
        this.nroproceso = nroproceso;
    }

    public int getInicio() {
        return inicio;
    }

    public void setInicio(int inicio) {
        this.inicio = inicio;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public Proceso(int nroproceso, int inicio, int duracion, int prioridad, Color color) {
        this.nroproceso = nroproceso;
        this.inicio = inicio;
        this.duracion = duracion;
        this.prioridad = prioridad;
        this.color = color;
        
    }
    
}
