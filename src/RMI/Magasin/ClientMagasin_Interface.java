package RMI.Magasin;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import Modele.Produit;

public interface ClientMagasin_Interface extends Remote  {
	
	
	public String bienvenue(String name) throws RemoteException;
	public List<Produit> Produits ()throws RemoteException;
	public List<Produit> PanierAll () throws RemoteException;
	public List<Produit> MonPanier (String client) throws RemoteException;
	public boolean AjouterProduit (String produit,String client) throws RemoteException;
	public boolean ViderPanier(String client) throws RemoteException;
	public boolean ValiderPanier(boolean validation) throws RemoteException;
	public boolean PanierVide(String client) throws RemoteException;
	public double TotalPanier(String client) throws RemoteException;



}
