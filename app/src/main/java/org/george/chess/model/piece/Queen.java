package org.george.chess.model.piece;

import java.util.List;

public class Queen extends Piece {
    public Queen(int side) {
        super(side, true, List.of(-7, 7, -9, 9, -1, 1, -8, 8));
    }
}
