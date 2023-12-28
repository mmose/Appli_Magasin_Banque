package Modele;

import java.io.Serializable;

public class Utilisateur implements Serializable {
	
	String username,titre;
	int port_user,port_externe;
	String type_user;
	
	
	public Utilisateur (String nom, String titre, String type, int port, int port2)
	{
		this.username=nom;
		this.type_user=type;
		this.port_user=port;
		this.port_externe=port2;
		this.titre=titre;
	}
	
	
	public Utilisateur (String nom, String titre, String type, int port)
	{
		this.username=nom;
		this.type_user=type;
		this.port_user=port;
		this.titre=titre;
	}
	
	public Utilisateur (String nom, String type , int port)
	{
		this.username=nom;
		this.type_user=type;
		this.port_user=port;
	}

	public String getUsername() {
		return username;
	}
	
	public int getPort_externe() {
		return port_externe;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getPort_user() {
		return port_user;
	}

	public void setPort_user(int port_user) {
		this.port_user = port_user;
	}

	public String getType_user() {
		return type_user;
	}

	public void setType_user(String type_user) {
		this.type_user = type_user;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}


	@Override
	public String toString() {
		return ""+ username;
	}

	public String toString2() {
		return ""+ username +"|"+titre+"|"+port_user+"|"+type_user;
	}
	
	public String toString3() {
		return ""+ username +"|"+type_user;
	}
	
	public String toString4() {
		return ""+ username +"|"+titre+"|"+port_user+"|"+type_user+"|"+port_externe;
	}
	
	
	
	

}
