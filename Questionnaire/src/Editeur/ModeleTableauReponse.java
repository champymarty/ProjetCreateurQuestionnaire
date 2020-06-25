package Editeur;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.table.AbstractTableModel;

public class ModeleTableauReponse extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3151810235970851326L;
	
	private final String[] entetes = {"Numero reponse", "Reponse", "Bonne reponse ?"};
	private ArrayList<Reponse> reponses = new ArrayList<>();
	
	private int posBonneReponse = -1;

	
	public ModeleTableauReponse(ArrayList<String> choix) {
		
	}
	
	public ModeleTableauReponse() {

	}
	
	public void ajouterReponse(String text, int numero, boolean bonneReponse) {
		reponses.add(new Reponse(numero + 1, text, bonneReponse));
		Collections.sort(reponses);
		fireTableDataChanged();
	}
	
	private void enleverTrouEntreNumeroReponse() {
		for(int i = 0 ; i < reponses.size(); i++) {
			reponses.get(i).setNumero(i + 1);
		}
	}
	public void enleverReponse(int index) {
		reponses.remove(index);
		enleverTrouEntreNumeroReponse();
		fireTableDataChanged();
	}
    @Override
    public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }
    
	@Override
	public int getRowCount() {
		return reponses.size();
	}

	@Override
	public int getColumnCount() {
		return entetes.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return reponses.get(rowIndex).getNumero();
		case 1:
			return reponses.get(rowIndex).getAffichage();
		case 2:
			return reponses.get(rowIndex).isBonneReponse();
		default:
			throw new IllegalArgumentException("Unexpected value: " + columnIndex);
		}
	}
	
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
    	return true;
    }
    
    @Override
    public Class getColumnClass(int columnIndex){
    	switch(columnIndex){
    		case 0:
    			return Integer.class;
    		case 1:
    			return String.class;
    		case 2: 
    			return Boolean.class;
    		default:
    			return Object.class;
    	}
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    	switch (columnIndex) {
		case 0:
			int newNumero = (int) aValue;
			for(Reponse rep : reponses) {
				if(rep.getNumero() == newNumero) {
					rep.setNumero(reponses.get(rowIndex).getNumero());
					break;
				}
			}
			reponses.get(rowIndex).setNumero(newNumero);
			if(rowIndex == posBonneReponse) {
				posBonneReponse = newNumero - 1;
			}
			Collections.sort(reponses);
			enleverTrouEntreNumeroReponse();
			fireTableDataChanged();
			break;
		case 1:
			String reponse = (String)aValue;
			reponses.get(rowIndex).setAffichage(reponse);
			fireTableRowsUpdated(rowIndex, rowIndex);
			break;
		case 2:
			if(posBonneReponse == -1) {
				posBonneReponse = rowIndex;
				reponses.get(posBonneReponse).setBonneReponse(true);
				fireTableRowsUpdated(rowIndex, rowIndex);
			}else if(posBonneReponse == rowIndex){
				//aucune update
			}else {
				reponses.get(posBonneReponse).setBonneReponse(false);
				fireTableRowsUpdated(posBonneReponse, posBonneReponse);
				posBonneReponse = rowIndex;
				reponses.get(posBonneReponse).setBonneReponse(true);
				fireTableRowsUpdated(posBonneReponse, posBonneReponse);
			}
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + rowIndex);
		}
    }

}
