/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Proceso;
import View.VistaPrincipal;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author CLMS
 */
public class ControladorPrincipal implements ActionListener {
    ArrayList<Proceso> listaProcesos;
     VistaPrincipal vistaPrincipal;
     DefaultTableModel tablaModelo;
     Simulador simulador;
     
    public ControladorPrincipal(ArrayList<Proceso> listaProcesos, VistaPrincipal vistaPrincipal) {
        this.listaProcesos = listaProcesos;
        this.vistaPrincipal = vistaPrincipal;
        this.vistaPrincipal.btnSimular.addActionListener(this);
        this.vistaPrincipal.btnAbrir.addActionListener(this);
        this.vistaPrincipal.btnGuardar.addActionListener(this);
        this.vistaPrincipal.btnAgregar.addActionListener(this);
        this.vistaPrincipal.btnEliminar.addActionListener(this);
                
        this.vistaPrincipal.setVisible(true);
        tablaModelo = new TablaModelo();
        this.vistaPrincipal.tablaProcesos.setModel(tablaModelo);
        seteaTabla();
        
        
    }
   
   
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Agregar".equals(e.getActionCommand())) {
            
            agregarProceso();
        }
        if ("Eliminar".equals(e.getActionCommand())) {
            
            eliminarProceso();
        }
        if ("Simular".equals(e.getActionCommand())) {
            
            simularProcesos();
        }
        
    }
    
    private void seteaTabla() {
        tablaModelo.setRowCount(1);
        tablaModelo.addColumn("No. Proceso");
        tablaModelo.addColumn("Inicio");
        tablaModelo.addColumn("Duracion");
        tablaModelo.addColumn("Prioridad");
        //tablaModelo.setValueAt("1", 0, 0);
        tablaModelo.setRowCount(0);
        String[] fila = {"1", "0", "0", "0"};
        tablaModelo.addRow(fila);
        //JOptionPane.showMessageDialog(null, "Prueba");
    }

    private void agregarProceso() {
        
        int ultimoFila;
        int ultimoProceso;
        String proceso;
        ultimoFila = tablaModelo.getRowCount();
        ultimoProceso = ultimoFila + 1;
        proceso = Integer.toString(ultimoProceso);
        //tablaModelo.setRowCount(ultimoProceso);
        //tablaModelo.setValueAt(proceso, ultimoFila, 0);
        String[] fila = {proceso, "0", "0", "0"};
        tablaModelo.addRow(fila);
        
        
        }

    private void eliminarProceso() {
        
        
        //Object proceso;
        int[] Fila = this.vistaPrincipal.tablaProcesos.getSelectedRows();
        int cantidadFilas = this.vistaPrincipal.tablaProcesos.getSelectedRowCount();
        int totalFilas = tablaModelo.getRowCount();
        int contador = 0;
        if (totalFilas == 1) {
            JOptionPane.showConfirmDialog(null, "Solo existe un proceso en lista. No se pudo eliminar el proceso") ;
            return ;
        }
        if (cantidadFilas > 0) {
            int respuesta = JOptionPane.showConfirmDialog(null, "Se eliminará " + Integer.toString(cantidadFilas) + ". ¿Está seguro?", "Eliminar filas", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                for (int temporal:Fila) {
                    tablaModelo.removeRow(temporal - contador);
                    contador++;
                }
                
                reorganizaProcesos();
            }
        }
        
        
        
        
    }

    private void simularProcesos() {
        int duracionTotal = 0;
        cargaLista();
        simulador = new Simulador(this.vistaPrincipal.PanelSimulador);
        
        for(Proceso proceso:listaProcesos){
            duracionTotal = duracionTotal + proceso.getDuracion();
        }
       //JOptionPane.showMessageDialog(null, Integer.toString( duracionTotal ));
        
        simulador.simulaProcesos(listaProcesos, duracionTotal);
        
    }

    private void reorganizaProcesos() {
        int totalFilas = tablaModelo.getRowCount();
        Object nroProceso;
        for (int i = 0; i < totalFilas; i++) {
            nroProceso = String.valueOf(i + 1);
            tablaModelo.setValueAt(nroProceso, i, 0);
        }
        
    }

    private void cargaLista() {
        
        listaProcesos.clear();
        int nroproceso, inicio, duracion, prioridad;
        Color color;
        for (int i = 0; i < tablaModelo.getRowCount(); i++) {
            nroproceso = Integer.parseInt((String) tablaModelo.getValueAt(i, 0));
            inicio = Integer.parseInt((String) tablaModelo.getValueAt(i, 1));
            duracion = Integer.parseInt((String) tablaModelo.getValueAt(i, 2));
            prioridad = Integer.parseInt((String) tablaModelo.getValueAt(i, 3));
            color = ControladorFunciones.colorAleatorio();
            Proceso proceso = new Proceso(nroproceso, inicio, duracion, prioridad, color);
            listaProcesos.add(proceso);
        }
        
    }

    
}
