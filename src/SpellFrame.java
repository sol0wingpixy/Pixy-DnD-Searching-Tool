import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

public class SpellFrame extends JFrame {
	private SpellPanel spellPanel;

	public SpellFrame(Spell spell) {
		setSize(500,500);
		setVisible(true);
		spellPanel = new SpellPanel(spell);
		spellPanel.setBounds(0, 0, WIDTH, HEIGHT);
		getContentPane().add(spellPanel);
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				spellPanel.setBounds(0, 0, WIDTH, HEIGHT);
			}
		});
	}
}
