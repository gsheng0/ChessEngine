package org.george.chess.service;

import org.george.chess.model.Move;
import org.george.chess.model.Position;
import java.util.List;
import java.util.ArrayList;

import static org.george.chess.util.Constants.*;

public class KnightMovesService implements PieceMovesService{
    public List<Move> generate(final Position position, final int tile, final int side){
        final List<Move> moves = new ArrayList<>();
        final long[][] pieces = position.getPieces();
        final long tileMask = 1l << tile;
        final long[] all = {0l, 0l}; //TODO: Add all field to position class
        final Move.Builder moveBuilder = Move.builder()
            .withSide(side)
            .withPiece(KNIGHT)
            .withFromTile(tile);

        for(int i = 0; i < SHIFTS[KNIGHT].length; i++){
            int shift = SHIFTS[KNIGHT][i];
            long pruned = tileMask & PRUNES[KNIGHT][i];
            pruned = shift < 0 ? pruned >>> -shift : pruned << shift;
            pruned &= ~all[side];
            if(pruned != 0){
                moves.add(moveBuilder.withToTile(tile + shift).build());
            }
        }
        return moves;
    }
}
