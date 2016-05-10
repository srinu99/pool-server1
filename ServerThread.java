
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * When the Server finds a client it creates a ServerThread with
 * the socket of the client passed into the constructor.
 * ServerThread will be handled by a thread in the threadpool.
 * It will loop until the client finishes.
 * @param connection - must be a valid client socket
 * @author Paul Marshall
 */

public class ServerThread implements Runnable {
  
	private Socket clientSocket = null;
        
	/**
	* takes a valid client socket and accepts text input
	* from client and creates a MessageImpl object with input
	* to count the characters and digits. 
	* Remains connected to client until they send "EXIT".
	* @param connection - a valid client socket
	*/
	public ServerThread(Socket connection){
		clientSocket = connection;
	}
        
	/**
	 * Accepts String input from client and creates the 
	 * MessageImpl object to send back.
	 * Terminates when the client sends "EXIT"
	 */
	public void run() {
	
		try{ 
				System.out.println("Connection received from "
	     		+ clientSocket.getInetAddress().getHostName()+" "
	     		+ clientSocket.getInetAddress().getHostAddress());
	     		
	     		System.out.println("New client is being processed by " + Thread.currentThread().getName());
	     		
	     		ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
	            BufferedReader inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	             
	            MessageImpl outputObject = null;
	            String inputLine = "";
	            boolean connected = true;
	           
	            while ((inputLine = inputStream.readLine()) != null && connected) {
	            	
	            	 if (inputLine.equals("EXIT")){ //if the input is BYE then send back a null object so client will terminate
	                     outputObject = null;
	                     outputStream.writeObject(outputObject);
	                     connected = false;
	            	 }else{											
	                     outputObject = new MessageImpl(inputLine);	//create the object
	                     outputStream.writeObject(outputObject);		//send object back to client
	            	 }
	            } //end of while loop
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally{ //finally, cleanup
	        	try{
	                clientSocket.close();
	        	} catch(IOException e){
	        		System.err.println("Error closing server socket");
	        		e.printStackTrace();
	        	}
	        }
	}
}
