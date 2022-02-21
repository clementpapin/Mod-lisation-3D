package test.math;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projet_modelisation.math.Matrice;
import projet_modelisation.math.ModeleMatrice;
import projet_modelisation.math.Point;
import projet_modelisation.ply.PlyModel;


public class TestModeleMatrice {
    
    private static ModeleMatrice matrice;
    private static PlyModel model;
    private static double[][] points;
    
    @BeforeAll
    public static void initializePlyModel() {
        model=new PlyModel(100,50);
        model.setPoints(new Point[]{new Point(3,3,5),new Point(4,3,4),new Point(6,9,8),new Point(9,1,2)});
    }
    
    @BeforeEach
    public void initializeMatrice(){
        points=new double[][]{{3,4,6,9},{3,3,9,1},{5,4,8,2},{0,0,0,1}};
        matrice=new ModeleMatrice(new Matrice(points));
    }

    @Test
    public void testTranslation(){
        matrice.translation(new Point(-2,-3,1),model);
        assertArrayEquals(new double[][]{{3,4,6,7},{3,3,9,-2},{5,4,8,3},{0,0,0,1}},matrice.getTableau());
        matrice.reset(points);
        matrice.translation(new Point(-4000,1000,200),model);
        assertArrayEquals(new double[][]{{3,4,6,-3991},{3,3,9,1001},{5,4,8,202},{0,0,0,1}},matrice.getTableau());

    }

   @Test
    public void testHomothetie(){
        matrice.homothetie(model.getCenterPoint(),68,model);
        assertArrayEquals(new double[][]{{204,272,408,243.5},{204,204,612,-200},{340,272,544,-182.25},{0,0,0,1}},matrice.getTableau());
    }
   
   @Test
   public void testRotation() {
	   matrice.rotationX(model.getCenterPoint(), 20, model);
       assertArrayEquals(new double[][]{{3,4,6,9},{5.788972439078314,4.876027188350687,10.97630056214155,0.26515437505884787},{-0.6984254431159234,-1.1065075049293152,-4.951850762041513,6.366610082196055},{0,0,0,1}},matrice.getTableau());
       assertNotEquals(new double[][]{{3,4,6,9}, {6,5,11,0.25},{-0.7,-1.1,-5,6.37},{0,0,0,1}},matrice.getTableau());
   }
}
