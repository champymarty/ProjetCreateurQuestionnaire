package Editeur;


public class Reponse implements Comparable {
	
	private int numero;
	private String affichage;
	private boolean bonneReponse;
	
	public Reponse(int numero, String affichage, boolean bonneReponse) {
		this.numero=numero;
		this.affichage = affichage;
		this.bonneReponse = bonneReponse;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public String getAffichage() {
		return affichage;
	}
	
	public boolean isBonneReponse() {
		return bonneReponse;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public void setAffichage(String affichage) {
		this.affichage = affichage;
	}
	
	public void setBonneReponse(boolean bonneReponse) {
		this.bonneReponse = bonneReponse;
	}

	@Override
	public int compareTo(Object o) {
		Reponse reponse = (Reponse)o;
		return new Integer(numero).compareTo(new Integer(reponse.getNumero()));
	}

}
