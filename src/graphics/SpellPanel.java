package graphics;
import java.awt.Font;

import javax.swing.*;

import spells.Spell;

public class SpellPanel extends JPanel {
	
	JPanel listPane;
	
	public SpellPanel(Spell spell)
	{
		this.setVisible(true);
		listPane = new JPanel();
		listPane.setLayout(new BoxLayout(listPane,BoxLayout.Y_AXIS));
		
		setup(spell);
		
		this.add(listPane);
	}
	
	public void setup(Spell spell)
	{
		listPane.removeAll();
		
		JTextArea titleText = new JTextArea(spell.name.getContentString());
		titleText.setVisible(true);
		titleText.setLineWrap(true);
		titleText.setWrapStyleWord(true);
		titleText.setEditable(false);
		titleText.setFont(new Font(Font.SANS_SERIF,Font.BOLD,15));
		listPane.add(titleText);
		
		JTextArea levelText = new JTextArea(spell.outputLevel());
		levelText.setVisible(true);
		levelText.setLineWrap(true);
		levelText.setWrapStyleWord(true);
		levelText.setEditable(false);
		levelText.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,10));
		listPane.add(levelText);
		
		JTextArea schoolText = new JTextArea(spell.school.name());
		schoolText.setVisible(true);
		schoolText.setLineWrap(true);
		schoolText.setWrapStyleWord(true);
		schoolText.setEditable(false);
		schoolText.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,10));
		listPane.add(schoolText);
		
		JTextArea mainText = new JTextArea(spell.text);
		mainText.setVisible(true);
		mainText.setLineWrap(true);
		mainText.setWrapStyleWord(true);
		mainText.setEditable(false);
		mainText.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,10));
		listPane.add(mainText);
	}

}
