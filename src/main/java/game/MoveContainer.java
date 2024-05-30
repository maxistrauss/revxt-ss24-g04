package game;

import move.Move;

import java.util.Set;

/**
 * Contains non-overwrite- and overwrite moves separately
 */
public record MoveContainer(Set<Move> nonOverwriteMoves, Set<Move> overwriteMoves) {
}
