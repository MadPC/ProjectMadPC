package com.madpc.rendering.core;

import com.madpc.core.block.Block;
import com.madpc.core.world.World;
import com.madpc.core.world.WorldHandler;


public class Renderer
{
	public Renderer() { }
	
	public void  render()
	{
		World wo = WorldHandler.instance.getWorld(0);
		if (wo != null)
		{
			for (int x = -400; x < 401; x++)
			{
				for (int y = -300; y < 301; y++)
				{
					for (int z = -1; z < 2; z++)
					{
						Block b = wo.getBlockAt(x, y, z);
						
					}
				}
			}
		}
	}
}
