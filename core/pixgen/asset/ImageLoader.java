package pixgen.asset;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageLoader extends AssetLoader
{	
	@Override
	public Image load(String path)
	{
		try 
		{
			return ImageIO.read(new File(path));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
}
