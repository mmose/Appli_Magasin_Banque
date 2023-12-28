package Controleur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.DoubleConsumer;


import Modele.Utilisateur;
import RMI.Banque.ClientBanque;
import RMI.Banque.ServeurBanque;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class Ctrl_Client {


	@FXML
	private VBox VBox_client;
	@FXML
	private Label titre_projet_rmi;
	@FXML
	private ListView<Utilisateur> listes_banque = new ListView<>();
	@FXML
	private ListView<Utilisateur> listes_magasin = new ListView<>();
	
	List<Utilisateur> banques = new ArrayList<>();
	List<Utilisateur> magasins = new ArrayList<>();
	
	private Stage primaryStage;
	

	
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
	
	public List<Utilisateur> Lecture_ServeurMagasins() throws IOException, ClassNotFoundException
	{
		 magasins.clear();
		
		try (BufferedReader br = new BufferedReader(new FileReader("serveur_magasin.tmp"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       String[] parts = line.split("\\|");
		       String nom_serveur = parts[0]; 
		       String titre = parts[1]; 
		       String port_serveur = parts[2];
		       String port_externe = parts[4]; 
		       magasins.add(new Utilisateur(nom_serveur,titre, "serveur",Integer.parseInt(port_serveur),Integer.parseInt(port_externe)));
		    }
		    
		}
		 return magasins;
	}
	
	
	public void Initialisation_Magasin() 
	{
		try {
			listes_magasin.getItems().addAll(Lecture_ServeurMagasins());
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Initialisation_Banque()
	{
		try {
			listes_banque.getItems().addAll(Lecture_ServeurBanques());
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Rafraichir_Banque() throws Exception
	{
		listes_banque.getItems().clear();
		Initialisation_Banque();
	}
	
	public void Rafraichir_Magasin() throws Exception
	{
		listes_magasin.getItems().clear();
		Initialisation_Magasin();
	}
	
	
	public void Double_Click_Banque()
	{
		listes_banque.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent click) {
		    	Utilisateur serveurbanque = new Utilisateur(null, null, null, 0);
		        if (click.getClickCount() == 2) {
		           serveurbanque = listes_banque.getSelectionModel().getSelectedItem();
		           System.out.println("Double clique sur "+serveurbanque.toString2());
		           Boite_Dialogue_Banque(serveurbanque);
		        }
		    }
		});
	}
	
	
	public void Double_Click_Magasin()
	{
		listes_magasin.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent click) {
		    	Utilisateur serveurmagasin = new Utilisateur(null, null, null, 0 , 0);
		        if (click.getClickCount() == 2) {
		           serveurmagasin = listes_magasin.getSelectionModel().getSelectedItem();
		           System.out.println("Vous venez de faire un double clique sur "+serveurmagasin.toString2());
		           Boite_Dialogue_Magasin(serveurmagasin);
		        }
		    }
		});
	}
	
	
	public void Boite_Dialogue_Magasin(Utilisateur serveurmagasin)
	{
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Saissiez votre nom");
		dialog.setHeaderText("Serveur : "+serveurmagasin.getUsername()+"\n"+"Port : "+serveurmagasin.getPort_user());
		dialog.setContentText("Nom :");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			Utilisateur clientmagasin = new Utilisateur(result.get(), "client",serveurmagasin.getPort_user());
	           
			try {
				Acces_Magasin(clientmagasin,serveurmagasin);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}

	}
	
	public void Boite_Dialogue_Banque(Utilisateur serveurbanque)
	{
		TextInputDialog username_txt = new TextInputDialog("");
		TextInputDialog pin_txt = new TextInputDialog("");
		    
		username_txt.setTitle("Saissiez votre identifiant de connexion");
		username_txt.setHeaderText("Serveur : "+serveurbanque.getUsername()+"\n"+"Port : "+serveurbanque.getPort_user());
		username_txt.setContentText("Nom utilisateur :");
		
		Optional<String> id = username_txt.showAndWait();
		Optional<String> pin = pin_txt.showAndWait();
		if (id.isPresent() && pin.isPresent())
		
		{
			String username = id.get();
			String code_pin = pin.get();
			Utilisateur clientBanque = new Utilisateur(username,"consommateur","client",serveurbanque.getPort_user());
			
			ClientBanque clientbanque = new ClientBanque(clientBanque);
			if (clientbanque.Authentification(clientBanque,Integer.parseInt(code_pin))==true)
			{
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information : "+serveurbanque.getUsername());
				alert.setHeaderText(null);
				alert.setContentText("Authenfication correcte !");
				alert.showAndWait();
				
				try {
					Acces_Banque(clientBanque,clientbanque,serveurbanque);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} 
			
			else if (clientbanque.Authentification(clientBanque,Integer.parseInt(code_pin))==false)
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Information : "+serveurbanque.getUsername());
				alert.setHeaderText(null);
				alert.setContentText("Authentification incorrecte");
				alert.showAndWait();
			}	
		}
		
	}
	
	
	
    public Stage Acces_Magasin(Utilisateur clientmagasin, Utilisateur serveurmagasin) throws IOException {
    	
  	   FXMLLoader loader = new FXMLLoader(
  	     getClass().getResource(
  	       "/Vue/clientmagasin.fxml"
  	     )
  	   );
  	   
  	  Stage stage;
  	  stage = (Stage) listes_banque.getScene().getWindow();
  	  
  	  Scene scene = new Scene(loader.load());
  	  stage.setScene(scene);
  	  stage.show();
  	  
 	   Ctrl_ClientMagasin controller = loader.getController();
 	   controller.initData(clientmagasin,serveurmagasin);
 	   controller.Double_Click_Produit();

  	   return stage;
  	 
     }
    
    
    public Stage Acces_Banque(Utilisateur clientBanque, ClientBanque clientbanque, Utilisateur serveurbanque) throws IOException {
    	
   	   FXMLLoader loader = new FXMLLoader(
   	     getClass().getResource(
   	       "/Vue/clientbanque.fxml"
   	     )
   	   );
   	   
   	  Stage stage;
   	  stage = (Stage) listes_banque.getScene().getWindow();
   	  
   	  Scene scene = new Scene(loader.load());
   	  stage.setScene(scene);
   	  stage.show();
   	  
  	   Ctrl_ClientBanque controller = loader.getController();
  	   controller.initData(clientBanque,clientbanque,serveurbanque);
  	   controller.Double_Click_Message();

   	   return stage;
   	 
      }

	

	
	
	
	public void setPrimaryStage(Stage s) {
		this.primaryStage = s;
	}


	public void fermer() throws IOException 
	{
		System.out.println("Fermeture du client ");
		System.exit(0);	
	}
	
	
	public void reduire()
	{
	 primaryStage.setIconified(true);
	}

	
	
}
