package Application;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;



public class SaveLoadPanel extends JPanel
{
	private WindowAndDataProvider windowAndDataProvider;
	private JButton saveButton;
	private JButton loadButton;
	
	public SaveLoadPanel(WindowAndDataProvider newWindowProvider)
	{
		windowAndDataProvider = newWindowProvider;
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(new TitledBorder("Graph Speichern und Laden"));
		
		saveButton = new JButton("Speichern unter");
		saveButton.addActionListener(new AListener());
		add(saveButton);
		
		loadButton = new JButton("Aus Datei Laden");
		loadButton.addActionListener(new AListener());
		add(loadButton);
	}
	
	private class AListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource().equals(saveButton))
			{
				windowAndDataProvider.saveFileDialog();
			}
			else if(e.getSource().equals(loadButton))
			{
				windowAndDataProvider.loadFileDialog();
			}
		}
	}
}
