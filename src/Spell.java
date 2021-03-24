import org.jsoup.nodes.*;

public class Spell
{
   public String name;//string
   public School school;//todo enum
   public int level;//todo int
   public boolean hasV;
   public boolean hasS;
   public boolean hasM;
   public String mComponents;//todo 3 bools, 1 string
   public String castTime;//string
   public String duration;//string
   public String range;//string
   public String aoeSize;//string
   public AOEType aoeType;//todo enum
   public String attackSave;//string
   public String damageEffect;//string
   public String text;//string
   public Spell(String n,Element el)
   {
      name = n;
      Element block = el.child(0).child(0);
      if(block.child(0).child(1).text().equals("Cantrip"))
    	  level = 0;
      else
    	  level = Integer.parseInt(block.child(0).child(1).text().substring(0, 1));
      castTime = block.child(1).child(1).text();
      range = block.child(2).child(1).text();
      if(block.child(2).child(1).childrenSize()>0)
      {
         aoeSize = block.child(2).child(1).child(0).text();
         aoeType = AOEType.toAOEType(block.child(2).child(1).child(0).child(0).attributes().get("class"));
      }
      String rawComps = block.child(3).child(1).child(0).text();
      hasV = rawComps.contains("V");
      hasS = rawComps.contains("S");
      hasM = rawComps.contains("M");
      duration = block.child(4).child(1).text();
      school = School.toSchool(block.child(5).child(1).text());
      attackSave = block.child(6).child(1).text();
      damageEffect = block.child(7).child(1).text();
      text = el.child(0).child(2).text();
      if(block.child(3).child(1).child(0).text().contains("*"))
    	  mComponents = text.substring(text.indexOf("* - ("),text.length()-1);
      System.out.print("");
   }
   
   public Spell(String input)
   {
	   /*
      String[] parts = input.split("_");
      name = parts[0];
      level = parts[1];
      castTime = parts[2];
      range = parts[3];
      aoeSize = parts[4];
      aoeType = parts[5];
      components = parts[6];
      duration = parts[7];
      school = parts[8];
      attackSave = parts[9];
      damageEffect = parts[10];
      text = parts[11];
      */
   }
   
   public String toString()
   {
      return name+"_"+level+"_"+castTime+"_"+range+"_"+aoeSize+"_"+aoeType+"_"+"componentsTODO"+"_"+duration+"_"+school+"_"+attackSave+"_"+damageEffect+"_"+text;
   }
}