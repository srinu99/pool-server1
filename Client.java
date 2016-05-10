import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {

	private boolean connected = false;
	private String client_hostname = "";
	private int client_port = 0;
	
	
	public Client(String hostname, int port){
		
    	if(!hostname.equals("") && port > 1024){ //if hostname is not empty and port is greater than 1024
    		client_hostname = hostname;
    		client_port = port;
    		System.out.println("Creating client using hostname: " + hostname + " And port: " + port);
    		runClient();
    	}else{
    		System.err.println("Hostname cannot be empty and port must be greater than 1024 (reserved ports)");
    		System.exit(1);
    	}
    }
	
	private void runClient(){
		
		  try
                        {
                           Socket clientSocket = new Socket(client_hostname, client_port);
		            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		        	ObjectInputStream	in = new ObjectInputStream(clientSocket.getInputStream());
		       
		        	BufferedReader textInput = new BufferedReader(new InputStreamReader(System.in));
		            MessageImpl fromServer;
		            String  fromUser;

		            connected = true;
		        	System.out.println("Client Connected!");
		        	
		        	//loop until termination
		            while (connected) {
			        	System.out.println("Type in a string to send to server or \"EXIT\" to exit");
		                fromUser = textInput.readLine();
		                
		                if (fromUser != null && !fromUser.equals("")) { //if userinput is not null and not empty
		                
		                    System.out.println("Client Sending String \"" + fromUser + "\" to Server");
		                    out.println(fromUser);
		                
		                    fromServer = (MessageImpl) in.readObject();
				                if(fromServer == null){
				                	System.out.println("Disconnecting from server");
				                	connected = false;
				                }else{
				                System.out.println("Client Recieved MessageImpl from Server");
				                System.out.println("Character Count: " + fromServer.getCharacterCount());
				                System.out.println("Digit Count: " + fromServer.getDigitCount());
			                }
		                }else{	
		                	 System.out.println("Please send a non-empty string");
		                }
		            }//end of while loop
		            System.out.println("Terminating Client");
		            clientSocket.close(); //client loop has ended, time to close socket and exit
		            System.exit(0); //everything went fine so we use exit code 0
		        } catch (UnknownHostException e) {
		            System.err.println("Couldnt find host: " + client_hostname);
		            System.exit(1);
		        } catch (IOException e) {
		            System.err.println("Couldn't get connection to " + client_hostname);
		            System.exit(1);
		        } catch (ClassNotFoundException e){
		        	System.out.println("Problem with creating the Message Object: " + e);
		        	System.exit(1);
		        }
	
	}
	
	
	
    public static void main(String[] args){
    	
       if(args.length == 0){ //0 this case is added for testing, allowing me to create several clients without manually entering arguments
    	   System.out.println("No parameters detected, using default values for testing"); //indicates that its using default values, mainly for testing
    	   Client client = new Client("192.168.0.100",6100); //creates client using localhost and port 3333
       }else if (args.length != 2) { //too many or little parameters
            System.err.println("Usage: java Client <host name> <port number>");
            System.exit(1);
        }else{
        	Client client = new Client(args[0], Integer.parseInt(args[1]));
        }
    }
}
