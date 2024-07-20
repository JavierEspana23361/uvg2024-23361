import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class anuncio here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class anuncio extends Actor
{
    /**
     * Act - do whatever the anuncio wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public anuncio()
    {
        GreenfootImage myImage = getImage () ;
        int myNewHeight = (int)myImage.getHeight () /5;
        int myNewWidth = (int)myImage.getWidth () /5;
        myImage.scale (myNewWidth, myNewHeight);
    }
}
