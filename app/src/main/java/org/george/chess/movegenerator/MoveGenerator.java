package org.george.chess.movegenerator;

import org.george.chess.model.Board;
import java.util.List;

public interface MoveGenerator {
    List<Board> generateMoves(int side, Board board);
}
