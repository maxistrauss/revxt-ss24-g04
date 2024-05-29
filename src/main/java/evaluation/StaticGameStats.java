package evaluation;

import board.*;
import game.Game;

import java.util.Arrays;

public class StaticGameStats {
    /**
     * The number of players this game usually starts with.
     */
    private final int initialPlayers;

    /**
     * The number of overwrite stones each player has in the beginning.
     */
    private final int initialOverwriteStones;

    /**
     * The number of bombs each player has in the beginning.
     */
    private final int initialBombs;

    /**
     * The number of steps from the center of the explosion a bomb blows tiles up.
     */
    private final int bombRadius;
    /**
     * Tile Ratings are calculated once in the constructor
     */
    private final int[][] tileRatings;

    private final byte OVERWRITE_STONE_RATING = 5;

    private final byte BOMB_RATING;



    public StaticGameStats(Game game, int initialPlayers, int initialOverwriteStones,
                           int initialBombs, int bombRadius) {
        this.initialPlayers = initialPlayers;
        this.initialOverwriteStones = initialOverwriteStones;
        this.initialBombs = initialBombs;
        this.bombRadius = bombRadius;
        BOMB_RATING = calculateBombRating();
        tileRatings = calculateTileRatings(game);
    }

    private int[][] calculateTileRatings(Game game) {
        int width = game.getWidth();
        int height = game.getHeight();
        int[][] tileRatings = new int[height][width];
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                if(game.getTile(new Coordinates(j, i)) == Tile.WALL){
                    tileRatings[i][j] = 0;
                    continue;
                }
                tileRatings[i][j] = calculateParicularTileRating(j, i, game);
            }
        }
        return tileRatings;
    }

    /**
     * Default tile rating: 1
     * Checks all 4 angles on, whether they are only open in one direction
     * Such angles are valuable, as tiles can "attack" in the angle but can not be "attacked"
     * 1 bonuspoint for every angle, that fullfills the criterion
     * Rating for WALLS: 0
     * @param x X-coordinate
     * @param y Y-coordinate
     * @return Tile rating as an Integer
     */

    private int calculateParicularTileRating(int x, int y, Game game){
        int tileRating = 1;
        Direction[] halfOfAllDirections = Arrays.copyOfRange(Direction.values(),0, 4);
        for(Direction direction : halfOfAllDirections) {
            TileReader tileReader = new TileReader(game, new Coordinates(x, y), direction);
            TileReader oppositeDirectionTileReader =
                    new TileReader(game, new Coordinates(x, y), direction.getOppositeDirection());
            if(tileReader.hasNext() && !oppositeDirectionTileReader.hasNext()){
                tileRating++;
                continue;
            }
            if(!tileReader.hasNext() && oppositeDirectionTileReader.hasNext()){
                tileRating++;
            }
        }
        return tileRating;
    }

    private byte calculateBombRating(){
        double result = Math.pow(0.9, bombRadius) * Math.pow(2 * bombRadius + 1, 2)
                        * Math.sqrt(1 - (double) 1 / initialPlayers);
        return (byte) (result + 0.5);
    }

    /**
     * Getter
     */
    public int getBombRadius() {
        return bombRadius;
    }

    public int getInitialPlayers() {
        return initialPlayers;
    }

    public int getInitialOverwriteStones() {
        return initialOverwriteStones;
    }

    public int getInitialBombs() {
        return initialBombs;
    }

    public int[][] getTileRatings() {
        return tileRatings;
    }
    public byte getOverWriteSoneRating(){
        return OVERWRITE_STONE_RATING;
    }
    public byte getBombRating(){
        return BOMB_RATING;
    }
}
