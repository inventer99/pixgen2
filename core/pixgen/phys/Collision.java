package pixgen.phys;

import pixgen.math.Vector;
import pixgen.scene.Node;

public class Collision
{
	public Vector depth;
	public Node with;
	
	public Collision(Vector depth)
	{
		this.depth = depth;
	}
}
