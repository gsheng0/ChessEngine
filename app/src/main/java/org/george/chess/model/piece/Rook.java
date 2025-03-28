package org.george.chess.model.piece;

import java.util.List;

public class Rook extends Piece {
    public Rook(int side) {
        super(side, true, List.of(-1, 1, -8, 8));
    }
}
