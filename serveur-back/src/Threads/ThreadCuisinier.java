package Threads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import Enumeration.EtatCmd;
import Enumeration.TypeMsg;
import PrincipauxComposants.Commande;
import PrincipauxComposants.Message;


/*
 * This is the thread responsible of communication between server and a food preparer
 */
public class ThreadCuisinier extends Thread {
	ThreadBoss threadDuBoss;
	public ThreadCuisinier(ThreadBoss threadBoss) {
		threadDuBoss = threadBoss;
	}
	
	
	@Override
	public void run() {
		Message msgRecu;
		ObjectInputStream in ;
		try {
			
			while(true)
			{
				in = new ObjectInputStream(threadDuBoss.socketDuCuisinier.getInputStream());
				msgRecu = (Message)in.readObject();
				threadDuBoss.envoyerCmdEnFileDattente();
						
				if (msgRecu.getTypeMessage() == TypeMsg.CMD_PRETE)
				{
					System.out.println("cmd prete");
					Commande cmdTmp = threadDuBoss.trouverCmd(msgRecu.getIdCommande());
					cmdTmp.setNbSousCommandes(cmdTmp.getNbSousCommandes()-1);
					cmdTmp.setNbSousCommandesEnCours(cmdTmp.getNbSousCommandesEnCours()-1);
					
					if(cmdTmp.getNbSousCommandes() == 0)
					{
						cmdTmp.setEtat(EtatCmd.PRETE);
						Socket serveurCorrespondant = threadDuBoss.RecupererSocketServeur(msgRecu.getIdCommande());
						
						threadDuBoss.EnvoyerData(serveurCorrespondant, msgRecu);
						
					}	
				}
				else 
				{
					Commande cmdTmp = threadDuBoss.trouverCmd(msgRecu.getIdCommande());
					cmdTmp.setNbSousCommandesEnCours(cmdTmp.getNbSousCommandesEnCours()-1);
					Socket serveurCorrespondant = threadDuBoss.RecupererSocketServeur(msgRecu.getIdCommande());
					threadDuBoss.EnvoyerData(serveurCorrespondant, msgRecu);
				}
				
				
						
			}
		} 
		catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}	
	
}
