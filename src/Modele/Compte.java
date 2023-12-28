package Modele;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Compte {

	private List<Banque> comptes = new ArrayList<>();
	private List<Message> messages = new ArrayList<>();
	
	public Compte()
	{
		comptes.add(new Banque(0001, "Victor", 120.0, 1234));
		comptes.add(new Banque(0002, "Clarisse", 1200.0, 1734));
		comptes.add(new Banque(0003, "Coca", -10.0, 3851));
		comptes.add(new Banque(0004, "Auchan", 10000, 7777));

	}
	
	
	public List<Banque> ListingComptes(){
		return comptes;
	}
	
	
	public int getNumCompte (String client)
	{
		for (Banque B : comptes) 
		{
			if(B.identifiant_compte.contains(client))
			{
				return B.numero_compte;
			}
		}	
		return 0;
	}
	
	
	public Banque ChercherCompte(String client){
		
		int numero_compte = getNumCompte(client);
		for (Banque B : comptes) 
		{
			if(B.numero_compte==numero_compte)
			{
				return B;
			}
		}	
	return null;
	}
	
	
	public boolean Transaction(Utilisateur payeur, Utilisateur receveur, double montant)
	{
		Banque comptePayeur = ChercherCompte(payeur.getUsername());
		Banque compteReceveur = ChercherCompte(receveur.getUsername());
		
		try {
			compteReceveur.setSolde(compteReceveur.getSolde()+montant);
			comptePayeur.setSolde(comptePayeur.getSolde()-montant);
			
			
			return true;
		} catch (Exception e) {}
		
		return false;
	}
	
	public double SoldeCompte(String client)
	{
		Banque compte = ChercherCompte(client);
		return compte.solde;
	}
	
	public boolean Authentification (String client,int code)
	{
		Banque compte = ChercherCompte(client);

		if (compte.identifiant_compte.equals(client) && compte.code_secret==code)
		{
			System.out.println(compte.toString());
			return true;
		}
		else if (compte.identifiant_compte.equals(client) && compte.code_secret!=code)
		{
			System.out.println("Code incorrect");
			return false;
		}
		else
			return false;
	}
	
	
	public boolean Paiement(String client, int code)
	{
		return Authentification(client,code);
	}
	
	
	public List<Message> getMessageCompte(String client)
	{
		List<Message> mesmessages =  new ArrayList<Message>(); 
	
		for (Message m : messages) 
		{
			if(m.getDestinataire().equals(client))
			{
				mesmessages.add(m);
			}
		}	
		return mesmessages;
	}
	
	public Boolean AjouterMessage(Message m)
	{
		messages.add(m);
		return true;
	}
	
	


}
