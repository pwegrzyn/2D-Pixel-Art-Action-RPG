package pl.edu.agh.to2.yadc.config;

public class Configuration {

	private int targetHeight;
	private int targetWidth;
	private int targetFps;
	private int targetFrameTime;
	
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

}
