package Editeur;

import java.awt.Component;


import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


	public class EditerCellRenderer extends DefaultTableCellRenderer {
		
		private JButton btn;
		
		public EditerCellRenderer() {
		}
	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	 
	        btn = new JButton("Éditer");
	        return btn;
	    }
	}
