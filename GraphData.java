package Application;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;



public class GraphData implements Serializable
{
	private double codomainLeft;
	private double codomainRight;
	private Color crossColor;
	private double domainLeft;
	private double domainRight;
	private ArrayList<Function> functions;
	private Color netColor;
	private double x0;
	private double xFactor;
	private double y0;
	private double yFactor;
	
	public GraphData()
	{
		functions = new ArrayList<Function>();
		crossColor = Color.black;
		netColor = Color.LIGHT_GRAY;
	}
	
	public Function addFunction(String functionTerm)
	{
		Function function = new Function(functionTerm, domainLeft, domainRight);
		functions.add(function);
		
		return function;
	}
	
	public double getCodomainLeft()
	{
		return codomainLeft;
	}
	
	public double getCodomainRight() 
	{
		return codomainRight;
	}
	
	public Color getCrossColor()
	{
		return crossColor;
	}
	
	public double getDomainLeft()
	{
		return domainLeft;
	}
	
	public double getDomainRight()
	{
		return domainRight;
	}
	
	public Function getFunction(int index)
	{
		return functions.get(index);
	}
	
	public Color getNetColor() 
	{
		return netColor;
	}

	public int getNumberOfFunctions()
	{
		return functions.size();
	}
	
	public double getX0()
	{
		return x0;
	}
	
	public double getXFactor()
	{
		return xFactor;
	}
	
	public double getY0()
	{
		return y0;
	}

	public double getYFactor()
	{
		return yFactor;
	}

	public void removeFunction(Function function)
	{
		functions.remove(function);		
	}

	public void setCodomainLeft(double newCodomainLeft)
	{
		codomainLeft = newCodomainLeft;
	}

	public void setCodomainRight(double newCodomainRight)
	{
		codomainRight = newCodomainRight;
	}

	public void setCrossColor(Color newCrossColor)
	{
		crossColor = newCrossColor;
	}

	public void setDomainLeft(double newDomainLeft)
	{
		domainLeft = newDomainLeft;
	}

	public void setDomainRight(double newDomainRight)
	{
		domainRight = newDomainRight;
	}

	public void setX0(double newX0)
	{
		x0 = newX0;
	}

	public void setXFactor(double newXFactor)
	{
		xFactor = newXFactor;
	}

	public void setY0(double newY0)
	{
		y0 = newY0;
	}

	public void setYFactor(double newYFactor)
	{
		yFactor = newYFactor;
	}
}
