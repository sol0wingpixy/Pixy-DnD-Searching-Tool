package monsters;

public enum Type {
	Aberration,
	Beast,
	Celestial,
	Construct,
	Dragon,
	Elemental,
	Fey,
	Fiend,
	Giant,
	Humanoid,
	Monstrosity,
	Ooze,
	Plant,
	Undead,
	NA;

	public static Type toType(String s)
	{
		s = s.toLowerCase();
		if(s.contains("aberration"))
		{
			return Type.Aberration;
		}
		if(s.contains("beast"))
		{
			return Type.Beast;
		}
		if(s.contains("celestial"))
		{
			return Type.Celestial;
		}
		if(s.contains("construct"))
		{
			return Type.Construct;
		}
		if(s.contains("dragon"))
		{
			return Type.Dragon;
		}
		if(s.contains("elemental"))
		{
			return Type.Elemental;
		}
		if(s.contains("fey"))
		{
			return Type.Fey;
		}
		if(s.contains("fiend"))
		{
			return Type.Fiend;
		}
		if(s.contains("giant"))
		{
			return Type.Giant;
		}
		if(s.contains("humanoid"))
		{
			return Type.Humanoid;
		}
		if(s.contains("monstrosity"))
		{
			return Type.Monstrosity;
		}
		if(s.contains("ooze"))
		{
			return Type.Ooze;
		}
		if(s.contains("plant"))
		{
			return Type.Plant;
		}
		if(s.contains("undead"))
		{
			return Type.Undead;
		}
		return null;
	}
}
