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

public class AjoutEditionCategorie extends JPanel
{
	String imageFile = "admin.jpg";
	JPanel formulairePan = new JPanel();
		JPanel champsFormulairePan = new JPanel();
			JLabel titre = new JLabel("Ajout / Modification des categories : ");
			JLabel champIdLabel = new JLabel("id de la categorie");
			JTextField champId = new JTextField();
			JLabel champIntituleLabel = new JLabel("Intitule ");
			JTextField champIntitule = new JTextField();
			JLabel champPrioriteLabel = new JLabel("Priorite ");
			JTextField champPriorite = new JTextField();

		JPanel BouttonFormulairePan = new JPanel();
			JButton appliquer = new JButton("Appliquer");
			JButton vider = new JButton("Vider les champs");
			JButton annuler = new JButton("Annuler");
			
	GestionBaseDonnee db;
	
	public AjoutEditionCategorie()
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
		
///////////////////////////////////////////////////////////////////////////
		champsFormulairePan.setBounds(100, 130, 400, 100);
		champsFormulairePan.setLayout(new GridLayout(3,2));
		champsFormulairePan.add(champIdLabel);
		champsFormulairePan.add(champId);
		champsFormulairePan.add(champIntituleLabel);
		champsFormulairePan.add(champIntitule);
		champsFormulairePan.add(champPrioriteLabel);
		champsFormulairePan.add(champPriorite);

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
	


		
}
