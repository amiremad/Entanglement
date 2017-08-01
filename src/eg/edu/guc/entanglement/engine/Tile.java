package eg.edu.guc.entanglement.engine;

import java.util.StringTokenizer;

public class Tile {

	private Path[] paths;
	private int openings;
	private int sides;
	private String config;


	private boolean fixed;

	public Tile()
	{
		setFixed(true);
	}
	
	public Tile(int x, int y, String p) {
		
		setConfig(p);
		
        setOpenings(x);
        
		setFixed(false);
		
		setSides(y);
		
		StringTokenizer s = new StringTokenizer(p);
		
		paths = new Path[x * y];

        for (int k = 0; k < paths.length; k++)
        {
        	if (s.hasMoreTokens())
        	{
        	paths[k] = new Path(k, Integer.parseInt(s.nextToken()));
        	}
        }
		
       
		
	}
	
	public void rotateTileClockwise()
	{
		int x = openings;
		int y = sides;
		for (int i = 0; i < paths.length; i++)
		{
			int s = paths[i].getStart();
			int e = paths[i].getEnd();
            if (s > ((x * y) - 1 - x))
            {
            	paths[i].setStart((s + x) - (x * y));
            }
            else
            {
            	paths[i].setStart(s + x);
            }
            if (e > ((x * y) - 1 - x))
            {
            	paths[i].setEnd((e + x) - (x * y));
            }
            else
            {
            	paths[i].setEnd(e + x);
            }
		}
		 
	
	}
	
	public void rotateTileAntiClockwise()
	{
		int x = openings;
		int y = sides;
		for (int i = 0; i < paths.length; i++)
		{
			int s = paths[i].getStart();
			int e = paths[i].getEnd();
            if (s < x)
            {
            	paths[i].setStart((s + (x * y)) - x);
            }
            else
            {
            	paths[i].setStart(s - x);
            }
            if (e < x)
            {
            	paths[i].setEnd((s + (x * y)) - x);
            }
            else
            {
            	paths[i].setEnd(e - x);
            }
		}
		
	}
	
	public void setFixed(boolean fixTile) {
		this.fixed = fixTile;
	}

	public boolean isFixed() {
		return fixed;
	}

	public Path[] getPaths() {
		return paths;
	}

	public void setPaths(Path[] paths) {
		this.paths = paths;
	}

	public void setOpenings(int openings) {
		this.openings = openings;
	}

	public int getOpenings() {
		return openings;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getConfig() {
		return config;
	}

	public void setSides(int sides) {
		this.sides = sides;
	}

	public int getSides() {
		return sides;
	}
}
