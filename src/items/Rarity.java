package items;

public enum Rarity
{
	Common,
	Uncommon,
	Rare,
	VeryRare,
	Legendary,
	Artifact,
	Varies,
	Unknown,
	NA;
	
	public static Rarity toRarity(String s)
	{
		if(s.contains("unknown"))
			return Unknown;
		if(s.contains("varies"))
			return Varies;
		if(s.contains("artifact"))
			return Artifact;
		if(s.contains("legendary"))
			return Legendary;
		if(s.contains("veryrare"))
			return VeryRare;
		if(s.contains("rare"))
			return Rare;
		if(s.contains("uncommon"))
			return Uncommon;
		if(s.contains("common"))
			return Common;
		System.out.println("Failed to id rarity: "+s);
		return NA;
	}
}
