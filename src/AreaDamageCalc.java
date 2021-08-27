import java.util.Scanner;

public class AreaDamageCalc {
	
	private static Scanner input;

	public static void main(String[] args)
	{
		input = new Scanner(System.in);
		// area type, then size, then # of die, then type
		AOEType aoe = AOEType.toAOEType(getResponse("Choose a type of area:",new String[] {"Line","Cone","Cube","Sphere","Cylinder"}));
		int size = getResponse("What is the size of the area?",null);
		int dieNum = getResponse("How many die are rolled?",null);
		int dieSize = getResponse("What size die are rolled?",null);
		System.out.println("Avg damage: "+avgDamage(dieNum,dieSize)+", Avg squares hit: "+aoeArea(aoe,size));
	}
	private static int getResponse(String q,String[] ans){
		System.out.println(q);
		if(ans!=null)
		{
		for(int i = 1; i <= ans.length; i++) {
			System.out.println(i + ": " + ans[i-1]);
		}
		}
		int i = Integer.parseInt(input.nextLine());
		return i;
	}
	public static double aoeArea(AOEType aoe, int size)
	{
		size/=5;
		int spaces = 0;
		switch(aoe)
		{
		case Line:
			spaces=size;
			break;
		case Cone:
			for(int i = 1; i<=size;i++)
				spaces+=i;
			break;
		case Cube:
			spaces = size*size;
			break;
		case Sphere:
		case Cylinder:// tests center of square, if too far from center out of circle
			for(int i = 0;i < size;i++)
			{
				for(int j = 0;j < size;j++)
				{
					double dist = Math.pow(i+0.5,2)+Math.pow(j+0.5,2);
					if(dist<Math.pow(size,2))
						spaces++;
				}
			}	
			spaces*=4;
			break;
		default:
			break;
		}
		return spaces;
	}
	public static double avgDamage(int dieNum, int dieSize)
	{
		return (((double)dieSize+1)/2)*dieNum;
	}
}
