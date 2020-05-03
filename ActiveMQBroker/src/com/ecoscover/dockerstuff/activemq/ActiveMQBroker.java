package com.ecoscover.dockerstuff.activemq;

import org.apache.activemq.broker.BrokerService;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class ActiveMQBroker {

	static Logger logger;
	public static void main(String[] args) {
		PropertyConfigurator.configure("Resources/log4j.properties");
		logger= Logger.getLogger(ActiveMQBroker.class);
		
		int i = 0, j;
        String arg;
        char flag;
        boolean vflag = false;
        String ip = "0.0.0.0";
        int localPort= 61616;
        String label= "Broker";

        while (i < args.length && args[i].startsWith("-")) {
            arg = args[i++];

    // use this type of check for "wordy" arguments
            if (arg.equals("-verbose")) {
                System.out.println("verbose mode on");
                vflag = true;
            }              
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
        }
        if (i == args.length)
            System.err.println("Usage: ParseCmdLine [-verbose] [-xn] [-output afile] filename");
        else
            System.out.println("Success!");
        logger.info("starting up ActiveMQ broker "+label+" local port:"+localPort);
        startBroker(label, ip, localPort);
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
	
	public static void startBroker(String label, String ip, int port)
	{
		BrokerService broker = new BrokerService();
		// configure the broker
		broker.setBrokerName(label);
		broker.setUseJmx(false);
		broker.setPersistent(false);
		String uri= "mqtt://"+ip+":"+port;
		logger.info("starting broker with connector:"+uri);
		try {
			broker.addConnector(uri);
		} catch (Exception e1) {
			logger.error("threw exception adding connector:"+e1.getMessage());
		}
		try {
			broker.start();
		} catch (Exception e) {
			logger.error("Threw exception starting broker:"+e.getMessage());
		}
		logger.info("Broker "+label+" started");
	}

}
