package pl.edu.agh.to2.yadc.input;

import java.util.HashMap;
import java.util.Map;


public class KeybindSet {

    private Map<String, Character> keyBinds;

    public KeybindSet() {
        this.keyBinds = new HashMap<>();
        // Defaults
        keyBinds.put("up", 'w');
		keyBinds.put("down", 's');
		keyBinds.put("left", 'a');
		keyBinds.put("right", 'd');
        keyBinds.put("attack", ' ');
        keyBinds.put("chatUp", 'k');
        keyBinds.put("chatDown", 'm');
    }

    public Character get(String code) {
        return this.keyBinds.get(code);
    }

    public void put(String code, Character val) {
        this.keyBinds.put(code, val);
    }

}