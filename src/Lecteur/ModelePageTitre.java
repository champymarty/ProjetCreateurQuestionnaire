package Lecteur;

import java.awt.Font;
import java.io.Serializable;

import javax.swing.JPanel;

import Editeur.TextRenderer;

public class ModelePageTitre extends Affichable implements Serializable {
	
	private TextRenderer txtRendererTitre, txtRendererSousTitre;
	
	private String titre, sousTitre;

	public ModelePageTitre(int ordrePassage, TextRenderer fontTitre, TextRenderer fontSousTitre, String titre, String sousTitre) {
		super(ordrePassage);
		this.txtRendererTitre = fontTitre;
		this.txtRendererSousTitre = fontSousTitre;
		this.titre = titre;
		this.sousTitre = sousTitre;
	}
	
	public ModelePageTitre(int ordrePassage) {
		super(ordrePassage);
		txtRendererTitre = new TextRenderer();
		txtRendererSousTitre = new TextRenderer();
		titre = "";
		sousTitre = "";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4719216662753507453L;
	
	@Override
	public JPanel generateAffichage() {
		return new JPanel();    //TODO
	}
	
	public TextRenderer getTxtRendererTitre() {
		return txtRendererTitre;
	}
	public TextRenderer getTxtRendererSousTitre() {
		return txtRendererSousTitre;
	}
	
	public Font getFontTitre() {
		return txtRendererTitre.getFont();
	}
	
	public Font getFontSousTitre() {
		return txtRendererSousTitre.getFont();
	}
	public String getTitre() {
		return titre;
	}
	public String getSousTitre() {
		return sousTitre;
	}
	public void setTxtRendererTitre(TextRenderer txtRendererTitre) {
		this.txtRendererTitre = txtRendererTitre;
	}
	public void setTxtRendererSousTitre(TextRenderer txtRendererSousTitre) {
		this.txtRendererSousTitre = txtRendererSousTitre;
	}
	
	public void setFontTitre(Font font) {
		txtRendererTitre.setFont(font);
	}
	
	public void setFontSousTitre(Font font) {
		txtRendererSousTitre.setFont(font);
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public void setSousTitre(String sousTitre) {
		this.sousTitre = sousTitre;
	}

}
