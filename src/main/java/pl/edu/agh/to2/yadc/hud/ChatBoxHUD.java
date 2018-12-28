package pl.edu.agh.to2.yadc.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import pl.edu.agh.to2.yadc.config.GlobalConfig;
import pl.edu.agh.to2.yadc.entity.Player;
import pl.edu.agh.to2.yadc.game.App;
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
    private double chatChangeCooldown = 0.1;
    private boolean isUserTyping;
    private double chatInteractTimer = 0;
    private double chatInteractCooldown = 0.5;
    private StringBuilder inputBuilder;
    private double inputChangeTimer = 0.05;
    private double inputChangeCooldown = 0.05;
    private int lastKeyPressed;
    private double sameKeyTimer = 0;
    private double sameKeyCooldown = 0.5;
    private double cursorBlinkTimer = 0;
    private double cursorBlinkCooldown = 0.7;
    private boolean cursorBlinkOn = false;

    public ChatBoxHUD() {

        this.allBuffer = new ArrayList<>();
        this.isUserTyping = false;
        this.inputBuilder = new StringBuilder("");

    }
    
    @Override
    public void advanceSelf(double delta) {
        if(inputManager == null) return;
        if(!isUserTyping) {
            updateTextArea(delta);
        }
        updateInputArea(delta);
        if(isUserTyping) {
            updateInputText(delta);
        }
    }

    @Override
    public synchronized void renderSelf(Graphics graphics, Camera camera) {

        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 9);
        graphics.setFont(font);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(329, 217, 1, 200);
        graphics.fillRect(329, 217, 200, 1);
        graphics.fillRect(329, 297, 169, 1);
        graphics.fillRect(329, 297, 169, 1);
        graphics.setColor(Color.GRAY);
        
        // Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 9);
        // graphics.setFont(font);

        // graphics.setColor(Color.BLACK);
        // graphics.fillRect(329, 217, 169, 94);

        // graphics.setColor(Color.WHITE);
        // graphics.fillRect(330, 218, 167, 80);

        // graphics.setColor(Color.LIGHT_GRAY);
        // graphics.fillRect(330, 298, 167, 12);

        graphics.setColor(Color.WHITE);
        if(isUserTyping && cursorBlinkOn) {
            graphics.drawString(this.inputBuilder.toString() + "|", 332, 308);
        } else {
            graphics.drawString(this.inputBuilder.toString(), 332, 308);
        }

        //graphics.fillRect(329, 297, 169, 1);

        int xPos = 332;
        int yPos = 228;
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

    private void updateTextArea(double delta) {
        chatChangeTimer += delta;
        if(chatChangeTimer > chatChangeCooldown) {
            if(inputManager.getPressedByName("chatDown")) {
                if(endCursor + 1 <= this.currentlyInAllBuffer) {
                    startCursor++;
                    endCursor++;
                    chatChangeTimer = 0;
                }
            }
            if(inputManager.getPressedByName("chatUp")) {
                if(startCursor - 1 >= 0) {
                    startCursor--;
                    endCursor--;
                    chatChangeTimer = 0;
                }
            }
        }
    }

    private void updateInputArea(double delta) {
        chatInteractTimer += delta;
        if(chatInteractTimer > chatInteractCooldown) {
            if(inputManager.getPressedByName("chatInteract")) {
                if(isUserTyping) {
                    parseCommand(inputBuilder.toString());
                    inputBuilder = new StringBuilder("");
                    isUserTyping = false;
                    inputManager.setNonChatInputDisabled(false);
                    chatInteractTimer = 0;
                } else {
                    isUserTyping = true;
                    inputManager.setNonChatInputDisabled(true);
                    chatInteractTimer = 0;
                }
            }
        }
    }

    private void updateInputText(double delta) {
        cursorBlinkTimer += delta;
        if(cursorBlinkTimer > cursorBlinkCooldown) {
            cursorBlinkOn = !cursorBlinkOn;
            cursorBlinkTimer = 0;
        }
        inputChangeTimer += delta;
        sameKeyTimer += delta;
        if(inputChangeTimer > inputChangeCooldown) {
            for(int i = 32; i <= 90; i++) {
                if(inputManager.getPressedByCode(0x08)) {
                    String buffer = inputBuilder.toString();
                    if(!buffer.equals("")) {
                        inputBuilder = new StringBuilder();
                    }
                }
                if(inputManager.getPressedByCode(i)) {
                    if(i == lastKeyPressed) {
                        if(sameKeyTimer > sameKeyCooldown) {
                            sameKeyTimer = 0;
                            if(inputBuilder.length() < MAX_CHAR_PER_LINE - 9) {
                                inputBuilder.append((char) i);
                                lastKeyPressed = i;
                            }
                        }
                    } else {
                        if(inputBuilder.length() < MAX_CHAR_PER_LINE - 8) {
                            inputBuilder.append((char) i);
                            lastKeyPressed = i;
                            sameKeyTimer = 0;
                        }
                    }
                }
            }
            inputChangeTimer = 0;
        }
    }

    public boolean isChatActive() {
        return this.isUserTyping;
    }

    private void parseCommand(String command) {
        String[] commands = command.split("\\s+");
        switch(commands[0]) {
            case "SH":
                if(commands.length < 2) {
                    printToChatBox("Incomplete Command!");
                    break;
                }
                switch(commands[1]) {
                    case "D":
                        printToChatBox("Debug info ON");
                        GlobalConfig.get().setDebug(true);
                    break;
                    case "U":
                        printToChatBox("User Interface ON");
                        GlobalConfig.get().setUIVisibility(true);
                    break;
                    case "B":
                        Player.showBackpack();
                    break;
                    case "G":
                        Player.showGold();
                    break;
                    default:
                        printToChatBox("Invalid Command!");
                    break;
                }
            break;
            case "H":
                if(commands.length < 2) {
                    printToChatBox("Incomplete Command!");
                    break;
                }
                switch(commands[1]) {
                    case "D":
                        printToChatBox("Debug info OFF");
                        GlobalConfig.get().setDebug(false);
                    break;
                    case "U":
                        printToChatBox("User Interface OFF");
                        GlobalConfig.get().setUIVisibility(false);
                    break;
                    default:
                        printToChatBox("Invalid Command!");
                    break;
                }
            break;
            case "Q":
                if(commands.length < 2) {
                    printToChatBox("Need to specify quest number!");
                    break;
                }
                if(Player.acceptNewQuest(Integer.parseInt(commands[1]))) {
                    printToChatBox("The specified quest has been accepted.");
                    break;
                }
                printToChatBox("The specified quest couldn't be accepted.");
            break;
            case "QUIT":
                App.quit();
            break;
            case "HELP":
                printToChatBox("Git gud");
            break;
            default: 
                printToChatBox("Invalid Command!");
            break;
        }
    }

}