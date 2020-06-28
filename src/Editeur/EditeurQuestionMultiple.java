package Editeur;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import Lecteur.ModeleChoixMultiple;
import Lecteur.ModeleVraiFaux;

public class EditeurQuestionMultiple extends JFrame {

	private ModeleEditeur modele;
	private ModeleTableauReponse modeleTab;
	
	private JTextField txtNumero = new JTextField(), txtQuestion = new JTextField(), txtReussite = new JTextField();
	private JTextField txtFail = new JTextField(), txtEssait = new JTextField();
	private JButton btnSave, btnSupprimer;
	private int posQuestion;
	
	private JCheckBox checkBox = new JCheckBox("Réponse avec image");
	
    private JTable tableau;
    private JScrollPane scroll;
    private JTextField txtReponse;
    private JLabel lblReponseImage;
    private JPanel pnlReponseCont;
    
    private JButton btnAdd2 = new JButton("Ajouter la réponse");
    
    private boolean isReponseImage;
    
	
	public EditeurQuestionMultiple(ModeleEditeur modele, int posQuestion) {
		this.modele = modele;
		this.posQuestion = posQuestion;
		setTitle("Editeur/créateur de questions à choix multiple");
		if(posQuestion == -1) {
			btnSave = new JButton("Ajouter question");
			btnSupprimer = new JButton("Annuler");
			modeleTab= new ModeleTableauReponse();
			isReponseImage= false;
			
			
		}else {
			btnSave = new JButton("Enregistrer modifications");
			btnSupprimer = new JButton("Supprimer la question");
			btnSupprimer.setBackground(Color.RED);
			ModeleChoixMultiple modeleQuestion = (ModeleChoixMultiple)modele.getQuestion(posQuestion);
			modeleTab = new ModeleTableauReponse(modeleQuestion.getChoix());
			isReponseImage = modele.isReponseImage(posQuestion);
		}
		modeleTab.setReponseImage(isReponseImage);
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
					modele.ajouterQuestionMultiple(Integer.parseInt(txtNumero.getText()), txtQuestion.getText(),
							txtReussite.getText(), txtFail.getText(), Integer.parseInt(txtEssait.getText())
							, modeleTab.getReponses(), modeleTab.getReponses().get(0).getNumero() - 1, isReponseImage);
					dispose();
				}else if(posQuestion >= 0) {
					modele.modifierQuestionMultiple(posQuestion, Integer.parseInt(txtNumero.getText()), txtQuestion.getText(),
							txtReussite.getText(), txtFail.getText(), Integer.parseInt(txtEssait.getText())
							, modeleTab.getReponses(), modeleTab.getReponses().get(0).getNumero() - 1, isReponseImage);
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
							+ "Action est irréversible", "Confirmation", JOptionPane.YES_NO_OPTION);
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
		    	    modeleTab.setReponseImage(isReponseImage);
		    	    cl.show(pnlReponseCont, "Img");
				}else {
					isReponseImage = false;
		    	    CardLayout cl = (CardLayout)(pnlReponseCont.getLayout());
		    	    modeleTab.setReponseImage(isReponseImage);
		    	    cl.show(pnlReponseCont, "txt");
				}
				
			}
		});
	}
	
	private void openFileChooser() {
		JFileChooser choix = new JFileChooser();
		choix.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		FileFilter imageFilter = new FileNameExtensionFilter(
			    "Image files", ImageIO.getReaderFileSuffixes());
		choix.addChoosableFileFilter(imageFilter);
		choix.setFileFilter(imageFilter);
		
		int retour=choix.showOpenDialog(null);
		if(retour==JFileChooser.APPROVE_OPTION){
			lblReponseImage.setText(choix.getSelectedFile().getAbsolutePath());
			btnAdd2.setEnabled(true);
		}
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
        
        JButton btnAdd = new JButton("Ajouter la réponse");
        JButton btnSupprimer = new JButton("Supprimer la réponse sélectionnée");
        btnAdd2.setEnabled(false);
        JButton btnSupprimer2 = new JButton("Supprimer la réponse sélectionnée");
        btnSupprimer.setBackground(Color.LIGHT_GRAY);
        btnSupprimer2.setBackground(Color.LIGHT_GRAY);
        JButton btnImage = new JButton("Chercher une image");
        
        txtReponse = new JTextField();
        txtReponse.setPreferredSize(new Dimension(400,25));
        lblReponseImage = new JLabel();
        lblReponseImage.setPreferredSize(new Dimension(400,25));
        
        pnlReponseTexte.add(new JLabel("Réponses: "));
        pnlReponseTexte.add(txtReponse);
        pnlReponseTexte.add(btnAdd);
        pnlReponseTexte.add(btnSupprimer);
        
        pnlReponseImage.add(new JLabel("Réponses (chemin d'accès de l'image): "));
        pnlReponseImage.add(lblReponseImage);
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
        
        btnImage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				openFileChooser();	
			}
		});
        btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				modeleTab.ajouterReponse(txtReponse.getText(), modeleTab.getRowCount(), false);
				txtReponse.setText("");
			}
		});
        btnAdd2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				modeleTab.ajouterReponse(lblReponseImage.getText(), modeleTab.getRowCount(), false);
				lblReponseImage.setText("");
				btnAdd2.setEnabled(false);
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
		pnlNo.add(new JLabel("Ordre d'affichage: "));
		txtNumero.setPreferredSize(new Dimension(50, 25));
		if(posQuestion >= 0) {
			txtNumero.setText(modele.getQuestion(posQuestion).getOrdrePassage()+"");
		}else {
		txtNumero.setText("" +  (modele.getQuestionnaire().getModeles().size() + 1) );
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
		
		//message réussite
		pnlMessageSucces.add(new JLabel("Message réussite: "));
		txtReussite.setPreferredSize(new Dimension(250, 25));
		if(posQuestion >= 0) {
			txtReussite.setText(modele.getQuestion(posQuestion).getMessageReussite());
		}
		pnlMessageSucces.add(txtReussite);
		pnlMessage.add(pnlMessageSucces);
		
		//messages Fail
		JPanel pnlMessageFail = new JPanel();
		pnlMessageFail.add(new JLabel("Message mauvaise réponse: "));
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
		pnlMessageEssait.add(new JLabel("Nombre d'essait ( -1 pour un nombre illimité d'essais ): "));
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
