package org.george.chess.model.piece;

import java.util.List;

import static org.george.chess.util.Constants.BLACK;

public class BlackPawn extends Piece {
    public BlackPawn() {
        super(BLACK, false, List.of(8), List.of(7, 9));
    }
}
