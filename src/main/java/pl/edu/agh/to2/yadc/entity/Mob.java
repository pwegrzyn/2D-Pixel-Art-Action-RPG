package pl.edu.agh.to2.yadc.entity;

public abstract class Mob extends Entity{

	public Mob(double xInit, double yInit, double collisionRadius) {
		super(xInit, yInit, collisionRadius);
		setTexture("resources/test_entity.png");
	}
}
