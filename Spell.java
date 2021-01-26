public class Spell implements Comparable<Spell>
{
   public int level;
   public String name;
   public boolean isDamage;
   public Spell(String input)
   {
      try{
         String[] temp = input.split(",");
         level = Integer.parseInt(temp[0]);
         name = temp[1];
         isDamage = temp[2].equals("1");
      } catch(ArrayIndexOutOfBoundsException e)
      {
         isDamage = false;
      }
   }
   public String toString()
   {
      return level + "," + name + "," + (isDamage?"1":"0");
   }
   
   public String fancyToString()
   {
      String out = "";
      switch(level)
      {
         case 0:
            out += "Cantrip: ";
            break;
         case 1:
            out += "1st: ";
            break;
         case 2:
            out += "2nd: ";
            break;
         case 3:
            out += "3rd: ";
            break;
         default:
            out += level + "th: ";
      }
      return out + name;
   }
   
   @Override
   public boolean equals(Object o)
   {
      if(o instanceof Spell)
      {
         return ((Spell)(o)).name.equals(this.name);
      }
      return false;
   }
   public int compareTo(Spell s)
   {  
      if(this.level == s.level)
         return this.name.compareTo(s.name);
      return this.level - s.level;
   }
}