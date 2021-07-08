import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class MainMenuFrame extends JFrame {
	
	public MainMenuFrame(ArrayList<Spell> spellList) {
		setSize(500,500);
		setVisible(true);
		setTitle("Pixy's Spell Searcher");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JList<Spell> displayList = new JList(spellList.toArray());
		JScrollPane scrollPane = new JScrollPane(displayList);
		add(scrollPane);
		scrollPane.setBounds(10,10,400,400);
		MouseListener mouseListener = new MouseAdapter() {
		     public void mouseClicked(MouseEvent e) {
		         if (e.getClickCount() == 2) {
		             int index = displayList.locationToIndex(e.getPoint());
		             new SpellFrame(displayList.getModel().getElementAt(index));
		          }
		     }
		 };
		displayList.addMouseListener(mouseListener);
		System.out.println(getComponents()[0]);
	}
}
