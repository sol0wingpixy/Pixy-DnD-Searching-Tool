
public enum Class {
	Artificer,
	Bard,
	Cleric,
	Druid,
	Paladin,
	Ranger,
	Sorcerer,
	Warlock,
	Wizard;
	
	public static Class toClass(String s)
	{
		switch(s)
		{
		case "Artificer":
			return Class.Artificer;
		case "Bard":
			return Class.Bard;
		case "Cleric":
			return Class.Cleric;
		case "Druid":
			return Class.Druid;
		case "Paladin":
			return Class.Paladin;
		case "Ranger":
			return Class.Ranger;
		case "Sorcerer":
			return Class.Sorcerer;
		case "Warlock":
			return Class.Warlock;
		case "Wizard":
			return Class.Wizard;
		default:
			return null;
		}
	}
	public static Class toClass(int i)
	{
		switch(i)
		{
		case 1:
			return Class.Artificer;
		case 2:
			return Class.Bard;
		case 3:
			return Class.Cleric;
		case 4:
			return Class.Druid;
		case 5:
			return Class.Paladin;
		case 6:
			return Class.Ranger;
		case 7:
			return Class.Sorcerer;
		case 8:
			return Class.Warlock;
		case 9:
			return Class.Wizard;
		default:
			return null;
		}
	}
	public static String[] classesAsString()
	{
		return new String[]{"Artificer","Bard","Cleric","Druid","Paladin","Ranger","Sorcerer","Warlock","Wizard"};
	}
}
