package physicsTests;

import static org.junit.Assert.*;

import org.junit.Test;

import pl.edu.agh.to2.yadc.physics.Vector;

public class VectorTests {
	
	@Test
	public void getAngleTestDefault() {
		Vector vector = new Vector(0,0);
		assertEquals(123, vector.getAngle(123), 0.0);
	}
	
	@Test
	public void getAngleTestSimple() {
		Vector vector = new Vector(1, 1);
		assertEquals(Math.PI/4, vector.getAngle(0), 1e-16);
	}
	
	
	@Test
	public void addAndUpdateTestDefault() {
		Vector vector = new Vector(0,0);
		assertEquals(123, vector.addAndUpdate(0, 0, 123), 0.0);
	}
	
	@Test
	public void addAndUpdateTestSimple() {
		Vector vector = new Vector(-1, 0);
		assertEquals(Math.PI/2, vector.addAndUpdate(1, 1, 0), 1e-16);
	}

}
