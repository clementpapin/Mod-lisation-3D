package projet_modelisation.math;

/**
 * Classe des points en general, chaque point possede une coordonnees X,Y et Z.
 * 
 *
 */
public class Point implements Comparable<Point>{
	private double coX;
	private double coY;
	private double coZ;
	private final static int ZERO =0;
	/**
	 * Constructeur de la classe
	 * @param coX
	 * @param coY
	 * @param coZ
	 */
	public Point(double coX, double coY, double coZ) {
		this.coX = coX;
		this.coY = coY;
		this.coZ = coZ;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean rep = true;
		if (this == obj)
			rep= true;
		else if (obj == null)
			rep= false;
		else if (getClass() != obj.getClass())
			rep= false;
		Point other = (Point) obj;
		if (Double.doubleToLongBits(coX) != Double.doubleToLongBits(other.coX))
			rep= false;
		else if (Double.doubleToLongBits(coY) != Double.doubleToLongBits(other.coY))
			rep= false;
		else if (Double.doubleToLongBits(coZ) != Double.doubleToLongBits(other.coZ))
			rep= false;
		return rep;
	}
	
	public String toString() {
		return "["+coX+","+coY+","+coZ+"]";
	}
	
	public int compareTo(Point arg0) {
		double res = this.coZ - arg0.coZ;
		int rep;
		if (res<ZERO) {
			rep=  -1;
		}else if (res>ZERO) {
			rep=1;
		}else {
			rep=0;
		}
		return rep;
	}

	/**
	 * Getters et setters utiles a la classe Point
	 * @return
	 */
	public double getCoX() {
		return coX;
	}
	public void setCoX(double coX) {
		this.coX = coX;
	}
	public double getCoY() {
		return coY;
	}
	public void setCoY(double coY) {
		this.coY = coY;
	}
	public double getCoZ() {
		return coZ;
	}
	public void setCoZ(double coZ) {
		this.coZ = coZ;
	}
	
}
