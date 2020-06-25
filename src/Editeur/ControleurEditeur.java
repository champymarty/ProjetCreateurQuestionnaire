package Editeur;

import gestionnaire.ModeleGestionnaire;

public class ControleurEditeur {
	
	private FenetreEditeur fenetre;
	private ModeleEditeur modele;
	private ModeleGestionnaire modeleGestionnaire;
	
	private int posQuestionnaire;
	
	public ControleurEditeur(ModeleGestionnaire modeleGestionnaire, int posQuestionnaire) {
		this.posQuestionnaire = posQuestionnaire;
		this.modeleGestionnaire = modeleGestionnaire;
		modele = new ModeleEditeur(modeleGestionnaire, posQuestionnaire);
		fenetre = new FenetreEditeur(modele);
	}

}
