package partie.rmi;

import partie.core.TypeDefense;
import partie.core.TypeUnite;
import partie.core.Unite;
import partie.ihm.InterfacePartie.Menu;

public interface ServeurPartie {

	void ajouterUnite(int camp, TypeUnite typeU, int grpSelect);
	void ajouterDefence(int camp, TypeDefense typeD, Menu menu);
	void supprimerDefence(int camp, Menu menu);
	boolean aDefence(int camp, Menu menu);
}