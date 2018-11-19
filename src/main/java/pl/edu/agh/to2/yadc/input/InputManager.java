package pl.edu.agh.to2.yadc.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import pl.edu.agh.to2.yadc.config.GlobalConfig;

public class InputManager {

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    
    private KeyListener keyListener = new KeyListener() {
    
        Map<String, Character> keybinds = GlobalConfig.getGlobalConfig().getKeyBinds();
        
        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyChar() == keybinds.get("down"))
                downPressed = false;
            if (e.getKeyChar() == keybinds.get("up")) 
                upPressed = false;
            if (e.getKeyChar() == keybinds.get("left")) 
                leftPressed = false;
            if (e.getKeyChar() == keybinds.get("right")) 
                rightPressed = false;
        }
    
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyChar() == keybinds.get("down")) 
                downPressed = true;
            if (e.getKeyChar() == keybinds.get("up")) 
                upPressed = true;
            if (e.getKeyChar() == keybinds.get("left")) 
                leftPressed = true;
            if (e.getKeyChar() == keybinds.get("right")) 
                rightPressed = true;
        }

        @Override
        public void keyTyped(KeyEvent e) {
            // ?
        }

    };
    

    public boolean upPressed() {
		return upPressed;
    }

    public boolean downPressed() {
		return downPressed;
    }
    
    public boolean leftPressed() {
		return leftPressed;
    }
    
    public boolean rightPressed() {
		return rightPressed;
	}

    public KeyListener getKeyListener() {
        return keyListener;
    }

}