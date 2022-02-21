package test.math;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;
import projet_modelisation.math.Matrice;

public class TestMatrice {
	private double[][] tab = new double[][]{{1},{2},{3}};
	
	@Test
	public void testMatrice() {
		Matrice matrice = new Matrice(tab);		
		assertArrayEquals(tab, matrice.getTableau());
	}
	
	
	@Test
	public void testProductMatrice() {
		double[][] matrice1 = new double[][] {{2,1,1}};
		Matrice mat1 = new Matrice(matrice1);
		
		double[][] matrice2 = new double[][]{{1},{1},{1}};
		Matrice mat2 = new Matrice(matrice2);
		
		double[][] expected = new double[][]{{4}};
		assertArrayEquals(expected, mat1.productMatrice(mat2).getTableau());
	}
}