package org.george.chess.service;

import org.george.chess.model.Move;
import org.george.chess.model.Position;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import static org.george.chess.util.Constants.*;

public class SlidingPieceMovesService{
    public List<Move> generateSlidingPieceMoves(final Position position, final int tile, final int piece, final int side, final long[] all){
        final List<Move> moves = new ArrayList<>();
        final long tileMask = 1l << tile;
        final Move.Builder moveBuilder = Move.builder()
            .withSide(side)
            .withPiece(piece)
            .withFromTile(tile);

        for(int i = 0; i < SHIFTS[piece].length; i++){
            int shift = SHIFTS[piece][i];
            int currentTile = tile;
            long tracker = tileMask;
            while(tracker != 0){
                tracker &= PRUNES[piece][i];
                tracker = shift < 0 ? tracker >>> -shift : tracker << shift;
                currentTile += shift;
                tracker &= ~all[side];
                if(tracker != 0){
                    moves.add(moveBuilder.withToTile(currentTile).build());
                }
                tracker ^= tracker & all[1 - side];
            }
        }
        return moves;
    }
}
