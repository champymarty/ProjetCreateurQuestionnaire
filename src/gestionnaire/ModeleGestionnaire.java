package gestionnaire;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

import Lecteur.Affichable;
import Lecteur.ModeleQuestion;
import Lecteur.ModeleVraiFaux;

public class ModeleGestionnaire extends AbstractListModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8724071832847224285L;
	private ArrayList<Questionnaire> questionnaires;
	private final String FICHIER_DATA = "Questionnaires.bin";
	
	private boolean modeLecture = true;
	
	public ModeleGestionnaire() {
		lectureDonne();
	}
	
	private void lectureDonne() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FICHIER_DATA));
            questionnaires = (ArrayList) ois.readObject();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
        }
        if(questionnaires == null) {
        	questionnaires = new ArrayList<>();
        }
        System.out.println(questionnaires.size());
	}
	
	public boolean isModeLecture() {
		return modeLecture;
	}
	
	public void setModeLecture(boolean modeLecture) {
		this.modeLecture = modeLecture;
	}
	public void setTitreQuestionnaire(int indexQuestionnaire, String newTitre) {
		questionnaires.get(indexQuestionnaire).setTitre(newTitre);
		fireContentsChanged(questionnaires.get(indexQuestionnaire), indexQuestionnaire, indexQuestionnaire);
	}
	
	public String getTitreQuestionnaire(int indexQuestionnaire) {
		return questionnaires.get(indexQuestionnaire).getTitre();
	}
	
	public void supprimerQuestionnaire(int posQuestionnaire) {
		fireIntervalRemoved(questionnaires.remove(posQuestionnaire), posQuestionnaire, posQuestionnaire);
	}
	
	public void ecritureDonne() {
		
        try {
            FileOutputStream fileOut = new FileOutputStream(FICHIER_DATA);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(questionnaires);
            out.flush();
            out.close();
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public int addQuestionnaire(String nom) {
		Questionnaire questionnaire = new Questionnaire(nom, questionnaires.size());
		questionnaires.add(questionnaire);
		fireIntervalAdded(this, questionnaires.size()-1, questionnaires.size()-1);
		return questionnaires.size()-1;
	}
	
	public void addQuestion(Questionnaire questionnaire, ModeleQuestion modele) {
		questionnaires.get(questionnaire.getPosModele()).addAffichable(modele);
	}
	
	@Override
	public int getSize() {
		return questionnaires.size();
	}
	
	public Affichable getModeleAt(int indexQuestionnaire, int indexQuestion) {
		return questionnaires.get(indexQuestionnaire).getModeles().get(indexQuestion);
	}

	@Override
	public Object getElementAt(int index) {
		return questionnaires.get(index);
	}

}
