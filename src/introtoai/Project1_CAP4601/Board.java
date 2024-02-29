/** @file  Board.java
 *  @brief This class is used to initialize the 
 *  	   game board, represent the game board
 *         in an appealing way, and check states 
 *         of the board (terminal state, column full,
 *         etc.)
 *         
 * @author David Martinez & Trevor Mee
 * @date   2/25/2024
 * @info   CAP4601
 * 
 */

package introtoai.Project1_CAP4601;

public class Board {
	
	// Private instance variables
	private int size;
	private char[][] boardArray;
	private int m;	//the m in connect-m
	private int lastMove;
	
	
	/**
	 * Parameterized constructor
	 * 
	 * @param size of board
	 * @param the m in connect-m
	 */
	public Board(int size, int m) {
		this.size = size;
		this.m = m;
		lastMove = -1;
		boardArray = new char[size][size];
		
		//initialize array to be filled with spaces to facilitate printing
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				boardArray[i][j] = ' ';
			}
		}
	}
	
	
	// Getter for the board
	public char[][] getArray(){
		return boardArray;
	}
	
	
	/**
	 * 	update the state of the board
	 * 
	 * @param original board of the game
	 */
	public Board(Board originalBoard) {
		this.size = originalBoard.size;
		this.m = originalBoard.m;
		this.lastMove = originalBoard.lastMove;
		boardArray = new char[size][size];
		
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				this.boardArray[i][j] = originalBoard.boardArray[i][j];
			}
		}
	}
	
	/**
	 * 	Check if a column is full 
	 * 
	 * @param column in check
	 * 
	 */
	public boolean isColFull(int col) {
		
		for(int row = 0; row < size; row++) {
			if(boardArray[row][col] == ' ') {
				return false;
			}
		}	
		return true;
	}
	
	
	/**
	 * 	dropping a piece into the game board in
	 *  specified column
	 *  
	 *  @param column to which piece to be placed
	 *  @param AI or Human symbol
	 */
	public void play(int col, char playerChar) {
		
		if(isColFull(col)) {
			return;
		}
		
        for (int row = (size - 1); row >= 0; row--) {
            if (boardArray[row][col] == ' ') {
            	boardArray[row][col] = playerChar;
            	lastMove = row;
                break;
            }
        }
	}
	
	/**
	 *  Method checking for a win by either player
	 *  
	 *  @param player either the human or AI
	*/ 
	public boolean checkForWin(char player) {
		
		char symbol = player;//(player == USER) ? USER_SYMBOL : AI_SYMBOL;

		// Check a horizontal win
		for(int row = 0; row < size; row++) {
			for(int col = 0; col <= size - m; col++) {
				boolean win = true;
				for(int i = 0; i < m; i++) {
					if(boardArray[row][col + i] != symbol) {
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
		for(int col = 0; col < size; col++) {
			for(int row = 0; row <= size - m; row++) {
				boolean win = true;
				for(int i = 0; i < m; i++) {
					if(boardArray[row + i][col] != symbol) {
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
		for (int row = 0; row <= size - m; row++) {
	        for (int col = 0; col <= size - m; col++) {
	            boolean win = true;
	            for (int i = 0; i < m; i++) {
	                if (boardArray[row + i][col + i] != symbol) {
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
		for (int row = 0; row <= size - m; row++) {
	        for (int col = size - 1; col >= m - 1; col--) {
	            boolean win = true;
	            for (int i = 0; i < m; i++) {
	                if (boardArray[row + i][col - i] != symbol) {
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
	 * 	Method checking if the board is full
	 */
	public boolean isBoardFull() {
		for(int row = 0; row < size; row++) {
			for(int col = 0; col < size; col++) {
				if(boardArray[row][col] == ' ')
					return false;
			}
		}
		return true;
	}
	
	
	/** 
	 *  Method to validate/verify a legal move
	 *  
	 *  @param player human or ai who is making the move
	 *  @param col column for piece to be placed
	 */
	public boolean isValidMove(int player, int col) {
		if(isColFull(col)) {
			return false;
		}else if (isBoardFull()) {
			return false;
		}else {
			return true; // Valid move
		}
	}
	
	
	/**
	 *  returns a visually appealing board representation
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		//print top border
		for (int i = 0; i < boardArray.length; i++) {
			sb.append(" ");
			sb.append(i);
		}
		sb.append("\n");
		
		sb.append("╔");
		for (int i = 0; i < (boardArray.length - 1); i++) {
			sb.append("═╦");
		}
		sb.append("═╗");
		sb.append("\n");
		
		for (int i = 0; i < (boardArray.length - 1); i++) {
			
			for (int j = 0; j < boardArray.length; j++) {
				sb.append("║");
				sb.append(boardArray[i][j]);
			}
			sb.append("║");
			sb.append("\n");
			
			sb.append("╠");
			for (int k = 0; k < (boardArray.length - 1); k++) {
				sb.append("═╬");
			}
			sb.append("═╣");
			sb.append("\n");
			
		}
		
		for (int j = 0; j < boardArray.length; j++) {
			sb.append("║");
			sb.append(boardArray[boardArray.length - 1][j]);
		}
		sb.append("║");
		sb.append("\n");
		
		//print bottom
		sb.append("╚");
		for (int i = 0; i < (boardArray.length - 1); i++) {
			sb.append("═╩");
		}
		sb.append("═╝");
		sb.append("\n");
		
		// return the board 
		return sb.toString();
	}
}
