import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
		
		static final int SCREEN_WIDTH = 1920;
		static final int SCREEN_HEIGHT = 1080;
		static final int UNIT_SIZE = 25; // apple size and grid space size
		static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
		static final int DELAY = 50;
		final int x[] = new int[GAME_UNITS];
		final int y[] = new int[GAME_UNITS];
		int bodyParts = 2;
		int applesEaten; // initial value of apples eaten set to 0
		int appleX; // x coordinate of apples eaten
		int appleY; // y position of apples eaten
		char direction = 'D'; // snake begins going Right
		boolean run = false; // program is running, initially false
		Timer timer;
		Random random;
		
		GamePanel() {
			random = new Random();
			this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
			this.setBackground(Color.DARK_GRAY);
			this.setFocusable(true);
			this.addKeyListener(new MyKeyAdapter());
			startGame();
			
		}
		// Constructor ^
		
		public void startGame() {
			newApple();
			run = true;
			timer = new Timer(DELAY, this);
			timer.start();
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			draw(g);
		}
		
		public void draw(Graphics g) {
			if (run ) {
				/* for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
					g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT); // creates grid for visualization
					g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE); // creates grid for visualization
				} */
				g.setColor(Color.GREEN);
				g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE); // apple size and coloring
				
				for (int i = 0; i < bodyParts; i++) {
					if (i == 0) {
						g.setColor(Color.green);
						g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
					}
					else {
						g.setColor(new Color(0, 255, 255));
						g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE); // creating head of snake
					}
				}
			}
			else {
				gameOver(g);
			}
		}
		
		public void newApple() {
			appleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE - 1)) * UNIT_SIZE; // random x position for apple
			appleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE - 1)) * UNIT_SIZE; // random y position for apple
		}
		
		public void move() {
			for (int i = bodyParts; i > 0; i--) {
				x[i] = x[i - 1];
				y[i] = y[i - 1];
			}
			switch (direction) {
			case 'W':
				y[0] = y[0] - UNIT_SIZE; // Up takes the head of the snake - 1 grid space
				break;
			case 'S':
				y[0] = y[0] + UNIT_SIZE;
				break;
			case 'A':
				x[0] = x[0] - UNIT_SIZE;
				break;
			case 'D':
				x[0] = x[0] + UNIT_SIZE;
				break;
			}
		}
		
		public void checkApple() {
			if ((x[0] == appleX) && (y[0] == appleY)) {
				bodyParts++;
				applesEaten++; // keeps score
				newApple(); // creates a new apple
			}
		}
		
		public void checkCollisions() {
			for (int i = bodyParts; i > 0; i--) {
				if ((x[0] == x[i] && y[0] == y [i])) {
					run = false; // checking for snake on snake collision
				}
			}
			if (x[0] < 0) {
				run = false; // snake head hits left wall
			}
			if (x[0] > SCREEN_WIDTH) {
				run = false; // snake head hits right wall
			}
			if (y[0] < 0) {
				run = false; // snake head hits ceiling
			}
			if (y[0] > SCREEN_HEIGHT) {
				run = false; // snake head hits floor
			}
			
			if (!run) {
				timer.stop();
			}
		}
		
		public void gameOver(Graphics g) {
			g.setColor(Color.PINK);
			g.setFont(new Font("Times New Roman", Font.BOLD, 80));
			FontMetrics font = getFontMetrics(g.getFont());
			g.drawString("GAME OVER", (SCREEN_WIDTH - font.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2);
			
			g.setColor(Color.PINK);
			g.setFont(new Font("Times New Roman", Font.BOLD, 40));
			FontMetrics font2 = getFontMetrics(g.getFont());
			g.drawString("SCORE: " + applesEaten, (SCREEN_WIDTH - font.stringWidth("SCORE: " + applesEaten))/2, g.getFont().getSize());
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (run) {
				move();
				checkApple();
				checkCollisions();
			}
			repaint();
		}
		
		public class MyKeyAdapter extends KeyAdapter {
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_A:
					if (direction != 'D') {
						direction = 'A';
					}
					break;
				case KeyEvent.VK_D:
					if (direction != 'A') {
						direction = 'D';
					}
					break;
				case KeyEvent.VK_W:
					if (direction != 'S') {
						direction = 'W';
					}
					break;
				case KeyEvent.VK_S:
					if (direction != 'W') {
						direction = 'S';
					}
					break;
			}
		}
	}
}
