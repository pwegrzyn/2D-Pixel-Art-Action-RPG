package pl.edu.agh.to2.yadc.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import pl.edu.agh.to2.yadc.input.InputManager;
import pl.edu.agh.to2.yadc.physics.Advanceable;
import pl.edu.agh.to2.yadc.render.Camera;
import pl.edu.agh.to2.yadc.render.Renderable;


public class ChatBoxHUD implements Advanceable, Renderable {

    private final int MAX_CHAR_PER_LINE = 33;
    private ArrayList<String> allBuffer;
    private final int ACTIVE_BUFFER_SIZE = 7;
    private int currentlyInAllBuffer = 0;
    private int startCursor = 0;
    private int endCursor = 0;
    private InputManager inputManager;
    private double chatChangeTimer = 0;
    private double chatChangeCooldown = 0.2;

    public ChatBoxHUD() {

        this.allBuffer = new ArrayList<>();
    }
    
    @Override
    public void advanceSelf(double delta) {
        if(inputManager == null) return;
        chatChangeTimer += delta;
        if(chatChangeTimer > chatChangeCooldown) {
            if(inputManager.chatDownPressed()) {
                if(endCursor + 1 <= this.currentlyInAllBuffer) {
                    startCursor++;
                    endCursor++;
                    chatChangeTimer = 0;
                }
            }
            if(inputManager.chatUpPressed()) {
                if(startCursor - 1 >= 0) {
                    startCursor--;
                    endCursor--;
                    chatChangeTimer = 0;
                }
            }
        }
    }

    @Override
    public synchronized void renderSelf(Graphics graphics, Camera camera) {
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 9);
        graphics.setFont(font);
        graphics.drawString("Press M to scroll down | K to scroll up", 330, 228);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(330, 230, 167, 80);
        graphics.setColor(Color.black);
        int xPos = 332;
        int yPos = 240;
        for(int i = startCursor, j = 0; i < endCursor; i++, j++) {
            graphics.drawString(this.allBuffer.get(i), xPos, yPos + j * 11);
        }
    }
    
    public synchronized void printToChatBox(String str) {

        if(str.length() > MAX_CHAR_PER_LINE) {
            String[] split = str.split("(?<=\\G.{" + MAX_CHAR_PER_LINE + "})");
            for(String s : split) {
                this.allBuffer.add(s);
                this.endCursor++;
                if(this.endCursor > ACTIVE_BUFFER_SIZE) {
                    this.startCursor++;
                }
                this.currentlyInAllBuffer++;
            }
            return;
        }

        this.allBuffer.add(str);
        this.endCursor++;
        if(this.endCursor > ACTIVE_BUFFER_SIZE) {
            this.startCursor++;
        }
        this.currentlyInAllBuffer++;

    }

    public void setInputManager(InputManager input) {
        this.inputManager = input;
    }

}