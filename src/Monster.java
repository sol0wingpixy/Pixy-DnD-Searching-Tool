import org.jsoup.nodes.*;
import java.util.*;
import java.io.*;

public class Monster implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String[] statNames = new String[] {"STR","DEX","CON","INT","WIS","CHA"};

	boolean hasInfo;
	public String name;
	int cr;
	public Type type;
	public String source;
	public String size;// Enum?
	public String subtype;
	public int ac;
	public int hp;
	public int hdCount;
	public int hdSize;
	public String speeds;
	public int[] stats;
	public int[] saves;
	public String skills;// split it up
	public String dmgRes;
	public String dmgImm;
	public String condImm; // fix all these?
	public String senses;
	public String languages;
	public int toHitMod;//todo; just grabs 1st action, no spell attack or other actions

	public Monster(Element smolInfo, Element bigInfo) {
		String tempString;
		int tempInt;

		name = smolInfo.child(2).child(0).text();

		tempString = smolInfo.child(1).text();
		if (tempString.contains("/")) {
			tempString = "-" + tempString.substring(2);
		}
		cr = Integer.parseInt(tempString);
		if (cr == 0)
			cr = -10;

		type = Type.toType(smolInfo.child(3).text());

		if (bigInfo.child(0).child(0).className().equals("ddb-blocked-content")) {
			hasInfo = false;

			source = bigInfo.child(0).child(0).child(0).child(1).child(0).text();
		} else {
			hasInfo = true;

			source = bigInfo.child(1).child(bigInfo.child(1).childrenSize() - 1).text();

			tempString = bigInfo.child(0).child(0).child(0).child(1).text();
			tempInt = tempString.indexOf(" ");
			size = tempString.substring(0, tempInt);

			if (tempString.contains("(")) {
				subtype = tempString.substring(tempString.indexOf("(") + 1, tempString.indexOf(")"));
			}

			tempString = bigInfo.child(0).child(0).child(2).child(0).child(1).text();
			if (tempString.indexOf(" ") != -1) {
				tempString = tempString.substring(0, tempString.indexOf(" "));
			}
			ac = Integer.parseInt(tempString);

			hp = Integer.parseInt(bigInfo.child(0).child(0).child(2).child(1).child(1).child(0).text());

			tempString = bigInfo.child(0).child(0).child(2).child(1).child(1).child(1).text();
			tempInt = tempString.indexOf("d");
			hdCount = Integer.parseInt(tempString.substring(1, tempInt));

			if (tempString.indexOf(" ") != -1) {
				hdSize = Integer.parseInt(tempString.substring(tempInt + 1, tempString.indexOf(" ")));
			} else {
				hdSize = Integer.parseInt(tempString.substring(tempInt + 1, tempString.indexOf(")")));
			}

			speeds = bigInfo.child(0).child(0).child(2).child(2).text();

			stats = new int[6];
			for (int i = 0; i < stats.length; i++) {
				stats[i] = Integer
						.parseInt(bigInfo.child(0).child(0).child(3).child(1).child(i).child(1).child(0).text());
			}

			for (int i = 0; i < bigInfo.child(0).child(0).child(4).childrenSize() - 1; i++) {
				tidbitsParsing(bigInfo.child(0).child(0).child(4).child(i));
			}

			for (int i = 0; i < bigInfo.child(0).child(0).child(6).childrenSize(); i++) {
				if (bigInfo.child(0).child(0).child(6).child(i).child(0).text().equals("Actions")) {
					tempString = bigInfo.child(0).child(0).child(6).child(i).child(1).text();
					tempInt = tempString.indexOf("+");
					tempString = tempString.substring(tempInt, tempString.indexOf(" ", tempInt));
					toHitMod = Integer.parseInt(tempString);
					break;
				}
			}
		}
	}

	private void tidbitsParsing(Element curr) {
		switch (curr.child(0).text()) {
		case "Saving Throws":
			saves = new int[6];
			String[] split = curr.child(1).text().split(",");
			int ptr = 0;
			for(int i = 0; i<saves.length; i++) {
				if(ptr >= split.length && split[ptr].contains(Monster.statNames[i])) {
					saves[i] = Integer.parseInt(split[ptr].substring(split[ptr].length()-1,split[ptr].length()));
					ptr++;
				} else {
					int temp = stats[i]-10;
					if(temp < 0)
						temp--;
					saves[i] = temp/2;
				}
			}
			break;
		case "Skills":
			skills = curr.child(1).text();
			break;
		case "Damage Resistances":
			dmgRes = curr.child(1).text();
			break;
		case "Damage Immunities":
			dmgImm = curr.child(1).text();
			break;
		case "Condition Immunities":
			condImm = curr.child(1).text();
			break;
		case "Senses":
			senses = curr.child(1).text();
			break;
		case "Languages":
			languages = curr.child(1).text();
			break;
		default:
			System.out.println("Add this to Monster.java: " + curr.child(0).text());
		}
	}
}
