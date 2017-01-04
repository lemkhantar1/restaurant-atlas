package panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import composantElementaire.ReglementCommandeRenderer;
import ecouteur.Ecouteur;

public class commandes extends JPanel {
	
	public String imageFile = "admin.jpg";
	public JPanel panPrincipale = new JPanel();
		public String titre = "Administration des commandes";
		public JLabel titreLabel = new JLabel();
		public JLabel barLabel = new JLabel("-------------------------------------------");
		
		public DefaultListModel model = new DefaultListModel();
		public JList list = new JList(model);
		public JScrollPane JSP = new JScrollPane(list);
		public JButton[] tabButton;
		public String[] critereFiltre = {"par id_commande","par serveur","par table"};
		public DefaultComboBoxModel modelFiltre = new DefaultComboBoxModel(critereFiltre);
		public JComboBox listeCritere = new JComboBox<>(modelFiltre);
		JTextField idFilter = new JTextField();
		

	
	public commandes()
	{
		tabButton = new JButton[2];
		
		this.titre = titre;
		titreLabel.setText(titre);
		Font trb = new Font("Century Gothic", Font.BOLD, 18);
		titreLabel.setFont(trb);
		titreLabel.setForeground(new Color(72, 49, 49));
		
		this.setLayout(null);
		this.add(panPrincipale);
		
		panPrincipale.setBounds(50, 120, 600, 500);
		panPrincipale.setBackground(new Color(255, 255, 255, 150));
		
		list.setCellRenderer(new ReglementCommandeRenderer());
	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		
		panPrincipale.setLayout(null);
		panPrincipale.add(titreLabel);
		panPrincipale.add(barLabel);
		panPrincipale.add(JSP);
		panPrincipale.add(listeCritere);
		panPrincipale.add(idFilter);
		
		for (int i = 0; i < tabButton.length; i++) {
			tabButton[i] = new JButton();
			panPrincipale.add(tabButton[i]);
		}
		
		tabButton[0].setBounds(65, 350, 160, 40);
		tabButton[0].setText("Supprimer");

		
		tabButton[1].setBounds(65, 400, 160, 40);
		tabButton[1].setText("Filter");
		
		listeCritere.setBounds(240, 400, 200, 40);
		
		idFilter.setBounds(450,400,100,40);

		


		JSP.setBounds(60,90,480,250);
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
