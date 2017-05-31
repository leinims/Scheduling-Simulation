/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Proceso;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import static javafx.scene.text.Font.font;
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
         
         String tick;
         
         Font font = new Font("Arial", 0, 15);
         grafico.setFont(font);
         proporcionx = tamAncho/(duracionTotal + 2);
         proporciony = tamLargo/(listaProcesos.size() + 2);


        // Se crea la línea de Tiempo
         
         grafico.setColor(Color.BLACK);
         grafico.drawLine(minAncho, minLargo, maxAncho, minLargo);
         for (int i = 0; i <= duracionTotal; i++) {
             
             grafico.drawLine((int) (proporcionx * i + minAncho) , minLargo, (int) (proporcionx * i + minAncho), (int) proporciony );
             tick = Integer.toString(i);
             
             grafico.drawString(tick, (int) (proporcionx * i + minAncho + 5), minLargo + 15 );
         }
         listaProcesos.forEach((proceso) -> {
             int duracion = proceso.getDuracion();
             int nroProceso = proceso.getNroproceso();
             int inicio = proceso.getInicio();
             int prioridad = proceso.getPrioridad();
             grafico.setColor(proceso.getColor());
             grafico.fillRect((int)(minAncho + proporcionx * inicio) , (int)(minLargo + proporciony * nroProceso), (int)(proporcionx * duracion), (int) proporciony);
             String infProceso = " #" + Integer.toString(nroProceso);
             //grafico.setColor(Color.BLACK);
             //grafico.drawString(infProceso, (int)(minAncho + proporcionx *( inicio + duracion)) + 10, (int)(minLargo + proporciony * nroProceso + proporciony/1));
        });
         
     }
     
}
