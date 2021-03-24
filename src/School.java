
public enum School {
	Abjuration,
	Conjuration,
	Divination,
	Enchantment,
	Evocation,
	Illusion,
	Necromancy,
	Transmutation;
	
	public static School toSchool(String s)
	{
		switch(s)
		{
		case "Abjuration":
			return School.Abjuration;
		case "Conjuration":
			return School.Conjuration;
		case "Divination":
			return School.Divination;
		case "Enchantment":
			return School.Enchantment;
		case "Evocation":
			return School.Evocation;
		case "Illusion":
			return School.Illusion;
		case "Necromancy":
			return School.Necromancy;
		case "Transmutation":
			return School.Transmutation;
		default:
			return null;
		}
	}
}
