
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
		case "i-aoe-line":
			return AOEType.Line;
		case "i-aoe-cone":
			return AOEType.Cone;
		case "i-aoe-cube":
			return AOEType.Cube;
		case "i-aoe-sphere":
			return AOEType.Sphere;
		case "i-aoe-cylinder":
			return AOEType.Cylinder;
		default:
			return null;
		}
	}
}
