package partie.core;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.HashMap;

import javax.swing.ImageIcon;

import javafx.scene.paint.Color;
import partie.rmi.JoueurPartie;
import partie.rmi.JoueurPartieImpl;

public class Defense extends Unite implements Serializable {

	public Defense(Vect2 pos, int camp, int rayonEntite, int cout, int degatA, float vitesseA, float porteeA, String imageName, TypeUnite typeU) {
		super(pos, 0, camp, rayonEntite, cout, degatA, vitesseA, porteeA, 0, imageName, typeU);
		
	}
	
	/***
	 * Met � jour l'unite<li>
	 * 	Diminue le cooldown gr�ce � dt<li>
	 *  Determine l'action de l'unite en fonction de son environnement
	 * @param dt float : Temps depuis la derniere mise � jour
	 * @param entites HashMap<Integer, Armee> : Ensemble des unites de la partie
	 * @param joueurs HashMap<Integer, JoueurPartie> : Ensemble des joueurs de la partie
	 */
	public void update(float dt, HashMap<Integer, Armee> entites, HashMap<Integer, JoueurPartie> joueurs) {
		if (cooldown > 0) {
			cooldown -= dt;
			if (cooldown < 0) {
				cooldown = 0;
			}
		}
		
		// Calcule et renvoie l'unite la plus proche � distance d'attaque de U
		Entite entiteCible = entiteAAttaquer(entites); 
		
		if (entiteCible != null) {
			// Si la cible pointe sur une entite, U attaque la cible
			attaqueEntite(entiteCible, entites, joueurs); 
			angle = Outils.getAngle(position, entiteCible.getPosition()) + (float)Math.PI / 2;
		}
	}
	
	/**
	 * Dessine l'unite sur le plateau
	 * @param g Graphics : Graphics du JPanel plateau
	 * @param ratio float : Ratio d'affichage
	 * @param offSetA Vect2 : Decalage d'affichage en X et Y
	 * @param offSetB Vect2 : Second decalage d'affichage en X et Y qui evite que les defenses se superposent
	 * @param images Images : Classe contenant toutes les images du jeu
	 * @param campJoueurImpl int : camp du joueur
	 */
	public void draw(Graphics g, float ratio, Vect2 offSetA, Vect2 offSetB, Images images, int campJoueurImpl) {
		float rayon = rayonEntite * ratio;
		
		int posX = (int)offSetA.x + (int)offSetB.x + (int)Math.floor(position.x * ratio - rayon);
		int posY = (int)offSetA.y + (int)offSetB.y + (int)Math.floor(position.y * ratio - rayon);
		int r = (int)(rayon * 2);
		
		g.setColor(color);
		if (imageName != null) {
			Image image = images.getImage(imageName);
			AffineTransform rotation = new AffineTransform();
			rotation.translate(posX , posY);
			rotation.scale(r / (float)(image.getWidth(null)), r / (float)(image.getHeight(null)));
			rotation.rotate(angle, (int)(image.getWidth(null)/2),(int)(image.getHeight(null)/2));
			((Graphics2D)g).drawImage(image, rotation, null);
		}
		
		if (camp == campJoueurImpl) {
			g.setColor(java.awt.Color.WHITE);
			int posX2 = (int)offSetA.x + (int)Math.floor((position.x - porteeA) * ratio);
			int posY2 = (int)offSetA.y + (int)Math.floor((position.y - porteeA) * ratio);
			int r2 = (int)Math.floor(porteeA * ratio * 2);
			g.drawOval(posX2, posY2, r2, r2);
		}
		
		
		
		
	}

}
