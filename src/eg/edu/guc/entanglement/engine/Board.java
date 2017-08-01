package eg.edu.guc.entanglement.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class Board implements BoardInterface {
	private Tile[] tilesDeck;
	private Player[] players;
	private Tile[] tiles;
	private int turn;
	private int width;
	private int height;
	private int openings;
	private int startPosition;

	public Board(java.io.Reader r) {
		try {

			BufferedReader br = new BufferedReader(r);
			int s = Integer.parseInt(br.readLine()); // sides
			int o = Integer.parseInt(br.readLine()); // openings
			setOpenings(o);
			int t = Integer.parseInt(br.readLine()); // number of tiles

			tilesDeck = new Tile[t];

			for (int i = 0; i < t; i++) {
				tilesDeck[i] = new Tile(o, s, br.readLine());
			}
			int w = Integer.parseInt(br.readLine()); // width
			int h = Integer.parseInt(br.readLine()); // height
			tiles = new Tile[w * h];
			setWidth(w);
			setHeight(h);

			startPosition = Integer.parseInt(br.readLine()); // starting
																// location
			int p = Integer.parseInt(br.readLine()); // players

			players = new Player[p];
			tiles[startPosition] = new SourceTile(openings);

			for (int m = 0; m < players.length; m++) {
				initializePlayerList(m);
			}

			for (int a = 0; a < players.length; a++) {
				for (int p1 = 0; p1 < players[a].getNextTile().getPaths().length; p1++) {
					if (players[a].getNextTile().getPaths()[p1].getStart() == players[a]
							.getNextOpening()) {
						players[a].setCurrentPath(players[a].getNextTile()
								.getPaths()[p1]);
					}
				}
			}
			
			for(int i = 0;i<players.length;i++)
			{
				if(!players[i].isGameOver())
				{
					turn = i;break;
				}
			}

		} catch (IOException e) {
			System.out.println("Thers's an IOExeption");
		}

	}

	public void initializePlayerList(int m) {
		switch (m) {

		case 0:
			players[m] = new Player(startPosition, m, isToBoundary(width,
					height, startPosition)[m], tiles[startPosition]);
			players[m].setNextTile(cloneTile(tilesDeck[(int) Math.random()
					* tilesDeck.length]));
			players[m].setNextOpening(adjacentOpening(
					players[m].getCurrentOpening(), openings));

		case 1:
			players[m] = new Player(startPosition, m, isToBoundary(width,
					height, startPosition)[m], tiles[startPosition]);
			players[m].setNextTile(cloneTile(tilesDeck[(int) Math.random()
					* tilesDeck.length]));
			players[m].setNextOpening(adjacentOpening(
					players[m].getCurrentOpening(), openings));

		case 2:
			players[m] = new Player(startPosition, m, isToBoundary(width,
					height, startPosition)[m], tiles[startPosition]);
			players[m].setNextTile(cloneTile(tilesDeck[(int) Math.random()
					* tilesDeck.length]));
			players[m].setNextOpening(adjacentOpening(
					players[m].getCurrentOpening(), openings));

		case 3:
			players[m] = new Player(startPosition, m, isToBoundary(width,
					height, startPosition)[m], tiles[startPosition]);
			players[m].setNextTile(cloneTile(tilesDeck[(int) Math.random()
					* tilesDeck.length]));
			players[m].setNextOpening(adjacentOpening(
					players[m].getCurrentOpening(), openings));
		default:
			break;
		}
		players[m].initializeNextPosition(width);
	}

	public boolean fixTile() {
		if (isGameOver(turn)) {
			return false;
		}
		while (!isGameOver(turn)) {

			tiles[players[turn].getNextPosition()] = players[turn]
					.getNextTile();

			occupyPath();

			updateCurrents();
			
			

			players[turn].incrementScore();
			
			if (isToBoundary(height, width, players[turn].getNextPosition())[players[turn]
					.getCurrentPath().getEnd() / openings]
					|| tiles[nextTilePosition(players[turn].getCurrentSide(),
							players[turn].getCurrentPosition())] instanceof SourceTile) {
				checkAutoFix();
				players[turn].setGameOver(true);

				continue;
			}

			players[turn].setNextPosition(nextTilePosition(
					players[turn].getCurrentSide(),
					players[turn].getCurrentPosition()));

			players[turn].setNextOpening(adjacentOpening(
					players[turn].getCurrentOpening(), openings));

			if (tiles[players[turn].getNextPosition()] == null) {
				updateNextsStop();
				checkAutoFix();
				break;
			}

			else {
				updateNextsContinue();
				checkAutoFix();
				continue;

			}

		}

		updateTurn();

		return true;

	}

	public void updateNextsStop() {
		players[turn].setNextTile(cloneTile(tilesDeck[(int) Math.random()
				* tilesDeck.length]));

		for (int k = 0; k < players[turn].getNextTile().getPaths().length; k++) {
			if (players[turn].getNextTile().getPaths()[k].getStart() == players[turn]
					.getNextOpening()) {
				players[turn].setCurrentPath(players[turn].getNextTile()
						.getPaths()[k]);
			}
		}
	}

	public void updateNextsContinue() {
		players[turn].setNextTile(tiles[nextTilePosition(
				players[turn].getCurrentSide(),
				players[turn].getCurrentPosition())]);

		for (int y = 0; y < players[turn].getNextTile().getPaths().length; y++) {
			if (players[turn].getNextTile().getPaths()[y].getStart() == players[turn]
					.getNextOpening()
					&& !players[turn].getNextTile().getPaths()[y].isOccupied()) {
				players[turn].setCurrentPath(players[turn].getNextTile()
						.getPaths()[y]);
			}
		}
	}

	public void updateCurrents() {
		players[turn].setCurrentTile(players[turn].getNextTile());

		players[turn].setCurrentPosition(players[turn].getNextPosition());

		players[turn]
				.setCurrentOpening(players[turn].getCurrentPath().getEnd());

		players[turn].setCurrentSide(players[turn].getCurrentPath().getEnd()
				/ openings);
	}

	public void occupyPath() {
		for (int i = 0; i < players[turn].getNextTile().getPaths().length; i++) {
			if (players[turn].getNextTile().getPaths()[i].getStart() == players[turn]
					.getCurrentPath().getStart()
					|| players[turn].getNextTile().getPaths()[i].getStart() == players[turn]
							.getCurrentPath().getEnd()) {
				tiles[players[turn].getNextPosition()].getPaths()[i]
						.setOccupied(true);
			}
		}
	}

	public void updateTurn() {
		if (turn == players.length - 1) {

			for (int i = 0; i < players.length; i++) {
				if (!players[i].isGameOver()) {
					turn = i;
					break;
				}
			}

		} else {

			for (int i = turn + 1; i < players.length; i++) {
				if (!players[i].isGameOver()) {
					turn = i;
					break;
				}
			}

		}
	}

	public boolean switchTile(int t) {
		try {
			if (!players[turn].isGameOver()) {
				players[turn].setNextTile(tilesDeck[t]);

				for (int k = 0; k < players[turn].getNextTile().getPaths().length; k++) {
					if (players[turn].getNextTile().getPaths()[k].getStart() == players[turn]
							.getNextOpening()) {
						players[turn].setCurrentPath(players[turn]
								.getNextTile().getPaths()[k]);
						return true;
					}
				}

				return true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Tile doesn't exist");
			return false;
		}
		return false;

	}

	public int nextTilePosition(int side, int position) {

		switch (side) {
		case 0:
			return position - width;
		case 1:
			return position + 1;
		case 2:
			return position + width;
		case 3:
			return position + -1;
		default:
			break;
		}

		return position;
	}

	public boolean isGameOver(int t) {
		try {
			return players[t].isGameOver();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Player doesn't exist");
			return false;
		}
	}

	public int getScore(int p) {
		return players[p].getScore();
	}

	public boolean rotateTileClockwise() {
		if (!players[turn].isGameOver()) {
			players[turn].getNextTile().rotateTileClockwise();
			for (int i = 0; i < players[turn].getNextTile().getPaths().length; i++) {
				if (players[turn].getNextTile().getPaths()[i].getStart() == players[turn]
						.getNextOpening()) {
					players[turn].setCurrentPath(players[turn].getNextTile()
							.getPaths()[i]);
					break;
				}
			}
			return true;
		}
		return false;
	}

	public boolean rotateTileAntiClockwise() {
		if (!players[turn].isGameOver()) {
			players[turn].getNextTile().rotateTileAntiClockwise();
			for (int i = 0; i < players[turn].getNextTile().getPaths().length; i++) {
				if (players[turn].getNextTile().getPaths()[i].getStart() == players[turn]
						.getNextOpening()) {
					players[turn].setCurrentPath(players[turn].getNextTile()
							.getPaths()[i]);
					break;
				}
			}
			return true;
		}
		return false;
	}

	public static int adjacentOpening(int x, int openings) {
		int[][] s = new int[4][openings];
		for (int i = 0; i < 4; i++) {
			int m = i * openings;
			for (int j = 0; j < openings; j++) {
				s[i][j] = m;
				m++;
			}
		}

		for (int h = 0; h < 4; h++) {
			for (int k = 0; k < openings; k++) {
				if (s[h][k] == x) {
					if (h == 0 || h == 1) {
						return s[h + 2][openings - k - 1];
					} else {
						return s[h - 2][openings - k - 1];
					}
				}
			}
		}
		return x;

	}

	public static Tile cloneTile(Tile t) {
		return new Tile(t.getOpenings(), t.getSides(), t.getConfig());
	}

	public void checkAutoFix() {
		for (int m = 0; m < players.length; m++) {
			if (players[turn].getCurrentPosition() == players[m]
					.getNextPosition() && m != turn) {
				while (!isGameOver(m)) {
				//	players[m].setNextTile(tiles[players[turn].getCurrentPosition()]);

			    	for (int k = 0; k < players[m].getNextTile().getPaths().length; k++) {
						if (players[m].getNextTile().getPaths()[k].getStart() == players[m]
								.getNextOpening()) {
							players[m].setCurrentPath(players[m].getNextTile()
									.getPaths()[k]);
						}
					}
					ocuppyPathAuto(m);

					updateCurrentsAuto(m);

					players[m].incrementScore();

					if (isToBoundary(height, width,
							players[m].getNextPosition())[players[m]
							.getCurrentPath().getEnd() / openings]
							|| tiles[nextTilePosition(
									players[m].getCurrentSide(),
									players[m].getCurrentPosition())] instanceof SourceTile) {
						players[m].setGameOver(true);
						return;
					}

					players[m].setNextPosition(nextTilePosition(
							players[m].getCurrentSide(),
							players[m].getCurrentPosition()));

					players[m].setNextOpening(adjacentOpening(
							players[m].getCurrentOpening(), openings));

					if (tiles[players[m].getNextPosition()] == null) {
						updateNextsStopAuto(m);
						break;
					}

					else {
						updateNextsContinueAuto(m);
						continue;
					}
				}
			}
		}
	}

	public void updateNextsStopAuto(int m) {
		players[m].setNextTile(cloneTile(tilesDeck[(int) Math.random()
				* tilesDeck.length]));

		for (int k = 0; k < players[m].getNextTile().getPaths().length; k++) {
			if (players[m].getNextTile().getPaths()[k].getStart() == players[m]
					.getNextOpening()) {
				players[m]
						.setCurrentPath(players[m].getNextTile().getPaths()[k]);

			}
		}
	}

	public void updateNextsContinueAuto(int m) {
		players[m].setNextTile(tiles[nextTilePosition(
				players[m].getCurrentSide(),
				players[m].getCurrentPosition())]);

		for (int y = 0; y < players[m].getNextTile().getPaths().length; y++) {
			if (players[m].getNextTile().getPaths()[y].getStart() == players[m]
					.getNextOpening()
					&& !players[m].getNextTile().getPaths()[y].isOccupied()) {
				players[m].setCurrentPath(players[m].getNextTile()
						.getPaths()[y]);

			}
		}
	}

	public void ocuppyPathAuto(int m) {
		for (int i = 0; i < players[m].getNextTile().getPaths().length; i++) {
			if (players[m].getNextTile().getPaths()[i].getStart() == players[m]
					.getCurrentPath().getStart()
					|| players[m].getNextTile().getPaths()[i].getStart() == players[m]
							.getCurrentPath().getEnd()) {
				tiles[players[m].getNextPosition()].getPaths()[i]
						.setOccupied(true);
			}
		}
	}

	public void updateCurrentsAuto(int m) {
		players[m].setCurrentTile(tiles[players[m]
					.getNextPosition()]);

		players[m].setCurrentPosition(players[m].getNextPosition());

		players[m].setCurrentOpening(players[m].getCurrentPath().getEnd());

		players[m].setCurrentSide(players[m].getCurrentPath().getEnd()
				/ openings);
	}

	public static boolean[] isToBoundary(int x, int y, int c) {

		boolean[] z = new boolean[4];
		int[][] board = new int[x][y];
		for (int i = 0; i < x; i++) {
			int m = i * x;
			for (int j = 0; j < y; j++) {
				board[i][j] = m;
				m++;
			}
		}

		for (int v = 0; v < x; v++) {

			for (int m = 0; m < y; m++) {

				if (board[v][m] == c) {

					if (v == 0) {
						if (c == 0) {
							z[0] = true;
							z[3] = true;
						} else {
							if (c == x - 1) {

								z[0] = true;
								z[1] = true;
							} else {
								z[0] = true;
							}
						}
					} else {
						if (v == y - 1) {

							if (m == 0) {
								z[2] = true;
								z[3] = true;
							} else {
								if (m == x - 1) {

									z[1] = true;
									z[2] = true;
								} else {
									z[2] = true;
								}
							}
						} else {
							if (m == 0) {
								z[3] = true;
							} else {
								if (m == x - 1) {
									z[1] = true;
								}
							}
						}
					}
				}
			}
		}
		return z;
	}

	public Path getNextPath(Path p, Tile t) {
		for (int i = 0; i < t.getPaths().length; i++) {
			if (t.getPaths()[i].getStart() == p.getEnd() + ((openings * 3) - 1)) {
				return t.getPaths()[i];
			}
		}
		return p;
	}

	public int getTurn() {
		return turn;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setTiles(Tile[] tiles) {
		this.tiles = tiles;
	}

	public Tile[] getTiles() {
		return tiles;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setOpenings(int openings) {
		this.openings = openings;
	}

	public int getOpenings() {
		return openings;
	}

	public void setCurrentPosition(int currentPosition) {
		this.startPosition = currentPosition;
	}

	public int getCurrentPosition() {
		return startPosition;
	}

	public static void main(String[] args) {
		Board b = new Board(new StringReader("4\n" + "3\n" + "1\n"
				+ "3 11 6 0 9 8 2 10 5 4 7 1\n" + "3\n" + "3\n" + "4\n" + "2"));
		
		b.getTiles()[0] = new Tile(3,4,"3 11 6 0 9 8 2 10 5 4 7 1");

		
		
		b.fixTile();
		b.fixTile();
		b.fixTile();
		

		boolean[] b1 = isToBoundary(7, 7, 7);

		for (int i = 0; i < b1.length; i++) {
			System.out.print(b1[i] + " ");
		}

		int[][] sides = new int[4][3];
		for (int i = 0; i < 4; i++) {
			int m = i * 3;
			for (int j = 0; j < 3; j++) {
				sides[i][j] = m;
				m++;
			}
		}
		System.out.println();

		for (int a = 0; a < sides.length; a++) {
			for (int c = 0; c < 3; c++) {
				System.out.print(sides[a][c] + " ");
			}
			System.out.println();
		}

		System.out.println(adjacentOpening(2, 3));

	}

	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}

	public int getStartPosition() {
		return startPosition;
	}
}
