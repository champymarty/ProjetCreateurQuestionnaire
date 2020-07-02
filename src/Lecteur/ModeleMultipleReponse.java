package Lecteur;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Editeur.Reponse;

public class ModeleMultipleReponse extends ModeleQuestion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5982108056365527000L;
	private ArrayList<Reponse> choix = new ArrayList<>();
	private ArrayList<Integer> indexSelectionnes = new ArrayList<>();
	private ArrayList<Integer> indexReponses;
	
	private boolean estImage;
	
	public ModeleMultipleReponse(int ordrePassage, String question, String messageReussite, String messageFail,
			int nbEssait, ArrayList<Reponse> choix,ArrayList<Integer> indexReponse, boolean estImage) {
		super(ordrePassage, question, messageReussite, messageFail, nbEssait);
		this.choix = choix;
		this.indexReponses = indexReponse;
		this.estImage = estImage;

	}
	
	public void updateSelectionne(ArrayList<Integer> selectionne) {
		indexSelectionnes = selectionne;
	}
	
	
	public ArrayList<Integer> getIndexReponses() {
		return indexReponses;
	}
	
	public boolean estImage() {
		return estImage;
	}
	
	public int getNbChoix() {
		return choix.size();
	}
	
	public void setChoix(ArrayList<Reponse> choix) {
		this.choix = choix;
	}
	
	public void setEstImage(boolean estImage) {
		this.estImage = estImage;
	}
	
	public void setIndexReponses(ArrayList<Integer> indexReponses) {
		this.indexReponses = indexReponses;
	}
	
	public void setIndexSelectionnes(ArrayList<Integer> indexSelectionnes) {
		this.indexSelectionnes = indexSelectionnes;
	}
	
	public ArrayList<Reponse> getChoix(){
		return choix;
	}
	
	public String getChoix(int index) {
		if( index < choix.size()) {
			return choix.get(index).getAffichage();
		}
		return null;
	}
	
	@Override
	public void afficherValidation() {
		if(reponseValide()) {
			JOptionPane.showMessageDialog(null, messageReussite, "Résultat réponse", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(null, messageFail, "Résultat réponse", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	

	@Override
	public boolean reponseValide() {
		if(indexSelectionnes.size() != indexReponses.size()) {
			return false;
		}
		for(int i = 0; i < indexSelectionnes.size(); i++) {
			System.out.print(indexSelectionnes.get(i) + " =? " + indexReponses.get(i));
			if(indexSelectionnes.get(i).intValue() != indexReponses.get(i).intValue() ) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String getQuestionType() {
		return "Question avec plusieurs réponses";
	}

	@Override
	public boolean isReponseImage() {
		return estImage;
	}
	
	@Override
	public JPanel generateAffichage() {
		return new PaneauMultipleReponse(this);
	}

}
