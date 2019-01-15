package pl.edu.agh.to2.yadc.game;

import java.awt.image.BufferedImage;
import java.util.Random;

import pl.edu.agh.to2.yadc.area.Area;
import pl.edu.agh.to2.yadc.area.AreaManager;
import pl.edu.agh.to2.yadc.config.GlobalConfig;
import pl.edu.agh.to2.yadc.entity.Chest;
import pl.edu.agh.to2.yadc.entity.HealthPowerUp;
import pl.edu.agh.to2.yadc.entity.ManaPowerUp;
import pl.edu.agh.to2.yadc.entity.MeleeMob;
import pl.edu.agh.to2.yadc.entity.MobFactory;
import pl.edu.agh.to2.yadc.entity.Player;
import pl.edu.agh.to2.yadc.entity.PowerUp;
import pl.edu.agh.to2.yadc.entity.ProjectileFactory;
import pl.edu.agh.to2.yadc.entity.RangedMob;
import pl.edu.agh.to2.yadc.entity.ScorePowerUp;
import pl.edu.agh.to2.yadc.entity.SpeedPowerUp;
import pl.edu.agh.to2.yadc.entity.Vendor;
import pl.edu.agh.to2.yadc.hud.HUD;
import pl.edu.agh.to2.yadc.input.InputManager;
import pl.edu.agh.to2.yadc.quest.QuestBoard;
import pl.edu.agh.to2.yadc.render.Animation;
import pl.edu.agh.to2.yadc.render.AnimationType;
import pl.edu.agh.to2.yadc.render.ImageLoader;
import pl.edu.agh.to2.yadc.render.RenderManager;

public class GameSessionManager {

    private static InputManager inputManagerComp;
    private static ImageLoader imageLoaderComp;
    private static HUD hudComp;
    private static AreaManager areaManagerComp;
    private static RenderManager renderManagerComp;
    private static boolean firstGameRun = true;
    private static Thread runningThread;
    private static boolean isThreadActive = false;
    private static final int performanceMaxEntity = 250;

    public static void stopCurrentSession() {
        if(!isThreadActive) return;
        runningThread.stop();
        runningThread = null;
        isThreadActive = false;
    }

    public static void newGameSession() {

        runningThread = new Thread() {

            @Override
            public void run() {
                Player player = new Player(700, 330);
                player.setInputManager(inputManagerComp);
                hudComp.bindPlayer(player);
                ProjectileFactory.setNormalProjectileTexture(ImageLoader.active.fetchImage("resources/minibullet.png"));
                ProjectileFactory.setSlowingProjectileTexture(ImageLoader.active.fetchImage("resources/minibullet_slow.png"));
                ProjectileFactory.setStunningProjectileTexture(ImageLoader.active.fetchImage("resources/minibullet_stun.png"));
                ProjectileFactory.setMobProjectileTexture(ImageLoader.active.fetchImage("resources/minibullet.png"));

                Area area = new Area("Knowhere");
                area.setTexture(imageLoaderComp.fetchImage("resources/grass_land.png"));
                area.setSize(1475, 663);
                area.addEntity(player);

                player.setArea(area);

                areaManagerComp.setCurrentArea(area);

                if(firstGameRun) {
                    renderManagerComp.startRendering(areaManagerComp);
                    firstGameRun = false;
                } else {
                    GlobalConfig.get().setFrozenRender(false);
                }
            
                MobFactory factory = new MobFactory();
            
                hudComp.printToChatBox("Started a new game session.");
            
                Random random = new Random();

                QuestBoard questBoard = new QuestBoard(600, 380);
                area.addEntity(questBoard);

                Vendor vendor = new Vendor(500, 380);
                Animation animation = new Animation(AnimationType.IDLE, new BufferedImage[] {
                    ImageLoader.active.fetchImage("resources/elf_m_idle_anim_f0.png"),
                    ImageLoader.active.fetchImage("resources/elf_m_idle_anim_f1.png"),
                    ImageLoader.active.fetchImage("resources/elf_m_idle_anim_f2.png"),
                    ImageLoader.active.fetchImage("resources/elf_m_idle_anim_f3.png")

                }, 0.1);
                vendor.assignAnimation(animation);
                vendor.pickAnimation(AnimationType.IDLE);
                area.addEntity(vendor);

                for (;;) {

                    if(GlobalConfig.get().getFrozenGameSessionThread()) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    
                    // Randomly spawn a melee mob (possibly aggresive)
                    int randomLocX = random.nextInt(1400 + 1 - 100) + 100;
                    int randomLocY = random.nextInt(600 + 1 - 100) + 100;
                    RangedMob mob = (RangedMob) factory.createRangedMob(randomLocX, randomLocY);
                    if(random.nextInt(5) == 0) mob.setAggresive(true);
                    area.addEntity(mob);

                    // Randomly spawn a ranged mob (possibly aggresive)
                    randomLocX = random.nextInt(1400 + 1 - 100) + 100;
                    randomLocY = random.nextInt(600 + 1 - 100) + 100;
                    MeleeMob mob2 = (MeleeMob) factory.createMeleeMob(randomLocX, randomLocY);
                    if(random.nextInt(5) == 0) mob.setAggresive(true);
                    area.addEntity(mob2);

                    // Randomly spawn a boss monster (with a given spawn rate)
                    if(random.nextInt(8) == 0) {
                        randomLocX = random.nextInt(1400 + 1 - 100) + 100;
                        randomLocY = random.nextInt(600 + 1 - 100) + 100;
                        MeleeMob boss = (MeleeMob) factory.createMeleeBoss(randomLocX, randomLocY);
                        GlobalConfig.get().printToChatBox("A boss monster has spawned!");
                        area.addEntity(boss);
                    }
                    
                    // Randomly spawn a ranged boss monster (with a given spawn rate)
                    if(random.nextInt(8) == 0) {
                        randomLocX = random.nextInt(1400 + 1 - 100) + 100;
                        randomLocY = random.nextInt(600 + 1 - 100) + 100;
                        RangedMob boss = (RangedMob) factory.createRangedBoss(randomLocX, randomLocY);
                        GlobalConfig.get().printToChatBox("A ranged boss monster has spawned");
                        area.addEntity(boss);
                    }

                    // Randomly spawn a power-up with 1/2 chance per iteration
                    randomLocX = random.nextInt(1400 + 1 - 100) + 100;
                    randomLocY = random.nextInt(600 + 1 - 100) + 100;
                    PowerUp powerUp = null;
                    switch(random.nextInt(10)) {
                        case 0:
                            powerUp = new HealthPowerUp(randomLocX, randomLocY, 100);
                            area.addEntity(powerUp);
                        break;
                        case 1:
                            powerUp = new ScorePowerUp(randomLocX, randomLocY, 5000);
                            area.addEntity(powerUp);
                        break;
                        case 2:
                            powerUp = new ManaPowerUp(randomLocX, randomLocY, 100);
                            area.addEntity(powerUp);
                        break;
                        case 3:
                            powerUp = new SpeedPowerUp(randomLocX, randomLocY, 2.0, 5.0);
                            area.addEntity(powerUp);
                        break;
                        case 4:
                            Chest chest = new Chest(randomLocX, randomLocY, 5);
                            area.addEntity(chest);
                        break;
                        default: break;
                    }
                    
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        };

        isThreadActive = true;
        runningThread.start();
    }

    public static void runPerfTest() {

        runningThread = new Thread() {

            @Override
            public void run() {
                boolean continueSpawning = true;
                Player player = new Player(700, 330);
                player.setInputManager(inputManagerComp);
                hudComp.bindPlayer(player);
                ProjectileFactory.setNormalProjectileTexture(ImageLoader.active.fetchImage("resources/minibullet.png"));
                ProjectileFactory
                        .setSlowingProjectileTexture(ImageLoader.active.fetchImage("resources/minibullet_slow.png"));
                ProjectileFactory
                        .setStunningProjectileTexture(ImageLoader.active.fetchImage("resources/minibullet_stun.png"));
                ProjectileFactory.setMobProjectileTexture(ImageLoader.active.fetchImage("resources/minibullet.png"));

                Area area = new Area("Knowhere");
                area.setTexture(imageLoaderComp.fetchImage("resources/grass_land.png"));
                area.setSize(1475, 663);
                area.addEntity(player);

                player.setArea(area);

                areaManagerComp.setCurrentArea(area);

                if (firstGameRun) {
                    renderManagerComp.startRendering(areaManagerComp);
                    firstGameRun = false;
                } else {
                    GlobalConfig.get().setFrozenRender(false);
                }

                MobFactory factory = new MobFactory();

                hudComp.printToChatBox("Started a new game session.");

                Random random = new Random();

                QuestBoard questBoard = new QuestBoard(600, 380);
                area.addEntity(questBoard);

                Vendor vendor = new Vendor(500, 380);
                Animation animation = new Animation(AnimationType.IDLE,
                        new BufferedImage[] { ImageLoader.active.fetchImage("resources/elf_m_idle_anim_f0.png"),
                                ImageLoader.active.fetchImage("resources/elf_m_idle_anim_f1.png"),
                                ImageLoader.active.fetchImage("resources/elf_m_idle_anim_f2.png"),
                                ImageLoader.active.fetchImage("resources/elf_m_idle_anim_f3.png")

                        }, 0.1);
                vendor.assignAnimation(animation);
                vendor.pickAnimation(AnimationType.IDLE);
                area.addEntity(vendor);

                player.godmode(true);
                System.out.println("Running performance test...");
                System.out.println("Simultaneously rendering " + performanceMaxEntity + " entites.");

                for (;;) {

                    if (area.getEntityRegister().getActiveEntities().size() > performanceMaxEntity) {
                        // fall asleep and say goodbye to your cpu [*]
                        continueSpawning = false;
                    } else {
                        continueSpawning = true;
                    }

                    if (!continueSpawning) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }

                    if (GlobalConfig.get().getFrozenGameSessionThread()) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }

                    int randomLocX = random.nextInt(1400 + 1 - 100) + 100;
                    int randomLocY = random.nextInt(600 + 1 - 100) + 100;
                    RangedMob mob = (RangedMob) factory.createRangedMob(randomLocX, randomLocY);
                    if (random.nextInt(5) == 0)
                        mob.setAggresive(true);
                    area.addEntity(mob);
                    randomLocX = random.nextInt(1400 + 1 - 100) + 100;
                    randomLocY = random.nextInt(600 + 1 - 100) + 100;
                    MeleeMob mob2 = (MeleeMob) factory.createMeleeMob(randomLocX, randomLocY);
                    if (random.nextInt(5) == 0)
                        mob.setAggresive(true);
                    area.addEntity(mob2);
                    randomLocX = random.nextInt(1400 + 1 - 100) + 100;
                    randomLocY = random.nextInt(600 + 1 - 100) + 100;
                    MeleeMob boss = (MeleeMob) factory.createMeleeBoss(randomLocX, randomLocY);
                    GlobalConfig.get().printToChatBox("A boss monster has spawned!");
                    area.addEntity(boss);
                    randomLocX = random.nextInt(1400 + 1 - 100) + 100;
                    randomLocY = random.nextInt(600 + 1 - 100) + 100;
                    RangedMob boss2 = (RangedMob) factory.createRangedBoss(randomLocX, randomLocY);
                    GlobalConfig.get().printToChatBox("A ranged boss monster has spawned");
                    area.addEntity(boss2);
                    randomLocX = random.nextInt(1400 + 1 - 100) + 100;
                    randomLocY = random.nextInt(600 + 1 - 100) + 100;
                    PowerUp powerUp = null;
                    switch (random.nextInt(10)) {
                    case 0:
                        powerUp = new HealthPowerUp(randomLocX, randomLocY, 100);
                        area.addEntity(powerUp);
                        break;
                    case 1:
                        powerUp = new ScorePowerUp(randomLocX, randomLocY, 5000);
                        area.addEntity(powerUp);
                        break;
                    case 2:
                        powerUp = new ManaPowerUp(randomLocX, randomLocY, 100);
                        area.addEntity(powerUp);
                        break;
                    case 3:
                        powerUp = new SpeedPowerUp(randomLocX, randomLocY, 2.0, 5.0);
                        area.addEntity(powerUp);
                        break;
                    case 4:
                        Chest chest = new Chest(randomLocX, randomLocY, 5);
                        area.addEntity(chest);
                        break;
                    default:
                        break;
                    }
                }
            }

        };

        isThreadActive = true;
        runningThread.start();
    }
    
    public static void init(InputManager inputManager, ImageLoader imageLoader, HUD hud, AreaManager areaManager, RenderManager renderManager) {
        inputManagerComp = inputManager;
        imageLoaderComp = imageLoader;
        hudComp = hud;
        areaManagerComp = areaManager;
        renderManagerComp = renderManager;
    }

}