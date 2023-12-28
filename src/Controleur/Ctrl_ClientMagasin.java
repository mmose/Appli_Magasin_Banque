package Controleur;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Modele.Produit;
import Modele.Utilisateur;
import RMI.Magasin.ServeurMagasin;
import RMI.Magasin.ClientMagasin;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class Ctrl_ClientMagasin {


	@FXML
	private VBox VBox_client;
	@FXML
	private Label titre_projet_rmi;
	@FXML
	private Text magasin_name,port_name,user_name,prix_total_mon_panier;
	@FXML
	private ListView<Produit> liste_produits = new ListView<>();
	@FXML
	private ListView<Produit> liste_mon_panier = new ListView<>();
	
	private Stage stage;
	
	@FXML
	private AnchorPane parent;
	private double xOffset = 0;
	private double yOffset = 0;

	ClientMagasin nouveauclient;
	Utilisateur serveurmagasin;
	
	
	public void initData(Utilisateur clientmagasin, Utilisateur serveurmagasin)
	{		
		nouveauclient = new ClientMagasin(clientmagasin);
		this.serveurmagasin=serveurmagasin;
		
		stage = (Stage) magasin_name.getScene().getWindow();
		magasin_name.setText(serveurmagasin.getUsername());
		port_name.setText(Integer.toString(serveurmagasin.getPort_user()));
		user_name.setText(clientmagasin.getUsername());
		
		ConnexionClient(clientmagasin, serveurmagasin);
	}
	
	
	public void initProduits()
	{
		liste_produits.getItems().addAll(nouveauclient.Catalogue());
	}
	
	
	public void initMonPanier()
	{
		liste_mon_panier.getItems().addAll(nouveauclient.MonPanier());
	}
	
	public void initPrixTotal()
	{
		prix_total_mon_panier.setText(Double.toString(nouveauclient.TotalPanier()));
	}
	
	
	public void ConnexionClient(Utilisateur clientmagasin, Utilisateur serveurmagasin)
	{
		if (nouveauclient.ConnexionMagasin()==true)
		{
			Ecriture_Client_Connecté(clientmagasin, serveurmagasin);
			initProduits();
		}
		else System.out.println("Echec de la connexion au serveur");
	}
	
	public void Ecriture_Client_Connecté(Utilisateur user, Utilisateur magasin)
	{
		try (FileWriter f = new FileWriter("client_magasin.tmp", true);
                BufferedWriter b = new BufferedWriter(f);
                PrintWriter p = new PrintWriter(b);) 
	 	{
            	p.println(user.toString3()+"|"+magasin.getUsername()+"|"+magasin.getPort_user());
        } catch (IOException i) {
            i.printStackTrace();
        }      
	}
	
	
	public void Double_Click_Produit()
	{
		liste_produits.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent click) {
		    	Produit produitselect = new Produit(0, null, null, 0);
		        if (click.getClickCount() == 2) {
		           produitselect = liste_produits.getSelectionModel().getSelectedItem();
		           produitselect.setNomClient(nouveauclient.getClientname());
		           nouveauclient.AjouterPanier(produitselect.getNomProduit());
		           
		           try {
					rafraichir();
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
		           
		        }
		    }
		});
	}
	
	
	public void vider_panier() throws ClassNotFoundException, IOException
	{
		nouveauclient.ViderPanier();
		rafraichir();
	}
	
	public void valider_panier() throws NumberFormatException, IOException, ClassNotFoundException
	{	
		String message = nouveauclient.ValiderPanier(true, serveurmagasin);
		
		if (message=="En attente du paiement ...")
		{
			TextInputDialog dialog = new TextInputDialog("");
			dialog.setTitle("Saissiez votre code secret");
			dialog.setHeaderText("Serveur : "+serveurmagasin.getUsername()+"\n"+"Port : "+serveurmagasin.getPort_user());
			dialog.setContentText("Code secret :");

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
				if (nouveauclient.PaiementPanier(Integer.parseInt(result.get()))==true)
				{
					vider_panier();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Confirmation");
					alert.setHeaderText("Achat effectué !");
					alert.setContentText(serveurmagasin.toString2());
					alert.showAndWait();
				}
				else 
				{
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Information paiement");
					alert.setHeaderText("Code incorrect");
					alert.setContentText(serveurmagasin.toString2());
					alert.showAndWait();
				}
			}
		}
		
		else if (message!="En attente du paiement ...")
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Paiement");
			alert.setHeaderText(message);
			alert.setContentText(serveurmagasin.toString2());
			alert.showAndWait();
		}
		
	}
	
	
	public void rafraichir() throws ClassNotFoundException, IOException
	{
		liste_mon_panier.getItems().clear();
		initMonPanier();
		initPrixTotal();
	}
	
	public void deplacer_fenetre()
	{
		parent.setOnMousePressed((event) -> {
		xOffset= event.getSceneX();
		yOffset=event.getSceneY();
		
		});
		parent.setOnMouseDragged((event) -> {
			stage.setX(event.getScreenX() - xOffset);
			stage.setY(event.getScreenY() - yOffset);
			stage.setOpacity(0.8f);
		});	
		parent.setOnDragDone((event) -> {
			stage.setOpacity(1.0f);
		});	
		parent.setOnMouseReleased((event) -> {
			stage.setOpacity(1.0f);
		});	
		
	}
	
	public void fermer() throws IOException 
	{
		suppresion_ligne_serveur();
		System.out.println("Fermeture du client magasin ");
		System.exit(0);	
	}
	
	public void suppresion_ligne_serveur() throws IOException
	{
        RandomAccessFile file = new RandomAccessFile("client_magasin.tmp", "rw");
        String delete;
        String task="";
        
        while ((delete = file.readLine()) != null) {
            if (delete.startsWith(nouveauclient.getClientname())) {
                continue;
            } 
            task+=delete+"\n";
        }
 
        System.out.println("Supression de la ligne : "+nouveauclient.getClientname()+" dans le fichier tmp");
        BufferedWriter writer = new BufferedWriter(new FileWriter("client_magasin.tmp"));
        writer.write(task);
        file.close();
        writer.close();
	}
	
	
	public void reduire()
	{
	 stage.setIconified(true);
	}
	
	public void setPrimaryStage(Stage s) {
		this.stage = s;
	}




	
	
}
