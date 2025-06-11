package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.Group;

public class Cup 
{   
    private static final double DEFAULT_RADIUS = 35.0;
    private static final double CUP_HEIGHT = 70.0;
    
    private final double x;
    private final double y;
    private final double radius;
    private final Group shape;
    private final CupSkin skin;
    
    public Cup(double x, double y, CupSkin skin) 
    {
        this.x = x;
        this.y = y;
        this.skin = skin;
        this.radius = DEFAULT_RADIUS;
        this.shape = new Group();
        
        createCupShape();
    }
    
    private void createCupShape() 
    {
        // Górny otwór kubeczka
        Ellipse opening = new Ellipse(x, y, radius, radius * 0.3);
        
        // Korpus kubeczka (trapez)
        Polygon body = new Polygon(
            x - radius, y,
            x + radius, y,
            x + radius * 0.7, y + CUP_HEIGHT,
            x - radius * 0.7, y + CUP_HEIGHT
        );
        
        applySkin(opening, body);
        
        shape.getChildren().addAll(body, opening);
    }
    
    private void applySkin(Ellipse opening, Polygon body) 
    {	
    	switch (skin) 
    	{
        case RED:
            body.setFill(Color.RED);
            break;
        case YELLOW:
        	body.setFill(Color.YELLOW);
            break;
        case GREEN:
        	body.setFill(Color.GREEN);
            break;
        case BLUE:
        	body.setFill(Color.BLUE);
            break;   
    }
    	//kubeczek
        body.setStroke(Color.BLACK);
        body.setStrokeWidth(1.5);
        
        //otwór kubeczka
        opening.setFill(Color.WHITE);
        opening.setStroke(Color.BLACK);
        opening.setStrokeWidth(1.5);
    }
    
    public Group getShape() 
    {
        return shape;
    }
    
    public double getX() 
    {
        return x;
    }
    
    public double getY() 
    {
        return y;
    }
    
    public double getRadius() 
    {
        return radius;
    }
}
