package com.madpc.rendering.core;

import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;


public class GLProgrammer
{
	
	private static float fFrustumScale = 1.0f;
	private static float fzNear = 0.5f;
	private static float fzFar = 3.0f;
	
	private static Matrix4f camToClip = new Matrix4f();
	private static FloatBuffer convBuff = BufferUtils.createFloatBuffer(16);
	
	public static int buildProgram(String vertex, String frag)
	{
		int prog = 0;
		
		int[] shaders = genShaders(vertex, frag);
		
		prog = glCreateProgram();
		
		for (int i : shaders)
		{
			glAttachShader(prog, i);
		}
		
		glLinkProgram(prog);
		if (glGetProgrami(prog, GL_LINK_STATUS) == GL11.GL_FALSE) 
		{
			throw new RuntimeException("Could not link program. Reason: " + glGetProgramInfoLog(prog, 1000));
		}
		
		// perform general validation that the program is usable
		glValidateProgram(prog);
		if (glGetProgrami(prog, GL_VALIDATE_STATUS) == GL11.GL_FALSE)
		{
			throw new RuntimeException("Could not validate program. Reason: " + glGetProgramInfoLog(prog, 1000));			
		}
		
		glUseProgram(prog);
		
		camToClip.m00 = fFrustumScale / (800 / (float)600);
		camToClip.m11 = fFrustumScale;
		camToClip.m22 = (fzFar + fzNear) / (fzNear - fzFar);
		camToClip.m23 = (2 * fzFar * fzNear) / (fzNear - fzFar);
		camToClip.m32 = -1.0f;
		
		convBuff.clear();
		camToClip.store(convBuff);
		convBuff.flip();
		
		int unif = glGetUniformLocation(prog, "perspectiveMatrix");
		
		//System.out.println(unif);
		
		glUniformMatrix4(unif, false, convBuff);
		
		glUseProgram(0);
		
		for (int i : shaders)
		{
			glDetachShader(prog, i);
			glDeleteShader(i);
		}
		
		return prog;
	}
	
	private static int[] genShaders(String vertex, String frag)
	{
		int[] ret = new int[2];
		
		ret[0] = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(ret[0], readFile(vertex));
		
		glCompileShader(ret[0]);
		// acquire compilation status
	 	int shaderStatus = glGetShaderi(ret[0], GL_COMPILE_STATUS);
	 	// check whether compilation was successful
	 	if( shaderStatus == GL11.GL_FALSE)
	 	{
	 		throw new IllegalStateException("compilation error for shader ["+vertex+"]. Reason: " + glGetShaderInfoLog(ret[0], 1000));
	 	}
		
		ret[1] = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(ret[1], readFile(frag));
		
		glCompileShader(ret[1]);
		// acquire compilation status
	 	shaderStatus = glGetShaderi(ret[1], GL_COMPILE_STATUS);
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
