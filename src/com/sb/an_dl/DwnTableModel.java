package com.sb.an_dl;

import javax.swing.Icon;
import javax.swing.table.AbstractTableModel;

import com.sb.downport.DownloadInfo;
import com.sb.downport.DownloadList;
import com.sb.downport.DownloadListItem;
import com.sb.downport.StringResource;

public class DwnTableModel extends AbstractTableModel {
   DownloadList list = null;
   Icon q;
   private static final long serialVersionUID = -8936395745120671317L;
   final String[] cols = new String[]{""};

   public void setList(DownloadList list) {
      this.list = list;
      this.fireTableDataChanged();
   }
   public void updatelist(DownloadList list) {
	   this.list = list;
	   
   }

   void setType(String type) {
      this.list.setType(type);
   }

   public Class getColumnClass(int col) {
      return DownloadListItem.class;
   }

   public String getColumnName(int col) {
	   Utils.l("DwnTbleModel", cols[col], false);
      return this.cols[col];
      
   }

   public int getColumnCount() {
	   Utils.l("DwnTbleModel", Integer.toString(cols.length), false);
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
	   Utils.l("DwnTbleModel", cols.length+ " item updated", false);
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
