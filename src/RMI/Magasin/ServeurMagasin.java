package RMI.Magasin;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import RMI.Banque.ClientBanque;
import Modele.Utilisateur;


public class ServeurMagasin {
	
	Utilisateur u;

	
	public ServeurMagasin(Utilisateur u)
	{
		this.u=u;
	}
	
	public boolean Creation_ServeurMagasin()
	{
		try 
		{
			if (u.getType_user()=="serveur")
			{
				LocateRegistry.createRegistry(u.getPort_user());
				Naming.rebind ("rmi://localhost:"+u.getPort_user()+"/hello", new ClientMagasin_Impl());
				System.out.println ("Server "+u.getUsername()+" prêt ! .");
				return true;
			}
			else System.out.println("Il faut être un serveur pour effectuer cette opération !");
		} 
			catch (Exception e) 
			{
				System.out.println ("Server Magasin échec " + e);
			}
		return false;
	}
	
	
	
	

	



}
