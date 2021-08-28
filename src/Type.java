
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
	Undead;

	public static Type toType(String s)
	{
		switch(s)
		{
		case "Aberration":
			return Type.Aberration;
		case "Beast":
			return Type.Beast;
		case "Celestial":
			return Type.Celestial;
		case "Construct":
			return Type.Construct;
		case "Dragon":
			return Type.Dragon;
		case "Elemental":
			return Type.Elemental;
		case "Fey":
			return Type.Fey;
		case "Fiend":
			return Type.Fiend;
		case "Giant":
			return Type.Giant;
		case "Humanoid":
			return Type.Humanoid;
		case "Monstrosity":
			return Type.Monstrosity;
		case "Ooze":
			return Type.Ooze;
		case "Plant":
			return Type.Plant;
		case "Undead":
			return Type.Undead;
		default:
			return null;
		}
	}
}
