package model;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Obstacle 
{
    private final Shape shape;
    
    public Obstacle(double x, double y, double width, double height) 
    {
        this.shape = new Rectangle(x, y, width, height);
        
        //ustawia wygląd przeszkody
        shape.setFill(Color.rgb(200, 200, 200));
        shape.setStroke(Color.BLACK);
        shape.setStrokeWidth(2.0);
    }
    
    // Sprawdza czy kolizja
    public boolean checkCollision(Ball ball) 
    {
            Rectangle rectangle = (Rectangle) shape;
            double rectX = rectangle.getX();
            double rectY = rectangle.getY();
            double rectWidth = rectangle.getWidth();
            double rectHeight = rectangle.getHeight();
            
            // Znalezienie najbliższego punktu na prostokącie do środka piłki
            double closestX = Math.max(rectX, Math.min(ball.getX(), rectX + rectWidth));
            double closestY = Math.max(rectY, Math.min(ball.getY(), rectY + rectHeight));
            
            // Odległość od środka piłeczki do najbliższego punktu
            double distance = Math.sqrt(
                Math.pow(ball.getX() - closestX, 2) +
                Math.pow(ball.getY() - closestY, 2)
            );
            
            // Jeśli odległość jest mniejsza niż promień piłeczki - kolizja
            if (distance < ball.getRadius()) 
            {
                handleCollision(ball, new Point2D(closestX, closestY));
                return true;
            }
        
        return false;
    }
    
    // Kolizja z przeszkodami
    private void handleCollision(Ball ball, Point2D collisionPoint) 
    {
        // Oblicza wektor normalny (od punktu kolizji do środka piłeczki)
        Point2D normal = new Point2D(
            ball.getX() - collisionPoint.getX(),
            ball.getY() - collisionPoint.getY()
        ).normalize();
        
        // Odbija piłeczkę od przeszkody
        ball.bounceFromLine(collisionPoint, normal);
    }
    
    public Shape getShape() 
    {
        return shape;
    }
    

}
