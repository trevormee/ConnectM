/** @file Main.java
 *  @brief This class contains the main method that 
 *         calls the GameController class and runs the 
 *         game loop
 *        
 *  @author Trevor Mee & David Martinez
 *  @date   2/26/2024
 * 
 */

package introtoai.Project1_CAP4601;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		// Create GameController Instance
		GameController game = new GameController();
		
		// Run the game loop
		game.gameLoop();

	}
	
}
