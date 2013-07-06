package com.madpc.core.world;

import com.madpc.core.block.Block;
import com.madpc.core.block.Debug;
import com.madpc.core.world.gen.Generator;


public class World
{
	private static final Generator NORM = new Generator((Object)null);
	private static final Debug d = new Debug();
	
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
	
	public Block getBlockAt(int x, int y, int z)
	{
		return d;
	}
}
