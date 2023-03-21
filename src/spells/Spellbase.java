package spells;
import java.io.*;
import java.util.*;

import org.jsoup.*;
import org.jsoup.nodes.*;

import com.google.gson.*;

import universal.*;

public class Spellbase
{
	private static final String TXTFILENAME = "SpellDatabaseSerial.txt";
	private static final String JSONFILENAME = "SpellDatabaseAll.JSON";
	private static final String CSVCONVERTNAME = "SpellDatabaseOutput.txt";
	private static final int NUM_PAGES = 27;
	
	private static Scanner input;
	private static List<Spell> masterSpellList;
	
	public static void main(String[] args)
	{
		masterSpellList = new ArrayList<Spell>();
		//Reading new Data:
		
		input = new Scanner(System.in);
		//System.out.println("Read New Data? (y/n)");
		if(/*input.nextLine().equals("y")*/true)
		{
			for(int i = 1; i <= NUM_PAGES; i++)
			{
				extractSpells(i,masterSpellList);
			}
			outputToFile(masterSpellList);
		}
		else
		{
			masterSpellList=readFromFile();
		}
		
		
		
		//MainMenuFrame mainMenuFrame = new MainMenuFrame(spellList);
		//while(ioLoop(spellList))
		//{}
		List<Spell> outList = new ArrayList<Spell>();
		//List<Spell> outList = new ArrayList<Spell>(spellList);
		//outList.addAll(masterSpellList);
		
		outList = addAllIndex(outList,new IndexedItem("psychic",IndexKind.SpellText));

		//outList = addAllIndex(outList,new IndexedItem(Class.Cleric,IndexKind.SpellClass));
		//outList = addAllIndex(outList,new IndexedItem(Class.Bard,IndexKind.SpellClass));
		//outList = addAllIndex(outList,new IndexedItem("1 Reaction",IndexKind.SpellCastingTime));
		//outList = subAllButIndex(outList,new IndexedItem("1 Action",IndexKind.SpellCastingTime));
		//outList = subAllButIndex(outList,new IndexedItem("Instantaneous",IndexKind.SpellDuration));
		//outList = addAllIndex(outList,new IndexedItem(true,IndexKind.SpellIsConcentration));
		Collections.sort(outList);
		printSpells(outList);
		
		int[] saveFreq = new int[6];
		for(Spell s:masterSpellList)
		{
			switch(s.attackSave.substring(0,3))
			{
			case "STR":
				saveFreq[0]++;
				break;
			case "DEX":
				saveFreq[1]++;
				break;
			case "CON":
				saveFreq[2]++;
				break;
			case "INT":
				saveFreq[3]++;
				break;
			case "WIS":
				saveFreq[4]++;
				break;
			case "CHA":
				saveFreq[5]++;
				break;
			}
		}
		System.out.println("Str: "+saveFreq[0]);
		System.out.println("Dex: "+saveFreq[1]);
		System.out.println("Con: "+saveFreq[2]);
		System.out.println("Int: "+saveFreq[3]);
		System.out.println("Wis: "+saveFreq[4]);
		System.out.println("Cha: "+saveFreq[5]);
		
		System.out.println("Fort: " + (saveFreq[0]+saveFreq[2]));
		System.out.println("Refl: " + (saveFreq[1]));
		System.out.println("Will: " + (saveFreq[3]+saveFreq[4]+saveFreq[5]));
	}
	
	public static List<Spell> getMasterSpellList()
	{
		if(masterSpellList == null)
		{
			masterSpellList = new ArrayList<Spell>();
			input = new Scanner(System.in);
		//System.out.println("Read New Data? (y/n)");
			if(/*input.nextLine().equals("y")*/true)
			{
				for(int i = 1; i <= NUM_PAGES; i++)
				{
					extractSpells(i,masterSpellList);
				}
			}
			else
			{
				masterSpellList=readFromFile();
			}
		}
		return masterSpellList;
	}
	
	public static boolean[] getContainsSaves()
	{
		boolean[] hasSaves = new boolean[6];
		
		
		return hasSaves;
	}

//	public static boolean ioLoop(List<Spell> spellList) {
//		List<Spell> curr = new ArrayList<Spell>();
//		int addClass; 
//		do {
//			addClass = getResponse("Choose which classes to add.",Class.classesAsString());
//			if(addClass !=0)
//				curr = addAllClass(curr,spellList,Class.toClass(addClass));
//		} while (addClass!=0);
//		int subClass;
//		do {
//			subClass = getResponse("Choose which classes to subtract.",Class.classesAsString());
//			if(subClass !=0)
//				curr = subAllClass(curr,Class.toClass(subClass));
//		} while (subClass!=0);
//		//sort
//		Collections.sort(curr);
//		printSpells(curr);
//		int doLoop = getResponse("Go again?",new String[] {"Yes"});
//		return doLoop==1;
//	}
	
	private static void printSpells(List<Spell> out)
	{
		if(out.size()<1)
		{
			System.out.println("Found no spells.");
			return;
		}
		int oldLevel = -1;
		for(int i=0;i<out.size();i++)
		{
			Spell s = out.get(i);
			if(s.level.getContentInt()!=oldLevel)
			{
				oldLevel = s.level.getContentInt();
				System.out.println(s.outputLevel()+":");
			}
			System.out.println("- "+s);
		}
	}
	
	private static List<Spell> addAllIndex(List<Spell> curr,IndexedItem i)
	{
		List<Spell> temp = new ArrayList<Spell>();
		temp.addAll(curr);
		for(Spell s:masterSpellList)
		{
			if(i.hasIndex(s)&&!temp.contains(s))
				temp.add(s);
		}
		return temp;
	}
	
	private static List<Spell> subAllButIndex(List<Spell> curr,IndexedItem i)
	{
		List<Spell> temp = new ArrayList<Spell>();
		for(Spell s:curr)
		{
			if(i.hasIndex(s)&&!temp.contains(s))
				temp.add(s);
		}
		return temp;
	}
	
	public static List<Spell> readFromFile()
	{
		try
		{
			FileInputStream inputFile = new FileInputStream(TXTFILENAME);
			ObjectInputStream objIn = new ObjectInputStream(inputFile);
			List<Spell> readSpellList = (ArrayList<Spell>)objIn.readObject();
			objIn.close();
			inputFile.close();
			return readSpellList;
		}
		catch(IOException|ClassNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static void outputToFile(List<Spell> spellList)
	{
		try
		{
			FileOutputStream output = new FileOutputStream(TXTFILENAME);
			ObjectOutputStream objOut = new ObjectOutputStream(output);
			objOut.writeObject(spellList);
			objOut.close();
			output.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			File spellFile = new File(CSVCONVERTNAME);
			spellFile.createNewFile();
			FileWriter spellWriter = new FileWriter(CSVCONVERTNAME);
			for(Spell s: spellList)
			{
				spellWriter.write(s.toCSVString()+"\n");
			}
			spellWriter.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			FileOutputStream output = new FileOutputStream(JSONFILENAME);
			PrintStream printOut = new PrintStream(output);
			printOut.print(new Gson().toJson(spellList).toString());
			printOut.close();
			output.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void extractSpells(int num, List<Spell> spellList)
	{
		try
		{
			File input = new File("src/Spell-Pages/Spell-Page"+num+".html");
			Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
			Element curr = doc.child(0);
			curr = curr.child(1);
			curr = curr.child(0);
			curr = curr.child(0);
			
			Spell newSpell;

			for(int i = 1; i < curr.childrenSize(); i+=2)
			{
				newSpell = new Spell(curr.child(i-1).child(2).child(0).text(),curr.child(i));
				if(!newSpell.name.getContentString().equals("skip"))
						spellList.add(new Spell(curr.child(i-1).child(2).child(0).text(),curr.child(i)));
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

}