package com.ecoscover.dockerstuff.tcp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TCPRelay {


	static Logger logger;
	public static void main(String[] args)
	{
		PropertyConfigurator.configure("Resources/log4j.properties");
		logger= Logger.getLogger(TCPRelay.class);
		
		int i = 0, j;
        String arg;
        char flag;
        boolean vflag = false;
        String ip = "192.168.1.8";
        int remotePort= 9877;
        int localPort= 9876;
        String label= "relay1";

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
            else if (arg.equals("-n")) {
                if (i < args.length)
                    label = args[i++];
                else
                    System.err.println("-n requires a name");
                if (vflag)
                    System.out.println("lable = " + label);
            }
            else if (arg.equals("-r")) {
                if (i < args.length)
                {
                	try
                	{
                		remotePort = Integer.parseInt(args[i++]);
                	}
                	catch(NumberFormatException e)
                	{
                		System.err.println("-r requires a VALID port");
                		remotePort= 0;
                	}
                }
                else
                    System.err.println("-r requires a port");
                if (vflag)
                    System.out.println("remote port = " + localPort);
            }
            else if (arg.equals("-l")) {
                if (i < args.length)
                {
                	try
                	{
                		localPort = Integer.parseInt(args[i++]);
                	}
                	catch(NumberFormatException e)
                	{
                		System.err.println("-p requires a VALID port");
                		localPort= 0;
                	}
                }
                else
                    System.err.println("-p requires a port");
                if (vflag)
                    System.out.println("local port = " + localPort);
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
        logger.info("starting up TCP Relay "+label+" local port:"+localPort+" remote port:"+remotePort);
        runTCPRelay(label, ip, localPort, remotePort);
	}

	static void runTCPRelay(String label, String ip, int lport, int rport)
	{
	    ServerSocket socket= null;
	    DataInputStream input= null;
	     
	    try
	    {
            socket = new ServerSocket(lport);
	    }
	    catch(IOException e)
	    {
	    	logger.error("unable to setup server socket on port:"+lport);
	    	logger.error(e.getMessage());
	    	System.exit(0);
	    }
	    while(true)
	    {
	    	Socket acceptedSoc;				
	    	final DataOutputStream outputFromClient;
			final DataInputStream inputToClient;
			try 
			{
				acceptedSoc = socket.accept();
				// sends output to the socket 
				outputFromClient    = new DataOutputStream(acceptedSoc.getOutputStream());
				inputToClient= new DataInputStream(acceptedSoc.getInputStream());
   			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(label+" got io exception on new connection:"+e.getMessage());
				return;
			}
			
			logger.info(label+" accepted connection on port:"+acceptedSoc.getLocalPort());
			
			//now setup client connection
			Socket clientSocket;
			final DataOutputStream outputFromServer;
			final DataInputStream inputToServer;
			try
			{
				clientSocket= new Socket(ip,rport);
				outputFromServer= new DataOutputStream(clientSocket.getOutputStream());
				inputToServer= new DataInputStream(clientSocket.getInputStream());
			}
			catch(IOException e)
			{
				logger.error(label+" unable to setup client connection"+e.getMessage());
				return;
			}
			
        
			logger.info(label+" Connected to server on port:"+rport);          
            
            
            Thread echoBackThread= new Thread(()->
            {
            	echoBackDataFromSocket(label, outputFromServer, inputToClient);
            });
        
            echoBackThread.start();
            
            Thread echoBackThreadTwo= new Thread(()->
            {
            	echoBackDataFromSocket(label,outputFromClient, inputToServer);
            });
            
            echoBackThreadTwo.start();        
        } 
    } 

		static void echoBackDataFromSocket(String label, DataOutputStream out, DataInputStream in)
		{
			logger.info(label+" starting echo service");
			
			while(true)
			{
				String line="";
				BufferedReader brIn= new BufferedReader(new InputStreamReader(in));
				try {
					line= brIn.readLine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					logger.error(label+" got IO exception on read:"+e1.getMessage());
				}
				line= new String(line.trim()+" "+label+"\n");
				System.out.println("Sending:"+line);
				System.out.println("length:"+line.length());
				logger.info(label+" Relaying message:"+line);
				try {
					out.writeBytes(line);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("relay io exception writing data:"+e.getMessage() );
					return;
				}
				if(line.contains("stop"))System.exit(0);
			}
			
		}
		
		
		public int returnnumber(int val)
		{
			return val;
		}
		
		public boolean isSame(int val1, int val2)
		{
			return val1 == val2;
		}
	}