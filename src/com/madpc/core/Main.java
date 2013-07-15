package com.madpc.core;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import com.madpc.core.exceptions.ResolutionException;
import com.madpc.core.world.WorldHandler;
import com.madpc.rendering.core.Renderer;

public class Main implements Runnable{
	
	private static DisplayMode[] modes;
	private static String workingDir;

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

	private Renderer g;
	
	@Override
	public void run() {
		/**
		 * Get the path of the fiels for shaderloading
		 */
		String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		try
		{
			workingDir = URLDecoder.decode(path, "UTF-8");
		}
		catch (UnsupportedEncodingException e)	{ e.printStackTrace(); }
		
		try
		{
			PixelFormat pixelFormat = new PixelFormat();
			ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
			.withForwardCompatible(true)
			.withProfileCore(true);
			Display.setDisplayMode(getMode(800, 600));
			Display.create(pixelFormat, contextAtrributes);
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		g = new Renderer();
		
		WorldHandler.instance.registerWorld(0, (Object)null);
		
		while (!Display.isCloseRequested())
		{
			g.render();
			Display.update();
			Display.sync(60);
		}
	}
	
	public static DisplayMode getMode(int w, int h)
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
	
	public static String getWorkingDir()
	{
		return workingDir;
	}

}
