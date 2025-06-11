package view;

import javafx.scene.image.Image;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import mainController.GameController;

public class MainMenuView 
{
	private static final int WINDOW_WIDTH = 1000; 
	private static final int WINDOW_HEIGHT = 800;
	private static final int BUTTON_WIDTH = 300;
	private static final int BUTTON_HEIGHT = 75;
	private static final int SPACING = 35;
	
	private final GameController gameController;
	
	public MainMenuView(GameController gameController) 
	{
	    this.gameController = gameController;
	}
	
	public Scene createScene()
	{
	    // Tworzy VBox z przyciskami i logo (wyśrodkowany)
	    VBox menuBox = new VBox(SPACING);
	    menuBox.setAlignment(Pos.CENTER);
	    menuBox.setBackground(new Background(new BackgroundFill(Color.rgb(236,236,236), null, null)));

	    Image logoImage = new Image(getClass().getResourceAsStream("/tytul.jpg"));
	    ImageView logoView = new ImageView(logoImage);
	    logoView.setPreserveRatio(true);
	    logoView.setFitWidth(400);
	    
	    // Pulsujące logo
	    ScaleTransition pulse = new ScaleTransition(Duration.seconds(3), logoView);
	    pulse.setFromX(1);
	    pulse.setFromY(1);
	    pulse.setToX(1.1);
	    pulse.setToY(1.1);
	    pulse.setAutoReverse(true);
	    pulse.setCycleCount(ScaleTransition.INDEFINITE);
	    pulse.play();

	    // Wczytanie obrazu tła
	    Image backgroundImage = new Image(getClass().getResourceAsStream("/tlo.PNG"));
	    BackgroundImage background = new BackgroundImage(backgroundImage,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundPosition.CENTER,
	            BackgroundSize.DEFAULT);
	    menuBox.setBackground(new Background(background));

	    Button startButton = MenuButtons("Start");
	    startButton.setOnAction(e -> gameController.showLevelSelection());

	    Button skinsButton = MenuButtons("Skins");
	    skinsButton.setOnAction(e -> gameController.showSkins());

	    Button exitButton = MenuButtons("Exit");
	    exitButton.setOnAction(e -> gameController.exitGame());

	    menuBox.getChildren().addAll(logoView, startButton, skinsButton, exitButton);

	    // Tworzymy ikonkę głośniczka
	    Image speakerOn = new Image(getClass().getResourceAsStream("/speaker_on.png"));
	    Image speakerOff = new Image(getClass().getResourceAsStream("/speaker_off.png"));
	    ImageView speakerIcon = new ImageView(gameController.isMusicMuted() ? speakerOff : speakerOn);
	    speakerIcon.setFitWidth(50);
	    speakerIcon.setFitHeight(50);

	    // Kontener główny StackPane
	    StackPane layeredRoot = new StackPane();
	    layeredRoot.getChildren().add(menuBox);

	    // Ustawienie ikonki w prawym dolnym rogu
	    StackPane.setAlignment(speakerIcon, Pos.BOTTOM_RIGHT);
	    StackPane.setMargin(speakerIcon, new Insets(0, 20, 20, 0));
	    layeredRoot.getChildren().add(speakerIcon);

	    // Obsługa kliknięcia
	    speakerIcon.setOnMouseClicked((MouseEvent e) -> {
	        boolean muted = !gameController.isMusicMuted();
	        gameController.setMusicMuted(muted);
	        speakerIcon.setImage(muted ? speakerOff : speakerOn);
	    });

	    Scene scene = new Scene(layeredRoot, WINDOW_WIDTH, WINDOW_HEIGHT);

	    scene.setOnKeyPressed(event ->
	    {
	        if (event.getCode() == KeyCode.ESCAPE)
	        {
	            gameController.exitGame();
	        }
	    });
	    
	    Image cursorImage = new Image(getClass().getResourceAsStream("/cursor.png"));
        scene.setCursor(new ImageCursor(cursorImage));
	    
	    return scene;
	}
	
	private Button MenuButtons(String text) 
	{
		Button button = new Button(text);
		
		button.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		button.setFont(Font.font("Comic Sans", FontWeight.BOLD, 27));
		button.setStyle("-fx-background-color: #4c8bf4; -fx-text-fill: white;");
	 
		button.setOnMouseEntered(e -> 
		{
			button.setStyle("-fx-background-color: #3D71CC; -fx-text-fill: white;");
			gameController.scaleDown(button).stop(); // zatrzymuje przeciwną animację
			gameController.scaleUp(button).playFromStart();
		});
		
		button.setOnMouseExited(e -> 
		{
			button.setStyle("-fx-background-color: #4c8bf4; -fx-text-fill: white;");
			gameController.scaleUp(button).stop(); // zatrzymuje przeciwną animację
			gameController.scaleDown(button).playFromStart();
		});
		        
		return button;
	 }
}