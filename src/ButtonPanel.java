import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {
	public ButtonPanel(ActionListener listener)
	{
		this.setVisible(true);
		JPanel listPane = new JPanel();
		listPane.setLayout(new BoxLayout(listPane,BoxLayout.Y_AXIS));
		
		JButton testButton = new JButton("test");
		testButton.addActionListener(listener);
		listPane.add(testButton);
		
		this.add(listPane);
	}
}
