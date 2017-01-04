package panel;

 import java.awt.*;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class AjoutEditionServeur extends JPanel
{
	String imageFile = "admin.jpg";
	JPanel formulairePan = new JPanel();
		JPanel champsFormulairePan = new JPanel();
			JLabel titre = new JLabel("Ajout / Modification des serveurs : ");
			JLabel champIdLabel = new JLabel("id du Serveur");
			JTextField champId = new JTextField();
			JLabel champLoginLabel = new JLabel("LOGIN ");
			JTextField champLogin = new JTextField();
			JLabel champNomLabel = new JLabel("NOM  ");
			JTextField champNom = new JTextField();
			JLabel champPrenomLabel = new JLabel("PRENOM ");
			JPasswordField champPrenom = new JPasswordField();
			JLabel champMotdepasseLabel = new JLabel("MOT DE PASSE");
			JTextField champMotdepasse = new JTextField();
			JLabel champTelLabel = new JLabel("TELEPHONE");
			JTextField champTel = new JTextField();

		JPanel BouttonFormulairePan = new JPanel();
			JButton appliquer = new JButton("Appliquer");
			JButton vider = new JButton("Vider les champs");
			JButton annuler = new JButton("Annuler");
	
	public AjoutEditionServeur()
	{

		this.setSize(800, 500);
		this.setLayout(null);
		


		
///////////////////////////////////////////////////////////////////////////
		formulairePan.setBounds(20, 120, 650, 450);
		formulairePan.setBackground(new Color(255, 255, 255, 200));
		formulairePan.add(titre);
		formulairePan.add(champsFormulairePan);
		formulairePan.add(BouttonFormulairePan);
		formulairePan.setLayout(null);
		
///////////////////////////////////////////////////////////////////////////
		champsFormulairePan.setBounds(100, 70, 400, 250);
		champsFormulairePan.setLayout(new GridLayout(6,2));
		champsFormulairePan.add(champIdLabel);
		champsFormulairePan.add(champId);
		champsFormulairePan.add(champLoginLabel);
		champsFormulairePan.add(champLogin);
		champsFormulairePan.add(champNomLabel);
		champsFormulairePan.add(champNom);
		champsFormulairePan.add(champPrenomLabel);
		champsFormulairePan.add(champPrenom);
		champsFormulairePan.add(champMotdepasseLabel);
		champsFormulairePan.add(champMotdepasse);
		champsFormulairePan.add(champTelLabel);
		champsFormulairePan.add(champTel);
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
