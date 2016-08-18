package snaker;

import java.awt.event.KeyEvent;

public class SnakeBody extends Snake {
	private Snake predecessor;
	
	public SnakeBody(SnakeRun snakerun, int size,  Snake predecessor) {
		super(snakerun, size);
		this.predecessor = predecessor;
		this.x = predecessor.xOld;
		this.y = predecessor.yOld;
	}
	
	public void move() {
		xOld = x;
		yOld = y;
		x = predecessor.xOld;
		y = predecessor.yOld;
	}
	
	public void keyReleased(KeyEvent key) {
		if (key.getKeyCode() == KeyEvent.VK_SPACE) {
			if (snakeRun.bullets > 0) {
			snakeRun.projectiles.add(new Projectile(snakeRun, getCentralX() - Projectile.DIAMETER / 2, getCentralY() - RADIUS - Projectile.DIAMETER, 0, -5));
			snakeRun.projectiles.add(new Projectile(snakeRun, getCentralX() - Projectile.DIAMETER / 2, getCentralY() + RADIUS, 0, 5));
			snakeRun.projectiles.add(new Projectile(snakeRun, getCentralX() + RADIUS, getCentralY() - Projectile.DIAMETER / 2, 5, 0));
			snakeRun.projectiles.add(new Projectile(snakeRun, getCentralX() - Projectile.DIAMETER - RADIUS, getCentralY() - Projectile.DIAMETER / 2, -5, 0));
			}
		}
	}

}
