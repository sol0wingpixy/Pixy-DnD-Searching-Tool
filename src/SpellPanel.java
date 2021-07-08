import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;

public class SpellPanel extends JPanel {
	
	public SpellPanel(Spell spell)
	{
		this.setVisible(true);
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JTextArea titleText = new JTextArea(spell.name);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weightx = 5;
		c.gridheight = 1;
		titleText.setVisible(true);
		titleText.setLineWrap(true);
		titleText.setWrapStyleWord(true);
		titleText.setEditable(false);
		titleText.setFont(new Font(Font.SANS_SERIF,Font.BOLD,15));
		add(titleText,c);
		
		JTextArea mainText = new JTextArea(spell.text);
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		c.gridwidth = 1;
		c.gridheight = 3;
		mainText.setVisible(true);
		mainText.setLineWrap(true);
		mainText.setWrapStyleWord(true);
		mainText.setEditable(false);
		mainText.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,10));
		add(mainText,c);
		
		JTextArea levelText = new JTextArea(spell.outputLevel());
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		levelText.setVisible(true);
		levelText.setLineWrap(true);
		levelText.setWrapStyleWord(true);
		levelText.setEditable(false);
		levelText.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,10));
		add(levelText,c);
		
		JTextArea schoolText = new JTextArea(spell.school.name());
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		schoolText.setVisible(true);
		schoolText.setLineWrap(true);
		schoolText.setWrapStyleWord(true);
		schoolText.setEditable(false);
		schoolText.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,10));
		add(schoolText,c);
	}

}
