package gestionnaire;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Editeur.ControleurEditeur;
import Lecteur.ControleurLecteur;
import Lecteur.ModeleVraiFaux;


public class FenetreGestionnaire extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4638942970162174098L;
	private ModeleGestionnaire modele;
	private String[] modes = { "Lecteur de questionnaires/quiz" , "Editeur de questionnaires/quiz" };
	private JComboBox cboModes = new JComboBox(modes);
	
	private JButton btnAjout = new JButton("Ajouter un questionnaire/quiz");
	
	
	public FenetreGestionnaire(ModeleGestionnaire modele) {
		this.modele = modele;
		initialiserFenetre();
		creerInterfaceModes();
		creerInterfaceQuestionnaire();
		creerInterfaceAjout();
		setVisible(true);
	}
	
	private void initialiserFenetre() {

		setSize(800, 600);
		setTitle("Createur de questionnaires/quiz");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				modele.ecritureDonne();
				System.exit(0);	
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void creerInterfaceModes() {
		JPanel pnl = new JPanel();
		pnl.add(new JLabel("Mode: "));
		pnl.add(cboModes);
		cboModes.setSelectedIndex(0);
		cboModes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cboModes.getSelectedIndex() == 0) {
					modele.setModeLecture(true);
				}else {
					modele.setModeLecture(false);
				}
			}
		});
		add(pnl, BorderLayout.NORTH);
	}

	private void creerInterfaceQuestionnaire() {
		JList list = new JList(modele);
		list.addMouseListener(new MouseAdapter() {
		          @Override
		          public void mouseClicked(MouseEvent e) {
		        	  Questionnaire questionnaire = (Questionnaire)modele.getElementAt(list.locationToIndex(e.getPoint()));
		        	  if(questionnaire == null) {
		        		  //L'utilisateur n'a pas click sur un questionnaire
		        	  }
		        	  else if(modele.isModeLecture()) {
		        		  ControleurLecteur controleur = new ControleurLecteur(questionnaire);
		        	  }else {
		        		  	ControleurEditeur cont = new ControleurEditeur(modele, list.locationToIndex(e.getPoint()));
		        	  }
		          }
			});
		JScrollPane scroll =  new JScrollPane(list);
		add(scroll, BorderLayout.CENTER);
	}
	
	private void creerInterfaceAjout() {
		JPanel pnl = new JPanel();
		pnl.add(btnAjout);
		btnAjout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
      		  String nom = JOptionPane.showInputDialog(null, "Entrer le nom du questionnaire/quiz", "Createur questionnaire/quiz",
    				  JOptionPane.QUESTION_MESSAGE);
    		  if(nom != null && nom != "") {
    			  ControleurEditeur cont = new ControleurEditeur(modele, modele.addQuestionnaire(nom));
    		  }
			}
		});
		add(pnl, BorderLayout.SOUTH);
	}
}
