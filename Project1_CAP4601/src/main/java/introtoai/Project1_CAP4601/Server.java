/** @file Server.java
 *  @brief This class represents a server that will listen for incoming client 
 *  	   connections. It creates a thread to handle multiple client connections 
 *         to play multiple games at once. It also uses the GameController 
 *         class to  manage each client connection.
 * 
 * 	@author Trevor Mee
 *  @date   2/25/2024
 *  @info   CAP4601
 *  
 */
package introtoai.Project1_CAP4601;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private static final int PORT_NUMBER = 5000;
	
	public static void main(String[] args) {
		
		System.out.println("Server running");
		ServerSocket server = null;
		
        try {
        	// Obtain server socket
        	server = new ServerSocket(PORT_NUMBER);
            
        	// Accept and start thread
            while(true) {  
            	Socket socket = server.accept();
            	System.out.println("Client connected.");
            	GameController controller = new GameController();
            	Thread thread = new Thread(new GameRunnable(socket, controller));
            	thread.start();
            }
           
           // Handle an IOException in the case that the could not be obtained
         } catch (IOException e) {
        	 	e.printStackTrace();
        	 	System.err.println("Unable to start server!");
        } 
        // Finally attempt to close the server and catch an IOException
          finally {
        		try {
        			server.close();
        		} catch(IOException e) {
        			e.printStackTrace();
        		}
        	}
		}
}
