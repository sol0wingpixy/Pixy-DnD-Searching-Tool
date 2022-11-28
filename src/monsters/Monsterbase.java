package monsters;

import org.jsoup.*;
import org.jsoup.nodes.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Monsterbase
{
	private static final String FILENAME = "MonsterDatabaseSerial.txt";
	
	private static List<String> outputVars;
	
	private static List<Monster> refMonsterList;

	private static Scanner input;
	private static final int BASECR = 2;
	private static final int BASEAC = 14;
	private static final boolean MAGE_ARMOR = true;

	public static void main(String[] args)
	{
		List<Monster> monsterList = new ArrayList<Monster>();
		//Reading new Data:

		input = new Scanner(System.in);
		//System.out.println("Read New Data? (y/n)");
		if(/*input.nextLine().equals("y")*/true)
		{		
			for(int i=1;i<=106;i++) {
				extractMonsters(i,monsterList);	
			}
		}
		else
		{
			monsterList=readFromFile();
		}
		
		monsterList = filterBy(monsterList,"hasInfo","True");
		//monsterList = filterBy(monsterList,"Type","fiend");
		//monsterList = filterBy(monsterList,"Subtype","devil");

		refMonsterList = new ArrayList<Monster>(monsterList);
		
		outputVars = new ArrayList<String>();
		
		//outputToFile(monsterList);

		//displayAC(monsterList,-4,3);
		
		
		monsterList = addToList(monsterList,"Type","Undead");
		//monsterList = filterBy(monsterList,"Trim","Swarm of");
		//monsterList = filterBy(monsterList,"CR","1 1");
		//monsterList = filterBy(monsterList,"Search","Possession");
		//monsterList = filterBy(monsterList,"Search","counterspell");
		//monsterList = filterBy(monsterList,"SearchConditionImmunities","stunned");
		
		//monsterList = invertSearch(monsterList,refMonsterList);


		//monsterList = sortBy(monsterList,"Hybrid","Dec");
		//monsterList = sortBy(monsterList,"AvgDmgAdj","Dec");
		//monsterList = sortBy(monsterList,"AvgDmg","Dec");
		//monsterList = sortBy(monsterList,"AvgDmgToHit","Dec");
		//monsterList = sortBy(monsterList,"ConAniHP","Dec");
		//monsterList = sortBy(monsterList,"ConAniHPBig","Dec");
		//monsterList = sortBy(monsterList, "Size","Dec");
		//monsterList = sortBy(monsterList, "HPACMA","Dec");
		//monsterList = sortBy(monsterList, "HP","Dec");
		//monsterList = sortBy(monsterList,"HP","Dec");
		monsterList = sortBy(monsterList,"CR","Dec");
		//monsterList = sortBy(monsterList,"ConSave","Dec");
		//monsterList = sortBy(monsterList,"AC","Dec");
		//monsterList = sortBy(monsterList,"HPSaves","Dec");
		//monsterList = filterBy(monsterList,"CR","-2 -2");
		displaySaves(monsterList,1,9);

		
		
		
		for(Monster m : monsterList)
		{
			String out = "- "+m.name;
			if(outputVars.contains("HP"))
			{
				out += " | HP:" + m.hp;
			}
			if(outputVars.contains("AC"))
			{
				out += " | AC:" + m.ac;
			}
			if(outputVars.contains("ConAniHP"))
			{
				out += " | ConAniHP:" + convertHP(m);
			}
			if(outputVars.contains("HPSaves"))
			{
				out += " | HPSaves:" + convertHP(m);
			}
			if(outputVars.contains("HPAC"))
			{
				out += " | HPAC:" + avgHPAC(m);
			}
			if(outputVars.contains("HPACMA"))
			{
				out += " | HPACMA:" + avgHPACMA(m);
			}
			if(outputVars.contains("ConSave"))
			{
				out += " | Con Save:" + m.saves[2];
			}
			if(outputVars.contains("toHit"))
			{
				out += " | toHit:" + m.toHitMod;
			}
			if(outputVars.contains("AvgDmg"))
			{
				out += " | AvgDmg:" + m.avgDamage;
			}
			if(outputVars.contains("CR"))
			{
				out += " | CR:" + m.cr;
			}
			if(outputVars.contains("Size"))
			{
				out += " | Size:" + m.size;
			}
			if(outputVars.contains("AvgDmgAdj"))
			{
				out += " | AvgDmgAdj:" + convertAvgDmg(m);
			}
			if(outputVars.contains("AvgDmgToHit"))
			{
				out += " | AvgDmgToHit:" + convertAvgDmg(m);
			}
			if(outputVars.contains("Hybrid"))
			{
				out += " | HybridAvg:" + HybridAvg(m);
			}
			if(outputVars.contains("ConAniHPBig"))
			{
				out += " | ConAniHPBig:" + ConAniHPBig(m);
			}
			
			System.out.println(out);
		}
		System.out.println(monsterList.size() + " monsters found.");
	}

	private static List<Monster> invertSearch(List<Monster> monsterList, List<Monster> ogMonsterList)
	{
		List<Monster> newMonsterList = new ArrayList<Monster>();
		for(Monster m : ogMonsterList)
		{
			if(!monsterList.contains(m))
			{
				newMonsterList.add(m);
			}
		}
		return newMonsterList;
	}
	
	private static List<Monster> sortBy(List<Monster> monsterList, String sortType, String sorter)
	{
		outputVars.add(sortType);
		switch(sortType)
		{
		case "HP":
			if(sorter.equals("Dec"))
				monsterList = monsterList.stream().sorted((m1,m2) -> new Integer(m2.hp).compareTo(m1.hp)).collect(Collectors.toList());
			else
				monsterList = monsterList.stream().sorted((m1,m2) -> new Integer(m1.hp).compareTo(m2.hp)).collect(Collectors.toList());
			return monsterList;
		case "AC":
			if(sorter.equals("Dec"))
				monsterList = monsterList.stream().sorted((m1,m2) -> new Integer(m2.ac).compareTo(m1.ac)).collect(Collectors.toList());
			else
				monsterList = monsterList.stream().sorted((m1,m2) -> new Integer(m1.ac).compareTo(m2.ac)).collect(Collectors.toList());
			return monsterList;
		case "ConAniHP":
			if(sorter.equals("Dec"))
				monsterList = monsterList.stream().sorted((m1,m2) -> convertHP(m2).compareTo(convertHP(m1))).collect(Collectors.toList());
			else
				monsterList = monsterList.stream().sorted((m1,m2) -> convertHP(m1).compareTo(convertHP(m2))).collect(Collectors.toList());
			return monsterList;
		case "HPSaves":
			if(sorter.equals("Dec"))
				monsterList = monsterList.stream().sorted((m1,m2) -> avgHPSaves(m2).compareTo(avgHPSaves(m1))).collect(Collectors.toList());
			else
				monsterList = monsterList.stream().sorted((m1,m2) -> avgHPSaves(m1).compareTo(avgHPSaves(m2))).collect(Collectors.toList());
			return monsterList;
		case "HPAC":
			if(sorter.equals("Dec"))
				monsterList = monsterList.stream().sorted((m1,m2) -> avgHPAC(m2).compareTo(avgHPAC(m1))).collect(Collectors.toList());
			else
				monsterList = monsterList.stream().sorted((m1,m2) -> avgHPAC(m1).compareTo(avgHPAC(m2))).collect(Collectors.toList());
			return monsterList;
		case "HPACMA":
			if(sorter.equals("Dec"))
				monsterList = monsterList.stream().sorted((m1,m2) -> avgHPACMA(m2).compareTo(avgHPACMA(m1))).collect(Collectors.toList());
			else
				monsterList = monsterList.stream().sorted((m1,m2) -> avgHPACMA(m1).compareTo(avgHPACMA(m2))).collect(Collectors.toList());
			return monsterList;
		case "ConSave":
			if(sorter.equals("Dec"))
				monsterList = monsterList.stream().sorted((m1,m2) -> new Integer(m2.saves[2]).compareTo(m1.saves[2])).collect(Collectors.toList());
			else
				monsterList = monsterList.stream().sorted((m1,m2) -> new Integer(m1.saves[2]).compareTo(m2.saves[2])).collect(Collectors.toList());
			return monsterList;
		case "toHit":
			if(sorter.equals("Dec"))
				monsterList = monsterList.stream().sorted((m1,m2) -> new Integer(m2.toHitMod).compareTo(m1.toHitMod)).collect(Collectors.toList());
			else
				monsterList = monsterList.stream().sorted((m1,m2) -> new Integer(m1.toHitMod).compareTo(m2.toHitMod)).collect(Collectors.toList());
			return monsterList;
		case "AvgDmg":
			if(sorter.equals("Dec"))
				monsterList = monsterList.stream().sorted((m1,m2) -> new Double(m2.avgDamage).compareTo(m1.avgDamage)).collect(Collectors.toList());
			else
				monsterList = monsterList.stream().sorted((m1,m2) -> new Double(m1.avgDamage).compareTo(m2.avgDamage)).collect(Collectors.toList());
			return monsterList;
		case "CR":
			if(sorter.equals("Dec"))
				monsterList = monsterList.stream().sorted((m1,m2) -> new Integer(m2.cr).compareTo(m1.cr)).collect(Collectors.toList());
			else
				monsterList = monsterList.stream().sorted((m1,m2) -> new Integer(m1.cr).compareTo(m2.cr)).collect(Collectors.toList());
			return monsterList;
		case "Size":
			if(sorter.equals("Dec"))
				monsterList = monsterList.stream().sorted((m1,m2) -> m2.sizeNumber().compareTo(m1.sizeNumber())).collect(Collectors.toList());
			else
				monsterList = monsterList.stream().sorted((m1,m2) -> m1.sizeNumber().compareTo(m2.sizeNumber())).collect(Collectors.toList());
			return monsterList;
		case "AvgDmgAdj":
			if(sorter.equals("Dec"))
				monsterList = monsterList.stream().sorted((m1,m2) -> convertAvgDmg(m2).compareTo(convertAvgDmg(m1))).collect(Collectors.toList());
			else
				monsterList = monsterList.stream().sorted((m1,m2) -> convertAvgDmg(m1).compareTo(convertAvgDmg(m2))).collect(Collectors.toList());
			return monsterList;
		case "AvgDmgToHit":
			if(sorter.equals("Dec"))
				monsterList = monsterList.stream().sorted((m1,m2) -> avgDmgToHit(m2).compareTo(avgDmgToHit(m1))).collect(Collectors.toList());
			else
				monsterList = monsterList.stream().sorted((m1,m2) -> avgDmgToHit(m1).compareTo(avgDmgToHit(m2))).collect(Collectors.toList());
			return monsterList;
		case "Hybrid":
			if(sorter.equals("Dec"))
				monsterList = monsterList.stream().sorted((m1,m2) -> HybridAvg(m2).compareTo(HybridAvg(m1))).collect(Collectors.toList());
			else
				monsterList = monsterList.stream().sorted((m1,m2) -> HybridAvg(m1).compareTo(HybridAvg(m2))).collect(Collectors.toList());
			return monsterList;
		case "ConAniHPBig":
			if(sorter.equals("Dec"))
				monsterList = monsterList.stream().sorted((m1,m2) -> ConAniHPBig(m2).compareTo(ConAniHPBig(m1))).collect(Collectors.toList());
			else
				monsterList = monsterList.stream().sorted((m1,m2) -> ConAniHPBig(m1).compareTo(ConAniHPBig(m2))).collect(Collectors.toList());
			return monsterList;
		default:
			return monsterList;
		}
	}
	
	public static Integer ConAniHPBig(Monster m)
	{
		int totalHP = convertHP(m);
		return totalHP * (1+(m.hp/30));
	}
	
	public static Double HybridAvg(Monster m)
	{
		double avgDmg = Monsterbase.avgDmgToHit(m);
		double avgHp = (double)Monsterbase.convertHP(m);
		double result = 1.0*avgDmg + 0.5*avgHp;
		return result;
	}
	
	public static Double avgDmgToHit(Monster m)
	{
		int toHit = m.toHitMod;
		double avgDmg = Monsterbase.convertAvgDmg(m);
		double result = 0;
		result += avgDmg*0.075; // nat 20, 5% * 1.5 damage
		int neededToHit = BASEAC - toHit;
		if(neededToHit < 20)
		{
		if(neededToHit < 1)
			result += avgDmg*.95;
		else
			result += avgDmg * (0.05 * (20-neededToHit));
		}
		return result;
	}
	
	public static Double avgHPAC(Monster m)
	{
		double convertHP = Monsterbase.convertHP(m);
		double ACfraction = 1 + .025*(m.ac - 10);
		return ACfraction * convertHP;
	}
	
	public static Double avgHPACMA(Monster m)
	{
		double ACfraction = 1 + .025*(Math.max(m.ac,13+m.stats[2]) - 10);
		return ACfraction * m.hp;
	}
	
	public static Double avgHPSaves(Monster m)
	{
		double convertHP = Monsterbase.convertHP(m);
		double rollingDefense = .05*m.ac;
		rollingDefense += .025*m.saves[1];
		rollingDefense += .025*m.saves[2];
		rollingDefense += .025*m.saves[4];
		rollingDefense /= 7;
		return rollingDefense * convertHP;
	}

	public static Double convertAvgDmg(Monster m)
	{
		int cr = m.cr;
		double dmg = m.avgDamage;
		double tempCR = cr;
		if (tempCR < 0)
			tempCR = -1 / tempCR;
		double ratio = BASECR / tempCR;
		double result = (ratio * dmg);
		return result;
	}

	public static Integer convertHP(Monster m)
	{
		int cr = m.cr;
		int hp = m.hp;
		double tempCR = cr;
		if (tempCR < 0)
			tempCR = -1 / tempCR;
		double ratio = BASECR / tempCR;
		int result = (int)(ratio * hp);
		return result;
	}

	private static List<Monster> addToList(List<Monster> monsterList, String addType, String addText)
	{
		List<Monster >tempMonsterList = new ArrayList<>(refMonsterList);
		switch(addType)
		{
		case "Type":
			tempMonsterList = tempMonsterList.stream().filter(m -> m.type == Type.toType(addText)).collect(Collectors.toList());
			monsterList.addAll(tempMonsterList);
			return monsterList;
		default:
			return monsterList;
		}
	}
	
	private static List<Monster> filterBy(List<Monster> monsterList, String filterType, String filter)
	{
		//List<Monster> newMonsterList = new ArrayList<Monster>();
		switch(filterType)
		{
		case "hasInfo":
			monsterList = monsterList.stream().filter(m -> m.hasInfo == Boolean.parseBoolean(filter)).collect(Collectors.toList());
			return monsterList;
		case "Type":
			monsterList = monsterList.stream().filter(m -> m.type == Type.toType(filter)).collect(Collectors.toList());
			return monsterList;
		case "CR":
			String[] temp = filter.split(" ");
			int[] minAndMax = {Integer.parseInt(temp[0]),Integer.parseInt(temp[1])};
			monsterList = monsterList.stream().filter(m -> m.cr >= minAndMax[0] && m.cr <= minAndMax[1]).collect(Collectors.toList());
			return monsterList;
		case "Trim":
			monsterList = monsterList.stream().filter(m -> !m.name.contains(filter)).collect(Collectors.toList());
			return monsterList;
		case "Search":
			monsterList = monsterList.stream().filter(m -> m.fullText.toLowerCase().contains(filter.toLowerCase())).collect(Collectors.toList());
			return monsterList;
		case "Subtype":
			monsterList = monsterList.stream().filter(m -> m.subtype.toLowerCase().contains(filter.toLowerCase())).collect(Collectors.toList());
			return monsterList;
		case "SearchSpeed":
			monsterList = monsterList.stream().filter(m -> m.speeds.toLowerCase().contains(filter.toLowerCase())).collect(Collectors.toList());
			return monsterList;
		case "SearchImmunities":
			monsterList = monsterList.stream().filter(m -> m.dmgImm.toLowerCase().contains(filter.toLowerCase())).collect(Collectors.toList());
			return monsterList;
		case "SearchConditionImmunities":
			monsterList = monsterList.stream().filter(m -> m.condImm.toLowerCase().contains(filter.toLowerCase())).collect(Collectors.toList());
			return monsterList;
		default:
			return monsterList;
		}
	}



	private static void displaySaves(List<Monster> monsterList, int crMin, int crMax)
	{
		double[] avgSaves = new double[6];
		int count = 0;
		for(int i=0; i<monsterList.size(); i++)
		{
			if(monsterList.get(i).hasInfo && monsterList.get(i).cr >= crMin && monsterList.get(i).cr <= crMax) {
				for(int j=0;j<6;j++)
				{
					avgSaves[j] += monsterList.get(i).saves[j];
				}
				count++;
			}
		}
		System.out.println("Saves for creatures between CR " + crMin +", " + crMax);
		System.out.println("Avg Str Save: "+avgSaves[0]/count);
		System.out.println("Avg Dex Save: "+avgSaves[1]/count);
		System.out.println("Avg Con Save: "+avgSaves[2]/count);
		System.out.println("Avg Int Save: "+avgSaves[3]/count);
		System.out.println("Avg Wis Save: "+avgSaves[4]/count);
		System.out.println("Avg Cha Save: "+avgSaves[5]/count);
	}
	
	private static void displayAC(List<Monster> monsterList, int crMin, int crMax)
	{
		double avgAC = 0;
		int count = 0;
		for(int i=0; i<monsterList.size(); i++)
		{
			if(monsterList.get(i).hasInfo && monsterList.get(i).cr >= crMin && monsterList.get(i).cr <= crMax) {
				avgAC += monsterList.get(i).ac;
				count++;
			}
		}
		System.out.println("AC for creatures between CR " + crMin +", " + crMax + " is: " + avgAC/count);
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
	public static List<Monster> readFromFile()
	{
		try
		{
			FileInputStream inputFile = new FileInputStream(FILENAME);
			ObjectInputStream objIn = new ObjectInputStream(inputFile);
			List<Monster> readSpellList = (ArrayList<Monster>)objIn.readObject();
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

	public static void outputToFile(List<Monster> monsterList)
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

	public static void extractMonsters(int num, List<Monster> monsterList)
	{
		try
		{
			File input = new File("src/Monster-Pages/Monster-Page"+num+".html");
			Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

			Element curr = doc.child(0).child(1).child(0).child(0);

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