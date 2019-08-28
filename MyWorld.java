import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class MyWorld here.
 * 
 * @author Teodoras Saulys, VU MIF PS 1 k. 2 gr. 2 pogr.
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    public int dot_count = 0;    
    Dot dot = new Dot();
    private WallEraser eraser = new WallEraser();
    Node node[][];
    int nodex, nodey;
    public int x,y,z;
    Stack<Integer> stack = new Stack();
    int score=0;
    PacMan pacman = new PacMan();
    int distance = 0;
    int choice;
    boolean[] choices = new boolean[4];
    int unvisited_cells = 0;
        
    public String intString(int a)
    {
	Integer b = new Integer(a);
	return b.toString();
    }
    public void set_status()
    {
        int x,y;
        for(int i=0;i<4;i++)
        {
            do{
            x = Greenfoot.getRandomNumber(nodex-5) + 2;
            y = Greenfoot.getRandomNumber(nodey-5) + 2;
            }while(eraser.is_occupied(node[x][y].x, node[x][y].y));
            Dew dew = new Dew();
            dew.setImage("dew.png");
            addObject(dew,node[x][y].x,node[x][y].y);
        }
        dot_count=0;
        unvisited_cells=0;
	for(int i=0;i<nodex;i++)
	    for(int j=0;j<nodey;j++)
	    {
		    if(!eraser.is_occupied(node[i][j].x, node[i][j].y))
		    {
			node[i][j].is_occupied = false;
                        unvisited_cells++;
                        if(!eraser.is_occupied_dew(node[i][j].x, node[i][j].y))
                        {
                            Dot d =new Dot();
                            d.setImage("red_dot.png");
                            addObject(d,node[i][j].x,node[i][j].y);
                            dot_count++;
                        }
		    }
		    else node[i][j].is_occupied = true;  
		    node[i][j].visited = false;
	    }
        for(int i=2;i<nodex-2;i++)
            for(int j=2;j<nodey-2;j++)
            {
                if(!node[i+1][j].is_occupied)
                    node[i][j].neigh[0] = node[i+1][j];
                if(!node[i][j+1].is_occupied)
                    node[i][j].neigh[1] = node[i][j+1];
                if(!node[i-1][j].is_occupied)
                    node[i][j].neigh[2] = node[i-1][j];
                if(!node[i][j-1].is_occupied)
                    node[i][j].neigh[3] = node[i][j-1];
            }

    }
    
    public void reset()
    {
        int x,y,z;
        for(int i=2;i<nodex-2;i++)
            for(int j=2;j<nodey-2;j++)
            {
                eraser.erase_pac(node[i][j].x,node[i][j].y);
                eraser.erase_ghost(node[i][j].x,node[i][j].y);
            }
            do
	    {
	       x = Greenfoot.getRandomNumber(nodex-5) + 2;
	       y = Greenfoot.getRandomNumber(nodey-5) + 2;
            }while(eraser.is_occupied(node[x][y].x, node[x][y].y));
            pacman.xpos=x;
            pacman.ypos=y;
            do
            {
                z = Greenfoot.getRandomNumber(4);
            } while(node[x][y].neigh[z] == null);
            pacman.setRotation(90*z);
            addObject(pacman,node[x][y].x,node[x][y].y);
            
            do
            {
                x = Greenfoot.getRandomNumber(nodex-5) + 2;
                y = Greenfoot.getRandomNumber(nodey-5) + 2;
            }while(node[x][y].is_occupied);

            Ghost ghost = new Ghost(true,x,y);
            ghost.setImage("ghost.png");
            addObject(ghost,node[x][y].x,node[x][y].y);
            
        
        for(int i=0;i<3;i++)
        {
            do
            {
                x = Greenfoot.getRandomNumber(nodex-5) + 2;
                y = Greenfoot.getRandomNumber(nodey-5) + 2;
            }while(node[x][y].is_occupied);

                Ghost gh = new Ghost(false,x,y);
                gh.setImage("ghost.png");
                addObject(gh,node[x][y].x,node[x][y].y);
            
        }
    }
    
    public void reset_all()
    {
        int x,y,z;
        for(int i=2;i<nodex-2;i++)
            for(int j=2;j<nodey-2;j++)
            {
                node[i][j].is_occupied=true;
                node[i][j].visited=false;
                node[i][j].evaluated=false;
                node[i][j].distance=35768;
                for(int k=0;k<4;k++)
                    node[i][j].neigh[k]=null;
                eraser.erase(node[i][j].x,node[i][j].y);
                eraser.erase_dot(node[i][j].x,node[i][j].y);
                eraser.erase_pac(node[i][j].x,node[i][j].y);
                eraser.erase_ghost(node[i][j].x,node[i][j].y);
                WallCell cell = new WallCell();
                cell.setImage("black_cell.png");
                addObject(cell,node[i][j].x,node[i][j].y);
                
            }
        unvisited_cells=0;
        for(int i=2;i<nodex-2;i+=2)
            for(int j=2;j<nodey-2;j+=2)
            {
                unvisited_cells++;
                eraser.erase(node[i][j].x,node[i][j].y);   
            }
        do{
        x = Greenfoot.getRandomNumber(nodex-5) + 2;
        y = Greenfoot.getRandomNumber(nodey-5) + 2;
        }while(eraser.is_occupied(node[x][y].x, node[x][y].y));
        
        for(int i=0;i<nodex;i++)
            node[i][0].visited = true;
        for(int i=0;i<nodey-1;i++)
            node[nodex-1][i].visited = true;
        for(int i=nodex-1;i>=0;i--)
            node[i][nodey-1].visited = true;
        for(int i=nodey-1;i>=0;i--)
            node[0][i].visited = true;
        
        for(int i=0;i<4;i++) choices[i]=false;


        generate_maze(x,y);
        set_status();
        
        do{
        x = Greenfoot.getRandomNumber(nodex-5) + 2;
        y = Greenfoot.getRandomNumber(nodey-5) + 2;
        }while(eraser.is_occupied(node[x][y].x, node[x][y].y));
        pacman.xpos=x;
        pacman.ypos=y;
        do
        {
            z = Greenfoot.getRandomNumber(4);
        }while(node[x][y].neigh[z] == null);
        pacman.setRotation(90*z);

        addObject(pacman,node[x][y].x,node[x][y].y);
        
            do
            {
                x = Greenfoot.getRandomNumber(nodex-5) + 2;
                y = Greenfoot.getRandomNumber(nodey-5) + 2;
            }while(node[x][y].is_occupied);

            Ghost ghost = new Ghost(true,x,y);
            ghost.setImage("ghost.png");
            addObject(ghost,node[x][y].x,node[x][y].y);
            
        
        for(int i=0;i<3;i++)
        {
            
            do
            {
                x = Greenfoot.getRandomNumber(nodex-5) + 2;
                y = Greenfoot.getRandomNumber(nodey-5) + 2;
            }while(node[x][y].is_occupied);

                Ghost gh = new Ghost(false,x,y);
                gh.setImage("ghost.png");
                addObject(gh,node[x][y].x,node[x][y].y);
            
        }
    }
    
    public void generate_maze(int gx, int gy)
    {
        x = gx;
        y = gy;
        /*
        for(int i=0;i<nodex;i++)
        {
            for(int j=0;j<nodey;j++)
            {
                if(node[i][j].visited) System.out.printf("o");
                else System.out.printf(" ");
            }
            System.out.printf("\n");
        }
*/
            if(!node[gx][gy].visited) unvisited_cells--;
            node[gx][gy].visited = true;
            for(int i=0;i<4;i++) choices[i]= false;
            for(int i=0;i<4;i++)
                switch(i)
                {
                    case 0:
                        if(!node[gx+2][gy].visited)
                            choices[i] = true; break;
                    case 1:
                        if(!node[gx][gy+2].visited)
                            choices[i] = true; break;
                    case 2:
                        if(!node[gx-2][gy].visited)
                            choices[i] = true; break;
                    case 3:
                        if(!node[gx][gy-2].visited)
                            choices[i] = true; break;
                }
            if( !choices[0] && !choices[1] && !choices[2] && !choices[3] )
            {
                if(!stack.empty()){
                    do{
                        gy = stack.pop();
                        gx = stack.pop();
                        for(int i=0;i<4;i++) choices[i]= false;
                        for(int i=0;i<4;i++)
                            switch(i)
                            {
                                case 0:
                                    if(!node[gx+2][gy].visited)
                                        choices[i] = true; break;
                                case 1:
                                    if(!node[gx][gy+2].visited)
                                        choices[i] = true; break;
                                case 2:
                                    if(!node[gx-2][gy].visited)
                                        choices[i] = true; break;
                                case 3:
                                    if(!node[gx][gy-2].visited)
                                        choices[i] = true; break;
                            }
                        
                    }while(!choices[0] && !choices[1] && !choices[2] && !choices[3] && !stack.empty());
                    
                    generate_maze(gx,gy);
                }
            }
            else do
            {
                choice = Greenfoot.getRandomNumber(4);
            }while(!choices[choice]);
            stack.push(gx);
            stack.push(gy);
            switch(choice)
            {
                case 0:
                    gx+=2; if(gx!=nodex-1) eraser.erase(node[gx-1][gy].x,node[gx-1][gy].y); break;
                case 1:
                    gy+=2; if(gy!=nodey-1) eraser.erase(node[gx][gy-1].x,node[gx][gy-1].y); break;
                case 2:
                    gx-=2; if(gx!=0) eraser.erase(node[gx+1][gy].x,node[gx+1][gy].y); break;
                case 3:
                    gy-=2; if(gy!=0) eraser.erase(node[gx][gy+1].x,node[gx][gy+1].y); break;
            }
            if( unvisited_cells>0) generate_maze(gx,gy);
        for(int i=2;i<nodex-2;i++)
        {
            eraser.erase(node[i][2].x,node[i][2].y);
            node[i][2].is_occupied=false;
        }
        for(int i=2;i<nodex-2;i++)
        {
            eraser.erase(node[i][nodey-3].x,node[i][nodey-3].y);
            node[i][nodey-3].is_occupied=false;
        }
        for(int i=2;i<nodey-2;i++)
        {
            eraser.erase(node[2][i].x,node[2][i].y);
            node[2][i].is_occupied=false;
        }
        for(int i=2;i<nodey-2;i++)
        {
            eraser.erase(node[nodex-3][i].x,node[nodex-3][i].y);
            node[nodex-3][i].is_occupied=false;
        }

        for(int i=2;i<nodex-2;i++)
        {
            eraser.erase(node[i][nodey/2].x,node[i][nodey/2].y);
            node[i][nodey/2].is_occupied=false;
        }

            
        }
    
    public int path(int x, int y, int des_x, int des_y)
    {
        for(int k=0;k<nodex;k++)
            for(int z=0;z<nodey;z++)
                {
                    node[k][z].visited=false;
                    node[k][z].evaluated=false;
                    node[k][z].distance=35768;
                }

        Stack<Integer> stack_dijkstra = new Stack();
        int tempx,tempy;
            node[x][y].set_distance(0);
            while(!node[des_x][des_y].visited)
            {
                for(int i=2;i<nodex-2;i++)
                    for(int j=2;j<nodey-2;j++)
                    {
                        if(node[i][j].evaluated && !node[i][j].visited && !node[des_x][des_y].visited)
                            node[i][j].set_distance(node[i][j].distance);
                    }
            }
            
        Node current = node[des_x][des_y];
        
        stack_dijkstra.push(current.x);
        stack_dijkstra.push(current.y);
        
        while(current != node[x][y])
        {
            for(int i=3;i>=0;i--)
            {
                if(current.neigh[i] != null)
                    if(current.distance == current.neigh[i].distance+1)
                    {
                            stack_dijkstra.push(current.neigh[i].x);
                            stack_dijkstra.push(current.neigh[i].y);
                            current = current.neigh[i]; i=0;                  
                        if(current == node[x][y])
                        {
                            
                            stack_dijkstra.pop();
                            stack_dijkstra.pop();
                            tempy = stack_dijkstra.pop();
                            tempx = stack_dijkstra.pop();
                            
                            for(int j=0;j<4;j++)
                                if(node[x][y].neigh[j]!=null)
                                    if(node[x][y].neigh[j].x == tempx && node[x][y].neigh[j].y == tempy)
                                        return j;
                        }
                    }
            }
        }
        return -1;
    }

    public MyWorld()
    {    
        super(320,240,1); 
        Greenfoot.setSpeed(57);
        setBackground("blue.jpg");
        int xpos = 0, ypos = 0;
        
        
        eraser.setImage("blank.png");
        addObject(eraser,0,0);
        
        for(int i=25;i<=getWidth()-25;i+=25)
            for(int j=25;j<=getHeight()-25;j+=25)
            {
                WallCell cell = new WallCell();
                cell.setImage("black_cell.png");
                addObject(cell,i,j);
                nodex = i/25; nodey = j/25;
            }
        node = new Node[nodex][nodey];
        
        
        for(int i=25;i<=getWidth()-25;i+=25)
            for(int j=25;j<=getHeight()-25;j+=25)
            {
                node[i/25-1][j/25-1] = new Node(i,j);
                node[i/25-1][j/25-1].nodex=nodex;
                node[i/25-1][j/25-1].nodey=nodey;
                if(eraser.is_occupied(i,j)){
                    node[i/25-1][j/25-1].is_occupied = true;
                }
            }

        for(int i=2;i<nodex-2;i+=2)
            for(int j=2;j<nodey-2;j+=2)
            {
                unvisited_cells++;
                eraser.erase(node[i][j].x,node[i][j].y);   
            }
        
        do{
        x = Greenfoot.getRandomNumber(nodex-5) + 2;
        y = Greenfoot.getRandomNumber(nodey-5) + 2;
        }while(eraser.is_occupied(node[x][y].x, node[x][y].y));

        //addObject(new PacMan(),node[x][y].x, node[x][y].y);
        
        for(int i=0;i<nodex;i++)
            node[i][0].visited = true;
        for(int i=0;i<nodey-1;i++)
            node[nodex-1][i].visited = true;
        for(int i=nodex-1;i>=0;i--)
            node[i][nodey-1].visited = true;
        for(int i=nodey-1;i>=0;i--)
            node[0][i].visited = true;
        
        for(int i=0;i<4;i++) choices[i]=false;
        generate_maze(x,y);
        set_status();
       

	
	    
	
	int rx,ry;
	
	    do{
	    rx = Greenfoot.getRandomNumber(nodex-5) + 2;
	    ry = Greenfoot.getRandomNumber(nodey-5) + 2;
	    }while(eraser.is_occupied(node[rx][ry].x, node[rx][ry].y));

        pacman.xpos=x;
        pacman.ypos=y;
        do
        {
            z = Greenfoot.getRandomNumber(4);
        }while(node[x][y].neigh[z] == null);
        pacman.setRotation(90*z);

       	addObject(pacman,node[x][y].x,node[x][y].y);
        
            do
            {
                x = Greenfoot.getRandomNumber(nodex-5) + 2;
                y = Greenfoot.getRandomNumber(nodey-5) + 2;
            }while(node[x][y].is_occupied);

            Ghost ghost = new Ghost(true,x,y);
            ghost.setImage("ghost.png");
            addObject(ghost,node[x][y].x,node[x][y].y);
            
        
        for(int i=0;i<3;i++)
        {
            
            do
            {
                x = Greenfoot.getRandomNumber(nodex-5) + 2;
                y = Greenfoot.getRandomNumber(nodey-5) + 2;
            }while(node[x][y].is_occupied);

                Ghost gh = new Ghost(false,x,y);
                gh.setImage("ghost.png");
                addObject(gh,node[x][y].x,node[x][y].y);
            
        }
	/*dijkstra_path(x,y,2,2);        
        
        while(!stack_dijkstra.empty())
        {
            y = stack_dijkstra.pop();
            x = stack_dijkstra.pop();
            WallCell cell = new WallCell();
            cell.setImage("red_cell.png");
            addObject(cell,node[x][y].x,node[x][y].y);
        }
        
        */

        
      /*
        while(!stack.empty())
        {
            y = stack.pop();
            x = stack.pop();
            showText("1",node[x][y].x,node[x][y].y);
        }
*/

        
        
    }
    @Override
        public void act()
        {
            //if(Greenfoot.isKeyDown("a"))
                //path(2,2,pacman.xpos,pacman.ypos);
            showText("Lives: "+intString(pacman.lives),node[2][1].x,node[2][1].y);
            showText("Score: "+intString(score),node[10][1].x,node[10][1].y);
        }
        
        public final void makeline(int x, int y, int length, boolean right_down)
        {
            if(right_down)
            {
                for(int i=x;i<(x+length);i++)
                    addObject(new WallCell(),i,y);
            }
            else
            {
                for(int i=y;i<(y+length);i++)
                    addObject(new WallCell(),x,i);
            }
            
        }
        /*
        public void get_permissions()
        {
            for(int i=0;i<getWidth();i++)
                for(int j=0;j<getHeight();j++)
                    if(!eraser.is_occupied(i,j))
                        permission[i][j] = true;
        }
*/
}
