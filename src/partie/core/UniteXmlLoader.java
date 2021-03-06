package partie.core;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXParseException;
import org.xml.sax.SAXException;


public class UniteXmlLoader {
	
	private Element root;
	
	public UniteXmlLoader() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	
	    try {
	       DocumentBuilder builder = factory.newDocumentBuilder();
	       File fileXML = new File(getClass().getResource("/Unites.xml").getPath());
	       Document xml;
	       try {
	          xml = builder.parse(fileXML);
	          root = xml.getDocumentElement();
	       } catch (SAXParseException e) { }
	    } catch (ParserConfigurationException e) {
	       e.printStackTrace();
	    } catch (SAXException e) {
	       e.printStackTrace();
	    } catch (IOException e) {
	       e.printStackTrace();
	    }
	}
	/**
	 * Creer une unite grace aux donnees recueillies dans le fichier xml<li>
	 *  Lis les information suivant typeU<li>
	 *  Initialise une unite avec ces infos
	 * @param typeU TypeUnite : type d'unite a creer
	 * @param camp int : Camp de l'unite creee
	 * @param pos Vect2 : position de l'unite creee
	 * @return l'unite voulue
	 */
	public Unite createUnite(TypeUnite typeU, int camp, Vect2 pos) {
		if (!Outils.estDefense(typeU)) {
			Node u = Outils.getChild(typeU.toString(), root);
			if (u != null) {
				
				Element carac = (Element)Outils.getChild("Caracteristiques", u);
				Element attaque = (Element)Outils.getChild("Attaque", u);
				
				int cout = Integer.parseInt(carac.getAttribute("cout"));
				int vieMax = Integer.parseInt(carac.getAttribute("vieMax"));
				int rayonUnite = Integer.parseInt(carac.getAttribute("rayonUnite"));
				float vitesseD = Float.parseFloat(carac.getAttribute("vitesseDeplacement"));
				String imageName = carac.getAttribute("imageName");
				int degatA = Integer.parseInt(attaque.getAttribute("degat"));
				float vitesseA = Float.parseFloat(attaque.getAttribute("vitesse"));
				float porteeA = Float.parseFloat(attaque.getAttribute("portee"));
				
				return new Unite(pos, vieMax, camp, rayonUnite, cout, degatA, vitesseA, porteeA, vitesseD, imageName, typeU);
				
			} else { return null; }
		}
		else {
			Node u = Outils.getChild(typeU.toString(), root);
			if (u != null) {
				
				Element carac = (Element)Outils.getChild("Caracteristiques", u);
				Element attaque = (Element)Outils.getChild("Attaque", u);
				
				int cout = Integer.parseInt(carac.getAttribute("cout"));
				int rayonUnite = Integer.parseInt(carac.getAttribute("rayonUnite"));
				String imageName = carac.getAttribute("imageName");
				int degatA = Integer.parseInt(attaque.getAttribute("degat"));
				float vitesseA = Float.parseFloat(attaque.getAttribute("vitesse"));
				float porteeA = Float.parseFloat(attaque.getAttribute("portee"));
				
				return new Defense(pos, camp, rayonUnite, cout, degatA, vitesseA, porteeA, imageName, typeU);
				
			} else { return null; }
		}
		
	}
	
	/**
	 * Donne le prix de l'unite voulue
	 * @param typeU type de l'unite 
	 * @return le cout de cette unite
	 */
	public int getCout(TypeUnite typeU) {
		Node u = Outils.getChild(typeU.toString(), root);
		if (u != null) {
			Element carac = (Element)Outils.getChild("Caracteristiques", u);
			
			return Integer.parseInt(carac.getAttribute("cout"));
		} else { return 0; }
		
	}
	
	

}
