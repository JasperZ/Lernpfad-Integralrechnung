package Application;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class FunctionFrame extends JFrame
{
	private Function function;
	private JLabel functionLabel;
	private JCheckBox drawCheck;
	private JCheckBox drawUpperSumCheck;
	private JCheckBox drawLowerSumCheck;
	private JLabel intervallLabel;
	private JLabel stepsLabel;
	private JLabel upperSumLabel;
	private JLabel lowerSumLabel;
	private JLabel integralLabel;
	private JTextField intervallA;
	private JTextField intervallB;
	private JTextField steps;
	private JTextField functionInput;
	private JButton editButton;
	private JButton cancelButton;
	private JPanel functionEditPanel;
	private JPanel drawSettingsPanel;
	private JPanel dataPanel;
	private JPanel outputPanel;
	private WindowAndDataProvider windowAndDataProvider;
	private JSlider slider;

	public FunctionFrame(WindowAndDataProvider newWindowProvider, Function newfunction)
	{
		function = newfunction;
		windowAndDataProvider = newWindowProvider;
		
		setTitle("Funktion: "+function.getTerm());
		setLayout(new GridLayout(0, 1));
		addWindowListener(new WListener());
		setVisible(true);
		setAlwaysOnTop(true);
		
		functionEditPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		functionEditPanel.setBorder(new TitledBorder("Funktion"));
		
		functionLabel = new JLabel("f(x) =");
		functionEditPanel.add(functionLabel);
		
		functionInput = new JTextField(function.getTerm());
		functionInput.setEditable(false);
		functionInput.setColumns(20);
		functionInput.addKeyListener(new KListener());
		functionEditPanel.add(functionInput);
		
		editButton = new JButton("Funktion Editieren");
		editButton.addActionListener(new Listener());
		functionEditPanel.add(editButton);
		
		cancelButton = new JButton("Abbrechen");
		cancelButton.addActionListener(new Listener());
		cancelButton.setVisible(false);
		functionEditPanel.add(cancelButton);
		
		drawSettingsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		drawSettingsPanel.setBorder(new TitledBorder("Zeichen-Einstellungen"));

		drawCheck = new JCheckBox();
		drawCheck.setText("Funktion zeichnen");
		drawCheck.setSelected(function.isVisible());
		drawCheck.addActionListener(new Listener());
		drawSettingsPanel.add(drawCheck);
		
		drawUpperSumCheck = new JCheckBox();
		drawUpperSumCheck.setText("Obersumme anzeigen");
		drawUpperSumCheck.setSelected(function.isObersummeVisible());
		drawUpperSumCheck.addActionListener(new Listener());
		drawUpperSumCheck.setForeground(Color.blue);
		drawSettingsPanel.add(drawUpperSumCheck);
		
		drawLowerSumCheck = new JCheckBox();
		drawLowerSumCheck.setText("Untersumme anzeigen");
		drawLowerSumCheck.setSelected(function.isUntersummeVisible());
		drawLowerSumCheck.addActionListener(new Listener());
		drawLowerSumCheck.setForeground(Color.green);
		drawSettingsPanel.add(drawLowerSumCheck);
		
		dataPanel = new JPanel(new GridLayout(0, 3));
		dataPanel.setBorder(new TitledBorder("Daten"));
		
		intervallLabel = new JLabel("Intervall:");
		dataPanel.add(intervallLabel);
		
		intervallA = new JTextField("-2");
		intervallA.setText(String.valueOf(function.getIntervall1()));
		intervallA.addKeyListener(new KListener());
		dataPanel.add(intervallA);
		
		intervallB = new JTextField();
		intervallB.setText(String.valueOf(function.getIntervall2()));
		intervallB.addKeyListener(new KListener());
		dataPanel.add(intervallB);
		
		stepsLabel = new JLabel("Schritte:");
		dataPanel.add(stepsLabel);
		
		steps = new JTextField("10");
		steps.setText(String.valueOf(function.getSteps()));
		steps.addKeyListener(new KListener());
		dataPanel.add(steps);
		
		slider = new JSlider(1, 1000, 10);
		slider.addChangeListener(new CListener());
		dataPanel.add(slider);
		slider.setValue(10);
		
		outputPanel = new JPanel(new GridLayout(0, 1));
		outputPanel.setBorder(new TitledBorder("Ausgabe"));
		
		upperSumLabel = new JLabel("Obersumme: ");
		upperSumLabel.setForeground(Color.blue);
		outputPanel.add(upperSumLabel);
		
		lowerSumLabel = new JLabel("Untersumme: ");
		lowerSumLabel.setForeground(Color.green);
		outputPanel.add(lowerSumLabel);
		
		integralLabel = new JLabel("Integral: ");
		outputPanel.add(integralLabel);
		
		add(functionEditPanel);
		add(drawSettingsPanel);
		add(dataPanel);
		add(outputPanel);
		
		pack();
		
		updateData();
	}
	
	public void updateData()
	{		
		try
		{
			function.setIntervall1(Double.parseDouble(intervallA.getText()));
			intervallA.setBackground(Color.white);
		}
		catch (NumberFormatException e)
		{
			intervallA.setBackground(Color.red);
		}
		
		try
		{
			function.setIntervall2(Double.parseDouble(intervallB.getText()));
			intervallB.setBackground(Color.white);
		}
		catch (NumberFormatException e)
		{
			intervallB.setBackground(Color.red);
		}
		
		try
		{
			function.setSteps(Double.parseDouble(steps.getText()));
			slider.setValue((int)Double.parseDouble(steps.getText()));
			steps.setBackground(Color.white);
		}
		catch (NumberFormatException e)
		{
			steps.setBackground(Color.red);
		}
		
		windowAndDataProvider.graphRepaint(true);
		windowAndDataProvider.updateShownGraphData();
		
		upperSumLabel.setText("Obersumme: "+String.valueOf(function.calcObersumme()));
		lowerSumLabel.setText("Untersumme: "+String.valueOf(function.calcUntersumme()));
		integralLabel.setText("Integral: "+String.valueOf(function.calcIntegral()));
	}
	
	public void closeWindow()
	{
		windowAndDataProvider.removeFunction(function);
		windowAndDataProvider.removeFunctionFrame(this);
		windowAndDataProvider.graphRepaint(true);
		windowAndDataProvider.updateShownGraphData();
	}
	
	private class Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Object source = e.getSource();
			
			if(source.equals(drawCheck))
			{
				function.setVisible(((JCheckBox)source).isSelected());
			}
			else if(source.equals(drawUpperSumCheck))
			{
				function.setObersummeVisible(((JCheckBox)source).isSelected());
			}
			else if(source.equals(drawLowerSumCheck))
			{
				function.setUntersummeVisible(((JCheckBox)source).isSelected());
			}
			else if(source.equals(editButton))
			{
				if(functionInput.isEditable() == false)
				{
    				functionInput.setEditable(true);
    				editButton.setText("Speichern");
    				cancelButton.setVisible(true);
				}
				else
				{
					function.setTerm(functionInput.getText(), windowAndDataProvider.getGraphData().getDomainLeft(), windowAndDataProvider.getGraphData().getDomainRight());
					setTitle("Funktion: "+function.getTerm());
					functionInput.setEditable(false);
					editButton.setText("Funktion Editieren");
					cancelButton.setVisible(false);
					updateData();
				}
			}
			else if(source.equals(cancelButton))
			{
				functionInput.setText(function.getTerm());
				functionInput.setEditable(false);
				editButton.setText("Funktion Editieren");
				editButton.setEnabled(true);
				cancelButton.setVisible(false);
				functionInput.setBackground(Color.white);
			}
			
			windowAndDataProvider.graphRepaint(true);
		}
	}
	
	private class WListener implements WindowListener
	{
		public void windowActivated(WindowEvent e)
		{
			
		}

		public void windowClosed(WindowEvent e)
		{
			
		}

		public void windowClosing(WindowEvent e)
		{
			closeWindow();
		}

		public void windowDeactivated(WindowEvent e)
		{
			
		}

		public void windowDeiconified(WindowEvent e)
		{
			
		}

		public void windowIconified(WindowEvent e)
		{
			
		}

		public void windowOpened(WindowEvent e)
		{
			
		}
		
	}
	
	private class KListener implements KeyListener
	{

		public void keyPressed(KeyEvent e)
		{
			;
		}

		public void keyReleased(KeyEvent e)
		{
			if(e.getSource().equals(functionInput))
			{
				if(Function.inputIsFunction(functionInput.getText()))
				{
					functionInput.setBackground(Color.white);
					editButton.setEnabled(true);
				}
				else
				{
					functionInput.setBackground(Color.red);
					editButton.setEnabled(false);
				}
			}
			else
			{
				updateData();
			}
		}

		public void keyTyped(KeyEvent e)
		{
			;
		}
	}
	
	private class CListener implements ChangeListener
	{
		public void stateChanged(ChangeEvent e)
		{
			steps.setText(String.valueOf(slider.getValue()));
			updateData();
		}
	}
}
