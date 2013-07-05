package com.madpc.rendering.core;

import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;


public class GLProgrammer
{
	
	public static int buildProgram(String vertex, String frag)
	{
		int prog = 0;
		
		int[] shaders = genShaders(vertex, frag);
		
		prog = GL20.glCreateProgram();
		
		for (int i : shaders)
		{
			GL20.glAttachShader(prog, i);
		}
		
		GL20.glLinkProgram(prog);
		if (glGetProgrami(prog, GL_LINK_STATUS) == GL11.GL_FALSE) 
		{
			throw new RuntimeException("could not link program. Reason: " + glGetProgramInfoLog(prog, 1000));
		}
		
		// perform general validation that the program is usable
		glValidateProgram(prog);
		if (glGetProgrami(prog, GL_VALIDATE_STATUS) == GL11.GL_FALSE)
		{
			throw new RuntimeException("could not validate program. Reason: " + glGetProgramInfoLog(prog, 1000));			
		}
		
		for (int i : shaders)
		{
			GL20.glDetachShader(prog, i);
			GL20.glDeleteShader(i);
		}
		
		return prog;
	}
	
	private static int[] genShaders(String vertex, String frag)
	{
		int[] ret = new int[2];
		
		ret[0] = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		GL20.glShaderSource(ret[0], readFile(vertex));
		
		GL20.glCompileShader(ret[0]);
		// acquire compilation status
	 	int shaderStatus = glGetShaderi(ret[0], GL20.GL_COMPILE_STATUS);
	 	// check whether compilation was successful
	 	if( shaderStatus == GL11.GL_FALSE)
	 	{
	 		throw new IllegalStateException("compilation error for shader ["+vertex+"]. Reason: " + glGetShaderInfoLog(ret[0], 1000));
	 	}
		
		ret[1] = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		GL20.glShaderSource(ret[1], readFile(frag));
		
		GL20.glCompileShader(ret[1]);
		// acquire compilation status
	 	shaderStatus = glGetShaderi(ret[1], GL20.GL_COMPILE_STATUS);
	 	// check whether compilation was successful
	 	if( shaderStatus == GL11.GL_FALSE)
	 	{
	 		throw new IllegalStateException("compilation error for shader ["+frag+"]. Reason: " + glGetShaderInfoLog(ret[1], 1000));
	 	}
		
		return ret;
	}
	
	private static CharBuffer readFile(String name)
	{
		String file = "";
		try(BufferedReader s = new BufferedReader(new FileReader(name)))
		{
			StringBuilder sb = new StringBuilder();
			
			String line;
			while ((line = s.readLine()) != null)
			{
				sb.append(line).append('\n');
			}
			file = sb.toString();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		CharBuffer b = BufferUtils.createCharBuffer(file.length());
		b.put(file);
		b.flip();
		return b;
	}
}
