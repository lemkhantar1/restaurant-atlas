package Threads;


/*
 * This is the thread responsible of communication between server and a waiter
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

import javax.smartcardio.CommandAPDU;

import com.sun.javafx.css.CalculatedValue;

import Constantes.Constantes;
import Enumeration.EtatCmd;
import Enumeration.TypeMsg;
import JDBC.GestionBaseDonnee;
import PrincipauxComposants.Commande;
import PrincipauxComposants.Message;
import PrincipauxComposants.Packet;



public class ThreadServeur extends Thread {
	
	
	ThreadBoss threadDuBoss;
	Socket monSocket;
	
	
	public ThreadServeur(ThreadBoss threadBoss, Socket monSocket) {
		threadDuBoss = threadBoss;
		this.monSocket = monSocket;
		
	}
	
	@Override
	public void run() {
		ObjectInputStream OIS;
		Packet packetRecu;
		Commande cmdTmp;
		try
		{
			
			while(true)
			{
				OIS = new ObjectInputStream(monSocket.getInputStream()) ;
				packetRecu = (Packet)OIS.readObject();
				switch(packetRecu.getTypePacket())
				{
				
					case NOUVELLE_CMD : 
							
							threadDuBoss.listeCommandes.addElement(packetRecu.getCommande());
							
							if(threadDuBoss.nombreCmdEnCours()< Constantes.nbMax)
							{
								packetRecu.getCommande().setNbSousCommandesEnCours(packetRecu.getCommande().getNbSousCommandesEnCours()+1);
								threadDuBoss.EnvoyerData(threadDuBoss.socketDuCuisinier, packetRecu.getCommande());
								threadDuBoss.trouverCmd(packetRecu.getCommande().getIdCommande()).setEtat(EtatCmd.ENCOURS);
								
								Message msgTmp = new Message(packetRecu.getCommande().getIdCommande(), TypeMsg.CMD_ENCOURS);
								threadDuBoss.EnvoyerData(threadDuBoss.RecupererSocketServeur(packetRecu.getCommande().getIdCommande()), msgTmp);
							}
							break;
							
					case AJOUT_SOUS_COM :  
							
							cmdTmp = threadDuBoss.trouverCmd(packetRecu.getCommande().getIdCommande());
							threadDuBoss.ajouterSousCmd(packetRecu.getCommande());
							
							if(threadDuBoss.cmdEstEnCours(cmdTmp))
							{
								cmdTmp.setNbSousCommandes(cmdTmp.getNbSousCommandes() + 1);
								if(threadDuBoss.nombreCmdEnCours() == Constantes.nbMax)
								{
									threadDuBoss.listeSousCommandes.addElement(packetRecu.getCommande());
								}
								else
								{
									cmdTmp.setNbSousCommandesEnCours(cmdTmp.getNbSousCommandesEnCours()+1);
									threadDuBoss.EnvoyerData(threadDuBoss.socketDuCuisinier, packetRecu.getCommande());
									
								}
							}
							
							break;
					
					case SUPPRESSION_SOUS_COM : 
								threadDuBoss.deleteSousCmd(packetRecu.getCommande());
							break;
					
					case RECTIFICATION :
						cmdTmp = threadDuBoss.trouverCmd(packetRecu.getCommande().getIdCommande());
						if(threadDuBoss.nombreCmdEnCours() < Constantes.nbMax)
						{
							cmdTmp.setNbSousCommandesEnCours(cmdTmp.getNbSousCommandesEnCours()+1);
							threadDuBoss.EnvoyerData(threadDuBoss.socketDuCuisinier, packetRecu.getCommande());
						}
						else
						{
							threadDuBoss.listeRectifications.addElement(packetRecu.getCommande());
						}
						break; 
						
					case PAIEMENT : 
						threadDuBoss.calculerPrixTotalCmd(packetRecu.getCommande().getIdCommande());
						cmdTmp = threadDuBoss.trouverCmd(packetRecu.getCommande().getIdCommande());
						System.out.println(cmdTmp.getPrixTotal());
						threadDuBoss.EnvoyerData(threadDuBoss.socketDuCaissier,cmdTmp);
						
						break;
						
					case ANNULATION_CMD:
						cmdTmp = threadDuBoss.trouverCmd(packetRecu.getCommande().getIdCommande());
						threadDuBoss.listeCommandes.remove(cmdTmp);
						threadDuBoss.db.supprimerCommande(cmdTmp.getIdCommande());
						
						break;
					
					
				}
			}
		}
		catch(IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
