package Editeur;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import Lecteur.ModeleChoixMultiple;
import Lecteur.ModeleVraiFaux;

public class EditeurQuestionMultiple extends JFrame {

	private ModeleEditeur modele;
	private ModeleTableauReponse modeleTab;
	
	private JTextField txtNumero = new JTextField(), txtQuestion = new JTextField(), txtReussite = new JTextField();
	private JTextField txtFail = new JTextField(), txtEssait = new JTextField();
	private JButton btnSave, btnSupprimer;
	private int posQuestion;
	
	private JCheckBox checkBox = new JCheckBox("R�ponse avec image");
	
    private JTable tableau;
    private JScrollPane scroll;
    private JTextField txtReponse, txtReponse2;
    private JPanel pnlReponseCont;
    
    private boolean isReponseImage;
    
	
	public EditeurQuestionMultiple(ModeleEditeur modele, int posQuestion) {
		this.modele = modele;
		this.posQuestion = posQuestion;
		setTitle("Editeur/cr�ateur de questions � choix multiple");
		if(posQuestion == -1) {
			btnSave = new JButton("Ajouter question");
			btnSupprimer = new JButton("Annuler");
			modeleTab= new ModeleTableauReponse();
			isReponseImage= false;
			
		}else {
			btnSave = new JButton("Enregistrer modifications");
			btnSupprimer = new JButton("Supprimer");
			btnSupprimer.setBackground(Color.RED);
			ModeleChoixMultiple modeleQuestion = (ModeleChoixMultiple)modele.getQuestion(posQuestion);
			modeleTab = new ModeleTableauReponse(modeleQuestion.getChoix());
			isReponseImage = modele.isReponseImage(posQuestion);
		}
		creerInterface();
		creerEvents();
		setSize(1200, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void creerEvents() {
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(posQuestion == -1) {
					modele.ajouterQuestionVraiFaux(Integer.parseInt(txtNumero.getText()),
							txtQuestion.getText(), txtReussite.getText(), txtFail.getText(), 
							true, Integer.parseInt(txtEssait.getText()));
					dispose();
				}else if(posQuestion >= 0) {
					modele.modificationQuestionVraiFaux(posQuestion, Integer.parseInt(txtNumero.getText()),
							txtQuestion.getText(), txtReussite.getText(), txtFail.getText(), 
							true, Integer.parseInt(txtEssait.getText()));
					dispose();
				}
			}
		});
		btnSupprimer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(posQuestion == -1) {
					dispose();
				}else if(posQuestion >= 0) {
					int reply = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cette question ? Cette"
							+ "Action est irr�versible", "Confirmation", JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						modele.supprimerAffichable(posQuestion);
						dispose();
					} 
				}
			}
		});
		
		checkBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkBox.isSelected()) {
					isReponseImage = true;
		    	    CardLayout cl = (CardLayout)(pnlReponseCont.getLayout());
		    	    cl.show(pnlReponseCont, "Img");
				}else {
					isReponseImage = false;
		    	    CardLayout cl = (CardLayout)(pnlReponseCont.getLayout());
		    	    cl.show(pnlReponseCont, "txt");
				}
				
			}
		});
	}
	
	private JPanel creerInterfaceTableauReponse() {
		//Table de reponses
		JPanel pnlReponse = new JPanel(new BorderLayout());
		
		//CheckBox 
		if(modele.isReponseImage(posQuestion)) {
			checkBox.setSelected(true);
		}else {
			checkBox.setSelected(false);
		}
		pnlReponse.add(checkBox, BorderLayout.NORTH);
		
    	tableau = new JTable(modeleTab);
        scroll = new JScrollPane(tableau);
        pnlReponse.add(scroll, BorderLayout.CENTER);
        
        pnlReponseCont = new JPanel(new CardLayout());
        JPanel pnlReponseTexte = new JPanel();
        JPanel pnlReponseImage = new JPanel();
        
        JButton btnAdd = new JButton("Ajouter la r�ponse");
        JButton btnSupprimer = new JButton("Supprimer la r�ponse s�lectionn�e");
        JButton btnAdd2 = new JButton("Ajouter la r�ponse");
        JButton btnSupprimer2 = new JButton("Supprimer la r�ponse s�lectionn�e");
        btnSupprimer.setBackground(Color.RED);
        btnSupprimer2.setBackground(Color.RED);
        JButton btnImage = new JButton("Chercher une image");
        
        txtReponse = new JTextField();
        txtReponse.setPreferredSize(new Dimension(400,25));
        txtReponse2 = new JTextField();
        txtReponse2.setPreferredSize(new Dimension(400,25));
        
        pnlReponseTexte.add(new JLabel("R�ponses: "));
        pnlReponseTexte.add(txtReponse);
        pnlReponseTexte.add(btnAdd);
        pnlReponseTexte.add(btnSupprimer);
        
        pnlReponseImage.add(new JLabel("R�ponses (chemin d'acc�s de l'image): "));
        pnlReponseImage.add(txtReponse2);
        pnlReponseImage.add(btnAdd2);
        pnlReponseImage.add(btnImage);
        pnlReponseImage.add(btnSupprimer2);
        
        pnlReponseCont.add(pnlReponseTexte, "txt");
        pnlReponseCont.add(pnlReponseImage, "Img");
        if(isReponseImage) {
        	checkBox.setSelected(true);
    	    CardLayout cl = (CardLayout)(pnlReponseCont.getLayout());
    	    cl.show(pnlReponseCont, "Img");
        }else {
        	checkBox.setSelected(false);
    	    CardLayout cl = (CardLayout)(pnlReponseCont.getLayout());
    	    cl.show(pnlReponseCont, "txt");
        }
        
        pnlReponse.add(pnlReponseCont, BorderLayout.SOUTH);
        
        //EVENTS//
        btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				modeleTab.ajouterReponse(txtReponse.getText(), modeleTab.getRowCount(), false);
				
			}
		});
        btnAdd2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				modeleTab.ajouterReponse(txtReponse2.getText(), modeleTab.getRowCount(), false);
				
			}
		});
        btnSupprimer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				modeleTab.enleverReponse(tableau.convertRowIndexToModel(tableau.getSelectedRow()));
				
			}
		});
        btnSupprimer2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				modeleTab.enleverReponse(tableau.convertRowIndexToModel(tableau.getSelectedRow()));
				
			}
		});
        return pnlReponse;
	}

	private JPanel creerInterfaceQuestion() {
		JPanel pnlNo = new JPanel();
		pnlNo.add(new JLabel("Num�ro de question: "));
		txtNumero.setPreferredSize(new Dimension(50, 25));
		if(posQuestion >= 0) {
			txtNumero.setText(modele.getQuestion(posQuestion).getNumeroQuestion()+"");
		}
		pnlNo.add(txtNumero);
		
		JPanel pnlQuestion = new JPanel();
		pnlQuestion.add(new JLabel("Question: "));
		txtQuestion.setPreferredSize(new Dimension(150, 25));
		if(posQuestion >= 0) {
			txtQuestion.setText(modele.getQuestion(posQuestion).getQuestion());
		}
		pnlQuestion.add(txtQuestion);
		
		JPanel pnl = new JPanel(new GridLayout(1,0));
		pnl.add(pnlNo);
		pnl.add(pnlQuestion);
		return pnl;
	}
	
	private JPanel creerInterfaceMessages() {
		JPanel pnlMessage = new JPanel(new GridLayout(1,0));
		JPanel pnlMessageSucces = new JPanel();
		
		//message r�ussite
		pnlMessageSucces.add(new JLabel("Message r�ussite: "));
		txtReussite.setPreferredSize(new Dimension(250, 25));
		if(posQuestion >= 0) {
			txtReussite.setText(modele.getQuestion(posQuestion).getMessageReussite());
		}
		pnlMessageSucces.add(txtReussite);
		pnlMessage.add(pnlMessageSucces);
		
		//messages Fail
		JPanel pnlMessageFail = new JPanel();
		pnlMessageFail.add(new JLabel("Message mauvaise r�ponse: "));
		txtFail.setPreferredSize(new Dimension(250, 25));
		if(posQuestion >= 0) {
			txtFail.setText(modele.getQuestion(posQuestion).getMessageFail());
		}
		pnlMessageFail.add(txtFail);
		pnlMessage.add(pnlMessageFail);
		return pnlMessage;
	}
	
	private JPanel creerInterfaceNbEssait() {
		//Nb essait
		JPanel pnlMessageEssait = new JPanel();
		pnlMessageEssait.add(new JLabel("Nombre d'essait ( -1 pour un nombre illimit� d'essais ): "));
		txtEssait.setPreferredSize(new Dimension(50, 25));
		if(posQuestion >= 0) {
			txtEssait.setText(modele.getQuestion(posQuestion).getNbEssait()+"");
		}
		pnlMessageEssait.add(txtEssait);
		
		return pnlMessageEssait;
	}
	
	private void creerBoutonControl() {
		JPanel pnlBas = new JPanel();
		pnlBas.add(btnSave);
		pnlBas.add(btnSupprimer);
		add(pnlBas, BorderLayout.SOUTH);
	}
	
	private void creerInterface() {
		JPanel pnlPrincipal = new JPanel(new GridLayout(2,1));
		JPanel pnlSettings = new JPanel(new GridLayout(0,1));
		

		pnlSettings.add(creerInterfaceQuestion());
		pnlSettings.add(creerInterfaceMessages());
		pnlSettings.add(creerInterfaceNbEssait());
		
		pnlPrincipal.add(pnlSettings);
		pnlPrincipal.add(creerInterfaceTableauReponse());
		
		
		creerBoutonControl();
		
		add(pnlPrincipal, BorderLayout.CENTER);
	}
}
