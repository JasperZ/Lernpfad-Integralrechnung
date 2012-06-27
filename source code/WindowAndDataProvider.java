package Application;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;

public class WindowAndDataProvider
{
	private ArrayList<FunctionFrame> functionFrames;
	private FunctionInputPanel functionInputPanel;
	private Graph graph;
	private GraphData graphData;
	private GraphSettingPanel graphSettingPanel;
	private MainFrame mainFrame;
	private SaveLoadPanel saveLoadPanel;
	
	public WindowAndDataProvider()
	{
		graphData = new GraphData();
		functionFrames = new ArrayList<FunctionFrame>();
		
		graph = new Graph(this);
		functionInputPanel = new FunctionInputPanel(this);
		graphSettingPanel = new GraphSettingPanel(this);
		saveLoadPanel = new SaveLoadPanel(this);
		mainFrame = new MainFrame(this);
	}
	
	public void addFunctionFrame(String functionTerm)
	{
		Function function = graphData.addFunction(functionTerm);
		functionFrames.add(new FunctionFrame(this, function));
	}
	
	public DPoint toCoordinationPoint(DPoint p)
	{
		DPoint pNew = new DPoint();
		
		pNew.setX(graphData.getX0() + p.getX()*graphData.getXFactor());
		pNew.setY(graphData.getY0() - p.getY()*graphData.getYFactor());
		
		return pNew;
	}
	
	public DPoint toRealPoint(DPoint p)
	{
		DPoint pNew = new DPoint();
		
		pNew.setX((graphData.getX0()-p.getX())/graphData.getXFactor()*(-1));
		pNew.setY((graphData.getY0()-p.getY())/graphData.getYFactor());
		
		return pNew;
	}

	public Graph getGraph()
	{
		return graph;
	}
	
	public GraphData getGraphData()
	{
		return graphData;
	}

	public FunctionInputPanel getInputPanel()
	{
		return functionInputPanel;
	}
	
	public SaveLoadPanel getSaveLoadPanel()
	{
		return saveLoadPanel;
	}

	public GraphSettingPanel getSettingPanel()
	{
		return graphSettingPanel;
	}

	public void graphRepaint(boolean dataChanged)
	{
		if(dataChanged)
		{
			for(int i=0; i<graphData.getNumberOfFunctions(); i++)
			{
				graphData.getFunction(i).calcFunctionData(graphData.getDomainLeft(), graphData.getDomainRight());
			}
		}

		graph.repaint();
	}
	
	public void loadFileDialog()
	{
		JFileChooser fc = new JFileChooser();
		
		
		if(fc.showOpenDialog(mainFrame) != JFileChooser.CANCEL_OPTION)
		{
			try
			{
				GraphData data;
				data = (GraphData) ObjectSerializer.readFromFile(fc.getSelectedFile().getPath());
				
				if(data != null)
				{
					for(int i=0; i<functionFrames.size(); i++)
					{
						functionFrames.get(i).dispose();
					}
					
					graphData = data;
					
					for(int i=0; i<graphData.getNumberOfFunctions(); i++)
					{
						functionFrames.add(new FunctionFrame(this, graphData.getFunction(i)));
					}
					
					updateShownGraphData();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void saveFileDialog()
	{
		JFileChooser fc = new JFileChooser();
		
		
		if(fc.showSaveDialog(mainFrame) != JFileChooser.CANCEL_OPTION)
		{
			try
			{
				ObjectSerializer.writeToFile(fc.getSelectedFile().getPath(), graphData);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void removeFunction(Function function)
	{
		graphData.removeFunction(function);
	}

	public void removeFunctionFrame(FunctionFrame functionFrame)
	{
		functionFrames.remove(functionFrame);
	}

	public void updateShownGraphData()
	{
		graphSettingPanel.setTextFields(graphData.getCodomainLeft(), graphData.getCodomainRight(), graphData.getDomainLeft(), graphData.getDomainRight());
	}
}
