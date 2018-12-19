package pl.edu.agh.to2.yadc.render;

import java.awt.Graphics;


public interface Renderable {

    void renderSelf(Graphics graphics, Camera camera);

}