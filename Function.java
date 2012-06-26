package Application;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;



public class Function implements Serializable
{
	private String term;
	private String aufleitung;
	private Color color;
	private boolean drawLowerSum;
	private boolean drawUpperSum;
	private boolean visible;
	private double dx;
	private double intervalLeft;
	private double intervalRight;
	private double steps;
	private ArrayList<DPoint> points;
	
	public Function(String newTerm, double domainLeft, double domainRight)
	{
		visible = true;
		term = newTerm;
		aufleitung = Calculator.aufleiten(term);
		color = Color.red;
		intervalLeft = -2;
		intervalRight = 2;
		steps = 10;
		drawUpperSum = false;
		drawLowerSum = false;
		
		calcFunctionData(domainLeft, domainRight);
	}
	
	public static boolean inputIsFunction(String function)
	{
		boolean right = true;
		char c;
		
		if(function.length() == 0)
		{
			return false;
		}
		
		for(int i=0; i<function.length(); i++)
		{
			c = function.charAt(i);
			
			if(c != 'x' && c != '+' && c != '-' && c != '*' && c != '/' && c != '^' && c != '^' &&c != '.' && c != ',' && c != '(' && c != ')' && (c < '0' || c > '9'))
			{
				right = false;
				break;
			}
		}
		
		return right;
	}
	
	public void calcFunctionData(double domainLeft, double domainRight)
	{
		double tmp;
		String var;
		
		dx = (intervalRight-intervalLeft)/steps;
		
		if(intervalLeft<domainLeft)
		{
			domainLeft = intervalLeft;
		}
		
		if(intervalRight>domainRight)
		{
			domainRight = intervalRight;
		}
		
		if(points == null)
		{
			ArrayList<DPoint> newPoints = new ArrayList<DPoint>();
			
    		for(double i=domainLeft; i<=domainRight; i+=0.0005)
    		{
    			NumberFormat nf = new DecimalFormat(".####");
    			String h = nf.format(i);
    			
    			var = term.replaceAll("x", "("+h+")");
    			tmp = Calculator.allesRechnen(var);
    			
    			newPoints.add(new DPoint(i, tmp));
    		}

    		points = newPoints;
		}
		
		if(domainLeft<points.get(0).getX())
		{
			ArrayList<DPoint> newPoints = new ArrayList<DPoint>();
			
			for(double i=domainLeft; i<points.get(0).getX(); i+=0.0005)
    		{
    			NumberFormat nf = new DecimalFormat(".####");
    			String h = nf.format(i);
    			
    			var = term.replaceAll("x", "("+h+")");
    			tmp = Calculator.allesRechnen(var);
    			
    			newPoints.add(new DPoint(i, tmp));
    		}

    		newPoints.addAll(points);
    		
    		points = newPoints;
		}
		
		if(domainRight>points.get(points.size()-1).getX())
		{
			ArrayList<DPoint> newPoints = new ArrayList<DPoint>();
			
			for(double i=points.get(points.size()-1).getX(); i<domainRight; i+=0.0005)
    		{
    			NumberFormat nf = new DecimalFormat(".####");
    			String h = nf.format(i);
    			
    			var = term.replaceAll("x", "("+h+")");
    			tmp = Calculator.allesRechnen(var);
    			
    			newPoints.add(new DPoint(i, tmp));
    		}

    		points.addAll(newPoints);
		}
	}
	
	public double calcIntegral()
	{
		String rechnung, term1, term2;
		
		term1 = aufleitung.replaceAll("x", "("+String.valueOf(intervalLeft)+")");
		term2 = aufleitung.replaceAll("x", "("+String.valueOf(intervalRight)+")");
		
		rechnung = "("+term2+")-("+term1+")";
		
		return Calculator.allesRechnen(rechnung);
	}
	
	public double calcObersumme()
	{
		double hoehe, erg;

		erg = 0.0;
		
		for(double i=intervalLeft; i<=intervalRight-dx+(dx/4); i+=dx)
		{
			int j1, j2;
			
			j1 = 0;
			
			
			while(points.get(j1).getX()<=i && j1 < points.size()-1)
			{
				j1++;
			}
			
			j2 = j1;
			
			while(points.get(j2).getX()<=i+dx && j2 < points.size()-1)
			{
				j2++;
			}
			
			hoehe = points.get(j1).getY();
			
			for(int j=j1; j<=j2; j++)
			{
				if(points.get(j).getY()>hoehe)
				{
					hoehe = points.get(j).getY();
				}
			}
			
			erg += dx*hoehe;			
		}
		
		return erg;
	}
	
	public double calcUntersumme()
	{		
		double hoehe, erg;
		erg = 0.0;
		
		for(double i=intervalLeft; i<=intervalRight-dx+(dx/4); i+=dx)
		{
			int j1, j2;
			
			j1 = 0;
			
			
			while(points.get(j1).getX()<=i && j1 < points.size()-1)
			{
				j1++;
			}
			
			j2 = j1;
			
			while(points.get(j2).getX()<=i+dx && j2 < points.size()-1)
			{
				j2++;
			}
			
			hoehe = points.get(j1).getY();
			
			for(int j=j1; j<=j2; j++)
			{
				if(points.get(j).getY()<hoehe)
				{
					hoehe = points.get(j).getY();
				}
			}
			
			erg += dx*hoehe;			
		}
		
		return erg;
	}
	
	public void drawFunction(WindowAndDataProvider windowAndDataProvider, Graphics g, double domainLeft, double domainRight)
	{
		if(visible == true)
		{
    		DPoint p1, p2;
    		int j1, j2;
    		
    		p1 = new DPoint();
    		p2 = new DPoint();
    		g.setColor(color);
    		
    		j1 = 0;
    		
    		while(points.get(j1).getX()<=domainLeft && j1 < points.size()-1)
    		{
    			j1++;
    		}
    		
    		j1--;
    		
    		j2 = j1;
    		
    		while(points.get(j2).getX()<=domainRight && j2 < points.size()-1)
    		{
    			j2++;
    		}
    		
    		for(int i=j1; i<j2; i++)
    		{
    			if(points.size()-1 != i)
    			{
    				p1 = windowAndDataProvider.toCoordinationPoint(points.get(i));
    				p2 = windowAndDataProvider.toCoordinationPoint(points.get(i+1));
    			}
    			else
    			{
    				p1 = windowAndDataProvider.toCoordinationPoint(points.get(i));
    				p2 = windowAndDataProvider.toCoordinationPoint(points.get(i));
    			}
    			
    			g.drawLine((int) Math.round(p1.getX()), (int) Math.round(p1.getY()), (int) Math.round(p2.getX()), (int) Math.round(p2.getY()));
    		}
		}
	}
	
	public void drawObersumme(WindowAndDataProvider windowAndDataProvider, Graphics g)
	{
		if(drawUpperSum == true)
		{
			double hoehe;
			DPoint p1, p2;
			
			p1 = new DPoint();
			p2 = new DPoint();
			
			for(double i=intervalLeft; i<=intervalRight-dx+(dx/4); i+=dx)
			{
				int j1, j2;
				
				j1 = 0;
				
				
				while(points.get(j1).getX()<=i && j1 < points.size()-1)
				{
					j1++;
				}
				
				j2 = j1;
				
				while(points.get(j2).getX()<=i+dx && j2 < points.size()-1)
				{
					j2++;
				}
				
				hoehe = points.get(j1).getY();
				
				for(int j=j1; j<=j2; j++)
				{
					if(points.get(j).getY()>hoehe)
					{
						hoehe = points.get(j).getY();
					}
				}
				
				p1.setX(i);
				p1.setY(0);
				p2.setX(i+dx);
				p2.setY(hoehe);
				
				p1 = windowAndDataProvider.toCoordinationPoint(p1);
				p2 = windowAndDataProvider.toCoordinationPoint(p2);
				
				int[] x = new int[4];
				int[] y = new int[4];
				
				x[0] = (int) Math.round(p1.getX());
				x[1] = (int) Math.round(p1.getX());
				x[2] = (int) Math.round(p2.getX());
				x[3] = (int) Math.round(p2.getX());
				
				y[0] = (int) Math.round(p1.getY());
				y[1] = (int) Math.round(p2.getY());
				y[2] = (int) Math.round(p2.getY());
				y[3] = (int) Math.round(p1.getY());
				
				g.setColor(Color.blue);
				g.fillPolygon(x, y, x.length);
				g.setColor(Color.black);
				g.drawPolygon(x, y, x.length);
			}
		}
	}
	
	public void drawUntersumme(WindowAndDataProvider windowAndDataProvider, Graphics g)
	{
		if(drawLowerSum == true)
		{
			double hoehe;
			DPoint p1, p2;
			
			p1 = new DPoint();
			p2 = new DPoint();
			
			for(double i=intervalLeft; i<=intervalRight-dx+(dx/4); i+=dx)
			{
				int j1, j2;
				
				j1 = 0;
				
				
				while(points.get(j1).getX()<=i && j1 < points.size()-1)
				{
					j1++;
				}
				
				j2 = j1;
				
				while(points.get(j2).getX()<=i+dx && j2 < points.size()-1)
				{
					j2++;
				}
				
				hoehe = points.get(j1).getY();
				
				for(int j=j1; j<=j2; j++)
				{
					if(points.get(j).getY()<hoehe)
					{
						hoehe = points.get(j).getY();
					}
				}
				
				p1.setX(i);
				p1.setY(0);
				p2.setX(i+dx);
				p2.setY(hoehe);
				
				p1 = windowAndDataProvider.toCoordinationPoint(p1);
				p2 = windowAndDataProvider.toCoordinationPoint(p2);
				
				int[] x = new int[4];
				int[] y = new int[4];
				
				x[0] = (int) Math.round(p1.getX());
				x[1] = (int) Math.round(p1.getX());
				x[2] = (int) Math.round(p2.getX());
				x[3] = (int) Math.round(p2.getX());
				
				y[0] = (int) Math.round(p1.getY());
				y[1] = (int) Math.round(p2.getY());
				y[2] = (int) Math.round(p2.getY());
				y[3] = (int) Math.round(p1.getY());
				
				g.setColor(Color.green);
				g.fillPolygon(x, y, x.length);
				g.setColor(Color.black);
				g.drawPolygon(x, y, x.length);
			}
		}
	}
	
	public String getAufleitung()
	{
		return aufleitung;
	}
	
	public double getIntervall1()
	{
		return intervalLeft;
	}
	
	public double getIntervall2()
	{
		return intervalRight;
	}
	
	public double getSteps()
	{
		return steps;
	}
	
	public String getTerm()
	{
		return term;
	}
	
	public boolean isObersummeVisible()
	{
		return drawUpperSum;
	}
	
	public boolean isUntersummeVisible()
	{
		return drawLowerSum;
	}
	
	public boolean isVisible()
	{
		return visible;
	}
	
	public void setIntervall1(double int1)
	{
		intervalLeft = int1;
	}
	
	public void setIntervall2(double int2)
	{
		intervalRight = int2;
	}
	
	public void setObersummeVisible(boolean visible)
	{
		drawUpperSum = visible;
	}
	
	public void setSteps(double newSteps)
	{
		steps = newSteps;
	}
	
	public void setTerm(String newTerm, double domainLeft, double domainRight)
	{
		term = newTerm;
		aufleitung = Calculator.aufleiten(term);
		points = null;
		calcFunctionData(domainLeft, domainRight);
	}
	
	public void setUntersummeVisible(boolean visible)
	{
		drawLowerSum= visible;
	}
	
	public void setVisible(boolean newVisible)
	{
		visible = newVisible;
	}
}
