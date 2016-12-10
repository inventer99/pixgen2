package pixgen;

import java.awt.Graphics2D;

import pixgen.asset.AssetManager;
import pixgen.phys.PhysicsManager;
import pixgen.scene.Node;

public abstract class Game
{
	public AssetManager assetManager;
	public PhysicsManager physicsManager;
	
	public Node rootNode;
	
	public Game()
	{
		this.assetManager = new AssetManager();
		this.physicsManager = new PhysicsManager();
		this.rootNode = new Node();
	}
	
	public abstract void preInit();
	public abstract void init();
	public abstract void update(float delta);
	public abstract void render(Graphics2D g);
	public abstract void renderUI(Graphics2D g);
	public abstract void destroy();
}
