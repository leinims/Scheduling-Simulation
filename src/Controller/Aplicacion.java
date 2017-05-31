/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Proceso;
import View.VistaPrincipal;
import java.util.ArrayList;

/**
 *
 * @author CLMS
 */
public class Aplicacion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Proceso> listaProceso = new ArrayList<Proceso>();
        VistaPrincipal vistaPrincipal = new VistaPrincipal();
        ControladorPrincipal controladorPrincipal = new ControladorPrincipal(listaProceso, vistaPrincipal);
        
        // TODO code application logic here
    }
    
}
