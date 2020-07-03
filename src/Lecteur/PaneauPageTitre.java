package Lecteur;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PaneauPageTitre extends JPanel {
	
	private JLabel previewTitre, previewSousTitre;
	
	public PaneauPageTitre(ModelePageTitre modele) {
		super();
		creerInterfacePreview(modele);
	}
	
	private void creerInterfacePreview(ModelePageTitre modelePT) {
		JPanel pnl = new JPanel();
		pnl.setLayout(new BoxLayout(pnl, BoxLayout.PAGE_AXIS));
		
		setLayout(new GridBagLayout());
		
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
		
		add(pnl, center);
	}
}
