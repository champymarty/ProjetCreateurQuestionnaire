package Lecteur;

import Editeur.TextRenderer;

public class ModelePageTexte extends Affichable {
	
	private String titre, text;
	private TextRenderer rendererTitre, rendererText; 

	public ModelePageTexte(int ordrePassage) {
		super(ordrePassage);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -9110868391670881287L;
	
	public void setTitre(String titre) {
		this.titre = titre;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setRendererText(TextRenderer rendererText) {
		this.rendererText = rendererText;
	}
	
	public void setRendererTitre(TextRenderer rendererTitre) {
		this.rendererTitre = rendererTitre;
	}
	
	@Override
	public String getQuestionType() {
		// TODO Auto-generated method stub
		return "Page de texte";
	}
	
	public TextRenderer getRendererText() {
		return rendererText;
	}
	
	public TextRenderer getRendererTitre() {
		return rendererTitre;
	}
	
	public String getTitre() {
		return titre;
	}
	
	public String getText() {
		return text;
	}

}
