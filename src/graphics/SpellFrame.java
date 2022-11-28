package graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

import spells.Spell;

public class SpellFrame extends JFrame {
	private SpellPanel spellPanel;

	public SpellFrame(Spell spell) {
		setSize(500,500);
		setVisible(true);
		setTitle(spell.name.getContentString());
		spellPanel = new SpellPanel(spell);
		getContentPane().add(spellPanel);
	}
}
