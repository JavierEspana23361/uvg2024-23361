import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Kick here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Kick extends World
{
    PointsK myPoints = new PointsK();

    /**
     * Constructor for objects of class Kick.
     * 
     */
    public Kick()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.

        super(992, 621, 1); 

        addObject(myPoints, 175, 50);
        addObject(new Aim(),500,560);
        
    }

    public void act()
    {
        myPoints.update(Player.sScore + Ball.sScore);
    }
    
    }
