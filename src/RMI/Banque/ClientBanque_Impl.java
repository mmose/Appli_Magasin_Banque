package RMI.Banque;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import Modele.Compte;
import Modele.Message;
import Modele.Utilisateur;

public class ClientBanque_Impl extends UnicastRemoteObject implements ClientBanque_Interface{

	private Compte compte = new Compte();
	
	protected ClientBanque_Impl() throws RemoteException {
		super();
	}
	
	
	private static final long serialVersionUID = 1L;
	

	@Override
	public boolean Authentification(Utilisateur u, int code) throws RemoteException {
		return compte.Authentification(u.getUsername(),code);
	}
	
	@Override
	public boolean Paiement(Utilisateur u, int code) throws RemoteException {
		return compte.Paiement(u.getUsername(), code);
	}
	
	@Override
	public double ConsulterSolde(Utilisateur u) throws RemoteException {
		return compte.SoldeCompte(u.getUsername());
	}

	@Override
	public boolean Transaction(Utilisateur receveur, Utilisateur payeur, double montant) throws RemoteException {
		return compte.Transaction(payeur, receveur, montant);
	}
	
	@Override
	public List<Message> ConsulterMessage(String clientname) throws RemoteException {
		return compte.getMessageCompte(clientname);
	}

	@Override
	public boolean AjouterMessage(Message m) throws RemoteException {
		return compte.AjouterMessage(m);
	}








}
