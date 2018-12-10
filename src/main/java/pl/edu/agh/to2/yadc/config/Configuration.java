package pl.edu.agh.to2.yadc.config;

import pl.edu.agh.to2.yadc.hud.HUD;
import pl.edu.agh.to2.yadc.input.KeybindSet;


public class Configuration {

	private int targetHeight;
	private int targetWidth;
	private int targetFps;
	private int targetFrameTime;
	private KeybindSet keyMapping;
	private HUD hud;
	private boolean UIvisibility = true;
	private boolean debug = false;
	
	public Configuration() {
		
		this.targetHeight = 312;
		this.targetWidth = 500;
		this.targetFps = 100;
		this.targetFrameTime = getFrameTimeFromFps(this.targetFps);
		
	}

	public void setTargetHeight(int height) {
		this.targetHeight = height;
	}
	
	public int getTargetHeight() {
		return this.targetHeight;
	}

	public void setTargetWidth(int width) {
		this.targetWidth = width;
	}
	
	public int getTargetWidth() {
		return this.targetWidth;
	}

	public void setTargetFps(int fps) {
		this.targetFps = fps;
		this.targetFrameTime = getFrameTimeFromFps(fps);
	}
	
	public int getTargetFps() {
		return this.targetFps;
	}
	
	public int getTargetFrameTime() {
		return this.targetFrameTime;
	}
	
	private int getFrameTimeFromFps(int fps) {
		return 1000000000 / fps;
	}

	public void setKeyBinds(KeybindSet keyBinds) {
		this.keyMapping = keyBinds;
	}

	public KeybindSet getKeyBinds() {
		return this.keyMapping;
	}

	public HUD getHUD() {
		return this.hud;
	}

	public void setHUD(HUD hud) {
		this.hud = hud;
	}

	public void printToChatBox(String str) {
		this.hud.printToChatBox(str);
	}

	public void setUIVisibility(boolean state) {
		this.UIvisibility = state;
	}

	public boolean getUIVisibility() {
		return this.UIvisibility;
	}

	public void setDebug(boolean state) {
		this.debug = state;
	}

	public boolean getDebug() {
		return this.debug;
	}

}
