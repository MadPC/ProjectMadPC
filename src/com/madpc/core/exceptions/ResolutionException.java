package com.madpc.core.exceptions;


public class ResolutionException extends RuntimeException
{

	private static final long	serialVersionUID	= -3995590391044253671L;
	
	public ResolutionException()
	{
		this("NULL");
	}
	
	public ResolutionException(String mes)
	{
		super(mes);
	}
}
