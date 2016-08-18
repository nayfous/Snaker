package snaker;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Snake {
	private static final int DIAMETER = 10;
	static final float RADIUS = DIAMETER / 2;
	SnakeRun snakeRun;
	int x;
	int y; 
	int xOld; 
	int yOld;
	int xSpeed = 0;
	int ySpeed = 0;
	
	public Snake(SnakeRun snakeRun, int size) {
		// initializing Snake parameters 
		this.snakeRun = snakeRun;
		this.x = (size / 2);
		this.y = (size / 2);
		this.xOld = this.x - DIAMETER;
		this.yOld = this.y;
	}
	
	public void move() {
		// If snake touches the borders of the window the speed will become 0 
		if (x + xSpeed < 0) {
			xSpeed = 0;
		} else if (x + xSpeed > snakeRun.getWidth() - DIAMETER) {
			xSpeed = 0;
		} else if (y + ySpeed < 0) {
			ySpeed = 0;
		} else if (y + ySpeed > snakeRun.getHeight() - DIAMETER) {
			ySpeed = 0;
		} else {
			xOld = x;
			yOld = y;
		}
		
		// Change coordinates of the snake
		x += xSpeed;
		y += ySpeed;
		
	}
	
	public void paint(Graphics2D window) {
		// Drawing a rectangle on the window
		window.fillRect(x, y, DIAMETER, DIAMETER);
		
	}
	
	public void keyPressed(KeyEvent key) {
		// If arrow keys are pressed the speed will change accordingly 
		if (key.getKeyCode() == KeyEvent.VK_LEFT) {
			xSpeed = -DIAMETER;
			ySpeed = 0;
		} 
		if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
			xSpeed = DIAMETER;
			ySpeed = 0;
		}
		if (key.getKeyCode() == KeyEvent.VK_UP) {
			ySpeed = -DIAMETER;
			xSpeed = 0;
		}
		if (key.getKeyCode() == KeyEvent.VK_DOWN) {
			ySpeed = DIAMETER;
			xSpeed = 0;
		}
		
	}
	
	public void keyReleased(KeyEvent key) {
		if (key.getKeyCode() == KeyEvent.VK_SPACE) {
			if (snakeRun.bullets > 0) {
				snakeRun.projectiles.add(new Projectile(snakeRun, getCentralX() - Projectile.RADIUS, getCentralY() - RADIUS - Projectile.DIAMETER, 0, -5));
				snakeRun.projectiles.add(new Projectile(snakeRun, getCentralX() - Projectile.RADIUS, getCentralY() + RADIUS, 0, 5));
				snakeRun.projectiles.add(new Projectile(snakeRun, getCentralX() + RADIUS, getCentralY() - Projectile.RADIUS, 5, 0));
				snakeRun.projectiles.add(new Projectile(snakeRun, getCentralX() - Projectile.DIAMETER - RADIUS, getCentralY() - Projectile.RADIUS, -5, 0));
				snakeRun.bullets -= 1;
			}
			
		}
	}
	
	public float getCentralX() {
		return x + RADIUS;
		
	}
	
	public float getCentralY() {
		return y + RADIUS;
		
	}
	
	public boolean collideFood(Food food) {
		float xD = getCentralX() - food.getCenterX();
		float yD = getCentralY() - food.getCenterY();
		
		float sumRadius = RADIUS + Food.RADIUS;
		float sqrtRadius  = sumRadius * sumRadius;
		
		float distanceSqr = (xD * xD) + (yD * yD);
		
		if (distanceSqr <= sqrtRadius) {
			return true;
		}
		
		return false;	
		
	}
	
}
