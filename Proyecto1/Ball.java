import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class cBall here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ball extends Actor
{
    /**
     * Act - do whatever the cBall wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    GifImage myGif = new GifImage("aball.gif");
    public static int sScore = 0;
    public static int vScore = 0;
    
    public void act()
    {
        setImage(myGif.getCurrentImage());
        move(6);
        punto();
        miss();
    }
    public void punto()
    {
        if (getX() >= 430 && getX() <= 580 && getY() >= 10 && getY() <= 180)
        {
            sScore = sScore + 1000;
            vScore = vScore + 1;
            //Greenfoot.stop();
            Player.sScore = 0;
            Greenfoot.setWorld(new ganar());
        }
    }
    public void miss()
    {
        if (isAtEdge())
        {
            vScore = vScore + 1;
            sScore = sScore + 0;
            Player.sScore = 0;
            Greenfoot.setWorld(new perder());
        }
    }
}
