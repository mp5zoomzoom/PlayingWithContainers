package com.ecoscover.dockerstuff.comminf;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.ecoscover.dockerstuff.comminf.messages.ComplexDataMsg;
import com.ecoscover.dockerstuff.comminf.messages.RawDataMsg;
import com.ecoscover.dockerstuff.comminf.messages.StatusMsg;
import com.ecoscover.dockerstuff.comminf.messages.StringDataMsg;

public interface CommInterfaceListener 
{	
	public void handleMessage(StatusMsg msg);
	
	public void handleMessage(RawDataMsg msg);
	
	public void handleMessage(StringDataMsg msg);

	public void handleMessage(ComplexDataMsg newMessage);

}
