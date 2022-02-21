package projet_modelisation.model_viewer;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import projet_modelisation.ply.PlyModel;

/**
 * Classe du modele avec l'execution de javaFX
 * 
 *
 */
public class ModelViewer extends Stage{
	/**
	 * Creer une vue a partir d'un model Ply
	 * @param plyModel
	 */
	public static void newView(PlyModel plyModel) {
		try {
			ModelViewer modelV = new ModelViewer(plyModel);
			modelV.setMaximized(true);
			modelV.show();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Fonction qui affiche la vue creer avec newView();
	 * @param plyModel
	 * @throws IOException
	 */
	public ModelViewer(PlyModel plyModel) throws IOException {

		this.setTitle("Model Viewer");

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/ModelViewer.fxml"));

		Parent root = loader.load();

		ModelViewerView view = new ModelViewerView();
		ModelViewerModel model = new ModelViewerModel();
		ModelViewerController controller = loader.getController();

		model.attach(view);
		controller.setModel(model);
		controller.createRotationAuto();
		view.setController(controller);
		model.changeModel(plyModel);
		controller.init();

		controller.center();

		Scene scene = new Scene(root);
		this.setScene(scene);
	}

}
