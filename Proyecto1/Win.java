import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Win here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Win extends Actor
{
    /**
     * Act - do whatever the Win wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public Win(int puntos)
    {
        setImage(new GreenfootImage("Touchdown!", 80, Color.RED, Color.WHITE));
        //setImage(new GreenfootImage("Estrellas:"+puntos, 80, Color.RED, Color.WHITE));
    }
}
