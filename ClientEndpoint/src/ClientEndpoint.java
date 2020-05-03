import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientEndpoint {
	   public static void main(String[] args) throws Exception
	   {
	      BufferedReader inFromUser =
	         new BufferedReader(new InputStreamReader(System.in));
	      DatagramSocket clientSocket = new DatagramSocket();
	      InetAddress IPAddress = InetAddress.getByName("localhost");
	      byte[] sendData = new byte[1024];
	      byte[] receiveData = new byte[1024];
	      String sentence = inFromUser.readLine();
	      while(true)
		  {
	    	  sendData = sentence.getBytes();
	    	  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
	    	  clientSocket.send(sendPacket);
	    	  
	    	  //read more data
	    	  sentence = inFromUser.readLine();
	    	  if(sentence.equals("stop")) break;
		  }
	      clientSocket.close();
	   }
}
