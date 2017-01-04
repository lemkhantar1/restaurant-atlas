package PrincipauxComposants;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import Enumeration.EtatCmd;

public class Commande implements Serializable{
	
	/*
	 *	class representing an order 
	 */
	private static final long serialVersionUID = 1L;
	
	int idCommande;
	int idServeur;
	int idTable;
	int nbSousCommandes;
	int nbSousCommandesEnCours;
	boolean rectification;
	float prixTotal;
	EtatCmd etat;
	boolean vip;
	Timestamp dateCommande;
	
	

	public Timestamp getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(Timestamp dateCommande) {
		this.dateCommande = dateCommande;
	}

	public float getPrixTotal() {
		return prixTotal;
	}

	public Commande(int idCommande, int idServeur, int idTable,  boolean rectification,
			 boolean vip, Vector<Produit> listeProduits) {
		super();
		this.idCommande = idCommande;
		this.idServeur = idServeur;
		this.idTable = idTable;
		this.rectification = rectification;
		this.vip = vip;
		this.listeProduits = listeProduits;
	
	}

	public int getNbSousCommandesEnCours() {
		return nbSousCommandesEnCours;
	}

	public void setNbSousCommandesEnCours(int nbSousCommandesEnCours) {
		this.nbSousCommandesEnCours = nbSousCommandesEnCours;
	}

	public void setPrixTotal(float prixTotal) {
		this.prixTotal = prixTotal;
	}

	public boolean isRectification() {
		return rectification;
	}

	public void setRectification(boolean rectification) {
		this.rectification = rectification;
	}

	public int getNbSousCommandes() {
		return nbSousCommandes;
	}

	public void setNbSousCommandes(int nbSousCommandes) {
		this.nbSousCommandes = nbSousCommandes;
	}
	
	
	Vector<Produit> listeProduits = new Vector<Produit>();
	
	
	public int getIdCommande() {
		return idCommande;
	}

	public void setIdCommande(int idCommande) {
		this.idCommande = idCommande;
	}

	public int getIdTable() {
		return idTable;
	}

	public void setIdTable(int idTable) {
		this.idTable = idTable;
	}

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

	public Vector<Produit> getListeProduits() {
		return listeProduits;
	}

	public void setListeProduits(Vector<Produit> listeProduits) {
		this.listeProduits = listeProduits;
	}

	public int getIdServeur() {
		return idServeur;
	}
	public void setIdServeur(int idServeur) {
		this.idServeur = idServeur;
	}

	public EtatCmd getEtat() {
		return etat;
	}
	public void setEtat(EtatCmd etat) {
		this.etat = etat;
	}

	public Produit recupererProduitParId(int idProduit) {
		
		for(int i=0;i<listeProduits.size();i++)
		{
			if(listeProduits.get(i).getIdProduit() == idProduit)
			{
				return listeProduits.get(i);
			}
		}
		
		return null;
	}

	

	



	
	


}
