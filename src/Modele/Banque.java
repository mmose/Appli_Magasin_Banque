package Modele;

public class Banque {
	
	int numero_compte,code_secret;
	String identifiant_compte;
	double solde;
	
	public Banque (int num, String identifiant , double solde , int code)
	{
		this.numero_compte=num;
		this.identifiant_compte=identifiant;
		this.solde=solde;
		this.code_secret=code;
		
	}

	public int getNumero_compte() {
		return numero_compte;
	}

	public void setNumero_compte(int numero_compte) {
		this.numero_compte = numero_compte;
	}

	public int getCode_secret() {
		return code_secret;
	}

	public void setCode_secret(int code_secret) {
		this.code_secret = code_secret;
	}

	public String getIdentifiant_compte() {
		return identifiant_compte;
	}

	public void setIdentifiant_compte(String identifiant_compte) {
		this.identifiant_compte = identifiant_compte;
	}

	public double getSolde() {
		return solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}

	@Override
	public String toString() {
		return "Banque [numero_compte=" + numero_compte + ", code_secret=" + code_secret + ", identifiant_compte="
				+ identifiant_compte + ", solde=" + solde + "]";
	}
	
	
	
	

}
