package spells;
import org.jsoup.*;
import org.jsoup.nodes.*;

import universal.*;

import java.io.*;
import java.util.*;

public class Spellbase
{
	private static final String FILENAME = "SpellDatabaseSerial.txt";
	
	private static Scanner input;
	private static ArrayList<Spell> masterSpellList;
	
	public static void main(String[] args)
	{
		masterSpellList = new ArrayList<Spell>();
		//Reading new Data:
		
		input = new Scanner(System.in);
		//System.out.println("Read New Data? (y/n)");
		if(/*input.nextLine().equals("y")*/true)
		{
			for(int i = 1; i <= 26; i++)
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
		outList.addAll(masterSpellList);

		//outList = addAllIndex(outList,new IndexedItem(Class.Cleric,IndexKind.SpellClass));
		//outList = addAllIndex(outList,new IndexedItem(Class.Bard,IndexKind.SpellClass));
		//outList = addAllIndex(outList,new IndexedItem("1 Reaction",IndexKind.SpellCastingTime));
		outList = subAllButIndex(outList,new IndexedItem("1 Action",IndexKind.SpellCastingTime));
		outList = subAllButIndex(outList,new IndexedItem("Instantaneous",IndexKind.SpellDuration));
		//outList = addAllIndex(outList,new IndexedItem(true,IndexKind.SpellIsConcentration));
		Collections.sort(outList);
		printSpells(outList);
		
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
	
	private static List<Spell> addAllClass(List<Spell> curr,Class c)
	{
		List<Spell> temp = new ArrayList<Spell>();
		for(Spell s:masterSpellList)
		{
//			if(s.classes.contains(c)&&!curr.contains(s))
//				temp.add(s);
		}
		return temp;
	}
	private static List<Spell> addAllLevel(List<Spell> curr, Integer[] levels)
	{
		List<Spell> temp = new ArrayList<Spell>();
		for(Spell s:masterSpellList)
		{
			if(Arrays.asList(levels).contains(s.level)&&!curr.contains(s))
				temp.add(s);
		}
		return temp;
	}
	private static List<Spell> onlyNotClass(List<Spell> curr,Class c)
	{
		List<Spell> temp = new ArrayList<Spell>();
		for(Spell s:curr)
		{
//			if(!s.classes.contains(c))
//				temp.add(s);
		}
		return temp;
	}
	private static List<Spell> onlyClass(List<Spell> curr,Class c)
	{
		List<Spell> temp = new ArrayList<Spell>();
		for(Spell s:curr)
		{
//			if(s.classes.contains(c))
//				temp.add(s);
		}
		return temp;
	}
	private static List<Spell> onlyNoAttackSave(List<Spell> curr)
	{
		List<Spell> temp = new ArrayList<Spell>();
		for(Spell s:curr)
		{
			if(s.attackSave.contains("None"))
				temp.add(s);
		}
		return temp;
	}
	private static int getResponse(String q,String[] ans){
		System.out.println(q);
		System.out.println("0: Done");
		for(int i = 1; i <= ans.length; i++) {
			System.out.println(i + ": " + ans[i-1]);
		}
		int i = Integer.parseInt(input.nextLine());
		return i;
	}
	public static ArrayList<Spell> readFromFile()
	{
		try
		{
			FileInputStream inputFile = new FileInputStream(FILENAME);
			ObjectInputStream objIn = new ObjectInputStream(inputFile);
			ArrayList<Spell> readSpellList = (ArrayList<Spell>)objIn.readObject();
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

	public static void outputToFile(ArrayList<Spell> spellList)
	{
		try
		{
			FileOutputStream output = new FileOutputStream(FILENAME);
			ObjectOutputStream objOut = new ObjectOutputStream(output);
			objOut.writeObject(spellList);
			objOut.close();
			output.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void extractSpells(int num, ArrayList<Spell> spellList)
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