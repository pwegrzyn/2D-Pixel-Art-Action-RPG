package renderTests;

import static org.junit.Assert.*;

import org.junit.Test;

import pl.edu.agh.to2.yadc.render.Camera;

public class CameraTests {

	@Test
	public void moveToTestX() {
		Camera camera = new Camera(0, 0);
		camera.moveTo(10, 0);
		assertEquals("Camera move X", 10, camera.getXPos());
	}
	
	@Test
	public void moveToTestY() {
		Camera camera = new Camera(0, 0);
		camera.moveTo(0, 10);
		assertEquals("Camera move Y", 10, camera.getYPos());
	}

}
