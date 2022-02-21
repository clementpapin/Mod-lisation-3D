package projet_modelisation.model_viewer;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import projet_modelisation.math.Face;
import projet_modelisation.math.Vecteur;
import projet_modelisation.ply.PlyModel;
import projet_modelisation.utils.Subject;

/**
 * Classe qui fait le lien entre les actions et l'affichage
 *
 */
public class ModelViewerView implements projet_modelisation.utils.Observer{

	private ModelViewerController controller;
	private Color cseg = Color.rgb(0,0,0);
	private Color cface= Color.rgb(159,255,0) ;
	private Color clum;
	private Vecteur lum = new Vecteur(0.5,0.5,0.5);
	private double lumiere; 
	public double oldX;
	public double oldY;	

	public void setController(ModelViewerController controller) {
		this.controller=controller;
	}

	/**
	 * Affiche le modele sur le canvas
	 * @param model
	 */
	public void afficher(PlyModel model) {

		cseg=controller.getColorSeg();
		cface=controller.getColorFace();
		Face[] faces = model.getFaces();
		for (Face face : faces) {

			List<double[]> tabDouble = new ArrayList<>();

			tabDouble.add(model.getXsOfFace(face));

			tabDouble.add(new double[tabDouble.get(0).length]);

			int step=0;
			for (double idx : tabDouble.get(0)) {
				tabDouble.get(1)[step]=idx/2;
				step++;
			}

			tabDouble.add( model.getYsOfFace(face));

			tabDouble.add(new double[tabDouble.get(2).length]);

			step=0;
			for (double idx2 : tabDouble.get(2)) {
				tabDouble.get(3)[step]=idx2/2;
				step++;
			}

			tabDouble.add( model.getZsOfFace(face));
			tabDouble.add(new double[tabDouble.get(4).length]);


			step=0;
			for (double idx3 : tabDouble.get(4)) {
				tabDouble.get(5)[step]=idx3/2;
				step++;
			}

			if(controller.getEtatLum()) {
				face.calculNorme();
				lumiere = Face.getLumiere(face.getNorme(), lum);
				clum = setLumiere(cface, lumiere);
				if(controller.getSltFace()) {
					fillColor(controller,(ArrayList<double[]>)tabDouble,face,clum);
				}
			}

			if(controller.getSegSlt()){
				strokeColor(controller,(ArrayList<double[]>)tabDouble,face,cseg);

			}
			if(controller.getSltFace()) {
				fillColor(controller,(ArrayList<double[]>) tabDouble,face,cface);
			}
		}


	}

	/**
	 * Fonction utilise localement par afficher pour le reglage des couleur sur les arretes
	 * @param controller
	 * @param tabDouble
	 * @param face
	 * @param color
	 */
	public void strokeColor(ModelViewerController controller,ArrayList<double[]> tabDouble,Face face,  Color color) {
		List<GraphicsContext> listContext=controller.getContext();
		for(GraphicsContext graph : listContext) {
			graph.setStroke(color);
		}
		listContext.get(0).strokePolyline(tabDouble.get(0), tabDouble.get(2), face.getNbPoint());
		listContext.get(1).strokePolyline(tabDouble.get(1), tabDouble.get(5), face.getNbPoint());
		listContext.get(2).strokePolyline(tabDouble.get(5), tabDouble.get(3), face.getNbPoint());
	}

	/**
	 * Fonction utilise localement par afficher pour le reglage des couleur sur les faces
	 * @param controller
	 * @param tabDouble
	 * @param face
	 * @param color
	 */
	public void fillColor(ModelViewerController controller,ArrayList<double[]> tabDouble,Face face,  Color color) {
		List<GraphicsContext> listContext=controller.getContext();
		for(GraphicsContext graph : listContext) {
			graph.setFill(color);
		}
		listContext.get(0).fillPolygon(tabDouble.get(0), tabDouble.get(2), face.getNbPoint());
		listContext.get(1).fillPolygon(tabDouble.get(1), tabDouble.get(5), face.getNbPoint());
		listContext.get(2).fillPolygon(tabDouble.get(5), tabDouble.get(3), face.getNbPoint());
	}


	/**
	 * Methode effectue  chaque changement du modle
	 */
	public void update(Subject subj, Object data) {
		PlyModel updated = (PlyModel) data;
		controller.clearCanvas();
		this.afficher(updated);
	}

	/**
	 * Fonction utilise localement pour changer la couleur
	 * @param context
	 * @param Reduits1
	 * @param Reduits2
	 * @param face
	 */
	public void changeColorSeg(GraphicsContext context,double[] Reduits1, double[] Reduits2,Face face) {

		context.setStroke(cseg);
		context.strokePolyline(Reduits1, Reduits2, face.getNbPoint());

	}
	/**
	 * Fonction utilise localement pour changer la couleur
	 * @param context
	 * @param Reduits1
	 * @param Reduits2
	 * @param face
	 * @param model
	 */
	public void changeColorFace(GraphicsContext context,double[] Reduits1, double[] Reduits2,Face face,PlyModel model) {

		context.setFill(cface);
		context.fillPolygon(Reduits1, Reduits2, face.getNbPoint());
		afficher(model);
	}



	/**
	 * Gere la couleur en fonction de la lumiere
	 * @param cface
	 * @param lumiere
	 * @return
	 */
	public Color setLumiere(Color cface, double lumiere) {
		double red = cface.getRed()*255*(lumiere+1)/1.5;
		double green = cface.getGreen()*255*(lumiere+1)/1.5;
		double blue = cface.getBlue()*255*(lumiere+1)/1.5;

		if(red < 0) red = 0; if(red > 255) red = 255;
		if(green < 0) green = 0; if(green > 255) green = 255;
		if(blue < 0) blue = 0; if(blue > 255) blue = 255;

		return Color.rgb((int) red, (int) green, (int) blue);
	}
	public void setColorSeg(Color color) {
		this.cseg=color;
	}
	public void setColorFace(Color color) {
		this.cface=color;
	}
}
