package model;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball 
{
    private static final double DEFAULT_RADIUS = 12.0; // promień
    private static final double GRAVITY = 0.1; // siła grawitacji
    private static final double FRICTION = 0.995; // współczynnik tarcia
    private static final double BOUNCE_DAMPING = 0.8; // tłumienie odbicia
    
    private final Circle shape;
    
    private Point2D velocity;
    private boolean isMoving;
    private final BallSkin skin;
    
    public Ball(double x, double y, BallSkin skin) 
    {
        this.skin = skin;
    	this.shape = new Circle(x, y, DEFAULT_RADIUS);
        this.velocity = new Point2D(0, 0);
        this.isMoving = false;
        
        applySkin();
    }
    
    private void applySkin() 
    {
        switch (skin) 
        {
            case RED:
                shape.setFill(Color.RED);
                break;
            case YELLOW:
                shape.setFill(Color.YELLOW);
                break;
            case GREEN:
                shape.setFill(Color.GREEN);
                break;
            case BLUE:
                shape.setFill(Color.BLUE);
                break;
        }
        
        shape.setStroke(Color.BLACK);
        shape.setStrokeWidth(1.0);
    }
    
    public void update() 
    {
        if (!isMoving) 
        {
            return;
        }
        
        // Siła grawitacji
        velocity = velocity.add(0, GRAVITY);
        
        // Tarcie tylko jeśli prędkość jest większa niż próg minimalny
        double speed = velocity.magnitude();
        
         //Prędkość graniczna
        if (speed > 0.04) 
        {
            velocity = velocity.multiply(FRICTION);
        }
        
        // Aktualizacja pozycji 
        shape.setCenterX(shape.getCenterX() + velocity.getX());
        shape.setCenterY(shape.getCenterY() + velocity.getY());
    }
    
    public void handleWallCollision(double minX, double maxX, double minY, double maxY) 
    {	
    	// Odbicie od lewej ściany
        if (shape.getCenterX() - shape.getRadius() < minX) 
        {
            shape.setCenterX(minX + shape.getRadius());
            velocity = new Point2D(-velocity.getX() * BOUNCE_DAMPING, velocity.getY());
        } 

        // Odbicie od prawej ściany
        else if (shape.getCenterX() + shape.getRadius() > maxX) 
        {
            shape.setCenterX(maxX - shape.getRadius());
            velocity = new Point2D(-velocity.getX() * BOUNCE_DAMPING, velocity.getY());
        }
        
        // Odbicie od górnej ściany
        if (shape.getCenterY() - shape.getRadius() < minY) 
        {
            shape.setCenterY(minY + shape.getRadius());
            velocity = new Point2D(velocity.getX(), -velocity.getY() * BOUNCE_DAMPING);
        } 
        
        // Odbicie od dolnej ściany
        else if (shape.getCenterY() + shape.getRadius() > maxY) 
        {
            shape.setCenterY(maxY - shape.getRadius());
            velocity = new Point2D(velocity.getX(), -velocity.getY() * BOUNCE_DAMPING);
        }
    }
    
    //linePoint – punkt na linii, od której piłka ma się odbić.
    //lineNormal – wektor normalny do tej linii (czyli prostopadły). 
    public void bounceFromLine(Point2D linePoint, Point2D lineNormal) 
    {
        Point2D ballCenter = new Point2D(shape.getCenterX(), shape.getCenterY()); //aktualna pozycja środka piłki.
        Point2D toBall = ballCenter.subtract(linePoint); //końcowy punkt minus początkowy

        // Sprawdza, czy wektor normalny jest skierowany w stronę piłeczki
        // Jeśli iloczyn skalarny < 0 to zwroty są przeciwne
        //pozwala na rysowanie linii z prawej i z lewej
        if (lineNormal.dotProduct(toBall) < 0) 
        {
            lineNormal = lineNormal.multiply(-1);
        }

        //Wektor odbicia
        //klasyczny wzor na odbicie wektora od plaszczyzny: v1 = v - 2(n . v) * n
        double dotProduct = velocity.dotProduct(lineNormal);
        Point2D reflection = velocity.subtract(lineNormal.multiply(2 * dotProduct));

        //Odbicie z tłumieniem
        velocity = reflection.multiply(BOUNCE_DAMPING);

        // Przesuwa piłeczkę poza linię, aby zapobiec przenikania 
        //toBall.dotProduct(lineNormal) - Jest to odległość od środka piłki do linii wzdłuż normalnej, czyli prostopadle do powierzchni.

        double overlap = shape.getRadius() - toBall.dotProduct(lineNormal);
        
        if (overlap > 0) 
        {
            Point2D correctionVector = lineNormal.multiply(overlap); //wektor przesunięcia w kierunku normalnym, o długości overlap
            shape.setCenterX(shape.getCenterX() + correctionVector.getX());
            shape.setCenterY(shape.getCenterY() + correctionVector.getY());
        }
    }
    
    public boolean isInCup(Cup cup) 
    {
        double dx = shape.getCenterX() - cup.getX();
        double dy = shape.getCenterY() - cup.getY();
        
        // Dopasowane do wysokości elipsy
        // Jeśli suma kwadratów współrzędnych jest mniejsza niż 1, 
        // to punkt znajduje się wewnątrz elipsy 
        
        double a = cup.getRadius();
        double b = cup.getRadius() * 0.3;

        double normalizedX = dx / a;
        double normalizedY = dy / b;

        return (normalizedX * normalizedX + normalizedY * normalizedY) < 1.0;
    }
    
    public void setInitialVelocity(double vx, double vy) 
    {
        this.velocity = new Point2D(vx, vy);
        this.isMoving = true;
    }
    
    public boolean isMoving() 
    {
        return isMoving;
    }
    
    public void stop() 
    {
        this.velocity = new Point2D(0, 0);
        this.isMoving = false;
    }
    
    public Circle getShape() 
    {
        return shape;
    }
 
    public double getX() 
    {
        return shape.getCenterX();
    }
    
    public double getY() 
    {
        return shape.getCenterY();
    }
    
    public double getRadius() 
    {
        return shape.getRadius();
    }
    
    public Point2D getVelocity() 
    {
        return velocity;
    }
    
    public void setVelocity(Point2D velocity) 
    {
        this.velocity = velocity;
    }
}
