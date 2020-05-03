package com.ecoscover.dockerstuff.dataproducer;

import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.ecoscover.dockerstuff.comminf.CommInterface;
import com.ecoscover.dockerstuff.comminf.CommInterfaceListener;
import com.ecoscover.dockerstuff.comminf.messages.ComplexDataMsg;
import com.ecoscover.dockerstuff.comminf.messages.RawDataMsg;
import com.ecoscover.dockerstuff.comminf.messages.StatusMsg;
import com.ecoscover.dockerstuff.comminf.messages.StringDataMsg;

public class DataProducer implements CommInterfaceListener
{
	public static final String env_rate="RATE";
	static Logger logger;
	CommInterface m_comm;
	Thread m_mainThread;
	int m_pause;

	public DataProducer() {
		
	}

	public static void main(String[] args) 
	{
		PropertyConfigurator.configure("Resources/log4j.properties");
		logger= Logger.getLogger(DataProducer.class);
		
		System.out.println("env rate:"+System.getenv(env_rate));
		DataProducer instance= new DataProducer();
		instance.m_comm= new CommInterface("RandomDataProducer",instance);
		
		instance.m_comm.connect();
		
		try
		{
			instance.m_pause= Integer.parseInt(System.getenv(env_rate));
		}
		catch(Exception e)
		{
			instance.m_pause= 1000;//default to 1 second
		}
		logger.info("Starting data producer with rate:"+instance.m_pause);
		
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
	
	Random r= new Random();
	
	public void runMainThread()
	{
		while(true)
		{
			Random r= new Random();
			long val= r.nextLong();
			RawDataMsg msg= new RawDataMsg();
			msg.data= val;
			m_comm.SendMsg(msg);
			logger.info("Producing random data:"+val);
			try {
				
				Thread.sleep(m_pause);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void handleMessage(StatusMsg msg)
	{
		
	}

	@Override
	public void handleMessage(RawDataMsg msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMessage(StringDataMsg msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMessage(ComplexDataMsg newMessage) {
		// TODO Auto-generated method stub
		
	}

}
