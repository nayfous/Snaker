package snaker;

import java.awt.Graphics2D;

public class Projectile {
	static final int DIAMETER = 5;
	static final float RADIUS = DIAMETER / 2;
	private SnakeRun snakeRun;
	float x;
	float y;
	float xMove;
	float yMove;
	
	public Projectile(SnakeRun snakeRun, float x, float y, float xMove, float yMove) {
		this.snakeRun = snakeRun;
		this.x = x;
		this.y = y;
		this.xMove = xMove;
		this.yMove = yMove;
		
	}
	
	public void move() {		
		x += xMove;
		y += yMove;
		
	}
	
	public void paint(Graphics2D window) {
		window.fillOval((int) x, (int) y, DIAMETER, DIAMETER);
		
	}
	
	public boolean collideWall() {
		if (x + xMove < 0) {
			return true;
		} else if (x + xMove > snakeRun.getWidth() - DIAMETER) {
			return true;
		} else if (y + yMove < 0) {
			return true;
		} else if (y + yMove > snakeRun.getHeight() - DIAMETER) {
			return true;
		}
		
		return false;
		
	}
	
	public float getCenterX() {
		return x + RADIUS;
		
	}
	
	public float getCenterY() {
		return y + RADIUS;
		
	}
	
	public boolean collideFood(Food food) {
		float xD = getCenterX() - food.getCenterX();
		float yD = getCenterY() - food.getCenterY();
		
		float sumRadius = RADIUS + Food.RADIUS;
		float sqrtRadius  = sumRadius * sumRadius;
		
		float distanceSqr = (xD * xD) + (yD * yD);
		
		if (distanceSqr <= sqrtRadius) {
			return true;
		}
		
		return false;
		
		
	}

}
