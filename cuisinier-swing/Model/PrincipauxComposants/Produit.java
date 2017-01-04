package PrincipauxComposants;

import java.io.Serializable;

public class Produit implements Serializable{

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		int idProduit;
		float prixUnitaire;
		String intitule;
		float priorite;
		String description;
		int quantite;
		
		public Produit(int idProduit, float prixUnitaire, String intitule, float priorite, String description, int quantite) {
			this.idProduit = idProduit;
			this.prixUnitaire = prixUnitaire;
			this.intitule = intitule;
			this.priorite = priorite;
			this.description = description;
			this.quantite = quantite;
		}
		
		public int getIdProduit() {
			return idProduit;
		}
		public void setIdProduit(int idProduit) {
			this.idProduit = idProduit;
		}
		public float getPrixUnitaire() {
			return prixUnitaire;
		}
		public void setPrixUnitaire(float prixUnitaire) {
			this.prixUnitaire = prixUnitaire;
		}
		public String getIntitule() {
			return intitule;
		}
		public void setIntitule(String intitule) {
			this.intitule = intitule;
		}
		public float getPriorite() {
			return priorite;
		}
		public void setPriorite(float priorite) {
			this.priorite = priorite;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public int getQuantite() {
			return quantite;
		}

		public void setQuantite(int quantite) {
			this.quantite = quantite;
		}
		
		
}
