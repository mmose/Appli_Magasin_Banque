package RMI.Banque;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import Modele.Message;
import Modele.Utilisateur;

public interface ClientBanque_Interface extends Remote {
	
	
	public boolean Authentification (Utilisateur u, int code) throws RemoteException;
	public boolean Paiement (Utilisateur u , int code) throws RemoteException;
	public double ConsulterSolde (Utilisateur u) throws RemoteException;
	public boolean  Transaction(Utilisateur receveur, Utilisateur payeur, double montant) throws RemoteException;
	public boolean AjouterMessage(Message m)throws RemoteException;
	public List<Message> ConsulterMessage(String clientname) throws RemoteException;



}
