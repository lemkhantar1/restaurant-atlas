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
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import composantElementaire.ReglementCommandeRenderer;

public class tables extends JPanel {
	
	public String imageFile = "admin.jpg";
	public JPanel panPrincipale = new JPanel();
		public String titre = "Administration des tables";
		public JLabel titreLabel = new JLabel();
		public JLabel barLabel = new JLabel("-------------------------------------------");
		
		public DefaultListModel model = new DefaultListModel();
		public JList list = new JList(model);
		public JScrollPane JSP = new JScrollPane(list);
		public JButton[] tabButton;
		SpinnerModel sm = new SpinnerNumberModel(0, 0, 5, 1);
		JSpinner nbTables = new JSpinner(sm);
	
	public tables()
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
		panPrincipale.add(nbTables);
		
		for (int i = 0; i < tabButton.length; i++) {
			tabButton[i] = new JButton();
			panPrincipale.add(tabButton[i]);
		}
		
		tabButton[0].setBounds(65, 395, 160, 40);
		tabButton[0].setText("Supprimer");

		tabButton[1].setBounds(340, 450, 160, 40);
		tabButton[1].setText("Ajouter tables");

		nbTables.setBounds(500, 450, 40, 40);
		


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
