package org.george.chess.model.piece;

import java.util.List;
import static org.george.chess.util.Constants.WHITE;

public class WhitePawn extends Piece {
    public WhitePawn() {
        super(WHITE, false, List.of(-8), List.of(-7, -9));
    }
}
