package com.madpc.core.world;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;


public class WorldHandler
{
	private HashMap<Integer, World> worlds = new HashMap<Integer, World>();
	public static WorldHandler instance = new WorldHandler();
	
	private WorldHandler() { }
	
	public World getWorld(int id)
	{
		return worlds.get(id);
	}
	
	public void registerWorld(int id, Object ... args)
	{
		World w = new World(id, args);
		worlds.put(id, w);
	}
	
	public Set<Entry<Integer, World>> getWorldSet()
	{
		return worlds.entrySet();
	}
}
