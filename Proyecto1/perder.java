import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class perder here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class perder extends World
{

    /**
     * Constructor for objects of class perder.
     * 
     */
    public perder()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1); 
        prepare();
    }
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Loose loose = new Loose();
        addObject(loose,277,196);
        loose.setLocation(327,193);
    }
    public void act()
    {
        Player.vScore = 0;
        Player.sScore = 0;
        Player.Health = 3;
        Ball.sScore = 0;
        Ball.vScore = 0;
    }
}
