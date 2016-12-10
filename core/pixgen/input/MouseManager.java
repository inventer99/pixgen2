package pixgen.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class MouseManager implements MouseListener, MouseMotionListener
{
	private ArrayList<Integer> buttons;
	private ArrayList<Integer> pressedButtons;
	private ArrayList<Integer> releasedButtons;
	
	private int mouseX, mouseY;
	private boolean dragged, moved;
	
	public MouseManager()
	{
		this.buttons = new ArrayList<Integer>();
		this.pressedButtons = new ArrayList<Integer>();
		this.releasedButtons = new ArrayList<Integer>();
	}
	
	public void clear()
	{
		pressedButtons.clear();
		releasedButtons.clear();
		dragged = false;
		moved = false;
	}
	
	public boolean mouseDown(int mousecode)
	{
		return buttons.contains(mousecode);
	}
	
	public boolean mousePressed(int mousecode)
	{
		return pressedButtons.contains(mousecode);
	}
	
	public boolean mouseReleased(int mousecode)
	{
		return releasedButtons.contains(mousecode);
	}
	
	public boolean mouseDragged()
	{
		return dragged;
	}
	
	public boolean mouseMoved()
	{
		return moved;
	}
	
	public int mouseX()
	{
		return mouseX;
	}
	
	public int mouseY()
	{
		return mouseY;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
		moved = true;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
		dragged = true;
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{	
		if(!buttons.contains(e.getButton()))
		{
			buttons.add(e.getButton());
		}
		pressedButtons.add(e.getButton());
	}
	
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		buttons.remove((Object) e.getButton());
		releasedButtons.add(e.getButton());
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
}
