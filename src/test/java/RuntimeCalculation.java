import board.Coordinates;
import game.Game;
import game.GameFactory;
import game.MoveCalculator;
import game.MoveExecutor;
import move.*;

public class RuntimeCalculation {
    public static void main(String[] args) {
        // final stats for which runtime should be calculated
        final String GAMEPATH = "/Users/ludwig/Desktop/testMaps/example_edit.map";
        final Move MOVE = new ChoiceMove(1, new Coordinates(9, 7), 2);

        Game game = GameFactory.createFromFile(GAMEPATH);
        long time = System.currentTimeMillis();
        MoveCalculator.getValidMovesForPlayer(game, game.getCurrentPlayerNumber());
        System.out.println();
        System.out.print("Took ");
        System.out.println(System.currentTimeMillis() - time + "ms to calculate all valid moves");
        time = System.nanoTime();
        MoveExecutor.executeMove(game, MOVE);
        System.out.println();
        System.out.print("Took ");
        System.out.println((System.nanoTime() - time) / 1000 + "us to execute move");

    }
}
