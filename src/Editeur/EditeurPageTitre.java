package Editeur;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Lecteur.ModelePageTitre;

public class EditeurPageTitre extends JFrame {
	
	private ModeleEditeur modele;
	private ModelePageTitre modelePT;
	private int posAffichable;
	
	private JTextField txtTitre = new JTextField(), txtSousTitre = new JTextField(), ordrePassage = new JTextField();
	private JLabel previewTitre, previewSousTitre;
	private JButton btnTitre = new JButton("Edit font titre"), btnSousTitre = new JButton("Edit font sous-titre");
	private JButton btnSave, btnCancel = new JButton("Annuler");
	
	
	public EditeurPageTitre(ModeleEditeur modele, int posAffichable) {
		this.modele = modele;
		this.posAffichable = posAffichable;
		if(posAffichable != -1) {
			modelePT = new ModelePageTitre((ModelePageTitre) modele.getAffichable(posAffichable));
		}else {
			modelePT = new ModelePageTitre(modele.getRowCount() + 1);
		}
		setTitle("Editeur page titre");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(800, 500);
		setLocationRelativeTo(null);
		creerInterfacePreview();
		creerInterfaceEditTexte();
		creerInterfaceSave();
	    addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
				modele.setEditeurQuestionOuvert(false);
				dispose();	
            }
        });
		setVisible(true);
	}
	
	private void creerInterfaceSave() {
		if(posAffichable == -1) {
			btnSave = new JButton("Ajouter la page titre");
			btnSave.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					modele.ajouterPageTitre(modelePT);
					modele.setEditeurQuestionOuvert(false);
					dispose();
				}
			});
		}else {
			btnSave = new JButton("Enregistrer les changements");
			btnSave.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					modele.modifierPageTitre(posAffichable, modelePT.getOrdrePassage(), modelePT.getTxtRendererTitre(),
							modelePT.getTxtRendererSousTitre(), modelePT.getTitre(), modelePT.getSousTitre());
					modele.setEditeurQuestionOuvert(false);
					dispose();
				}
			});
		}
		JPanel pnl = new JPanel();
		pnl.add(btnSave);
		pnl.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modele.setEditeurQuestionOuvert(false);
				dispose();
			}
		});
		add(pnl, BorderLayout.SOUTH);
	}
	private void creerInterfaceEditTexte() {
		JPanel pnlBox = new JPanel();
		pnlBox.setLayout(new BoxLayout(pnlBox, BoxLayout.PAGE_AXIS));
		
		JPanel pnlOrdrePassage = new JPanel();
		ordrePassage.setPreferredSize(new Dimension(50, 25));
		ordrePassage.setText("" + modelePT.getOrdrePassage());
		pnlOrdrePassage.add(new JLabel("Ordre passage: "));
		pnlOrdrePassage.add(ordrePassage);
		pnlBox.add(pnlOrdrePassage);
		ordrePassage.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				try {
					modelePT.setOrdrePassage(Integer.parseInt(ordrePassage.getText()));
				}catch(Exception ex) {}
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				try {
					modelePT.setOrdrePassage(Integer.parseInt(ordrePassage.getText()));
				}catch(Exception ex) {}				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JPanel pnlTitre = new JPanel();
		pnlTitre.add(new JLabel("Titre: "));
		pnlTitre.add(txtTitre);
		pnlTitre.add(btnTitre);
		pnlBox.add(pnlTitre);
		
		JPanel pnlSousTitre = new JPanel();
		pnlSousTitre.add(new JLabel("Sous titre: "));
		pnlSousTitre.add(txtSousTitre);
		pnlSousTitre.add(btnSousTitre);
		pnlBox.add(pnlSousTitre);
		
		add(pnlBox, BorderLayout.NORTH);
		
		txtTitre.setPreferredSize(new Dimension(300,25));
		txtTitre.setText(modelePT.getTitre());
		txtSousTitre.setPreferredSize(new Dimension(300,25));
		txtSousTitre.setText(modelePT.getSousTitre());
		
		btnTitre.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FontSetter fontSetter = new FontSetter(modelePT.getTxtRendererTitre(), getThis(), 0);
				
			}
		});
		btnSousTitre.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FontSetter fontSetter = new FontSetter(modelePT.getTxtRendererSousTitre(), getThis(), 1);
			}
		});
		txtTitre.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				previewTitre.setText(txtTitre.getText());
				modelePT.setTitre(txtTitre.getText());
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				previewTitre.setText(txtTitre.getText());
				modelePT.setTitre(txtTitre.getText());
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		txtSousTitre.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				previewSousTitre.setText(txtSousTitre.getText());
				modelePT.setSousTitre(txtSousTitre.getText());
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				previewSousTitre.setText(txtSousTitre.getText());
				modelePT.setSousTitre(txtSousTitre.getText());
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private EditeurPageTitre getThis() {
		return this;
	}
	
	public void updateTxt(TextRenderer renderer, int texte) {
		if(texte == 0) {
			previewTitre.setFont(renderer.getFont());
			previewTitre.setForeground(renderer.getColor());
			modelePT.setTxtRendererTitre(renderer);
		}else {
			previewSousTitre.setFont(renderer.getFont());
			previewSousTitre.setForeground(renderer.getColor());
			modelePT.setTxtRendererSousTitre(renderer);
		}
	}
	private void creerInterfacePreview() {
		JPanel pnl = new JPanel();
		pnl.setLayout(new BoxLayout(pnl, BoxLayout.PAGE_AXIS));
		
		JPanel pnlCenter = new JPanel(new GridBagLayout());
		
		GridBagConstraints center = new GridBagConstraints();
		center.anchor = GridBagConstraints.CENTER;
		center.fill = GridBagConstraints.NONE;
		
		previewTitre = new JLabel(modelePT.getTitre());
		previewTitre.setFont(modelePT.getFontTitre());
		previewTitre.setForeground(modelePT.getTxtRendererTitre().getColor());
		previewTitre.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		pnl.add(previewTitre, center);
		
		previewSousTitre = new JLabel(modelePT.getSousTitre());
		previewSousTitre.setFont(modelePT.getFontSousTitre());
		previewSousTitre.setForeground(modelePT.getTxtRendererSousTitre().getColor());
		previewSousTitre.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		pnl.add(previewSousTitre, center);
		
		pnlCenter.add(pnl);
		add(pnlCenter, BorderLayout.CENTER);
		
	}
	
	

}
