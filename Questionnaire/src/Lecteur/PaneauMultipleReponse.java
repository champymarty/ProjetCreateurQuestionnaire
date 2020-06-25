package Lecteur;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PaneauMultipleReponse extends JPanel {
	
	private ModeleMultipleReponse modele;
	
	private ArrayList<CheckBoxAvecIndex> boutons = new ArrayList<>();
	
	public PaneauMultipleReponse(ModeleMultipleReponse modele) {
		super();
		this.modele = modele;
		creerCheckBox();
		creerInterface();
	}
	
	private void creerCheckBox() {
		for(int i = 0; i < modele.getNbChoix(); i++ ) {
			CheckBoxAvecIndex box;
			if(modele.estImage()) {
				box = new CheckBoxAvecIndex("", i);
			}else {
				box = new CheckBoxAvecIndex(modele.getChoix(i), i);
			}
			box.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					envoyerSelectionnerModele();
				}
			});
			boutons.add(box);
		}
	}
	
	private void envoyerSelectionnerModele() {
		ArrayList<Integer> indexSelectionne = new ArrayList<>();
		for(CheckBoxAvecIndex box : boutons) {
			if(box.isSelected()) {
				indexSelectionne.add(box.getIndex());
			}
		}
		modele.updateSelectionne(indexSelectionne);
	}
	
	private void creerInterface() {
		setLayout(new BorderLayout());
		if(modele.estImage()) {
			interfaceImage();
		}else {
			interfaceString();
		}
	}
	
	private void interfaceImage() {
		setLayout(new BorderLayout());
		JPanel pnlTitre = new JPanel();
		pnlTitre.add(new JLabel(modele.getQuestion()));
		add(pnlTitre, BorderLayout.NORTH);
		
		JPanel pnlChoix = new JPanel();
		int nbLigne, nbColonne;
		
		if(modele.getNbChoix() <= 2) {
			nbLigne = 1;
			nbColonne =2;
		}else if(modele.getNbChoix() <= 4){
			nbLigne = 2;
			nbColonne = 2;	
		} else if(modele.getNbChoix()  <= 6) {
			nbLigne = 2;
			nbColonne = 3;	
		}else {
			nbLigne = ( modele.getNbChoix() / 3 ) + 1;
			nbColonne = 3;	
		}
		pnlChoix.setLayout(new GridLayout(nbLigne,nbColonne));
		for(int i = 0; i < modele.getNbChoix(); i++) {
			JPanel pnl = new JPanel();
			pnl.add(boutons.get(i));
				ImageIcon originalIcon = new ImageIcon(modele.getChoix(i));
				
		        int width = ControleurLecteur.LONGEUR / nbColonne;
		        int height = ControleurLecteur.HAUTEUR / nbLigne;

		        Image scaled = modele.scaleImage(originalIcon.getImage(), width, height);

		        ImageIcon scaledIcon = new ImageIcon(scaled);

		        pnl.add(new JLabel(scaledIcon));
			pnlChoix.add(pnl);
		}
		add(pnlChoix, BorderLayout.CENTER);
	}
	private void interfaceString() {
		JPanel pnlQuestion = new JPanel();
		pnlQuestion.add(new JLabel(modele.getQuestion()));
		
		JPanel pnlBox = new JPanel();
		pnlBox.setLayout(new BoxLayout(pnlBox, BoxLayout.PAGE_AXIS));
		for(int i = 0; i < modele.getNbChoix(); i++) {
			
			Dimension minSize = new Dimension(0, 2);
			Dimension prefSize = new Dimension(0, 10);
			Dimension maxSize = new Dimension(0, 20);
			pnlBox.add(new Box.Filler(minSize, prefSize, maxSize));
			pnlBox.add(boutons.get(i));
		}
		
		add(pnlQuestion, BorderLayout.NORTH);
		add(pnlBox, BorderLayout.LINE_START);
	}
}
