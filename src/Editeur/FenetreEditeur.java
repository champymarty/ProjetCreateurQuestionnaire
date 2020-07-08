package Editeur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Editeur.ModeleEditeur.AffichableType;

public class FenetreEditeur extends JFrame {
	
	private JTextField txtNom;
	
	private JButton btn = new JButton("Ajouter une question");
	private JButton btnSave = new JButton("Enregistrer");
	private JButton btnSupprimer = new JButton("Supprimer");
    private String[] cboModel = {"Question Vrai/faux", "Question choix multiples", "Question multiple réponse", "Page titre", "Page de texte"};
    private JComboBox cbo = new JComboBox(cboModel);
	
    private JTable tableau;
    private JScrollPane scroll;
    
    private ModeleEditeur modele;
    
    public FenetreEditeur(ModeleEditeur modele) {
		this.modele = modele;
		setTitle("Editeur de questionnaires");
		setSize(800, 500);
		creerInterfaceHaut();
		creerInterfaceMilieu();
		creerInterfaceBas();
		setLocationRelativeTo(null);
		setVisible(true);
	}
    
    private void creerInterfaceHaut() {
    	JPanel pnl = new JPanel();
    	txtNom = new JTextField(modele.getNomQuestionnaire());
    	txtNom.setPreferredSize(new Dimension(150, 25));
    	pnl.add(new JLabel("Nom du questionnaire/quiz: "));
    	pnl.add(txtNom);
    	pnl.add(btnSupprimer);
    	btnSupprimer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int reply = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce questionnaire ?", "Confirmation", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					modele.supprimerQuestionnaire();
					dispose();
				} 	
			}
		});
    	btnSupprimer.setBackground(Color.RED);
    	add(pnl, BorderLayout.NORTH);
    }
    private void creerInterfaceMilieu() {
    	tableau = new JTable(modele);
        tableau.setFillsViewportHeight(true);
        tableau.getColumnModel().getColumn(3).setCellRenderer(new CellBoutonRenderer());
        tableau.getColumnModel().getColumn(3).setCellEditor(new EditeurCellEdit(modele));
        scroll = new JScrollPane(tableau);
        
        add(scroll, BorderLayout.CENTER);
    }
    private void creerInterfaceBas() {
    	JPanel pnl = new JPanel();
    	pnl.add(new JLabel("Type de question à ajouter: "));
    	pnl.add(cbo);
    	pnl.add(btn);
    	pnl.add(btnSave);
    	btnSave.setBackground(Color.GREEN);
    	
    	btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AffichableType questionType;

				switch (cbo.getSelectedIndex()) {
				case 0:
					questionType = AffichableType.VRAI_FAUX;
					break;
				case 1:
					questionType = AffichableType.QUESTION_CHOIX_MULTIPLE;
					break;
				case 2:
					questionType = AffichableType.MULITPLE_REPONSE;
					break;
				case 3:
					questionType = AffichableType.PAGE_TITRE;
					break;
				case 4:
					questionType = AffichableType.PAGE_TEXTE;
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + cbo.getSelectedIndex());
				}
				modele.ouvrirEditeurQuestion(questionType);
			}
		});
    	btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				modele.setNomQuestionnaire(txtNom.getText());
				modele.enregistrerDonne();
				dispose();
			}
		});
    	
    	add(pnl, BorderLayout.SOUTH);
    }

}
