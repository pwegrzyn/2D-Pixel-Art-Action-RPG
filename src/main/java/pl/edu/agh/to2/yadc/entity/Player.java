package pl.edu.agh.to2.yadc.entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import pl.edu.agh.to2.yadc.config.GlobalConfig;
import pl.edu.agh.to2.yadc.game.GameSessionManager;
import pl.edu.agh.to2.yadc.input.InputManager;
import pl.edu.agh.to2.yadc.item.Equipment;
import pl.edu.agh.to2.yadc.item.Item;
import pl.edu.agh.to2.yadc.physics.Vector;
import pl.edu.agh.to2.yadc.quest.Quest;
import pl.edu.agh.to2.yadc.quest.QuestBoard;
import pl.edu.agh.to2.yadc.quest.QuestLog;
import pl.edu.agh.to2.yadc.quest.SlayQuest;


public class Player extends Entity {

    private int velocity;
	private InputManager inputManager;
	private int attackCooldown = 0;
    private long lastAttackTime = 0;
	private StatManager statManager;
	private static QuestLog questLog;
	private static Equipment equipment;
	private int score;
	
    public Player(double xInit, double yInit) {
        super(xInit, yInit, 10);
		this.velocity = 150;
		this.statManager = new StatManager(0, 0, 0, 0, 0, 0);
		this.statManager.setRange(20);
		this.statManager.setBaseHealth(1000);
		this.statManager.setHealth(1000);
		this.statManager.setBaseMana(500);
		this.statManager.setMana(500);
		questLog = new QuestLog();
		availableQuests = new LinkedList<>();
		equipment = new Equipment();
		this.score = 0;
    }

    private boolean up = false;
    private boolean down = false;
    private boolean right = false;
    private boolean left = false;
    private Vector moveVector = new Vector(0, 0);
	private BufferedImage projectileTexture;
	private boolean performingAttack;
	private static List<Quest> availableQuests;
	private static QuestBoard questBoard;

    @Override
    public void advanceSelf(double delta) {

		reactToUserInput(delta);

		// check if not walked out of the area
		if(this.xPos >= area.getXSize()) {
			this.xPos = area.getXSize() - 0;
		}
		if(this.yPos >= area.getYSize()) {
			this.yPos = area.getYSize() - 0;
		}
		if(this.xPos < 0) {
			this.xPos = 0;
		}
		if(this.yPos < 0) {
			this.yPos = 0;
		}
    
        if (performingAttack) {
        	if (this.lastAttackTime == 0 || this.lastAttackTime + this.attackCooldown < System.currentTimeMillis()) {
		    	Projectile bullet = ProjectileFactory.createNormalArrow(this, 4, projectileTexture);
		    	this.lastAttackTime = System.currentTimeMillis();
		    	this.attackCooldown = 100;
				bullet.setTexture(this.projectileTexture);
				this.area.addEntity(bullet);
			}
			performingAttack = false;
		}

		if (statManager.getCurrentHealth() <= 0) {
			try {
				setTexture(ImageIO.read(new File("resources/grave.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			GlobalConfig.get().printToChatBox("Oh, snap! You died with a score of: " + this.score);
			GlobalConfig.get().printToChatBox("Click NEW GAME in the menu bar to start a new game.");
			GameSessionManager.stopCurrentSession();
			GlobalConfig.get().setFrozenRender(true);
		}
	}
	
	private void reactToUserInput(double delta) {

		boolean isInputDisabled = inputManager.isNonChatInputDisabled();
		
		if (inputManager.getPressedByName("up") && !isInputDisabled) {
	    	this.yPos -= this.velocity * delta;
	    }
	    if (inputManager.getPressedByName("down") && !isInputDisabled) {
	    	this.yPos += this.velocity * delta;
	    }
	    if (inputManager.getPressedByName("left") && !isInputDisabled) {
	    	this.xPos -= this.velocity * delta;
	    } 
	    if (inputManager.getPressedByName("right") && !isInputDisabled) {
    	    this.xPos += this.velocity * delta;  
	    }
		
		if (inputManager.getPressedByName("lookUp") && !isInputDisabled) {
	    	if (!up) {
		        this.angularRotation = moveVector.addAndUpdate(0,  -1, this.angularRotation);
				up = true;
				performingAttack = true;
	    	}
	    }
	    else {
	    	if (up) {
	    		up = false;
	    		this.angularRotation = moveVector.addAndUpdate(0,  1, this.angularRotation);
	    	}
	    }
	
	    if (inputManager.getPressedByName("lookDown") && !isInputDisabled) {
	    	if (!down) {
		    	down = true;
				this.angularRotation = moveVector.addAndUpdate(0,  1, this.angularRotation);
				performingAttack = true;
	    	}
	    }
	    else {
	    	if (down) {
	    		down = false;
	    		this.angularRotation = moveVector.addAndUpdate(0,  -1, this.angularRotation);
	    	}
	    }
	
	    if (inputManager.getPressedByName("lookLeft") && !isInputDisabled) {
	    	if (!left) {
		        left = true;
				this.angularRotation = moveVector.addAndUpdate(-1,  0, this.angularRotation);
				performingAttack = true;
	    	}
	    } 
	    else {
	    	if (left) {
	    		left = false;
	    		this.angularRotation = moveVector.addAndUpdate(1,  0, this.angularRotation);
	    	}
	    }
	
	    if (inputManager.getPressedByName("lookRight") && !isInputDisabled) { 
    	    if (!right) {
		        right = true;		    
				this.angularRotation = moveVector.addAndUpdate(1,  0, this.angularRotation);
				performingAttack = true;
	    	}
	    }
	    else {
	    	if (right) {
	    		right = false;
	    		this.angularRotation = moveVector.addAndUpdate(-1,  0, this.angularRotation);
	    	}
		}
		
	}

	
	@Override
	public void performCollisionAction(Entity entity) {
		if(entity instanceof Projectile) {
			if(((Projectile)entity).getOwner() == this) {
				return;
			}
		}
		else if(entity instanceof MeleeMob) {
			List<Action> copy = new LinkedList<Action>(entity.spreadingActions);
			for (Action effect : copy) {
				effect.activate(this);
				if(entity.spreadingActions.get(0)!=effect) entity.spreadingActions.remove(effect);
			}
		}
		else if(entity instanceof QuestBoard) {
			List<Quest> availableQuestsRef = ((QuestBoard) entity).getAvailableQuests();
			questBoard = ((QuestBoard) entity);
			availableQuests.clear();
			for(Quest quest : availableQuestsRef) {
				availableQuests.add(quest);
			}
		}
		super.performCollisionAction(entity);
		// Kek

		double currentDistance = Math.sqrt(Math.pow(Math.abs(entity.getXPos() - this.getXPos()), 2) 
			+ Math.pow(Math.abs(entity.getYPos() - this.getYPos()), 2));
		this.yPos = entity.getYPos() + (this.getYPos() > entity.getYPos() ? 1 : -1) * Math.abs(this.getYPos() 
			- entity.getYPos())/currentDistance * (this.collisionRadius + entity.collisionRadius);
		this.xPos = entity.getXPos() + (this.getXPos() > entity.getXPos() ? 1 : -1) * Math.abs(this.getXPos() 
			- entity.getXPos())/currentDistance * (this.collisionRadius + entity.collisionRadius);
		
	}


	public void setInputManager(InputManager input) {
		this.inputManager = input;
	}

	public void setProjectileTexture(BufferedImage fetchImage) {
		this.projectileTexture = fetchImage;
	}

	public StatManager getStatManager() {
		return this.statManager;
	}

	public QuestLog getQuestLog() {
		return this.questLog;
	}
	
	public void addExp(int exp) {
		int currentExp = this.statManager.getCurrentExp();
		int expToNextLvl = this.statManager.getExpToNextLvl();
		GlobalConfig.get().printToChatBox("Received " + exp + " xp.");
		if (currentExp + exp >= expToNextLvl) {
			this.statManager.setLvl(this.statManager.getLvl() + 1);
			GlobalConfig.get().printToChatBox("Congratulations! You have gained a new level.");
			GlobalConfig.get().printToChatBox("You are now level " + this.statManager.getLvl() + ".");
			this.statManager.setExpToNextLvl(this.statManager.getExpToNextLvl() * 2);
			this.statManager.setExp(currentExp + exp - expToNextLvl);
		}
		else {
			this.statManager.setExp(this.statManager.getExp() + exp);
		}
	}

	public void addScore(int score) {
		this.score += score;
	}

	public int getScore() {
		return this.score;
	}

	public static boolean acceptNewQuest(int index) {
		if(availableQuests == null) return false;
		if(availableQuests.size() <= index) return false;
		Quest newQuest = availableQuests.get(index);
		if(questLog.addQuest(newQuest)) {
			newQuest.accept();
			newQuest.setQuestLog(questLog);
			questBoard.remove(newQuest);
			return true;
		}
		return false;
	}

	public void checkQuestsProgress(Entity ent) {
		String mobType;
		if(ent instanceof MeleeMob) {
			mobType = "Knight";
		} else if(ent instanceof RangedMob) {
			mobType = "Archer";
		} else {
			mobType = "";
		}
		List<Quest> toComplete = new LinkedList<>();
		for(Quest quest : questLog.getActiveQuests()) {
			if(quest instanceof SlayQuest) {
				SlayQuest slayQuest = (SlayQuest) quest;
				if(mobType.equals(slayQuest.getMonsterType()) || slayQuest.getMonsterType().equals("Any Mob Type")) {
					if(slayQuest.progress()) {
						toComplete.add(slayQuest);
						addExp(slayQuest.getExpReward());
						addScore(slayQuest.getScoreReward());
						GlobalConfig.get().printToChatBox("Quest " + slayQuest.getName() + " finished.");
					}
				}
			}
		}
		// To avoid concurrent modification
		for(Quest quest : toComplete) {
			questLog.getActiveQuests().remove(quest);
		}
	}

	public Equipment getEquipment() {
		return this.equipment;
	}

	public static void showBackpack() {
		GlobalConfig.get().printToChatBox("Backpack:");
		if(equipment.getBackpack().getItems().size() == 0) {
			GlobalConfig.get().printToChatBox("<empty>");
		}
		for(Item item : equipment.getBackpack().getItems()) {
			GlobalConfig.get().printToChatBox(" - " + item.getDescription() + " (id: " + item.getId() + ")");
		}
	}

	public static void showGold() {
		GlobalConfig.get().printToChatBox("Current Gold: " + equipment.getCurrentGold());
	}
}