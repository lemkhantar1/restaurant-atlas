package panel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;

import PrincipauxComposants.Commande;
import PrincipauxComposants.Produit;
import composantElementaire.ReglementCommandeRenderer;
import ecouteur.Ecouteur;
import fenetres.FenetrePrincipale;

public class GestionnairePan extends JPanel
{
	
	Vector<Commande> vectorCommandes = new Vector<Commande>();
	Socket monSocket;
	
	String imageFile = "restaurant.jpg";
	FenetrePrincipale fenetre;
	
	JTabbedPane tabbedpan = new JTabbedPane();
		JPanel gestionnaireDeCommande = new JPanel();
			JLabel titreListe = new JLabel("COMMANDES NON REGLEES : ");
			JLabel barre = new JLabel("--------------------------------");
			JList<Commande> listeCommande = new JList<Commande>();
			JScrollPane JSP = new JScrollPane(listeCommande);
			DefaultListModel<Commande> model = new DefaultListModel<Commande>();
			JButton imprimerButton = new JButton("Imprimer");
			
			public void addCommandeToPanel(Commande cmd)
			{
				vectorCommandes.add(cmd);
				model.addElement(vectorCommandes.lastElement());
			}
			
			public void removeCommandeFromPanel(Commande cmd)
			{
				model.removeElement(cmd);
				vectorCommandes.remove(cmd);
			}
			
			public JList<Commande> getListeCommande() {
				return listeCommande;
			}


			public void setListeCommande(JList<Commande> listeCommande) {
				this.listeCommande = listeCommande;
			}




			public void setImprimerButton(JButton imprimerButton) {
				this.imprimerButton = imprimerButton;
			}


			public void setReglerButton(JButton reglerButton) {
				this.reglerButton = reglerButton;
			}


			public JButton getImprimerButton() {
				return imprimerButton;
			}


			public JButton getReglerButton() {
				return reglerButton;
			}


			JButton reglerButton = new JButton("Regler");

	
	
	public GestionnairePan(FenetrePrincipale fenetre)
	{
		super();
		this.fenetre = fenetre;
		this.setLayout(null);
		
		tabbedpan.add("Gestionnaire de commandes",gestionnaireDeCommande);
		
		tabbedpan.setBounds(50, 120, 600, 500);
		gestionnaireDeCommande.setBackground(new Color(255, 255, 255, 200));
		
		gestionnaireDeCommande.setLayout(null);
		
		Font trb = new Font("Century Gothic", Font.BOLD, 18);
		titreListe.setFont(trb);
		titreListe.setBounds(30, -20, 400, 100);
		titreListe.setForeground(new Color(72, 49, 49));
		
		barre.setBounds(30, -5, 400, 100);

		JSP.setBounds(40,80,500,300);
		
		imprimerButton.setBounds(150,390,120,40);
		imprimerButton.addActionListener(new Ecouteur(this));
		reglerButton.setBounds(300,390,120,40);
		reglerButton.addActionListener(new Ecouteur(this));
		
		gestionnaireDeCommande.add(titreListe);
		gestionnaireDeCommande.add(barre);
		gestionnaireDeCommande.add(JSP);
		gestionnaireDeCommande.add(imprimerButton);
		gestionnaireDeCommande.add(reglerButton);

		listeCommande.setModel(model);
		listeCommande.setCellRenderer(new ReglementCommandeRenderer());
	    listeCommande.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		

		
		this.add(tabbedpan);
		
	}
	
	
	public void paintComponent(Graphics g)
	{
		/*create image icon to get image*/
		ImageIcon imageicon = new ImageIcon(imageFile);
		Image image = imageicon.getImage();
	
		/*Draw image on the panel*/
		super.paintComponent(g);
	
		if (image != null)
			g.drawImage(image, 0, 0, 700, 800, this);
	}





	public Socket getMonSocket() {
		return monSocket;
	}


	public void setMonSocket(Socket monSocket) {
		this.monSocket = monSocket;
	}
}