package graphics;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import spells.Spell;
import graphics.SpellPanel;

public class MainMenuFrame extends JFrame implements ActionListener {
	
	JPanel listPane;
	JList<Spell> displayList;
	
	public MainMenuFrame(ArrayList<Spell> spellList) {
		
		listPane = new JPanel();
		listPane.setLayout(new BoxLayout(listPane,BoxLayout.X_AXIS));
		
		displayList = new JList(spellList.toArray());
		
		JScrollPane scrollPane = new JScrollPane(displayList);
		listPane.add(scrollPane);
		
		SpellPanel spellPanel = new SpellPanel(displayList.getModel().getElementAt(0));
		listPane.add(spellPanel);
		
		ButtonPanel buttonPanel = new ButtonPanel(this);
		listPane.add(buttonPanel);
		
		MouseListener mouseListener = new MouseAdapter() {
		     public void mouseClicked(MouseEvent e) {
	             int index = displayList.locationToIndex(e.getPoint());
		         if (e.getClickCount() == 2) {
		             listPane.remove(1);
		        	 listPane.add(new SpellPanel(displayList.getModel().getElementAt(index)));
		          }
		         if (e.getClickCount() == 1) {
		        	 System.out.println(getComponentCount());
		         }
		     }
		 };
		displayList.addMouseListener(mouseListener);
		
		this.add(listPane);
		
		setVisible(true);
		setSize(900,500);
		setTitle("Pixy's Spell Searcher");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
   	 	System.out.println(getComponentCount());
	}
	public void actionPerformed(ActionEvent e)
	{
		displayList.removeAll();
		this.repaint();
	}
}
