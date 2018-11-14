package pl.edu.agh.to2.yadc.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import pl.edu.agh.to2.yadc.render.RenderManager;

public class InputManager {

    private static boolean upPressed;
    private static boolean downPressed;
    private static boolean leftPressed;
    private static boolean rightPressed;
    
    private static KeyListener keyListener = new KeyListener() {
    
        Map<String, Character> keybinds = RenderManager.getCurrentConfiguration().getKeyBinds();
        
        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyChar() == keybinds.get("down")) downPressed = false;
			if (e.getKeyChar() == keybinds.get("up")) upPressed = false;
			if (e.getKeyChar() == keybinds.get("left")) leftPressed = false;
			if (e.getKeyChar() == keybinds.get("right")) rightPressed = false;
        }
    
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyChar() == keybinds.get("down")) downPressed = true;
			if (e.getKeyChar() == keybinds.get("up")) upPressed = true;
			if (e.getKeyChar() == keybinds.get("left")) leftPressed = true;
			if (e.getKeyChar() == keybinds.get("right")) rightPressed = true;
        }

        @Override
        public void keyTyped(KeyEvent e) {
            // ?
        }

    };
    

    public static boolean upPressed() {
		return upPressed;
    }

    public static boolean downPressed() {
		return downPressed;
    }
    
    public static boolean leftPressed() {
		return leftPressed;
    }
    
    public static boolean rightPressed() {
		return rightPressed;
	}

    public static KeyListener getKeyListener() {
        return keyListener;
    }

}