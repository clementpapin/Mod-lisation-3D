package projet_modelisation.ply;

import java.util.Arrays;

import projet_modelisation.math.Face;
import projet_modelisation.math.ModeleMatrice;
import projet_modelisation.math.Point;

/**
 * Classe pour les calculs du fichier ply utilise pour la vue
 *
 */
public class PlyModel {
	
	private Point[] points;
	private int cptPoints;
	
	private Point centerPoint;
	
	public ModeleMatrice matrice;
	private Face[] faces;
	private int cptFaces;
	
	/**
	 * Constructeur de la classe qui prend le nombre de face et de point
	 * @param nbPoints
	 * @param nbFaces
	 */
	public PlyModel(int nbPoints, int nbFaces) {
		
		this.cptPoints = 0;
		this.points = new Point[nbPoints];
		this.cptFaces = 0;
		this.faces = new Face[nbFaces];
		this.matrice = new ModeleMatrice(nbPoints);
	}
	
	/**
	 * Fonction qui creer et ajoute un point dans le PlyModel
	 * @param doubleX
	 * @param doubleY
	 * @param doubleZ
	 */
	public void addPoint(double doubleX,double doubleY,double doubleZ) {
		Point point=new Point(doubleX,doubleY,doubleZ);
		this.points[cptPoints] =point;
		this.matrice.addPoint(point);
		cptPoints++;
		if (cptPoints==this.points.length) {
			this.actualizeCenterPoint();
		}
	}
	/**
	 * Methode qui actualise les points pour garder le centre des points a jour
	 */
	private void actualizeCenterPoint() {
		double totalx=0,totalY=0,totalZ=0;
		int idx=0;
		for (Point p : points) {
			totalx+=p.getCoX();
			totalY+=p.getCoY();
			totalZ+=p.getCoZ();
			idx++;
		}
	
	this.centerPoint = new Point(totalx/idx, totalY/idx, totalZ/idx);
	}
	/**
	 * Methode qui ajoute des faces
	 * @param nbElements
	 * @param indices
	 */
	public void addFace(int nbElements, int[] indices) {
		Face newFace = new Face(nbElements, indices, this.matrice);
		this.faces[cptFaces] = newFace;
		cptFaces++;
	}
	

	/**
	 * Fonction qui calcul la moyenne point pour chaque face
	 */
	public void calculerMoyenneDesPoints() {
		for (Face f : faces) f.moyenneDesPoints();
	}
	
	/**
	 * Retourne un tableau contenant tous les x de chaque point de la face
	 * @param face
	 * @return
	 */
	public double[] getXsOfFace(Face face) {
		double[] res = new double[face.getNbPoint()];
		for (int i=0; i<face.getNbPoint(); i++) {
			res[i] = this.matrice.matrice.getValue(0, face.getIndices()[i]);
		}
		return res;
	}
	
	/**
	 * Retourne un tableau contenant tous les y de chaque point de la face
	 * @param face
	 * @return
	 */
	public double[] getYsOfFace(Face face) {
		double[] points= new double[face.getNbPoint()];
		for(int i=0; i<face.getNbPoint(); i++) {
			points[i]=this.matrice.matrice.getValue(1,face.getIndices()[i]);
		}
		return points;
	}
	
	/**
	 * Retourne un tableau contenant tous les y de chaque point de la face
	 * @param face
	 * @return
	 */
	public double[] getZsOfFace(Face face) {
		double[] res = new double[face.getNbPoint()];
		for (int i=0; i<face.getNbPoint(); i++) {
			res[i] = this.matrice.matrice.getValue(2, face.getIndices()[i]);
		}
		return res;
	}
	
	/**
	 * getters et setters utilent a la classe PlyModel
	 * @return
	 */
	public Face[] getFaces() {
		return this.faces;
	}

	/**
	 * getters et setters utilent a la classe PlyModel
	 * @return
	 */
	public Point[] getPoints() {
		return points;
	}

	/**
	 * getters et setters utilent a la classe PlyModel
	 * @param points
	 */
	public void setPoints(Point[] points) {
		this.points = points;
	}
	/**
	 * 
	 * getters et setters utilent a la classe PlyModel
	 * @return
	 */
	public Point getCenterPoint() {
		this.actualizePoints();
		return centerPoint;
	}
	/**
	 * getters et setters utilent a la classe PlyModel
	 * @param centerPoint
	 */
	public void setCenterPoint(Point centerPoint) {
		this.centerPoint = centerPoint;
	}

	/**
	 * getters et setters utilent a la classe PlyModel
	 * @return
	 */
	public int getCptFaces() {
		return cptFaces;
	}

	/**
	 * getters et setters utilent a la classe PlyModel
	 * @param cptFaces
	 */
	public void setCptFaces(int cptFaces) {
		this.cptFaces = cptFaces;
	}

	/**
	 * getters et setters utilent a la classe PlyModel
	 * @param faces
	 */
	public void setFaces(Face[] faces) {
		this.faces = faces;
	}
	
	/**
	 * Trie les faces du modele de la plus loin a la plus proche
	 */
	public void sortFaces() {
		Arrays.sort(this.faces);
	}
	/**
	 * Methode qui actualise les points pour que les coos soient a jour
	 */
	public void actualizePoints() {
		for (int i=0; i<cptPoints; i++) {
			this.points[i].setCoX(this.matrice.getValue(0, i));
			this.points[i].setCoY(this.matrice.getValue(1, i));
			this.points[i].setCoZ(this.matrice.getValue(2, i));
		}
		this.actualizeCenterPoint();
	}
}