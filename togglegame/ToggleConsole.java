import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * This class is used for modeling a console for the Toggle game. It provides
 * the user interface for the game.
 * 
 * @author Kenneth Wong
 */
public class ToggleConsole {
	private ToggleGame game;
	private Scanner scanner;

	/**
	 * Creates and returns an instance of the ToggleConsole class.
	 */
	public ToggleConsole() {
		game = new ToggleGame();
		scanner = new Scanner(System.in);
	}

	/**
	 * Creates and returns an instance of the ToggleConsole class.
	 * 
	 * @param n an int specifying the size of the board
	 */
	public ToggleConsole(int n) {
		game = new ToggleGame(n);
		scanner = new Scanner(System.in);
	}

	/**
	 * Returns an array of int representing the row and column indices of the tile
	 * selected through the console.
	 * 
	 * @return An array of int [i j] representing the row and column indices of the
	 *         selected tile
	 */
	public int[] getSelected() {
		int[] selected = new int[2];
		int i = 0;
		int size = game.getSize();

		System.out.print("Pls input row and column indices: ");
		String input = scanner.nextLine();

		StringTokenizer st = new StringTokenizer(input);
		while (st.hasMoreTokens()) {
			try {
				int idx = Integer.parseInt(st.nextToken());
				if (idx >= 0 && idx < size) {
					selected[i] = idx;
					i++;
					if (i > 1) {
						break;
					}
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}

		if (i == 2) {
			return selected;
		} else {
			return null;
		}
	}

	/**
	 * Redraws the console.
	 */
	public void refresh() {
		if (game == null) return;

		for (int i = 0; i < game.getSize(); i++) {
			for (int j = 0; j < game.getSize(); j++) {
				System.out.print(game.getBoard()[i][j] + " ");
			}
			System.out.println("");
		}
		if (game.endOfGame()) {
			System.out.println("Game completed in " + game.getSteps() + " steps.");
		} else {
			System.out.println("Steps = " + game.getSteps() + ".");
		}
	}

	/**
	 * Starts to play the game.
	 */
	public void play() {
		refresh();
		while (!game.endOfGame()) {
			int[] selected = getSelected();
			while (selected == null) {
				selected = getSelected();
			}

			int i = selected[0];
			int j = selected[1];
			game.press(i, j);
			;
			System.out.println("");
			refresh();
		}
	}
	
	/**
	 * main() method for starting this Java application.
	 * 
	 * @param args not being used
	 *            
	 */
	public static void main(String[] args) {
		ToggleConsole console = new ToggleConsole();
		console.play();
	}
}
