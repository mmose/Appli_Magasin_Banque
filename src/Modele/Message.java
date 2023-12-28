package Modele;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Message implements Serializable {
	
	String destinataire, expediteur, message,date;
	List<Produit> paniers;
	double montant;
	
	public Message (String expediteur, String destinataire, String message, String date, double montant , List<Produit> paniers) 
	{
		this.destinataire=destinataire;
		this.expediteur=expediteur;
		this.message=message;
		this.montant=montant;
		this.date=date;
		this.paniers=paniers;
	}
	

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public List<Produit> getPaniers() {
		return paniers;
	}

	public void setPaniers(List<Produit> paniers) {
		this.paniers = paniers;
	}


	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getDestinataire() {
		return destinataire;
	}

	public void setDestinataire(String destinataire) {
		this.destinataire = destinataire;
	}

	public String getExpediteur() {
		return expediteur;
	}

	public void setExpediteur(String expediteur) {
		this.expediteur = expediteur;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "De: " + expediteur + "  " +date;
	}
	
	
}
