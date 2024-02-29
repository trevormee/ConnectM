/** @file GameController.java
 *  @brief This class contains the main connectM 
 *  	   game logic to be used in the program.
 * 
 * 	@author Trevor Mee & David Martinez
 *  @date   2/25/2024
 *  @info   CAP4601
 *  
 */
package introtoai.Project1_CAP4601;

import java.util.Arrays;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController {
	
	// Private instance variables
	private char[][] board;
	private Board mainBoard;
	private int n;//size of the board
	private int m;//the "m" in connect-m
	private int h;
	private boolean userTurn;
	//MIN_VALUE has bug where if you multiply it by -1 you still get MIN_VALUE
	//hence this workaround
	private int MIN = Integer.MAX_VALUE * -1;
	//a similar value is defined for MAX_VALUE for consistency
	//MAX_VALUE does not have the same issue as MIN_VALUE
	private int MAX = Integer.MAX_VALUE;
	
	// Private final instance variables
	private final char USER_SYMBOL = 'X';
	private final char AI_SYMBOL = 'O';
	private final int AI = 0;
	private final int USER = 1;
	private final int depth = 9; // Change depending on preference!
	
	
	
    /**
     * 	Default constructor that uses the the getUserInput
     *  method to obtain n,m & h; create an empty board; and 
     *  determine the first player of the game from the 
     *  firstPlayer() method
     */
	public GameController() {
		getUserInput();
		board = new char[n][n];
		mainBoard = new Board(n , m);
		firstPlayer();		
	}
	
	
	/**
	 *  Gets user input for n (size of board),
	 *  m (number of 'pieces' needed contiguously
	 *  to win the game), and h (determines whether
	 *  the human or AI makes the fist move)
	 */
	private void getUserInput() {
        Scanner scnr = new Scanner(System.in);
        
        System.out.println("connectM n m h");
        
        while(true) {
        	String gameSetup = scnr.nextLine();
        	String[] nmh = gameSetup.split("\\s+");
        	
        	if(nmh.length != 3) {
        		System.out.println("Invalid input. Try again");
        		continue;
        	}
        	
        	try {
        		n = Integer.parseInt(nmh[0]);
        		if(n < 3 || n > 10) {
        			System.out.println("n must be between 3 and 10 inclusive");
        			continue;
        		}
        		
        		m = Integer.parseInt(nmh[1]);
        		if(m < 1 || m > n) {
        			System.out.println("m must be between 1 and n inclusive");
        			continue;
        		}
        		
        		h = Integer.parseInt(nmh[2]);
        		if(h != 0 && h != 1) {
        			System.out.println("h must be either 0 or 1");
        			continue;
        		}
        		
        		System.out.println("The Game Board will be " + n + "x" + n +
        							" board.\nYou will need to connect " + m +
        							" in a row to win.\nPlease switch over to the client console.");
                break;
        	}catch(NumberFormatException e) {
        		e.printStackTrace();
        		continue;
        	}
        	        	
        }
	}
	/**
	 * if USER_SYMBOL (X) is inputed
	 * then AI_SYMBOL is returned, vice versa otherwise
	 * @param player the char representing a player
	 * @return the char representing the player's opponent
	 */
	private char opponent(char player) {
		if(player == 'X') {
			return 'O';
		}else {
			return 'X';
		}
	}
	
	/**
	 *  Method to determine if the user or
	 *  AI will make the first move
	 */
	public int firstPlayer() {
		if(h == 1) {
			System.out.println("User first");
			userTurn = true;
			return USER;
		}else if (h == 0) {
			System.out.println("AI first");
			userTurn = false;
			return AI;
		}else {
			return -1;
		}
	}
	
	
	/**
	 * 	 main game loop logic for making moves and
	 *   updating the state of the board
	 */
	public void gameLoop() {
		
        Scanner scnr = new Scanner(System.in);	
        
		while(!isGameOver()) {
			
			// Human turn
			if(isUserTurn()) {
				System.out.println(printUpdateState());
				System.out.println("Current Board...");
				System.out.println(boardString());
  			    System.out.println("Enter the column you would like your piece to be placed");
  			    int userCol = scnr.nextInt();
  			    moveUser(userCol);
  			// AI Turn
			}else {
				System.out.println(printUpdateState());
				System.out.println("Current Board...");
				System.out.println(boardString());
				moveAI();
			}
			
			// If terminal state, break the game loop
			if(isGameOver()) {
				System.out.println(boardString());
				System.out.println(printUpdateState());
				break;
			}
			
		}
		
		System.out.println("The game is over");
		
		// Close the scanners
		scnr.close();
	}
	
	
	/**
	 *  Method used to represent the game board
	 *  as a String in an appealing way
	 */
	public String boardString() {
		return mainBoard.toString();
	}
	
	
	/**
	 * 	Method to update the state of the game
	 */
	public String printUpdateState() {
		if(mainBoard.checkForWin(USER_SYMBOL)) {
			return "The human wins!";
		}else if (mainBoard.checkForWin(AI_SYMBOL)) {
			return "The AI wins!";
		}else if(mainBoard.isBoardFull()) {
			return "It is a draw";
		}else {
			return "Move";
		}
	}
	

	/**
	 *  Method that has the logic for a user to
	 *  make a move on the game board
	 *  
	 *  @param col the column chosen by user to put piece
	 *
	 */
	public boolean moveUser(int col) {
		
		if (mainBoard.isColFull(col)) {
	        System.out.println("Column is full. Please choose another column.");
	        return false;
		}else {
			mainBoard.play(col, USER_SYMBOL);
			userTurn = !userTurn;
			return true;
		}
	}
	
	
	// returns whose turn it is
	public boolean isUserTurn() {
		return userTurn;
	}
	
	
	/**
	 *  Method that has the logic for the AI to
	 *  make a move on the game board
	 */
	 public void moveAI() {
		 
		 // Find the bestMove from the minimax function
		 Move bestMove = minimax(AI_SYMBOL, mainBoard, depth, MIN, MAX);
		 //Move bestMove = max(AI_SYMBOL, mainBoard, depth);
		 
		 // Play the best move
	     mainBoard.play(bestMove.getColIndex(), AI_SYMBOL);
	     
		 System.out.println("Server played: " + bestMove.getColIndex());
	     
		 // Flip turn to the human
		 userTurn = !userTurn;
	     
	 }
	 
	 
	/**
	 *  Method checking if the game is over
	 */
	public boolean isGameOver() {
		return mainBoard.checkForWin(USER_SYMBOL) || mainBoard.checkForWin(AI_SYMBOL) || mainBoard.isBoardFull();
	}
	
	/**
	 * Checks in a given direction if a win is hypothetically possible
	 * (e.g. contains no opponent pieces)
	 * @param player the player for whom to optimize for
	 * @param row the row of the piece
	 * @param col the col of the piece
	 * @param xDir the direction in which to search on the horizontal axis
	 * @param yDir the direction in which to search on the vertical axis
	 * @param boardState an array with the state of the board
	 * @return
	 */
	private int canWin(char player, int row, int col, int xDir, int yDir, char[][] boardState) {
		int rowBound = row + (n * yDir);
		int colBound = col + (n * xDir);
		int opponentPieces = 0;
		int largestBoardIndex = n - 1;
		
		//if a direction is out of bounds don't bother checking it
		// for example, don't check if there are any winning moves possible
		// to the left of a piece in the leftmost column
		if (rowBound < 0 || rowBound > largestBoardIndex || colBound < 0 || colBound > largestBoardIndex) {
			return 0;
		}
		
		//if an opponent piece is found in a given direction increase opponentPieces
		for (int i = 0; i < m; i++) {
			row += yDir;
			col += xDir;
			if (boardState[row][col] == opponent(player)) {
				opponentPieces++;
			} 
		}
		
		//if no opponent pieces are found (meaning a win is hypothetically
		// possible in that direction) then return 1
		if (opponentPieces == 0) {
			return 1;
		}else {
			return 0;
		}
		
	}
	
	/**
	 *  Method checking how many winning moves are possible
	 *  for a spot on the board (assumes it is passed a valid move)
	 *  
	 *  @param player either the human or AI using the consts AI_CHAR(O) or USER_CHAR(X)
	 *  @param rowVal the column index of the spot to check (0 to n-1)
	 *  @param colVal the row index of the spot to check (0 to n-1)
	 */
	public int spotUtility(char player, int rowVal, int colVal, char[][] boardState) {
		int utility = 0;
				
		//check in horizontal direction for possible wins
		utility += canWin(player, rowVal, colVal, 1, 0, boardState);
		utility += canWin(player, rowVal, colVal, -1, 0, boardState);
		//check in vertical direction for possible wins
		utility += canWin(player, rowVal, colVal, 0, 1, boardState);
		utility += canWin(player, rowVal, colVal, 0, -1, boardState);
		//check diagonals for possible wins
		utility += canWin(player, rowVal, colVal, 1, 1, boardState);
		utility += canWin(player, rowVal, colVal, 1, -1, boardState);
		utility += canWin(player, rowVal, colVal, -1, 1, boardState);
		utility += canWin(player, rowVal, colVal, -1, -1, boardState);
		

		return utility;
		
	}

	
	/**
	 *  returns the number of winning possibilities on board
	 *  for player minus the number of winning possibilities
	 *  on board for the player's opponent
	 *  
	 *  @param player either the human or AI using the consts AI(0) or USER(1)
	*/
	//evaluate the quality of a move for "player"
	//player can be AI or person
	//assumes it is passed a valid move
	public int utility(char player, Board board) {
		int playerUtility = 0;
		int opponentUtility = 0;
		char[][] boardState = board.getArray();
		
		
		//if a state is a winning state for the player
		//return arbitrarily large utility
		if (board.checkForWin(player) == true) {
			return MAX;
		}
		//do the opposite for opponent winning
		if (board.checkForWin(opponent(player)) == true) {
			return MIN;
		}
		
		//find number of possible winning moves for player
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				playerUtility += spotUtility(player, i, j, boardState);
			}
		}
		//find number of possible winning moves for player's opponent
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				opponentUtility += spotUtility(opponent(player), i, j, boardState);
			}
		}
		return playerUtility - opponentUtility;
	}	

	/**
	 * The main minimax function, now featuring alpha-beta pruning!
	 * @param player the play whom you want to find an optimal move for
	 * @param board a Board object representing the state of a game board
	 * @param depth how deep to search
	 * @param alpha
	 * @param beta
	 * @return a Move object containing a move (the column in which to play) as well as the move's utility
	 */
	public Move minimax(char player, Board board, int depth, int alpha, int beta) {
		int bestCol = -1;
		Board copyBoard;
		int simUtility;

		
		// Check terminal states
		if(depth == 0 || isGameOver()) {
			return new Move(-1, utility(player, board));
		}
		
		for(int i = 0; i < n; i++) {
			if(!mainBoard.isColFull(i)) {
				copyBoard = new Board(board);
				copyBoard.play(i, player);
				// Recursively go through minimax function
				simUtility = (-1 * minimax(opponent(player), copyBoard, depth - 1, (-1 * beta), (-1 * alpha)).getUtility());
				// Find the best Column to place piece
				if(simUtility > alpha ) {
					alpha = simUtility;
					bestCol = i;
				}
				if(alpha >= beta) {
					break;	// Prune
				}
			}
		}
		// Return the best move
		return new Move(bestCol, alpha);
	}
}
