package pl.edu.agh.to2.yadc.input;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;


public class KeybindSet {

    private Map<String, Integer> keyBinds;

    public KeybindSet() {
        this.keyBinds = new HashMap<>();
        // Defaults
        keyBinds.put("up", KeyEvent.VK_W);
		keyBinds.put("down", KeyEvent.VK_S);
		keyBinds.put("left", KeyEvent.VK_A);
		keyBinds.put("right", KeyEvent.VK_D);
        keyBinds.put("attack", KeyEvent.VK_SPACE);
        keyBinds.put("chatUp", KeyEvent.VK_K);
        keyBinds.put("chatDown", KeyEvent.VK_M);
        keyBinds.put("lookUp", KeyEvent.VK_UP);
        keyBinds.put("lookDown", KeyEvent.VK_DOWN);
        keyBinds.put("lookLeft", KeyEvent.VK_LEFT);
        keyBinds.put("lookRight", KeyEvent.VK_RIGHT);
        keyBinds.put("chatInteract", KeyEvent.VK_ENTER);
        keyBinds.put("cancel", KeyEvent.VK_ESCAPE);
    }

    public int get(String code) {
        return this.keyBinds.get(code);
    }

    public void put(String code, int keyCode) {
        this.keyBinds.put(code, keyCode);
    }

}