package pl.edu.agh.to2.yadc.game;

import java.util.Random;

import pl.edu.agh.to2.yadc.area.Area;
import pl.edu.agh.to2.yadc.area.AreaManager;
import pl.edu.agh.to2.yadc.config.GlobalConfig;
import pl.edu.agh.to2.yadc.entity.HealthPowerUp;
import pl.edu.agh.to2.yadc.entity.MeleeMob;
import pl.edu.agh.to2.yadc.entity.MobFactory;
import pl.edu.agh.to2.yadc.entity.Player;
import pl.edu.agh.to2.yadc.entity.PowerUp;
import pl.edu.agh.to2.yadc.entity.RangedMob;
import pl.edu.agh.to2.yadc.entity.ScorePowerUp;
import pl.edu.agh.to2.yadc.hud.HUD;
import pl.edu.agh.to2.yadc.input.InputManager;
import pl.edu.agh.to2.yadc.quest.QuestBoard;
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
                player.setTexture(imageLoaderComp.fetchImage("resources/avatar.png"));
                player.setProjectileTexture(imageLoaderComp.fetchImage("resources/minibullet.png"));
                hudComp.bindPlayer(player);

                Area area = new Area("Knowhere");
                area.setTexture(imageLoaderComp.fetchImage("resources/grass_area.png"));
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
                questBoard.setTexture(imageLoaderComp.fetchImage("resources/questboard.png"));
                area.addEntity(questBoard);

                for (;;) {

                    // Randomly spawn a melee mob
                    int randomLocX = random.nextInt(1400 + 1 - 100) + 100;
                    int randomLocY = random.nextInt(600 + 1 - 100) + 100;
                    RangedMob mob = (RangedMob) factory.createRangedMob(randomLocX, randomLocY, 10,
                            imageLoaderComp.fetchImage("resources/ranged_enemy.png"),
                            imageLoaderComp.fetchImage("resources/arrow.png"));
                    area.addEntity(mob);

                    // Randomly spawn a ranged mob
                    randomLocX = random.nextInt(1400 + 1 - 100) + 100;
                    randomLocY = random.nextInt(600 + 1 - 100) + 100;
                    MeleeMob mob2 = (MeleeMob) factory.createMeleeMob(randomLocX, randomLocY, 10,
                            imageLoaderComp.fetchImage("resources/melee_enemy.png"));
                    area.addEntity(mob2);

                    // Randomly spawn a power-up
                    randomLocX = random.nextInt(1400 + 1 - 100) + 100;
                    randomLocY = random.nextInt(600 + 1 - 100) + 100;
                    PowerUp powerUp = null;
                    switch(random.nextInt(2)) {
                        case 0:
                            powerUp = new HealthPowerUp(randomLocX, randomLocY, 100);
                            powerUp.setTexture(imageLoaderComp.fetchImage("resources/heart.png"));
                        break;
                        case 1:
                            powerUp = new ScorePowerUp(randomLocX, randomLocY, 5000);
                            powerUp.setTexture(imageLoaderComp.fetchImage("resources/star.png"));
                        break;
                        default: break;
                    }
                    area.addEntity(powerUp);
                    
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
    
    public static void init(InputManager inputManager, ImageLoader imageLoader, HUD hud, AreaManager areaManager, RenderManager renderManager) {
        inputManagerComp = inputManager;
        imageLoaderComp = imageLoader;
        hudComp = hud;
        areaManagerComp = areaManager;
        renderManagerComp = renderManager;
    }

}