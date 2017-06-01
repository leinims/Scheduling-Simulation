/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CLMS
 */
public class TablaModelo extends DefaultTableModel{
    @Override
    public boolean isCellEditable(int fila, int columna)
    {
        
        return columna == 1 || columna == 2 || columna == 3;
    }
   
    @Override
    public void setValueAt(Object Value, int fila, int columna)
    {
        String valor = (String) Value;
        if (ControladorFunciones.isNumeric((String) Value))
        {   
            if((columna == 2 && (Integer.parseInt(valor) > 0)) || columna != 2){
            super.setValueAt(Value, fila, columna);
            }
        
        }
        
        
    }
    
}
