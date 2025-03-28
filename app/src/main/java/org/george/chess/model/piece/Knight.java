package org.george.chess.model.piece;

import java.util.List;

public class Knight extends Piece {
    public Knight(int side) {
        super(side, false, true, List.of(-17, -15, -10, -6, 6, 10, 15, 17));
    }
}
