package panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import composantElementaire.ReglementCommandeRenderer;

public class Serveur_produit_categorie extends JPanel {
	
	public String imageFile = "admin.jpg";
	public JPanel panPrincipale = new JPanel();
		public String titre;
		public JLabel titreLabel = new JLabel();
		public JLabel barLabel = new JLabel("-------------------------------------------");
		
		public DefaultListModel model = new DefaultListModel();
		public JList list = new JList(model);
		public JScrollPane JSP = new JScrollPane(list);
		public JButton[] tabButton;
	
	public Serveur_produit_categorie(String titre)
	{
		tabButton = new JButton[3];
		
		this.titre = titre;
		titreLabel.setText(titre);
		Font trb = new Font("Century Gothic", Font.BOLD, 18);
		titreLabel.setFont(trb);
		titreLabel.setForeground(new Color(72, 49, 49));
		
		this.setLayout(null);
		this.add(panPrincipale);
		
		panPrincipale.setBounds(50, 120, 600, 450);
		panPrincipale.setBackground(new Color(255, 255, 255, 150));
		
		list.setCellRenderer(new ReglementCommandeRenderer());
	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		
		panPrincipale.setLayout(null);
		panPrincipale.add(titreLabel);
		panPrincipale.add(barLabel);
		panPrincipale.add(JSP);
		for (int i = 0; i < tabButton.length; i++) {
			tabButton[i] = new JButton();
			panPrincipale.add(tabButton[i]);
		}
		
		tabButton[0].setBounds(50, 400, 160, 40);
		tabButton[0].setText("Modifier");

		tabButton[1].setBounds(220, 400, 160, 40);
		tabButton[1].setText("Supprimer");

		tabButton[2].setBounds(390, 400, 160, 40);
		tabButton[2].setText("Ajouter");

		JSP.setBounds(60,90,480,300);
		titreLabel.setBounds(10, -20, 500, 100);
		barLabel.setBounds(10, 0, 500, 100);
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
