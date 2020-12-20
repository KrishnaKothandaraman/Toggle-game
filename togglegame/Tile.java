import java.io.Serializable;

//3035660756
public class Tile implements Serializable{
	
	private Boolean state;
	private Tile[] neighbours;
	
	Tile(){
		state = false;
		neighbours = new Tile[4];
	}
	/**
	 * Method to return the current state of the tile
	 * @return Boolean true is tile is on and false if Tile is off
	 */
	
	public Boolean getState() {
		return this.state;
	}
	/**
	 * Method to change the state of the tile being clicked
	 * @return int 1 if tile is now on and -1 if tile is now off
	 */
	public int toggle() {
		if(this.state) {
			this.state = false;
			return -1;
		}
		else {
			this.state = true;
			return 1;
		}
	}
	/**
	 * Sets the neighbouts for a given tile
	 * @param t the neighbour tile
	 * @param i	value specifying if the tile is top = 0 , bottom = 2 , left = 3 or right = 1 neighbour
	 */
	public void setNeighbour(Tile t, int i) {
		neighbours[i] = t;
	}
	
	/**
	 * Toggles the state of the tile being clicked and it's neighbour tiles
	 * @return the difference between number of new tiles that are ON and OFF.
	 */
	
	public int press() {
		int ctr = 0;
		ctr += this.toggle();
		for(int i=0;i<4;i++) {
			if(this.neighbours[i] != null) {
					ctr += this.neighbours[i].toggle();
			}
		}
		return ctr;
	}
	
	/**
	 *  Method to get whether the tile is On or OFF
	 *  @return String ON if tile is On and "--" if tile is OFF
	 */
	public String toString() {
		if(this.getState() == true)
			return "ON";
		else
			return "--";
	}
	
}
