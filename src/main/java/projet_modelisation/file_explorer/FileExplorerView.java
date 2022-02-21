package projet_modelisation.file_explorer;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.util.Collections;
import java.util.List;

import javafx.scene.input.MouseButton;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import projet_modelisation.ply.Ply;
import projet_modelisation.ply.PlyFormatException;
import projet_modelisation.ply.PlyHeaderException;
import projet_modelisation.utils.Subject;
/**
 * Classe pour la vue javafx du FileExplorer
 *
 */
public class FileExplorerView extends Stage implements projet_modelisation.utils.Observer{
	
	private FileExplorerController controller ;
	
	@FXML
	private Button selectDirectory,name,poly,points;
	@FXML
	private ListView<String> fileList;
	@FXML
	private Label infoLabel;
	
	private boolean nameReverse,polyReverse,pointsReverse;
	private List<String> ordered;
	
	protected DirectoryChooser chooser = new DirectoryChooser();
	
	public void setController(FileExplorerController controller) {
		this.controller = controller;
	}
	
	/**
	 * Initialise la vue :
	 * Cree les EventHandler des boutons et de la liste
	 * 
	 * @param list : Iterable<> contenant tous les noms de fichier
	 */
	public void init(Iterable<String> list) {
		
		this.fileList.setOnMouseClicked( e -> {
			if (e.getButton()==MouseButton.PRIMARY && e.getClickCount()==1) {
				controller.showInformations(this.fileList.getSelectionModel().getSelectedItem());
			} if (e.getButton()==MouseButton.PRIMARY && e.getClickCount()==2) {
				controller.showModel(this.fileList.getSelectionModel().getSelectedItem());
			}
		});
		
		name.setOnAction(e->{
			
			if(!nameReverse) refresh(controller.getFilesOrderedByName());
			else{
				ordered=controller.getFilesOrderedByName();
				Collections.reverse(ordered);
				refresh(ordered);
			}
			nameReverse=!nameReverse;
		});
		
		poly.setOnAction(e->{
			if(!polyReverse) refresh(controller.getFilesOrderedByFace());
			else{
				ordered=controller.getFilesOrderedByFace();
				Collections.reverse(ordered);
				refresh(ordered);
			}
			polyReverse=!polyReverse;
		});
		
		points.setOnAction(e->{
			if(!pointsReverse) refresh(controller.getFilesOrderedByPoints());
			else{
				ordered=controller.getFilesOrderedByPoints();
				Collections.reverse(ordered);
				refresh(ordered);
			}
			pointsReverse=!pointsReverse;
		});
		
		selectDirectory.setOnAction(e -> {
			File file = chooser.showDialog(getOwner());
			if (file!=null) this.controller.changeDirectory(file);
		});
		
		this.refresh(list);
	}
	
	/**
	 * Rafraichit la liste lors du changement de dossier contenant les modeles
	 * @param list : Iterable<> contenant les noms des fichiers
	 */
	private void refresh(Iterable<String> list) {
		this.fileList.getItems().clear();
		for (String s : list) {
			this.fileList.getItems().add(s);
		}
	}
	
	/**
	 * Methode execute chaque changement du modele
	 */
	@Override
	public void update(Subject subj, Object data) {
		boolean needReturn=false;
		if (data==null) {
			this.refresh(controller.getFileNames());
			needReturn=true;
		}
		if(!needReturn) {
			Ply selected = (Ply)data;
			try {
				
				selected.loadInformations();
			} catch (PlyFormatException e) {
				PlyFormatError(selected);
				needReturn=true;
				
			} catch (PlyHeaderException e) {
				PlyHeaderError(selected);
				needReturn=true;
			}
			if(needReturn) return;
			this.infoLabel.setText("Nom: "+selected.getName()+"\n"+
					   "Description: "+selected.getDescription()+"\n"+
					   "Auteur: "+selected.getAutor()+"\n"+
					   "Date de creation: "+selected.getDateCreation()+"\n"+
					   "Nombre de faces: "+selected.getNbFaces()+"\n"+
					   "Nombre de points: "+selected.getNbPoints());
		}
				
	}
	
	/**
	 * Fonction utilise en cas d'une erreur de header 
	 * @param Ply selected
	 */
	protected void PlyHeaderError(Ply selected) {
		Alert error = new Alert(AlertType.ERROR);
		error.setHeaderText("Header non reconnu");
		error.setContentText(selected.getFileName() + " : Un probleme est survenu lors de la lecture du header");
		setTextInfoLabelError(error);
	}
	/**
	 * Fonction utilise en cas d'une erreur de format avec le fichier Ply 
	 * @param Ply selected
	 */
	protected void PlyFormatError(Ply selected) {
		Alert error = new Alert(AlertType.ERROR);
		error.setHeaderText("Format non reconnu");
		error.setContentText(selected.getFileName() + " : Le fichier n'est pas au format ply ou n'est pas en ascii");
		setTextInfoLabelError(error);
	}
	private void setTextInfoLabelError(Alert error) {
		infoLabel.setText("");
		error.showAndWait();
	}
	
}