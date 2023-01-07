package universal;

import java.io.*;
import java.util.*;

import spells.*;
import spells.Class;

import monsters.*;
import monsters.Type;

import items.*;

public class IndexedItem implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public Object content;
	public IndexKind indexKind;
	
	public IndexedItem(Object o,IndexKind k)
	{
		this.content = o;
		this.indexKind = k;
	}
	
	public boolean equals(IndexedItem i)
	{
		return this.content.equals(i.content);
	}

	
	public boolean hasIndex(Spell s)
	{
		switch(indexKind)
		{
		case SpellName:
			return this.getContentString().equals(s.name.getContentString());
		case SpellLevel:
			return this.getContentInt() == s.level.getContentInt();
		case SpellClass:
			return s.classes.getContentListClass().contains(this.getContentClass());
		case SpellCastingTime:
			return s.castTime.getContentString().contains(this.getContentString());
		case SpellDuration:
			return s.duration.getContentString().equals(this.getContentString());
		case SpellIsConcentration:
			return this.getContentBoolean() == s.duration.getContentString().contains("Concentration");
		default:
			return false;	
		}
	}
	
	public boolean hasIndex(Monster m)
	{
		switch(indexKind)
		{
		case MonsterType:
			return this.getContentType().equals(m.type.getContentType());
		default:
			return false;
		}
	}
	
	public boolean hasIndex(MagicItem mi)
	{
		switch(indexKind)
		{
		default:
			return false;
		}
	}
	
	public boolean getContentBoolean()
	{
		try
		{
			return (Boolean)content;
		} 
		catch(ClassCastException e)
		{
			System.out.println("Attempted to cast invalid boolean");
			e.printStackTrace();
			return false;
		}
	}
	
	public int getContentInt()
	{
		try
		{
			return (Integer)content;
		} 
		catch(ClassCastException e)
		{
			System.out.println("Attempted to cast invalid int");
			e.printStackTrace();
			return 0;
		}
	}
	
	public String getContentString()
	{
		try
		{
			return (String)content;
		} 
		catch(ClassCastException e)
		{
			System.out.println("Attempted to cast invalid string");
			e.printStackTrace();
			return "Object not a string";
		}
	}
	
	public Type getContentType()
	{
		try
		{
			return (Type)content;
		} 
		catch(ClassCastException e)
		{
			System.out.println("Attempted to cast invalid type");
			e.printStackTrace();
			return Type.NA;
		}
	}
	
	public Class getContentClass()
	{
		try
		{
			return (Class)content;
		} 
		catch(ClassCastException e)
		{
			System.out.println("Attempted to cast invalid class");
			e.printStackTrace();
			return Class.NA;
		}
	}
	
	public List<Class> getContentListClass()
	{
		try
		{
			return (List<Class>)content;
		} 
		catch(ClassCastException e)
		{
			System.out.println("Attempted to cast invalid list of class");
			e.printStackTrace();
			return new ArrayList<Class>();
		}
	}
	
	public Rarity getContentRarity()
	{
		try
		{
			return (Rarity)content;
		} 
		catch(ClassCastException e)
		{
			System.out.println("Attempted to cast invalid rarity");
			e.printStackTrace();
			return Rarity.NA;
		}
	}
	
	public String toString()
	{
		return content.toString();
	}
	
	public static final IndexedItem TypeUndead = new IndexedItem(Type.Undead,IndexKind.MonsterType);
	public static final IndexedItem TypeBeast = new IndexedItem(Type.Beast,IndexKind.MonsterType);

	
	public static IndexedItem CR(int i)
	{
		return new IndexedItem(i,IndexKind.MonsterCR);
	}
}
