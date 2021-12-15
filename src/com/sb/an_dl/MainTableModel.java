package com.sb.an_dl;

import java.util.List;
import javax.swing.table.AbstractTableModel;
 
import com.sb.an_dl.Anime;
 
public class MainTableModel extends AbstractTableModel
{
    private final List<Anime> animeList;
     
    private final String[] columnNames = new String[] {""
    };
    private final Class[] columnClass = new Class[] {
        Integer.class, String.class, Double.class, Boolean.class
    };
 
    public MainTableModel(List<Anime> animeList)
    {
    	Utils.l("MainTableModel" ,"updated", true);
        this.animeList = animeList;
        this.fireTableDataChanged();
    }
     
    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }
 
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        //return columnClass[columnIndex];
    	return Anime.class;
    }
 
    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }
 
    @Override
    public int getRowCount()
    {
        return animeList.size();
    }
 
    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Anime row = animeList.get(rowIndex);
        return row;
    	/*Employee row = employeeList.get(rowIndex);
        if(0 == columnIndex) {
            return row.getId();
        }
        else if(1 == columnIndex) {
            return row.getName();
        }
        else if(2 == columnIndex) {
            return row.getHourlyRate();
        }
        else if(3 == columnIndex) {
            return row.isPartTime();
        }
        return null;*/
    }
}
