package projet_modelisation.math;

import projet_modelisation.ply.PlyModel;

/**
 * Classe des caculs de matrice en fonction du type de calcul : homothetie, rotation, translations, ...
 */
public class ModeleMatrice {

    public Matrice matrice;
    private static final int LINES=4;
    private int column=0;
    protected int idx=0;
    private Matrice base;
    protected Transformations transformations=new Transformations();
   /**
    * Constructeur de la classe
    * @param matrice
    */
    public ModeleMatrice(Matrice matrice){
        this.matrice=matrice;
    }
    
    /**
     * Constructeur de la classe
     * @param nbPoints
     */
    public ModeleMatrice(int nbPoints) {
    	this.matrice=new Matrice(LINES,nbPoints);
    }
    
    /**
     * Ajoute un point a� la matrice
     * @param point
     */
    public void addPoint(Point point){
        matrice.setPoint(0, column, point.getCoX());
        matrice.setPoint(1, column, point.getCoY());
        matrice.setPoint(2, column, point.getCoZ());
        matrice.setPoint(3, column, 1);
        column++;
    }

    /**
     * Translate la matrice par rapport au point p
     * @param point pour la translation de la matrice
     * @return la matrice apres translation
     */
    public void translation(Point point,PlyModel model){
        matrice=transformations.translationMatrice(point).productMatrice(matrice);
        model.actualizePoints();
    }

    /**
     * Permet d'effectuer l'homothethie de la matrice de rapport rapportHomothetie
     * @param centre k le centre de la matrice
     * @param rapportHomothetie le rapport de l'homothetie
     * @return la matrice apres homothetie
     */
    public void homothetie(Point centre, double rapportHomothetie,PlyModel model){
        matrice=calculCentre(centre, rapportHomothetie,"homothetie");
        model.actualizePoints();
    }

    /**
     * Permet d'effectuer la rotation de la matrice de rapport rotation
     * @param centre
     * @param rotation
     * @return la matrice apres rotation
    */
    public void rotationX(Point centre,double rotation,PlyModel model){
        matrice=calculCentre(centre, rotation, "rotationX");
        model.actualizePoints();
    }
    
    /**
     * Permet d'effectuer la rotation de la matrice de rapport rotation
     * @param centre
     * @param rotation
     * @return la matrice apres rotation
    */
    public void rotationY(Point centre,double rotation,PlyModel model) {
        matrice=calculCentre(centre, rotation, "rotationY");
        model.actualizePoints();
    }

   /**
     * Ramene la matrice au centre, effectue une multiplication suivant le type de transformation souhaite et la renvoie au point d'origine
     * tout ca pour simplifier les calculs
     * @param centre le centre de la matrice
     * @param valeur de la transformation (rapport d'omotethie ou rotation);
     * @param typeTransfo le type de transforation, homothetie ou rotation
     * @return la matrice après transformation
     */
    private	Matrice calculCentre(Point centre,double valeur,String typeTransfo){

        Point opposit=new Point(-centre.getCoX(),-centre.getCoY(),-centre.getCoZ());

        Matrice versOrigine=transformations.translationMatrice(opposit);
        Matrice transformation= typeTransfo.equals("homothetie") ? transformations.homothetieMatrice(valeur) : typeTransfo.equals("rotationX") ? transformations.rotationMatriceX(valeur) : transformations.rotationMatriceY(valeur);
        Matrice versCentre=transformations.translationMatrice(centre);

        versOrigine=versOrigine.productMatrice(matrice);
        transformation=transformation.productMatrice(versOrigine); 
        versCentre=versCentre.productMatrice(transformation);

    return versCentre;
    }
    
    /**
     * 
     * @param line
     * @param column
     * @return l'élément de la matrice à l'indice [line] [column]
     */
    public double getValue(int line,int column) {
    	return matrice.getTableau()[line][column];
    }
    
    /**
     * Permet de remplacer la matrice courante par la matrice centree 
     * du modele (permet de recentre le modele)
     */
    public void setBase() {
    	this.matrice=base;
    }
    
    /**
     * Defini la matrice correspondant au centre de la figure comme 
     * la matrice courante cela signifie que lorsque notre modele est
     * au centre de l'ecran, on conserve sa matrice pour pouvoir la reutiliser
     **/
    public void replace() {
    	this.base=matrice;
    }

    /**
     *
     * @return la tableau de points la matrice
     */
	public double[][] getTableau() {
		return matrice.getTableau();
	}
	
	/**
	 * Remplace les points de la matrice avec le tableau de points passé en paramètre
	 * utile pour les tests
	 * @param points
	 */
	public void reset(double[][] points) {
		this.matrice=new Matrice(points);
	}

	/**
	 * Permets de recuperer un point a un indice specifique
	 * @param indice
	 * @return
	 */

	public Point getPoint(int indice) {
		return new Point(this.getValue(0, indice),this.getValue(1, indice),this.getValue(2, indice));
	}
}