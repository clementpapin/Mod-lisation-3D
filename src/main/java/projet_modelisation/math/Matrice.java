package projet_modelisation.math;
/**
 * Classe des matrices, permet de stocker les valeurs
 * 
 *
 */
public class Matrice {
	private double[][] matrice;
	private int lines;
	private int columns;
	
	/**
	 * Creer une matrice a l'aide du nombres de lignes et de colonnes
	 * @param lines
	 * @param columns
	 */
	public Matrice(int lines, int columns) {
		this.lines=lines;
		this.columns=columns;
		this.matrice= new double[lines][columns];
	}
	
	/**
	 * Creer une matrice a l'aide d'un tableau à double dimensions
	 * @param matrice
	 */
	public Matrice(double[][] matrice) {
		this.matrice=matrice;
		this.lines=matrice.length;
		this.columns=matrice[0].length;
	}
	
	/**
	 * Methode qui retourne une matrice sous forme de tableau
	 * @return
	 */
	public double[][] getTableau() {
		return this.matrice;
	}
	
	/**
	 * Methode qui permet de faire une multiplication entre deux matrices
	 * @param matrice2
	 * @return
	 */
	public Matrice productMatrice(Matrice matrice2){
		if (this.columns != matrice2.lines) {
			throw new RuntimeException();
		}
		Matrice product = new Matrice(this.lines,matrice2.columns);
		for (int i = 0; i < product.lines; i++)
	        for (int j = 0; j < product.columns; j++)
	            for (int k = 0; k < this.columns; k++)
	                product.matrice[i][j] += (this.matrice[i][k] * matrice2.matrice[k][j]);
		return product;
	}
	
	/**
	 * Fonction qui definie une valeur d'une matrice au bon endroit
	 * @param lines
	 * @param columns
	 * @param coordinates
	 */
	public void setPoint(int lines, int columns, double coordinates) {
		matrice[lines][columns] = coordinates;
	}
	
	/**
	 * Retourne une matrice
	 * @param lines
	 * @param columns
	 * @return
	 */
	public double getValue(int lines, int columns) {
		return this.matrice[lines][columns];
	} 
}