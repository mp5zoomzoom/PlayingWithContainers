package com.ecoscover.dockerstuff.comminf;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnect;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.ecoscover.dockerstuff.comminf.messages.ComplexDataMsg;
import com.ecoscover.dockerstuff.comminf.messages.RawDataMsg;
import com.ecoscover.dockerstuff.comminf.messages.StringDataMsg;
import com.google.gson.Gson;


public class CommInterface implements MqttCallback
{
	public static final String env_comPort= "CONTAINER_COMM_PORT";
	public static final String env_comIp="CONTAINER_COMM_IP";
	int m_port;
	Logger logger;

	int qos             = 2;
	String broker       = "192.168.1.8";
	String clientId;

	MqttAsyncClient client;
	
	CommInterfaceListener m_listener;
	
	Gson g= new Gson();
	public CommInterface(String clientId,CommInterfaceListener inf)
	{		
		this.m_listener= inf;
		this.clientId= clientId;
		PropertyConfigurator.configure("Resources/log4j.properties");
		logger= Logger.getLogger(CommInterface.class);
		int val= 0;
		
		try
		{
			val= Integer.parseInt(System.getenv(env_comPort));
			
		}
		catch(Exception e)
		{
			logger.info("unable to set comm port to:"+System.getenv(env_comPort));
			val= 61616;//set the default;
		}
		logger.info("setting comm port to:"+val);
		m_port= val;
		String tempIp= System.getProperty(env_comIp);
		if(tempIp != null) broker= tempIp;
		
	}
	public boolean connect()
	{
		MemoryPersistence persistence = new MemoryPersistence();
		String uri= "tcp://"+broker+":"+m_port;
		logger.info("Connecting to:"+uri);
		try {
			client = new MqttAsyncClient(uri, MqttAsyncClient.generateClientId(), null);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("unable to create mqtt client:"+e.getMessage());
			return false;
		}
		MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(true);
	
		logger.info("Connecting to broker: "+broker);
		try {
		
			client.connect(connOpts).waitForCompletion();
			client.setCallback(this);
		} catch (MqttSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("unable to connect to broker with security exception:"+e.getMessage());
			return false;
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("unable to connect to broker with mqtt exception:"+e.getMessage());
			return false;
		}
		
		String topicFilters= "RawDataMsg";
		try {
			client.subscribe(topicFilters, 0);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		topicFilters= "ComplexDataMsg";
		try {
			client.subscribe(topicFilters, 0);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		topicFilters= "StringDataMsg";
		try {
			client.subscribe(topicFilters, 0);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("Connected");
		return true;
	}
	
	public void SendMsg(RawDataMsg args)
	{
		MqttMessage msg= new MqttMessage();
		msg.setQos(2);
		msg.setPayload(g.toJson(args).getBytes());
		logger.info("sending raw data message:"+args.topic);
		try {
			client.publish(args.topic, msg);
		}
		catch(Exception e)
		{
			logger.error("unable to publish:"+e.getMessage());
			e.printStackTrace();
		}

	}
	public void SendMsg(ComplexDataMsg args) 
	{
		logger.info("sending complex data message");
		MqttMessage msg= new MqttMessage();
		msg.setQos(2);
		msg.setPayload(g.toJson(args).getBytes());
		try {
			client.publish(args.topic, msg);
		}
		catch(Exception e)
		{
			logger.error("unable to publish msg:"+e.getMessage());
		}	
	}
	
	public void SendMsg(StringDataMsg args) 
	{
		logger.info("sending string data message");
		MqttMessage msg= new MqttMessage();
		msg.setQos(2);
		msg.setPayload(g.toJson(args).getBytes());
		try {
			client.publish(args.topic, msg);
		}
		catch(Exception e)
		{
			logger.error("unable to publish msg:"+e.getMessage());
		}	
	}
	
	@Override
	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception 
	{
		logger.debug("message arrived:"+arg0);
		if(arg1 == null) return;
		if(arg0 == null) return;
		
		if(arg0.equals("RawDataMsg"))
		{
			RawDataMsg newMessage= g.fromJson(new String(arg1.getPayload()), RawDataMsg.class);
			m_listener.handleMessage(newMessage);
		}
		else if(arg0.equals("StringDataMsg"))
		{
			StringDataMsg newMessage= g.fromJson(new String(arg1.getPayload()), StringDataMsg.class);
			m_listener.handleMessage(newMessage);
		}
		else if(arg0.equals("ComplexDataMsg"))
		{
			ComplexDataMsg newMessage= g.fromJson(new String(arg1.getPayload()), ComplexDataMsg.class);
			m_listener.handleMessage(newMessage);
		}
	}
}
