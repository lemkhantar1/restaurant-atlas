package pdfCreator;


import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

import PrincipauxComposants.Commande;

public class Impression
{
	public static void creerFacture(Commande cmd)
	{
		String out = "factures/facture-"+cmd.getIdCommande()+".pdf";
		Document document = new Document(PageSize.A8);
		try {

            PdfWriter.getInstance(document, new FileOutputStream(out));
            document.setMargins(0, 0, 0, 0);
            document.open();

            Font d = new Font(Font.FontFamily.HELVETICA,12,Font.BOLD);
            Font d2= new Font(Font.FontFamily.HELVETICA,8,Font.BOLD);
            Paragraph titre = new Paragraph("RESTAURANT ATLAS",d);
            titre.setAlignment(Element.ALIGN_CENTER);
            document.add(titre);
            Paragraph bar = new Paragraph("-----------------------------------------------",d2);
            bar.setAlignment(Element.ALIGN_CENTER);
            document.add(bar);
            String prod = "Table  : "+cmd.getIdTable();
            for(int i=0;i<cmd.getListeProduits().size();i++)
            {
            	prod+="\n"+cmd.getListeProduits().get(i).getQuantite()+"             "+cmd.getListeProduits().get(i).getIntitule()+"             "+cmd.getListeProduits().get(i).getQuantite()*cmd.getListeProduits().get(i).getPrixUnitaire()+" DHS";
            }
            Paragraph produits = new Paragraph(prod,d2);
            produits.setAlignment(Element.ALIGN_CENTER);
            document.add(produits);
            document.add(bar);
            Font d3 = new Font(Font.FontFamily.HELVETICA,10,Font.BOLD);
            Paragraph total = new Paragraph("TOTAL : "+cmd.getPrixTotal()+" DHS",d3);
            total.setAlignment(Element.ALIGN_CENTER);
            document.add(total);
            document.add(bar);
            String det = cmd.getDateCommande().toGMTString();
            det+="\nNumero de commande : "+cmd.getIdCommande()+"\nMERCI POUR VOTRE VISITE !";
            Paragraph details  = new Paragraph(det,d2);
            details.setAlignment(Element.ALIGN_CENTER);
            document.add(details);
            
            
            
            
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
        }
        catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        document.close();
	}
}
