package Lecteur;

import java.io.Serializable;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ModeleVraiFaux extends ModeleQuestion implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7668907741038359986L;
	private boolean reponse, reponseActuel;

	public ModeleVraiFaux(int ordreAffichage, String question, String messageReussite, String messageFail, boolean reponse, int nbEssait) {
		super(ordreAffichage, question, messageReussite, messageFail, nbEssait);
		this.reponse = reponse;
	}
	
	@Override
	public JPanel generateAffichage() {
		return new PaneauVraiFaux(this);
	}
	
	public void reponseChanger(boolean newReponse) {
		reponseActuel = newReponse;
	}
	
	public boolean getReponse() {
		return reponse;
	}
	
	@Override
	public boolean reponseValide() {
		return (reponseActuel == reponse);
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
	public String getQuestionType() {
		return "Question Vrai ou Faux";
	}

	@Override
	public boolean isReponseImage() {
		return false;
	}
	
	public void setReponse(boolean reponse) {
		this.reponse = reponse;
	}
}
