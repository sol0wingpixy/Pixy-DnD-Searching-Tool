import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

public class SpellFrame extends JFrame {
	private SpellPanel spellPanel;

	public SpellFrame(Spell spell) {
		setSize(500,500);
		setVisible(true);
		setTitle(spell.name);
		spellPanel = new SpellPanel(spell);
		getContentPane().add(spellPanel);
	}
}
