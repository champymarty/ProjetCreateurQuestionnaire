package Lecteur;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PaneauVraiFaux extends JPanel{

	private ModeleVraiFaux modele;
	private JRadioButton vrai = new JRadioButton("Vrai"), faux = new JRadioButton("Faux");
	private ButtonGroup group = new ButtonGroup();
	
	
	public PaneauVraiFaux(ModeleVraiFaux modeleVraiFaux) {
		super();
		this.modele = modeleVraiFaux;
		setLayout(new BorderLayout());
		monterInterface();
	}
	
	private void monterInterface() {
		JLabel lbl = new JLabel(modele.getNumeroQuestion() +") "+ modele.getQuestion());
		lbl.setHorizontalAlignment(JLabel.CENTER);
		add(lbl, BorderLayout.NORTH);
		
		group.add(vrai);
		group.add(faux);
		JPanel pnl = new JPanel();
		pnl.add(vrai);
		pnl.add(faux);
		add(pnl, BorderLayout.CENTER);
		
		vrai.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				modele.reponseChanger(true);
				
			}
		});
		
		faux.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				modele.reponseChanger(false);
				
			}
		});
	}
	
	
}
