package partie.core;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import partie.ihm.InterfacePartie.Menu;
import partie.rmi.JoueurPartie;
import partie.rmi.JoueurPartieImpl;

public class Armee implements Serializable {
	
	private ArrayList<Groupe> groupes;
	private Base base;
	
	public Armee (Vect2 posBase, int camp) {
		this.base = new Base(posBase, camp);
		this.groupes = new ArrayList<Groupe>();
		this.groupes.add(new Groupe(base.getPosition()));
		this.groupes.add(new Groupe(base.getPosition()));
		this.groupes.add(new Groupe(base.getPosition()));
	}
	
	
	public ArrayList<Groupe> getGroupes() { return this.groupes; }
	public Base getBase() { return this.base; }
	
	
	public void setBase(Base b) {
		this.base = b;
	}
	public void addGroupe(Groupe g) {
		this.groupes.add(g);
	}
	
	/**
	 * Met � jour toutes les unit�s de l'arm�e
	 * @param dt
	 * 			Temps depuis la derniere mis � jour
	 * @param entites
	 * 			Ensemble des entit�s du jeu
	 * @param joueurs
	 * 			Ensemble des joueurs
	 */
	public void update (float dt, HashMap<Integer, Armee> entites, HashMap<Integer, JoueurPartie> joueurs) {
		if (base != null) this.base.update(dt, entites, joueurs);
		for (int j = 0; j < this.getGroupes().size(); j++) {
			this.getGroupes().get(j).update(dt, entites, joueurs);
		}
	}
	
	/**
	 * Dessine toutes les unites de l'arm�e
	 * @param g
	 * 			Graphics
	 * @param ratio
	 * 			Ratio d'affichage
	 * @param offSet
	 * 			Vect2 : d�calage en x et y
	 */
	public void draw(Graphics g, float ratio, Vect2 offSet, Images images, int campJoueurImpl) {
		
		for (int j = 0; j < this.getGroupes().size(); j++) {
			this.getGroupes().get(j).draw(g, ratio, offSet, images, campJoueurImpl);
		}
		// On v�rifie si la base existe
		if (this.getBase() != null) {
			// On dessine la base sur le plateau
			this.getBase().draw(g, ratio, offSet, images, campJoueurImpl);
		}
	}
	/**
	 * Ajoute une unite dans le groupe designe
	 * @param grp int : groupe selectionne
	 * @param u Unite : unite a rajoute dans le groupe
	 */
	public void ajouterUnite(int grp, Unite u) {
		this.groupes.get(grp).addUnite(u);
	}
	/**
	 * Ajoute une defense a l'emplacement selectionne
	 * @param m Menu : emplacement de defense selectionne
	 * @param d Defense : defense a rajouter
	 */
	public void ajouterDefense(Menu m, Defense d) {
		this.base.addDef(m, d);
	}
	/**
	 * Enleve la defense a l'emplacement selectionne
	 * @param m Menu : emplacement selectionne
	 */
	public void supprimerDef(Menu m) {
		this.base.suppDef(m);
	}
	
	/**
	 * Supprime l'unit� en param�tre de la liste des unit�s du serveur
	 * @param u L'unit� � supprimer
	 */
	public void supprimerUnite(Unite u) {
		// Parcourt tous les groupes et supprimes U lorsque celle-ci est trouv�e
		for (Groupe g : this.getGroupes()) {
			g.suppUnite(u);
		}
	}
	
	/**
	 * Supprime toutes les unites mobiles ainsi que les defenses
	 */
	public void supprimerToutesUnites() {
		for (Groupe g : this.getGroupes()) {
			g.suppToutesUnites();
		}
		base.suppToutesDef();
	}
}
