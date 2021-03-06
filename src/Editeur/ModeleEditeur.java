package Editeur;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.table.AbstractTableModel;

import Lecteur.Affichable;
import Lecteur.ModeleChoixMultiple;
import Lecteur.ModeleMultipleReponse;
import Lecteur.ModelePageTexte;
import Lecteur.ModelePageTitre;
import Lecteur.ModeleQuestion;
import Lecteur.ModeleVraiFaux;
import gestionnaire.ModeleGestionnaire;
import gestionnaire.Questionnaire;

public class ModeleEditeur extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1530180134079951336L;
	private final String[] entetes = {"Odre de passage", "Type de question", "Question/Titre", " Editer"};
	
	private ModeleGestionnaire modeleGestionnaire;
	private int posQuestionnaire;
	
	private boolean editeurQuestionOuvert = false;
	
	public enum AffichableType{
		VRAI_FAUX,
		QUESTION_CHOIX_MULTIPLE,
		MULITPLE_REPONSE,
		PAGE_TITRE,
		PAGE_TEXTE;
	};
	
	public ModeleEditeur(ModeleGestionnaire modeleGestionnaire, int posQuestionnaire) {
		this.posQuestionnaire = posQuestionnaire;
		this.modeleGestionnaire = modeleGestionnaire;
	}
	
	public void setEditeurQuestionOuvert(boolean editeurQuestionOuvert) {
		this.editeurQuestionOuvert = editeurQuestionOuvert;
	}
	
	public boolean isEditeurQuestionOuvert() {
		return editeurQuestionOuvert;
	}
	
	public String getNomQuestionnaire() {
		return modeleGestionnaire.getTitreQuestionnaire(posQuestionnaire);
	}
	
	public void setNomQuestionnaire(String newTitre) {
		modeleGestionnaire.setTitreQuestionnaire(posQuestionnaire, newTitre);
	}
	
	public void enregistrerDonne() {
		modeleGestionnaire.ecritureDonne();
	}
	
	public Affichable getAffichable(int indexAffichable) {
		return modeleGestionnaire.getModeleAt(posQuestionnaire, indexAffichable);
	}
	public ModeleQuestion getQuestion(int indexQuestion) {
		return (ModeleQuestion)modeleGestionnaire.getModeleAt(posQuestionnaire, indexQuestion);
	}
	
	public void supprimerAffichable(int index) {
		( (Questionnaire)modeleGestionnaire.getElementAt(posQuestionnaire) ).getModeles().remove(index);
		for(int i = index ;i < ( (Questionnaire)modeleGestionnaire.getElementAt(posQuestionnaire) ).getNombreQuestion(); i++) {
			( (Questionnaire)modeleGestionnaire.getElementAt(posQuestionnaire) ).getModeles().get(i).setOrdrePassage(i+1);
		}
		fireTableRowsDeleted(index, index);
	}

    @Override
    public Class getColumnClass(int columnIndex){
    	switch(columnIndex){
    		case 0:
    			return Integer.class;
    		case 1:
    			return String.class;
    		case 2: 
    			return String.class;
    		case 3:
    			return String.class;
    		default:
    			return Object.class;
    	}
    }

    @Override
    public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }

	@Override
	public int getRowCount() {
		Questionnaire questionnaireActuelle = (Questionnaire)modeleGestionnaire.getElementAt(posQuestionnaire);
		return questionnaireActuelle.getModeles().size();
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return entetes.length;
	}
	
	public void supprimerAffichable() {
		modeleGestionnaire.supprimerQuestionnaire(posQuestionnaire);
	}
	
	public Questionnaire getQuestionnaire() {
		return (Questionnaire)modeleGestionnaire.getElementAt(posQuestionnaire);
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		switch (columnIndex) {
		case 0:
			return modeleGestionnaire.getModeleAt(posQuestionnaire, rowIndex).getOrdrePassage();
		case 1:
			return modeleGestionnaire.getModeleAt(posQuestionnaire, rowIndex).getQuestionType();
		case 2:
			if(modeleGestionnaire.getModeleAt(posQuestionnaire, rowIndex) instanceof ModeleQuestion) {
				return ((ModeleQuestion)modeleGestionnaire.getModeleAt(posQuestionnaire, rowIndex)).getQuestion();
			}else if(modeleGestionnaire.getModeleAt(posQuestionnaire, rowIndex) instanceof ModelePageTitre) {
				return ((ModelePageTitre)modeleGestionnaire.getModeleAt(posQuestionnaire, rowIndex)).getTitre();
			}else if(modeleGestionnaire.getModeleAt(posQuestionnaire, rowIndex) instanceof ModelePageTexte) {
				return ((ModelePageTexte)modeleGestionnaire.getModeleAt(posQuestionnaire, rowIndex)).getTitre();
			}
		default:
			return null;
		}
	}
	
	public void ouvrirEditeurQuestion(AffichableType question) {
		switch (question) {
		case VRAI_FAUX:
			EditeurVraiFaux editeur1 = new EditeurVraiFaux(this, -1);
			break;
		case QUESTION_CHOIX_MULTIPLE:
			EditeurQuestionMultiple editeur2 = new EditeurQuestionMultiple(this, -1, false);
			break;
		case MULITPLE_REPONSE:
			EditeurQuestionMultiple editeur3 = new EditeurQuestionMultiple(this, -1, true);
			break;
		case PAGE_TITRE:
			EditeurPageTitre editeur4 = new EditeurPageTitre(this, -1);
			break;
		case PAGE_TEXTE:
			EditeurPageTexte editeur5 = new EditeurPageTexte(this, -1);
			break;
		default:
			break;
		}
		
	}
	
	public void ajouterAffichable(Affichable affichable) {
		if(affichable.getOrdrePassage() >= getQuestionnaire().getNombreQuestion() || affichable.getOrdrePassage() <= 0) {
			affichable.setOrdrePassage(getQuestionnaire().getNombreQuestion() + 1);
		}
		getQuestionnaire().getModeles().add(affichable.getOrdrePassage() - 1, affichable);
		for(int i = affichable.getOrdrePassage() ; i < getQuestionnaire().getNombreQuestion() ; i++) {
			getAffichable(i).setOrdrePassage(getAffichable(i).getOrdrePassage() + 1);
		}
		fireTableDataChanged();
	}
	
	public void modificationQuestionVraiFaux(int index, int ordrePassage, String question, String messageReussite, String messageFail,
			boolean reponse, int nbEssait) {
		updatedOrdrePassage(index, ordrePassage);
		getQuestion(index).setQuestion(question);
		getQuestion(index).setMessageReussite(messageReussite);
		getQuestion(index).setMessageFail(messageFail);
		((ModeleVraiFaux)getQuestion(index)).setReponse(reponse);
		getQuestion(index).setNbEssait(nbEssait);
		trierAffichables();
		fireTableDataChanged();
	}
	
	public void modifierQuestionMultiple(int index, int ordrePassage, String question, String messageReussite, String messageFail, 
			int nbEssait, ArrayList<Reponse> choix, int indexReponse, boolean estImage) {
		updatedOrdrePassage(index, ordrePassage);
		getQuestion(index).setQuestion(question);
		getQuestion(index).setMessageReussite(messageReussite);
		getQuestion(index).setMessageFail(messageFail);
		((ModeleChoixMultiple)getQuestion(index)).setChoix(choix);
		((ModeleChoixMultiple)getQuestion(index)).setIndexReponse(indexReponse);
		((ModeleChoixMultiple)getQuestion(index)).setEstImage(estImage);
		getQuestion(index).setNbEssait(nbEssait);
		trierAffichables();
		fireTableDataChanged();
	}

	
	public void modifierQuestionReponseMultiple(int index, int ordrePassage, String question, String messageReussite, String messageFail, 
			int nbEssait, ArrayList<Reponse> choix, ArrayList<Integer> indexReponse, boolean estImage) {
		updatedOrdrePassage(index, ordrePassage);
		getQuestion(index).setQuestion(question);
		getQuestion(index).setMessageReussite(messageReussite);
		getQuestion(index).setMessageFail(messageFail);
		((ModeleMultipleReponse)getQuestion(index)).setChoix(choix);
		((ModeleMultipleReponse)getQuestion(index)).setIndexReponses(indexReponse);
		((ModeleMultipleReponse)getQuestion(index)).setEstImage(estImage);
		getQuestion(index).setNbEssait(nbEssait);
		trierAffichables();
		fireTableDataChanged();
	}

	private void trierAffichables() {
		Collections.sort(getQuestionnaire().getModeles());
	}
	
	public void modifierPageTitre(int index, int ordrePassage, TextRenderer rendererTitre, TextRenderer rendererSousTitre,
			String titre, String sousTitre) {
		updatedOrdrePassage(index, ordrePassage);
		((ModelePageTitre)getAffichable(index)).setTxtRendererTitre(rendererTitre);
		((ModelePageTitre)getAffichable(index)).setTxtRendererSousTitre(rendererSousTitre);
		((ModelePageTitre)getAffichable(index)).setTitre(titre);
		((ModelePageTitre)getAffichable(index)).setSousTitre(sousTitre);
		trierAffichables();
		fireTableDataChanged();
	}
	
	public void modifierPageTexte(int index, int ordrePassage, TextRenderer rendererTitre, TextRenderer rendererText,
			String titre, String texte) {
		updatedOrdrePassage(index, ordrePassage);
		((ModelePageTexte)getAffichable(index)).setRendererTitre(rendererTitre);
		((ModelePageTexte)getAffichable(index)).setRendererText(rendererText);
		((ModelePageTexte)getAffichable(index)).setTitre(titre);
		((ModelePageTexte)getAffichable(index)).setText(texte);
		trierAffichables();
		fireTableDataChanged();
	}
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
    	if(columnIndex == 1) {
    		return false;
    	}else {
    		return true;
    	}
    }
    
    public boolean isReponseImage(int indexQuestion) {
    	try {
    		
    		return ((ModeleQuestion)getAffichable(indexQuestion)).isReponseImage();
    	}catch(IndexOutOfBoundsException e) {
    		return false;
    	}
    }
    
    public void afficher() {
    	System.out.println("--------------------------------");
		for(Affichable aff : getQuestionnaire().getModeles()) {
			System.out.println(""+aff.getOrdrePassage() + " " +aff.getQuestionType());
		}
		System.out.println("--------------------------------");
    }
    
    public void updatedOrdrePassage(int indexAffichableUpdate, int newOrdrePassage) {
    	if(newOrdrePassage > getQuestionnaire().getNombreQuestion() || newOrdrePassage <= 0) {
			newOrdrePassage = getQuestionnaire().getNombreQuestion();
		}
		for(Affichable aff : getQuestionnaire().getModeles()) {
			if(aff.getOrdrePassage() == newOrdrePassage) {
				aff.setOrdrePassage(getAffichable(indexAffichableUpdate).getOrdrePassage());
				break;
			}
		}
		getAffichable(indexAffichableUpdate).setOrdrePassage(newOrdrePassage);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    	switch (columnIndex) {
		case 0:
			updatedOrdrePassage(rowIndex, (int)aValue);
			trierAffichables();
			fireTableDataChanged();
			break;
		case 2:
			if(modeleGestionnaire.getModeleAt(posQuestionnaire, rowIndex) instanceof ModeleQuestion) {
				getQuestion(rowIndex).setQuestion((String) aValue);
			}else if(modeleGestionnaire.getModeleAt(posQuestionnaire, rowIndex) instanceof ModelePageTitre) {
				((ModelePageTitre)getAffichable(rowIndex)).setTitre((String) aValue);
			}else if(modeleGestionnaire.getModeleAt(posQuestionnaire, rowIndex) instanceof ModelePageTexte) {
				((ModelePageTexte)modeleGestionnaire.getModeleAt(posQuestionnaire, rowIndex)).setTitre((String)aValue);
			}
			break;

		default:
			break;
		}
    }

}
