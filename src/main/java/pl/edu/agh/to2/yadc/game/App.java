package pl.edu.agh.to2.yadc.game;

import pl.edu.agh.to2.yadc.config.Configuration;
import pl.edu.agh.to2.yadc.render.RenderManager;

public class App {

	public static void main(String[] args) {
		
		Configuration cfg = new Configuration();
		cfg.setTargetHeight(312);
		cfg.setTargetWidth(500);
		cfg.setTargetFps(100);
		
		RenderManager.initialSetup(cfg);
		RenderManager.startRendering();
		
	}

	public static void quit() {
		System.out.println("Exiting...");
		System.exit(0);
	}

}
