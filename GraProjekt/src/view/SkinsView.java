package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import mainController.GameController;
import model.BallSkin;
import model.CupSkin;

public class SkinsView 
{
	private static final String TITLE = "Choose Skins";
	private static final int WINDOW_WIDTH = 1000; 
	private static final int WINDOW_HEIGHT = 800;
	private static final int BALL_RADIUS = 25;
    private static final int CUP_RADIUS = 35;
    private static final int CUP_HEIGHT = 70;
	private static final int SPACING = 40;
	
	private final GameController gameController;
	
	public SkinsView(GameController gameController) 
	{
        this.gameController = gameController;
    }
	
	public Scene createScene() 
	{
		// Główny kontener
        VBox root = new VBox(SPACING);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        
        // Wczytanie obrazu tła
	    Image backgroundImage = new Image(getClass().getResourceAsStream("/tlo.PNG"));
	    BackgroundImage background = new BackgroundImage(backgroundImage,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundPosition.CENTER,
	            BackgroundSize.DEFAULT);
	    root.setBackground(new Background(background));
        
        //tytuł widoku
        Text title = new Text(TITLE);
        title.setFont(Font.font("Comic Sans", FontWeight.BOLD, 62));
        title.setFill(Color.rgb(71, 42, 133));
        
        //sekcja wyboru wyglądu piłeczki
        VBox ballSection = createBallSkinsSection();
        
        //sekcja wyboru wyglądu kubeczka
        VBox cupSection = createCupSkinsSection();
        
        //przycisk powrotu
        Button returnButton = new Button("Return");
        returnButton.setFont(Font.font("Comic Sans", FontWeight.BOLD, 24));
        returnButton.setStyle("-fx-background-color: #d1545a; -fx-text-fill: white;");
        returnButton.setPrefWidth(150);
        returnButton.setPrefHeight(40);
        returnButton.setOnAction(e -> gameController.showMainMenu());
        
        returnButton.setOnMouseEntered(e -> 
		{
			returnButton.setStyle("-fx-background-color: #bf474d; -fx-text-fill: white;");
			gameController.scaleDown(returnButton).stop(); // zatrzymuje przeciwną animację
			gameController.scaleUp(returnButton).playFromStart();
		});
        
        returnButton.setOnMouseExited(e -> 
		{
			returnButton.setStyle("-fx-background-color: #d1545a; -fx-text-fill: white;");
			gameController.scaleUp(returnButton).stop(); // zatrzymuje przeciwną animację
			gameController.scaleDown(returnButton).playFromStart();
		});
        
        HBox buttonBox = new HBox(returnButton);
        buttonBox.setAlignment(Pos.CENTER);
        
        
        root.getChildren().addAll(title, ballSection, cupSection, buttonBox); // dodaje elementy do głównego kontenera
        
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        scene.setOnKeyPressed(event -> 
        {
            if (event.getCode() == KeyCode.ESCAPE) 
            {
                gameController.showMainMenu();
            }
        });
        
        Image cursorImage = new Image(getClass().getResourceAsStream("/cursor.png"));
        scene.setCursor(new ImageCursor(cursorImage));
        
        return scene; 
    }
	
	private VBox createBallSkinsSection() 
	{
        VBox section = new VBox(10);
        section.setAlignment(Pos.CENTER);
        
        Label sectionTitle = new Label("Ball");
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        
        HBox ballOptions = new HBox(20);
        ballOptions.setAlignment(Pos.CENTER);
        
        //opcje wyglądu piłeczki
        BallSkin currentSkin = gameController.getSelectedBallSkin();
        
        VBox redBall = createBallOption(BallSkin.RED, Color.RED, "Red", 
                                           currentSkin == BallSkin.RED);
        
        VBox yellowBall = createBallOption(BallSkin.YELLOW, Color.YELLOW, "Yellow", 
                                       currentSkin == BallSkin.YELLOW);
        
        VBox greenBall = createBallOption(BallSkin.GREEN, Color.GREEN, "Green", 
                                        currentSkin == BallSkin.GREEN);
        
        VBox blueBall = createBallOption(BallSkin.BLUE, Color.BLUE, "Blue", 
                                          currentSkin == BallSkin.BLUE);
        
        ballOptions.getChildren().addAll(redBall, yellowBall, greenBall, blueBall);
        section.getChildren().addAll(sectionTitle, ballOptions);
        
        return section;
    }
	
	private VBox createCupSkinsSection() 
	{
        VBox section = new VBox(10);
        section.setAlignment(Pos.CENTER);
        
        Label sectionTitle = new Label("Cup");
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 34));
        
        HBox cupOptions = new HBox(20);
        cupOptions.setAlignment(Pos.CENTER);
        
        //opcje wyglądu kubeczka
        CupSkin currentSkin = gameController.getSelectedCupSkin();
        
        
        VBox redCup = createCupOption(CupSkin.RED, Color.RED, "Red", 
                                         currentSkin == CupSkin.RED);
        
        
        VBox yellowCup = createCupOption(CupSkin.YELLOW, Color.YELLOW, "Yellow", 
                                      currentSkin == CupSkin.YELLOW);
        
        
        VBox greenCup = createCupOption(CupSkin.GREEN, Color.GREEN, "Green", 
                                       currentSkin == CupSkin.GREEN);
        
        
        VBox blueCup = createCupOption(CupSkin.BLUE, Color.BLUE, "Blue",
                                        currentSkin == CupSkin.BLUE);
        
        cupOptions.getChildren().addAll(redCup, yellowCup, greenCup, blueCup);
        section.getChildren().addAll(sectionTitle, cupOptions);
        
        return section;
    }
	
	private VBox createBallOption(BallSkin skin, Color color, String name, boolean isSelected)
	{
        VBox option = new VBox(5);
        option.setAlignment(Pos.CENTER);
        option.setPadding(new Insets(15));
        
        //zaznacza aktualnie wybranego skina
        if (isSelected) 
        {
            option.setStyle("-fx-border-color: #4CAF50; -fx-border-width: 2px; -fx-border-radius: 5px;");
        }
        
        
        Circle ball = new Circle(BALL_RADIUS, color);
        ball.setStroke(Color.BLACK);
        ball.setStrokeWidth(1.0);
        
        
        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        
        option.getChildren().addAll(ball, nameLabel);
        
        
        option.setOnMouseClicked(e -> {
            gameController.setBallSkin(skin);
            
            gameController.showSkins();
        });
        
        return option;
    }
	
	private VBox createCupOption(CupSkin skin, Color color, String name, boolean isSelected) 
	{
        VBox option = new VBox(5);
        option.setAlignment(Pos.CENTER);
        option.setPadding(new Insets(10));
        
       
        if (isSelected) 
        {
            option.setStyle("-fx-border-color: #4CAF50; -fx-border-width: 2px; -fx-border-radius: 5px;");
        }
        
        
        Group cup = new Group();

        Polygon body = new Polygon(
            -CUP_RADIUS, 0,
             CUP_RADIUS, 0,
             CUP_RADIUS * 0.7, CUP_HEIGHT,
            -CUP_RADIUS * 0.7, CUP_HEIGHT
        );
        body.setFill(color);
        body.setStroke(Color.BLACK);
        body.setStrokeWidth(1.5);

        
        Ellipse opening = new Ellipse(0, 0, CUP_RADIUS, CUP_RADIUS * 0.3);
        opening.setFill(Color.WHITE);
        opening.setStroke(Color.BLACK);
        opening.setStrokeWidth(1.5);

        cup.getChildren().addAll(body, opening);

        // Nazwa
        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        
        option.getChildren().addAll(cup, nameLabel);
        
        
        option.setOnMouseClicked(e -> 
        {
            gameController.setCupSkin(skin);
            
            gameController.showSkins();
        });
        
        return option;
    }
}
