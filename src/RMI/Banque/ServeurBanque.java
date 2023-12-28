package RMI.Banque;

import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;

import Modele.Utilisateur;


public class ServeurBanque implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7984924266167737444L;
	Utilisateur u;
	
	
	public ServeurBanque(Utilisateur u)
	{
		this.u=u;
	}
	
	public boolean Creation_ServeurBanque()
	{
		try 
		{
		LocateRegistry.createRegistry(u.getPort_user());
		Naming.rebind ("rmi://localhost:"+u.getPort_user()+"/hello", new ClientBanque_Impl());
		System.out.println ("Server "+u.getUsername()+" prêt ! .");
		return true;
		} 
			catch (Exception e) 
			{
				System.out.println ("Server Magasin échec " + e);
			}
		return false;
	}
	
	
	
}
