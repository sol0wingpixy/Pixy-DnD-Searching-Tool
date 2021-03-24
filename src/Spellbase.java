import org.jsoup.*;
import org.jsoup.nodes.*;
import java.io.*;
import java.util.*;

public class Spellbase
{
   private static final String FILENAME = "Database.txt";

   public static void main(String[] args)
   {
      ArrayList<Spell> spellList = new ArrayList();
      Scanner input = new Scanner(System.in);
      System.out.println("Read New Data? (y/n)");
      if(input.nextLine().equals("y"))
      {
         for(int i = 1; i <= 26; i++)
         {
            extractSpells(i,spellList);
         }
      }
      else
      {
         readFromFile(spellList);
      }
      
      //*
      outputToFile(spellList);
      //*/
      System.out.println("Time to do things!");
   }
   
   public static void readFromFile(ArrayList<Spell> spellList)
   {
      try
      {
         File inputFile = new File(FILENAME);
         Scanner input = new Scanner(inputFile);
         while(input.hasNextLine())
         {
            spellList.add(new Spell(input.nextLine()));
         }
         input.close();
      }
      catch(IOException e)
      {
         e.printStackTrace();   
      }
   }
   
   public static void outputToFile(ArrayList<Spell> spellList)
   {
      try
      {
         FileWriter output = new FileWriter(FILENAME);
         for(int i = 0; i < spellList.size(); i++)
         {
            output.write(spellList.get(i).toString()+"\n");
         }
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