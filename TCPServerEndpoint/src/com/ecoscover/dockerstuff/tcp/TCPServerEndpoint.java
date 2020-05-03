package com.ecoscover.dockerstuff.tcp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TCPServerEndpoint {
	static Logger logger;

	public static void main(String[] args) {
		PropertyConfigurator.configure("Resources/log4j.properties");
		logger= Logger.getLogger(TCPServerEndpoint.class);
		
		logger.info("Starting up Server endpoint");
        int i = 0, j;
        String arg;
        char flag;
        boolean vflag = false;
        String ip = "";
        int port= 9877;

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
        
        runTCPServer(port);
    

	}

	static void runTCPServer(int port)
	{
	    ServerSocket socket= null;
	    DataInputStream input= null;
	     
	    try
	    {
            socket = new ServerSocket(port);
	    }
	    catch(IOException e)
	    {
	    	e.printStackTrace();
	    }
	    while(true)
	    {
	    	Socket acceptedSoc;				
	    	final DataOutputStream output;
			final DataInputStream inFromSoc;
			try 
			{
				acceptedSoc = socket.accept();
				// sends output to the socket 
				output    = new DataOutputStream(acceptedSoc.getOutputStream());
				inFromSoc= new DataInputStream(acceptedSoc.getInputStream());
   			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
        
			logger.info("Server Accepted connection");         
            
            
            Thread echoBackThread= new Thread(()->
            {
            	echoBackDataFromSocket(output, inFromSoc);
            });
        
            echoBackThread.start();
        } 
    } 

	static void echoBackDataFromSocket(DataOutputStream out, DataInputStream in)
	{
		logger.info("Server starting echo service");
		while(true)
		{
			String line="";
			BufferedReader brIn= new BufferedReader(new InputStreamReader(in));
			try {
				line= brIn.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			line= new String(line.trim()+" Server\n");
			logger.info("Server writing:"+line);
			logger.info("Server writing length:"+line.length());
			
			try {
				out.writeBytes(line);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("Server got io exception when writting:"+e.getMessage());
				return;
			}
			if(line.contains("stop"))System.exit(0);
		}
		
	}
}
