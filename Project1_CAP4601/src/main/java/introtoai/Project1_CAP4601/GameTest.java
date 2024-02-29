package introtoai.Project1_CAP4601;

//enter 5 4 1 in console when running tests
public class GameTest {
    public static void main(String[] args) {
    	System.out.println("running tests");
        // Initialize the GameController
        GameController gameController = new GameController();
        
        // Make some moves for the user and the AI
        gameController.moveUser(4); // User moves to column 0
        gameController.moveAI();    // AI makes a move

        gameController.moveUser(4); // User moves to column 1
        gameController.moveAI();    // AI makes a move

        gameController.moveUser(4); // User moves to column 2
        gameController.moveAI();    // AI makes a move
        
        gameController.moveUser(4);
        gameController.moveAI();
        
        System.out.println(gameController.utility(1, gameController.getBoard()));

        // Print the board state after each move
        System.out.println("tests ran succesfully");
        System.out.println(gameController.boardString());
        
    }
}

