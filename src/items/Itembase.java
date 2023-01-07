package items;
import org.jsoup.*;
import org.jsoup.nodes.*;

import universal.*;

import java.io.*;
import java.util.*;

import spells.*;

public class Itembase
{
	private static final String FILENAME = "ItemDatabaseSerial.txt";
	private static int skippedItems;
	
	private static Scanner input;
	private static List<MagicItem> masterItemList;
	private static List<Spell> spellList;
	
	
	public static void main(String[] args)
	{
		spellList = Spellbase.getMasterSpellList();
		masterItemList = new ArrayList<MagicItem>();
		//Reading new Data:
		
		input = new Scanner(System.in);
		//System.out.println("Read New Data? (y/n)");
		if(/*input.nextLine().equals("y")*/true)
		{
			for(int i = 1; i <= 40; i++)
			{
				extractItems(i,masterItemList);
			}
			outputToFile(masterItemList);
		}
		else
		{
			masterItemList=readFromFile();
		}
		
		
		List<MagicItem> outList = new ArrayList<MagicItem>();
		//outList.addAll(masterItemList);

		outList = addAllSpell(outList,"Magic Missile");
		
		Collections.sort(outList, Comparators.sortItemByRarity());
		
		Collections.sort(outList);
		printItems(outList);
		
	}
	
	private static void printItems(List<MagicItem> out)
	{
		if(out.size()<1)
		{
			System.out.println("Found no items.");
			return;
		}
		int oldLevel = -1;
		for(int i=0;i<out.size();i++)
		{
			MagicItem mi = out.get(i);
			System.out.println("- "+mi);
		}
		System.out.println("Found " + out.size() + " items, skipped "+skippedItems + " items.");
	}
	
	
	private static List<MagicItem> addAllIndex(List<MagicItem> curr,IndexedItem i)
	{
		List<MagicItem> temp = new ArrayList<MagicItem>();
		temp.addAll(curr);
		for(MagicItem mi:masterItemList)
		{
			if(i.hasIndex(mi)&&!temp.contains(mi))
				temp.add(mi);
		}
		return temp;
	}
	
	private static List<MagicItem> addAllSpell(List<MagicItem> curr,String spell)
	{
		List<MagicItem> temp = new ArrayList<MagicItem>();
		temp.addAll(curr);
		for(MagicItem mi:masterItemList)
		{
			if(mi.hasSpell(spell)&&!temp.contains(mi))
				temp.add(mi);
		}
		return temp;
	}
	
	private static List<MagicItem> subAllButIndex(List<MagicItem> curr,IndexedItem i)
	{
		List<MagicItem> temp = new ArrayList<MagicItem>();
		for(MagicItem mi:curr)
		{
			if(i.hasIndex(mi)&&!temp.contains(mi))
				temp.add(mi);
		}
		return temp;
	}
	
	public static List<MagicItem> readFromFile()
	{
		try
		{
			FileInputStream inputFile = new FileInputStream(FILENAME);
			ObjectInputStream objIn = new ObjectInputStream(inputFile);
			List<MagicItem> readMagicItemList = (ArrayList<MagicItem>)objIn.readObject();
			objIn.close();
			inputFile.close();
			return readMagicItemList;
		}
		catch(IOException|ClassNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static void outputToFile(List<MagicItem> magicItemList)
	{
		try
		{
			FileOutputStream output = new FileOutputStream(FILENAME);
			ObjectOutputStream objOut = new ObjectOutputStream(output);
			objOut.writeObject(magicItemList);
			objOut.close();
			output.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void extractItems(int num, List<MagicItem> magicItemList)
	{
		try
		{
			File input = new File("src/Item-Pages/Item-Page"+num+".html");
			Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
			Element curr = doc.child(0);
			curr = curr.child(1);
			curr = curr.child(0);
			curr = curr.child(0);
			
			for(int i = 1; i < curr.childrenSize(); i+=2)
			{
				//temp, will be compressed
							
				Element nameNode = curr.child(i-1);
				nameNode = nameNode.child(1);
				nameNode = nameNode.child(0);
				if(curr.child(i).child(0).child(0).className().equals("ddb-blocked-content"))
				{
					skippedItems++;
				}
				else
				{
					magicItemList.add(new MagicItem(nameNode.text(),curr.child(i),spellList));
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

}