package pixgen.scene;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import pixgen.PixGen;
import pixgen.comp.Animation;
import pixgen.comp.Component;
import pixgen.comp.Texture;
import pixgen.math.Vector;

public class Entity extends Node
{
	protected Vector renderOffset;
	
	@Override
	public void onInit()
	{
		renderOffset = new Vector();
	}

	@Override
	public void render(Graphics2D g)
	{
		AffineTransform resetTx = g.getTransform();
		
		g.translate(localTranslation.x * PixGen.getCamera().unit, -localTranslation.y * PixGen.getCamera().unit);
		g.rotate(Math.toRadians(localRotation));
		
		AffineTransform localTx = g.getTransform();
		
		g.scale(localScale.x, localScale.y);
		
		Texture texture = (Texture) getComponent(Texture.class);
		Animation animation = (Animation) getComponent(Animation.class);
		
		if(texture != null)
		{
			int index = 0;
			
			if(animation != null)
				index = animation.getIndex();
				
			float scaleHeight = (float) texture.getIndex(index).getHeight(null) / (float) texture.getIndex(index).getWidth(null);
			
			float drawX = (renderOffset.x * PixGen.getCamera().unit) + (-(PixGen.getCamera().unit / 2));
			float drawY = (-renderOffset.y * PixGen.getCamera().unit) + (-(PixGen.getCamera().unit * scaleHeight / 2));
			
			g.drawImage(
					texture.getIndex(index),
					(int) drawX,
					(int) drawY,
					(int) PixGen.getCamera().unit,
					(int) (PixGen.getCamera().unit * scaleHeight), null);
		}
		
		onRender(g);
		
		g.setTransform(localTx);
		
		for(Node child : children)
			child.render(g);
		
		g.setTransform(resetTx);
	}
}
