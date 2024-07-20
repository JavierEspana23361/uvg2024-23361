import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ball here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Aim extends Actor
{
    /**
     * Act - do whatever the Ball wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Aim()
    {
        rotation();
    }
    public void act()
    {
        setRotation(getRotation()+5);
        
        if ("space".equals(Greenfoot.getKey()))
        {
            fire();
        }
    }
    
    private void fire(){
        Ball ball = new Ball();
        getWorld().addObject(ball,500,560);
        ball.setRotation(getRotation());
        getWorld().removeObject(this);
    }
    public void rotation()
    {
        setRotation(270);
    }
    
}
