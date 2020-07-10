package Lecteur;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FenetrePrincipal extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5728200420541360820L;
	private Affichable modeleActuel;
	private ControleurLecteur controleur;
	
	private JPanel pnlQuestions;
	
	private int nbEssait;
	
	public FenetrePrincipal(ArrayList<JPanel> paneaux, Affichable premierMod, ControleurLecteur controleur) {
		setTitle("Quiz par Chaton");
		modeleActuel = premierMod;
		if(modeleActuel instanceof ModeleQuestion) {
			nbEssait = ( (ModeleQuestion)modeleActuel ).getNbEssait();
		}
		this.controleur = controleur;
		setSize(ControleurLecteur.LONGEUR, ControleurLecteur.HAUTEUR);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		creerBoutons();
		initialiserQuestions(paneaux);
		setVisible(true);
	}
	
	
	
	private void creerBoutons() {
		JButton btnSuivant = new JButton("Next");
		JPanel pnl = new JPanel();
		pnl.add(btnSuivant);
		add(pnl, BorderLayout.SOUTH);
		
		btnSuivant.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(modeleActuel instanceof ModeleQuestion) {
					ModeleQuestion modele = (ModeleQuestion) modeleActuel;
					if(modele.reponseValide()) {
						modele.afficherValidation();
						controleur.prochaineQuestion(true);
					}else {
						modele.afficherValidation();
					if(nbEssait != -1) {
						nbEssait--;
						if(nbEssait <= 0) {
							controleur.prochaineQuestion(false);
						}
					}
				}
			}else if(modeleActuel instanceof ModelePageTitre || modeleActuel instanceof ModelePageTexte) {
				controleur.prochaineQuestion(true);
			}

		}
		});
	}
	
	private void initialiserQuestions(ArrayList<JPanel> paneaux) {
		 pnlQuestions = new JPanel(new CardLayout());
		for(int i = 0 ; i < paneaux.size() ; i++) {
			pnlQuestions.add(paneaux.get(i), i + "");
		}
	    CardLayout cl = (CardLayout)(pnlQuestions.getLayout());
	    cl.show(pnlQuestions, "0");
	    add(pnlQuestions, BorderLayout.CENTER); 
	}
	
	public void setQuestion(int indexPanel, Affichable modele) {
	    CardLayout cl = (CardLayout)(pnlQuestions.getLayout());
	    cl.show(pnlQuestions, indexPanel + "");
	    modeleActuel = modele;
		if(modeleActuel instanceof ModeleQuestion) {
			nbEssait = ( (ModeleQuestion)modeleActuel ).getNbEssait();
		}
	}

}
