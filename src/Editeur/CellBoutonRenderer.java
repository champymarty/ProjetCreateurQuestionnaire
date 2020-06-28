package Editeur;

import java.awt.Component;


import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


	public class CellBoutonRenderer extends DefaultTableCellRenderer {
		
		private JButton btn;
		
		public CellBoutonRenderer() {
		}
	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	 
	        btn = new JButton("Éditer");
	        return btn;
	    }
	}
