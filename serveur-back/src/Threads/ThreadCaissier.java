package Threads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.SQLException;

import javax.smartcardio.CommandAPDU;

import Enumeration.EtatCmd;
import Enumeration.TypeMsg;
import PrincipauxComposants.Commande;
import PrincipauxComposants.Message;


/*
 * This is the thread responsible of communication between server and a pay-master
 */
public class ThreadCaissier extends Thread {

	ThreadBoss threadDuBoss;
	public ThreadCaissier(ThreadBoss threadBoss) {
		threadDuBoss = threadBoss;
	}
	
	@Override
	public void run() {
		try {
			ObjectInputStream in ;
			Message msgRecu;
			while(true)
			{
				
					in = new ObjectInputStream(threadDuBoss.socketDuCaissier.getInputStream());
					msgRecu= (Message)in.readObject();
					
					Socket serveurCorrepondant = threadDuBoss.RecupererSocketServeur(msgRecu.getIdCommande());
					
					
					if (msgRecu.getTypeMessage() == TypeMsg.FACTURE_REGLE) {
								System.out.println("cmd reglee");
								Commande cmdCorrespondante = threadDuBoss.trouverCmd(msgRecu.getIdCommande());
								threadDuBoss.db.modifierCommande(cmdCorrespondante);
								threadDuBoss.listeCommandes.remove(cmdCorrespondante);
					}
					else
					{
						System.out.println("impression recu du caissier");
					}
					threadDuBoss.EnvoyerData(serveurCorrepondant, msgRecu);
				
			} 
		}
		catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}


}
