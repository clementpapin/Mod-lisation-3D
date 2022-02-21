package projet_modelisation.math;

import java.util.Arrays;

/**
 * Classe des faces en general
 *
 *
 */
public class Face implements Comparable<Face>{

	public Point[] points;
	public Point moyennePoint;
	
	private int[] indicesPoints;
	private ModeleMatrice matrice;
	private Vecteur normal;

	/**
	 * Creation d'une face avec un nombres d'elements definis
	 * @param nbElements
	 */
	public Face(int nbElements) {
		this.points = new Point[nbElements];
		this.indicesPoints = new int[nbElements];
	}


	
	public Face(int nbElements, int[] indices, ModeleMatrice matrice) {
		this(nbElements);
		this.indicesPoints = indices;
		this.matrice = matrice;
	}

	/**
	 * Fonction qui calcul le centre de la face 
	 */
	public void moyenneDesPoints() {
		double doubleX = 0;
		double doubleY = 0;
		double doubleZ = 0;
		for (int i : this.indicesPoints) {
			doubleX = doubleX + this.matrice.getValue(0, i);
			doubleY = doubleY + this.matrice.getValue(1, i);
			doubleZ = doubleZ + this.matrice.getValue(2, i);
		}
		this.moyennePoint = new Point(doubleX/this.indicesPoints.length, doubleY/this.indicesPoints.length, doubleZ/this.indicesPoints.length);
	}

	@Override
	public int compareTo(Face arg0) {
		return this.moyennePoint.compareTo(arg0.moyennePoint);
	}

	@Override
	public boolean equals(Object obj) {
		boolean rep = true;
		if (this == obj)
			rep =true;
		if ((obj == null) || (getClass() != obj.getClass()))
			rep= false;
		Face other = (Face) obj;
		if (!Arrays.equals(points, other.points))
			rep= false;
		return rep;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (Point p : this.points) {
			str.append(p.toString() + " ");
		}
		return str.toString();
	}

	/**
	 * Getters et setters utiles a la class Face
	 * @return
	 */
	public int getNbPoint() {
		return this.indicesPoints.length;
	}
	/**
	 * Getters et setters utiles a la class Face
	 * @return
	 */
	public int[] getIndices() {
		return this.indicesPoints;
	}
	/**
	 * Getters et setters utiles a la class Face
	 * @return
	 */
	public double getMoyenneZ() {
		return this.moyennePoint.getCoZ();
	}
	/**
	 * Getters et setters utiles a la class Face
	 * @return
	 */
	public double getMoyenneY() {
		return this.moyennePoint.getCoY();
	}
	/**
	 * Getters et setters utiles a la class Face
	 * @return
	 */
	public double getMoyenneX() {
		return this.moyennePoint.getCoX();
	}
	/**
	 * Getters et setters utiles a la class Face
	 * @return
	 */
	public Point getMoyennePoint() {
		return this.moyennePoint;
	}
	/**
	 * Calcul la norme entre 2 vecteurs
	 */
	public void calculNorme() {
		Vecteur vec = new Vecteur(this.matrice.getPoint(indicesPoints[0]), this.matrice.getPoint(indicesPoints[1]));
		Vecteur vec2 = new Vecteur(this.matrice.getPoint(indicesPoints[0]), this.matrice.getPoint(indicesPoints[2]));
		this.normal = vec2.produitVectorielle(vec);
	}
	/**
	 * Getters et setters utiles a la class Face
	 * @return
	 */
	public Vecteur getNorme() {
		return this.normal;
	}
	/**
	 * Getters et setters utiles a la class Face
	 * @return
	 */
	public static double getLumiere(Vecteur normeFace, Vecteur lum) {
		return normeFace.produitScalaire(lum);
	}
}