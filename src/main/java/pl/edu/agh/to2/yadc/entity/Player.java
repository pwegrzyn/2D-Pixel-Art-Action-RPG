package pl.edu.agh.to2.yadc.entity;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.to2.yadc.config.GlobalConfig;
import pl.edu.agh.to2.yadc.game.GameSessionManager;
import pl.edu.agh.to2.yadc.input.InputManager;
import pl.edu.agh.to2.yadc.item.Equipment;
import pl.edu.agh.to2.yadc.item.HealthPotion;
import pl.edu.agh.to2.yadc.item.Item;
import pl.edu.agh.to2.yadc.item.ManaPotion;
import pl.edu.agh.to2.yadc.physics.Vector;
import pl.edu.agh.to2.yadc.quest.Quest;
import pl.edu.agh.to2.yadc.quest.QuestBoard;
import pl.edu.agh.to2.yadc.quest.QuestLog;
import pl.edu.agh.to2.yadc.quest.SlayQuest;


public class Player extends Entity {

	private InputManager inputManager;
	private int attackCooldown = 0;
    private long lastAttackTime = 0;
    private int manaRegenCooldown = 100;
    private long lastManaRegenTime = 0;
	private static StatManager statManager;
	private static QuestLog questLog;
	private static Equipment equipment;
	private int score;
	private boolean godmode;
	private BufferedImage graveTexture;
	private ProjectileTypes activeProjectile = ProjectileTypes.NORMAL;
	private double ProjectileSwitchCooldown = 0.5;
	private double projectileSwitchTimer = 0;
	private double consumableUseCooldown = 0.5;
	private double consumableUseTimer = 0;
	private double speedChangeTimer = 0;
	private double speedChangeDuration = 0;
	private double speedMultiplier = 1.0;
	
    public Player(double xInit, double yInit) {
        super(xInit, yInit, 10);
		equipment = new Equipment(this);

		statManager = new StatManager(equipment, 0, 0, 0, 0, 0, 0);
		statManager.setRange(20);
		statManager.setBaseHealth(1000);
		statManager.setHealth(1000);
		statManager.setBaseMana(500);
		statManager.setMana(500);
		statManager.setPhysicalDmg(100);
		statManager.setMagicDmg(20);
		questLog = new QuestLog();
		availableQuests = new LinkedList<>();
		this.score = 0;

		for(int i = 0; i < 3; i++) {
			equipment.addToBackpack(new HealthPotion());
			equipment.addToBackpack(new ManaPotion());
		}
		
		this.godmode = false;
    }

    private boolean up = false;
    private boolean down = false;
    private boolean right = false;
    private boolean left = false;
    private Vector moveVector = new Vector(0, 0);
	private boolean performingAttack;
	private static List<Quest> availableQuests;
	private static QuestBoard questBoard;
	private int hppot_amount = 3;
	private int manapot_amount = 3;

    @Override
    public void advanceSelf(double delta) {
		
		reactToUserInput(delta);

		speedChangeHandler(delta);

		// check if not walked out of the area
		if(this.xPos >= area.getXSize()) {
			this.xPos = area.getXSize();
		}
		if(this.yPos >= area.getYSize()) {
			this.yPos = area.getYSize();
		}
		if(this.xPos < 0) {
			this.xPos = 0;
		}
		if(this.yPos < 0) {
			this.yPos = 0;
		}
    
		if(this.getStatManager().getCurrentMana() < this.getStatManager().getMaxMana()) {
			if (this.lastManaRegenTime == 0 || this.lastManaRegenTime + this.manaRegenCooldown < System.currentTimeMillis()) {
				this.getStatManager().setMana(this.getStatManager().getCurrentMana()+1);
				this.lastManaRegenTime = System.currentTimeMillis();
			}
		}
		
        if (performingAttack) {
        	if (this.lastAttackTime == 0 || this.lastAttackTime + this.attackCooldown < System.currentTimeMillis()) {
        		Projectile bullet = ProjectileFactory.createProjectile(activeProjectile, this, 4);
		    	this.lastAttackTime = System.currentTimeMillis();
		    	this.attackCooldown = 100;
				if(activeProjectile!=ProjectileTypes.TRIPLE || !(bullet instanceof MultipleProjectile)) this.area.addEntity(bullet);
				else {
					for(Projectile p: ((MultipleProjectile)bullet).getProjectiles()) {
						this.area.addEntity(p);
					}
				}
			}
			performingAttack = false;
		}

		// Player avatar death
		if (statManager.getCurrentHealth() <= 0 && !godmode) {
			setTexture(this.graveTexture);
			GlobalConfig.get().printToChatBox("Oh, snap! You died.");
			GlobalConfig.get().printToChatBox("Click NEWGAME to start a new game");
			GameSessionManager.stopCurrentSession();
			GlobalConfig.get().setFrozenRender(true);
		}
	}
	
	private void reactToUserInput(double delta) {

		boolean isInputDisabled = inputManager.isNonChatInputDisabled();
		
		if (inputManager.getPressedByName("up") && !isInputDisabled) {
	    	this.yPos -= statManager.getSpeed() * this.speedMultiplier * delta;
	    }
	    if (inputManager.getPressedByName("down") && !isInputDisabled) {
	    	this.yPos += statManager.getSpeed() * this.speedMultiplier * delta;
	    }
	    if (inputManager.getPressedByName("left") && !isInputDisabled) {
	    	this.xPos -= statManager.getSpeed() * this.speedMultiplier * delta;
	    } 
	    if (inputManager.getPressedByName("right") && !isInputDisabled) {
    	    this.xPos += statManager.getSpeed() * this.speedMultiplier * delta;  
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

		consumableUseTimer += delta;
		if(consumableUseTimer > consumableUseCooldown) {
			int res;
			if (inputManager.getPressedByName("useConsumable_1") && !isInputDisabled) {
				
				if((res = equipment.getBackpack().useHealthPotion(this)) != -1) {
					GlobalConfig.get().printToChatBox(res + " health potions left");
					hppot_amount = res;
				} else {
					GlobalConfig.get().printToChatBox("You dont have any pots left");
					hppot_amount = 0;
				}
				consumableUseTimer = 0;
			}
			if (inputManager.getPressedByName("useConsumable_2") && !isInputDisabled) {
	
				if ((res = equipment.getBackpack().useManaPotion(this)) != -1) {
					GlobalConfig.get().printToChatBox(res + " mana potions left");
					manapot_amount = res;
				} else {
					GlobalConfig.get().printToChatBox("You dont have any pots left");
					manapot_amount = 0;
				}
				consumableUseTimer = 0;
			}
		}

		projectileSwitchTimer += delta;
	    if(projectileSwitchTimer > ProjectileSwitchCooldown) {
		    if (inputManager.getPressedByName("slowingProjectile") && !isInputDisabled) {
		    	if(activeProjectile != ProjectileTypes.SLOWING) this.activeProjectile = ProjectileTypes.SLOWING;
				else this.activeProjectile = ProjectileTypes.NORMAL;
				projectileSwitchTimer = 0;
		    }
		    else if (inputManager.getPressedByName("stunningProjectile") && !isInputDisabled) {
		    	if(activeProjectile != ProjectileTypes.STUNNING) this.activeProjectile = ProjectileTypes.STUNNING;
				else this.activeProjectile = ProjectileTypes.NORMAL;
				projectileSwitchTimer = 0;
		    }
		    else if (inputManager.getPressedByName("tripleProjectile") && !isInputDisabled) {
		    	if(activeProjectile != ProjectileTypes.TRIPLE) this.activeProjectile = ProjectileTypes.TRIPLE;
				else this.activeProjectile = ProjectileTypes.NORMAL;
				projectileSwitchTimer = 0;
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

	public static StatManager getStatManager() {
		return statManager;
	}

	public QuestLog getQuestLog() {
		return this.questLog;
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
			mobType = "Marauder";
		} else if(ent instanceof RangedMob) {
			mobType = "Ranger";
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
						this.statManager.addExp(slayQuest.getExpReward());
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

	public static Equipment getEquipment() {
		return equipment;
	}

	public static void showBackpack() {
		GlobalConfig.get().printToChatBox("Backpack:");
		if(equipment.getBackpack().getItems().size() == 0) {
			GlobalConfig.get().printToChatBox("<empty>");
		}
		for(Item item : equipment.getBackpack().getItems()) {
			GlobalConfig.get().printToChatBox(" - " + item.getDescription() + " (id: " + item.getId() + ")");
		}
		GlobalConfig.get().printToChatBox("Capacity: " + equipment.getBackpack().getUsedSpace() + " / " + equipment.getBackpack().getBaseCapacity());
	}

	public static void showGold() {
		GlobalConfig.get().printToChatBox("Current Gold: " + equipment.getCurrentGold());
	}

	public BufferedImage getGraveTexture() {
		return this.graveTexture;
	}

	public void setGraveTexture(BufferedImage texture) {
		this.graveTexture = texture;
	}

	public ProjectileTypes getActiveProjecile() {
		return this.activeProjectile;
	}
	
	public void setActiveProjecile(ProjectileTypes type) {
		this.activeProjectile = type;
	}

	public int getHPPot() {
		return this.hppot_amount;
	}

	public int getManaPot() {
		return this.manapot_amount;
	}

	public void setHPPot(int val) {
		this.hppot_amount = val;
	}

	public void setManaPot(int val) {
		this.manapot_amount = val;
	}

	public void godmode(boolean val) {
		this.godmode = val;
	}

	private void speedChangeHandler(double delta) {
		speedChangeTimer += delta;
		if(this.speedChangeTimer > this.speedChangeDuration) {
			this.speedChangeTimer = 0;
			this.speedMultiplier = 1.0;
			this.speedChangeDuration = 0.0;
			return;
		}
	}

	public void changeSpeedTemporarily(double time, double multiplier) {
		this.speedChangeDuration = time;
		this.speedMultiplier = multiplier;
	}
}