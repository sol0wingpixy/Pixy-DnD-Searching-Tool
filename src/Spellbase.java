import org.jsoup.*;
import org.jsoup.nodes.*;
import java.io.*;
import java.util.*;

public class Spellbase
{
	private static final String FILENAME = "DatabaseSerial.txt";
	
	private static Scanner input;
	
	public static void main(String[] args)
	{
		ArrayList<Spell> spellList = new ArrayList();
		//Reading new Data:
		
		input = new Scanner(System.in);
		//System.out.println("Read New Data? (y/n)");
		if(/*input.nextLine().equals("y")*/true)
		{
			for(int i = 1; i <= 26; i++)
			{
				extractSpells(i,spellList);
			}
		}
		else
		{
			spellList=readFromFile();
		}
		
		outputToFile(spellList);
		
		//MainMenuFrame mainMenuFrame = new MainMenuFrame(spellList);
		while(ioLoop(spellList))
		{}
	}

	public static boolean ioLoop(List<Spell> spellList) {
		List<Spell> curr = new ArrayList<Spell>();
		int addClass; 
		do {
			addClass = getResponse("Choose which classes to add.",Class.classesAsString());
			if(addClass !=0)
				curr = addAllClass(curr,spellList,Class.toClass(addClass));
		} while (addClass!=0);
		int subClass;
		do {
			subClass = getResponse("Choose which classes to subtract.",Class.classesAsString());
			if(subClass !=0)
				curr = subAllClass(curr,Class.toClass(subClass));
		} while (subClass!=0);
		//sort
		Collections.sort(curr);
		printSpells(curr);
		int doLoop = getResponse("Go again?",new String[] {"Yes"});
		return doLoop==1;
	}
	
	private static void printSpells(List<Spell> spellList)
	{
		int oldLevel = -1;
		for(int i=0;i<spellList.size();i++)
		{
			Spell s = spellList.get(i);
			if(s.level!=oldLevel)
			{
				oldLevel = s.level;
				System.out.println(s.outputLevel()+":");
			}
			System.out.println("- "+s);
		}
	}
	
	private static List<Spell> addAllClass(List<Spell> curr,List<Spell> spellList,Class c)
	{
		List<Spell> temp = new ArrayList<Spell>(curr);
		for(Spell s:spellList)
		{
			if(s.classes.contains(c)&&!temp.contains(s))
				temp.add(s);
		}
		return temp;
	}
	private static List<Spell> subAllClass(List<Spell> curr,Class c)
	{
		List<Spell> temp = new ArrayList<Spell>();
		for(Spell s:curr)
		{
			if(!s.classes.contains(c))
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