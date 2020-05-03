package com.ecoscover.dockerstuff.base;

import java.util.ArrayList;

import com.ecoscover.dockerstuff.comminf.CommInterface;

public class BaseComponent
{
	CommInterface m_com;
	
	Thread m_mainControlTread;
	ArrayList<Thread> m_appThreads;
	
	public BaseComponent()
	{
		
	}
	
	public void initialize(CommInterface com)
	{
		m_com= com;
	}
	
	private void mainControlThread()
	{
		//initialize everything
		
		//main loop
		while(true)//this should never shut down
		{
			for(Thread t:m_appThreads)
			{
				
			}
			
		}
		
	}
	
	private long getCurrentTime()
	{
		return System.currentTimeMillis();
	}

}
