package Editeur;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

import javax.swing.JLabel;

public class TextRenderer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4454347191031322541L;
	
	private Font font;
	private Color color;
	private boolean defaultFont;
	
	public TextRenderer(Font font, Color color) {
		this.font = font;
		this.color = color;
		verifierSiDefaultFont(font);
	}
	
	public TextRenderer() {
		font = new JLabel().getFont();
		color = Color.BLACK;
		defaultFont = false;
	}
	
	public TextRenderer(TextRenderer renderer) {
		this.font = renderer.getFont();
		this.color = renderer.getColor();
		verifierSiDefaultFont(font);
	}
	
	private void verifierSiDefaultFont(Font font) {
		if(this.font.equals(font)) {
			defaultFont = true;
		}else {
			defaultFont = false;
		}
	}
	
	public Color getColor() {
		return color;
	}
	
	public Font getFont() {
		return font;
	}
	
	public void setFont(Font font) {
		verifierSiDefaultFont(font);
		this.font = font;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public boolean isDefaultFont() {
		return defaultFont;
	}

}
