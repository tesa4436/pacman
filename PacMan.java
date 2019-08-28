import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PacMan here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PacMan extends Actor
{
    /**
     * Act - do whatever the PacMan wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    int lives = 3;
    public boolean roguestate = false;
    int xpos, ypos;
    int rogue_timer;
    WallCell facing_wall = null;
    public int rotation;
    int xplus=0, yplus=0;
    boolean can_turn=true, can_move=true;
    public int getLives() { return lives; }
    boolean immune=false;
    
    Node[][] node;
    
    //if( getWorld()) != null ){}
    
    @Override
    public void act() {
    if(getWorld()!=null)
    {
        if(lives > 0 && can_turn){
            if( Greenfoot.isKeyDown("right"))
	    {
                if(!((MyWorld) getWorld()).node[xpos+1][ypos].is_occupied)
                {
                    setRotation(0);
                }
	    }
            else if( Greenfoot.isKeyDown("down"))
	    {
                if(!((MyWorld) getWorld()).node[xpos][ypos+1].is_occupied)
                {
                    setRotation(90);
                }
	    }
            else if( Greenfoot.isKeyDown("left"))
	    {
                if(!((MyWorld) getWorld()).node[xpos-1][ypos].is_occupied)
                {
                    setRotation(180);
                }
	    }
            else if( Greenfoot.isKeyDown("up"))
	    {
                if(!((MyWorld) getWorld()).node[xpos][ypos-1].is_occupied)
                {
                    setRotation(270);
                }

	    }
        }
        
	    xplus=0;yplus=0;
	    switch(getRotation())
	    {
		case 0:
		    if(!((MyWorld) getWorld()).node[xpos+1][ypos].is_occupied)
		    {
			xplus = 1; yplus = 0;
		    }
		    break;
		case 90:
		    if(!((MyWorld) getWorld()).node[xpos][ypos+1].is_occupied)
		    {
			xplus = 0; yplus = 1;
			
		    }

		    break;
		
		case 180:
		    if(!((MyWorld) getWorld()).node[xpos-1][ypos].is_occupied)	
		    {			
			xplus = -1; yplus = 0;
			
		    }

		    break;
		
		case 270:
		    if(!((MyWorld) getWorld()).node[xpos][ypos-1].is_occupied)
		    {			
			xplus = 0; yplus = -1;
		    }
		    break;
	    }
	    
	    if(!((MyWorld) getWorld()).node[xpos+xplus][ypos+yplus].is_occupied && lives>0 && can_move)
	    {
		if( (getX() != ((MyWorld) getWorld()).node[xpos+xplus][ypos+yplus].x) || (getY() != ((MyWorld) getWorld()).node[xpos+xplus][ypos+yplus].y) )
		{
                    can_turn=false;
		    setLocation(getX()+xplus,getY()+yplus);
		    //Greenfoot.delay(1);
		}
                else
                {
                    can_turn=true;
                    this.xpos+=this.xplus;
                    this.ypos+=this.yplus;
                }
            
                //System.out.println(xpos+"  "+ ypos);
            }
	if(isTouching(Ghost.class) && !roguestate)
        {
            can_move=false;
            setImage("pacman_red.png");
            lives--;
            
            if(lives==0)
            {
                Greenfoot.delay(200);
                ( (MyWorld) getWorld()).reset_all();
                lives=3;
                setImage("pacman_small.png");
                ((MyWorld) getWorld()).score=0;
                roguestate=false;
                Greenfoot.delay(200);
                can_move=true;
            }
            else
            {
                Greenfoot.delay(200);
                ( (MyWorld) getWorld()).reset();
                Greenfoot.delay(200);
                roguestate=false;
                setImage("pacman_small.png");
                can_move=true;
            }
            
        }
        else if(isTouching(Ghost.class) && roguestate)
        {
            removeTouching(Ghost.class);
            ((MyWorld) getWorld()).score++;
        }
       
        if(isTouching(Dot.class))
        {
            ((MyWorld) getWorld()).dot_count--;
            removeTouching(Dot.class);
            if(((MyWorld) getWorld()).dot_count==0)
            {
                Greenfoot.delay(200);
                ( (MyWorld) getWorld()).reset_all();
                lives=3;
                setImage("pacman_small.png");
                Greenfoot.delay(200);
                can_move=true;
            }

            
        }
        
        if(isTouching(Dew.class))
        {
            roguestate = true;
            rogue_timer = 1000;
            removeTouching(Dew.class);
            setImage("pacman_rogue.png");
        }

        if(roguestate)
        {
            rogue_timer--;
            if(rogue_timer==0)
            {
                roguestate=false;
                setImage("pacman_small.png");
            }
        }
    }
	
	
	
}
    }

