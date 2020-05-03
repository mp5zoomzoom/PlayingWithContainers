package com.ecoscover.dockerstuff.relay;
import java.io.*;
import java.net.*;

public class UdpRelay
{
   public static void main(String args[]) throws Exception
   {
	   String nodeName= System.getenv("nodeName");
	   String sendTo_str= System.getenv("forwardTo");
	   
	   int port= Integer.parseInt(sendTo_str);
	   
	   System.out.println("starting relay:"+nodeName);
	   System.out.println("Forwarding data to port:"+port);
	   Thread serverThread= new Thread()
	   {
		   @Override
		   public void run()
		   {
			   try
			   {
				   runServer(nodeName,port);
			   }
			   catch(Exception e)
			   {
				   e.printStackTrace();
			   }	
		   }
	   };
	   serverThread.start();
	   
	   while(true)
	   {
		   Thread.sleep(1000);
	   }
		  
   }
   public static void runServer(String nodeName, int port) throws Exception
   {
     DatagramSocket serverSocket = new DatagramSocket(9876);
     DatagramSocket clientSocket = new DatagramSocket(port);
     InetAddress IPAddress = InetAddress.getByName("192.168.1.8");
        byte[] receiveData = new byte[1024];
        while(true)
           {
              DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
              serverSocket.receive(receivePacket);
              
              String dataRx= new String(receivePacket.getData()).trim();

              System.out.println("receivepacket data size:"+dataRx.length());
              byte[] sendData= appendBuffer(dataRx,"-"+nodeName);
              String sentString= new String(sendData);
              System.out.println("data:"+sentString);
              System.out.println("size:"+sendData.length);
              DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
              clientSocket.send(sendPacket);
           }
   }
   
   public static byte[] appendBuffer(String buff, String val)
   {
	   String temp= buff+val;
	   byte[] retval=  temp.getBytes();
	   return retval;
	}
   

}