package gestionnaire;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import Lecteur.Affichable;
import Lecteur.ModeleQuestion;
import Lecteur.ModeleVraiFaux;

public class Questionnaire implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5137858605048880917L;
	private String titre;
	private ArrayList<Affichable> modeles = new ArrayList<>();
	private int posModele;
	
	public Questionnaire(String titre, int posModele) {
		this.titre = titre;
		this.posModele = posModele;
	}
	public void ajouterQuestion(ModeleQuestion mod) {
		modeles.add(mod);
	}
	
	public int getPosModele() {
		return posModele;
	}
	
	public void setPosModele(int posModele) {
		this.posModele = posModele;
	}
	
	public String getTitre() {
		return titre;
	}
	
	public ArrayList<Affichable> getModeles() {
		return modeles;
	}
	
	public String toString() { 
		if(modeles.isEmpty()) {
			return titre + " ( questionnaires vides )";
		}
        return titre;
    }
	
	public void addAffichable(Affichable modele) {
		modeles.add(modele);
	}

	public int getNombreQuestion() {
		return modeles.size();
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
}
