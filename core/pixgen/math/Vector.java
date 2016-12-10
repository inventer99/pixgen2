package pixgen.math;

public class Vector
{
	public float x;
	public float y;
	
	public Vector(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector()
	{
		this(0, 0);
	}
	
	public Vector(Vector v)
	{
		this.x = v.x;
		this.y = v.y;
	}
	
	public Vector add(Vector a)
	{
		this.x += a.x;
		this.y += a.y;
		
		return this;
	}
	
	public Vector sub(Vector a)
	{
		this.x -= a.x;
		this.y -= a.y;
		
		return this;
	}
	
	public Vector mul(float s)
	{
		this.x *= s;
		this.y *= s;
		
		return this;
	}
	
	public Vector div(float s)
	{
		this.x /= s;
		this.y /= s;
		
		return this;
	}
	
	public Vector normalize()
	{
		float l = Math.abs(this.getLength());
		
		this.x = this.x / l;
		this.y = this.y / l;
		
		return this;
	}

	public float getLength()
	{
		return (float) Math.sqrt(x * x + y * y);
	}
}
