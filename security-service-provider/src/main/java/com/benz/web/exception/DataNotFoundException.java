package com.benz.web.exception;


public class DataNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1285914756013911293L;
	
	public DataNotFoundException(String msg)
	{
		super(msg);
	}

}
