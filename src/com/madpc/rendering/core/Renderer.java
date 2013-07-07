package com.madpc.rendering.core;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import com.madpc.core.Main;
import com.madpc.core.block.Block;
import com.madpc.core.world.World;
import com.madpc.core.world.WorldHandler;
import com.madpc.rendering.data.Meshed;


public class Renderer
{
	private Mesher mesh;
	private int normProgram;
	private static int WIDTH;
	private static int HEIGHT;
	private int offsetloc;
	
	/**
	 * Offset matrix used for things like rotation and offsets<br>
	 * (:D)
	 */
	private Matrix4f off = new Matrix4f();
	
	/**
	 * The Buffer in wich the Offset matrix is loaded to be passed to openGL
	 */
	private FloatBuffer offb = BufferUtils.createFloatBuffer(16);
	
	public Renderer() 
	{
		mesh = new Mesher();
		mesh.generateBases();
		normProgram = GLProgrammer.buildProgram(Main.getWorkingDir() + "com/madpc/rendering/shaders/NormvertexShader.vert", Main.getWorkingDir() + "com/madpc/rendering/shaders/NormfragShader.frag");
		
		WIDTH = 800;
		HEIGHT = 600;
		
		glClearDepth(1.0f);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		glViewport(0, 0, WIDTH, HEIGHT);
		
		glEnable(GL_DEPTH_TEST);
		glDepthMask(true);
		glDepthFunc(GL_LEQUAL);
		glDepthRange(0.0f, 1.0f);
		
		//glEnable(GL_DEPTH_CLAMP);
		
		//Setting to 1.0f is useless, but they will be needed at some point
		//off.m00 = 1.0f;
		//off.m11 = 1.0f;
		//off.m22 = 1.0f;
		offb.clear();
		off.store(offb);
		offb.flip();
		
		offsetloc = glGetUniformLocation(normProgram, "offset");
	}
	
	public void  render()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glUseProgram(normProgram);
		
		//System.out.println(offsetloc);
		
		glUniform3f(offsetloc, 0.5f, 0.5f, 0.0f);
		
		//glUniformMatrix4(offsetloc, false, offb);
		
		World wo = WorldHandler.instance.getWorld(0);
		if (wo != null)
		{
			Meshed m = mesh.getMesh(0);
			
			GL30.glBindVertexArray(m.getVao());
			glDrawElements(GL_TRIANGLES, m.getIndiceLength(), GL_UNSIGNED_SHORT, 0);
			GL30.glBindVertexArray(0);
			
			/*for (int x = -400; x < 401; x++)
			{
				for (int y = -300; y < 301; y++)
				{
					for (int z = -1; z < 2; z++)
					{
						Block b = wo.getBlockAt(x, y, z);
						Meshed m = mesh.getMesh(0);
						
						GL30.glBindVertexArray(m.getVao());
						glDrawElements(GL_TRIANGLES, m.getIndiceLength(), GL_UNSIGNED_SHORT, 0);
						GL30.glBindVertexArray(0);
						
					}
				}
			}*/
		}
		glUseProgram(0);
	}
	
	
}
