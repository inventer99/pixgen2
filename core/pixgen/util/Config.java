package pixgen.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config
{
	private Properties properties;
	private String path;
	
	public Config()
	{
		properties = new Properties();
	}
	
	public Config(String path)
	{
		this();
		this.path = path;
		load();
	}
	
	private void load()
	{
		try 
		{
			FileInputStream in = new FileInputStream(path);
			properties.load(in);
			in.close();
		} 
		catch (IOException e) 
		{
			System.out.println("Could not load: \"" + path + "\"!");
			e.printStackTrace();
		}
	}
	
	public void merge(Config config)
	{
		Properties merged = new Properties();
		merged.putAll(properties);
		merged.putAll(config.properties);
		
		properties = merged;
	}
	
	public int getInt(String name)
	{
		return Integer.parseInt(properties.getProperty(name, "0"));
	}
	
	public void setInt(String name, int value)
	{
		properties.setProperty(name, "" + value);
	}
	
	public float getFloat(String name)
	{
		return Float.parseFloat(properties.getProperty(name, "0"));
	}
	
	public void setFloat(String name, float value)
	{
		properties.setProperty(name, "" + value);
	}

	public boolean getBoolean(String name)
	{
		return Boolean.parseBoolean(properties.getProperty(name, "false"));
	}
	
	public void setBoolean(String name, boolean value)
	{
		properties.setProperty(name, "" + value);
	}
	
	public String getString(String name)
	{
		return properties.getProperty(name, "");
	}
	
	public void setString(String name, String value)
	{
		properties.setProperty(name, value);
	}
}
