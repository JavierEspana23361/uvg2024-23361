import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ganar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ganar extends World
{

    /**
     * Constructor for objects of class ganar.
     * 
     */
    public ganar()
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
        ganador ganador = new ganador();
        addObject(ganador,296,196);
        ganador.setLocation(326,196);
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
