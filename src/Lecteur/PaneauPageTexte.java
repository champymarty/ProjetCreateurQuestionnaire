package Lecteur;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class PaneauPageTexte extends JPanel {

	private ModelePageTexte modele;
	
	private JLabel lblTitre;
	private JTextArea txt;
	
	public PaneauPageTexte(ModelePageTexte modele) {
		this.modele = modele;
		creerInterface();
	}
	
	private void creerInterface() {
		setLayout(new BorderLayout());
		JPanel pnl1 = new JPanel();
		JPanel pnl2 = new JPanel();
		lblTitre = new JLabel(modele.getTitre());
		lblTitre.setFont(modele.getRendererTitre().getFont());
		lblTitre.setForeground(modele.getRendererTitre().getColor());
		
		txt = new JTextArea(modele.getText());
		txt.setFont(modele.getRendererText().getFont());
		txt.setForeground(modele.getRendererText().getColor());
		txt.setEditable(false);
		txt.setOpaque(false);
		
		pnl1.add(lblTitre, JPanel.LEFT_ALIGNMENT);
		pnl2.add(txt);
		
		add(pnl1, BorderLayout.NORTH);
		add(pnl2, BorderLayout.WEST);
	}
}
