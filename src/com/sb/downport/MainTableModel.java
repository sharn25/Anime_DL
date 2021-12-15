package com.sb.downport;

import javax.swing.Icon;
import javax.swing.table.AbstractTableModel;

import com.sb.an_dl.Utils;

public class MainTableModel extends AbstractTableModel {
   DownloadList list = null;
   Icon q;
   private static final long serialVersionUID = -8936395745120671317L;
   final String[] cols = new String[]{""};

   public void setList(DownloadList list) {
      this.list = list;
      this.fireTableDataChanged();
   }

   void setType(String type) {
      this.list.setType(type);
   }

   public Class getColumnClass(int col) {
      return DownloadListItem.class;
   }

   public String getColumnName(int col) {
	   Utils.l("MainTableModel",cols[col]);
      return this.cols[col];
      
   }

   public int getColumnCount() {
	   Utils.l("MainTableModel",cols.length);
      return this.cols.length;
   }

   public int getRowCount() {
      return this.list == null?0:this.list.size();
   }

   public Object getValueAt(int row, int col) {
      DownloadListItem item = this.list.get(row);
      return item;
   }

   public void updateItem(DownloadInfo info) {
	   Utils.l("MainTableModel",cols.length);
      DownloadListItem item = this.list.getByID(info.id);
      if(item != null) {
         item.updateData(info);
         int index = this.list.getIndex(item);
         if(index >= 0) {
            this.fireTableRowsUpdated(index, index);
         }
      }
   }

   String getString(String id) {
      return StringResource.getString(id);
   }
}
