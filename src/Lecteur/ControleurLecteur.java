package Lecteur;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gestionnaire.Questionnaire;

public class ControleurLecteur {

	private FenetrePrincipal app;
	private Questionnaire questionnaire;
	private ArrayList<JPanel> panneauQuestions = new ArrayList<>();
	
	private int indexActuel = 0, nbQuestionReussi = 0, nbQuestion = 0;
	
	public static int LONGEUR = 800;
	public static int HAUTEUR = 600;
	
	public ControleurLecteur(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
		chargerQuestions();
		if(questionnaire.getNombreQuestion() > 0) {
			app = new FenetrePrincipal(panneauQuestions, questionnaire.getModeles().get(0), this);
		}
	}
	
	private void chargerQuestions() {
		for(Affichable modele : questionnaire.getModeles()) {
			if(modele instanceof ModeleQuestion) {
				((ModeleQuestion) modele).setNumeroQuestion(++nbQuestion);
			}
			panneauQuestions.add(modele.generateAffichage());
		}
	}
	
	public void prochaineQuestion(boolean questionPrecedenteReussite) {
		if(questionPrecedenteReussite && questionnaire.getModeles().get(indexActuel) instanceof ModeleQuestion) {
			nbQuestionReussi++;
		}
		indexActuel++;
		if(indexActuel >= panneauQuestions.size()) {
			JOptionPane.showMessageDialog(app, "Vous avez réussi " + nbQuestionReussi + " sur "+nbQuestion+ " "
					+ ". Bravo !!!!!!!!!! :D");
			app.dispose();
		}else {
			app.setQuestion(indexActuel, questionnaire.getModeles().get(indexActuel));
		}
	}
	
}
