package Lecteur;

import java.io.Serializable;

import javax.swing.JPanel;

public class Affichable implements Serializable, Comparable {
	
	protected int ordrePassage;
	
	
	public Affichable( int ordrePassage) {
		this.ordrePassage = ordrePassage;
	}
	
	public JPanel generateAffichage() {
		return null;
	}

	public int getOrdrePassage() {
		return ordrePassage;
	}
	
	public String getQuestionType() {
		return "Affichable";
	}
	
	public void setOrdrePassage(int ordrePassage) {
		this.ordrePassage = ordrePassage;
	}
	
	@Override
	public int compareTo(Object o) {
		Affichable modele = (Affichable) o;
		return new Integer(ordrePassage).compareTo(new Integer(modele.getOrdrePassage()));
	}

}
