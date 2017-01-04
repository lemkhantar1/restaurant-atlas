package thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import PrincipauxComposants.Commande;
import panel.GestionnairePan;

public class MonThread extends Thread {
	
	Socket monSocket;
	ObjectInputStream OIS;
	GestionnairePan panel;
	
	public MonThread(Socket s, GestionnairePan pan)
	{
		monSocket = s;
		panel = pan;
	}
	
	public void run() {
		
		Commande cmdRecu;
		
		super.run();
		try
		{
			while(true)
			{
				OIS = new ObjectInputStream(monSocket.getInputStream());
				cmdRecu = (Commande)OIS.readObject();
				panel.addCommandeToPanel(cmdRecu);
			}
			
		}catch(IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
	}

}
