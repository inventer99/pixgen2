package pixgen.math;

public class Transformation 
{	
	public static Vector Rotate(Vector v, float i)
	{
		double d = Math.toRadians(i);
		
		v.x = ((float) ((v.x * Math.cos(d)) - (v.y * Math.sin(d))));
		v.y = ((float) ((v.x * Math.sin(d)) + (v.y * Math.cos(d))));
		
		return v;
	}
	
	public static Vector RotateAbout(Vector v, Vector p, float i)
	{
		double d = Math.toRadians(i);
		
		v.x -= p.x;
		v.y -= p.y;
		
		v.x = ((float) ((v.x * Math.cos(d)) - (v.y * Math.sin(d))));
		v.y = ((float) ((v.x * Math.sin(d)) + (v.y * Math.cos(d))));
		
		v.x += p.x;
		v.y += p.y;
		
		return v;
	}
}
