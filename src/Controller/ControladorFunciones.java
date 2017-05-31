/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.awt.Color;
import java.util.Random;

/**
 *
 * @author CLMS
 */
public class ControladorFunciones {
    public static boolean isNumeric(String cadena)
    {
      return cadena.matches("-?\\d+(\\.\\d+)?");
    }
    public static Color colorAleatorio(){
        Random rnd = new Random();
        float rojo = rnd.nextFloat() ;
        float verde = rnd.nextFloat() ;
        float azul = rnd.nextFloat() ;
        Color color = new Color(rojo, verde, azul); // La sobrecarga del Color me permite usar numeros de 0 a 1 -- el Constructor al recibir el float lo multiplica por 255 
        return color;
        
    }
}
