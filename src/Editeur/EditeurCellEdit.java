package Editeur;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import Lecteur.ModeleChoixMultiple;
import Lecteur.ModeleMultipleReponse;
import Lecteur.ModeleQuestion;
import Lecteur.ModeleVraiFaux;


public class EditeurCellEdit extends AbstractCellEditor implements TableCellEditor{

	private JButton bouton;
	private ModeleEditeur modele;

    public EditeurCellEdit(ModeleEditeur modele) {
        super();
        this.modele = modele;
        bouton = new JButton("Éditer");
        bouton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
				
			}
		});
 
    }
 
    @Override
    public Object getCellEditorValue() {
        return "";
    }
    
    private void openEditor(int indexModele) {
    	if(!modele.isEditeurQuestionOuvert()) {
    	ModeleQuestion modelePress = modele.getQuestion(indexModele);
    	if(modelePress instanceof ModeleVraiFaux) {
    		EditeurVraiFaux editeur = new EditeurVraiFaux(modele, indexModele);
    	}else if(modelePress instanceof ModeleChoixMultiple) {
    		EditeurQuestionMultiple editeur = new EditeurQuestionMultiple(modele, indexModele, false);
    	}else if(modelePress instanceof ModeleMultipleReponse) {
    		EditeurQuestionMultiple editeur = new EditeurQuestionMultiple(modele, indexModele, true);
    	}
    	modele.setEditeurQuestionOuvert(true);
    	}
    }
 
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    	openEditor(table.convertRowIndexToModel(row));
        return bouton;
    }
}
