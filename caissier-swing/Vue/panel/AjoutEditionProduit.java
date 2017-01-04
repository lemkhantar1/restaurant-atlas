package panel;

 import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Constantes.Constantes;
import jdbc.GestionBaseDonnee;

public class AjoutEditionProduit extends JPanel
{
	String imageFile = "admin.jpg";
	JPanel formulairePan = new JPanel();
		JPanel champsFormulairePan = new JPanel();
			JLabel titre = new JLabel("Ajout / Modification des produits : ");
			JLabel champIdLabel = new JLabel("id du Produit");
			JTextField champId = new JTextField();
			JLabel champIntituleLabel = new JLabel("Intitule ");
			JTextField champIntitule = new JTextField();
			JLabel champPrixUnitaireLabel = new JLabel("Prix Unitaire ");
			JTextField champPrixUnitaire = new JTextField();
			JLabel champDispoLabel = new JLabel("disponibilite ");
			String dispo[] = {"disponible","non disponible"};
			JComboBox<String> champDispo = new JComboBox<String>(dispo);
			JLabel champCategorieLabel = new JLabel("categorie");
			DefaultComboBoxModel model = new DefaultComboBoxModel();
			JComboBox champCategorie = new JComboBox(model);

		JPanel BouttonFormulairePan = new JPanel();
			JButton appliquer = new JButton("Appliquer");
			JButton vider = new JButton("Vider les champs");
			JButton annuler = new JButton("Annuler");
			
	GestionBaseDonnee db;
	
	public AjoutEditionProduit()
	{

		this.setSize(800, 500);
		this.setLayout(null);
		
///////////////////////////////////////////////////////////////////////////

		try {
			db = new GestionBaseDonnee(Constantes.ip_database+":"+Constantes.port_database, Constantes.name_database, Constantes.login_database, Constantes.passwd_database);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
///////////////////////////////////////////////////////////////////////////
		formulairePan.setBounds(20, 120, 650, 450);
		formulairePan.setBackground(new Color(255, 255, 255, 200));
		formulairePan.add(titre);
		formulairePan.add(champsFormulairePan);
		formulairePan.add(BouttonFormulairePan);
		formulairePan.setLayout(null);
		remplirComboCategorie();
		
///////////////////////////////////////////////////////////////////////////
		champsFormulairePan.setBounds(100, 70, 400, 250);
		champsFormulairePan.setLayout(new GridLayout(5,2));
		champsFormulairePan.add(champIdLabel);
		champsFormulairePan.add(champId);
		champsFormulairePan.add(champIntituleLabel);
		champsFormulairePan.add(champIntitule);
		champsFormulairePan.add(champDispoLabel);
		champsFormulairePan.add(champDispo);
		champsFormulairePan.add(champPrixUnitaireLabel);
		champsFormulairePan.add(champPrixUnitaire);
		champsFormulairePan.add(champCategorieLabel);
		champsFormulairePan.add(champCategorie);
		champsFormulairePan.setOpaque(false);

			
//////////////////////////////////////////////////////////////////////////
		BouttonFormulairePan.setBounds(120, 370, 400, 50);
		BouttonFormulairePan.setLayout(new GridLayout(1, 3));
		BouttonFormulairePan.add(appliquer);
		BouttonFormulairePan.add(vider);
		BouttonFormulairePan.add(annuler);
		BouttonFormulairePan.setOpaque(false);
		
//////////////////////////////////////////////////////////////////////////		
		titre.setForeground(new Color(72,49,49));
		Font font = new Font(" TimesRoman ",Font.BOLD,20);
		titre.setFont(font);
		titre.setBounds(40,10,400,50);
		
//////////////////////////////////////////////////////////////////////////	
		this.add(formulairePan);
		this.setVisible(true);
		
		
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
	
	public void remplirComboCategorie()
	{
		try {
			ResultSet resultat = db.recupererListeCategorie();
			model.removeAllElements();
			while(resultat.next())
			{
				model.addElement(resultat.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


		
}
