package universal;

import java.util.*;

import items.*;

public class Comparators
{
	public static Comparator<MagicItem> sortItemByRarity()
	{
		return (MagicItem item1, MagicItem item2) -> 
			Rarity.rarityToInt(item1.rarity.getContentRarity())-Rarity.rarityToInt(item2.rarity.getContentRarity());
	}
}
