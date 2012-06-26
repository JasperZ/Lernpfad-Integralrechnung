package Application;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;

import javax.swing.JPanel;



public class Graph extends JPanel
{
	private WindowAndDataProvider windowAndDataProvider;
	private boolean firstClick;
	
	public Graph(WindowAndDataProvider newWindowProvider)
	{
		windowAndDataProvider = newWindowProvider;
		
		addMouseMotionListener(new MMListener());
		addMouseWheelListener(new MWListener());
		addMouseListener(new MListener());
		
		setBackground(Color.white);
		setDoubleBuffered(true);
	}
	
	private int getDrawWidth()
	{
		return getWidth()-2;
	}
	
	private int getDrawHeight()
	{
		return getHeight()-2;
	}
	
	public void paint(Graphics g)
	{
		GraphData data = windowAndDataProvider.getGraphData();
		
		super.paint(g);
		updateData();
		drawCoordinationSystem(g);
		
		for(int i=0; i<data.getNumberOfFunctions(); i++)
		{
			data.getFunction(i).drawObersumme(windowAndDataProvider, g);
			data.getFunction(i).drawUntersumme(windowAndDataProvider, g);
		}
		
		for(int i=0; i<data.getNumberOfFunctions(); i++)
		{
			data.getFunction(i).drawFunction(windowAndDataProvider, g, data.getDomainLeft(), data.getDomainRight());
		}
	}
	
	public void updateData()
	{
		GraphData data = windowAndDataProvider.getGraphData();
		data.setXFactor(getDrawWidth()/(data.getDomainRight()-data.getDomainLeft()));
		data.setYFactor(getDrawHeight()/(data.getCodomainRight()-data.getCodomainLeft()));
		
		data.setX0(getDrawWidth()-(data.getDomainRight()*data.getXFactor()));
		data.setY0(data.getCodomainRight()*data.getYFactor());
	}
	
	public void drawCoordinationSystem(Graphics g)
	{
		GraphData data = windowAndDataProvider.getGraphData();
		g.setColor(Color.cyan);
		
		g.setColor(data.getNetColor());
		
		DPoint p1 = new DPoint();
		DPoint p2 = new DPoint();
		
		for(double i=0; i<=data.getDomainRight(); i++)
		{
			if(i!=0)
			{
    			p1.setX(i);
    			p1.setY(data.getCodomainLeft());
    			p2.setX(i);
    			p2.setY(data.getCodomainRight());
    			
    			p1 = windowAndDataProvider.toCoordinationPoint(p1);
    			p2 = windowAndDataProvider.toCoordinationPoint(p2);

    			g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
			}
		}
		
		for(double i=0; i>=data.getDomainLeft(); i--)
		{
			if(i!=0)
			{
    			p1.setX(i);
    			p1.setY(data.getCodomainLeft());
    			p2.setX(i);
    			p2.setY(data.getCodomainRight());
    			
    			p1 = windowAndDataProvider.toCoordinationPoint(p1);
    			p2 = windowAndDataProvider.toCoordinationPoint(p2);

    			g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
			}
		}
		
		for(double i=0; i<=data.getCodomainRight(); i++)
		{
			if(i!=0)
			{
    			p1.setX(data.getDomainLeft());
    			p1.setY(i);
    			p2.setX(data.getDomainRight());
    			p2.setY(i);
    			
    			p1 = windowAndDataProvider.toCoordinationPoint(p1);
    			p2 = windowAndDataProvider.toCoordinationPoint(p2);
    			
    			g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
			}
		}
		
		for(double i=0; i>=data.getCodomainLeft(); i--)
		{
			if(i!=0)
			{
    			p1.setX(data.getDomainLeft());
    			p1.setY(i);
    			p2.setX(data.getDomainRight());
    			p2.setY(i);
    			
    			p1 = windowAndDataProvider.toCoordinationPoint(p1);
    			p2 = windowAndDataProvider.toCoordinationPoint(p2);
    			
    			g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
			}
		}

		g.setColor(data.getCrossColor());
		
		//Koordinatenkreuz
		g.drawLine((int) data.getX0(), 0, (int) data.getX0(), getDrawHeight());
		g.drawLine(0, (int) data.getY0(), getDrawWidth(), (int) data.getY0());
		
		//Koordinatenpfeile
		p1.setX(data.getX0());
		p1.setY(0);
		
		p2.setX(data.getX0()-5);
		p2.setY(10);
		
		g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
		
		p1.setX(data.getX0());
		p1.setY(0);
		
		p2.setX(data.getX0()+5);
		p2.setY(10);
		
		g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
		
		p1.setX(getDrawWidth());
		p1.setY(data.getY0());
		
		p2.setX(getDrawWidth()-10);
		p2.setY(data.getY0()-5);
		
		g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
		
		p1.setX(getDrawWidth());
		p1.setY(data.getY0());
		
		p2.setX(getDrawWidth()-10);
		p2.setY(data.getY0()+5);
		
		g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
		
		//Koordinatenbezeichnung
		p1.setX(getDrawWidth()-15);
		p1.setY(data.getY0()+15);
		
		p2.setX(data.getX0()-15);
		p2.setY(0+15);
		
		g.drawString("X", (int) p1.getX(), (int) p1.getY());
		g.drawString("Y", (int) p2.getX(), (int) p2.getY());
	}
	
	public class MMListener extends MouseMotionAdapter
	{
		double xx;
		double yy;
		
		public MMListener()
		{
			xx = 0;
			yy = 0;
			firstClick = true;
		}

		public void mouseDragged(MouseEvent e)
		{
			GraphData data = windowAndDataProvider.getGraphData();
			DPoint p1 = new DPoint(e.getX(), e.getY());
			DPoint p2 = new DPoint(xx, yy);
			
			p1 = windowAndDataProvider.toRealPoint(p1);
			
			if(firstClick == true)
			{
				xx = p1.getX();
				yy = p1.getY();
				firstClick = false;
			}
			else
			{
    			double xdiff, ydiff;
    			
    			xdiff = p1.getX() - p2.getX();
    			ydiff = p1.getY() - p2.getY();
    			
    			data.setDomainLeft(data.getDomainLeft() - xdiff);
    			data.setDomainRight(data.getDomainRight() - xdiff);
    			data.setCodomainLeft(data.getCodomainLeft() - ydiff);
    			data.setCodomainRight(data.getCodomainRight() - ydiff);
    			
    			windowAndDataProvider.updateShownGraphData();
    			windowAndDataProvider.graphRepaint(true);
			}
		}
	}
	
	public class MListener extends MouseAdapter
	{
		public void mouseReleased(MouseEvent e)
		{
			firstClick = false;
		}

		public void mousePressed(MouseEvent e)
		{
			firstClick = true;
		}
	}
	
	public class MWListener extends MouseAdapter
	{
		public void mouseWheelMoved(MouseWheelEvent e)
		{
			GraphData data = windowAndDataProvider.getGraphData();
			double x = 0;
			DPoint p = new DPoint(e.getX(), e.getY());
			
			p = windowAndDataProvider.toRealPoint(p);
			
			if(e.getWheelRotation() == -1)
			{
				x = -(data.getDomainRight()-data.getDomainLeft())/10;
			}
			else
			{
				x = (data.getDomainRight()-data.getDomainLeft())/10;
			}
			
			if(data.getDomainLeft() - x < data.getDomainRight() + x && data.getCodomainLeft() + x < data.getCodomainRight())
			{
        		data.setDomainLeft(data.getDomainLeft() - x);
        		data.setDomainRight(data.getDomainRight() + x);
        		data.setCodomainLeft(data.getCodomainLeft() - x);
        		data.setCodomainRight(data.getCodomainRight() + x);
			}
			
			windowAndDataProvider.updateShownGraphData();
			windowAndDataProvider.graphRepaint(true);
		}
	}
}