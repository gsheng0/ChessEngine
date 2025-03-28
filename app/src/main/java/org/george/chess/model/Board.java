package org.george.chess.model;

import java.util.Map;
import java.util.HashMap;
import org.george.chess.model.piece.Piece;

public class Board {
    private Map<Integer, Piece>[] pieces;

    public Board() {
        this.pieces = new HashMap[2];
    }
}
