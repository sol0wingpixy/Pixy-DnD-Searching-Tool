import org.jsoup.nodes.*;

public class Spell
{
   public String name;
   public String school;
   public String level;//todo int
   public String components;
   public String castTime;
   public String duration;
   public String range;
   public String aoeSize;
   public String aoeType;
   public String attackSave;
   public String damageEffect;
   public String text;
   public Spell(String n,Element el)
   {
      name = n;
      Element block = el.child(0).child(0);
   
      level = block.child(0).child(1).text();
      castTime = block.child(1).child(1).text();
      range = block.child(2).child(1).text();
      if(block.child(2).child(1).childrenSize()>0)
      {
         aoeSize = block.child(2).child(1).child(0).text();
         aoeType = block.child(2).child(1).child(0).child(0).attributes().get("class");
      }
      components = block.child(3).child(1).child(0).text();
      duration = block.child(4).child(1).text();
      school = block.child(5).child(1).text();
      attackSave = block.child(6).child(1).text();
      damageEffect = block.child(7).child(1).text();
      text = el.child(0).child(2).text();
   }
   
   public Spell(String input)
   {
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
   }
   
   public String toString()
   {
      return name+"_"+level+"_"+castTime+"_"+range+"_"+aoeSize+"_"+aoeType+"_"+components+"_"+duration+"_"+school+"_"+attackSave+"_"+damageEffect+"_"+text;
   }
}