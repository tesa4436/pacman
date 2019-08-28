/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import greenfoot.*;
/**
 *
 * @author teo
 */
public class WallEraser extends Actor{
    
    private Actor cell;
    
    public void erase(int x, int y)
    {
        setLocation(x,y);
        if((cell = getOneIntersectingObject(WallCell.class)) != null)
            removeTouching(WallCell.class);
    }
    public void erase_dot(int x, int y)
    {
        setLocation(x,y);
        if((cell = getOneIntersectingObject(Dot.class)) != null)
            removeTouching(Dot.class);
    }
    public void erase_pac(int x, int y)
    {
        setLocation(x,y);
        if((cell = getOneIntersectingObject(PacMan.class)) != null)
            removeTouching(PacMan.class);
    }
    public void erase_ghost(int x, int y)
    {
        setLocation(x,y);
        if((cell = getOneIntersectingObject(Ghost.class)) != null)
            removeTouching(Ghost.class);
    }

    public boolean is_occupied(int x,int y)
    {
        setLocation(x,y);
        return (isTouching(WallCell.class) || isTouching(PacMan.class));
    }
    public boolean is_occupied_dew(int x,int y)
    {
        setLocation(x,y);
        return (isTouching(Dew.class));
    }

}
