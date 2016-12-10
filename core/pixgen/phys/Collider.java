package pixgen.phys;

import pixgen.comp.Component;
import pixgen.util.Updateable;

public class Collider extends Component implements Updateable
{
	public float x;
	public float y;
	
	@Override
	public void update(float delta)
	{
		x = parent.localTranslation.x;
		y = parent.localTranslation.y;
	}
}
