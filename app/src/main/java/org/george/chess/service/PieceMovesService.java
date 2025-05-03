package org.george.chess.service;

import java.util.List;
import org.george.chess.model.Move;
import org.george.chess.model.Position;

public interface PieceMovesService {
    public List<Move> generate(final Position position, final int tile, final int side);
}
