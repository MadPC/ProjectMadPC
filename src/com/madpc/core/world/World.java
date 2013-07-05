package com.madpc.core.world;

import com.madpc.core.world.gen.Generator;


public class World
{
	private static final Generator NORM = new Generator((Object)null);
	
	private int id;
	private Generator gen;
	
	public World(int id)
	{
		this.id = id;
		gen = NORM;
	}
	
	public World(int id, Object ... args)
	{
		this.id = id;
		gen = new Generator(args);
	}
}
