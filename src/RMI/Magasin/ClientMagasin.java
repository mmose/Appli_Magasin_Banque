package RMI.Magasin;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

import RMI.Banque.ClientBanque;
import RMI.Banque.ClientBanque_Interface;
import Modele.Banque;
import Modele.Utilisateur;
import Modele.Produit;


public class ClientMagasin{

	Utilisateur client,serveur_magasin;
	ClientMagasin_Interface obj;
	
	ClientBanque magasin;
	
	public ClientMagasin(Utilisateur c)
	{
		this.client=c;
	}
	
	
	public Boolean ConnexionMagasin()
	{
		try {
			if (client.getType_user()=="client")
			{
				obj = (ClientMagasin_Interface) Naming.lookup ("rmi://localhost:"+client.getPort_user()+"/hello");	
				System.out.println("Client "+client.getUsername()+" connecté !");
				// ICI on mettera l'obj du clientBanque pour le magasin ! 
				//System.out.println(obj.bienvenue(obj.toString()));
				return true;
			}
			else System.out.println("Il faut être un client pour vous connecté !");
			
		}
		catch (Exception e) 
		{ 
			System.out.println ("HelloClient exception: " + e);
		}
		return false;
	}
	
	
	
	public List<Produit> Catalogue()
	{
		if(ConnexionMagasin()==true) 
		{
		try {
		
			return obj.Produits();
			
		} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	
	public List<Produit> MonPanier()
	{
		if(ConnexionMagasin()==true)
		{			
			try {
				return obj.MonPanier(getClientname());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
				
		}
		return null;
	}
	

	public void AjouterPanier(String produit)
	{
		if(ConnexionMagasin()==true)
		{
			try {
				System.out.println(obj.AjouterProduit(produit,client.getUsername()));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void ViderPanier()
	{
		if (ConnexionMagasin()==true)
		{
			try {
				if (obj.ViderPanier(client.getUsername())==true)
				{
					System.out.println("Panier supprimé !");
				}
				else System.out.println("Panier non supprimé !");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public double TotalPanier()
	{
		double montant = 0;
		
		if (ConnexionMagasin()==true)
		{
			try {
				montant = obj.TotalPanier(getClientname());
				return montant;
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		
		return montant;	
	}
	
	
	public String ValiderPanier(boolean validation, Utilisateur serveur_magasin)
	{
		if (ConnexionMagasin()==true)
		{
			try {
				
				if(obj.ValiderPanier(validation)==true && obj.PanierVide(client.getUsername())==false)
				{
					System.out.println("Vous venez de validé votre panier pour un montant de "+TotalPanier());
							
							ClientBanque magasin = new ClientBanque(serveur_magasin);
							if (magasin.ConnexionBanque()==true)
							{
										if (magasin.Authentification(serveur_magasin, 7777)==true)
										{
											this.magasin=magasin;
											this.serveur_magasin=serveur_magasin;
											return "En attente du paiement ...";
										} 
										else return "Authentification du magasin incorrecte";
							
							} return "Connexion à la banque innaccessible";
					
				}
					return "Panier pas validé ";
				
			} 
			catch (RemoteException e) {e.printStackTrace();}
			
		}
		return "Problème de connexion au serveur magasin";
	
	}
	
	public boolean PaiementPanier(int code_client) throws RemoteException 
	{
		if(magasin.Paiement(obj.MonPanier(client.getUsername()),TotalPanier(),serveur_magasin, client, code_client)==true)
		{
			return true;
		}
		return false;
	}
	
	
	
	
	public String getClientname()
	{
		return client.getUsername();
	}
	

}
