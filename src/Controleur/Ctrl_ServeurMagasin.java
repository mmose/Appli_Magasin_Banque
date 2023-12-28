package Controleur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;


import Modele.Produit;
import Modele.Utilisateur;
import RMI.Magasin.ServeurMagasin;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Ctrl_ServeurMagasin  {

	private Stage primaryStage;
	@FXML
	private TextField nom_magasin,port_magasin;
	@FXML
	private ChoiceBox<Utilisateur> liste_port_banque;
	@FXML
	private Text lbl_client,lbl_port;
	@FXML
	private ListView<Utilisateur> list_clients_magasin = new ListView<>();
	List<Utilisateur> utilisateurs_connectes = new ArrayList<>();
	
	@FXML
	private AnchorPane parent;
	private double xOffset = 0 ;
	private double yOffset = 0 ;
	
	String nom_serveur ;
	int port_serveur,port_externe;
	ServeurMagasin serveur;
	
	
	List<Utilisateur> banques = new ArrayList<>();
	
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	
	public void Init()
	{
		try {
			chargement_listes_banques();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void creer_serveur()
	{
		nom_serveur = nom_magasin.getText().toString();
		port_serveur = Integer.parseInt(port_magasin.getText().toString());
		port_externe = liste_port_banque.getSelectionModel().getSelectedItem().getPort_user();
		Utilisateur user = new Utilisateur(nom_serveur,"magasin","serveur", port_serveur, port_externe);
		ServeurMagasin serveur = new ServeurMagasin(user);

		if (serveur.Creation_ServeurMagasin()==true)
		{
			Init_Serveur();
			try {
				ecriture_serveur(user);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else System.out.println("Le serveur n'a pas pu être crée ");
	}
	
	
	public List<Utilisateur> Lecture_Clientmagasin() throws IOException, ClassNotFoundException
	{
		utilisateurs_connectes.clear();
		
		try (BufferedReader br = new BufferedReader(new FileReader("client_magasin.tmp"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       String[] parts = line.split("\\|");
		       String nom_client = parts[0]; 
		       String type_client = parts[1]; 
		       utilisateurs_connectes.add(new Utilisateur(nom_client,type_client,port_serveur));
		    }
		}
		
		 return utilisateurs_connectes;
	}
	
	
	public List<Utilisateur> Lecture_ServeurBanques() throws IOException, ClassNotFoundException
	{
		 banques.clear();
	
		try (BufferedReader br = new BufferedReader(new FileReader("serveur_banque.tmp"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       String[] parts = line.split("\\|");
		       String nom_serveur = parts[0]; 
		       String titre = parts[1]; 
		       String port_serveur = parts[2]; 
		       banques.add(new Utilisateur(nom_serveur,titre, "serveur",Integer.parseInt(port_serveur)));
		    }
		    
		}
		 return banques;
	}
	
	
	
	public void chargement_listes_banques() throws ClassNotFoundException, IOException
	{
		liste_port_banque.getItems().addAll(Lecture_ServeurBanques());
	}
	
	
	public void chargement_listes_utilisateurs() throws ClassNotFoundException, IOException
	{
		list_clients_magasin.getItems().addAll(Lecture_Clientmagasin());
	}
	
	public void rafraichir() throws ClassNotFoundException, IOException
	{
		list_clients_magasin.getItems().clear();
		liste_port_banque.getItems().clear();
		chargement_listes_utilisateurs();
		chargement_listes_banques();
	}
	

	public void ecriture_serveur(Utilisateur user) throws IOException
	{	
		try (FileWriter f = new FileWriter("serveur_magasin.tmp", true);
	                BufferedWriter b = new BufferedWriter(f);
	                PrintWriter p = new PrintWriter(b);) 
		 	{
	            	p.println(user.toString4());
	        } catch (IOException i) {
	            i.printStackTrace();
	        }      
	}
	
	
	public void Init_Serveur()
	{
		lbl_client.setVisible(true);
		lbl_port.setVisible(true);
		
		lbl_client.setText(nom_serveur);
		lbl_port.setText(String.valueOf(port_serveur));
		
		nom_magasin.setEditable(false);
		port_magasin.setEditable(false);
		liste_port_banque.setDisable(true);

		nom_magasin.setDisable(true);
		port_magasin.setDisable(true);
	}
	
	
	public void deplacer_fenetre()
	{
		parent.setOnMousePressed((event) -> {
		xOffset= event.getSceneX();
		yOffset=event.getSceneY();
		
		});
		parent.setOnMouseDragged((event) -> {
			primaryStage.setX(event.getScreenX() - xOffset);
			primaryStage.setY(event.getScreenY() - yOffset);
			primaryStage.setOpacity(0.8f);
		});	
		parent.setOnDragDone((event) -> {
			primaryStage.setOpacity(1.0f);
		});	
		parent.setOnMouseReleased((event) -> {
			primaryStage.setOpacity(1.0f);
		});	
		
	}
	
	public void fermer() throws IOException 
	{
		if (nom_serveur!= null)
			{
			suppresion_ligne_serveur();
			}
		System.out.println("Fermeture de serveurmagasin "+nom_serveur);
		System.exit(0);	
	}
	
	
	public void suppresion_ligne_serveur() throws IOException
	{
        RandomAccessFile file = new RandomAccessFile("serveur_magasin.tmp", "rw");
        String delete;
        String task="";
        
        while ((delete = file.readLine()) != null) {
            if (delete.startsWith(nom_serveur)) {
                continue;
            } 
            task+=delete+"\n";
        }
 
        System.out.println("Supression de la ligne : "+nom_serveur+" dans le fichier tmp");
        BufferedWriter writer = new BufferedWriter(new FileWriter("serveur_magasin.tmp"));
        writer.write(task);
        file.close();
        writer.close();
	}
	
	
	public void reduire()
	{
	 primaryStage.setIconified(true);
	}
	
	public void setPrimaryStage(Stage s) {
		this.primaryStage = s;
	}


}
