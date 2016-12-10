package pixgen.phys;

import java.util.ArrayList;

import pixgen.math.Vector;
import pixgen.scene.Node;
import pixgen.util.Updateable;

public class PhysicsManager implements Updateable
{
	protected ArrayList<Node> children;
	
	public PhysicsManager()
	{
		this.children = new ArrayList<Node>();
	}
	
	@Override
	public void update(float delta)
	{		
		for(int i = 0;i < children.size();i++)
		{
			Collider col1 = (Collider) children.get(i).getComponent(Collider.class);
			
			for(int j = i + 1;j < children.size();j++)
			{
				Collider col2 = (Collider) children.get(j).getComponent(Collider.class);
				
				Collision collision = null;
				
				if(col1 instanceof AABBCollider && col2 instanceof AABBCollider)
					collision = AABBvsAABB((AABBCollider) col1, (AABBCollider) col2);
				else if(col1 instanceof CircleCollider && col2 instanceof CircleCollider)
					collision = CirclevsCircle((CircleCollider) col1, (CircleCollider) col2);
				else if((col1 instanceof AABBCollider && col2 instanceof CircleCollider))
					collision = AABBvsCircle((AABBCollider) col1, (CircleCollider) col2);
				else if((col2 instanceof AABBCollider && col1 instanceof CircleCollider))
					collision = AABBvsCircle((AABBCollider) col2, (CircleCollider) col1);
				
				if(collision != null)
				{
					Node node1 = col1.getParent();
					if(node1 instanceof PhysicsListener)
						((PhysicsListener) node1).collided(collision);
					
					Node node2 = col2.getParent();
					if(node2 instanceof PhysicsListener)
						((PhysicsListener) node2).collided(collision);
				}
			}
		}
	}
	
	private Collision AABBvsAABB(AABBCollider aabb1, AABBCollider aabb2)
	{
		boolean x = Math.abs(aabb1.x - aabb2.x) >= (aabb1.width / 2 + aabb2.width / 2);
	    boolean y = Math.abs(aabb1.y - aabb2.y) >= (aabb1.height / 2 + aabb2.height / 2);
	    
		if(!x && !y)
			return new Collision(new Vector());
		else
			return null;
	}
	
	private Collision CirclevsCircle(CircleCollider circle1, CircleCollider circle2)
	{
		Vector dist = new Vector();
		dist.x = circle2.x - circle1.x;
		dist.y = circle2.y - circle1.y;
		float distSquared = (dist.x * dist.x) + (dist.y * dist.y);
		
		float radius = circle1.radius + circle2.radius;
		float radiusSquared = radius * radius;
		
		if(distSquared <= radiusSquared)
			return new Collision(new Vector());
		else
			return null;
	}
	
	private Collision AABBvsCircle(AABBCollider aabb, CircleCollider circle)
	{
		Vector distCircle = new Vector();
		
		distCircle.x = Math.abs(circle.x - aabb.x);
		distCircle.y = Math.abs(circle.y - aabb.y);
		
		if (distCircle.x > (aabb.width/2 + circle.radius))
			return null;
		if (distCircle.y > (aabb.height/2 + circle.radius))
			return null;
		
		if (distCircle.x <= (aabb.width/2))
			return new Collision(new Vector());
		if (distCircle.y <= (aabb.height/2))
			return new Collision(new Vector());
		
		Vector distCorner = new Vector();
		
		distCorner.x = distCircle.x - aabb.width / 2;
		distCorner.y = distCircle.y - aabb.height / 2;
		
		float distCornerSquared =  distCorner.x * distCorner.x + distCorner.y * distCorner.y;
		float circleRadiusSquared = circle.radius * circle.radius;
		
		if(distCornerSquared <= circleRadiusSquared)
			return new Collision(new Vector());
		else
			return null;
	}
	
	public void addChild(Node child)
	{
		children.add(child);
	}
	
	public void removeChild(Node child)
	{
		children.remove(child);
	}
	
	public ArrayList<Node> getChildren()
	{
		return this.children;
	}
}
