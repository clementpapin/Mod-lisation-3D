package projet_modelisation.file_explorer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import projet_modelisation.model_viewer.ModelViewer;
import projet_modelisation.ply.Ply;
import projet_modelisation.ply.PlyFormatException;
import projet_modelisation.ply.PlyHeaderException;

/**
 * Classe qui controle l'affichage du FileExplorer
 */
public class FileExplorerController {

	private FileExplorerView view;
	private FileExplorerModel model;

	/**
	 * Setter pour une vue FileExplorerView
	 * @param view
	 */
	public void setView(FileExplorerView view) {
		this.view = view;
	}

	/**
	 * Setter pour un model FileExplorerModel
	 * @param model
	 */
	public void setModel(FileExplorerModel model) {
		this.model = model;
	}
	
	/**
	 * Montre les informations du modele selectionne dans la liste des modeles.
	 * @param filename : nom du fichier selectionne
	 */
	public void showInformations(String filename) {
		this.model.setSelectedFile(filename);
	}
	
	/**
	 * Initialise la vue
	 */
 	public void init() {
 		this.view.init(this.model.getFilenames());
 	}
 	
 	/**
 	 * Renvoie la liste des noms de fichier du dossier slectionne.
 	 * @return Iterable<\String\> : liste des noms de fichier
 	 */
 	public Iterable<String> getFileNames() {
 		return this.model.getFilenames();
 	}
 	/**
 	 * Organise la liste des Ply dans l'ordre alphabethique 
 	 * @return
 	 */
	public List<String> getFilesOrderedByName(){
		Set<String> files=model.getKeySet();
		List<String> res=new ArrayList<>();
		res.addAll(files);
		Collections.sort(res);
		
	return res;
	}
	
	/**
	 * Organise la liste de Ply par le nombre de face
	 * @return
	 */
	public List<String> getFilesOrderedByFace(){
		List<Ply> plyList=getPly();
		List<String> names=new ArrayList<>();

		Collections.sort(plyList,new Comparator<Ply>() {
			public int compare(Ply obj1, Ply obj2) {
				return obj2.getNbFaces()-obj1.getNbFaces();
			}
		});
		
		for(Ply ply : plyList) {
			names.add(ply.getFileName());
		}
		
	return names;
	}


	/**
	 * Organise la liste des Ply dans le fileExplorer par nombre de point
	 * @return
	 */
	public List<String> getFilesOrderedByPoints(){
		List<String> names=new ArrayList<>();
		List<Ply> plyList=getPly();

		Collections.sort(plyList,new Comparator<Ply>() {
			public int compare(Ply obj1, Ply obj2) {
				return obj2.getNbPoints()-obj1.getNbPoints();
			}
		});
		
		for(Ply ply : plyList) {
			names.add(ply.getFileName());
		}
		
	return names;
	}
	/**
	 * Retourne et ajoute la list des Ply
	 * @return
	 */
	private List<Ply> getPly(){
		List<Ply> res=new ArrayList<>();
	    Collection<Ply> collec=model.getPly();
		for(Ply ply : collec) {
			res.add(ply);
			try {
				ply.loadInformations();
			} catch (PlyFormatException | PlyHeaderException e) {}
			
		}
	return res;
	}
 	/**
 	 * Affiche le modele 3D dans un ModelViewer
 	 * @param filename : nom du fichier  
 	 */
 	public void showModel(String filename) {
 		ModelViewer.newView(this.model.getModelToShow());
 	}
 	
 	/**
 	 * Change le dossier contenant les modeles
 	 * @param directory : nouveau dossier contenant les modeles
 	 */
 	public void changeDirectory(File directory) {
 		this.model.changeDirectory(directory);
 	}	
 	
}