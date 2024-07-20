import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Hearts here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hearts extends Actor
{
    /**
     * Act - do whatever the Hearts wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    GreenfootImage[] hearts = { new GreenfootImage("no_hearts.png"),
                                new GreenfootImage("one_heart.png"),
                                new GreenfootImage("two_hearts.png"),
                                new GreenfootImage("three_hearts.png") };
    int health = 3;
 
    private void updateImage()
    {
        setImage(hearts[health]);
    }
    public Hearts()
    {
        updateImage();
    }
    public void act()
    {
        // Add your action code here.
    }
    public void adjustHearts(int adjustmentValue)
    {
        health += adjustmentValue;
        if (health > 3) health = 3;
        if (health <= 0) Greenfoot.stop();
        else updateImage();
    }
}
