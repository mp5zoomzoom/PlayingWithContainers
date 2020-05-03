package com.ecoscover.dockerstuff.datachanger;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.ecoscover.dockerstuff.comminf.CommInterface;
import com.ecoscover.dockerstuff.comminf.CommInterfaceListener;
import com.ecoscover.dockerstuff.comminf.messages.RawDataMsg;
import com.ecoscover.dockerstuff.comminf.messages.StatusMsg;
import com.ecoscover.dockerstuff.comminf.messages.StringDataMsg;
import com.ecoscover.dockerstuff.comminf.messages.ComplexDataMsg;

public class DataChanger implements CommInterfaceListener
{
	static Logger logger;
	CommInterface m_comm;
	Thread m_mainThread;
	
	public DataChanger() {}
	
	public static void main(String[] args) 
	{		
		PropertyConfigurator.configure("Resources/log4j.properties");
		logger= Logger.getLogger(DataChanger.class);
		
		DataChanger instance= new DataChanger();
		instance.m_comm= new CommInterface("DataChanger",instance);

		instance.m_comm.connect();		
		
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
	public void handleMessage(StatusMsg msg)
	{
		
	}
	
	@Override
	public void handleMessage(RawDataMsg msg)
	{
		logger.info("Got a raw message:"+msg);
		
		ComplexDataMsg msg1= new ComplexDataMsg();
		ComplexDataMsg msg2= new ComplexDataMsg();
		ComplexDataMsg msg3= new ComplexDataMsg();
		ComplexDataMsg msg4= new ComplexDataMsg();
		
		msg1.num1= (int) ((msg.data)/2147483648L);
		msg1.num2= msg1.num1/10;
		msg1.num3= msg1.num1/1000;
		msg1.num4= msg1.num1/100000;
		m_comm.SendMsg(msg1);
		
		msg2.num1= (int) (msg.data-(msg.data)/2147483648L);
		msg2.num2= msg2.num1/10;
		msg2.num3= msg2.num1/1000;
		msg2.num4= msg2.num1/100000;
		m_comm.SendMsg(msg2);
		
		msg3.num1= (int) ((msg.data)/2147483648L/2);
		msg3.num2= msg3.num1/10;
		msg3.num3= msg3.num1/1000;
		msg3.num4= msg3.num1/100000;
		m_comm.SendMsg(msg3);
		
		msg4.num1= (int) ((msg.data)/2147483648L/5);
		msg4.num2= msg4.num1/10;
		msg4.num3= msg4.num1/1000;
		msg4.num4= msg4.num1/100000;
		m_comm.SendMsg(msg4);	
		logger.info("sent 4 new complex messages");
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
