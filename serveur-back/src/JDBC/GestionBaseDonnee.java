package JDBC;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Vector;

import PrincipauxComposants.*;


/*
 * this class represent the main object that guarantee the acces to database.
 * I used the MySql Driver "mysql-connector-java-5.1.38-bin.jar".
 * If you want to configure the access to your database /
 * you can do it by editing the class Constantes.Constantes.
 * This class was edited for all the projects included in the solution, /
 * Thus you can find many functions here that are not used in this program, / 
 * but they will be used in the other fragment of the solution
 * */
public class GestionBaseDonnee {
	
	
	Connection connexion;
	
	public GestionBaseDonnee(String host,String dataBase,String login , String password) throws SQLException {
		this.connexion = DriverManager.getConnection("jdbc:mysql://"+host+"/"+dataBase,login,password);
	}
	
	public Commande recupererCommandeParID(int idCmd) throws SQLException
	{
		
		PreparedStatement prepStat = connexion.prepareStatement("SELECT * FROM commande WHERE id_commande = ? ");
		prepStat.setInt(1, idCmd);
		ResultSet commandeObtenu= prepStat.executeQuery();
		
		if(commandeObtenu.next())
			return cmdTableToObjet(commandeObtenu);
		
		throw new NullPointerException();

	}
	
	public int ajouterCommande() throws SQLException
	{
		Statement prepStat = connexion.createStatement();
		prepStat.executeUpdate("INSERT INTO commande VALUES()");
		
		ResultSet result = prepStat.executeQuery("SELECT MAX(id_commande) FROM commande");
		result.next();
		return result.getInt(1);
		
	}
	
	public void ajouterTable(int idTable) throws SQLException
	{
		PreparedStatement prepStat = connexion.prepareStatement("INSERT INTO tables "+"VALUES(?)");
		prepStat.setInt(1, idTable);
		
		prepStat.executeUpdate();
	}
	
	public void supprimerTable(int idTable) throws SQLException
	{
		PreparedStatement prepStat = connexion.prepareStatement("DELETE FROM tables WHERE id_table = ?");
		prepStat.setInt(1, idTable);
		
		prepStat.executeUpdate();
	}
	
	public void ajouterCategorie(String intituleCategorie,int priorite) throws SQLException
	{
		PreparedStatement prepStat = connexion.prepareStatement("INSERT INTO categorie(intitule_categorie,priorite) "+"VALUES(?,?)");
		prepStat.setString(1, intituleCategorie);
		prepStat.setInt(2, priorite);
		
		prepStat.executeUpdate();
	}
	
	public void supprimerCategorie(String intituleCategorie) throws SQLException
	{
		PreparedStatement prepStat = connexion.prepareStatement("DELETE FROM categorie WHERE intitule_categorie = ?");
		prepStat.setString(1, intituleCategorie);
		
		prepStat.executeUpdate();
	}
	
	public void ajouterServeur(String loginServeur,String motPasseServeur ,String nomServeur,String prenomServeur,String telServeur) throws SQLException
	{
		PreparedStatement prepStat = connexion.prepareStatement("INSERT INTO serveur(login_serveur,password_serveur,nom_serveur,prenom_serveur,tel_serveur) "+"VALUES(?,?,?,?,?)");
		
		prepStat.setString(1, loginServeur);
		prepStat.setString(2, motPasseServeur);
		prepStat.setString(3, nomServeur);
		prepStat.setString(4, prenomServeur);
		prepStat.setString(5, telServeur);
		
		prepStat.executeUpdate();
	}
	
	public void supprimerServeur(int idServeur) throws SQLException
	{
		PreparedStatement prepStat = connexion.prepareStatement("DELETE FROM serveur WHERE id_serveur = ?");
		prepStat.setInt(1, idServeur);
		
		prepStat.executeUpdate();
	}
	
	
	public void ajouterProduit(String intituleProduit,float prixUnitaire,int idCategorie) throws SQLException
	{
		PreparedStatement prepStat = connexion.prepareStatement("INSERT INTO serveur(intitule,prix_unitaire,id_categorie) "+"VALUES(?,?,?)");
		
		prepStat.setString(1, intituleProduit);
		prepStat.setFloat(2, prixUnitaire);
		prepStat.setInt(3, idCategorie);
		
		prepStat.executeUpdate();
	}
	
	public void supprimerProduit(int idProduit) throws SQLException
	{
		PreparedStatement prepStat = connexion.prepareStatement("DELETE FROM produit WHERE id_produit = ?");
		prepStat.setInt(1, idProduit);
		
		prepStat.executeUpdate();
	}
	
	public void modifierCommande(Commande cmd) throws SQLException
	{
		PreparedStatement prepStat = connexion.prepareStatement("UPDATE commande SET prix_total = ?,vip = ?,id_serveur = ?,id_table = ?,date_commande = ? WHERE id_commande = ?");
		
		prepStat.setFloat(1, cmd.getPrixTotal());
		prepStat.setBoolean(2, cmd.isVip());
		prepStat.setInt(3, cmd.getIdServeur());
		prepStat.setInt(4, cmd.getIdTable());
		prepStat.setTimestamp(5, cmd.getDateCommande());
		prepStat.setInt(6, cmd.getIdCommande());
		
		prepStat.executeUpdate();
		

		for(int i =0 ;i<cmd.getListeProduits().size();i++)
		{
			prepStat = connexion.prepareStatement("INSERT INTO produit_commande VALUES(?,?,?)");
			
			prepStat.setInt(1, cmd.getIdCommande());
			prepStat.setInt(2, cmd.getListeProduits().get(i).getIdProduit());
			prepStat.setInt(3, cmd.getListeProduits().get(i).getQuantite());
			
			
			prepStat.executeUpdate();
		}
	}
	
	public void supprimerCommande(int idCommande) throws SQLException
	{
		PreparedStatement prepStat = connexion.prepareStatement("DELETE FROM commande WHERE id_commande = ?");
		prepStat.setInt(1, idCommande);
		
		prepStat.executeUpdate();
	}
	
	public Vector<Produit> recupererListeProduitParCategorie(int idCategorie) throws SQLException
	{
		PreparedStatement prepStat = connexion.prepareStatement("SELECT * FROM produit WHERE id_categorie = ?");
		prepStat.setInt(1, idCategorie);
		
		ResultSet result= prepStat.executeQuery();
		
		Vector<Produit> listeProduits = new Vector<Produit>();
		
		while (result.next()) {
			
			listeProduits.addElement(produitTableToObject(result));
			
		}
		
		return listeProduits;
		
	}
	
	public ResultSet recupererListeServeur() throws SQLException
	{
		Statement prepStat = connexion.createStatement();
		
		ResultSet result= prepStat.executeQuery("SELECT * FROM serveur");
		
		return result;
		
	}
	
	public Vector<Commande> recupererListeCommandeParServeur(int idServeur) throws SQLException
	{

		PreparedStatement prepStat = connexion.prepareStatement("SELECT * FROM commande WHERE id_serveur = ?");
		prepStat.setInt(1, idServeur);
		
		ResultSet result= prepStat.executeQuery();
		
		Vector<Commande> listeCommande = new Vector<Commande>();
		while (result.next()) {
			
			listeCommande.addElement(cmdTableToObjet(result));
			
		}
		
		return listeCommande;
		
	}
	
	public Vector<Commande> recupererListeCommandeParTable(int idTable) throws SQLException
	{
		PreparedStatement prepStat = connexion.prepareStatement("SELECT * FROM commande WHERE id_table = ?");
		prepStat.setInt(1, idTable);
		
		ResultSet result= prepStat.executeQuery();
		
		Vector<Commande> listeCommande = new Vector<Commande>();
		while (result.next()) {
			
			listeCommande.addElement(cmdTableToObjet(result));
			
		}
		
		return listeCommande;
	}
	
	public Vector<Commande> recupererListeCommandeParDate(Timestamp dateDebut,Timestamp dateFin) throws SQLException
	{
		
		
		PreparedStatement prepStat = connexion.prepareStatement("SELECT * FROM commande WHERE date_commande BETWEEN ? and ?");
		prepStat.setTimestamp(1, dateDebut);
		prepStat.setTimestamp(2, dateFin);
		
		ResultSet result= prepStat.executeQuery();
		
		Vector<Commande> listeCommande = new Vector<Commande>();
		while (result.next()) {
			
			listeCommande.addElement(cmdTableToObjet(result));
			
		}
		
		return listeCommande;
		
	}
	
	public void modifierDisponibiliteServeur(int idServeur,boolean estDisponible) throws SQLException
	{
		PreparedStatement prepStat = connexion.prepareStatement("UPDATE serveur SET enfonction = ? WHERE id_serveur=?");
		
		prepStat.setBoolean(1, estDisponible);
		prepStat.setInt(2, idServeur);
		
		prepStat.executeUpdate();
	}
	
	public Commande cmdTableToObjet(ResultSet commandeObtenu) throws SQLException
	{
		// Recuperer les IDs des produits correspondants � la commande
				PreparedStatement prepStat = connexion.prepareStatement("SELECT * FROM produit_commande WHERE id_commande = ? ");
				prepStat.setInt(1, commandeObtenu.getInt(1));
				ResultSet idProduitsObtenu= prepStat.executeQuery();
				//-----------------------------------------------------------
				
				
				Vector<Produit> listeProduits = new Vector<>();
				while(idProduitsObtenu.next())
				{
					// recuperer produit associ� � l'ID trouv� 
					prepStat = connexion.prepareStatement("SELECT * FROM produit WHERE id_produit = ? ");
					prepStat.setInt(1, idProduitsObtenu.getInt(2));
					ResultSet produitsObtenu= prepStat.executeQuery();
					produitsObtenu.next();
					//-----------------------------------------
					
					// Recuperer priorite correspondante au produit
					prepStat = connexion.prepareStatement("SELECT * FROM categorie WHERE id_categorie = ? ");
					prepStat.setInt(1, produitsObtenu.getInt(5));
					ResultSet categorieObtenu= prepStat.executeQuery();
					categorieObtenu.next();
					int priorite =  categorieObtenu.getInt(3);
					//----------------------------------------------
							
					// Ajouter le produit recuper� � une liste des produits		
					listeProduits.add(new Produit(idProduitsObtenu.getInt(2), produitsObtenu.getFloat(3), produitsObtenu.getString(2),priorite,"", idProduitsObtenu.getInt(3)));
				}
				
				// Prepparation de la commande � retourner
				Commande cmd = new Commande(commandeObtenu.getInt(1), commandeObtenu.getInt(4), commandeObtenu.getInt(5), false, commandeObtenu.getBoolean(3), listeProduits);
				cmd.setDateCommande(commandeObtenu.getTimestamp(6));
				cmd.setPrixTotal(commandeObtenu.getFloat(2));
				//----------------------------------------
						
				return cmd;
	}
	
	public Produit produitTableToObject(ResultSet produitsObtenus) throws SQLException
	{
		
			// Recuperer priorite correspondante au produit
			PreparedStatement prepStat = connexion.prepareStatement("SELECT * FROM categorie WHERE id_categorie = ? ");
			prepStat.setInt(1, produitsObtenus.getInt(5));
			ResultSet categorieObtenu= prepStat.executeQuery();
			categorieObtenu.next();
			int priorite =  categorieObtenu.getInt(3);
			//----------------------------------------------
			
			return new Produit(produitsObtenus.getInt(1), produitsObtenus.getFloat(3), produitsObtenus.getString(2), priorite, "", 0);
	}
	
	
	public int authentification_serveur(String login, String password) throws SQLException
	{
		PreparedStatement prepStat = connexion.prepareStatement("SELECT * FROM serveur WHERE login_serveur = ? and password_serveur = ?");
		prepStat.setString(1, login);
		prepStat.setString(2, password);
		
		ResultSet result= prepStat.executeQuery();
		
		if(result.next())
		{
			return result.getInt(1);
		}
		else
		{
			return 0;
		}
	}
	
	public int authentification_caissier(String login, String password) throws SQLException
	{
		PreparedStatement prepStat = connexion.prepareStatement("SELECT * FROM caissier WHERE login_caissier = ? and password_caissier = ?");
		prepStat.setString(1, login);
		prepStat.setString(2, password);
		
		ResultSet result= prepStat.executeQuery();
		
		if(result.next())
		{
			return result.getInt(1);
		}
		else
		{
			return 0;
		}
	}
	
	public int authentification_cuisinier(String login, String password) throws SQLException
	{
		PreparedStatement prepStat = connexion.prepareStatement("SELECT * FROM cuisinier WHERE login_cuisinier = ? and password_cuisinier = ?");
		prepStat.setString(1, login);
		prepStat.setString(2, password);
		
		ResultSet result= prepStat.executeQuery();
		
		if(result.next())
		{
			return result.getInt(1);
		}
		else
		{
			return 0;
		}
	}
	
	
	public void changerMotDePasseServeur(int idServeur,String newPassword) throws SQLException
	{
		PreparedStatement prepStat = connexion.prepareStatement("UPDATE serveur SET password_serveur = ? WHERE id_serveur=?");
		
		prepStat.setString(1, newPassword);
		prepStat.setInt(2, idServeur);
		
		prepStat.executeUpdate();
	}
	
	public void changerMotDePasseCuisinier(int idCuisinier,String newPassword) throws SQLException
	{
		PreparedStatement prepStat = connexion.prepareStatement("UPDATE cuisinier SET password_cuisinier = ? WHERE id_cuisinier=?");
		
		prepStat.setString(1, newPassword);
		prepStat.setInt(2, idCuisinier);
		
		prepStat.executeUpdate();
	}
	
	public void changerMotDePasseCaissier(int idCaissier,String newPassword) throws SQLException
	{
		PreparedStatement prepStat = connexion.prepareStatement("UPDATE caissier SET password_caissier = ? WHERE id_caissier=?");
		
		prepStat.setString(1, newPassword);
		prepStat.setInt(2, idCaissier);
		
		prepStat.executeUpdate();
	}
	
}

