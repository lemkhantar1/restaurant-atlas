package composantElementaire;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import PrincipauxComposants.Commande;


public class ReglementCommandeRenderer extends JTextArea implements ListCellRenderer<Commande> {

	@Override
	public Component getListCellRendererComponent(JList<? extends Commande> list, Commande value, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border Bordure;
		Color background;
        Color foreground;
        String chaine="Commande numero : "+value.getIdCommande();
		chaine += "\nServeur : "+value.getIdServeur();
		chaine += "\nTable : "+value.getIdTable();
		chaine += "\n\t\t\tTotal : "+value.getPrixTotal()+" DHs ";
		
		setText(chaine);
		if (isSelected) {
            background = new Color(0,0,0);
            foreground = Color.WHITE;
            Bordure = raisedbevel;
        // unselected, and not the DnD drop location
        } else {
            background =new Color(72,49,49);
            foreground = Color.white;
            Bordure = loweredbevel;
        };

        setBackground(background);
        setForeground(foreground);
        setBorder(Bordure);
		return this;
	}






	
	

}
