package Logic;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MyCustomRenderer extends DefaultTableCellRenderer{
	private static final long serialVersionUID = 1L;
	
	public MyCustomRenderer(){
		setOpaque(true);
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col){
		 Component comp = super.getTableCellRendererComponent(table,  value, isSelected, hasFocus, row, col);
	     String s =  table.getModel().getValueAt(row, 6).toString();
	     if(s.equals("Y")){
	         comp.setForeground(Color.GRAY);
	     }
	     else{
	         comp.setForeground(null);
	     }
	     return(comp);
	 }
}
