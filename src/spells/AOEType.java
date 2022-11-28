package spells;

public enum AOEType {
	Line,
	Cone,
	Cube,
	Sphere,
	Cylinder;
	
	public static AOEType toAOEType(String s)
	{
		switch(s)
		{
		case "Line":
		case "i-aoe-line":
			return AOEType.Line;
		case "Cone":
		case "i-aoe-cone":
			return AOEType.Cone;
		case "Cube":
		case "i-aoe-cube":
			return AOEType.Cube;
		case "Sphere":
		case "i-aoe-sphere":
			return AOEType.Sphere;
		case "Cylinder":
		case "i-aoe-cylinder":
			return AOEType.Cylinder;
		default:
			return null;
		}
	}
	public static AOEType toAOEType(int i)
	{
		switch(i)
		{
		case 1:
			return AOEType.Line;
		case 2:
			return AOEType.Cone;
		case 3:
			return AOEType.Cube;
		case 4:
			return AOEType.Sphere;
		case 5:
			return AOEType.Cylinder;
		default:
			return null;
		}
	}
}
