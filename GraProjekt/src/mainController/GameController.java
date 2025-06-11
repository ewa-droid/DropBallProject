package mainController;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.ScaleTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Level;
import sound.SoundManager;
import model.BallSkin;
import model.CupSkin;
import view.MainMenuView;
import view.LevelSelectionView;
import view.LevelView;
import view.SkinsView;

public class GameController 
{
    private final Stage primaryStage;
    
    private final List<Level> levels;
    private int currentLevel;
    private int maxUnlockedLevel;
    private BallSkin selectedBallSkin;
    private CupSkin selectedCupSkin;
    private boolean menuShown = false;
    private boolean musicMuted = false;
    
    private MediaPlayer backgroundMusicPlayer; // Dodaje pole do obsługi muzyki
    
    //Widoki
    private MainMenuView mainMenuView;
    private LevelSelectionView levelSelectionView;
    private SkinsView skinsView;
    private LevelView levelView;

    public GameController(Stage primaryStage) 
    {
        this.primaryStage = primaryStage; // Główne okno aplikacji
        
        this.levels = new ArrayList<>();
        this.currentLevel = 0;
        this.maxUnlockedLevel = 0;
        
        this.selectedBallSkin = BallSkin.RED;
        this.selectedCupSkin = CupSkin.RED;
        
        //inicjalizuje muzyke w tle
        initializeBackgroundMusic();
        
        //tworzy poziomy gry
        initializeLevels();
        
        //tworzy interfejsy użytkownika (widoki)
        initializeViews();
    }
    
    private void initializeBackgroundMusic() 
    {
        try 
        {
            Media bgMusic = new Media(getClass().getResource("/MUSIC.wav").toExternalForm());
            backgroundMusicPlayer = new MediaPlayer(bgMusic);
            backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // zapętlenie
            backgroundMusicPlayer.setVolume(0.3); // 0.0 - 1.0, można zmienić głośność
            backgroundMusicPlayer.play();
        } 
        
        catch (Exception e) 
        {
            System.err.println("Nie można załadować muzyki tła: " + e.getMessage());
        }
    }

    // Wyciszanie muzyki w tle
    public boolean isMusicMuted() 
    {
        return musicMuted;
    }

    public void setMusicMuted(boolean muted) 
    {
        this.musicMuted = muted;
        
        if (backgroundMusicPlayer != null) 
        {
            backgroundMusicPlayer.setMute(muted);
        }
    }

    private void initializeLevels()
    {
        // Tworzy 6 poziomow (obiekty klasy Level) i dodaje je do listy levels
        for (int i = 0; i < 6; i++) 
        {
            levels.add(new Level(i + 1));
        }
    }
    
    private void initializeViews() 
    {
        mainMenuView = new MainMenuView(this);
        levelSelectionView = new LevelSelectionView(this);
        skinsView = new SkinsView(this);
        levelView = new LevelView(this);
    }
    
    public void showMainMenu()
    {
    	if (menuShown) // Zapobiega włączeniu się dźwięku przy otwarciu gry
    	{
            sound.SoundManager.playButtonSound();
        } 
    	
    	else 
        {
            menuShown = true;
        }
    	
    	Scene scene = mainMenuView.createScene();
        primaryStage.setScene(scene);
    }
    
    public void showLevelSelection() 
    {
    	SoundManager.playButtonSound();
    	
    	Scene scene = levelSelectionView.createScene();
        primaryStage.setScene(scene);
    }
    
    public void startLevel(int levelIndex)
    {
    	SoundManager.playButtonSound();
    	
    	if (levelIndex <= maxUnlockedLevel) 
        {
            currentLevel = levelIndex;
            Scene scene = levelView.createScene(levels.get(currentLevel));
            primaryStage.setScene(scene);
        }
    }
    
    public void showSkins() 
    {
    	SoundManager.playButtonSound();
    	
    	Scene scene = skinsView.createScene();
        primaryStage.setScene(scene);
    }
    
    public void levelCompleted() 
    {
        // Odblokowuje następny poziom
        if (currentLevel == maxUnlockedLevel && maxUnlockedLevel < levels.size() - 1) 
        {
            maxUnlockedLevel++;
        }
    }
    
    public void restartLevel() 
    {
        startLevel(currentLevel);
    }
    
    public List<Level> getLevels() 
    {
        return levels;
    }
    
    public int getMaxUnlockedLevel() 
    {
        return maxUnlockedLevel;
    }
    
    public void exitGame() 
    {
    	SoundManager.playButtonSound();
    	
    	primaryStage.close();
    }
    
    public void setBallSkin(BallSkin skin) 
    {
        this.selectedBallSkin = skin;
    }
    
    public void setCupSkin(CupSkin skin) 
    {
        this.selectedCupSkin = skin;
    }
    
    public BallSkin getSelectedBallSkin() 
    {
        return selectedBallSkin;
    }
    
    public CupSkin getSelectedCupSkin() 
    {
        return selectedCupSkin;
    }
    
    public ScaleTransition scaleUp(Button button)
	{
		ScaleTransition scaleUp = new ScaleTransition(Duration.millis(120), button);
        scaleUp.setToX(1.05);
        scaleUp.setToY(1.05);
        
        return scaleUp;
	}
	
    public ScaleTransition scaleDown(Button button)
	{
		ScaleTransition scaleDown = new ScaleTransition(Duration.millis(120), button);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);
        
        return scaleDown;
	}
}
