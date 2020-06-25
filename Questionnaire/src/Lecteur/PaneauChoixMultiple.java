package Lecteur;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PaneauChoixMultiple extends JPanel {
	
	private ModeleChoixMultiple modele;
	
	private ArrayList<JRadioButton> boutons = new ArrayList<>();
	private ButtonGroup group = new ButtonGroup();
	
	public PaneauChoixMultiple(ModeleChoixMultiple modele) {
		super();
		this.modele = modele;
		creerBouton();
		creerInterface();
	}
	
	private void creerBouton() {
		for(int i = 0; i < modele.getNbChoix(); i++) {
			RadioBoutonAvecIndex btn = new RadioBoutonAvecIndex(i);
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					RadioBoutonAvecIndex btn = (RadioBoutonAvecIndex) e.getSource();
					modele.choixSelectionerUpdate(btn.getIndex());
				}
			});
			group.add(btn);
			boutons.add(btn);
		}
	}
	private void creerInterface() {
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
			if(modele.isImage()) {
				ImageIcon originalIcon = new ImageIcon(modele.getChoix(i));
				
		        int width = ControleurLecteur.LONGEUR / nbColonne;
		        int height = ControleurLecteur.HAUTEUR / nbLigne;

		        Image scaled = modele.scaleImage(originalIcon.getImage(), width, height);

		        ImageIcon scaledIcon = new ImageIcon(scaled);

		        pnl.add(new JLabel(scaledIcon));
				
			}else {
				pnl.add(new JLabel(modele.getChoix(i)));
			}
			pnlChoix.add(pnl);
		}
		add(pnlChoix, BorderLayout.CENTER);
	}

}
