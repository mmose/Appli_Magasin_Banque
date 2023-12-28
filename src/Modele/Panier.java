package Modele;

import java.util.ArrayList;
import java.util.List;

public class Panier {

	/**
	 * all_produits = LISTE DE TOUS LES PRODUITS DU MAGASIN
	 * paniers = LISTE DE TOUS LES PRODUITS AJOUTES AU PANIER
	 */
	private List<Produit> all_Produits = new ArrayList<>();
	List<Produit> paniers = new ArrayList<Produit>(); 
	
	
	public Panier()
	{
		all_Produits.add(new Produit(1,"Banane",4.0));
		all_Produits.add(new Produit(2,"Mangue",2.0));
		all_Produits.add(new Produit(3,"Sneakers",5.0));
		all_Produits.add(new Produit(4,"Buenos",8.0));
		all_Produits.add(new Produit(5,"Cristaline",1.0));
	}
	
	public List<Produit> ListingProduit(){
		return all_Produits;
	}
	
	public List<Produit> PanierAll()
	{
		return paniers;
	}
	
	/**
	 * METHODE QUI PERMET DE VOIR LE PANIER POUR UN CLIENT
	 * @param saisie_par = nom du clientMagasin
	 * @return UN NOUVEAU PANIER AVEC LES PRODUITS QU'IL A AJOUTES
	 */
	public List<Produit> MonPanier(String saisie_par){
		
		List<Produit> monPanier = new ArrayList<Produit>(); 
		
		for (Produit P : paniers) 
		{
			if(P.getNomClient().equals(saisie_par))
			{
				monPanier.add(P);
			}
		}
		
		return monPanier;
	}
	
	
	/**
	 * METHODE QUI PERMET D'AJOUTER UN ELEMENT DANS LE PANIER
	 * @param saisie = LE PRODUIT
	 * @param saisie_par = LE CLIENT QUI A FAIT LA SAISIE DU PRODUIT
	 */
	public boolean AjouterPanier(String saisie,String saisie_par) {
		for (Produit P : all_Produits)
		{
			if (P.getNomProduit().contains(saisie))
			{
				 paniers.add(new Produit(P.getNumeroProduit(), P.getNomProduit(),saisie_par,P.getPrix()));
				 return true;
			}	
		}
		
		return false;
	}
	
	
	public boolean ViderPanier(String saisie_par) {
	
		List<Produit> monPanier = new ArrayList<Produit>(); 
		
		for (Produit P : paniers) 
		{
			if(P.getNomClient().equals(saisie_par))
			{
				monPanier.add(P);
			}
		}
				try {
					paniers.removeAll(monPanier);
					return true;
				} catch (Exception e) {}
				
				return false;
	}
	
	
	public double TotalPanier(String saisie_par)
	{
		double total = 0;
		
		for (Produit P : paniers) 
		{
			if(P.getNomClient().equals(saisie_par))
			{
				total = P.getPrix()+total;
			}
			else System.out.println("Impossible de faire le total car il n'y a pas d'article pour l'utilisateur "+saisie_par);
		}
		
		return total;
	}


}
