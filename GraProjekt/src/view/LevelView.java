package view;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import mainController.GameController;
import model.Ball;
import model.Cup;
import model.Level;
import model.Obstacle;
import model.Path;
import java.util.ArrayList;
import java.util.List;

public class LevelView 
{
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 800;

    private final GameController gameController;

    private AnimationTimer gameLoop;
    private Pane gameArea;
    private Ball ball;
    private Cup cup;
    private List<Path> paths = new ArrayList<>();
    private Level currentLevel;
    private boolean isDrawing;
    private boolean gameOver;
    private Text levelText;

    // Dodaje pole dla dźwięku ukończenia poziomu
    private AudioClip winSound;
    
    private StackPane layeredRoot;

    public LevelView(GameController gameController) 
    {
        this.gameController = gameController;
        
        try 
        {
            winSound = new AudioClip(getClass().getResource("/win.wav").toExternalForm());
            winSound.setVolume(0.3); // 0.0 = cisza 1.0 = pełna głośność
        } 
        
        catch (Exception e) 
        {
            System.err.println("Nie można załadować dźwięku ukończenia poziomu: " + e.getMessage());
        }
    }

    public Scene createScene(Level level) 
    {
        this.currentLevel = level;
        this.gameOver = false;

        // Umieszcza wszystkie swoje dzieci elementy w środku kontenera (czyli wyśrodkowuje je).
        // Każdy nowy element nakładany jest na poprzedni.
        
        layeredRoot = new StackPane();
        
        BorderPane root = new BorderPane(); //dzieli przestrzeń na pięć obszarów:

        gameArea = new Pane();
        gameArea.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        
       try // Wczytanie tła (kratki)
       {
            Image bgImage = new Image(getClass().getResourceAsStream("/kratka.jpg"));
            BackgroundSize backgroundSize = new BackgroundSize(WINDOW_WIDTH, WINDOW_HEIGHT, false, false, false, false);
            BackgroundImage backgroundImage = new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize
            );
            
            gameArea.setBackground(new Background(backgroundImage));
        } 
       
       catch (Exception e) 
       {
            System.err.println("Nie udało się wczytać tła: " + e.getMessage());
        }

        initializeGameElements(); //wczytuje piłkę, kubek, przeszkody

        root.setCenter(gameArea);
        HBox topControls = createTopControls(); //Tworzy górny pasek z przyciskami
        root.setTop(topControls);

        layeredRoot.getChildren().add(root);

        setupMouseHandlers(); //Ustawia obsługę myszki
        startGameLoop(); //tutaj jest uruchamiana pętla gry!!!!!!

        Scene scene = new Scene(layeredRoot, WINDOW_WIDTH, WINDOW_HEIGHT);

        scene.setOnKeyPressed(event -> 
        {
            if (event.getCode() == KeyCode.SPACE) // restart poziomu albo przejście do kolejnego
            {
            	if (ball.isInCup(cup))
            		gameController.startLevel(level.getLevelNumber());
            	else
            		gameController.restartLevel();
            }
            
            else if (event.getCode() == KeyCode.ESCAPE) // powrót do menu wyboru poziomu
            {
                gameController.showLevelSelection();
            }
        });

        gameArea.requestFocus(); // nasłuchuje zdarzeń klawiatury 
        
        Image cursorImage = new Image(getClass().getResourceAsStream("/pencil.png"));
        scene.setCursor(new ImageCursor(cursorImage));
        
        return scene;
    }

    private void initializeGameElements() 
    {
        gameArea.getChildren().clear(); // Czyści poprzednią grę
        paths.clear(); // Czyści narysowane ścieżki
        
        // Dodaje kulke, kubek i przeszkody
        ball = new Ball
        (
        	currentLevel.getBallStartX(), 
        	currentLevel.getBallStartY(), 
        	gameController.getSelectedBallSkin()
        );
        
        cup = new Cup
        (
            currentLevel.getCupX(),
            currentLevel.getCupY(),
            gameController.getSelectedCupSkin()
        );
        
        gameArea.getChildren().add(cup.getShape());
        gameArea.getChildren().add(ball.getShape());

        for (Obstacle obstacle : currentLevel.getObstacles()) 
        {
            gameArea.getChildren().add(obstacle.getShape());
        }
    }

    private HBox createTopControls() 
    {
        HBox controls = new HBox(20);
        controls.setPadding(new Insets(10));
        controls.setAlignment(Pos.CENTER);
        controls.setStyle("-fx-background-color: #cccccc;");

        Button menuButton = new Button("Menu");
        menuButton.setFont(Font.font("Arial", FontWeight.BOLD, 21));
        menuButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        menuButton.setOnAction(e -> gameController.showLevelSelection());
        
        menuButton.setOnMouseEntered(e -> 
		{
			gameController.scaleDown(menuButton).stop(); 
			gameController.scaleUp(menuButton).playFromStart();
		});
        
        menuButton.setOnMouseExited(e -> 
		{
			gameController.scaleUp(menuButton).stop(); 
			gameController.scaleDown(menuButton).playFromStart();
		});
        
        Button resetButton = new Button("Reset");
        resetButton.setFont(Font.font("Arial", FontWeight.BOLD, 21));
        resetButton.setStyle("-fx-background-color: #ff9800; -fx-text-fill: white;");
        resetButton.setOnAction(e -> gameController.restartLevel());
        
        resetButton.setOnMouseEntered(e -> 
		{
			gameController.scaleDown(resetButton).stop(); 
			gameController.scaleUp(resetButton).playFromStart();
		});
        
        resetButton.setOnMouseExited(e -> 
		{
			gameController.scaleUp(resetButton).stop(); 
			gameController.scaleDown(resetButton).playFromStart();
		});
        
        levelText = new Text("Level " + currentLevel.getLevelNumber());
        levelText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        levelText.setFill(Color.DARKBLUE);

        Region spacerLeft = new Region(); //dwa niewidoczne puste elementy GUI (Region), dzieki temu tekst
        Region spacerRight = new Region(); //jest ladnie wysrodkowany
        HBox.setHgrow(spacerLeft, Priority.ALWAYS);
        HBox.setHgrow(spacerRight, Priority.ALWAYS);

        controls.getChildren().addAll(menuButton, spacerLeft, levelText, spacerRight, resetButton);
        return controls;
    }

    private void setupMouseHandlers() 
    {
        gameArea.setOnMousePressed(e -> 
        {
            if (!gameOver) 
            {
            	// Oblicza odległość kursora od środka piłki
            	 double dx = e.getX() - ball.getX();
                 double dy = e.getY() - ball.getY();
                 double distance = Math.sqrt(dx * dx + dy * dy);
                 
                 // Aby uniknąć oszukiwania, gracz nie może zacząć rysowania na kulce
                 if (distance <= ball.getRadius()) 
                 {
                     return;
                 }
            	
            	isDrawing = true; // rysujemy - true
                Path newPath = new Path(); // Rozpoczyna rysowanie nowego Path
                paths.add(newPath); // i dodaje ją do listy
                Line line = newPath.addPoint(e.getX(), e.getY()); //dodaje pierwszy punkt do nowej sciezki
                if (line != null) 
                {
                    gameArea.getChildren().add(line); // dodaje do interfejsu 
                }
            }
        });

        gameArea.setOnMouseDragged(e -> 
        {
            if (isDrawing && !paths.isEmpty()) 
            {
            	double dx = e.getX() - ball.getX();
                double dy = e.getY() - ball.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                
                if (distance <= ball.getRadius()) 
                {
                    return;
                }
            	
            	Path currentPath = paths.get(paths.size() - 1); // Pobiera ścieżkę która się aktualnie rysuje z listy paths
                Line line = currentPath.addPoint(e.getX(), e.getY());
                if (line != null) 
                {
                    gameArea.getChildren().add(line);
                }
            }
        });

        gameArea.setOnMouseReleased(e -> 
        {
            isDrawing = false;
            if (!ball.isMoving()) 
            {
                // Nadawanie prędkości początkowej piłeczce
            	// wtedy w obiekcie ball isMoving=true
            	ball.setInitialVelocity(0, 0.5);
            }
        });
    }

    private void startGameLoop() 
    {
        if (gameLoop != null) 
        {
            gameLoop.stop();
        }

        gameLoop = new AnimationTimer() // Wywołuje metodę handle() co klatkę (czyli zwykle 60 razy na sekundę).
        {
            @Override
            public void handle(long now) 
            {
                updateGame();
            }
        };

        gameLoop.start();
    }

    public void cleanup() 
    {
        if (gameLoop != null) 
        {
            gameLoop.stop();
        }
    }

    private void updateGame() 
    {
        if (gameOver) return;

        if (ball.isMoving()) 
        {
            ball.update();
            ball.handleWallCollision(0, gameArea.getWidth(), 0, gameArea.getHeight());

            for (Path p : paths) 
            {
                p.checkCollisionWithBall(ball);
            }
            
            for (Obstacle obstacle : currentLevel.getObstacles()) 
            {
                obstacle.checkCollision(ball);
            }
            
            if (ball.isInCup(cup)) 
            {
                gameOver = true;
                ball.stop();
                gameController.levelCompleted();
                
                // Odtwarza dźwięk ukończenia poziomu
                if (winSound != null) {
                    winSound.play();
                }
                
                showLevelCompletedOverlay();
            }

            Point2D velocity = ball.getVelocity();
            if (Math.abs(velocity.getX()) < 0.01 && Math.abs(velocity.getY()) < 0.01) // Sprawdza, czy piłeczka prawie się nie porusza
            { 
                if (ball.getY() > WINDOW_HEIGHT - 20) // sprawdza, czy piłeczka znajduje się bardzo nisko na ekranie
                {
                    ball.stop();
                }
            }
        }
    }

    private void showLevelCompletedOverlay() 
    {	
    	LevelCompletedOverlay overlay = new LevelCompletedOverlay(gameController, currentLevel.getLevelNumber());
        layeredRoot.getChildren().add(overlay);
    }
}