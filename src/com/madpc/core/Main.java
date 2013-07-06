package com.madpc.core;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.madpc.core.exceptions.ResolutionException;
import com.madpc.core.world.WorldHandler;
import com.madpc.rendering.core.Renderer;

public class Main implements Runnable{
	
	private static DisplayMode[] modes;
	
	static
	{
		try
		{
			modes = Display.getAvailableDisplayModes();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main m = new Main();
		m.run();
	}

	private Renderer g = new Renderer();
	
	@Override
	public void run() {
		try
		{
			Display.setDisplayMode(getMode(800, 600));
			Display.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		WorldHandler.instance.registerWorld(0, (Object)null);
		
		while (!Display.isCloseRequested())
		{
			g.render();
			Display.update();
			Display.sync(60);
		}
	}
	
	public DisplayMode getMode(int w, int h)
	{
		for (DisplayMode m : modes)
		{
			if (m.getHeight() == h && m.getWidth() == w)
				return m;
		}
		if (w == 800 && h == 600)
			throw new ResolutionException("Resolution 800x600 not Found! (What system do you use?!)");
		return null;
	}

}
