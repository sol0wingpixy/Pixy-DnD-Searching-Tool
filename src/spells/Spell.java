package spells;

import org.jsoup.nodes.*;
import java.util.*;
import java.io.*;
import universal.*;


public class Spell implements Serializable, Comparable<Spell>
{
	private static final long serialVersionUID = 1L;
	public IndexedItem name;
	public boolean ritual;
	public boolean concentration;
	public School school;
	public IndexedItem level;
	public boolean hasV;
	public boolean hasS;
	public boolean hasM;
	public String mComponents;
	public IndexedItem castTime;//string
	public IndexedItem duration;//string
	public String range;//string
	public String aoeSize;//string
	public AOEType aoeType;//enum
	public String attackSave;//string
	public int intSaveForced;
	public String damageEffect;//string
	public IndexedItem fullText;//string
	public String abilityText;
	public String higherLevelsText = "";
	public IndexedItem classes;
	public Spell(String n,Element el)
	{
		String tempString;
		int tempInt;
		if(ritual = n.endsWith("Ritual"))
		{
			n = n.substring(0, n.length()-7);
		}
		if(concentration = n.endsWith("Concentration"))
		{
			n = n.substring(0, n.length()-14);
		}
		
		n = n.replace("’", "'");
		
		name = new IndexedItem(n,IndexKind.SpellName);
		
		Element block = el.child(0).child(0);
		if(block.className().equals("ddb-blocked-content") || n.equals("Freedom of the Waves") || n.equals("Freedom of the Winds"))
		{
			name = new IndexedItem("skip",IndexKind.SpellName);
		}
		else
		{
			if (block.child(0).child(1).text().equals("Cantrip"))
				level = new IndexedItem(0, IndexKind.SpellLevel);
			else
				level = new IndexedItem(Integer.parseInt(block.child(0).child(1).text().substring(0, 1)),
						IndexKind.SpellLevel);
			castTime = new IndexedItem(block.child(1).child(1).text(), IndexKind.SpellCastingTime);
			range = block.child(2).child(1).text();
			if (block.child(2).child(1).childrenSize() > 0)
			{
				aoeSize = block.child(2).child(1).child(0).text();
				aoeType = AOEType.toAOEType(block.child(2).child(1).child(0).child(0).attributes().get("class"));
			}
			String rawComps = block.child(3).child(1).child(0).text();
			hasV = rawComps.contains("V");
			hasS = rawComps.contains("S");
			hasM = rawComps.contains("M");
			duration = new IndexedItem(block.child(4).child(1).text(), IndexKind.SpellDuration);
			school = School.toSchool(block.child(5).child(1).text());
			attackSave = block.child(6).child(1).text();
			switch(attackSave.substring(0,3))
			{
			case "STR":
				intSaveForced = 0;
				break;
			case "DEX":
				intSaveForced = 1;
				break;
			case "CON":
				intSaveForced = 2;
				break;
			case "INT":
				intSaveForced = 3;
				break;
			case "WIS":
				intSaveForced = 4;
				break;
			case "CHA":
				intSaveForced = 5;
				break;
			default:
				intSaveForced = -1;
			}
			damageEffect = block.child(7).child(1).text();
			fullText = new IndexedItem(el.child(0).child(2).text(),IndexKind.SpellText);
			if(fullText.getContentString().contains("At Higher Levels."))
			{
				tempInt = fullText.getContentString().indexOf("At Higher Levels.");
				abilityText = fullText.getContentString().substring(0,tempInt);
				higherLevelsText = fullText.getContentString().substring(tempInt);
				tempInt = higherLevelsText.indexOf("*");
				if(tempInt != -1)
				{
					higherLevelsText = higherLevelsText.substring(0,tempInt-1);
				}
			}
			else
			{
				abilityText = fullText.getContentString();
				tempInt = abilityText.indexOf("*");
				if(tempInt != -1)
				{
					abilityText = abilityText.substring(0,tempInt-1);
				}
			}
			tempString = fullText.getContentString();
			if (block.child(3).child(1).child(0).text().contains("*"))
				mComponents = tempString.substring(tempString.indexOf("* - (") + 5, tempString.length() - 1);
			List<Class> tempClasses = new ArrayList<Class>();
			Element classPar = el.child(1).child(2);
			if (!classPar.text().contains("Classes"))
			{
				classPar = el.child(1).child(1);
			}
			for (Element i : classPar.children())
			{
				Class c = Class.toClass(i.text());
				if (c != null)
				{
					tempClasses.add(c);
				}
			}
			classes = new IndexedItem(tempClasses, IndexKind.SpellClasses);
		}
	}

	
	public int compareTo(Spell s)
	{
		int levelDif = ((this.level.getContentInt()*100)+100)-((s.level.getContentInt()*100)+100);
		int alphaDif = this.name.getContentString().charAt(0)-s.name.getContentString().charAt(0);
		return levelDif+alphaDif;
	}
	
	public boolean equals(Spell s)
	{
		return this.name.equals(s.name);
	}
	
	public String toString()
	{
		return name.getContentString();
	}
	public String toCSVString()
	{
		return name.getContentString()+"^"+level.getContentInt()+"^"+(ritual ? 1 : 0)+"^"+school+"^"+castTime.getContentString()+"^"+range+"^"+(hasV ? "V" : "")+"^"+(hasS ? "S" : "")+"^"+(hasM ? "M" : "")+"^"+mComponents+"^"+(concentration ? 1:0)+"^"+duration.getContentString()+"^"+abilityText+"^"+higherLevelsText;            

	}
	public String outputLevel()
	{
		switch(level.getContentInt())
		{
		case 0:
			return "Cantrip";
		case 1:
			return "1st Level";
		case 2:
			return "2nd Level";
		case 3:
			return "3rd Level";
		case 4:
			return "4th Level";
		case 5:
			return "5th Level";
		case 6:
			return "6th Level";
		case 7:
			return "7th Level";
		case 8:
			return "8th Level";
		case 9:
			return "9th Level";
		default:
			return "Invalid Level";
		}
	}
}