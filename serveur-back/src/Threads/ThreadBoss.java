package Threads;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

import Constantes.Constantes;
import Enumeration.EtatCmd;
import Enumeration.TypeMsg;
import JDBC.GestionBaseDonnee;
import PrincipauxComposants.Commande;
import PrincipauxComposants.Message;
import PrincipauxComposants.Produit;


/*
 *		this class is the main Thread that welcomes the clients (food preparer, pay-master or a waiter)
 *		By this class, we receive demand of connection, and we boot a new socket for each type of clients.
 *		
 *		After connecting to a client, this thread is also responsible of receiving 
 *		and depackaging the messages.
 */


public class ThreadBoss extends Thread {
	
	ServerSocket socketDuServeur;
	Socket socketDuCaissier, socketDuCuisinier;
	Hashtable<Integer, Socket> listeServeurs = new Hashtable<Integer, Socket>();
	Vector<Commande> listeCommandes = new Vector<Commande>();
	Vector<Commande> listeSousCommandes = new Vector<Commande>();
	Vector<Commande> listeRectifications = new Vector<Commande>();
	GestionBaseDonnee db;
	
	
	
	public ThreadBoss(ServerSocket socketDuServeur) 
	{
		try {
			db = new GestionBaseDonnee(Constantes.ip_database+":"+Constantes.port_database, Constantes.name_database, Constantes.login_database, Constantes.passwd_database);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.socketDuServeur = socketDuServeur;
	}
    
	public void run() 
	{
		
		BufferedReader buffRead;
		String typeClient;
		Socket socketDuClient;
		Thread ThreadDuClient;
		
		try
		{
			
			while(true)
			{
				socketDuClient = socketDuServeur.accept();
				System.out.println("Client connecte");
				buffRead = new BufferedReader(new InputStreamReader(socketDuClient.getInputStream()));
				typeClient = buffRead.readLine();
				
				switch(typeClient)
				{
					case "caissier" : 
							socketDuCaissier = socketDuClient;
							ThreadDuClient = new ThreadCaissier(this);
							ThreadDuClient.start();
							break;
					case "cuisinier" :
							socketDuCuisinier = socketDuClient;
							ThreadDuClient = new ThreadCuisinier(this);
							ThreadDuClient.start();
							break;
					default :
						System.out.println("id serveur = "+typeClient);
						listeServeurs.put(Integer.parseInt(typeClient),socketDuClient);
						ThreadDuClient = new ThreadServeur(this,socketDuClient);
						ThreadDuClient.start();

						break;
							
						
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}}
	
    public static void main(String[] args)
    {
        
        ServerSocket socketDuServeur;
        try 
        {
	        socketDuServeur = new ServerSocket(Constantes.port);
	        ThreadBoss TB = new ThreadBoss(socketDuServeur);
	        TB.start();
	        System.out.println("Serveur pret !!");
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }}

    Socket RecupererSocketServeur(int idCmd)
	{
		Commande cmdTmp = trouverCmd(idCmd);
		int idServeur = cmdTmp.getIdServeur();
		return listeServeurs.get(idServeur);
	}

	void EnvoyerData(Socket socket , Object msgOUcmd) throws IOException
	{
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(msgOUcmd);
		
	}
	
	int nombreCmdEnCours()
	{
		int nbCmdEncours = 0;
	
		for(int i=0;i<listeCommandes.size();i++)
		{

				nbCmdEncours +=listeCommandes.get(i).getNbSousCommandesEnCours() ;

		}
		
		return nbCmdEncours;}
	
	boolean cmdEstEnCours(Commande cmd)
	{
		int idCmd = cmd.getIdCommande() ;
		Commande cmdTmp = trouverCmd(idCmd);
		
		if(cmdTmp.getEtat() == EtatCmd.ENCOURS)
		{
			return true;
		}
		
		return false;
			
			
		
	}
	
	void ajouterSousCmd(Commande sousCmd)
	{
		Commande cmdMere = trouverCmd(sousCmd.getIdCommande());
		
		for (int i = 0; i < sousCmd.getListeProduits().size(); i++) {
			cmdMere.getListeProduits().add( sousCmd.getListeProduits().get(i) );
		}
		
	}
	
	void deleteSousCmd(Commande sousCmd)
	{
		Commande cmdMere = trouverCmd(sousCmd.getIdCommande());
		for (int i = 0; i < sousCmd.getListeProduits().size(); i++) {
			
			 Produit produitTmp = cmdMere.recupererProduitParId(sousCmd.getListeProduits().get(i).getIdProduit());
			 produitTmp.setQuantite(produitTmp.getQuantite()- sousCmd.getListeProduits().get(i).getQuantite());
			
			 if(produitTmp.getQuantite() == 0)
			 {
				cmdMere.getListeProduits().remove(produitTmp);
			 }
			
		}
	}
	
	Commande trouverCmd(int idCmd)
	{
		for(int i=0;i<listeCommandes.size();i++)
		{
			if(listeCommandes.get(i).getIdCommande() == idCmd)
			{
				return listeCommandes.get(i);
			}
		}
		return null;
	}
		
	Commande premiereCmdEnAttente()
	{
		for(int i=0;i<listeCommandes.size();i++)
		{
			if(listeCommandes.get(i).getEtat() == EtatCmd.EN_ATTENTE)
			{
				return listeCommandes.get(i);
			}
		}
		return null;
	}
	
	void envoyerCmdEnFileDattente() throws IOException
	{
		Commande cmdTmp = null;

		if(listeRectifications.size()>0)
		{
			cmdTmp = listeRectifications.get(0);
			listeRectifications.remove(0);
			
		}
		else if(listeSousCommandes.size()>0)
		{
			cmdTmp = listeSousCommandes.get(0);
			listeSousCommandes.remove(0);
			System.out.println(listeSousCommandes.size());

		}
		else
		{
			cmdTmp=premiereCmdEnAttente();
			
			if(cmdTmp != null)
			{
				cmdTmp.setEtat(EtatCmd.ENCOURS);
				Message msgTmp = new Message(cmdTmp.getIdCommande(), TypeMsg.CMD_ENCOURS);
				EnvoyerData(RecupererSocketServeur(cmdTmp.getIdCommande()), msgTmp);
			}
			
		}
		
		if(cmdTmp != null)
		{
			cmdTmp.setNbSousCommandesEnCours(cmdTmp.getNbSousCommandesEnCours()+1);
			EnvoyerData(socketDuCuisinier, cmdTmp);
		}
		
		
		
	}
	
	void calculerPrixTotalCmd(int idCmd)
	{
		Commande cmdTmp = trouverCmd(idCmd);
		
		float sommeTotal = 0;
		System.out.println("nb de produit :"+cmdTmp.getListeProduits().size());
		for (int i = 0; i < cmdTmp.getListeProduits().size(); i++) {
			System.out.println("Prix : "+ cmdTmp.getListeProduits().get(i).getPrixUnitaire()  + "Qantite : "+ cmdTmp.getListeProduits().get(i).getQuantite());
			sommeTotal += cmdTmp.getListeProduits().get(i).getPrixUnitaire() * cmdTmp.getListeProduits().get(i).getQuantite() ;
		}
		
		System.out.println(sommeTotal);
		cmdTmp.setPrixTotal(sommeTotal);
	
	}
	

}

