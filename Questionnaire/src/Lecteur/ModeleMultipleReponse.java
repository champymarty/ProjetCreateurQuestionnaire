package Lecteur;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ModeleMultipleReponse extends ModeleQuestion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5982108056365527000L;
	private ArrayList<String> choix = new ArrayList<>();
	private ArrayList<Integer> indexSelectionnes = new ArrayList<>();
	private ArrayList<Integer> indexReponses;
	
	private boolean estImage;
	
	public ModeleMultipleReponse(int numeroQuestion, String question, String messageReussite, String messageFail,
			int nbEssait, ArrayList<String> choix,ArrayList<Integer> indexReponse, boolean estImage) {
		super(numeroQuestion, question, messageReussite, messageFail, nbEssait);
		this.choix = choix;
		this.indexReponses = indexReponse;
		this.estImage = estImage;

	}
	
	public void updateSelectionne(ArrayList<Integer> selectionne) {
		indexSelectionnes = selectionne;
	}
	
	public boolean estImage() {
		return estImage;
	}
	
	public int getNbChoix() {
		return choix.size();
	}
	
	public ArrayList<String> getChoix(){
		return choix;
	}
	
	public String getChoix(int index) {
		if( index < choix.size()) {
			return choix.get(index);
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
			if(indexSelectionnes.get(i) != indexReponses.get(i)) {
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
