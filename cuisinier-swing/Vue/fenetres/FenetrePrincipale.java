package fenetres;
import pdfCreator.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

import PrincipauxComposants.Commande;
import PrincipauxComposants.Produit;
import panel.AuthentificationPan;
import panel.GestionnairePan;

import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Timestamp;
import java.util.Vector;

public class FenetrePrincipale extends JFrame
{
	public AuthentificationPan authentificationPan = new AuthentificationPan(this);
	public GestionnairePan gestionnairePan = new GestionnairePan(this);
	
	
	public FenetrePrincipale()
	{
		this.setTitle("Restaurant ENSA SAFI");
		this.add(authentificationPan);
		this.setSize(700,650);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	
	
	
	public static void main (String... args)
	{
		

	FenetrePrincipale pd = new FenetrePrincipale();
		//FenetreGeneratrice a = new FenetreGeneratrice();
	}
}