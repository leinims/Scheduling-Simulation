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
        // make read only fields except column 0,13,14
        return columna == 1 || columna == 2 || columna == 3;
    }
   
    @Override
    public void setValueAt(Object Value, int fila, int columna)
    {
        if (ControladorFunciones.isNumeric((String) Value))
        {
            super.setValueAt(Value, fila, columna);
        }
        
    }
    
}
