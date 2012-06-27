package Application;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;




public class MainFrame extends JFrame
{
	private JPanel graphPanel;
	private JPanel settingsAndStoragePanel;
	private WindowAndDataProvider windowAndDataProvider;
	
	public MainFrame(WindowAndDataProvider newWindowProvider)
	{
		windowAndDataProvider = newWindowProvider;
		
		setTitle("Lernpfad: Integralrechnung");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(480, 500));
		setLayout(new BorderLayout());
		setVisible(true);
		
		settingsAndStoragePanel = new JPanel();
		settingsAndStoragePanel.setLayout(new GridLayout(0,	1));
		settingsAndStoragePanel.add(windowAndDataProvider.getSettingPanel());
		settingsAndStoragePanel.add(windowAndDataProvider.getSaveLoadPanel());
		
		graphPanel = new JPanel();
		graphPanel.setLayout(new GridLayout(1, 1));
		graphPanel.setBorder(new TitledBorder("Graph"));
		
		graphPanel.add(windowAndDataProvider.getGraph());
		
		add(BorderLayout.NORTH, windowAndDataProvider.getInputPanel());
		add(BorderLayout.CENTER, graphPanel);
		add(BorderLayout.SOUTH, settingsAndStoragePanel);
		
		pack();
	}
}