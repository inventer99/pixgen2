package pixgen.comp;

import pixgen.util.Updateable;

public class Animation extends Component implements Updateable
{
	private int texStart;
	private int texEnd;
	private int index;
	
	private float frameRate;
	private float frameTime;
	
	private boolean animating;
	
	public Animation(float frameRate, int texStart, int texEnd)
	{
		this.frameRate = frameRate;
		this.texStart = texStart;
		this.texEnd = texEnd;
		
		this.index = this.texStart;
		
		this.animating = false;
	}
	
	@Override
	public void update(float delta)
	{
		if(animating)
		{
			frameTime += delta;
			
			if(frameTime >= frameRate)
			{
				frameTime = 0;
				
				index++;
			}
			
			if(index > texEnd)
				index = texStart;
		}
	}

	public void pause()
	{
		animating = false;
	}
	
	public void play()
	{
		if(parent != null)
			for(Component ani : parent.getComponents(Animation.class))
				((Animation) ani).stop();
		
		animating = true;
	}
	
	public void stop()
	{
		animating = false;
		
		index = texStart;
	}
	
	public int getIndex() 
	{
		return index;
	}
	
	public boolean isPlaying()
	{
		return animating;
	}
}
