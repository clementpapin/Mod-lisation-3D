package projet_modelisation.model_viewer;
import javafx.animation.AnimationTimer;
import javafx.scene.control.RadioButton;

/**
 *Classe pour creer une rotation automatique et la gerer
 *
 */
public class RotationAuto extends AnimationTimer{
	
	public RadioButton rAuto;
	public ModelViewerController controller;
	private long lastUpdate=0;
	
	/**
	 * Constructeur de la classe
	 * @param controller
	 * @param rAuto
	 */
	public RotationAuto(ModelViewerController controller, RadioButton rAuto) {
		this.rAuto=rAuto;
		this.controller=controller;
	}
	
	/**
	 * fonction qui gere si oui ou non on tourne la piece
	 * @param long
	 */
	@Override
	public void handle(long now) {
		if(!rAuto.isSelected()) this.stop();
		
        if (now - lastUpdate >= 500_000_000) {
    		controller.rotationY(0.05);    
            lastUpdate = now ;
        }
	}

}
