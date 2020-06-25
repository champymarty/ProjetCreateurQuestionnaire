package Lecteur;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.JPanel;

public abstract class ModeleQuestion extends Affichable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8327326666543902958L;
	private int numeroQuestion;
	private String question;

	protected String messageReussite;
	protected String messageFail;

	protected int nbEssait;
	
	public ModeleQuestion(int ordrePassage, String question,String messageReussite, String messageFail, int nbEssait) {
		super(ordrePassage);
		this.question = question;
		this.messageReussite = messageReussite;
		this.messageFail = messageFail;
		this.nbEssait = nbEssait;
	}

	public int getNbEssait() {
		return nbEssait;
	}
	
	public int getNumeroQuestion() {
		return numeroQuestion;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public String getMessageFail() {
		return messageFail;
	}
	
	public String getMessageReussite() {
		return messageReussite;
	}
	
	public void setNumeroQuestion(int numeroQuestion) {
		this.numeroQuestion = numeroQuestion;
	}
	
	public void setNbEssait(int nbEssait) {
		this.nbEssait = nbEssait;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public void setMessageFail(String messageFail) {
		this.messageFail = messageFail;
	}
	
	public void setMessageReussite(String messageReussite) {
		this.messageReussite = messageReussite;
	}
	
    public Image scaleImage(Image image, int largeur, int hauteur) {

        return image.getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
    }
    
	public abstract void afficherValidation();
	
	public abstract boolean reponseValide();
	
	public abstract boolean isReponseImage();
	
}