package Application;
import java.io.Serializable;


public class DPoint implements Serializable
{
	private double x;
	private double y;
	
	public DPoint()
	{
		;
	}
	
	public DPoint(double newX, double newY)
	{
		x = newX;
		y = newY;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public void setX(double newX)
	{
		x = newX;
	}
	
	public void setY(double newY)
	{
		y = newY;
	}
}
