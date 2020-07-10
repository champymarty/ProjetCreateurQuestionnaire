package Editeur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Lecteur.ModelePageTexte;

public class EditeurPageTexte extends JFrame {

/**
	 * 
	 */
	private static final long serialVersionUID = 5147145409834559135L;
	
private JTextField txtTitre, txtNumero;
private JTextArea txtArea;
private JScrollPane scroll;
private JButton btnApprove,btnAnnuler, btnEditTxt, btnEditTitre, btnPreview = new JButton("Voir apperçu"), btnSupprimer = new JButton("Supprimer la page texte");

private ModeleEditeur modele;
private int posAffichable = -1;

public EditeurPageTexte(ModeleEditeur modele, int posAffichable) {
	this.modele = modele;
	this.posAffichable = posAffichable;
	setTitle("Editeur page texte");
	setSize(new Dimension(800,500));
	creerInterfaceTitre();
	creerInterfaceText();
	creerInterfaceBouton();
	creerEvents();
	setAlwaysOnTop(true);
	setVisible(true);
}

private void creerInterfaceTitre() {
	JPanel pnl = new JPanel();
	if(posAffichable == -1) {
		txtTitre = new JTextField();
		txtNumero = new JTextField(""+(modele.getRowCount() + 1));
	}else {
		txtTitre = new JTextField(((ModelePageTexte)modele.getAffichable(posAffichable)).getTitre());
		txtTitre.setFont(((ModelePageTexte)modele.getAffichable(posAffichable)).getRendererTitre().getFont());
		txtTitre.setForeground(((ModelePageTexte)modele.getAffichable(posAffichable)).getRendererTitre().getColor());
		txtNumero = new JTextField(""+((ModelePageTexte)modele.getAffichable(posAffichable)).getOrdrePassage());
	}
	txtNumero.setPreferredSize(new Dimension(50, 25));
	btnEditTitre = new JButton("Edit titre font");
	txtTitre.setPreferredSize(new Dimension(200, 25));
	pnl.add(new JLabel("Ordre passage: "));
	pnl.add(txtNumero);
	pnl.add(new JLabel("Titre: "));
	pnl.add(txtTitre);
	pnl.add(btnEditTitre);
	pnl.add(btnSupprimer);
	btnSupprimer.setBackground(Color.RED);
	add(pnl, BorderLayout.NORTH);
}

private void creerInterfaceText() {
	JPanel pnl = new JPanel();
	if(posAffichable == -1) {
		txtArea = new JTextArea();
	}else {
		txtArea = new JTextArea(((ModelePageTexte)modele.getAffichable(posAffichable)).getText());
		txtArea.setFont(((ModelePageTexte)modele.getAffichable(posAffichable)).getRendererText().getFont());
		txtArea.setForeground(((ModelePageTexte)modele.getAffichable(posAffichable)).getRendererText().getColor());
	}
	scroll = new JScrollPane(txtArea);
	scroll.setPreferredSize(new Dimension(700, 300));
	
	JLabel lbl = new JLabel("Texte: ");
	lbl.setAlignmentX(JLabel.LEFT);
	pnl.add(lbl);
	
	pnl.add(scroll);
	add(pnl, BorderLayout.CENTER);
}

private void creerInterfaceBouton() {
	JPanel pnl = new JPanel(new GridLayout(3,1));
	btnAnnuler = new JButton("Annuler");
	btnEditTxt = new JButton("Edit texte font");
	if(posAffichable == -1) {
		btnApprove = new JButton("Ajouter la page de texte");
	}else {
		btnApprove = new JButton("Enregistrer les changements");
	}
	btnApprove.setBackground(Color.GREEN);
	JPanel panelTemp1 = new JPanel();
	JPanel panelTemp2 = new JPanel();
	panelTemp1.add(btnEditTxt);
	panelTemp2.add(btnPreview);
	pnl.add(panelTemp1);
	pnl.add(panelTemp2);
	
	JPanel panelT = new JPanel();
	panelT.add(btnApprove);
	panelT.add(btnAnnuler);
	pnl.add(panelT);
	
	add(pnl, BorderLayout.SOUTH);
}

private void creerEvents() {
	btnAnnuler.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			quit();
		}
	});
    addWindowListener(new WindowAdapter(){
        @Override
        public void windowClosing(WindowEvent e){
        	quit();
        }
    });
    btnApprove.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(posAffichable == -1) {
				modele.ajouterAffichable(genererModele());
			}else {
				ModelePageTexte modeleModifier = genererModele();
				modele.modifierPageTexte(posAffichable, modeleModifier.getOrdrePassage(),
						modeleModifier.getRendererTitre(), modeleModifier.getRendererText(),
						modeleModifier.getTitre(), modeleModifier.getText());
			}
			quit();
		}
	});
    btnEditTxt.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			setAlwaysOnTop(false);
			@SuppressWarnings("unused")
			FontSetterPageText editeur = new FontSetterPageText(new TextRenderer(txtArea.getFont(), txtArea.getForeground()), 
					getThis(), false);
		}
	});
    btnEditTitre.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			setAlwaysOnTop(false);
			@SuppressWarnings("unused")
			FontSetterPageText editeur = new FontSetterPageText(new TextRenderer(txtTitre.getFont(), txtTitre.getForeground()), 
					getThis(), true);
		}
	});
    btnPreview.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame frame = new JFrame();
			frame.getContentPane().add(genererModele().generateAffichage());
			frame.setTitle("Preview Page titre");
			frame.setSize(800, 500);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
	});
    btnSupprimer.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			setAlwaysOnTop(false);
			if(posAffichable == -1) {
				modele.setEditeurQuestionOuvert(false);
				dispose();
			}else if(posAffichable >= 0) {
				int reply = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cette question ? Cette"
						+ " action est irréversible", "Confirmation", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					modele.supprimerAffichable(posAffichable);
					modele.setEditeurQuestionOuvert(false);
					dispose();
				} 
			}
		}
	});
}
private ModelePageTexte genererModele() {
	return new ModelePageTexte(Integer.parseInt(txtNumero.getText()), txtTitre.getText(), txtArea.getText(),
			new TextRenderer(txtTitre.getFont(), txtTitre.getForeground()), new TextRenderer(txtArea.getFont(), txtArea.getForeground()) );
}

private EditeurPageTexte getThis() {
	return this;
}

private void quit() {
	modele.setEditeurQuestionOuvert(false);
	dispose();
}

public void updateTxt(TextRenderer renderer, boolean titre) {
	if(titre) {
		txtTitre.setFont(renderer.getFont());
		txtTitre.setForeground(renderer.getColor());
	}else {
		txtArea.setFont(renderer.getFont());
		txtArea.setForeground(renderer.getColor());
	}
}

}
