package vgPrototype;

import java.awt.Rectangle;

public class Enemy {

	private int power, speedX, centerX, centerY;
	private Background bg = StartingClass.getBg1();
	
	public Rectangle r = new Rectangle(0,0,0,0);
	
	public int health = 5;
	
	private int movementSpeed;

	// Behavioral Methods
	public void update() {
		follow();
		centerX += speedX;
		speedX = bg.getSpeedX() * 5 + movementSpeed;
		r.setBounds(centerX + 25, centerY + 48, 23, 33);
		
		if (r.intersects(Robot.yellowRed)){
			checkCollision();
		}
		
	}

	
	private void checkCollision() {
		if (r.intersects(Robot.rect) || r.intersects(Robot.rect2) || r.intersects(Robot.rect3) || r.intersects(Robot.rect4)){
			System.out.println("collision");
			
			}
		}
	
public void follow() {
		
		if (centerX < -95 || centerX > 810){
			movementSpeed = 0;
		}

		else if (Math.abs(Robot.getCenterX() - centerX) < 5) {
			movementSpeed = 0;
		}

		else {

			if (Robot.getCenterX() >= centerX) {
				movementSpeed = 1;
			} else {
				movementSpeed = -1;
			}
		}

	}	

	public void die() {
	}

	public void attack() {
	}

	public int getPower() {
		return power;
	}

	public int getSpeedX() {
		return speedX;
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public Background getBg() {
		return bg;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setBg(Background bg) {
		this.bg = bg;
	}

}
