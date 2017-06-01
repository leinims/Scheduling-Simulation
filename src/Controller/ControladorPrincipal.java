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
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author CLMS
 */
public class ControladorPrincipal implements ActionListener, FocusListener {
    ArrayList<Proceso> listaProcesos;
     VistaPrincipal vistaPrincipal;
     DefaultTableModel tablaModelo;
     DefaultTableModel tablaModeloRendimiento;
     Simulador simulador;
     String algoritmoActual;
     int tick;
     
    public ControladorPrincipal(ArrayList<Proceso> listaProcesos, VistaPrincipal vistaPrincipal) {
        this.listaProcesos = listaProcesos;
        this.vistaPrincipal = vistaPrincipal;
        this.vistaPrincipal.btnSimular.addActionListener(this);
        this.vistaPrincipal.btnAgregar.addActionListener(this);
        this.vistaPrincipal.btnEliminar.addActionListener(this);
        this.vistaPrincipal.comboAlgoritmos.addActionListener(this);
        this.vistaPrincipal.txtTicks.addFocusListener(this);
        
        this.vistaPrincipal.comboAlgoritmos.addItem("FIFO");
        this.vistaPrincipal.comboAlgoritmos.addItem("Round Robin");
        this.vistaPrincipal.comboAlgoritmos.addItem("SPN");
        
        
        this.vistaPrincipal.setVisible(true);
        tablaModelo = new TablaModelo();
        tablaModeloRendimiento = new DefaultTableModel();
        this.vistaPrincipal.tablaProcesos.setModel(tablaModelo);
        this.vistaPrincipal.tablaRendimiento.setModel(tablaModeloRendimiento);
        seteaTablaProcesos();
        seteaTablaRendimiento();
        tick = 4;
        
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
        if ("comboBoxChanged".equals(e.getActionCommand())) {
            algoritmoActual = (String) this.vistaPrincipal.comboAlgoritmos.getSelectedItem();
            if("Round Robin".equals(algoritmoActual) ){
                this.vistaPrincipal.txtTicks.setEnabled(true);
            }
            else {
                this.vistaPrincipal.txtTicks.setEnabled(false);
            }
        }
        
        
    }
    
    private void seteaTablaProcesos() {
        tablaModelo.setRowCount(1);
        tablaModelo.addColumn("No. Proceso");
        tablaModelo.addColumn("Inicio");
        tablaModelo.addColumn("Duracion");
        tablaModelo.addColumn("Prioridad");
        //tablaModelo.setValueAt("1", 0, 0);
        tablaModelo.setRowCount(0);
        String[] fila = {"1", "0", "1", "0"};
        tablaModelo.addRow(fila);
        
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
        String[] fila = {proceso, "0", "1", "0"};
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
       
       
       ArrayList<Proceso> listaResultado ;
       listaResultado = calculaProceso(duracionTotal);
       simulador.simulaProcesos(listaResultado, duracionTotal);
       
       for(Proceso procesoTemporal1:listaProcesos){
           for(Proceso procesoTemporal2:listaResultado){
               if(procesoTemporal1.getNroproceso() == procesoTemporal2.getNroproceso() ){
                   procesoTemporal1.setT(procesoTemporal2.getInicio() + procesoTemporal2.getDuracion());
                   
               } 
           }
           procesoTemporal1.setE(procesoTemporal1.getT() - procesoTemporal1.getDuracion());
           if (procesoTemporal1.getDuracion() != 0) {
                procesoTemporal1.setP((float) procesoTemporal1.getT() / (float) procesoTemporal1.getDuracion());
                procesoTemporal1.setR((float) procesoTemporal1.getDuracion() / (float) procesoTemporal1.getT()); 
           } else {
                procesoTemporal1.setP(0);
                procesoTemporal1.setR(0); 
           }
              
       }
      muestraRendimiento();
        
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

    private ArrayList<Proceso> calculaProceso(int duracionTotal) {
            
            ArrayList<Proceso> listaClon = (ArrayList<Proceso>) listaProcesos.clone();
            ArrayList<Proceso> listaresultado = new ArrayList<Proceso>();
            int momentoActual = 0;
            
            
            for (int i = 0; i < listaProcesos.size(); i++) {    
                
                Proceso procesoSiguiente = new Proceso(9999, 9999, 9999, 0, Color.BLACK) ;
                for (Proceso proceso:listaClon){
                    if ("FIFO".equals(this.algoritmoActual) || "Round Robin".equals(this.algoritmoActual)) {
                        if (proceso.getInicio() < procesoSiguiente.getInicio()) {

                            procesoSiguiente = proceso;
                        }
                         
                    }
                    if ("SPN".equals(this.algoritmoActual)) {
                        if (proceso.getDuracion() < procesoSiguiente.getDuracion() && proceso.getInicio() <= momentoActual) {

                            procesoSiguiente = proceso;
                        }
                    }
                    
                    
                }
                if ("FIFO".equals(this.algoritmoActual) || "SPN".equals(this.algoritmoActual)) {
                    if (momentoActual > procesoSiguiente.getInicio()) {
                        procesoSiguiente.setInicio(momentoActual);
                    }
                    else {
                        momentoActual = procesoSiguiente.getInicio();
                    }
                    momentoActual = momentoActual + procesoSiguiente.getDuracion();
                    listaresultado.add(procesoSiguiente);
                    listaClon.remove(procesoSiguiente);
                }
                
                if ("Round Robin".equals(this.algoritmoActual)) {
                    
                    Proceso procesoParcial;
                    
                    if (procesoSiguiente.getDuracion() > tick){
                        procesoParcial = new Proceso(procesoSiguiente.getNroproceso(), momentoActual, tick, procesoSiguiente.getPrioridad(), procesoSiguiente.getColor());
                        momentoActual = momentoActual + tick;   
                    }
                    else //if (procesoSiguiente.getDuracion() <= tick)
                    {
                        procesoParcial = new Proceso(procesoSiguiente.getNroproceso(), momentoActual, procesoSiguiente.getDuracion(), procesoSiguiente.getPrioridad(), procesoSiguiente.getColor());
                        momentoActual = momentoActual + procesoSiguiente.getDuracion();
                    }
                    listaresultado.add(procesoParcial);
                    listaClon.remove(procesoSiguiente);
                    
                    if (procesoSiguiente.getDuracion() > tick){
                        i--;
                        procesoSiguiente.setDuracion(procesoSiguiente.getDuracion() - tick);
                        procesoSiguiente.setInicio(momentoActual);
                        listaClon.add(procesoSiguiente);
                    }

                }
                
                //JOptionPane.showMessageDialog(null, Integer.toString( listaClon.size() ));
            }
            
            return listaresultado;
    }   

    @Override
    public void focusGained(FocusEvent e) {}

    @Override
    public void focusLost(FocusEvent e) {
        if(!ControladorFunciones.isNumeric(this.vistaPrincipal.txtTicks.getText())){
            this.vistaPrincipal.txtTicks.setText("4");
        }
        this.tick = Integer.parseInt(this.vistaPrincipal.txtTicks.getText());
    }

    private void seteaTablaRendimiento() {
        tablaModeloRendimiento.setRowCount(0);
        tablaModeloRendimiento.addColumn("Nro Proceso");
        tablaModeloRendimiento.addColumn("Inicio");
        tablaModeloRendimiento.addColumn("Duración");
        tablaModeloRendimiento.addColumn("Prioridad");
        tablaModeloRendimiento.addColumn("T");
        tablaModeloRendimiento.addColumn("E");
        tablaModeloRendimiento.addColumn("P");
        tablaModeloRendimiento.addColumn("R");
        this.vistaPrincipal.tablaRendimiento.setEnabled(false);
    }

    private void muestraRendimiento() {
        cargaTablaRendimiento(listaProcesos, tablaModeloRendimiento);
    }

    private void cargaTablaRendimiento(ArrayList<Proceso> lista, DefaultTableModel tabla) {
        tabla.setRowCount(0);
        int contador = 0;
        for(Proceso proceso:lista){
            String nroProceso = Integer.toString(proceso.getNroproceso());
            String inicio = Integer.toString(proceso.getInicio() );
            String duracion = Integer.toString(proceso.getDuracion());
            String prioridad = Integer.toString(proceso.getPrioridad());
            String T = Integer.toString(proceso.getT() );
            String E = Integer.toString(proceso.getE() );
            String P = String.valueOf(proceso.getP());
            String R = String.valueOf(proceso.getR());
            Color color = proceso.getColor();
            String[] fila = {nroProceso, inicio, duracion, prioridad, T, E, P, R};
            tabla.addRow(fila);
            
        }
    }
                    
            
            
    

    
}
