package com.madpc.rendering.data;


public class Meshed
{
	private int vao;
	private int vertices;
	private int indices;
	private int IndiceLength;
	

	public Meshed(int vao, int vertices, int indices)
	{
		this(vao, vertices, indices, 0);
	}
	
	public Meshed(int vao, int vertices, int indices, int indiceLength)
	{
		this.vao = vao;
		this.vertices = vertices;
		this.indices = indices;
		this.IndiceLength = indiceLength;
	}
	
	public int getVao()
	{
		return vao;
	}

	
	public int getVertices()
	{
		return vertices;
	}

	
	public int getIndices()
	{
		return indices;
	}

	
	public void setVertices(int vertices)
	{
		this.vertices = vertices;
	}

	
	public void setIndices(int indices)
	{
		this.indices = indices;
	}

	public int getIndiceLength()
	{
		return IndiceLength;
	}
	
	public void setIndiceLength(int indiceLength)
	{
		IndiceLength = indiceLength;
	}
}
