// 305660756
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * This class is used for providing the GUI for the Toggle game.
 * 
 * @author Kenneth Wong
 *
 */
public class ToggleGUI {
	private ToggleGame game;
	private JFrame frame;
	private TogglePanel togglePanel;
	private JLabel statusBar;
	private int tileWidth;
	private int tileHeight;
	private Image tileOnImage;
	private Image tileOffImage;
	
	/**
	 * Creates and returns an instance of the ToggleGUI class.
	 */
	public ToggleGUI() {		
		// load the images for the tiles
		tileOnImage = new ImageIcon("src/red_tile.png").getImage();
		tileOffImage = new ImageIcon("src/green_tile.png").getImage();
		tileWidth = tileOnImage.getWidth(null);
		tileHeight = tileOffImage.getHeight(null);
		
		// create the window frame
		frame = new JFrame("Toggle");

		// create the panel for displaying tiles
		togglePanel = new TogglePanel();
		// create the label for displaying steps
		statusBar = new JLabel();
		
		// compose the left panel
		frame.add(togglePanel, BorderLayout.CENTER);
		frame.add(statusBar, BorderLayout.SOUTH);
	
		// create the menubar
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		JMenu menu;
		JMenuItem menuItem;
		
		JMenu file = new JMenu("File");
		JMenuItem saveGame = new JMenuItem("Save");
		JMenuItem loadGame = new JMenuItem("Load");
		file.add(saveGame);
		file.add(loadGame);
		saveGame.addActionListener(new SaveMenuItemListener());
		loadGame.addActionListener(new LoadMenuItemListener());
		
		// create Game menu
		menu = new JMenu("Game");
		menuBar.add(file);
		menuBar.add(menu);
		menuItem = new JMenuItem("5x5");
		menuItem.addActionListener(new fiveMenuItemListener());
		menu.add(menuItem);
		menuItem = new JMenuItem("7x7");
		menuItem.addActionListener(new sevenMenuItemListener());
		menu.add(menuItem);
		menuItem = new JMenuItem("9x9");
		menuItem.addActionListener(new nineMenuItemListener());
		menu.add(menuItem);
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	private void refresh() {
		if (game != null) {
			if (game.endOfGame()) {
				statusBar.setText("Game completed in " + game.getSteps() + " steps.");
			}
			else {
				statusBar.setText("Steps = " + game.getSteps() + ".");
			}
		}
		frame.repaint();
	}
	
	private void resize() {
		togglePanel.calSize();
		statusBar.setText(" ");
		frame.validate();
		frame.pack();
	}
	
	private void newGame(int n) {
		if (n < 5) {
			n = 5;
		}
		game = new ToggleGame(n);

		System.out.println("Created a new " + n + "x" + n + " Toggle game.");
		resize();
		refresh();
	}
	
	private void loadGame() {
		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Toggle save files","tog");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(frame);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	try {
	    		FileInputStream fs = new FileInputStream(chooser.getSelectedFile());
	    		ObjectInputStream os = new ObjectInputStream(fs);
	    		this.game = (ToggleGame) os.readObject();
	    		resize();
	    		refresh();
	    		os.close();
	    		} catch (Exception ex) {
	    		ex.printStackTrace();
	    		}
	    }
		refresh();
	}
	
	private void saveGame() {
		if (game == null || game.endOfGame()) return;
		
		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Toggle save files", "tog");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showSaveDialog(frame);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	try {
	    		FileOutputStream fs = new FileOutputStream(chooser.getSelectedFile());
	    		ObjectOutputStream os = new ObjectOutputStream(fs);
	    		os.writeObject(game);
	    		os.close();
	    		} catch (Exception ex) {
	    		ex.printStackTrace();
	    		}
	    	
	    	/*
	    	 * TO DO: add your code here for saving a Toggle game to a file.
	    	 * You may use JFileChooser.getSelectedFile() to retrieve the selected file.
	    	 */
	    }
		refresh();
	}
	
	private class TogglePanel extends JPanel implements MouseListener{
		private static final long serialVersionUID = -9051442904166756843L;
		public TogglePanel() {
			calSize();
			setBackground(new Color(180, 180, 180));
			this.addMouseListener(this);
		}
		
		/**
		 * Calculate the preferred size of the panel based on the game board
		 */
		public void calSize() {
			if (game != null && game.getSize() > 0) {
				int preferredWidth = tileWidth * game.getSize();
				int preferredHeight = tileHeight * game.getSize();
				setPreferredSize(new Dimension(preferredWidth, preferredHeight));
			} else {
				int preferredWidth = tileWidth * 5;
				int preferredHeight = tileHeight * 5;
				setPreferredSize(new Dimension(preferredWidth, preferredHeight));
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if(!game.endOfGame()){
				int x = e.getX();
				int y = e.getY();
				int cardRow = y/tileHeight;
				int cardCol = x/tileWidth;
				game.getBoard()[cardCol][cardRow].press();
			}
			repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void paintComponent(Graphics g) {
			if(game != null) {
			Graphics2D g2 = (Graphics2D) g;
			for(int i=0;i<game.getSize();i++) {
				for(int j=0;j<game.getSize();j++) {
					if(game.getBoard()[i][j].getState() == true) {
						g2.drawImage(tileOnImage, i*tileHeight , j*tileWidth, this);
					}
					else
						g2.drawImage(tileOffImage, i*tileHeight, j*tileWidth ,this);
				}
			}
		}
			
	}
		

	} // TogglePanel
	

	private class SaveMenuItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			saveGame();
		}
	} 
	private class LoadMenuItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			loadGame();
		}
	} 
	private class fiveMenuItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			newGame(5);
		}
	} // fiveMenuItemListener
	
	private class sevenMenuItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			newGame(7);
		}
	} // sevenMenuItemListener
	
	private class nineMenuItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			newGame(9);
		}
	} // nineMenuItemListener
		
	/**
	 * main() method for starting this Java application.
	 * 
	 * @param args not being used
	 *            
	 */
	public static void main(String[] args) {
		new ToggleGUI();
	}
}
