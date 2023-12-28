package RMI.Banque;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Modele.Banque;
import Modele.Message;
import Modele.Produit;
import Modele.Utilisateur;


public class ClientBanque {

	ClientBanque_Interface obj;
	Utilisateur client;
	boolean paiementvalide , authentificationvalide;
	
	
	public ClientBanque(Utilisateur c)
	{
		this.client=c;
	}
	
	public String quelport()
	{
		if (client.getTitre().equals("magasin") && client.getType_user().equals("serveur"))
		{
			return Integer.toString(client.getPort_externe());
		}
		else return Integer.toString(client.getPort_user());
	}
	
	
	public Boolean ConnexionBanque() 
	{
		try {
			obj = (ClientBanque_Interface) Naming.lookup ("rmi://localhost:"+quelport()+"/hello");
			return true;
			}
		catch (Exception e) 
		{ 
			System.out.println ("HelloClient exception: " + e);
		}
		return false;
	}
	
	
	public boolean Authentification(Utilisateur c,int code)
	{
		authentificationvalide = false;
		
		if (ConnexionBanque()==true)
		{
			try {
					System.out.println("Authentification en cours ...");
					if (obj.Authentification(c, code)==true)
					{
						System.out.println("Authentification correct !");
						authentificationvalide = true;
					}
					else authentificationvalide=false;
				}
			
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
		return authentificationvalide;
	}
	
	
	public boolean Paiement(List<Produit> paniers, double montant, Utilisateur receveur, Utilisateur payeur , int code)
	{
		paiementvalide=false;
		
		if (ConnexionBanque()==true)
		{
			
			System.out.println("Vous allez faire un achat de "+montant);
			
			if (receveur.getType_user()=="serveur")
			{
				
			try {
				if (obj.Paiement(payeur, code)==true)
				{
					System.out.println("Paiement valide !");
					Mise_A_Jour_Solde(montant,receveur, payeur);
					Ecriture_Mesage(paniers, montant, receveur, payeur);
					paiementvalide=true;
					return paiementvalide;
				}
				else System.out.println("Authentifcation incorrect");
				}
			catch (RemoteException e) {
				e.printStackTrace();
			}
			
			} 
			else System.out.println("Vous devez être un serveur pour effectuer une demande de paiement");
			
		}
		return paiementvalide;
	}
	
	
	public void Mise_A_Jour_Solde(double montant,Utilisateur receveur, Utilisateur payeur)
	{
		try {
			obj.Transaction(receveur, payeur, montant);
			System.out.println("Transaction effectué");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
	public Double Consulter_Solde()
	{
		if (ConnexionBanque()==true)
		{
			if (authentificationvalide==true)
			{
				try {
					return obj.ConsulterSolde(client);
				} catch (RemoteException e) {
				}
			}
			else System.out.println("Vous devez vous authentifier pour consulter votre solde !");
		}
		
		return null;
		
	}
	
	
	public void Ecriture_Mesage(List<Produit> paniers, double montant, Utilisateur receveur, Utilisateur payeur)
	{
		Message credit = new Message(String.valueOf(receveur.getPort_externe()), receveur.getUsername(), "Votre compte a été crédité de "+montant, new Date().toString(), montant, paniers);
		Message debit = new Message(String.valueOf(receveur.getPort_externe()), payeur.getUsername(), "Votre compte a été décrédité de "+montant, new Date().toString(),montant, paniers);
		try {
			System.out.println("Message ajouté ! "+obj.AjouterMessage(credit));
			System.out.println("Message ajouté ! "+obj.AjouterMessage(debit));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<Message> Consulter_Message()
	{
		if (ConnexionBanque()==true)
		{
			try {
				return obj.ConsulterMessage(getClientname());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public String getClientname()
	{
		return client.getUsername();
	}
	

	


}
