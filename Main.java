import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Main{
   public static String[] classNames;

   public static void main(String[] args)
   {
      Scanner input = new Scanner(System.in);
      ArrayList<ArrayList<Spell>> spells = buildDatabase();
      
      ArrayList<Spell> tempList = new ArrayList<Spell>();
      ArrayList<Integer> addedClasses = new ArrayList<Integer>();
      ArrayList<Spell> subList = new ArrayList<Spell>();
      ArrayList<Integer> subbedClasses = new ArrayList<Integer>();
      ArrayList<Integer> addedLevels = new ArrayList<Integer>();
      
      try
      {
         while(true)
         {
            while(true)
            {
               System.out.println("Add spell list of?");
               for(int i = 0; i < classNames.length; i++)
               {
                  System.out.println((i+1) + ": "+classNames[i]);
               }
               int choice = Integer.parseInt(input.nextLine().substring(0,1));
               if(choice == 0)
                  break;
               tempList.addAll(spells.get(choice-1));
               addedClasses.add(choice-1);
            }
         
            while(true)
            {
               System.out.println("Subtract spell list of?");
               for(int i = 0; i < classNames.length; i++)
               {
                  System.out.println((i+1) + ": "+classNames[i]);
               }
               int choice = Integer.parseInt(input.nextLine().substring(0,1));
               if(choice == 0)
                  break;
               subList.addAll(spells.get(choice-1));
               subbedClasses.add(choice-1);
            }
         
            tempList.removeAll(subList);
         
            Collections.sort(tempList);
         
            for(int i = 1; i < tempList.size(); i++)
            {
               if(tempList.get(i-1).equals(tempList.get(i)))
               {
                  tempList.remove(i);
                  i--;
               }
            }
         
            while(true)
            {
               System.out.println("Add spell level:");
               try
               {
                  addedLevels.add(Integer.parseInt(input.nextLine().substring(0,1)));
               }
               catch(NumberFormatException e)
               {
                  break;
               }
            }
            for(int i = 0; i < tempList.size(); i++)
            {
               if(!addedLevels.contains(tempList.get(i).level))
               {
                  tempList.remove(i);
                  i--;
               }
            }
            
            
            System.out.println("Is Damaging?");
            try
            {
               int dmg = Integer.parseInt(input.nextLine().substring(0,1));
               if(dmg == 0 || dmg == 1)
               {
                  boolean isTempDmg = dmg == 0;
                  for(int i = 0; i < tempList.size(); i++)
                  {
                     if(tempList.get(i).isDamage == isTempDmg)
                     {
                        tempList.remove(i);
                        i--;
                     }
                  }
               }
            }
            catch(NumberFormatException e)
            {
               
            }
            
            System.out.print("Added: " + classNames[addedClasses.get(0)]);
            for(int i = 1; i < addedClasses.size(); i++)
            {
               System.out.print(", " + classNames[addedClasses.get(i)]);
            }
            System.out.println();
            
            if(subbedClasses.size() > 0)
            {
               System.out.print("Subtracted: " + classNames[subbedClasses.get(0)]);
               for(int i = 1; i < subbedClasses.size(); i++)
               {
                  System.out.print(", " + classNames[subbedClasses.get(i)]);
               }
               System.out.println();
            }
            
            for(Spell s : tempList)
               System.out.println(s.fancyToString());
            System.out.println();
         
            tempList.clear();
            addedClasses.clear();
            subList.clear();
            subbedClasses.clear();
            addedLevels.clear();
            
         }
      }
      catch(NumberFormatException e)
      {
         try
         {
            BufferedWriter dataWriter = new BufferedWriter(new FileWriter("Database.txt"));
            dataWriter.newLine();
            for(int i = 0; i < classNames.length; i++)
            {
               dataWriter.write(classNames[i] + ":\n");
               for(int j = 0; j < spells.get(i).size(); j++)
               {
                  dataWriter.write(spells.get(i).get(j) + "\n");
               }
               dataWriter.write(":\n");
            }
            dataWriter.close();
         }
         catch(IOException e2)
         {
            
         }
      }
   }
   public static ArrayList<ArrayList<Spell>> buildDatabase()
   {
      String[] CnS = fileInput().split(":");// Class and Spells
      String[] classes = new String[CnS.length/2];
      String[] spellList = new String[CnS.length/2];
      for(int i = 0; i < CnS.length; i += 2)
      {
         classes[i/2] = CnS[i].substring(1);
         spellList[i/2] = CnS[i+1].substring(1);
      }
      
      ArrayList<ArrayList<Spell>> spells = new ArrayList<ArrayList<Spell>>();
      
      for(int i = 0; i < classes.length; i++)
      {
         ArrayList<Spell> classSpells = new ArrayList<Spell>();
         String[] spellStrings = spellList[i].split("-");
         for(String s : spellStrings)
            classSpells.add(new Spell(s));
         spells.add(classSpells);
      }
      classNames = classes;
      return spells;
   }
   public static String fileInput()
   {
      StringBuilder buffer = new StringBuilder();
      try
      {
         Scanner input = new Scanner(new FileReader("Spells.txt"));
         while(input.hasNextLine())
         {
            buffer.append(input.nextLine()+"-");
         }
      } 
      catch(IOException e)
      {
      
      }
      return buffer.toString();
   }
}