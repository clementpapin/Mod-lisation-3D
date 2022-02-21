package projet_modelisation.file_explorer;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * classe utilise pour la fenetre du fileExplorer
 * @author cycyd
 *
 */
public class FileExplorer extends Stage{
	
	/**
	 * Fonction principale de la classe
	 * @throws IOException
	 */
	public FileExplorer() throws IOException {
		
		this.setTitle("File Explorer");
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/FileExplorer.fxml"));
		
		Parent root = loader.load();
		
		FileExplorerView view = loader.getController();
		FileExplorerModel model = new FileExplorerModel();
		FileExplorerController controller = new FileExplorerController();
		
		model.attach(view);
		controller.setView(view);
		controller.setModel(model);
		view.setController(controller);
		
		controller.init();
		
		Scene scene = new Scene(root);
        this.setScene(scene);
        this.setResizable(false);
	}
}