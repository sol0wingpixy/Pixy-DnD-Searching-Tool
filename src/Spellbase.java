import org.jsoup.*;
import org.jsoup.nodes.*;
import java.io.*;
import java.util.*;

public class Spellbase
{
	private static final String FILENAME = "DatabaseSerial.txt";

	public static void main(String[] args)
	{
		ArrayList<Spell> spellList = new ArrayList();
		//Reading new Data:
		
		Scanner input = new Scanner(System.in);
		System.out.println("Read New Data? (y/n)");
		if(/*input.nextLine().equals("y")*/false)
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
		
		MainMenuFrame mainMenuFrame = new MainMenuFrame(spellList);
		
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