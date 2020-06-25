package Lecteur;

import javax.swing.JCheckBox;

public class CheckBoxAvecIndex extends JCheckBox {
	
	private int index;
	
	public CheckBoxAvecIndex(String texte, int index) {
		super(texte);
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}

}
