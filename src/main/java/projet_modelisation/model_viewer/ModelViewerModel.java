package projet_modelisation.model_viewer;

import projet_modelisation.math.Point;
import projet_modelisation.ply.PlyModel;
import projet_modelisation.utils.Subject;
/**
 *Classe pour le Modelviewer et stock un Model Ply
 *
 */
public class ModelViewerModel extends Subject{
	
	private PlyModel modele;
	
	/**
	 * Change le modele affiche
	 * @param newModel
	 */
	public void changeModel(PlyModel newModel) {
		this.modele = newModel;
		this.modele.calculerMoyenneDesPoints();
		this.modele.sortFaces();
		this.notifyObservers(newModel);
	}
	
	/**
	 * Effectue une translation du modele
	 * @param transX
	 * @param transY
	 * @param transZ
	 */
	public void translate(double transX, double  transY, double transZ) {
		this.modele.matrice.translation(new Point(transX,transY,transZ),modele);
		this.notifyObservers(modele);
	}
	
	/**
	 * Effectue une homothetie
	 * @param value
	 */
	public void homothetie(double value) {
		this.modele.matrice.homothetie(modele.getCenterPoint(),value,modele);
		this.notifyObservers(modele);
	}
	
	/**	
	 * Effectue une rotation autour de l'axe X
	 * @param value
	 */
	public void rotationX(double value) {
		this.modele.matrice.rotationX(modele.getCenterPoint(),value,modele);
		this.modele.sortFaces();
		this.notifyObservers(modele);
	}
	
	/**
	 * Effectue une rotation autour de l'axe Y
	 * @param value
	 */
	public void rotationY(double value) {
		this.modele.matrice.rotationY(modele.getCenterPoint(),value,modele);
		this.modele.sortFaces();
		this.notifyObservers(modele);
	}
	
	/**
	 * retourne le modele actuel
	 * @return
	 */
	public PlyModel getPlyModel() {
		return this.modele;
	}
	
	public void setBase() {
		this.modele.matrice.setBase();
	}
	
	public Point getCenterPoint() {
		return this.modele.getCenterPoint();
	}
	
	public void replace() {
		this.modele.matrice.replace();
	}
}
