package Editeur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Lecteur.ModeleVraiFaux;

public class EditeurVraiFaux extends JFrame{
	
	private ModeleEditeur modele;
	
	private JTextField txtOrdrePassage = new JTextField(), txtQuestion = new JTextField(), txtReussite = new JTextField();
	private JTextField txtFail = new JTextField(), txtEssait = new JTextField();
	private JButton btnSave, btnSupprimer;
	private JRadioButton btn1, btn2;
	private ButtonGroup group = new ButtonGroup();
	private int posQuestion;
		
	public EditeurVraiFaux(ModeleEditeur modele, int posQuestion) {
		this.modele = modele;
		this.posQuestion = posQuestion;
		setTitle("Editeur/créateur de question Vrai/faux");
		if(posQuestion == -1) {
			btnSave = new JButton("Ajouter question");
			btnSupprimer = new JButton("Annuler");
			txtOrdrePassage.setText("" +  (modele.getQuestionnaire().getModeles().size() + 1) );
			
		}else {
			btnSave = new JButton("Enregistrer modifications");
			btnSupprimer = new JButton("Supprimer");
			btnSupprimer.setBackground(Color.RED);
		}
		creerInterface();
		creerEvents();
		setSize(800, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	    addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
            	quit();
            }
        });
	    setAlwaysOnTop(true);
		setVisible(true);
	}
	
	private void quit() {
    	modele.setEditeurQuestionOuvert(false);
    	dispose();
	}
	
	public void creerEvents() {
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(posQuestion == -1) {
					ModeleVraiFaux modeleVF = new ModeleVraiFaux(Integer.parseInt(txtOrdrePassage.getText()),
							txtQuestion.getText(), txtReussite.getText(), txtFail.getText(), 
							true, Integer.parseInt(txtEssait.getText()));
					modele.ajouterAffichable(modeleVF);
					quit();
				}else if(posQuestion >= 0) {
					modele.modificationQuestionVraiFaux(posQuestion, Integer.parseInt(txtOrdrePassage.getText()),
							txtQuestion.getText(), txtReussite.getText(), txtFail.getText(), 
							true, Integer.parseInt(txtEssait.getText()));
					quit();
				}
			}
		});
		btnSupprimer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setAlwaysOnTop(false);
				if(posQuestion == -1) {
					quit();
				}else if(posQuestion >= 0) {
					int reply = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cette question ? Cette"
							+ "Action est irréversible", "Confirmation", JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						modele.supprimerAffichable(posQuestion);
						quit();
					} 
				}
			}
		});
	}
	
	public void creerInterface() {
		JPanel pnlPrincipal = new JPanel(new GridLayout(3,0));
		
		//numero question
		JPanel pnl = new JPanel();
		pnl.add(new JLabel("Ordre de passage: "));
		txtOrdrePassage.setPreferredSize(new Dimension(150, 25));
		if(posQuestion >= 0) {
			txtOrdrePassage.setText(modele.getQuestion(posQuestion).getOrdrePassage()+"");
		}
		pnl.add(txtOrdrePassage);
		pnlPrincipal.add(pnl);
		
		//question
		JPanel pnlQuestion = new JPanel();
		pnlQuestion.add(new JLabel("Question: "));
		txtQuestion.setPreferredSize(new Dimension(225, 25));
		if(posQuestion >= 0) {
			txtQuestion.setText(modele.getQuestion(posQuestion).getQuestion());
		}
		pnlQuestion.add(txtQuestion);
		pnlPrincipal.add(pnlQuestion);
		
		//messages réussite
		JPanel pnlMessageSucces = new JPanel();
		pnlMessageSucces.add(new JLabel("Message réussite: "));
		txtReussite.setPreferredSize(new Dimension(150, 25));
		if(posQuestion >= 0) {
			txtReussite.setText( modele.getQuestion(posQuestion).getMessageReussite() );
		}
		pnlMessageSucces.add(txtReussite);
		pnlPrincipal.add(pnlMessageSucces);
		
		//messages Fail
		JPanel pnlMessageFail = new JPanel();
		pnlMessageFail.add(new JLabel("Message mauvaise réponse: "));
		txtFail.setPreferredSize(new Dimension(150, 25));
		if(posQuestion >= 0) {
			txtFail.setText(modele.getQuestion(posQuestion).getMessageFail());
		}
		pnlMessageFail.add(txtFail);
		pnlPrincipal.add(pnlMessageFail);
		
		//Réponses
		JPanel pnlReponse = new JPanel();
		pnlReponse.add(new JLabel("Réponse: "));
		btn1 = new JRadioButton("Vrai");
		btn2 = new JRadioButton("faux");
		group.add(btn1);
		group.add(btn2);
		if(posQuestion >= 0) {
			if(((ModeleVraiFaux)modele.getQuestion(posQuestion)).getReponse()) {
				btn1.setSelected(true);
			}else {
				btn2.setSelected(true);
			}
		}
		pnlReponse.add(btn1);
		pnlReponse.add(btn2);
		pnlPrincipal.add(pnlReponse);
		
		
		//Nb essait
		JPanel pnlMessageEssait = new JPanel();
		pnlMessageEssait.add(new JLabel("Nombre d'essait ( -1 pour un nombre illimité d'essais ): "));
		txtEssait.setPreferredSize(new Dimension(150, 25));
		if(posQuestion >= 0) {
			txtEssait.setText(modele.getQuestion(posQuestion).getNbEssait()+"");
		}
		pnlMessageEssait.add(txtEssait);
		pnlPrincipal.add(pnlMessageEssait);
		
		//Boutons de controle
		JPanel pnlBas = new JPanel();
		pnlBas.add(btnSave);
		pnlBas.add(btnSupprimer);
		add(pnlBas, BorderLayout.SOUTH);
		
		add(pnlPrincipal, BorderLayout.CENTER);
	}

}
