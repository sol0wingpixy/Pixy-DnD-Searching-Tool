import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class MainMenuFrame extends JFrame {
	
	public MainMenuFrame(ArrayList<Spell> spellList) {

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JList<Spell> displayList = new JList(spellList.toArray());
		
		JScrollPane scrollPane = new JScrollPane(displayList);
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 1;
		c.fill = GridBagConstraints.VERTICAL;
		add(scrollPane,c);
		
		SpellPanel spellPanel = new SpellPanel(displayList.getModel().getElementAt(0));
		c.gridx = 1;
		add(spellPanel,c);
		
		MouseListener mouseListener = new MouseAdapter() {
		     public void mouseClicked(MouseEvent e) {
	             int index = displayList.locationToIndex(e.getPoint());
		         if (e.getClickCount() == 2) {
		             new SpellFrame(displayList.getModel().getElementAt(index));
		          }
		         if (e.getClickCount() == 1) {
		        	 System.out.println(getComponentCount());
		         }
		     }
		 };
		displayList.addMouseListener(mouseListener);
		setVisible(true);
		setSize(900,500);
		setTitle("Pixy's Spell Searcher");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
   	 	System.out.println(getComponentCount());
	}
}
