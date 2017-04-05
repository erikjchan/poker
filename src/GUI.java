import javax.swing.JFrame;

/**
 * Class that represents the GUI for the game.
 * 
 * @author echan
 */
public class GUI {
	private Table table;

	/**
	 * Constructor class.
	 */
	public GUI() {
		table = new Table();
		table.pack();
		table.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// table.getContentPane().add(emptyLabel, BorderLayout.CENTER);
		table.pack();
		table.setVisible(true);
	}

	/**
	 * Get the frame of the GUI.
	 * 
	 * @return the frame of the GUI
	 */
	public JFrame getFrame() {
		return this.table;
	}
}