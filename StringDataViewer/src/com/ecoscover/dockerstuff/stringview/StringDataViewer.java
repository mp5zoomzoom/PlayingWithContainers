package com.ecoscover.dockerstuff.stringview;

import com.ecoscover.dockerstuff.comminf.CommInterface;
import com.ecoscover.dockerstuff.comminf.CommInterfaceListener;
import com.ecoscover.dockerstuff.comminf.messages.ComplexDataMsg;
import com.ecoscover.dockerstuff.comminf.messages.RawDataMsg;
import com.ecoscover.dockerstuff.comminf.messages.StatusMsg;
import com.ecoscover.dockerstuff.comminf.messages.StringDataMsg;

public class StringDataViewer implements CommInterfaceListener
{
	CommInterface m_comm;

	public static void main(String[] args) {
		StringDataViewer instance= new StringDataViewer();
		instance.m_comm= new CommInterface("stringView",instance);
		instance.m_comm.connect();
		
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
		System.out.println("String message:"+msg.data);	
	}

	@Override
	public void handleMessage(ComplexDataMsg newMessage) {
		// TODO Auto-generated method stub
		
	}

}
