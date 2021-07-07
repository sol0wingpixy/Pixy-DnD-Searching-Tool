import javax.swing.*;

public class SpellFrame extends JFrame {
	
	public SpellFrame(Spell spell)
	{
		setSize(400,400);
		setVisible(true);
		setTitle(spell.name);
		JTextArea text = new JTextArea(spell.text);
		add(text);
		text.setBounds(10,10,300,300);
		text.setVisible(true);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
	}

}
