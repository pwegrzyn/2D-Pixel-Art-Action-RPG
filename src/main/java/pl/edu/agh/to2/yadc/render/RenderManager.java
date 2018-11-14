package pl.edu.agh.to2.yadc.render;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import pl.edu.agh.to2.yadc.camera.Camera;
import pl.edu.agh.to2.yadc.config.Configuration;
import pl.edu.agh.to2.yadc.entity.Entity;
import pl.edu.agh.to2.yadc.game.App;


public class RenderManager {
	

	private static Frame mainFrame;
	private static Canvas mainCanvas;
	private static int canvasHeight;
	private static int canvasWidth;
	private static long currentFps;
	private static Configuration config;
	
	private static Camera mainCamera;
	private static BufferedImage map;
	

	public static void initialSetup(Configuration initialConfig) {
		
		config = initialConfig;
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenDimension = toolkit.getScreenSize();
		int widthRatio = (int) Math.floor(screenDimension.width / config.getTargetWidth());
		int heightRatio = (int) Math.floor(screenDimension.height / config.getTargetHeight());
		int factor = Math.min(widthRatio,  heightRatio);
		canvasWidth = config.getTargetWidth() * factor;
		canvasHeight = config.getTargetHeight() * factor;

		mainFrame = new Frame();
		mainCanvas = new Canvas();
		Dimension dim = new Dimension(canvasWidth, canvasHeight);
		mainCanvas.setPreferredSize(dim);
		mainFrame.add(mainCanvas);
		
		mainCamera = new Camera(0, 0);
		
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				App.quit();
			}
		});
		
		mainFrame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == 's') mainCamera.move(0, -100);
				if (e.getKeyChar() == 'w') mainCamera.move(0, 100);
				if (e.getKeyChar() == 'a') mainCamera.move(100, 0);
				if (e.getKeyChar() == 'd') mainCamera.move(-100, 0);
			}
		});
		
		setUpMenuBar(mainFrame);
		
		mainFrame.setTitle("YADC");
		mainFrame.setBackground(Color.white);
		mainFrame.pack();
		mainFrame.setResizable(false);
		mainFrame.setLocationRelativeTo(null);
		
		mainFrame.setVisible(true);
		try {
			map = ImageIO.read(new File("resources/aaa.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
	
	public static void startRendering() {
		Thread mainThread = new Thread() {
			
			@Override
			public void run() {
				GraphicsConfiguration configuration = mainCanvas.getGraphicsConfiguration();
				VolatileImage image = configuration.createCompatibleVolatileImage(config.getTargetWidth(), config.getTargetHeight());
				
				while(true) {
					
					long currentFrameStartTime = System.nanoTime();
					
					renderFrame(image, configuration);
					
					long currentFrameTime = System.nanoTime() - currentFrameStartTime;
					if(currentFrameTime < config.getTargetFrameTime()) {
						try {
							Thread.sleep((config.getTargetFrameTime() - currentFrameTime) / 1000000);
						} catch (InterruptedException e) {
							System.err.println("Interrupted while capping FPS");
							e.printStackTrace();
						}
					}
					
					long cappedFrameTime = System.nanoTime() - currentFrameStartTime;
					currentFps = 1000000000 / cappedFrameTime;	
				}
			}
		};
		
		mainThread.start();
	}
	
	
	private static void renderFrame(VolatileImage image, GraphicsConfiguration configuration) {
		
		if(image.validate(configuration) == VolatileImage.IMAGE_INCOMPATIBLE) {
			image = configuration.createCompatibleVolatileImage(config.getTargetWidth(), config.getTargetHeight());
		}
		

		Graphics graphics = image.getGraphics();
		 
		
		// START RENDER
		graphics.setColor(Color.WHITE);
		
	//	graphics.drawImage(image, 0, 0, null);
		
		graphics.fillRect(0, 0, config.getTargetWidth(), config.getTargetHeight());
		
		
		
			graphics.drawImage(map,   0 + mainCamera.getXPos(),  0 + mainCamera.getYPos(), 2500, 1500, null);

		showMetrics(graphics);
		
		// END RENDER
		
		graphics.dispose();
		graphics = mainCanvas.getGraphics();
		graphics.drawImage(image, 0, 0, canvasWidth, canvasHeight, null);
		graphics.dispose();
		
	}
	
	
	private static void showMetrics(Graphics graphics) {
		graphics.setColor(Color.BLACK);
		graphics.drawString("FPS: " + String.valueOf(currentFps), 2, 13);
		long usedRam = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
		graphics.drawString("RAM: " + String.valueOf(usedRam), 2, 30);
	}
	
	
	private static void setUpMenuBar(Frame frame) {
	
		MenuBar menuBar = new MenuBar();
		
		Menu gameMenu = new Menu("Game");
		Menu settingsMenu = new Menu("Settings");
		Menu helpMenu = new Menu("Help");
		
		MenuItem newGameItem = new MenuItem("New Game");
		newGameItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("New game pressed");
			}
		});
		gameMenu.add(newGameItem);
		MenuItem quitGameItem = new MenuItem("Quit Game");
		quitGameItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.quit();
			}
		});
		gameMenu.add(quitGameItem);
		
		Menu setFpsMenu = new Menu("Set FPS");
		MenuItem setFps30Item = new MenuItem("30 FPS");
		setFps30Item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				config.setTargetFps(30);
			}
		});
		MenuItem setFps60Item = new MenuItem("60 FPS");
		setFps60Item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				config.setTargetFps(60);
			}
		});
		MenuItem setFpsMaxItem = new MenuItem("Unlimited");
		setFpsMaxItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				config.setTargetFps(9001);
			}
		});
		setFpsMenu.add(setFps30Item);
		setFpsMenu.add(setFps60Item);
		setFpsMenu.add(setFpsMaxItem);
		settingsMenu.add(setFpsMenu);
		
		menuBar.add(gameMenu);
		menuBar.add(settingsMenu);
		menuBar.add(helpMenu);
		frame.setMenuBar(menuBar);
		
	}

	public static Canvas getMainCanvas() {
		return mainCanvas;
	}

}
