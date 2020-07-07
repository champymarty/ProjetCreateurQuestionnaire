package Editeur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Lecteur.ModelePageTitre;

public class FontSetter extends JFrame {
	
	private String effects[] = {"Plain", "Bold", "Italique", "Bold et Italique"};
	
	private JComboBox cboPolice = new JComboBox(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
	private JComboBox cboEffect = new JComboBox(effects);
	
	private JButton btn = new JButton("Retour");
	private JButton btnColor = new JButton("Choisir Couleur");
	private JTextField txtTaillePolice = new JTextField();
	
	private TextRenderer renderer;
	private EditeurPageTitre editeur;
	private boolean titre;
	
	
	public FontSetter(TextRenderer renderer, EditeurPageTitre editeur, boolean titre) {
		this.renderer = new TextRenderer(renderer);
		this.editeur = editeur;
		this.titre = titre;
		this.renderer = renderer;
		setFontInfo();
		setTitle("Modificateur de Font");
		creerInterface();
		creerEvents();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    pack();
	    setVisible(true);
	}
	
	private void setFontInfo() {
			cboEffect.setSelectedItem(renderer.getFont().getFamily());
			txtTaillePolice.setText("" + renderer.getFont().getSize());
			if(renderer.getFont().isPlain()) {
				cboPolice.setSelectedIndex(0);
			}else if(renderer.getFont().isBold() && renderer.getFont().isItalic()) {
				cboPolice.setSelectedIndex(3);
			}else if(renderer.getFont().isBold()) {
				cboPolice.setSelectedIndex(1);
			}else if(renderer.getFont().isItalic()) {
				cboPolice.setSelectedIndex(2);
			}
			
	}
	
	public void creerInterface() {
		JPanel pnl = new JPanel();
		pnl.add(new JLabel("Taille: "));
		txtTaillePolice.setPreferredSize(new Dimension(50, 25));
		pnl.add(txtTaillePolice);
		pnl.add(cboPolice);
		pnl.add(cboEffect);
		pnl.add(new JLabel("Couleur texte: "));
		pnl.add(btnColor);
		btnColor.setBackground(renderer.getColor());
		add(pnl, BorderLayout.CENTER);
		
		JPanel pnlBtn = new JPanel();
		pnlBtn.add(btn);
		add(pnlBtn, BorderLayout.SOUTH);
		

	}
	
	private void creerEvents() {
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateFont();
				dispose();
			}
		});
		
		cboPolice.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateFont();
			}
		});
		
		cboEffect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateFont();
			}
		});
		
		txtTaillePolice.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				updateFont();	
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				
				updateFont();	
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
		btnColor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
		        
		        // color chooser Dialog Box 
		        Color color = JColorChooser.showDialog(null, 
		                    "Select a color", renderer.getColor());
		        if(color != null) {
		        	btnColor.setBackground(color);
		        	renderer.setColor(color);
		        	updateFont();
		        }

			}
		});
	}
	private void updateFont() {
		try {
		if(cboEffect.getSelectedIndex() == 0) {
			renderer.setFont(new Font((String)cboPolice.getSelectedItem(), Font.PLAIN, Integer.parseInt(txtTaillePolice.getText())));
		}else if(cboEffect.getSelectedIndex() == 3) {
			renderer.setFont(new Font((String)cboPolice.getSelectedItem(), Font.BOLD | Font.ITALIC, Integer.parseInt(txtTaillePolice.getText())));
		}else if(cboEffect.getSelectedIndex() == 1) {
			renderer.setFont(new Font((String)cboPolice.getSelectedItem(), Font.BOLD, Integer.parseInt(txtTaillePolice.getText())));
		}else if(cboEffect.getSelectedIndex() == 2) {
			renderer.setFont(new Font((String)cboPolice.getSelectedItem(), Font.ITALIC, Integer.parseInt(txtTaillePolice.getText())));
		}
		editeur.updateTxt(renderer, titre);
		}catch(Exception e){
			
		}
	}

}
