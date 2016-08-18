package snaker;

import java.awt.Graphics2D;
import java.util.concurrent.ThreadLocalRandom;

public class Food {
	private static final int DIAMETER = 10;
	static final float RADIUS = DIAMETER / 2;
	private static final float INVERSEMASS = 1 / (4/3) * (float) (Math.PI) * (float) (Math.pow(RADIUS, 3));
	private SnakeRun snakeRun;
	float x;
	float y;
	float xMove;
	float yMove;
	float[] speed = {-5, 5, 0};
	float angleInDegree = ThreadLocalRandom.current().nextInt(360); 
	
	public Food(SnakeRun snakeRun, int size) {
		this.snakeRun = snakeRun;
		this.x = ThreadLocalRandom.current().nextInt(0, 2) * ThreadLocalRandom.current().nextInt(0, size);
		this.y = ThreadLocalRandom.current().nextInt(0, 2) * ThreadLocalRandom.current().nextInt(0, size);
		this.xMove = (float) (speed[ThreadLocalRandom.current().nextInt(0, 2)] * Math.cos(Math.toRadians(angleInDegree)));
		this.yMove = (float) (speed[ThreadLocalRandom.current().nextInt(0, 2)] * (float) (Math.sin(Math.toRadians(angleInDegree))));
		
	}
	
	public void move() {
		if (x + xMove < 0) {
			xMove = -xMove;
			x = 0;
		} else if (x + xMove > snakeRun.getWidth() - DIAMETER) {
			xMove = -xMove;
			x = snakeRun.getWidth() - DIAMETER;
		} else if (y + yMove < 0) {
			yMove = -yMove;
			y = 0;
		} else if (y + yMove > snakeRun.getHeight() - DIAMETER) {
			yMove = -yMove;
			y = snakeRun.getHeight() - DIAMETER;
		}
		
		x += xMove;
		y += yMove;
		
		
	}
	
	public void paint(Graphics2D window) {
		window.fillOval((int) x, (int) y, DIAMETER, DIAMETER);
		
	}
	
	public boolean collide(Food food) {
		float xD = getCenterX() - food.getCenterX();
		float yD = getCenterY() - food.getCenterY();
		
		float sqrtRadius  = (float) DIAMETER * (float) DIAMETER;
		
		float distanceSqr = (xD * xD) + (yD * yD);
		
		if (distanceSqr <= sqrtRadius) {
			return true;
		}
		
		return false;
	}
	
	public void collisionResponse(Food food) {
		
		float xSub = x - food.x;
		float ySub = y - food.y;
	    float distance = (float) Math.sqrt(xSub*xSub + ySub*ySub); 
	    float xMTD = xSub * (((float) DIAMETER - distance) / distance);
	    float yMTD = ySub * (((float) DIAMETER - distance) / distance);
	    float mtdDistance = (float) Math.sqrt(xMTD*xMTD + yMTD*yMTD); 

	    x = x + xMTD * 0.5f;
	    y = y + yMTD * 0.5f;
	    food.x = food.x - xMTD * 0.5f;
	    food.y = food.y - yMTD * 0.5f;

	    // impact speed
	    float vX = xMove - food.xMove;
	    float vY = yMove - food.yMove;
	    
	    if (mtdDistance != 0.0f) {
	    	xMTD = xMTD / mtdDistance;
	    	yMTD = yMTD / mtdDistance;
	    } else {
	    	xMTD = 0.0f;
	    	yMTD = 0.0f;
	    }
	    
	    float vn = vX * xMTD + vY * yMTD;

	    if (vn > 0.0f) return;

	    float i = (-(1.0f + 0.85f) * vn) / (INVERSEMASS + INVERSEMASS);
	    float xImpulse = xMTD * i;
	    float yImpulse = yMTD * i;

	    xMove = xMove + xImpulse * INVERSEMASS;
	    yMove = yMove + yImpulse * INVERSEMASS;
	    food.xMove = food.xMove - xImpulse * INVERSEMASS;
	    food.yMove = food.yMove - yImpulse * INVERSEMASS;
		
	}
	
	public float getCenterX() {
		return x + RADIUS;
		
	}
	
	public float getCenterY() {
		return y + RADIUS;
		
	}

}
