package com.madpc.rendering.core;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;

import com.madpc.rendering.data.Meshed;

/**
 * This only has to do with the Graphical Meshes! not With Collision!
 * @author reinei
 */
public class Mesher
{
	
	private static final float RIGHT_EXTENT = 0.8f;
	private static final float LEFT_EXTENT = -RIGHT_EXTENT;
	private static final float TOP_EXTENT = 0.20f;
	private static final float MIDDLE_EXTENT = 0.0f;
	private static final float BOTTOM_EXTENT = -TOP_EXTENT;
	private static final float FRONT_EXTENT = -1.25f;
	private static final float REAR_EXTENT = -1.75f;
	
	private static final float[] CUBE = new float[] {
	//	 X		Y	 Z
		0.0f, 0.0f, 0.0f, //TOP LEFT
		0.0f, -1.0f, 0.0f,//BOTTOM LEFT
		1.0f, 0.0f, 0.0f, //TOP RIGHT
		1.0f, -1.0f, 0.0f,//BOTOOM RIGHT
		
		0.0f, 0.0f, -1.0f, //TOP LEFT
		0.0f, -1.0f, -1.0f,//BOTTOM LEFT
		1.0f, 0.0f, -1.0f, //TOP RIGHT
		1.0f, -1.0f, -1.0f,//BOTTOM RIGHT
		
		/*LEFT_EXTENT,	TOP_EXTENT,		REAR_EXTENT,
		LEFT_EXTENT,	MIDDLE_EXTENT,	FRONT_EXTENT,
		RIGHT_EXTENT,	MIDDLE_EXTENT,	FRONT_EXTENT,
		RIGHT_EXTENT,	TOP_EXTENT,		REAR_EXTENT, //12

		LEFT_EXTENT,	BOTTOM_EXTENT,	REAR_EXTENT,
		LEFT_EXTENT,	MIDDLE_EXTENT,	FRONT_EXTENT,
		RIGHT_EXTENT,	MIDDLE_EXTENT,	FRONT_EXTENT,
		RIGHT_EXTENT,	BOTTOM_EXTENT,	REAR_EXTENT, //12

		LEFT_EXTENT,	TOP_EXTENT,		REAR_EXTENT,
		LEFT_EXTENT,	MIDDLE_EXTENT,	FRONT_EXTENT, //30
		LEFT_EXTENT,	BOTTOM_EXTENT,	REAR_EXTENT,

		RIGHT_EXTENT,	TOP_EXTENT,		REAR_EXTENT, //12
		RIGHT_EXTENT,	MIDDLE_EXTENT,	FRONT_EXTENT,
		RIGHT_EXTENT,	BOTTOM_EXTENT,	REAR_EXTENT,

		LEFT_EXTENT,	BOTTOM_EXTENT,	REAR_EXTENT,
		LEFT_EXTENT,	TOP_EXTENT,		REAR_EXTENT, //12
		RIGHT_EXTENT,	TOP_EXTENT,		REAR_EXTENT,
		RIGHT_EXTENT,	BOTTOM_EXTENT,	REAR_EXTENT, //6
													 //TOTAL:54
		//Object 2 positions
		TOP_EXTENT,		RIGHT_EXTENT,	REAR_EXTENT,
		MIDDLE_EXTENT,	RIGHT_EXTENT,	FRONT_EXTENT,
		MIDDLE_EXTENT,	LEFT_EXTENT,	FRONT_EXTENT,
		TOP_EXTENT,		LEFT_EXTENT,	REAR_EXTENT, //12

		BOTTOM_EXTENT,	RIGHT_EXTENT,	REAR_EXTENT,
		MIDDLE_EXTENT,	RIGHT_EXTENT,	FRONT_EXTENT,
		MIDDLE_EXTENT,	LEFT_EXTENT,	FRONT_EXTENT,
		BOTTOM_EXTENT,	LEFT_EXTENT,	REAR_EXTENT, //12

		TOP_EXTENT,		RIGHT_EXTENT,	REAR_EXTENT,
		MIDDLE_EXTENT,	RIGHT_EXTENT,	FRONT_EXTENT,
		BOTTOM_EXTENT,	RIGHT_EXTENT,	REAR_EXTENT,
						
		TOP_EXTENT,		LEFT_EXTENT,	REAR_EXTENT, //12
		MIDDLE_EXTENT,	LEFT_EXTENT,	FRONT_EXTENT,
		BOTTOM_EXTENT,	LEFT_EXTENT,	REAR_EXTENT,
						
		BOTTOM_EXTENT,	RIGHT_EXTENT,	REAR_EXTENT,
		TOP_EXTENT,		RIGHT_EXTENT,	REAR_EXTENT, //12
		TOP_EXTENT,		LEFT_EXTENT,	REAR_EXTENT,
		BOTTOM_EXTENT,	LEFT_EXTENT,	REAR_EXTENT*/
	};
	
	private static final short[] NORMALIZE_INDICES = new short[] {
		0, 3, 1,	0, 2, 3,
		4, 7, 5,	4, 6, 7
		
		/*0, 2, 1,
		3, 2, 0,

		4, 5, 6,
		6, 7, 4,

		8, 9, 10,
		11, 13, 12,

		14, 16, 15,
		17, 16, 14,*/
	};
	
	/**
	 * Maps Block ids To they respective VAO binding ID.
	 */
	private HashMap<Integer, Meshed> meshes = new HashMap<Integer, Meshed>();
	
	public void generateBases()
	{
		int cubeAO = glGenVertexArrays();
		glBindVertexArray(cubeAO);
			
			//glEnable(GL_CULL_FACE);
	   		//glCullFace(GL_BACK);
	    	//glFrontFace(GL_CW);
			
			FloatBuffer b = BufferUtils.createFloatBuffer(CUBE.length);
			b.put(CUBE);
			b.flip();
			
			int vertices = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vertices);
			glBufferData(GL_ARRAY_BUFFER, b, GL_STATIC_DRAW); 
			glEnableVertexAttribArray(0);
			glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			
			ShortBuffer b2 = BufferUtils.createShortBuffer(NORMALIZE_INDICES.length);
			b2.put(NORMALIZE_INDICES);
			b2.flip();
			
			int indices = glGenBuffers();
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indices);
			
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, b2, GL_STATIC_DRAW);
			
		glBindVertexArray(0);
		glDisableVertexAttribArray(0);
		
		Meshed m = new Meshed(cubeAO, vertices, indices);
		m.setIndiceLength(NORMALIZE_INDICES.length);
		meshes.put(0, m);
	}
	
	public Meshed getMesh(int index)
	{
		return this.meshes.get(index);
	}
}
