package pixgen.effect;

import java.util.Random;

import pixgen.comp.Texture;
import pixgen.math.Vector;
import pixgen.scene.Node;

public class ParticleEmitter extends Node
{
	private Texture particleTexture;
	private Vector particleScale;
	private int particlesPerSec;
	private float life;
	private float initSpeed;
	private float initDir;
	private float randSpeed;
	private float randDir;
	
	private float spawnTime;
	private float time;
	
	private Random r;
	
	
	public ParticleEmitter(Texture particleTexture, Vector particleScale, int particlesPerSec, float life, float initSpeed, float initDir, float randSpeed, float randDir)
	{
		this.particleTexture = particleTexture;
		this.particleScale = particleScale;
		this.particlesPerSec = particlesPerSec;
		this.life = life;
		this.initSpeed = initSpeed;
		this.initDir = initDir;
		this.randSpeed = randSpeed;
		this.randDir = randDir;
		
		this.spawnTime = 1 / this.particlesPerSec;
		this.time = 0;
		
		this.r = new Random();
	}
	
	@Override
	public void onUpdate(float delta)
	{
		time += delta;
		
		if(time >= spawnTime)
		{
			float dir = initDir + (r.nextFloat() * randDir) - (randDir / 2);
			float speed = initSpeed + (r.nextFloat() * randSpeed) - (randSpeed / 2);
			
			Vector iv = new Vector(
					speed * (float) Math.cos(Math.toRadians(dir)),
					speed * (float) Math.sin(Math.toRadians(dir)));
			this.addChild(new Particle(particleTexture, particleScale, life, iv));
			
			time = 0;
		}
	}
}
