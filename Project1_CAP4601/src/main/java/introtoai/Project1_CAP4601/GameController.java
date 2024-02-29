/** @file GameController.java
 *  @brief This class contains the main connectM 
 *  	   game logic to be used in the program.
 * 
 * 	@author Trevor Mee
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
	private int n;//size of the board
	private int m;//the "m" in connect-m
	private int h;
	private boolean userTurn;
	
	
	// Private final instance variables
	private final char USER_SYMBOL = 'X';
	private final char AI_SYMBOL = 'O';
	private final int AI = 0;
	private final int USER = 1;
	
	
    /**
     * 	Default constructor that uses the the getUserInput
     *  method to obtain n,m & h; create an empty board; and 
     *  determine the first player of the game from the 
     *  firstPlayer() method
     */
	public GameController() {
		getUserInput();
		board = new char[n][n];
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
        scnr.close();
	}
	
	
	// Getter for the variable n
	public int getN() {
		return n;
	}
	
	// Setter for the variable n
	public void setN(int n) {
		this.n = n;
	}
	
	// Getter for the variable m
	public int getM() {
		return m;
	}
	
	// Setter for the variable m
	public void setM(int m) {
		this.m = m;
	}
	
	// Getter for the variable h
	public int getH() {
		return h;
	}
	
	// Setter for the variable h
	public void setH(int h ) {
		this.h = h;
	}
	
	// Method to check if it is user or AI's turn
	public boolean isUserTurn() {
		return userTurn;
	}
	
	//Getter for the game board
	public char[][] getBoard(){
		return board;
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
	 *  Method used to represent the game board
	 *  as a String in an appealing way
	 */
	public String boardString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" ");
		for(int i = 0; i < board.length; i++) {
			sb.append(i + " ");
		}
		sb.append("\n");
		for(int row = 0; row < board.length; row++) {
			sb.append("|");
			for(int col = 0; col < board[row].length; col++) {
				sb.append(board[row][col]);
				sb.append("|");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	/**
	 * 	Method to update the state of the game
	 */
	public String printUpdateState() {
		if(checkForWin(USER)) {
			return "Client Wins";
		}else if (checkForWin(AI)) {
			return "Server Wins!";
		}else if(isBoardFull()) {
			return "It is a draw";
		}else {
			return "server: move";
		}
	}
	
	/** 
	 *  Method to validate/verify a legal move
	 *  
	 *  @param player human or ai who is making the move
	 *  @param col column for piece to be placed
	 */
	public boolean move(int player, int col) {
		if(isColFull(col)) {
			return false;
		}else if (isBoardFull()) {
			return false;
		}else {
			return true; // Valid move
		}
	}
	

	/**
	 *  Method that has the logic for a user to
	 *  make a move on the game board
	 *  
	 *  @param col the column chosen by user to put piece
	 *
	 */
	public void moveUser(int col) {
		char symbol = (userTurn) ? USER_SYMBOL : AI_SYMBOL;
		
		if(isColFull(col)) {
			return;
		}
		
        for (int row = n - 1; row >= 0; row--) {
            if (board[row][col] == '\0') {
                board[row][col] = symbol;
                break;
            }
        }
        userTurn = !userTurn;
	}
	
	
	/**
	 *  Method that has the logic for the AI to
	 *  make a move on the game board
	 */
	 public void moveAI() {
	        char symbol = AI_SYMBOL;
	        
	        List<Integer> availableColumns = new ArrayList<>();
	        for (int col = 0; col < n; col++) {
	            if (board[0][col] == '\0') { 
	                availableColumns.add(col);
	            }
	        }
	        Random random = new Random();
	        int randomColumnIndex = random.nextInt(availableColumns.size());
	        int selectedColumn = availableColumns.get(randomColumnIndex);
	        
	        for (int row = n - 1; row >= 0; row--) {
	            if (board[row][selectedColumn] == '\0') {
	                board[row][selectedColumn] = symbol;
	                break;
	            }
	        }
	        
	        userTurn = !userTurn;
	 }
	
	 
	/**
	 *  Method checking if the game is over
	 */
	public boolean isGameOver() {
		return checkForWin(USER) || checkForWin(AI) || isBoardFull();
	}
	

	/**
	 * 	Method checking if the board is full
	 */
	public boolean isBoardFull() {
		for(int row = 0; row < n; row++) {
			for(int col = 0; col < n; col++) {
				if(board[row][col] == '\0')
					return false;
			}
		}
		return true;
	}

	
	/**
	 * 	Check if a column is full 
	 */
	public boolean isColFull(int col) {
		
		for(int row = 0; row < n; row++) {
			if(board[row][col] == '\0') {
				return false;
			}
		}	
		return true;
	}
	
	
	/**
	 *  Method checking for a win by either player
	 *  
	 *  @param player either the human or AI
	*/ 
	public boolean checkForWin(int player) {
		
		char symbol = (player == USER) ? USER_SYMBOL : AI_SYMBOL;

		// Check a horizontal win
		for(int row = 0; row < n; row++) {
			for(int col = 0; col <= n - m; col++) {
				boolean win = true;
				for(int i = 0; i < m; i++) {
					if(board[row][col + i] != symbol) {
						win = false;
						break;
					}
				}
				if(win) {
					return true;
				}
			}
		}
		
		// Check a vertical win
		for(int col = 0; col < n; col++) {
			for(int row = 0; row <= n - m; row++) {
				boolean win = true;
				for(int i = 0; i < m; i++) {
					if(board[row + i][col] != symbol) {
						win = false;
						break;
					}
				}
				if(win) {
					return true;
				}
			}
		}
		
		// Check a diagonal win from left to right
		for (int row = 0; row <= n - m; row++) {
	        for (int col = 0; col <= n - m; col++) {
	            boolean win = true;
	            for (int i = 0; i < m; i++) {
	                if (board[row + i][col + i] != symbol) {
	                    win = false;
	                    break;
	                }
	            }
	            if (win) {
	            	return true;
	            }
	        }
	    }
		
		// Check a diagonal win from right to left
		for (int row = 0; row <= n - m; row++) {
	        for (int col = n - 1; col >= m - 1; col--) {
	            boolean win = true;
	            for (int i = 0; i < m; i++) {
	                if (board[row + i][col - i] != symbol) {
	                    win = false;
	                    break;
	                }
	            }
	            if (win) {
	            	return true;
	            }
	        }
	    }
		
		return false;
	}
	

	
	/**
	 *  Method checking how many winning moves are possible
	 *  for a spot on the board
	 *  
	 *  @param player either the human or AI using the consts AI(0) or USER(1)
	 *  @param rowVal the column index of the spot to check (0 to n-1)
	 *  @param colVal the row index of the spot to check (0 to n-1)
	*/ 
	//evaluate the quality of a move for "player"
	//player can be AI or person
	//assumes it is passed a valid move
	public int spotUtility(int player, int rowVal, int colVal, char[][] boardState) {
		int leftmostBound = colVal - (m - 1);
		int rightmostBound = colVal + (m - 1);
		int topmostBound = rowVal - (m - 1);
		int bottommostBound = rowVal + (m - 1);
		
		int utility = 0;
		char playerStone;
		char opponentStone;
		
		if(player == USER) {
			playerStone = USER_SYMBOL;
			opponentStone = AI_SYMBOL;
		}
		else {
			playerStone = AI_SYMBOL;
			opponentStone = USER_SYMBOL;
		}
		
		//if spot is blank or has opponent piece the utility is 0
		if (boardState[rowVal][colVal] != playerStone) {
			return 0;
		}
		
		//if there are m-1 spaces left of a player's piece and those
		//spaces have no opponent pieces add 1 to utility
		if (leftmostBound >= 0) {
			int opponentPieces = 0;
			for (int i = leftmostBound; i < colVal; i++) {
				if (boardState[rowVal][i] == opponentStone){
					opponentPieces++;
				}
			}
			if (opponentPieces == 0) {
				utility++;
			}
		}
		//same as above but searching right
		if (rightmostBound < n) {
			int opponentPieces = 0;
			for (int i = colVal; i <= rightmostBound; i++) {
				if (boardState[rowVal][i] == opponentStone){
					opponentPieces++;
				}
			}
			if (opponentPieces == 0) {
				utility++;
			}
		}
		//same as above but searching up
		if (topmostBound >= 0) {
			int opponentPieces = 0;
			for (int i = topmostBound; i < rowVal; i++) {
				if (boardState[i][colVal] == opponentStone){
					opponentPieces++;
				}
			}
			if (opponentPieces == 0) {
				utility++;
			}
		}
		//same as above but searching down
		if (bottommostBound < n) {
			int opponentPieces = 0;
			for (int i = rowVal; i <= bottommostBound; i++) {
				if (boardState[i][colVal] == opponentStone){
					opponentPieces++;
				}
			}
			if (opponentPieces == 0) {
				utility++;
			}
		}
		//eventually this should also check diagonally
		//but this will work for now
		
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
	public int utility(int player, char[][] boardState) {
		int playerUtility = 0;
		int opponentUtility = 0;
		int opponent;
		
		if (player == 0) {
			opponent = 1;
		}else {
			opponent = 0;
		}
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				playerUtility += spotUtility(player, i, j, boardState);
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				opponentUtility += spotUtility(opponent, i, j, boardState);
			}
		}
		return playerUtility - opponentUtility;
	}

}