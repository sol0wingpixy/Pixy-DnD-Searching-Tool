import org.jsoup.*;
import org.jsoup.nodes.*;
import java.io.*;
import java.util.*;

public class Spellbase
{
	private static final String FILENAME = "SpellDatabaseSerial.txt";
	
	private static Scanner input;
	private static ArrayList<Spell> spellList;
	
	public static void main(String[] args)
	{
		spellList = new ArrayList<Spell>();
		//Reading new Data:
		
		input = new Scanner(System.in);
		//System.out.println("Read New Data? (y/n)");
		if(/*input.nextLine().equals("y")*/false)
		{
			for(int i = 1; i <= 26; i++)
			{
				extractSpells(i,spellList);
			}
			outputToFile(spellList);
		}
		else
		{
			spellList=readFromFile();
		}
		
		
		
		//MainMenuFrame mainMenuFrame = new MainMenuFrame(spellList);
		//while(ioLoop(spellList))
		//{}
		List<Spell> outList = new ArrayList<Spell>();
		//List<Spell> outList = new ArrayList<Spell>(spellList);
		//outList = addAllClass(outList,Class.Cleric);
		//outList = onlyNotClass(outList,Class.Wizard);
		outList = addAllLevel(outList,new Integer[] {1});
		outList = onlyNoAttackSave(outList);
		outList = onlyClass(outList,Class.Wizard);
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
		int oldLevel = -1;
		for(int i=0;i<out.size();i++)
		{
			Spell s = out.get(i);
			if(s.level!=oldLevel)
			{
				oldLevel = s.level;
				System.out.println(s.outputLevel()+":");
			}
			System.out.println("- "+s);
		}
	}
	
	private static List<Spell> addAllClass(List<Spell> curr,Class c)
	{
		List<Spell> temp = new ArrayList<Spell>();
		for(Spell s:spellList)
		{
			if(s.classes.contains(c)&&!curr.contains(s))
				temp.add(s);
		}
		return temp;
	}
	private static List<Spell> addAllLevel(List<Spell> curr, Integer[] levels)
	{
		List<Spell> temp = new ArrayList<Spell>();
		for(Spell s:spellList)
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
			if(!s.classes.contains(c))
				temp.add(s);
		}
		return temp;
	}
	private static List<Spell> onlyClass(List<Spell> curr,Class c)
	{
		List<Spell> temp = new ArrayList<Spell>();
		for(Spell s:curr)
		{
			if(s.classes.contains(c))
				temp.add(s);
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
			File input = new File("src/spell-pages/spell-page"+num+".html");
			Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
			Element curr = doc.child(0).child(1).child(0);

			for(int i = 1; i < curr.childrenSize(); i+=2)
			{
				spellList.add(new Spell(curr.child(i-1).child(2).child(0).text(),curr.child(i)));
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

}