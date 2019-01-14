package entityTests;

import static org.junit.Assert.*;
import org.junit.Test;

import pl.edu.agh.to2.yadc.entity.Effect;
import pl.edu.agh.to2.yadc.entity.Player;

public class EffectTests {

	@Test
	public void getDirectionBeforeFinishTest() {
		Effect effect = new Effect(1000, e -> {}, e -> {});
		assertEquals("Before finish", false, effect.isFinished());
	}
	
//	@Test
//	public void getDirectionAfterFinishTest() {
//		Effect effect = new Effect(0, e -> {}, e -> {});
//		effect.updateEffect(new Player(0,0));
//		assertEquals("After finish", true, effect.isFinished());
//	}

}
