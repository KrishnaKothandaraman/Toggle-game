//3035660756
import java.io.Serializable;
import java.util.Random;

public class ToggleGame implements Serializable{
	private int gridSize;
	private int steps;
	private Tile[][] gameTiles;
	
	/**
	 * Public no param constructor to initialize a toggle game with size = 5. Also creates and initializes a board
	 */
	ToggleGame(){
		gridSize = 5;
		createBoard();
		steps = 0;
	}
	/**
	 * Public param constructor to initialize a toggle game with size = param. Also creates and initializes a board
	 * @param the size of the board
	 */
	ToggleGame(int size){
		gridSize = size;
		createBoard();
		steps = 0;
	}
	/**
	 * Returns the size of the board
	 * @return int with size of the board
	 */
	public int getSize() {
		return this.gridSize;
	}
	/**
	 * Returns the number of steps
	 * @return int value of the number of steps
	 */
	public int getSteps() {
		return this.steps;
	}
	/**
	 * Returns the gameBoard
	 * @return 2D array of Tiles 
	 */
	public Tile[][] getBoard() {
		return this.gameTiles;
	}
	/**
	 * Method to create a board of a particular size. Sets the neighbours of all tiles.
	 */
	public void createBoard() {
		gameTiles = new Tile[getSize()][getSize()];
		for(int i=0;i<this.getSize(); i++) {
			for(int j=0;j<this.getSize(); j++) {
				Tile t = new Tile();
				gameTiles[i][j] = t;
			}
		}
		for(int i=0;i<this.getSize(); i++) {
			for(int j=0;j<this.getSize(); j++) {

				if(i-1 >=0) {
					gameTiles[i][j].setNeighbour(gameTiles[i-1][j], 0);
				}
				else {
					gameTiles[i][j].setNeighbour(null, 0);
				}
				if(j-1 >=0) {
					gameTiles[i][j].setNeighbour(gameTiles[i][j-1], 3);
				}
				else {
					gameTiles[i][j].setNeighbour(null, 3);
				}
				if(i+1 <= getSize() - 1) {
					gameTiles[i][j].setNeighbour(gameTiles[i+1][j], 2);
				}
				else {
					gameTiles[i][j].setNeighbour(null, 2);
				}
				if(j+1 <= getSize() - 1) {
					gameTiles[i][j].setNeighbour(gameTiles[i][j+1], 1);
				}
				else {
					gameTiles[i][j].setNeighbour(null, 1);
				}
			}
		}
		initGameBoard();
	}
	/**
	 * Initializes the gameBoard by pressing Random Tiles.
	 */
	public void initGameBoard() {
		Random rand = new Random();
		int on =0;
		int size = getSize();
		for(int i=0;i<getSize();i++) {
			for(int j=0;j<getSize()/2;j++) {
				int randRow = rand.nextInt(size);
				int randCol = rand.nextInt(size);
				press(randRow,randCol);
			}
		}
	}
	
	/**
	 * Checks if the game has ended or not
	 * @return true is the game has ended and false otherwise.
	 */
	
	public Boolean endOfGame() {
		int ctr=0;
		for(int i=0;i<getSize();i++) {
			for(int j=0;j<getSize();j++) {
				if(gameTiles[i][j].getState() == false){
					ctr++;
				}
			}
		}
		if(ctr == getSize() * getSize())
			return true;
		else
			return false;
	}
	/**
	 * Toggles a given tile and it's neighbours
	 * @param r row value of the tile
	 * @param c column value of the tile.
	 */
	public void press(int r, int c) {
		int on = 0;
		on += gameTiles[r][c].press();
		steps++;
	}
}
