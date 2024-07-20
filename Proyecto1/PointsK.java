import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PointsK here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PointsK extends Actor
{
    /**
     * Act - do whatever the PointsK wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    int score = 0;
    public void act()
    {
        setImage(new GreenfootImage("Puntos: "+score, 45, Color.GREEN, Color.WHITE));
    }
    public void update(int points)
    {
        score = points;
    }
}
