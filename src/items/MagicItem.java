package items;

import org.jsoup.*;
import org.jsoup.nodes.*;

import spells.*;

import java.util.*;
import java.io.*;
import universal.*;

public class MagicItem implements Serializable, Comparable<MagicItem>
{
	
	private static final long serialVersionUID = 1L;
	public IndexedItem name;
	public IndexedItem rarity;
	public IndexedItem attunement;
	public List<Spell> spellList;
	public String text;
	public MagicItem(String n,Element el,List<Spell> masterSpellList)
	{
		spellList = new ArrayList<Spell>();
		String tempString;
		int tempInt;
		n = n.replace("’", "'");
		n = n.replace("Legacy This doesn't reflect the latest rules and lore. Learn More", "(Legacy)");
		name = new IndexedItem(n,IndexKind.ItemName);
		Element block = el.child(0).child(0);
		tempString = block.child(0).text();
		attunement = new IndexedItem(tempString.contains("attunement"),IndexKind.ItemAttunement);
		rarity = new IndexedItem(Rarity.toRarity(tempString),IndexKind.ItemRarity);
		text = "";
		tempString = "";
		for(int i = 1; i < block.childrenSize()-1; i++)
		{
			text+=block.child(i).text() + " ";
			tempString += block.child(i).toString();
		}
		tempInt = tempString.indexOf("spell-tooltip");
		List<String> spellNames = new ArrayList<String>();
		while(tempInt != -1)
		{
			tempString = tempString.substring(tempInt + 13);
			spellNames.add(tempString.substring(tempString.indexOf('>')+1,tempString.indexOf('<')).replace("’", "'"));
			tempInt = tempString.indexOf("spell-tooltip");
		}
		int verifyCount = 0;
		for(Spell s : masterSpellList)
		{
			for(int i = 0; i < spellNames.size(); i++)
			{
				if(s.name.getContentString().toLowerCase().equals(spellNames.get(i).toLowerCase()))
				{
					if(!this.spellList.contains(s))
						this.spellList.add(s);
					verifyCount++;
				}
			}
		}
		if(verifyCount != spellNames.size())
		{
			System.out.println(name + " has bad spells");
		}
		Element notes = block.child(block.childrenSize()-1);
	}
	
	public boolean hasSpell(String spellName)
	{
		spellName = spellName.toLowerCase();
		for(Spell s:spellList)
		{
			if(s.name.getContentString().toLowerCase().equals(spellName))
				return true;
		}
		return false;
	}
	
	public int compareTo(MagicItem o)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String toString()
	{
		return name.getContentString() + ", " + rarity.getContentRarity();
	}
}
