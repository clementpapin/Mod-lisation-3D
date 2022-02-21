package projet_modelisation.ply;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;

/**
 *Classe qui prend en charge les fichier ply avec toute ces données 
 *Utilise pour l'affichage dans le la vue et le fileExplorer
 */
public class Ply implements Comparator<Ply>{
	private File file;
	private int nbFaces;
	private int nbPoints;
	private String name;
	private String description;
	private String dateCreation;
	private String autor;
	private BufferedReader reader;
	private boolean plyFormat;
	private static double coeff;
	private static final int ZERO =0,DEUX=2,TROIS=3;
	
	/**
	 * Constructeur du fichier Ply
	 * On recupere les informations du fichier ply qui nous servirons a creer le model Ply
	 * @param file
	 */
	public Ply(File file) {
		this.plyFormat=true;
		this.file = file;
		try {
			this.reader = new BufferedReader(new FileReader(this.file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	this.closeReader();
	}

	/**
	 * Second constructeur qui prend un String en parametre en guise de chemin pour le fichier
	 * @param path
	 */
	public Ply(String path) {
		this(new File(path));
	}

/**
 * Fonction void qui verifie le fichier, et retourne une erreur si le fichier ne correspond pas a un fichier .ply
 * @throws PlyFormatException
 */
	private void checkFormat() throws PlyFormatException{
		String line = null;
		try {
			line = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		if(!line.equals("ply")) {
			this.closeReader();
			throw new PlyFormatException();
		}
		try {
			line = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] words = line.split(" ");
		if (words.length < DEUX) {
			this.closeReader();
			throw new PlyFormatException();
		}

		if ( (!words[0].equals("format")) || (!words[1].equals("ascii")) ) {
			this.closeReader();
			throw new PlyFormatException();
		}
	}
	
	/**
	 * Fonction qui va charger les informations du type nom, auteur, date de creation
	 * si c'est indique dans le fichier grace a la fonction analyzeLine(String line)
	 * Nous recuperons le nombre de faces et de vertex
	 * @return PlyModel
	 * @throws PlyNaNException
	 */
	public PlyModel loadModel() throws PlyNaNException {
		PlyModel model = new PlyModel(nbPoints, nbFaces);
		String line = "";
		while (!line.equals("end_header")) {
			try {
				line = reader.readLine();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}

		for (int i=0; i<nbPoints; i++) {
			try {
				line = reader.readLine();
			}catch (IOException e) {
				e.printStackTrace();
			}
			if(i==ZERO) getCoeff(line);
			this.readVertex(line, model);
		}

		for (int i=0; i<nbFaces; i++) {
			try {
				line = reader.readLine();
			}catch (IOException e) {
				e.printStackTrace();
			}
			this.readFace(line, model);
		}
		this.closeReader();
	return model;
	}

	/**
	 * Creer une face ayant pour indices des points les informations contenues dans la ligne
	 * @param line
	 * @param model
	 * @throws PlyNaNException
	 */
	private void readFace(String line, PlyModel model) throws PlyNaNException {
		String[] words = line.split(" ");
		if (words.length<DEUX) throw new PlyNaNException();
		try {
			int nbElements = Integer.parseInt(words[0]);
			if (words.length<=nbElements) throw new PlyNaNException();
			int[] indices = new int[nbElements];
			for (int i=0; i<nbElements; i++) {
				indices[i] = Integer.parseInt(words[i+1]);
			}
			model.addFace(nbElements, indices);
		}catch (Exception e) {
			e.printStackTrace();
			throw new PlyNaNException();
		}
	}

	/**
	 * Creer un point ayant pour coordonnes les informations contenues dans la ligne
	 * @param line
	 * @param model
	 * @throws PlyNaNException
	 */
	private void readVertex(String line, PlyModel model) throws PlyNaNException {
		String[] words = line.split(" ");
		if (words.length<TROIS) throw new PlyNaNException();
		try {
			model.addPoint(Double.parseDouble(words[0])*coeff,Double.parseDouble(words[1])*coeff,Double.parseDouble(words[2])*coeff);
		}catch (Exception e) {
			throw new PlyNaNException();
		}
	}

	/**
	 * Fonction void qui cherche dans le fichier .ply les information pour tout les points
	 */
	public void loadInformations() throws PlyFormatException, PlyHeaderException{
		this.checkFormat();
		String line = "";
		try {
			line = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(!line.equals("end_header")) {
			this.analyzeLine(line);
			try {
				line = reader.readLine();
			} catch (IOException e) {
			e.printStackTrace();
			}
		}
		this.closeReader();
	}

	/**
	 * Fonction qui ferme le reader du fichier
	 */
	private void closeReader() {
		try {
			reader.close();
			this.reader = new BufferedReader(new FileReader(this.file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Fonction qui observe la String rentree en parametre et 
	 * @param String line
	 * @throws PlyHeaderException
	 */
	private void analyzeLine(String line) throws PlyHeaderException {
		String[] words = line.split(" ");
		if ( (words.length<=2) && (!words[0].equals("info"))) {
			this.closeReader();
			throw new PlyHeaderException();
		} else if (words[0].equals("info")) {
			initiateData(words);
		}else if(words[0].equals("element")) {
			int neg = -1;
			try {
				neg = Integer.parseInt(words[2]);
			} catch(Exception e) {
				this.closeReader();
				throw new PlyHeaderException();
			}
			switch (words[1]) {
			case "vertex":
				this.nbPoints = neg;
				break;
			case "face":
				this.nbFaces = neg;
				break;
			}
		}else if (words[0].equals("property")) {

		}
	}

	/**
	 * Fonction qui retourne une chaine en un tableau de String
	 * @param words
	 * @return
	 */
	private String getAsString(String[] words) {
		StringBuilder stringB = new StringBuilder();
		for (int i=2; i<words.length; i++) {
			stringB.append(words[i] + (i!=words.length-1?" ":"") );
		}
		return stringB.toString();
	}
	
	/**
	 * Utilise localement pour initialiser les valeur du fichier ply
	 * @param words
	 */
	private void initiateData(String[] words) {
		switch (words[1]) {
		case "nom":
			this.name = this.getAsString(words);
			break;
		case "description":
			this.description = this.getAsString(words);
			break;
		case "dateCreation":
			this.dateCreation = this.getAsString(words);
			break;
		case "auteur":
			this.autor = this.getAsString(words);
			break;
		}
	}
	
	private static void getCoeff(String value) {
		String[] split=value.split(" ");
		double doubleValue=Double.parseDouble(split[0]);
		if(doubleValue>1) coeff= doubleValue>450 ? 1 : 450/doubleValue;
		else coeff= doubleValue>100 ? 1 : 50/doubleValue;
	}

	@Override
	public int compare(Ply obj1, Ply obj2) {
		return obj1.getNbFaces()-obj2.getNbFaces();
	}
	
	/**
	 * getters et setters utilent a la classe Ply
	 * @return
	 */
	public int getNbFaces() {
		return nbFaces;
	}
	/**
	 * getters et setters utilent a la classe Ply
	 * @return
	 */
	public int getNbPoints() {
		return nbPoints;
	}
	/**
	 * getters et setters utilent a la classe Ply
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * getters et setters utilent a la classe Ply
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * getters et setters utilent a la classe Ply
	 * @return
	 */
	public String getDateCreation() {
		return dateCreation;
	}
	/**
	 * getters et setters utilent a la classe Ply
	 * @return
	 */
	public String getAutor() {
		return autor;
	}
	/**
	 * getters et setters utilent a la classe Ply
	 * @return
	 */
	public boolean isFormatPly() {
		return this.plyFormat;
	}
	/**
	 * getters et setters utilent a la classe Ply
	 * @return
	 */
	public String getFileName() {
		return this.file.getName();
	}
	
	
	
}