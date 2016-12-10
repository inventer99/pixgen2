package pixgen.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyManager implements KeyListener
{
	private ArrayList<Integer> keys;
	
	public KeyManager()
	{
		this.keys = new ArrayList<Integer>();
	}
	
	public boolean keyDown(int keycode)
	{
		return keys.contains(keycode);
	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(!keys.contains(e.getKeyCode()))
		{
			keys.add(e.getKeyCode());
		}
	}
	@Override
	public void keyReleased(KeyEvent e) 
	{
		keys.remove((Object) e.getKeyCode());
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
}
