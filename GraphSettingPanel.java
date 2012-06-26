package Application;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;



public class GraphSettingPanel extends JPanel
{
	private JLabel domainLabel;
	private JLabel codomainLabel;
	private JTextField domainLeftTextField;
	private JTextField domainRightTextField;
	private JTextField codomainLeftTextField;
	private JTextField codomainRightTextField;
	private WindowAndDataProvider windowAndDataProvider;
	
	public GraphSettingPanel(WindowAndDataProvider newWindowProvider)
	{
		windowAndDataProvider = newWindowProvider;
		
		setLayout(new GridLayout(2, 3));
		setBorder(new TitledBorder("Graph-Einstellungen"));
		
		domainLabel = new JLabel("Definitionsbereich:");
		add(domainLabel);
		
		domainLeftTextField = new JTextField("-4");
		domainLeftTextField.addKeyListener(new KListener());
		add(domainLeftTextField);
		
		domainRightTextField = new JTextField("4");
		domainRightTextField.addKeyListener(new KListener());
		add(domainRightTextField);
		
		codomainLabel = new JLabel("Wertebereich:");
		add(codomainLabel);
		
		codomainLeftTextField = new JTextField("-4");
		codomainLeftTextField.addKeyListener(new KListener());
		add(codomainLeftTextField);
		
		codomainRightTextField = new JTextField("4");
		codomainRightTextField.addKeyListener(new KListener());
		add(codomainRightTextField);
		
		setGraphData();
	}
	
	public void setTextFields(double nwb1, double nwb2, double ndb1, double ndb2)
	{
		codomainLeftTextField.setText(String.valueOf(nwb1));
		codomainRightTextField.setText(String.valueOf(nwb2));
		domainLeftTextField.setText(String.valueOf(ndb1));
		domainRightTextField.setText(String.valueOf(ndb2));
	}
	
	public void setGraphData()
	{
		if(codomainLeftTextField.getBackground().equals(Color.white) &&  codomainRightTextField.getBackground().equals(Color.white) && domainLeftTextField.getBackground().equals(Color.white) && domainRightTextField.getBackground().equals(Color.white))
		{
    		windowAndDataProvider.getGraphData().setDomainLeft(Double.parseDouble(domainLeftTextField.getText()));
    		windowAndDataProvider.getGraphData().setDomainRight(Double.parseDouble(domainRightTextField.getText()));
    		windowAndDataProvider.getGraphData().setCodomainLeft(Double.parseDouble(codomainLeftTextField.getText()));
    		windowAndDataProvider.getGraphData().setCodomainRight(Double.parseDouble(codomainRightTextField.getText()));
		}
	}
	
	public class KListener implements KeyListener
	{
		public void keyPressed(KeyEvent e)
		{
			;
		}

		public void keyReleased(KeyEvent e)
		{
			try
			{
				Double.parseDouble(((JTextField)e.getSource()).getText());
				((JTextField)e.getSource()).setBackground(Color.white);
				setGraphData();
				windowAndDataProvider.graphRepaint(true);
			}
			catch(NumberFormatException ee)
			{
				((JTextField)e.getSource()).setBackground(Color.red);
			}
		}
		
		public void keyTyped(KeyEvent e)
		{
			;
		}
	}
}
