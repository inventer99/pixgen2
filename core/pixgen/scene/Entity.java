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
	
	protected int textureIndex;
	
	@Override
	public void onInit()
	{
		renderOffset = new Vector();
		textureIndex = 0;
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
		Animation animation = null;
		for(Component comp : getComponents(Animation.class))
		{
			Animation a = (Animation) comp;
			if(a.isPlaying())
				animation = a;
		}
			
		
		if(texture != null)
		{
			if(animation != null)
				textureIndex = animation.getIndex();
				
			float scaleHeight = (float) texture.getIndex(textureIndex).getHeight(null) / (float) texture.getIndex(textureIndex).getWidth(null);
			
			float drawX = (renderOffset.x * PixGen.getCamera().unit) + (-(PixGen.getCamera().unit / 2));
			float drawY = (-renderOffset.y * PixGen.getCamera().unit) + (-(PixGen.getCamera().unit * scaleHeight / 2));
			
			g.drawImage(
					texture.getIndex(textureIndex),
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
