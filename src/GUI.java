import javax.swing.JFrame;

/**
 * Class that represents the GUI for the game.
 * 
 * @author echan
 */
public class GUI {
	private JFrame frame;

	/**
	 * Constructor class.
	 */
	public GUI() {
		frame = new JFrame();
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	/**
	 * Get the frame of the GUI.
	 * 
	 * @return the frame of the GUI
	 */
	public JFrame getFrame() {
		return this.frame;
	}
}