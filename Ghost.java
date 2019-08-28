import greenfoot.*;
/*
 *
 * @author teo
 */
public class Ghost extends Actor
{
    int xpos, ypos;
    int xplus=0,yplus=0;
    int mov_dir=-1;
    boolean is_moving = false;
    boolean tracking;
    int ran_x=0, ran_y=0;
    
    public Ghost(boolean track, int x, int y)
    {
        tracking=track;
        xpos=x;
        ypos=y;
        
    }
    
    @Override
    public void act()
    {
        if(tracking && !((MyWorld) getWorld()).pacman.roguestate)
        {
            if(!is_moving)
            {
                if(!((MyWorld) getWorld()).pacman.roguestate)
                    mov_dir = ((MyWorld) getWorld()).path(xpos,ypos,((MyWorld) getWorld()).pacman.xpos,((MyWorld) getWorld()).pacman.ypos);
            }
            move(mov_dir);
        }
        else 
        {
            if(((ran_x==0 && ran_y==0) || (ran_x==xpos && ran_y==ypos)))
            {
                    do
                    {
                        ran_x = Greenfoot.getRandomNumber(((MyWorld) getWorld()).nodex-5)+2;
                        ran_y = Greenfoot.getRandomNumber(((MyWorld) getWorld()).nodey-5)+2;
                    } while(((MyWorld) getWorld()).node[ran_x][ran_y].is_occupied);
            }
            if(!is_moving)
                mov_dir = ((MyWorld) getWorld()).path(xpos,ypos,ran_x,ran_y);
            move(mov_dir);
        }
        
    }

    @Override
    public void move(int direction)
    {
	    switch(direction)
	    {
		case 0:
		    if(!((MyWorld) getWorld()).node[xpos+1][ypos].is_occupied)
		    {
			xplus = 1; yplus = 0;
		    }
		    break;
		case 1:
		    if(!((MyWorld) getWorld()).node[xpos][ypos+1].is_occupied)
		    {
			xplus = 0; yplus = 1;
			
		    }

		    break;
		
		case 2:
		    if(!((MyWorld) getWorld()).node[xpos-1][ypos].is_occupied)	
		    {			
			xplus = -1; yplus = 0;
			
		    }

		    break;
		
		case 3:
		    if(!((MyWorld) getWorld()).node[xpos][ypos-1].is_occupied)
		    {			
			xplus = 0; yplus = -1;
		    }
		    break;
                case -1:
                    //System.out.println("NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                default:
                    xplus=0;yplus=0;
	    }
	    
	    if(!((MyWorld) getWorld()).node[xpos+xplus][ypos+yplus].is_occupied && !isTouching(PacMan.class))
	    {
                
		if( (getX() != ((MyWorld) getWorld()).node[xpos+xplus][ypos+yplus].x) || (getY() != ((MyWorld) getWorld()).node[xpos+xplus][ypos+yplus].y) )
		{
                    is_moving=true;
		    setLocation(getX()+xplus,getY()+yplus);
		    //Greenfoot.delay(1);
		}
                else
                {
                    is_moving=false;
                    this.xpos+=this.xplus;
                    this.ypos+=this.yplus;
                }

            }
            
    }
}
