/** @file GameRunnable.java
 *  @brief 
 * 
 * 	@author Trevor Mee
 *  @date   2/25/2024
 *  @info   CAP4601
 *  
 */
package introtoai.Project1_CAP4601;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameRunnable implements Runnable {
    private Socket socket;
    private GameController controller;
    private BufferedReader input;
    private PrintWriter output;

    // Parameterized constructor
    public GameRunnable(Socket socket, GameController controller) {
        this.socket = socket;
        this.controller = controller;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /**
     *  Run method used to start a new thread for the 
     *  connect M game
     */
    //@Override
    public void run() {
      try {
    	  
    	  while(!controller.isGameOver()) {
    		  
    		  if(controller.isUserTurn()) {
    			  output.println("sending: status");
    			  output.println(controller.printUpdateState());
    			  output.println("sending: board");
    			  output.println(controller.boardString());
    			  output.println("Enter the column you would like your piece to be placed");
    			  int userCol= Integer.parseInt(input.readLine());
    			  controller.moveUser(userCol);
    			  
    		  }else {
    			  output.println("sending: status");
    			  output.println(controller.printUpdateState());
    			  output.println("sending: board");
              	  output.println(controller.boardString());
              	  controller.moveAI();
    		  }
    		  if(controller.isGameOver()) {
    			  output.println(controller.boardString());
                  output.println(controller.printUpdateState());
                  break;
    		  }
    	  }
    	  output.println("sending: quit");
      	  output.println("server: quit");
          socket.close();
      } catch(IOException e) {
    	  e.printStackTrace();
      }
    }
}


