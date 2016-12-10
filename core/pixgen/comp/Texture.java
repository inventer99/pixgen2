package pixgen.comp;

import java.awt.Image;
import java.util.ArrayList;

public class Texture extends Component
{
	private ArrayList<Image> images;
	
	public Texture()
	{
		this.images = new ArrayList<Image>();
	}
	
	public Texture(Image i)
	{
		this();
		
		add(i);
	}
	
	public void add(Image i)
	{
		images.add(i);
	}
	
	public Image getIndex(int index)
	{
		return images.get(index);
	}
}
