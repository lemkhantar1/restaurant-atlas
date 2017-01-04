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


public class CuisineCommandeRenderer extends JTextArea implements ListCellRenderer<Commande> {

	@Override
	public Component getListCellRendererComponent(JList<? extends Commande> list, Commande value, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border Bordure;
		Color background;
        Color foreground;
        String chaine=" - Commande numero : "+value.getIdCommande();
		
        for(int i=0; i<value.getListeProduits().size() ; i++)
        {
        	chaine+="\n\t - "+value.getListeProduits().get(i).getIntitule()+" :      Quantite = "+value.getListeProduits().get(i).getQuantite();
        	chaine+="\n\t Description : "+value.getListeProduits().get(i).getDescription();
        	chaine+="\n----------------------------------------------";
        }
		
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
