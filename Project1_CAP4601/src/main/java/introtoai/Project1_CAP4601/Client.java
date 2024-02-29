/** @file Client.java
 *  @brief This client class is used to connect to a server
 *  	   to play the connectM game. This class will
 *         communicate with the server about different game
 *         moves and receive game updates.
 * 
 * 	@author Trevor Mee
 *  @date   2/25/2024
 *  @info   CAP4601
 *  
 */

package introtoai.Project1_CAP4601;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public Client(String host, int port) {
    	try {
            // Init. and connect
	    	System.out.println("Client connected to host  " + "127.0.0.1" + " on port " + port );  	
	    	
	    	Socket socket = null;
	    	BufferedReader serverInput = null;
	    	PrintWriter clientOutput = null;
	    	BufferedReader userInput = null;
	    try {
	    	// Get the socket from server and 
	    	// Create IO Instances
	    	socket = new Socket(host, port);
            serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clientOutput = new PrintWriter(socket.getOutputStream(), true);
            userInput = new BufferedReader(new InputStreamReader(System.in));
	   
	    } catch (UnknownHostException e) {
		    e.printStackTrace();
		    System.out.println("Client unknownhost exception: " + host);
		    System.exit(1);
	   }
	    
	   // Main Client Logic
       
	    System.out.println(serverInput.readLine());
       while (true) {
    	   String response = serverInput.readLine();
                
           if(response == null) {
              	break;
           }
           
           System.out.println(response);

           if (response.contains("Enter the column you would like your piece to be placed")) {
        	   int move = Integer.parseInt(userInput.readLine());
               		clientOutput.println(move);
           } else if (response.contains("Game over")) {
                    System.out.println(serverInput.readLine());
                    break;
                }
            }
               
        // Close all resources
        serverInput.close();
        clientOutput.close();
        userInput.close();
        socket.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
         
    }

    
    // Main method
    public static void main(String[] args) {
        String host = "127.0.0.1"; 
        int port = 5000;
        new Client(host, port);
    }
}
