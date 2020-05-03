package com.ecoscover.dockerstuff.datastringer;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.ecoscover.dockerstuff.comminf.CommInterface;
import com.ecoscover.dockerstuff.comminf.CommInterfaceListener;
import com.ecoscover.dockerstuff.comminf.messages.ComplexDataMsg;
import com.ecoscover.dockerstuff.comminf.messages.RawDataMsg;
import com.ecoscover.dockerstuff.comminf.messages.StatusMsg;
import com.ecoscover.dockerstuff.comminf.messages.StringDataMsg;

public class DataStringer  implements CommInterfaceListener
{
	public static final String env_packSize= "SIZE";
	static Logger logger;
	CommInterface m_comm;
	Thread m_mainThread;
	int m_sizeCutoff= 5000;
	
	public static void main(String[] args) {
		PropertyConfigurator.configure("Resources/log4j.properties");
		logger= Logger.getLogger(DataStringer.class);
		
		
		
		DataStringer instance= new DataStringer();
		instance.m_comm= new CommInterface("DataStringer",instance);

		instance.m_comm.connect();	
		
		try
		{
			instance.m_sizeCutoff= Integer.parseInt(System.getenv(env_packSize));
		}
		catch(Exception e)
		{
			instance.m_sizeCutoff= 5000;//default to 5000
		}
		logger.info("Starting data Stringer with size:"+instance.m_sizeCutoff);
		
		instance.m_mainThread= new Thread(()->
		{
			instance.runMainThread();
		});
		
		instance.m_mainThread.start();
		
		while(true)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void runMainThread()
	{
		
	}
	
	@Override
	public void handleMessage(StatusMsg msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMessage(RawDataMsg msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMessage(StringDataMsg msg) {
		// TODO Auto-generated method stub
		
	}

	StringBuffer m_stringBuff= new StringBuffer();
	@Override
	public void handleMessage(ComplexDataMsg newMessage) 
	{
		logger.info("Handling complexMsg");
		m_stringBuff.append(newMessage.num1);
		m_stringBuff.append(":");
		m_stringBuff.append(newMessage.num2);
		m_stringBuff.append(":");
		m_stringBuff.append(newMessage.num3);
		m_stringBuff.append(":");
		m_stringBuff.append(newMessage.num4);
		m_stringBuff.append(":");
		
		if(m_stringBuff.length()>m_sizeCutoff)
		{
			StringDataMsg newMsg= new StringDataMsg();
			newMsg.data=m_stringBuff.toString();
			logger.info("Sending string message:"+newMsg.data);
			m_comm.SendMsg(newMsg);
			m_stringBuff= new StringBuffer();
		}
		
	}

}
