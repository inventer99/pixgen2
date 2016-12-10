package pixgen.comp;

import pixgen.scene.Node;

public class Component
{
	protected Node parent;
	
	public void setParent(Node parent)
	{
		this.parent = parent;
	}
	
	public Node getParent()
	{
		return this.parent;
	}
}
