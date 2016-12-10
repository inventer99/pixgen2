package pixgen.asset;

import java.io.File;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.TinySound;

public class MusicLoader extends AssetLoader
{	
	@Override
	public Music load(String path)
	{
		return TinySound.loadMusic(new File(path));
	}
}
