package gestionnaire;

public class ControleurGestionnaire {
	
	private ModeleGestionnaire modele;
	private FenetreGestionnaire fenetre;
	
	public ControleurGestionnaire() {
		modele = new ModeleGestionnaire();
		fenetre = new FenetreGestionnaire(modele);
	}

}
