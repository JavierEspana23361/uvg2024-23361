import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Enemigo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemigo extends Actor
{
    GreenfootImage naranja = new GreenfootImage("naranja.gif");
    GifImage gif = new GifImage("naranja.gif");

    
    /**
     * Act - do whatever the Enemigo wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void rotation()
    {
        setRotation(90);
    }
    public void act()
    {
        // Add your action code here.
        //Player();
        
        setImage(gif.getCurrentImage());
        if (getY() <= 115){  
            setRotation(90);
        }
        else if (getY() >= 500){
            setRotation(270);     
        }
        
        
        move(5);
        
        
    }
    public Enemigo()
    {
        setImage("naranja.gif");
        rotation();
    }
}
