package pixgen;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import kuusisto.tinysound.TinySound;
import pixgen.input.KeyManager;
import pixgen.input.MouseManager;
import pixgen.scene.Camera;
import pixgen.util.Config;

public class PixGen implements Runnable
{
	// Engine thread
	private Thread t_engine;
	
	// Game associated with the engine
	private Game game;
	
	private boolean isRunning;
	
	// Engine graphics context
	private Graphics2D g;
	private Window window;
	
	// Managers for input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	// Default configuration
	private Config config;
	
	// Main camera
	private static Camera camera;
	
	private void defaultConfig()
	{
		config = new Config();
		config.setString("window.title", "PixGen2");
		config.setInt("window.width", 848);
		config.setInt("window.height", 480);
		config.setInt("engine.updaterate", 120);
		config.setInt("engine.drawrate", 30);
		config.setInt("render.width", 848);
		config.setInt("render.height", 480);
	}
	
	private void init()
	{
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
		
		window = new Window(config);
		window.addKeyListener(this.getKeyManager());
		window.addMouseListener(this.getMouseManager());
		window.addMouseMotionListener(this.getMouseManager());
		
		g = (Graphics2D) window.target.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
//		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, 100);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_PURE);
		
		TinySound.init();
		
		camera = new Camera(config.getInt("render.height"));
	}
	
	private void destroy()
	{
		window.dispose();
		
		TinySound.shutdown();
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run()
	{
		// Set the default configuration
		this.defaultConfig();
	
		// Let the game configure its-self
		game.preInit();
		
		// Initiate the engine
		this.init();
		
		// Initiate the game
		game.init();
		
		long startTime = 0;
		long stopTime  = 0;
		long diffTime  = 0;
		long deltaTime = 0;
		float delta    = 0;
		long updateRate = 1000 / config.getInt("engine.updaterate");
		float drawRate = config.getInt("engine.drawrate") / 1000f;
		
		float drawCount = 0;
		int dps = 0;
		
		float secCount = 0;
		int ups = 0;
		
		isRunning = true;
		while(isRunning)
		{
			startTime = System.currentTimeMillis();
			{
				game.update(delta);
				game.rootNode.update(delta);
				game.physicsManager.update(delta);
				camera.update(delta);
				mouseManager.clear();
				
				drawCount += delta;
				if(drawCount >= drawRate)
				{
					g.clearRect(0, 0, window.target.getWidth(), window.target.getHeight());
					
					AffineTransform tx = g.getTransform();
					// Set (0, 0) to the center of the screen
					g.translate(config.getInt("render.width") / 2, config.getInt("render.height") / 2);
					// Move to camera
					g.rotate(Math.toRadians(-camera.rotation));
					g.translate(-camera.translation.x * camera.unit, camera.translation.y * camera.unit);
					
					game.render(g);
					game.rootNode.render(g);
					g.setTransform(tx);
					
					game.renderUI(g);
					
					window.repaint();
					dps++;
					drawCount = 0;
				}
				
				secCount += delta;
				if(secCount >= 1)
				{
					System.out.println("ups: " + ups + ", dps: " + dps);
					dps = 0;
					ups = 0;
					secCount = 0;
				}
				ups++;
				
				if(!window.isOpen)
					stop();
			}
			stopTime = System.currentTimeMillis();
			diffTime = stopTime - startTime;
			if(diffTime < updateRate)
			{
				synchronized(t_engine)
				{
					try
					{
						t_engine.sleep(updateRate - diffTime);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
			deltaTime = System.currentTimeMillis();
			delta = (deltaTime - startTime) / 1000f;
		}
		
		game.destroy();
		
		destroy();
	}
	
	public void start(Game game)
	{
		this.game = game;
		
		t_engine = new Thread(this, "pixgen_engine");
		t_engine.start();
	}
	
	public void stop()
	{
		this.isRunning = false;
	}
	
	public Config getConfig()
	{
		return this.config;
	}
	
	public void setConfig(Config config, boolean merge)
	{
		if(merge)
			this.config.merge(config);
		else
			this.config = config;
	}
	
	public Game getGame()
	{
		return this.game;
	}
	
	public static Camera getCamera()
	{
		return camera;
	}
	
	public static void setCamera(Camera ncamera)
	{
		camera = ncamera;
	}
	
	public KeyManager getKeyManager() 
	{
		return this.keyManager;
	}
	
	public MouseManager getMouseManager() 
	{
		return this.mouseManager;
	}
	
	public Graphics2D getGraphics()
	{
		return this.g;
	}
	
//	public StateManager getStateManager() 
//	{
//		return this.stateManager;
//	}
}
