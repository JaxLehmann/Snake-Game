import javax.swing.JFrame; // JFrame used for GUI (buttons and frame)

public class GameFrame extends JFrame {
	GameFrame() {
		this.add(new GamePanel()); // draws the snake, food, background (Game in the window)
		this.setTitle("Snake"); // simple title
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // button to stop program "x"
		this.setResizable(false); // user cannot resize the window
		this.pack(); // sizes window to fit components it contains such as GamePanel
		this.setVisible(true); // actually displays the window created on the screen
		this.setLocationRelativeTo(null); // centers position to middle of the screen
	}
	// Constructor ^
}
