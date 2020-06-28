package Lecteur;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Editeur.Reponse;

public class ModeleChoixMultiple extends ModeleQuestion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7665737088406962171L;
	private ArrayList<Reponse> choix = new ArrayList<>();
	private boolean estImage;
	private int indexReponse, indexActuel = -1;

	public ModeleChoixMultiple(int numeroQuestion, String question, String messageReussite, String messageFail,
			int nbEssait, ArrayList<Reponse> choix, int indexReponse, boolean estImage) {
		super(numeroQuestion, question, messageReussite, messageFail, nbEssait);
		this.choix = choix;
		this.indexReponse = indexReponse;
		this.estImage = estImage;
	}
	public int getIndexReponse() {
		return indexReponse;
	}
	
	public void choixSelectionerUpdate(int newIndex) {
		indexActuel = newIndex;
	}
	
	public int getIndexActuel() {
		return indexActuel;
	}
	
	public boolean isImage() {
		return estImage;
	}
	
	public int getNbChoix() {
		return choix.size();
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
		return (indexActuel == indexReponse);
	}
	@Override
	public String getQuestionType() {
		return "Question choix multiple";
	}
	@Override
	public boolean isReponseImage() {
		return estImage;
	}
	
	public void setChoix(ArrayList<Reponse> choix) {
		this.choix = choix;
	}
	
	public void setEstImage(boolean estImage) {
		this.estImage = estImage;
	}
	
	public void setIndexReponse(int indexReponse) {
		this.indexReponse = indexReponse;
	}
	
	@Override
	public JPanel generateAffichage() {
		return new PaneauChoixMultiple(this);
	}

}
