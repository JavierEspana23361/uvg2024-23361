import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends Actor
{
    boolean touchingEnemigo = false;
    
    GifImage gif = new GifImage("azul.gif");
    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        checkKeyPress();
        Star();
        punto();
        move();
        hitEnemigo();
    }
    public static int vScore = 0;
    public static int sScore = 0;
    public static int Health = 3;
    public Player()
    {
        setImage("azul.gif");
        GreenfootImage azul = getImage();
        int myNewHeight = (int)azul.getHeight () /2;
        int myNewWidth = (int)azul.getWidth () /2;
        azul.scale (myNewWidth,myNewHeight);
    }
    public void checkKeyPress()
    {
        setImage(gif.getCurrentImage());
        setRotation(0);
        if (getY() <= 115){  
            setLocation(getX(), 116);
        }
        else if (getY() >= 500){
            setLocation(getX(), 499);
        }
        else if (getX() >= 934){
            setLocation(933, getY());
        }
        else if (getX() <= 21){
            setLocation(22, getY());
        }
        if (Greenfoot.isKeyDown("up")){
            setRotation(270);
            move(5);
        }
        if (Greenfoot.isKeyDown("down")){
            setRotation(90);
            move(5);
        }
        if (Greenfoot.isKeyDown("left")){
            setRotation(180);
            move(5);
        }
        if (Greenfoot.isKeyDown("right")){
            setRotation(0);
            move(5);
        }
    }
    public void Star()
    {
        Actor star;
        star = getOneObjectAtOffset(0, 0, Star.class);
        if (star != null)
        {
           getWorld().removeObject(star); 
        
           World world;
           world = getWorld();
           sScore = sScore + 250;
        }
    }
    public void punto()
    {
        if (getX() >= 35 && getX() <= 85 && getY() >= 100 && getY() <= 510)
        {
            vScore = vScore + 1;
            sScore = sScore + 1000;
        }
    }
    public void move()
    {
        int newX = this.getX();
        int newY = this.getY();
        if (Greenfoot.isKeyDown("up")) 
        {
            newY--;
        }
        else if (Greenfoot.isKeyDown("left")) {
           newX--; 
        }
        else if (Greenfoot.isKeyDown("down")) {
            newY++;
        }
        else if (Greenfoot.isKeyDown("right")) {
          newX++;  
        }
        if (newX > 825 && newX < 950 && newY > 510 && newY < 105){
            this.setLocation(newX, newY);
        }
        
    }
    public void hitEnemigo(){
        Actor enemigo = getOneIntersectingObject(Enemigo.class);
        //Actor enemigo;
        //enemigo = getOneObjectAtOffset(0, 0, Enemigo.class);
        if (enemigo != null)
        {
            if(touchingEnemigo == false){
                Health = Health - 1;
                touchingEnemigo = true;
            }
        } else {
            touchingEnemigo = false;
        }
    }
    
}
