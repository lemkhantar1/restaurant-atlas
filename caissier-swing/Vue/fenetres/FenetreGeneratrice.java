package fenetres;
import javax.swing.JFrame;
import javax.swing.JPanel;

import panel.AjoutEditionCategorie;
import panel.AjoutEditionProduit;
import panel.AjoutEditionServeur;

public class FenetreGeneratrice extends JFrame {
	
	public JPanel panel;
	
	public FenetreGeneratrice()
	{
		panel = new AjoutEditionCategorie();
		this.setTitle("Administation");
		this.setSize(700, 650);
		this.setLocationRelativeTo(null);
		this.add(panel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}

}
