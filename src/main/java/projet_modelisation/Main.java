package projet_modelisation;

import javafx.application.Application; 
import javafx.stage.Stage;
import projet_modelisation.file_explorer.FileExplorer;
	
/**
 * Main classe qui sert de lancement
 */
public class Main extends Application{
	
	
	
	//MAIN
	@Override
	public void start(Stage primaryStage) throws Exception {
		FileExplorer fileExplo = new FileExplorer();
		fileExplo.show();
	}

}
