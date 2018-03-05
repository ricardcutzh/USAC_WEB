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
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
public class ErrorModel implements TableModel{
    ArrayList<TError> errores;
    public ErrorModel(ArrayList<TError> errores) 
    {
        this.errores = errores;
    }
    
    @Override
    public int getRowCount() {
        return this.errores.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int columnIndex) {
        String titulo ="";
        switch(columnIndex)
        {
            case 0:
            {
                titulo = "Ruta de Archivo";
                break;
            }
            case 1:
            {
                titulo = "Lexema";
                break;
            }
            case 2:
            {
                titulo = "Tipo de Error";
                break;
            }
            case 3:
            {
                titulo = "Descripcion";
                break;
            }
            case 4:
            {
                titulo = "Linea";
                break;
            }
            case 5:
            {
                titulo = "Columna";
                break;
            }
        }
        return titulo;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex == 3 || columnIndex==4)
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
        TError error = this.errores.get(rowIndex);
        switch(columnIndex)
        {
            case 0:
            {
                return error.archivo;
            }
            case 1:
            {
                return error.lexema;
            }
            case 2:
            {
                return error.tipo;
            }
            case 3:
            {
                return error.descripcion;
            }
            case 4:
            {
                return error.line;
            }
            case 5:
            {
                return error.column;
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
