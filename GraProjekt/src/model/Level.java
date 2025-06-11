package model;

import java.util.ArrayList;
import java.util.List;

import model.Obstacle;

public class Level 
{
    private final int levelNumber;
    private final List<Obstacle> obstacles;
    private double ballStartX;
    private double ballStartY;
    private double cupX;
    private double cupY;
    
    public Level(int levelNumber) 
    {
        this.levelNumber = levelNumber;
        this.obstacles = new ArrayList<>();
        
        // Ustawia początkowe położenie piłeczki
        this.ballStartX = 50;
        this.ballStartY = 50;
        this.cupX = 700;
        this.cupY = 490;
        
        // Inicjalizuje poziom
        initializeLevel();
    }
    
    private void initializeLevel() 
    {
        switch (levelNumber) 
        {
            case 1:   	
//            	// Poziom 1
            	this.ballStartX = 100;
                this.ballStartY = 80; 
                this.cupX = 760;
                this.cupY = 610;
                
            	obstacles.add(new Obstacle(700, 680, 120, 300));
                
                break;
                
            case 2:
            	// Poziom 2
            	this.ballStartX = 200;
                this.ballStartY = 60;
                this.cupX = 160;
                this.cupY = 630;
                
                obstacles.add(new Obstacle(300, 60, 50, 180));
                obstacles.add(new Obstacle(-1, 420, 580, 50));
                
                obstacles.add(new Obstacle(850, 140, 200, 480));
                
            	obstacles.add(new Obstacle(100, 700, 120, 200));
            	
            	break;
                
            case 3: 
                
            	// Poziom 3
            	this.ballStartX = 500;
                this.ballStartY = 60;
                this.cupX = 500;
                this.cupY = 650;
                
                obstacles.add(new Obstacle(380, 150, 240, 140));
                
                obstacles.add(new Obstacle(10, 260, 50, 30));
                obstacles.add(new Obstacle(110, 260, 50, 30));
                obstacles.add(new Obstacle(210, 260, 50, 30));
                obstacles.add(new Obstacle(310, 260, 50, 30));
                
                obstacles.add(new Obstacle(640, 260, 50, 30));
                obstacles.add(new Obstacle(740, 260, 50, 30));
                obstacles.add(new Obstacle(840, 260, 50, 30));
                obstacles.add(new Obstacle(940, 260, 50, 30));
                
                obstacles.add(new Obstacle(370, 350, 260, 30));
                
                obstacles.add(new Obstacle(250, 500, 230, 30));
                obstacles.add(new Obstacle(520, 500, 230, 30));

                obstacles.add(new Obstacle(440, 720, 120, 200));
                
                break;  
                
            case 4:
            	// Poziom 4
            	this.ballStartX = 180;
                this.ballStartY = 20;
                this.cupX = 575;
                this.cupY = 675;
                
            	obstacles.add(new Obstacle(-1, -1, 60, 805));

            	obstacles.add(new Obstacle(59, 100, 200, 50));
            	obstacles.add(new Obstacle(259, 220, 200, 50));
            	obstacles.add(new Obstacle(59, 340, 200, 50));
            	obstacles.add(new Obstacle(390, 559, 69, 30));
            	
            	obstacles.add(new Obstacle(459, -1, 40, 390));
            	obstacles.add(new Obstacle(459, 439, 40, 150));
            	obstacles.add(new Obstacle(459, 639, 40, 300));
            	
            	obstacles.add(new Obstacle(499, 515, 280, 40));
            	
            	obstacles.add(new Obstacle(630, 75, 240, 240));
                
                break;
            	    
            case 5:
            	// Poziom 5
            	this.ballStartX = 925;
                this.ballStartY = 60;
                this.cupX = 820;
                this.cupY = 630;
                
                obstacles.add(new Obstacle(245, 50, 150, 150));
                
                obstacles.add(new Obstacle(850, 30, 50, 180));
                obstacles.add(new Obstacle(950, 30, 50, 180));
                
                obstacles.add(new Obstacle(750, 270, 100, 180));
                
                obstacles.add(new Obstacle(-1, 400, 270, 50));
                obstacles.add(new Obstacle(370, 400, 740, 50));
                
                obstacles.add(new Obstacle(100, 620, 400, 400));
                
            	obstacles.add(new Obstacle(760, 700, 120, 200));
            	
            	break;
                
            case 6:
            	// Poziom 6	
            	this.ballStartX = 200;
                this.ballStartY = 40;
                
                this.cupX = 80;
                this.cupY = 672;
                
                obstacles.add(new Obstacle(40, 60, 100, 150));
                
                obstacles.add(new Obstacle(-1, 290, 321, 40));
                
                obstacles.add(new Obstacle(260, -1, 20, 160));
                
                obstacles.add(new Obstacle(320, 210, 20, 80));
                
                obstacles.add(new Obstacle(340, 275, 240, 15));

                obstacles.add(new Obstacle(680, 250, 30, 600));
                
                obstacles.add(new Obstacle(430, 400, 120, 120));
                
                obstacles.add(new Obstacle(100, 500, 240, 100));
                
                obstacles.add(new Obstacle(500, 640, 120, 200));
                
                break;
        }
    }
    
    public int getLevelNumber() 
    {
        return levelNumber;
    }
    
    public List<Obstacle> getObstacles() 
    {
        return obstacles;
    }
    
    public double getBallStartX() 
    {
        return ballStartX;
    }
    
    public double getBallStartY() 
    {
        return ballStartY;
    }

    public double getCupX() 
    {
        return cupX;
    }
    
    public double getCupY() 
    {
        return cupY;
    }
}
