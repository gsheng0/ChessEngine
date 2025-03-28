package org.george.chess.model.piece;

import java.util.List;

public class Bishop extends Piece {
    public Bishop(int side) {
        super(side, true, List.of(-9, 9, -7, 7));
    }
}
