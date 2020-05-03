package com.ecoscover.dockerstuff.tcp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TCPClientEndpoint {
	static Logger logger;
	public static void main(String[] args) {
		PropertyConfigurator.configure("Resources/log4j.properties");
		logger= Logger.getLogger(TCPClientEndpoint.class);
		logger.info("Starting up client endpoint");
        int i = 0, j;
        String arg;
        char flag;
        boolean vflag = false;
        String ip = "192.168.1.8";
        int port= 9876;

        while (i < args.length && args[i].startsWith("-")) {
            arg = args[i++];

    // use this type of check for "wordy" arguments
            if (arg.equals("-verbose")) {
                System.out.println("verbose mode on");
                vflag = true;
            }

    // use this type of check for arguments that require arguments
            else if (arg.equals("-i")) {
                if (i < args.length)
                    ip = args[i++];
                else
                    System.err.println("-i requires an ip address");
                if (vflag)
                    System.out.println("ip address = " + ip);
            }
            else if (arg.equals("-p")) {
                if (i < args.length)
                {
                	try
                	{
                		port = Integer.parseInt(args[i++]);
                	}
                	catch(NumberFormatException e)
                	{
                		System.err.println("-p requires a VALID port");
                		port= 0;
                	}
                }
                else
                    System.err.println("-p requires a port");
                if (vflag)
                    System.out.println("port = " + port);
            }
            

    // use this type of check for a series of flag arguments
            else {
                for (j = 1; j < arg.length(); j++) {
                    flag = arg.charAt(j);
                    switch (flag) {
                    case 'x':
                        if (vflag) System.out.println("Option x");
                        break;
                    case 'n':
                        if (vflag) System.out.println("Option n");
                        break;
                    default:
                        System.err.println("ParseCmdLine: illegal option " + flag);
                        break;
                    }
                }
            }
        }
        if (i == args.length)
            System.err.println("Usage: ParseCmdLine [-verbose] [-xn] [-output afile] filename");
        else
            System.out.println("Success!");
        
        runTCPClient(ip,port);
    

	}
	static DataInputStream inFromSoc= null;
	static void runTCPClient(String ip, int port)
	{
	    Socket socket= null;
	    DataInputStream input= null;
	    DataOutputStream output= null;
	    
        try
        { 
            socket = new Socket(ip, port); 
            System.out.println("Connected"); 
  
            
            // takes input from terminal 
            input  = new DataInputStream(System.in); 
  
            // sends output to the socket 
            output    = new DataOutputStream(socket.getOutputStream());
            inFromSoc= new DataInputStream(socket.getInputStream());
        } 
        catch(UnknownHostException u) 
        { 
            System.out.println(u);
            return;
        } 
        catch(IOException i) 
        { 
            System.out.println(i);
            return;
        } 
        
        Thread echoBackThread= new Thread(()->
        {
        	outputDataFromSocket(inFromSoc);
        });
        
        echoBackThread.start();
        
        // string to read message from input 
        String line = ""; 
  
        // keep reading until "Over" is input 
        while (!line.equals("Over")) 
        { 
            try
            { 
                line = input.readLine(); 
                line=line+'\n';
                output.writeBytes(line);
                System.out.println("sent:"+line);
                logger.info("Client sent:"+line);
            } 
            catch(IOException i) 
            { 
                System.out.println(i); 
            } 
        } 
  
        // close the connection 
        try
        { 
            input.close(); 
            output.close(); 
            socket.close(); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
    } 

	static void outputDataFromSocket(DataInputStream in)
	{
		
		String line="";
		BufferedReader brIn= new BufferedReader(new InputStreamReader(in));
		while(true)
		{

			try {
				line= brIn.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.info("Client Received:"+line);
			logger.info("Client received length:"+line.length());
			if(line.contains("stop"))System.exit(0);
		}
		
	}
	
	public int returnnumber(int val)
	{
		return val;
	}

}
