package view;

import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import mainController.GameController;

public class LevelSelectionView 
{
	private static final String TITLE = "Levels";
	private static final int WINDOW_WIDTH = 1000; 
	private static final int WINDOW_HEIGHT = 800;
	private static final int BUTTON_SIZE = 120;
    private static final int GRID_GAP = 30;
    private static final int SPACING = 45;
	
	private final GameController gameController;
	
	public LevelSelectionView(GameController gameController) 
	{
        this.gameController = gameController;
    }
	
	public Scene createScene() 
	{
        // Główny kontener
        VBox root = new VBox(SPACING);
        root.setAlignment(Pos.CENTER);
        
        // Wczytanie obrazu tła
	    Image backgroundImage = new Image(getClass().getResourceAsStream("/tlo.PNG"));
	    BackgroundImage background = new BackgroundImage(backgroundImage,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundPosition.CENTER,
	            BackgroundSize.DEFAULT);
	    root.setBackground(new Background(background));
        
        //Tytuł widoku
        Text title = new Text(TITLE);
        title.setFont(Font.font("Comic Sans", FontWeight.BOLD, 75));
        title.setFill(Color.rgb(71, 42, 133));
        
        //Siatka z poziomami
        GridPane levelGrid = new GridPane();
        levelGrid.setAlignment(Pos.CENTER);
        levelGrid.setHgap(GRID_GAP);
        levelGrid.setVgap(GRID_GAP);
        
        //Tworzy przyciski poziomów
        int maxUnlocked = gameController.getMaxUnlockedLevel();
        
        for (int i = 0; i < 6; i++) 
        {
        	final int levelIndex = i;
        	
        	Button levelButton = createLevelButton(i + 1, i <= maxUnlocked);
            
        	// Dodaje akcję otwierania odblokowanych poziomów
        	if (i <= maxUnlocked) 
        		levelButton.setOnAction(e -> gameController.startLevel(levelIndex));
        	
            // Dodaje przycisk do siatki (3 w rzędzie)
            levelGrid.add(levelButton, i % 3, i / 3);
        }
        
        // Przycisk powrotu
        Button returnButton = new Button("Return");
        returnButton.setFont(Font.font("Comic Sans", FontWeight.BOLD, 24));
        returnButton.setStyle("-fx-background-color: #d1545a; -fx-text-fill: white;");
        returnButton.setPrefWidth(150);
        returnButton.setPrefHeight(40);
        returnButton.setOnAction(e -> gameController.showMainMenu());
        
        returnButton.setOnMouseEntered(e -> 
		{
			returnButton.setStyle("-fx-background-color: #bf474d; -fx-text-fill: white;");
			gameController.scaleDown(returnButton).stop();
			gameController.scaleUp(returnButton).playFromStart();
		});
        
        returnButton.setOnMouseExited(e -> 
		{
			returnButton.setStyle("-fx-background-color: #d1545a; -fx-text-fill: white;");
			gameController.scaleUp(returnButton).stop(); 
			gameController.scaleDown(returnButton).playFromStart();
		});
        
        HBox buttonBox = new HBox(returnButton);
        buttonBox.setAlignment(Pos.CENTER);
        
        root.getChildren().addAll(title, levelGrid, buttonBox);
        
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        scene.setOnKeyPressed(event -> 
        {
        	int maxLevel = gameController.getMaxUnlockedLevel();

        	
        	if (event.getCode() == KeyCode.SPACE) 
            {
                gameController.startLevel(maxLevel);
            }

        	
        	else if (event.getCode() == KeyCode.ESCAPE) 
            {
                gameController.showMainMenu();
            }
        });
        
        root.requestFocus();
        
        Image cursorImage = new Image(getClass().getResourceAsStream("/cursor.png"));
        scene.setCursor(new ImageCursor(cursorImage));
        
        return scene;
    }
	
	private Button createLevelButton(int levelNumber, boolean isUnlocked) 
	{
        Button button = new Button(Integer.toString(levelNumber));
        
        if (isUnlocked)
        {
	        button.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
	        button.setFont(Font.font("Comic Sans", FontWeight.BOLD, 36));
	        button.setStyle("-fx-background-color: #a45cd1; -fx-text-fill: white;");
	        
	        button.setOnMouseEntered(e -> 
			{
				button.setStyle("-fx-background-color: #914bbd; -fx-text-fill: white;");
				gameController.scaleDown(button).stop(); // zatrzymuje przeciwną animację
				gameController.scaleUp(button).playFromStart();
			});
	        
	        button.setOnMouseExited(e -> 
			{
				button.setStyle("-fx-background-color: #a45cd1; -fx-text-fill: white;");
				gameController.scaleUp(button).stop(); // zatrzymuje przeciwną animację
				gameController.scaleDown(button).playFromStart();
			});
        }
        
        else
        {
        	button.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
        	button.setFont(Font.font("Comic Sans", FontWeight.BOLD, 36));
        	button.setStyle("-fx-background-color: #cccccc; -fx-text-fill: #666666;");
            button.setDisable(true);
        }
        
        return button;
    }
}
