package RMI.Magasin;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import Modele.Compte;
import Modele.Panier;
import Modele.Produit;



public class ClientMagasin_Impl extends UnicastRemoteObject implements ClientMagasin_Interface{

	private Panier panier = new Panier();
	
	
	public ClientMagasin_Impl () throws RemoteException {
		super();  	
	}
	
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public String bienvenue(String name) throws RemoteException {
		return "Bienvenue sur le Magasin !  "+name;
	}

	@Override
	public List<Produit> Produits() throws RemoteException {
		return panier.ListingProduit();
	}
	
	@Override
	public List<Produit> PanierAll() throws RemoteException {
		return panier.PanierAll();
	}

	@Override
	public List<Produit> MonPanier(String client) throws RemoteException {
		return panier.MonPanier(client);
	}

	@Override
	public boolean AjouterProduit(String produit,String client) throws RemoteException {
		return panier.AjouterPanier(produit,client);
	}

	@Override
	public boolean ViderPanier(String client) throws RemoteException {
		return panier.ViderPanier(client);
	}
	
	@Override
	public double TotalPanier(String client) throws RemoteException {
		return panier.TotalPanier(client);
	}

	@Override
	public boolean ValiderPanier(boolean validation) throws RemoteException {
		if (validation==true)
		{
			return true;
		}
		else return false;
	}

	@Override
	public boolean PanierVide(String client) throws RemoteException {
		if (MonPanier(client).isEmpty())
		{
			return true;
		}
		return false;
	}


	





}
