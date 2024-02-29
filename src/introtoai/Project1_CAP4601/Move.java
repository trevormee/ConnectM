/** @file  Move.java
 *  @brief This class will assist the evaluation method
 *  	   when a player makes a move
 *  
 *  @author David Martinez & Trevor Mee
 *  @date   2/25/2024
 *  @info   CAP4601
 * 
 */

package introtoai.Project1_CAP4601;

public class Move {
	
	// Private instance variables
	private int utility;
	private int colIndex;
	
	
	/**
	 * 	Parameterized constructor using the 
	 *  utility (evaluation) method
	 *  
	 *  @param index of the column 
	 *  @param utility
	 */
	public Move(int colIndex, int utility) {
		this.utility = utility;
		this.colIndex = colIndex;
	}
	
	
	// Getter for the utility
	public int getUtility() {
		return utility;
	}
	
	
	// Getter for the index of the column
	public int getColIndex() {
		return colIndex;
	}
}
