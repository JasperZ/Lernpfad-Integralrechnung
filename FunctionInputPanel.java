package Application;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;



public class FunctionInputPanel extends JPanel
{
	private JLabel functionLabel;
	private JTextField functionTextField;
	private JButton addButton;
	private WindowAndDataProvider windowAndDataProvider;
	
	public FunctionInputPanel(WindowAndDataProvider newWindowProvider)
	{
		windowAndDataProvider = newWindowProvider;
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(new TitledBorder("Funktion"));
		
		functionLabel = new JLabel("f(x) =");
		add(functionLabel);
		
		functionTextField = new JTextField();
		functionTextField.setColumns(20);
		functionTextField.addKeyListener(new KListener());
		add(functionTextField);
		
		addButton = new JButton("Funktion hinzufügen");
		addButton.setEnabled(false);
		addButton.addActionListener(new AListener());
		add(addButton);
	}
	
	private class KListener implements KeyListener
	{
		public void keyPressed(KeyEvent e)
		{
			;
		}

		public void keyReleased(KeyEvent e)
		{
			if(Function.inputIsFunction(functionTextField.getText()))
			{
				addButton.setEnabled(true);
				functionTextField.setBackground(Color.white);
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					windowAndDataProvider.addFunctionFrame(functionTextField.getText());
					functionTextField.setText("");
					functionTextField.setBackground(Color.white);
				}
			}
			else
			{
				addButton.setEnabled(false);
				functionTextField.setBackground(Color.red);
			}
		}

		public void keyTyped(KeyEvent e)
		{
			;
		}
	}
	
	private class AListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			windowAndDataProvider.addFunctionFrame(functionTextField.getText());
			functionTextField.setText("");
			functionTextField.setBackground(Color.white);
		}
	}
}
