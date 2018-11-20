package pl.edu.agh.to2.yadc.entity;

public abstract class Mob extends Entity{

	protected int velocity;
	
	public Mob(double xInit, double yInit, double collisionRadius) {
		super(xInit, yInit, collisionRadius);
	}
}
