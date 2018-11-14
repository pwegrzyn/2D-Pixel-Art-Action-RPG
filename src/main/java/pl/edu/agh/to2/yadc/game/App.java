package pl.edu.agh.to2.yadc.game;

import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.to2.yadc.config.Configuration;
import pl.edu.agh.to2.yadc.entity.Area;
import pl.edu.agh.to2.yadc.entity.AreaManager;
import pl.edu.agh.to2.yadc.entity.Player;
import pl.edu.agh.to2.yadc.render.RenderManager;


public class App {

	public static void main(String[] args) {
		
		Configuration cfg = new Configuration();
		cfg.setTargetHeight(312);
		cfg.setTargetWidth(500);
		cfg.setTargetFps(100);
		Map<String, Character> keyBinds = new HashMap<>();
		keyBinds.put("up", 'w');
		keyBinds.put("down", 's');
		keyBinds.put("left", 'a');
		keyBinds.put("right", 'd');
		cfg.setKeyBinds(keyBinds);
		
		RenderManager.initialSetup(cfg);

		Player player = new Player(50, 50);
		Area area = new Area("resources\\grass_area.png");
		area.addEntity(player);
		AreaManager.setCurrentArea(area);

		RenderManager.startRendering();
		
	}

	public static void quit() {
		System.out.println("Exiting...");
		System.exit(0);
	}

}
