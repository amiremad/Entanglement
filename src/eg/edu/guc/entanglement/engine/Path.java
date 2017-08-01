package eg.edu.guc.entanglement.engine;

public class Path 
{
   private int start;
   private int end;
   private boolean occupied;
   
   public Path(int s, int e)
   {
	   setStart(s);
	   setEnd(e);
	   setOccupied(false);
   }

public void setStart(int start) {
	this.start = start;
}

public int getStart() {
	return start;
}

public void setEnd(int end) {
	this.end = end;
}

public int getEnd() {
	return end;
}

public void setOccupied(boolean occupied) {
	this.occupied = occupied;
}

public boolean isOccupied() {
	return occupied;
}
}
