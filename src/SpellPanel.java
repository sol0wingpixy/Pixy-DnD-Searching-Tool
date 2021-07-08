import javax.swing.*;

public class SpellPanel extends JPanel {
	
	public SpellPanel(Spell spell)
	{
		setSize(500,500);
		setVisible(true);
		JTextArea mainText = new JTextArea(spell.text);
		add(mainText);
		mainText.setBounds(10,50,300,300);
		mainText.setVisible(true);
		mainText.setLineWrap(true);
		mainText.setWrapStyleWord(true);
		mainText.setEditable(false);
		JTextArea titleText = new JTextArea(spell.name);
		add(titleText);
		titleText.setBounds(100,10,300,90);
		titleText.setVisible(true);
		titleText.setLineWrap(true);
		titleText.setWrapStyleWord(true);
		titleText.setEditable(false);
		System.out.println(titleText.getBounds());
	}

}
