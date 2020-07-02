package Editeur;

import Lecteur.Affichable;
import java.awt.Font;
import java.util.ArrayList;


public class ModeleAvecTextEditable extends Affichable{
	
	private ArrayList<Font> fonts = new ArrayList<>();

	public ModeleAvecTextEditable(int ordrePassage) {
		super(ordrePassage);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1163636304992425248L;
	
	public Font getFont(int index){
		return fonts.get(index);
	}
	
	public void addFont(Font font) {
		fonts.add(font);
	}
	
	public int getSize() {
		return fonts.size();
	}

}
