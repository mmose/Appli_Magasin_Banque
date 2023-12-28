package Modele;

import java.io.Serializable;

public class Produit implements Serializable {
	
	private int numeroProduit;
	private double prix;
	private String nomProduit;
	private String nomClient;
	
	public Produit (int num,String nom, double prix)
	{
		this.numeroProduit=num;
		this.nomProduit=nom;
		this.prix=prix;
	}
	
	public Produit(int num,String nom,String client, double prix)
	{
		this.numeroProduit=num;
		this.nomProduit=nom;
		this.nomClient=client;
		this.prix=prix;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public int getNumeroProduit() {
		return numeroProduit;
	}

	public String getNomClient() {
		return nomClient;
	}

	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}

	public void setNumeroProduit(int numeroProduit) {
		this.numeroProduit = numeroProduit;
	}

	public String getNomProduit() {
		return nomProduit;
	}

	public void setNomProduit(String nomProduit) {
		this.nomProduit = nomProduit;
	}
	

	@Override
	public String toString() {
		return "produit: "+ numeroProduit + " prix: " + prix + " nom: " + nomProduit +"";
	}
	

	public String toString2() {
		return "Produit [numeroProduit=" + numeroProduit + ", prix=" + prix + ", nomProduit=" + nomProduit
				+ ", nomClient=" + nomClient + "]";
	}

	
	
	
	
	
	
	

}
