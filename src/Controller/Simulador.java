/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Proceso;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author CLMS
 */
public class Simulador {
    private JPanel Lienzo;
    private Graphics grafico;
    private int maxLargo, maxAncho, minLargo, minAncho, tamAncho, tamLargo ; //Establezco márgenes
    private float proporcionx, proporciony;

    
    public Simulador(JPanel Lienzo) {
        this.Lienzo = Lienzo;
        minLargo = (int) Lienzo.getHeight() / 20;
        tamLargo = Lienzo.getHeight() - 2 * minLargo;
        maxLargo = Lienzo.getHeight() - minLargo;
        minAncho = (int) Lienzo.getWidth() / 20;
        tamAncho = Lienzo.getWidth() - 2 * minAncho;
        maxAncho = Lienzo.getWidth() - minAncho;
        grafico = this.Lienzo.getGraphics();
        proporcionx = 1;
        proporciony = 1;
        iniciaLienzo();
        //graficaPlantilla();
        
    }
     private void iniciaLienzo() {
         
        grafico.setColor(Color.WHITE);
        //grafico.fillRect(minAncho, minLargo, maxAncho, maxLargo);
        grafico.fillRect(minAncho, minLargo, tamAncho, tamLargo);
        Lienzo.paintComponents(grafico);
        
        //JOptionPane.showMessageDialog(null, "Prueba");
        
        
    }
     public void simulaProcesos(ArrayList<Proceso> listaProcesos, int duracionTotal) {
         
         proporcionx = tamAncho/duracionTotal;
         proporciony = tamLargo/listaProcesos.size();


        // Se crea la línea de Tiempo
         
         grafico.setColor(Color.BLACK);
         grafico.drawLine(minAncho, minLargo, maxAncho, minLargo);
         
         
     }
     
}
