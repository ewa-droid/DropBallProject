package mainController;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application
{
    private static final String GAME_TITLE = "DropBall";
    
    private GameController gameController;
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		gameController = new GameController(primaryStage);
	
        primaryStage.setTitle(GAME_TITLE);
        primaryStage.setResizable(false);
        
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/LogoBeerponga.jpg")));
        
        gameController.showMainMenu();
        
        primaryStage.show();
	}
	
	public static void main(String[] args) 
	{
		launch(args);
    }
}