package monsters;

import org.jsoup.nodes.*;

import spells.Spell;
import spells.Spellbase;

import java.util.*;
import java.io.*;
import universal.*;

public class Monster implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String[] statNames = new String[] { "STR", "DEX", "CON", "INT", "WIS", "CHA" };
	public static final int[] CR_ORDER = new int[] { -10, -8, -4, -2, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
			16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30 };

	public static int indexOfCR(int cr) {
		for (int i = 0; i < CR_ORDER.length; i++) {
			int temp = CR_ORDER[i];
			if (temp == cr)
				return i;
		}
		return -1;
	}
	public static double OrderToCr(int order)
	{
		if(order >= 1)
			return (double)order;
		if(order == -10)
			return 0;
		return -1.0/order;
	}
	
	public static int CRtoPB(int cr) {
		if(cr < 5)
			return 2;
		if(cr < 9)
			return 3;
		if(cr < 13)
			return 4;
		if(cr < 17)
			return 5;
		if(cr < 21)
			return 6;
		if(cr < 25)
			return 7;
		if(cr < 29)
			return 8;
		return 9;
	}

	public boolean hasInfo;
	public String name;
	public IndexedItem cr;
	public IndexedItem type;
	public String source;
	public String size;// Enum?
	public String subtype = "";
	public IndexedItem ac;
	public int hp;
	public int hdCount;
	public int hdSize;
	public String speeds;
	public int[] stats;
	public int[] saves;
	public String skills;// split it up
	public String dmgVul = "";
	public String dmgRes;
	public String dmgImm = "";
	public String condImm = ""; // fix all these?
	public String senses;
	public String languages;
	public boolean hasToHit;
	public int toHitMod;// todo; just grabs 1st action, no spell attack or other actions
	public String fullText;
	public List<String> attacks;
	public double avgDamage;
	public boolean[] forceSaves;
	public List<Spell> spellList;

	public Monster(Element smolInfo, Element bigInfo) {
		String tempString;
		int tempInt;

		fullText = bigInfo.child(0).child(0).wholeText();
		
		name = smolInfo.child(2).child(0).text();

		tempInt = name.indexOf("Legacy This doesn't reflect the latest rules and lore. Learn More");
		if (tempInt != -1) {
			name = name.substring(0, tempInt) + "(Legacy)";
		}

		tempString = smolInfo.child(1).text();
		if (tempString.contains("/")) {
			tempString = "-" + tempString.substring(2);
		}
		
		if (tempString.length() != 0) {
			tempInt = Integer.parseInt(tempString);
			if (tempInt == 0)
				tempInt = -10;
		} else {
			tempInt = 0;
			//System.out.println("CR error with " + name);
		}
		cr = new IndexedItem(new Integer(tempInt),IndexKind.MonsterCR);

		type = new IndexedItem(Type.toType(smolInfo.child(3).text()), IndexKind.MonsterType);
		try {

			if (bigInfo.child(0).child(0).className().equals("ddb-blocked-content")) {
				hasInfo = false;

				source = bigInfo.child(0).child(0).child(0).child(1).child(0).text();
			} else {
				hasInfo = true;
				
				spellList = new ArrayList<Spell>();
				forceSaves = new boolean[6];
				tempString = bigInfo.child(0).child(0).toString();
				tempInt = tempString.indexOf("spell-tooltip");
				List<String> spellNames = new ArrayList<String>();
				while(tempInt != -1)
				{
					tempString = tempString.substring(tempInt + 13);
					spellNames.add(tempString.substring(tempString.indexOf('>')+1,tempString.indexOf('<')).
							replace("’", "'").replace(",","").trim());
					tempInt = tempString.indexOf("spell-tooltip");
				}
				List<Spell> masterSpellList = Spellbase.getMasterSpellList();
				int verifyCount = 0;
				for(Spell s : masterSpellList)
				{
					for(int i = 0; i < spellNames.size(); i++)
					{
						if(s.name.getContentString().toLowerCase().equals(spellNames.get(i).toLowerCase()))
						{
							if(!this.spellList.contains(s))
							{
								this.spellList.add(s);
								if(s.intSaveForced>=0)
									forceSaves[s.intSaveForced] = true;
							}
							verifyCount++;
						}
					}
				}
				if(verifyCount != spellNames.size())
				{
					System.out.println(name + " has bad spells");
				}
				
				tempString = fullText.toLowerCase();
				tempInt = tempString.indexOf("saving throw");
				while(tempInt!=-1)
				{
					if(tempInt > 14)
					{
						tempString = tempString.substring(tempInt - 14);
						tempInt = tempString.indexOf("saving throw");
						if(-1 < tempString.indexOf("strength") && tempString.indexOf("strength") < tempInt)
							forceSaves[0]=true;
						else if(-1 < tempString.indexOf("dexterity") && tempString.indexOf("dexterity") < tempInt)
							forceSaves[1]=true;
						else if(-1 < tempString.indexOf("constitution") && tempString.indexOf("constitution") < tempInt)
							forceSaves[2]=true;
						else if(-1 < tempString.indexOf("intelligence") && tempString.indexOf("intelligence") < tempInt)
							forceSaves[3]=true;
						else if(-1 < tempString.indexOf("wisdom") && tempString.indexOf("wisdom") < tempInt)
							forceSaves[4]=true;
						else if(-1 < tempString.indexOf("charisma") && tempString.indexOf("charisma") < tempInt)
							forceSaves[5]=true;
					}
					tempString = tempString.substring(tempInt + 11);
					tempInt = tempString.indexOf("saving throw");
				}

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
				ac = new IndexedItem(Integer.parseInt(tempString),IndexKind.MonsterAC);

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
				if (saves == null) {
					saves = new int[6];
					for (int i = 0; i < stats.length; i++) {
						tempInt = stats[i] - 10;
						if (tempInt < 0)
							tempInt--;
						saves[i] = tempInt / 2;
					}
				}
				for (int i = 0; i < bigInfo.child(0).child(0).child(6).childrenSize(); i++) {
					if (bigInfo.child(0).child(0).child(6).child(i).child(0).text().equals("Actions")) {

						// tempString = bigInfo.child(0).child(0).child(6).child(i).child(1).text();
						Element curr = bigInfo.child(0).child(0).child(6).child(i).child(1);
						if (name.equals("Dolgrim"))
							tempInt = 0;
						attacks = new ArrayList<String>();
						for (Element j : curr.children()) {
							attacks.add(j.text());
						}
						tempInt = -1;
						int j;
						for (j = 0; j < attacks.size(); j++) {
							tempInt = attacks.get(j).indexOf("+");
							if (tempInt != -1)
								break;
						}
						if (j == attacks.size()) {
							hasToHit = false;
						} else {
							tempString = attacks.get(j).substring(tempInt, attacks.get(j).indexOf(" ", tempInt));
							if (tempString.length() > 1) {
								toHitMod = Integer.parseInt(tempString);
								hasToHit = true;
								avgDamage = calcAvgDamage();
								break;
							} else
								hasToHit = false;
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(name);
			e.printStackTrace();
		}
	}

	private void tidbitsParsing(Element curr) {
		switch (curr.child(0).text()) {
		case "Saving Throws":
			saves = new int[6];
			String[] split = curr.child(1).text().split(",");
			int ptr = 0;
			int temp;
			for (int i = 0; i < saves.length; i++) {
				if (ptr < split.length && split[ptr].contains(Monster.statNames[i])) {
					saves[i] = Integer.parseInt(split[ptr].substring(split[ptr].length() - 1, split[ptr].length()));
					ptr++;
				} else {
					temp = stats[i] - 10;
					if (temp < 0)
						temp--;
					saves[i] = temp / 2;
				}
			}
			break;
		case "Skills":
			skills = curr.child(1).text();
			break;
		case "Damage Vulnerabilities":
			dmgVul = curr.child(1).text();
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
			System.out.println("Add this to Monster.java: " + curr.child(0).text() + ", from: " + name);
		}
	}

	public String toString() {
		return name + " (CR:" + cr + ", HP:" + Monsterbase.convertHP(this) + ", DPRtoHit: "
				+ Monsterbase.avgDmgToHit(this) + ")\n";
	}

	public boolean equals(Object o) {
		if (o instanceof Monster) {
			Monster m = (Monster) o;
			return this.name.equals(m.name);
		}

		return o.equals(this);

	}

	public Integer sizeNumber() {
		switch (size) {
		case "Tiny":
			return 1;
		case "Small":
			return 2;
		case "Medium":
			return 3;
		case "Large":
			return 4;
		case "Huge":
			return 5;
		case "Gargantuan":
			return 6;
		default:
			return 0;
		}
	}

	public double calcAvgDamage() {
		String first = attacks.get(0);
		if (name.equals("Chimera"))
			first = first;

		if (first.contains("Multiattack")) {
			int index = first.indexOf("two");
			int offset = 4;
			int mult = 2;
			if (index == -1) {
				index = first.indexOf("three");
				offset = 6;
				mult = 3;
				if (index == -1) {
					index = first.indexOf("four");
					offset = 5;
					mult = 4;
					if (index == -1) {
						index = first.indexOf("five");
						offset = 5;
						mult = 5;
					}
				}
			}

			int nextIndex = first.indexOf(" ", index + offset);
			if (nextIndex < 0) {
				if (first.substring(index + offset).equals("attacks.")) {
					int attacksLeft = mult;
					int attackIndex = 1;
					double damageTotal = 0;
					while (attacksLeft > 0) {
						damageTotal += calcDamage(attacks.get(attackIndex++));
						if (attackIndex == attacks.size())
							attackIndex = 1;
						attacksLeft--;
					}
					return damageTotal;
				} else {
					System.out.println();
					return -1;
				}
			}
			String name = first.substring(index + offset, nextIndex);
			if (name.equals("attacks:")) {
				String[] split = first.split(" with its ");
				String[] fullSplit = first.split(" with its ");
				double totalDamage = 0;
				for (int i = 1; i < split.length; i++) {
					// TODO, reference previous element. At the end there will be a number word
					// it indicates how many attacks are made i.e. "one] with its [claws."
					int splitIndex = split[i].length();
					if (split[i].indexOf('.') != -1)
						splitIndex = Math.min(splitIndex, split[i].indexOf('.'));
					if (split[i].indexOf(',') != -1)
						splitIndex = Math.min(splitIndex, split[i].indexOf(','));
					if (split[i].indexOf(' ') != -1)
						splitIndex = Math.min(splitIndex, split[i].indexOf(' '));

					split[i] = split[i].substring(0, splitIndex);

					for (int j = 1; j < attacks.size(); j++) {
						if (attacks.get(j).toLowerCase().startsWith(split[i].toLowerCase())) {
							if (i == attacks.size())
								break;
							double temp = calcDamage(attacks.get(i));
							if (fullSplit[i - 1].endsWith("one"))
								totalDamage += temp;
							else if (fullSplit[i - 1].endsWith("two"))
								totalDamage += temp * 2;
							else if (fullSplit[i - 1].endsWith("three"))
								totalDamage += temp * 3;
							else if (fullSplit[i - 1].endsWith("four"))
								totalDamage += temp * 4;
						}
					}
				}
				if (mult == 5)
					System.out.println(this.name + " ; " + totalDamage);
				return totalDamage;
			} else {
				for (int i = 1; i < attacks.size(); i++) {
					if (attacks.get(i).toLowerCase().startsWith(name.toLowerCase())) {
						return calcDamage(attacks.get(i)) * mult;
					}
				}
				return -1;
			}
		} else {
			return calcDamage(attacks.get(0));
		}
	}

	public double calcDamage(String attack) {
		if (name.equals("Giant Poisonous Snake"))
			attack = attack;
		List<String> damageStrings = getInParen(attack);
		double total = 0;
		int index;
		if (damageStrings.size() == 0) {
			index = attack.indexOf("poison");
			if (index == -1)
				index = attack.indexOf("slashing");
			if (index == -1)
				index = attack.indexOf("bludgeoning");
			if (index == -1)
				index = attack.indexOf("piercing");
			if (index == -1)
				index = attack.indexOf("force");
			if (index == -1)
				return -1;
			index--;
			do {
				index--;
			} while (attack.charAt(index) != ' ');
			index++;
			String offDamage = attack.substring(index, attack.indexOf(' ', index));
			if (offDamage.matches("[0-9]+"))
				total += Integer.parseInt(offDamage);
		} else {
			boolean posMod = true;
			for (int j = 0; j < damageStrings.size(); j++) {
				String s = damageStrings.get(j).replace("plus", "+");
				String[] split;
				if (s.contains("+")) {
					split = s.split("\\+");
				} else {
					if (s.contains("−")) {
						split = s.split("\\−");
						posMod = false;
					} else {
						if (s.contains("-")) {
							split = s.split("\\-");
							posMod = false;
						} else {
							if (s.contains("–")) {
								split = s.split("\\–");
								posMod = false;
							} else
								split = new String[] { s };
						}
					}
				}
				for (int i = 0; i < split.length; i++) {
					split[i] = split[i].trim();
					index = split[i].indexOf("d");
					if (index != -1 && !split[i].startsWith("d") && Character.isDigit(split[i].charAt(index - 1))
							&& !split[i].endsWith("d") && Character.isDigit(split[i].charAt(index + 1))) {
						int dieNum = Integer.parseInt(split[i].split("d")[0]);
						int dieType = Integer.parseInt(split[i].split("d")[1]);
						if (j == damageStrings.size() - 1 && attack.contains("half"))
							total += dieNum * (((double) dieType + 1) * 0.5) * 0.5;
						else
							total += dieNum * (((double) dieType + 1) * 0.5);
					} else {
						if (split[i].matches("[0-9]+")) {
							if (posMod)
								total += Integer.parseInt(split[i]);
							else
								total -= Integer.parseInt(split[i]);
						}
					}
				}
			}
		}

		return total;
	}

	private static List<String> getInParen(String text) {
		List<String> vals = new ArrayList<String>();
		int startIndex;
		int endIndex;
		while (text.indexOf('(') != -1) {
			startIndex = text.indexOf('(');
			endIndex = text.indexOf(')');
			vals.add(text.substring(startIndex + 1, endIndex));
			text = text.substring(endIndex + 1);
		}
		return vals;
	}
}
