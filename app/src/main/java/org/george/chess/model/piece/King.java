package org.george.chess.model.piece;

import java.util.List;

public class King extends Piece {
    public King(int side) {
        super(side, false, List.of(-7, 7, -9, 9, -1, 1, -8, 8));
    }
}
