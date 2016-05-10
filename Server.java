
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;



public class Server {
	
	//fields
	private int server_thread_count = 0; 
	private int server_listen_port = 0;
	private ServerSocket serverSocket = null;
	private ExecutorService threadPool = null;

	public Server(int port, int thread_num){ //could have constructor throwing error instead of a try/catch block
		
		if(port> 1024 && thread_num > 0){
			server_listen_port = port;
			server_thread_count = thread_num;
			
			try{ //create server
				serverSocket = new ServerSocket(server_listen_port);
			} catch(IOException e){
				System.err.println("Error creating server socket");
				e.printStackTrace();
				System.exit(1);
			}
			
			System.out.println("Creating server using port: " + server_listen_port + " Using a thread pool of size " + server_thread_count);
			threadPool = Executors.newFixedThreadPool(server_thread_count); //initialised after server, no point doing it before if server fails
			listen();
		}else{
			System.err.println("Port must be greater than 1024 (reserved ports) and size of Threadpool must be greater than 0");
			System.exit(1); //no server created, just exit
		}
	}
	
   
	private void listen() {//start listening for connections
		
			while(true){ //listen until false
				try{
					System.out.println("Waiting for connection at port " + server_listen_port);
					Socket connection = serverSocket.accept();
					threadPool.execute(new ServerThread(connection));
					System.out.println("New connection established");
				} catch(IOException e){
					System.err.println("Error accepting connection");
					e.printStackTrace();
					closeServerAndPool();
				}
			}
			
		}
	
	private void closeServerAndPool(){
		   threadPool.shutdown(); // Disable new tasks from being submitted
		   try {
		     // Wait a while for existing tasks to terminate
		     if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
		    	 threadPool.shutdownNow(); // Cancel currently executing tasks
		       // Wait a while for tasks to respond to being cancelled
		       if (!threadPool.awaitTermination(60, TimeUnit.SECONDS))
		           System.err.println("Pool did not terminate");
		     }
		   } catch (InterruptedException e) {
		     // (Re-)Cancel if current thread also interrupted
			   threadPool.shutdownNow();
		     // Preserve interrupt status
		     Thread.currentThread().interrupt();
		   }
		System.out.println("Pool has closed successfully");
		
		try{
			serverSocket.close();
		}catch(IOException e){
			System.err.println("Error closing socket");
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Server has closed successfully");
		System.exit(0);
	}
	
	
	 public static void main(String[] args){
		 
	       if(args.length == 0){ //0 this case is added for testing, allowing me to create several clients without manually entering arguments
	    	   System.out.println("No parameters detected, using default values for testing"); //indicates that its using default values, mainly for testing
	    	   Server server = new Server(6100, 5); //creates client using localhost and port 3333
	       }else if (args.length != 2) { //too many or little parameters
	            System.err.println("Usage: java Server <port> <threadpool size>");
	            System.exit(1);
	        }else{
	        	Server server = new Server(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
	        }		 
	 }
}
	
