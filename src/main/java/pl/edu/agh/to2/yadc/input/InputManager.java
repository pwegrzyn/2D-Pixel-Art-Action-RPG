package pl.edu.agh.to2.yadc.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager {

    private static boolean upPressed;
    private static boolean downPressed;
    private static boolean leftPressed;
    private static boolean rightPressed;
    
    private static KeyListener keyListener = new KeyListener() {
    
        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyChar() == 's') downPressed = false;
			if (e.getKeyChar() == 'w') upPressed = false;
			if (e.getKeyChar() == 'a') leftPressed = false;
			if (e.getKeyChar() == 'd') rightPressed = false;
        }
    
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyChar() == 's') downPressed = true;
			if (e.getKeyChar() == 'w') upPressed = true;
			if (e.getKeyChar() == 'a') leftPressed = true;
			if (e.getKeyChar() == 'd') rightPressed = true;
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