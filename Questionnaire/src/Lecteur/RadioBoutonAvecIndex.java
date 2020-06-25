package Lecteur;

import javax.swing.JRadioButton;

public class RadioBoutonAvecIndex extends JRadioButton {
	
	private int index;
	
	public RadioBoutonAvecIndex(int index) {
		super();
		this.index = index;
	}
	public int getIndex() {
		return index;
	}

}
