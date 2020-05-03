import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerEndpoint {
	   public static void main(String[] args) throws Exception
	   {
	     DatagramSocket serverSocket = new DatagramSocket(9877);
	        byte[] receiveData = new byte[1024];
	        byte[] sendData = new byte[1024];
	        
	        while(true)
	           {
	              DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	              serverSocket.receive(receivePacket);
	              String sentence = new String( receivePacket.getData());
	              System.out.println("server RECEIVED: " + sentence);
	              if(sentence.contains("stop"))break;
	           }
	        serverSocket.close();
	  }
}
