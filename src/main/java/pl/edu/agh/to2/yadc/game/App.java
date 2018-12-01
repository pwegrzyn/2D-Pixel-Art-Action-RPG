package pl.edu.agh.to2.yadc.game;

import pl.edu.agh.to2.yadc.config.Configuration;
import pl.edu.agh.to2.yadc.config.GlobalConfig;

import java.util.Random;

import pl.edu.agh.to2.yadc.area.Area;
import pl.edu.agh.to2.yadc.area.AreaManager;
import pl.edu.agh.to2.yadc.entity.Player;
import pl.edu.agh.to2.yadc.entity.TestEnemy;
import pl.edu.agh.to2.yadc.input.InputManager;
import pl.edu.agh.to2.yadc.input.KeybindSet;
import pl.edu.agh.to2.yadc.render.ImageLoader;
import pl.edu.agh.to2.yadc.render.RenderManager;


public class App {

	public static void main(String[] args) {
		
		Configuration cfg = new Configuration();
		cfg.setTargetHeight(312);
		cfg.setTargetWidth(500);
		cfg.setTargetFps(100);
		cfg.setKeyBinds(new KeybindSet());
		GlobalConfig.setGlobalConfig(cfg);

		RenderManager renderManager = new RenderManager();
		InputManager inputManager = new InputManager();
		AreaManager areaManager = new AreaManager();
		renderManager.setInputManager(inputManager);
		renderManager.initialSetup();
		ImageLoader imageLoader = new ImageLoader(renderManager.getMainCanvas());

		Player player = new Player(100, 200);
		player.setInputManager(inputManager);
		player.setTexture(imageLoader.fetchImage("resources/test_entity.png"));
		player.setProjectileTexture(imageLoader.fetchImage("resources/bullet.png"));

		Area area = new Area("Knowhere");
		area.setTexture(imageLoader.fetchImage("resources/grass_area.png"));
		area.addEntity(player);
		
		player.setArea(area);

		areaManager.setCurrentArea(area);

		renderManager.startRendering(areaManager);

		Random random = new Random();
		for(;;) {
			int randomLoc = random.nextInt(500 + 1 - 100) + 100;
			TestEnemy mob = new TestEnemy(randomLoc, randomLoc, 10);
			mob.setTexture(imageLoader.fetchImage("resources/test_enemy.png"));
			mob.setProjectileTexture(imageLoader.fetchImage("resources/bullet.png"));
			area.addEntity(mob);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public static void quit() {
		System.out.println("Exiting...");
		System.exit(0);
	}

}
