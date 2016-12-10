package pixgen.scene;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;

import pixgen.PixGen;
import pixgen.comp.Component;
import pixgen.math.Vector;
import pixgen.util.Renderable;
import pixgen.util.Updateable;

public class Node implements Updateable, Renderable
{
	protected Node parent;
	protected ArrayList<Node> children;
	private Iterator<Node> iteratee;
	
	protected ArrayList<Component> components;
	
	public Vector localTranslation;
	public float localRotation;
	public Vector localScale;
	
	public Node()
	{
		this.parent = null;
		this.children = new ArrayList<Node>();
		this.components = new ArrayList<Component>();
		
		this.localTranslation = new Vector(0.0F, 0.0F);
		this.localRotation = 0.0F;
		this.localScale = new Vector(1.0F, 1.0F);
		
		onInit();
	}
	
	protected void onInit() {};
	
	protected void onUpdate(float delta) {};
	
	@Override
	public void update(float delta)
	{
		for(Component comp : components)
			if(comp instanceof Updateable)
				((Updateable) comp).update(delta);
		
		onUpdate(delta);
		
		Iterator<Node> iterator = children.iterator();
		while(iterator.hasNext())
		{
			iteratee = iterator;
			((Node) iterator.next()).update(delta);
		}
		iteratee = null;
	}
	
	protected void onRender(Graphics2D g) {};
	
	@Override
	public void render(Graphics2D g)
	{
		AffineTransform resetTx = g.getTransform();
		
		g.translate(localTranslation.x * PixGen.getCamera().unit, -localTranslation.y * PixGen.getCamera().unit);
		g.rotate(Math.toRadians(localRotation));
		
		AffineTransform localTx = g.getTransform();
		
		g.scale(localScale.x, localScale.y);
		
		onRender(g);
		
		g.setTransform(localTx);
		
		for(Node child : children)
			child.render(g);
		
		g.setTransform(resetTx);
	}
	
	public void addChild(Node child)
	{
		child.parent = this;
		children.add(child);
	}
	
	public void removeChild(Node child)
	{
		child.parent = null;
		if(iteratee != null)
			iteratee.remove();
		else
			children.remove(child);
	}
	
	public ArrayList<Node> getChildren()
	{
		return this.children;
	}
	
	public Node getParent()
	{
		return this.parent;
	}
	
	public void addComponent(Component comp)
	{
		comp.setParent(this);
		components.add(comp);
	}
	
	public void removeComponent(Component comp)
	{
		comp.setParent(null);
		components.remove(comp);
	}
	
	public Component getComponent(Class<? extends Component> type)
	{
		for(Component comp : components)
			if(type.isInstance(comp))
				return comp;
		
		return null;
	}
}
