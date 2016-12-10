package pixgen.asset;

import java.io.File;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

public class SoundLoader extends AssetLoader
{	
	@Override
	public Sound load(String path)
	{
		return TinySound.loadSound(new File(path));
	}
}
