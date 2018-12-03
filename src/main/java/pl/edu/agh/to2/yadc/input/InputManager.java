package pl.edu.agh.to2.yadc.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import pl.edu.agh.to2.yadc.config.GlobalConfig;

public class InputManager {

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean attackPressed;
    private boolean chatUpPressed;
    private boolean chatDownPressed;
    private boolean lookUpPressed;
    private boolean lookDownPressed;
    private boolean lookLeftPressed;
    private boolean lookRightPressed;
    
    private KeyListener keyListener = new KeyListener() {
    
        KeybindSet keybinds = GlobalConfig.getGlobalConfig().getKeyBinds();
        
        @Override
        public void keyReleased(KeyEvent e) {
            // WSAD
            if (e.getKeyChar() == keybinds.get("down"))
                downPressed = false;
            if (e.getKeyChar() == keybinds.get("up")) 
                upPressed = false;
            if (e.getKeyChar() == keybinds.get("left")) 
                leftPressed = false;
            if (e.getKeyChar() == keybinds.get("right")) 
                rightPressed = false;
            // ATTACK
            if (e.getKeyChar() == keybinds.get("attack")) 
                attackPressed = false;
            // CHAT
            if (e.getKeyChar() == keybinds.get("chatUp")) 
                chatUpPressed = false;
            if (e.getKeyChar() == keybinds.get("chatDown")) 
                chatDownPressed = false;
            // DIRECTION
            if (e.getKeyCode() == KeyEvent.VK_UP) 
                lookUpPressed = false;
            if (e.getKeyCode() == KeyEvent.VK_DOWN) 
                lookDownPressed = false;
            if (e.getKeyCode() == KeyEvent.VK_LEFT)
                lookLeftPressed = false;
            if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                lookRightPressed = false;
        }
    
        @Override
        public void keyPressed(KeyEvent e) {
            // WSAD
            if (e.getKeyChar() == keybinds.get("down")) 
                downPressed = true;
            if (e.getKeyChar() == keybinds.get("up")) 
                upPressed = true;
            if (e.getKeyChar() == keybinds.get("left")) 
                leftPressed = true;
            if (e.getKeyChar() == keybinds.get("right")) 
                rightPressed = true;
            // ATTACK
            if (e.getKeyChar() == keybinds.get("attack")) 
                attackPressed = true;
            // CHAT
            if (e.getKeyChar() == keybinds.get("chatUp")) 
                chatUpPressed = true;
            if (e.getKeyChar() == keybinds.get("chatDown")) 
                chatDownPressed = true;
            // DIRECTION
            if (e.getKeyCode() == KeyEvent.VK_UP) 
                lookUpPressed = true;
            if (e.getKeyCode() == KeyEvent.VK_DOWN) 
                lookDownPressed = true;
            if (e.getKeyCode() == KeyEvent.VK_LEFT)
                lookLeftPressed = true;
            if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                lookRightPressed = true;
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
    
    public boolean attackPressed() {
		return attackPressed;
    }
    
    public boolean chatDownPressed() {
        return chatDownPressed;
    }

    public boolean chatUpPressed() {
        return chatUpPressed;
    }

    public boolean lookDownPressed() {
        return lookDownPressed;
    }

    public boolean lookUpPressed() {
        return lookUpPressed;
    }

    public boolean lookLeftPressed() {
        return lookLeftPressed;
    }

    public boolean lookRightPressed() {
        return lookRightPressed;
    }

    public KeyListener getKeyListener() {
        return keyListener;
    }

}