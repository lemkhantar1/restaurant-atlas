package ecouteur;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Constantes.Constantes;
import Enumeration.TypeMsg;
import PrincipauxComposants.Message;
import jdbc.GestionBaseDonnee;
import panel.AuthentificationPan;
import panel.GestionnairePan;
import pdfCreator.Impression;
import thread.MonThread;

public class Ecouteur implements ActionListener {
	
	JPanel pan;
	GestionBaseDonnee db;

	public Ecouteur(JPanel pan)
	{
		
			this.pan = pan;
			try {
				System.out.println("connecting...");
				this.db =  new GestionBaseDonnee(Constantes.ip_database+":"+Constantes.port_database, Constantes.name_database, Constantes.login_database, Constantes.passwd_database);
				System.out.println("database connected");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(pan instanceof AuthentificationPan)
		{
			
			AuthentificationPan panTmp = (AuthentificationPan)pan ;
			if(arg0.getSource() == panTmp.getButtonlogin())
			{
				int reponse;
				try {
					reponse = db.authentification_caissier(panTmp.getLogin().getText(), new String(panTmp.getMdp().getPassword()));
					System.out.println("Authe "+reponse);
					if(reponse == 0)
					{
						panTmp.getErreurLabel().setForeground(Color.RED);
						panTmp.getErreurLabel().setText("Erreur d'authetification");
					}
					else
					{
						try {
							panTmp.getFenetre().gestionnairePan.setMonSocket(new Socket(Constantes.ip_database,Constantes.port));
							PrintWriter pw = new PrintWriter(panTmp.getFenetre().gestionnairePan.getMonSocket().getOutputStream());
							pw.println("caissier");
							pw.flush();
							MonThread thread = new MonThread(panTmp.getFenetre().gestionnairePan.getMonSocket(),panTmp.getFenetre().gestionnairePan);
							thread.start();
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						panTmp.getFenetre().remove(panTmp);
						panTmp.getFenetre().add(panTmp.getFenetre().gestionnairePan);
						panTmp.getFenetre().validate();
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		else if(pan instanceof GestionnairePan)
		{
			GestionnairePan panTmp = (GestionnairePan)pan ;
			if(arg0.getSource() == panTmp.getImprimerButton())
			{
				panTmp.getListeCommande().getSelectedValue().setDateCommande(new Timestamp(System.currentTimeMillis()));
				Impression.creerFacture(panTmp.getListeCommande().getSelectedValue());
				JOptionPane.showMessageDialog(null, "La facture est generee dans le dossier factures.");
				try
				{
					Message msg = new Message(panTmp.getListeCommande().getSelectedValue().getIdCommande(),TypeMsg.FACTURE_IMPRIMEE);
					ObjectOutputStream OOS = new ObjectOutputStream(panTmp.getMonSocket().getOutputStream());
					OOS.writeObject(msg);
					OOS.flush();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
			else if(arg0.getSource() == panTmp.getReglerButton())
			{
				try
				{
					Message msg = new Message(panTmp.getListeCommande().getSelectedValue().getIdCommande(),TypeMsg.FACTURE_REGLE);
					ObjectOutputStream OIS = new ObjectOutputStream(panTmp.getMonSocket().getOutputStream());
					OIS.writeObject(msg);
					OIS.flush();
					panTmp.removeCommandeFromPanel(panTmp.getListeCommande().getSelectedValue());
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				
			}

		}
		
	}


}
