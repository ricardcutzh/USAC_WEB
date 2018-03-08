/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;

/**
 *
 * @author richard
 */
import CJS_EXEC.NodoImprimir;
import java.util.ArrayList;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
public class PrintModel implements TableModel{
    ArrayList<NodoImprimir> impresion;
    public PrintModel(ArrayList<NodoImprimir> impresion)
    {
        this.impresion = impresion;
    }
    
    @Override
    public int getRowCount() {
        return this.impresion.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        String titulo = "";
        switch(columnIndex)
        {
            case 0:
            {
                titulo = "Archivo";
                break;
            }
            case 1:
            {
                titulo = "Linea";
                break;
            }
            case 2:
            {
                titulo = "Columna";
                break;
            }
            case 3:
            {
                titulo = "Salida";
                break;  
            }
        }
        return titulo;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex == 1 || columnIndex == 2)
        {
            return Integer.class;
        }
        else
        {
            return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        NodoImprimir nodo = this.impresion.get(rowIndex);
        switch(columnIndex)
        {
            case 0:
            {
                return nodo.getArchivo();
            }
            case 1:
            {
                return nodo.getLinea();
            }
            case 2:
            {
                return nodo.getColumna();
            }
            case 3:
            {
                return nodo.getMensaje();
            }
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        
    }
    
}
