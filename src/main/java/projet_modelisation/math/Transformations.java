package projet_modelisation.math;

import java.lang.Math;

/**
 * Class pour les calculs de transformation des figures
 * @author clement.papin.etu
 *
 */
public class Transformations {
    
	/**
	 * Methode de calcul pour la translation de la figure
	 * @param point
	 * @return
	 */
    public Matrice translationMatrice(Point point){

        double[][] translation=new double[][]{{1,0,0,point.getCoX()},{0,1,0,point.getCoY()},{0,0,1,point.getCoZ()},{0,0,0,1}};

    return new Matrice(translation);
    }

    /**
     * Methode de calcul pour l homothetie de la figure
     * @param homothetie
     * @return
     */
    public Matrice homothetieMatrice(double homothetie){

        double[][] matriceH=new double[][]{{homothetie,0,0,0},{0,homothetie,0,0},{0,0,homothetie,0},{0,0,0,1}};

    return new Matrice(matriceH);
    }

    /**
     * Methode de calcul pour la rotation sur l'axe X de la figure
     * @param angle
     * @return
     */
	public  Matrice rotationMatriceX(double angle) {
		return new Matrice(new double[][] {{1,0,0,0},{0,Math.cos(angle),Math.sin(angle),0},{0,-Math.sin(angle),Math.cos(angle),0},{0,0,0,1}});
	}
	
	/**
	 * Methode de calcul pour la rotation sur l'axe Y de la figure
	 * @param angle
	 * @return
	 */
	public Matrice rotationMatriceY(double angle) {
		return new Matrice(new double[][]{{Math.cos(angle),0,-(Math.sin(angle)),0},{0,1,0,0},{Math.sin(angle),0,Math.cos(angle),0},{0,0,0,1}});
	}
}
