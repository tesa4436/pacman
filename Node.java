import java.util.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author teo
 */
public class Node{
    
    public int x;
    public int y;
    public boolean is_occupied;
    public boolean visited;
    public boolean evaluated;
    public int distance = 35768;
    Node[] neigh = new Node[4];
    int nodex,nodey;
   
    public Node(int a, int b)
    {
        x = a;
        y = b;
    }
    
    public void set_distance(int dist)
    {
        if(!this.is_occupied)
        {
            this.visited = true;
            this.evaluated = true;
            if(dist<distance)
                distance=dist;
            
            for(int i=0;i<4;i++)
                if(neigh[i]!=null)
                    if( (!neigh[i].visited && !neigh[i].is_occupied))
                    {
                        neigh[i].distance = distance+1;
                        neigh[i].evaluated=true;
                    }
        }
    }
    
    public void set_occupied(boolean a) { is_occupied = a; }
    
}
