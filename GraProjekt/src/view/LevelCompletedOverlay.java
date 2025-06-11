package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import mainController.GameController;

public class LevelCompletedOverlay extends StackPane 
{
	private static final int WINDOW_WIDTH = 1000; 
	private static final int WINDOW_HEIGHT = 800;
	private final GameController gameController;
	
    public LevelCompletedOverlay(GameController gameController, int currentLevel) 
    {
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        this.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setAlignment(Pos.CENTER);
        this.gameController = gameController;
        
        Text title = new Text("Level Completed!");
        title.setFont(Font.font("Comic Sans", FontWeight.BOLD, 80));
        title.setFill(Color.WHITE);

        Button nextButton = menuStyleButton("Next Level");
        nextButton.setOnAction(e -> 
        {
        	if (currentLevel < gameController.getLevels().size()) 
        	{
        	    gameController.startLevel(currentLevel);
        	}
        	
        	else
        	{
        		gameController.startLevel(0);
        	}
        });
        
        Button returnButton = menuStyleButton("Return to Levels");
        returnButton.setOnAction(e -> gameController.showLevelSelection());

        VBox content = new VBox(35, title, nextButton, returnButton);
        content.setAlignment(Pos.CENTER);
        this.getChildren().add(content);
    }

    private Button menuStyleButton(String text) 
    {
        Button button = new Button(text);
        button.setPrefSize(250, 75);
        button.setFont(Font.font("Comic Sans", FontWeight.BOLD, 24));
        button.setStyle("-fx-background-color: #4c51af; -fx-text-fill: white;");
        
        button.setOnMouseEntered(e -> 
		{
			button.setStyle("-fx-background-color: #5b419c; -fx-text-fill: white;");
			gameController.scaleDown(button).stop(); 
			gameController.scaleUp(button).playFromStart();
		});
        
        button.setOnMouseExited(e -> 
		{
			button.setStyle("-fx-background-color: #4c51af; -fx-text-fill: white;");
			gameController.scaleUp(button).stop(); 
			gameController.scaleDown(button).playFromStart();
		});
        
        return button;
    }
}
