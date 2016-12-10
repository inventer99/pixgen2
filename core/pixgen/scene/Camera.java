package pixgen.scene;

import pixgen.math.Vector;
import pixgen.util.Updateable;

public class Camera implements Updateable
{
	private int height;
	private float zoom;
	public float unit;
	
	public Vector translation;
	public float rotation;

	private Node followee;
	private boolean followTranslation; 
	private boolean followRotation;
	
	public Camera(int height)
	{
		this.height = height;
		this.zoom = 8.0F;
		this.unit = this.height / this.zoom;
		
		this.translation = new Vector(0.0F, 0.0F);
		this.rotation = 0.0F;
		
		this.followee = null;
		this.followTranslation = false;
		this.followRotation = false;
	}
	
	public void setZoom(float zoom)
	{
		this.zoom = zoom;
		this.unit = this.height / this.zoom;
	}
	
	public void follow(Node node, boolean followTranslation, boolean followRotation)
	{
		this.followee = node;
		this.followTranslation = followTranslation;
		this.followRotation = followRotation;
	}

	@Override
	public void update(float delta)
	{
		if(followee != null)
		{
			if(followTranslation)
				this.translation = new Vector(followee.localTranslation.x, followee.localTranslation.y);
			if(followRotation)
				this.rotation = followee.localRotation;
		}
	}
}
