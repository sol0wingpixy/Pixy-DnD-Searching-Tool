import org.jsoup.nodes.*;
import java.io.*;

public class Spell implements Serializable
{
	private static final long serialVersionUID = 1L;
	public String name;
	public boolean ritual;
	public boolean concentration;
	public School school;
	public int level;
	public boolean hasV;
	public boolean hasS;
	public boolean hasM;
	public String mComponents;
	public String castTime;//string
	public String duration;//string
	public String range;//string
	public String aoeSize;//string
	public AOEType aoeType;//enum
	public String attackSave;//string
	public String damageEffect;//string
	public String text;//string
	public Spell(String n,Element el)
	{
		if(ritual = n.endsWith("Ritual"))
		{
			n = n.substring(0, n.length()-7);
		}
		if(concentration = n.endsWith("Concentration"))
		{
			n = n.substring(0, n.length()-14);
		}
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
			mComponents = text.substring(text.indexOf("* - (")+5,text.length()-1);
	}

	public String toString()
	{
		return name;
	}
}