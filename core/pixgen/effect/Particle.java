package pixgen.effect;

import pixgen.comp.Texture;
import pixgen.math.Vector;
import pixgen.scene.Entity;

public class Particle extends Entity
{
	private float life;
	private Vector velocity;
	
	public Particle(Texture texture, Vector scale, float life, Vector initVelocity)
	{
		this.addComponent(texture);
		
		this.localScale = scale;
		
		this.life = life;
		this.velocity = initVelocity;
	}
	
	@Override
	public void onUpdate(float delta)
	{
		life -= delta;
		this.localTranslation.add(velocity);
		
		if(life <= 0)
			this.parent.removeChild(this);
	}
}
