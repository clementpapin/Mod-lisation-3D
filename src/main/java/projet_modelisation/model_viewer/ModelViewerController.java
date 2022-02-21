package projet_modelisation.model_viewer;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import projet_modelisation.math.Point;

/**
 * Classe qui controle le ModelViewer, on retrouve les actions a
 * executer lors d'une interaction avec la vue
 *
 */
public class ModelViewerController {

	private ModelViewerModel model;
	private static final int ZERO=0;

	@FXML
	protected Button centrer,up,down,left,right,rotation,translation,homothetie;

	@FXML
	protected Canvas canvas, upCanvas, downCanvas;

	@FXML
	protected ColorPicker cpseg,cpface;

	@FXML
	protected RadioButton rbseg,rbface,rbRotationAuto,rbLumiere;

	private boolean sltSeg=true,sltFace=true;

	protected Color cseg = Color.rgb(0,0,0);
	protected Color cface= Color.rgb(159,255,0) ;
	
	private boolean etatLum = false;

	private char statutButton ='x';
	public double oldX;
	public double oldY;
	private boolean first=true;

	private RotationAuto rAuto;

	/**
	 * Utilisation du bouton UP en fonction du mode 
	 * @param statut
	 */
	public void useButtonUp(char statut) {
		switch(statut) {
		case 'r' : rotationX((10*Math.PI)/256);break;
		case 't' : translate(0, -10, 0.0);break;
		case 'h' : homothetie(2);break;
		}
	}
	/**
	 * Utilisation du bouton DOWN en fonction du mode 
	 * @param statut
	 */
	public void useButtonDown(char statut) {
		switch(statut) {
		case 'r' : rotationX((-10*Math.PI)/256);break;
		case 't' : translate(0, 10, 0.0);break;
		case 'h' : homothetie(0.5);break;
		}
	}
	/**
	 * Utilisation du bouton LEFT en fonction du mode 
	 * @param statut
	 */
	public void useButtonLeft(char statut) {
		switch(statut) {
		case 'r' : rotationY((-10*Math.PI)/256);break;
		case 't' : translate(-10, 0.0, 0.0);break;
		}
	}
	/**
	 * Utilisation du bouton RIGHT en fonction du mode 
	 * @param statut
	 */
	public void useButtonRight(char statut) {
		switch(statut) {
		case 'r' : rotationY((10*Math.PI)/256);break;
		case 't' : translate(10, 0.0, 0.0);break;
		}
	}

	public void setController(ModelViewerController controller) {
		rAuto=new RotationAuto(controller,rbRotationAuto);

	}

	/**
	 * Permet de desactiver les boutons LEFT et RIGHT pour l'homothetie
	 */
	private void disableLR() {
		left.setDisable(true);
		right.setDisable(true);
	}
	/**
	 * Reactive les boutons lors d'un autre choix que l'homothetie
	 */
	private void enableLR() {
		left.setDisable(false);
		right.setDisable(false);
	}

	public void setModel(ModelViewerModel model) {
		this.model = model;
	}

	public void createRotationAuto() {
		rAuto=new RotationAuto(this,rbRotationAuto);
	}

	/**
	 * Fonction pour nettoyer les canvas affiche
	 */
	public void clearCanvas() {
		canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		upCanvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		downCanvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	/**
	 * Initialise la vue + controle les actions de l'utilisateur
	 */
	public void init() {
		centrer.setOnAction(e -> {
			center();
			enableLR();
			statutButton='x';
		});
		rotation.setOnMousePressed(e -> {
			statutButton='r';
			enableLR();
		});
		translation.setOnMousePressed(e -> {
			statutButton='t';
			enableLR();
		});
		homothetie.setOnMousePressed(e -> {
			statutButton='h';
			disableLR();

		});


		rbseg.setOnAction(e->{
			if(rbseg.isSelected()) this.sltSeg=true;
			else this.sltSeg=false;
			translate(0, 0, 0);

		});
		rbface.setOnAction(e->{
			
			if(rbface.isSelected()) this.sltFace=true;
			else this.sltFace=false;
			
			translate(0, 0, 0);

		});

		rbRotationAuto.setOnAction(e->{
			rAuto.start();
		});
		
		rbLumiere.setOnAction(e->{
			if(rbLumiere.isSelected()) {
				this.etatLum = true;
			}else this.etatLum = false;
			translate(0,0,0);
		});

		cpseg.setOnAction(e -> {
			this.cseg=cpseg.getValue();
			translate(0, 0, 0);
		});
		cpface.setOnAction(e ->{
			this.cface=cpface.getValue();
			translate(0, 0, 0);
		});

		canvas.setOnMousePressed(e -> {
			oldX = e.getX();
			oldY = e.getY();
		});
		up.setOnMousePressed(e->useButtonUp(statutButton));
		down.setOnMousePressed(e->useButtonDown(statutButton));
		left.setOnMousePressed(e->useButtonLeft(statutButton));
		right.setOnMousePressed(e->useButtonRight(statutButton));

		canvas.setOnMouseDragged(e -> {
			double xDelta = e.getX()-oldX;
			double yDelta = e.getY()-oldY;
			oldX = e.getX();
			oldY = e.getY();
			if (e.getButton()==MouseButton.SECONDARY)  {
				translate(xDelta, yDelta, 0);
			}else if (e.getButton()==MouseButton.PRIMARY) {
				if (xDelta!=ZERO) {
					rotationY((-xDelta*Math.PI)/256);
				}
				if (yDelta!=ZERO) {
					rotationX((-yDelta*Math.PI)/256);
				}
			}
		});
		canvas.setOnScroll(e -> {
			if (e.getDeltaY()<ZERO) homothetie(0.5);
			else homothetie(2);
		});
	}


	/**
	 * Effectue une translation du modele
	 * @param tx
	 * @param ty
	 * @param tz
	 */
	public void translate(double tx, double ty, double tz) {
		this.model.translate(tx, ty, tz);
	}
	/**
	 * Effectue une homothetie
	 * @param k
	 */
	public void homothetie(double k) {
		this.model.homothetie(k);
	}
	/**
	 * Effectue une rotation autour de l'axe X
	 * @param k
	 */
	public void rotationX(double k) {
		this.model.rotationX(k);
	}
	/**
	 * Effectue une rotation autour de l'axe Y
	 * @param k
	 */
	public void rotationY(double k) {
		this.model.rotationY(k);
	}

	/**
	 * Centre le modele au milieu du canvas
	 * @param model
	 */
	public void center() {
		if(!first) {model.setBase();translate(0, 0, 0);return;}
		Point modelCenter = model.getCenterPoint();
		double centreX = canvas.getWidth()/2.0;
		double centreY = canvas.getHeight()/2.0;
		translate(centreX-modelCenter.getCoX(), centreY-modelCenter.getCoY(), 400);
		model.replace();
		first=false;
	}

	/**
	 * Fonction qui retourne une liste de GraphicsContext, utilise par les
	 * fonction d'affichage des couleurs
	 * @return
	 */
	public List<GraphicsContext> getContext() {
		List<GraphicsContext> res=new ArrayList<>();
		res.add(canvas.getGraphicsContext2D());
		res.add(upCanvas.getGraphicsContext2D());
		res.add(downCanvas.getGraphicsContext2D());

		return res;
	}

	public boolean getSltFace() {
		return this.sltFace;
	}

	public boolean getSegSlt() {
		return this.sltSeg;
	}

	public Color getColorFace() {
		return cface;
	}

	public Color getColorSeg() {
		return cseg;
	}
	
	public boolean getEtatLum() {
		return this.etatLum;
	}
}
