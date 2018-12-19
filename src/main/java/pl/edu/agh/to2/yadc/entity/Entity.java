package pl.edu.agh.to2.yadc.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pl.edu.agh.to2.yadc.area.Area;
import pl.edu.agh.to2.yadc.config.GlobalConfig;
import pl.edu.agh.to2.yadc.physics.Advanceable;
import pl.edu.agh.to2.yadc.physics.CollisionEngine;
import pl.edu.agh.to2.yadc.render.Camera;
import pl.edu.agh.to2.yadc.render.Renderable;

public abstract class Entity implements Renderable, Advanceable {

	protected BufferedImage texture;
	protected double xPos;
	protected double yPos;
	protected Boolean collidable;
	protected double collisionRadius;
	protected double angularRotation;
	protected Area area;
	
	public List<Effect> activeEffects;
	public List<Action> spreadingActions;
	private Map<Entity, Integer> collisionCache;

	public Entity(double xInit, double yInit, double collisionRadius) {
		this.xPos = xInit;
		this.yPos = yInit;
		this.collidable = true;
		this.collisionRadius = collisionRadius;
		this.angularRotation = 0.0;
		
		this.activeEffects = new LinkedList<>();
		this.spreadingActions = new LinkedList<>();
		this.collisionCache = new HashMap<>();
	}

	abstract public void advanceSelf(double delta);

	public final void renderSelf(Graphics graphics, Camera currentCamera) {
		checkCollisions();
		updateEffects();
		int width = GlobalConfig.get().getTargetWidth();
		int height = GlobalConfig.get().getTargetHeight();
		int xApparent = (int) xPos - currentCamera.getXPos() + width / 2;
		int yApparent = (int) yPos - currentCamera.getYPos() + height / 2;
		graphics.drawImage(this.texture, xApparent - texture.getWidth() / 2, yApparent - texture.getHeight() / 2,
				texture.getWidth(), texture.getHeight(), null);

	}

	public final void checkCollisions() {
		for (Entity cachedEntity : collisionCache.keySet()){
			collisionCache.put(cachedEntity, 0);
		}
		List<Entity> collidedEntities = CollisionEngine.getCollisions(this, this.area);
		for (Entity ent : collidedEntities) {
			if(ent instanceof MeleeMob) {
				this.performCollisionAction(ent);
			}
			else if (!collisionCache.containsKey(ent)) {
				this.performCollisionAction(ent);
			}
			collisionCache.put(ent,  1);
		}
		collisionCache.keySet().removeIf(ent -> collisionCache.get(ent) == 0);
	}
	
	public final void updateEffects() {
		for (Effect effect : activeEffects) {
			effect.updateEffect(this); 
		}
		activeEffects.removeIf(eff -> eff.isFinished());
	}

	public void performCollisionAction(Entity entity) {
		for (Action effect : entity.spreadingActions) {
			effect.activate(this);
		}
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public double getXPos() {
		return xPos;
	}

	public double getYPos() {
		return yPos;
	}

	public double getCollisionRadius() {
		return collisionRadius;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	public Map<Entity, Integer> getCollisionCache() {
		return collisionCache;
	}

}