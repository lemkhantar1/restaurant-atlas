package PrincipauxComposants;
import java.io.Serializable;
import java.util.Hashtable;

import Enumeration.TypeMsg;

public class Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int idCommande;
	TypeMsg typeMessage;
	
	
	public Message(int idCommande,TypeMsg typeMessage) {
		this.idCommande = idCommande;
		this.typeMessage = typeMessage;
	}


	public int getIdCommande() {
		return idCommande;
	}


	public void setIdCommande(int idCommande) {
		this.idCommande = idCommande;
	}


	public TypeMsg getTypeMessage() {
		return typeMessage;
	}


	public void setTypeMessage(TypeMsg typeMessage) {
		this.typeMessage = typeMessage;
	}
	
	
	

}
