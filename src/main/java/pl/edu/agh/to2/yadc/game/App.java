package pl.edu.agh.to2.yadc.game;

import pl.edu.agh.to2.yadc.config.Configuration;
import pl.edu.agh.to2.yadc.config.GlobalConfig;
import pl.edu.agh.to2.yadc.area.AreaManager;
import pl.edu.agh.to2.yadc.hud.HUD;
import pl.edu.agh.to2.yadc.input.InputManager;
import pl.edu.agh.to2.yadc.input.KeybindSet;
import pl.edu.agh.to2.yadc.physics.CollisionEngine;
import pl.edu.agh.to2.yadc.render.ImageLoader;
import pl.edu.agh.to2.yadc.render.RenderManager;


public class App {

	public static void main(String[] args) {
		
		Configuration cfg = new Configuration();
		cfg.setTargetHeight(312);
		cfg.setTargetWidth(500);
		cfg.setTargetFps(9001);
		cfg.setKeyBinds(new KeybindSet());
		GlobalConfig.setGlobalConfig(cfg);

		RenderManager renderManager = new RenderManager();
		InputManager inputManager = new InputManager();
		AreaManager areaManager = new AreaManager();
		HUD hud = new HUD();
		renderManager.setInputManager(inputManager);
		hud.setInputManager(inputManager);
		renderManager.initialSetup();
		renderManager.setGameplayHUD(hud);
		ImageLoader imageLoader = new ImageLoader(renderManager.getMainCanvas());

		GlobalConfig.get().setHUD(hud);

		CollisionEngine.setStrategy("circle");

		GameSessionManager.init(inputManager, imageLoader, hud, areaManager, renderManager);

		GameSessionManager.newGameSession();
		
	}

	public static void quit() {
		System.exit(0);
	}

}
