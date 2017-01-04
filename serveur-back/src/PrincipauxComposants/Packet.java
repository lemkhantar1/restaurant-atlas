package PrincipauxComposants;
import java.io.Serializable;

import Enumeration.TypePacket;

public class Packet implements Serializable{

	/*
	 *	class representing a packet that transit in the network 
	 */
	private static final long serialVersionUID = 1L;
	
	TypePacket typePacket;
	Commande commande;
	
	public Packet(TypePacket nouvelleCmd, Commande commande2) {
		typePacket = nouvelleCmd;
		commande = commande2;
		
	}
	
	public TypePacket getTypePacket() {
		return typePacket;
	}
	public void setTypePacket(TypePacket typePacket) {
		this.typePacket = typePacket;
	}
	public Commande getCommande() {
		return commande;
	}
	public void setCommande(Commande commande) {
		this.commande = commande;
	}
}
