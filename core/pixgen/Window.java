package pixgen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;

import pixgen.util.Config;

@SuppressWarnings("serial")
public class Window extends JFrame
{
	public boolean isOpen = true;
	public BufferedImage target;
	
	public Window(Config config)
	{
		this.setTitle(config.getString("window.title"));
		
		if(config.getBoolean("window.fullscreen"))
		{
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			
			if (gd.isFullScreenSupported()) {
				setUndecorated(true);
				gd.setFullScreenWindow(this);
				
				config.setInt("window.width", (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth());
				config.setInt("window.height", (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight());
			} else {
				System.err.println("Full screen not supported");
			}
		}
		
		this.setSize(
				config.getInt("window.width"),
				config.getInt("window.height"));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				Window.this.isOpen = false;
			}
		});
		
		this.target = new BufferedImage(
				config.getInt("render.width"),
				config.getInt("render.height"),
				BufferedImage.TYPE_INT_ARGB);
		this.add(new JComponent() {
			@Override
			public void paintComponent(Graphics g)
			{
				Graphics2D g2d = (Graphics2D) g;
				super.paintComponent(g2d);
				
				float scale = (float) config.getInt("window.height") / (float) config.getInt("render.height");
				float offset = (config.getInt("window.width") - config.getInt("render.width") * scale) / 2;
				
				g2d.clearRect(0, 0,
						config.getInt("window.width"),
						config.getInt("window.height"));
				g2d.setColor(Color.BLACK);
				g2d.fillRect(0, 0,
						config.getInt("window.width"),
						config.getInt("window.height"));
				g2d.drawImage(Window.this.target, (int) offset, 0,
						(int) (config.getInt("render.width") * scale),
						(int) (config.getInt("render.height") * scale), null);
			}
		});
		
		this.setVisible(true);
	}
}
