package projet_modelisation.math;


/**
 * Class d'un vecteur
 * @author clement.papin.etu
 *
 */
public class Vecteur {
	/**
	 * definition des attributs de la classe vecteur
	 */
	private double coX, coY , coZ;
	
	/**
	 * Constructeur d'un vecteur
	 * @param coX
	 * @param coY
	 * @param coZ
	 */
	public Vecteur(double coX, double coY, double coZ) {
		this.coX=coX;
		this.coY=coY;
		this.coZ=coZ;
	}
	
	/**
	 * Methode pour trouver un vecteur a partir de 2 points
	 * @param ptA
	 * @param ptB
	 */
	public Vecteur(Point ptA, Point ptB) {
		this(ptB.getCoX()-ptA.getCoX(),ptB.getCoY()-ptA.getCoY(),ptB.getCoZ()-ptA.getCoZ());
	}
	
	/**
	 * Methode qui recupere le point X
	 * @return
	 */
	public double getX() {
		return this.coX;
	}
	
	/**
	 * Methode qui recupere le point Y
	 * @return
	 */
	public double getY() {
		return this.coY;
	}
	
	/**
	 * Methode qui recupere le point Z
	 * @return
	 */
	public double getZ() {
		return this.coZ;
	}
	
	/**
	 * Methode qui calcule le produit scalaire d'un vecteur
	 * @param vec
	 * @return
	 */
	public double produitScalaire(Vecteur vec) {
		return coX*vec.coX+coY*vec.coY+coZ*vec.coZ;
	}
	
	/**
	 * Methode qui calcule le produit vectorielle d'un vecteur
	 * @param vec
	 * @return
	 */
	public Vecteur produitVectorielle(Vecteur vec) {
		return new Vecteur((coY*vec.coZ)-(coZ*vec.coY),(coZ*vec.coX)-(coX*vec.coZ),(coX*vec.coY)-(coY*vec.coX));
	}
}