package pl.edu.agh.to2.yadc.entity;

import java.util.LinkedList;

public class MultipleProjectile extends Projectile{
	
	int numberOfProjectiles = 0;
	private LinkedList<Projectile> projectiles = new LinkedList<Projectile>();
	
	public MultipleProjectile(Entity owner, double collisionRadius, int numberOfProjectiles) {
		super(owner, collisionRadius);
		this.numberOfProjectiles = numberOfProjectiles;
		for(int i = 0; i < numberOfProjectiles; i++) {
			Projectile pro = ProjectileFactory.createProjectile(ProjectileTypes.NORMAL, owner, collisionRadius);
			pro.angularRotation += 2*Math.PI/numberOfProjectiles*i;
			projectiles.add(pro);
		}
	}
	
	public LinkedList<Projectile> getProjectiles(){
		return this.projectiles;
	}
	
}
