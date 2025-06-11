package model;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

import java.util.ArrayList;
import java.util.List;

public class Path 
{    
    private static final double PATH_THICKNESS = 5.0;
    private static final Color PATH_COLOR = Color.LIGHTBLUE;
    
    private final List<Point2D> points;
    private final List<Line> lines;
    private final List<Point2D> lineNormals; //Wektory normalne do linii
    
    public Path() 
    {
        this.points = new ArrayList<>();
        this.lines = new ArrayList<>();
        this.lineNormals = new ArrayList<>();
    }
    
    public Line addPoint(double x, double y) 
    {
        Point2D newPoint = new Point2D(x, y);
        
        points.add(newPoint);
        
        // Jeśli to pierwszy punkt, nie tworzymy jeszcze linii
        if (points.size() < 2) 
        {
            return null;
        }
        
        // Tworzy nową linię między dwoma ostatnimi punktami
        Point2D prevPoint = points.get(points.size() - 2); //przedostatni punkt
        Line line = new Line(prevPoint.getX(), prevPoint.getY(), x, y);
        line.setStroke(PATH_COLOR);
        line.setStrokeWidth(PATH_THICKNESS);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        
        // Wektor normalny do linii (prostopadły)
        double dx = x - prevPoint.getX();
        double dy = y - prevPoint.getY();
        double length = Math.sqrt(dx * dx + dy * dy);
        
        
        Point2D normal;
        
        if (length > 0) 
        {
            normal = new Point2D(-dy / length, dx / length); //obrót o +90stopni
        } 
        
        else 
        {
            normal = new Point2D(0, 1); // Domyślny wektor normalny, jeśli długość jest 0
        }
        
        // Dodaj linię i jej wektor normalny
        lines.add(line);
        lineNormals.add(normal);
        
        return line;
    }
    
    public void checkCollisionWithBall(Ball ball) 
    {
    	// Środek i promień kulki
        double ballX = ball.getX();
        double ballY = ball.getY();
        double ballRadius = ball.getRadius();
        
        for (int i = 0; i < lines.size(); i++) 
        {
            Line line = lines.get(i);
            Point2D normal = lineNormals.get(i);
            
            // Punkt początkowy linii
            double x1 = line.getStartX();
            double y1 = line.getStartY();
            
            // Punkt końcowy linii
            double x2 = line.getEndX();
            double y2 = line.getEndY();
            
            // Wektor znormalizowany linii
            double lineLength = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
            double dx = (x2 - x1) / lineLength;
            double dy = (y2 - y1) / lineLength; 
            
            // Iloczyn skalarny wektora linii i wektora od początku linii do piłeczki.
            // (jak daleko wzdłuż linii znajduje się punkt najbliższy środkowi piłki)
            double t = dx * (ballX - x1) + dy * (ballY - y1);
            
            // Ograniczenie t do przedziału [0, lineLength]
            t = Math.max(0, Math.min(lineLength, t));
            
            // Najbliższy punkt na linii
            double nearestX = x1 + t * dx;
            double nearestY = y1 + t * dy;
            
            // Odległość od piłeczki do najbliższego punktu na linii
            double distance = Math.sqrt(
                (ballX - nearestX) * (ballX - nearestX) +
                (ballY - nearestY) * (ballY - nearestY)
            );
            
            // Jesli odleglość jest mniejsza niż promień piłeczki, nastąpiła kolizja
            if (distance < ballRadius) 
            	
            {
                //odbicie piłeczki od linii
                ball.bounceFromLine(new Point2D(nearestX, nearestY), normal);
                break; 
            }
        }
    }
    
    // Resetowanie toru
    public void reset() 
    {
        points.clear();
        lines.clear();
        lineNormals.clear();
    }
 
    public List<Line> getLines() 
    {
        return lines;
    }
}
