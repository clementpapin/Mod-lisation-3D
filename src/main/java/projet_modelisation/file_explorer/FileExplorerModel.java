package projet_modelisation.file_explorer;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import projet_modelisation.ply.Ply;
import projet_modelisation.ply.PlyModel;
import projet_modelisation.ply.PlyNaNException;
import projet_modelisation.utils.Subject;
/**
 * Classe pour les informations et controle du FileExplorer
 *
 */
public class FileExplorerModel extends Subject{
	
	private File directory;
	private Ply selected;
	private HashMap<String, Ply> files;
	private List<Integer> faceCount;
	
	/**
	 * Constructeur de la classe, initalise les valeurs pour l'affichage
	 */
	public FileExplorerModel() {
		this.directory = new File(System.getProperty("user.dir"));
		this.changeDirectory(directory);
	}
	
	 /**
	  *  Change le dossier contenant les modeles.
	  * @param directory : dossier contenant les modeles.
	  */
	public void changeDirectory(File directory) {
		this.directory = directory;
		this.files = new HashMap<>();
		for (File file : directory.listFiles()) {
			if (file.isFile() && file.getName().endsWith(".ply")) {
				this.files.put(file.getName(), new Ply(file));
			}
		}
		this.notifyObservers(null);
		System.out.println("CHANGED DIRECTORY TO: "+directory.getAbsolutePath()+" CONTAINING "+files.size()+" FILES");
	}
	
	/**
	 * Change le fichier selectionne
	 * @param filename : nom du fichier selectionne
	 */
	public void setSelectedFile(String filename) {
		this.selected = this.files.get(filename);
		this.notifyObservers(this.selected);
	}
	
	/**
	 * Retourne le modele a afficher dans le ModelViewer
	 * @return PlyModel : modele
	 **/
	public PlyModel getModelToShow() {
		PlyModel rep=null;
		try {
			rep= this.selected.loadModel();
		} catch (PlyNaNException e) {
			e.printStackTrace();
		}
		return rep;
	}
	
	/**
 	 * Renvoie la liste des noms de fichier du dossier selectionne.
 	 * @return Iterable<\String\> : liste des noms de fichier
 	 **/
	public Iterable<String> getFilenames() {
		return this.files.keySet();
	}
	
	/**
	 * Simple getter pour la Keyset de la variable local "file"
	 * @return Set<String> 
	 */
	public Set<String> getKeySet(){
		return this.files.keySet();
	}
	
	/**
	 * Simple getter pour avoir les valeur de la variable local "file"
	 * @return Collection<Ply>
	 */
	public Collection<Ply> getPly(){
		return this.files.values();
	}
}