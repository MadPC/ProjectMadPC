package com.madpc.rendering.core;

import java.nio.CharBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;


public class GLProgrammer
{
	
	public static int buildProgram(String vertex, String frag)
	{
		int prog = 0;
		
		int[] shaders = genShaders(vertex, frag);
		
		prog = GL20.glCreateProgram();
		return prog;
	}
	
	private static int[] genShaders(String vertex, String frag)
	{
		int[] ret = new int[2];
		
		ret[0] = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		
		GL20.glShaderSource(ret[0], );
		
		return ret;
	}
	
	private static CharBuffer readFile(String name)
	{
		try()
		{
			
		}
		CharBuffer b = BufferUtils.createCharBuffer(0);
		b.flip();
		return b;
	}
}
