package pixgen.asset;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import pixgen.scene.Node;

public class AssetManager
{
	private ArrayList<AssetLoader> loaders;
	private HashMap<String, Class<? extends AssetLoader>> extentions;
	
	public AssetManager()
	{
		this.loaders = new ArrayList<AssetLoader>();
		this.extentions = new HashMap<String, Class<? extends AssetLoader>>();
		
		registerLoader(ImageLoader.class, "png", "jpg");
		registerLoader(MusicLoader.class, "wav", "mp3");
		registerLoader(SoundLoader.class, "wav", "mp3");
		registerLoader(SceneLoader.class, "pgs");
	}
	
	public Image loadImage(String path)
	{
		return (Image) load(path, ImageLoader.class);
	}
	
	public Music loadMusic(String path)
	{
		return (Music) load(path, MusicLoader.class);
	}
	
	public Sound loadSound(String path)
	{
		return (Sound) load(path, SoundLoader.class);
	}
	
	public Node loadScene(String path)
	{
		return (Node) load(path);
	}
	
	public Object load(String path, Class<? extends AssetLoader> extLoader)
	{			
		AssetLoader assetLoader = null;
		for(AssetLoader loader : loaders)
		{
			if(extLoader.isInstance(loader))
			{
				assetLoader = loader;
				break;
			}
		}
		
		try {
			return assetLoader.load(path);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
			return null;
		}
	}
	
	public Object load(String path)
	{			
		Class<? extends AssetLoader> extLoader = extentions.get(
				path.substring(path.lastIndexOf('.') + 1));
		
		AssetLoader assetLoader = null;
		for(AssetLoader loader : loaders)
		{
			if(extLoader.isInstance(loader))
			{
				assetLoader = loader;
				break;
			}
		}
		
		try {
			return assetLoader.load(path);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
			return null;
		}
	}
	
	public void registerLoader(Class<? extends AssetLoader> loader, String... exts)
	{
		try
		{
			loaders.add(loader.newInstance());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		for(String ext : exts)
		{
			extentions.put(ext, loader);
		}
	}
}
