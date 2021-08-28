import org.jsoup.*;
import org.jsoup.nodes.*;
import java.io.*;
import java.util.*;

public class Monsterbase
{
	private static final String FILENAME = "MonsterDatabaseSerial.txt";
	
	private static Scanner input;
	
	public static void main(String[] args)
	{
		ArrayList<Monster> monsterList = new ArrayList<Monster>();
		//Reading new Data:
		
		input = new Scanner(System.in);
		//System.out.println("Read New Data? (y/n)");
		if(/*input.nextLine().equals("y")*/true)
		{		
			for(int i=1;i<=2;i++) {
				extractMonsters(i,monsterList);	
			}
		}
		else
		{
			monsterList=readFromFile();
		}
		double avgWisSave = 0;
		for(int i=0; i<monsterList.size(); i++)
		{
			if(monsterList.get(i).hasInfo && monsterList.get(i).cr <= 5) {
				System.out.println(monsterList.get(i).name + " : " + monsterList.get(i).saves[4]);
				avgWisSave += monsterList.get(i).saves[4];
			}
		}
		avgWisSave /= monsterList.size();
		System.out.println("Avg wis save: " + avgWisSave);
		outputToFile(monsterList);
		System.out.println("didn't break");
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
	public static ArrayList<Monster> readFromFile()
	{
		try
		{
			FileInputStream inputFile = new FileInputStream(FILENAME);
			ObjectInputStream objIn = new ObjectInputStream(inputFile);
			ArrayList<Monster> readSpellList = (ArrayList<Monster>)objIn.readObject();
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

	public static void outputToFile(ArrayList<Monster> monsterList)
	{
		try
		{
			FileOutputStream output = new FileOutputStream(FILENAME);
			ObjectOutputStream objOut = new ObjectOutputStream(output);
			objOut.writeObject(monsterList);
			objOut.close();
			output.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void extractMonsters(int num, ArrayList<Monster> monsterList)
	{
		try
		{
			File input = new File("src/monster-pages/monster-page"+num+".html");
			Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
			
			Element curr = doc.child(0).child(1).child(0);
			
			for(int i = 0; i < curr.childrenSize(); i+=2)
			{
				monsterList.add(new Monster(curr.child(i),curr.child(i+1)));
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

}