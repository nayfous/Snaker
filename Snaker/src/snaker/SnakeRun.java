package snaker;

//Importing necessary library's
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class SnakeRun extends JPanel {
	private static final int WINDOWSIZE = 500;
	ArrayList<Food> foods = new ArrayList<Food>();
	ArrayList<Snake> snakes = new ArrayList<Snake>();
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public int timesky = 0;
	public int bullets = 1;
	public int score = 0;
	
	public SnakeRun() {
		// Getting the key events
		foods.add(new Food(this, WINDOWSIZE));
		snakes.add(new Snake(this, WINDOWSIZE));
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				for (int i = 0; i < snakes.size(); i++) {
					snakes.get(i).keyReleased(e);
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				snakes.get(0).keyPressed(e);				
			}
		});
		setFocusable(true);
		
	}
	
	private void move() {
		// Initializing move function of the objects
		for (int i = 0; i < snakes.size(); i++) {
			for (int j = 0; j < foods.size(); j++) {
				if (snakes.get(i).collideFood(foods.get(j))) {
					gameOver();
				}
			}
			snakes.get(i).move();
		}
		
		for (int i = 0; i < projectiles.size(); i++) {
			for (int j = 0; j < foods.size(); j++) {
				if (projectiles.get(i).collideFood(foods.get(j))) {
					foods.add(new Food(this, WINDOWSIZE));
					snakes.add(new SnakeBody(this, WINDOWSIZE, snakes.get(snakes.size() - 1)));
					score += 1;
					projectiles.clear();
					break;
				}
			}
		}
		
		for (int i = 0; i < foods.size(); i++) {
			foods.get(i).move();
		}
		
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).move();
		}
		
	}
	
	@Override
	public void paint(Graphics window) {
		// Initializing the window and painting the snake
		super.paint(window);
		Graphics2D window2d = (Graphics2D) window;
		window2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for (int i = 0; i < foods.size(); i++) {
			foods.get(i).paint(window2d);
		}
		
		for (int i = 0; i < snakes.size(); i++) {
			snakes.get(i).paint(window2d);
		}
		
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).paint(window2d);
		}
		
		window.setColor(Color.GRAY);
		window.setFont(new Font("Verdana", Font.BOLD, 15));
		window.drawString("Bullets: " + String.valueOf(bullets), 10, 15);
		window.drawString("Score: " + String.valueOf(score), 400, 15);
		
	}
	
	public void gameOver() {
		JOptionPane.showMessageDialog(this, "Your score is: " + score, "Game Over", JOptionPane.YES_NO_OPTION);
		System.exit(ABORT);
	}
	
	public static void main(String[] args) throws InterruptedException {
		// Making frame object and setting size, location and visibility 
		JFrame window = new JFrame("Snaker");
		SnakeRun snakeRun = new SnakeRun();
		window.add(snakeRun);
		window.setSize(WINDOWSIZE, WINDOWSIZE);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		while (true) {
			// the main loop
			snakeRun.move();
			snakeRun.repaint();
			Thread.sleep(1000/30);
			
			if (snakeRun.timesky == 60) {
				if (snakeRun.bullets < 10) {
					snakeRun.bullets += 1;
				}
				snakeRun.timesky = 0;
			}
			
			snakeRun.timesky += 1;
			
		}

	}

}
