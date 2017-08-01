package eg.edu.guc.entanglement.engine;

import java.util.ArrayList;

public class Player {

	private int currentPosition;
	private int nextPosition;

	private Tile currentTile;
	private Tile nextTile;

	private int currentOpening;
	private int nextOpening;

	private boolean gameOver;
	private int id;
	private ArrayList<Path> path;
	private int score;

	private Path currentPath;
	private int currentSide;

	public Player(int sp, int i, boolean t, Tile c) {
		setCurrentPosition(sp);
		setCurrentTile(c);
		setGameOver(t);
		setScore(0);
		setId(i);

		setCurrentOpening(i * (c.getOpenings()));
		setCurrentSide(currentOpening / (c.getOpenings()));

	}
	
	public void initializeNextPosition(int w)
	{
		if (id == 0) {
			nextPosition = currentPosition - w;
		}
		if (id == 1) {
			nextPosition = currentPosition + 1;
		}
		if (id == 2) {
			nextPosition = currentPosition + w;
		}
		if (id == 3) {
			nextPosition = currentPosition - 1;
		}
	}

	public void incrementScore() {
		score++;
	}

	public void setCurrentTile(Tile currentTile) {
		this.currentTile = currentTile;
	}

	public Tile getCurrentTile() {
		return currentTile;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public void setPath(ArrayList<Path> path) {
		this.path = path;
	}

	public ArrayList<Path> getPath() {
		return path;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setNextPosition(int nextPosition) {
		this.nextPosition = nextPosition;
	}

	public int getNextPosition() {
		return nextPosition;
	}

	public void setNextOpening(int nextOpening) {
		this.nextOpening = nextOpening;
	}

	public int getNextOpening() {
		return nextOpening;
	}

	public void setCurrentOpening(int currentOpening) {
		this.currentOpening = currentOpening;
	}

	public int getCurrentOpening() {
		return currentOpening;
	}

	public void setNextTile(Tile nextTile) {
		this.nextTile = nextTile;
	}

	public Tile getNextTile() {
		return nextTile;
	}

	public void setCurrentPath(Path currentPath) {
		this.currentPath = currentPath;
	}

	public Path getCurrentPath() {
		return currentPath;
	}

	public void setCurrentSide(int currentSide) {
		this.currentSide = currentSide;
	}

	public int getCurrentSide() {
		return currentSide;
	}
}
