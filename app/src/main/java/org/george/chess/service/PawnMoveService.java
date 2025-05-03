package org.george.chess.service;

import org.george.chess.model.Move;
import org.george.chess.model.Position;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import static org.george.chess.util.Constants.*;

public class PawnMoveService{
    public List<Move> generatePawnMoves(final Position position, final int tile, final int side, final long[] all){
        final List<Move> moves = new ArrayList<>();
        final long[][] pieces = position.getPieces();
        final long tileMask = 1l << tile;
        final Move.Builder moveBuilder = Move.builder()
            .withSide(side)
            .withPiece(PAWN)
            .withFromTile(tile);
        
        long pruned = tileMask & PAWN_PRUNES[side];
        if(pruned == 0){
            return Collections.emptyList();
        }

        //Normal moves
        int shift = PAWN_SHIFTS[side];
        pruned = shift < 0 ? pruned >>> -shift : pruned << shift;
        pruned &= ~(all[WHITE] | all[BLACK]);
        if(pruned != 0){
            moves.add(moveBuilder.withToTile(tile + shift).build());
        }

        //Double pawn push
        if((tileMask & (PAWN_START_RANKS[side])) != 0 && moves.size() == 1){
            pruned = shift < 0 ? pruned >>> -shift : pruned << shift;
            pruned &= ~(all[WHITE] | all[BLACK]);
            if(pruned != 0){
                moves.add(moveBuilder.withToTile(tile + (2 * shift)).build());
            }
        }

        //Captures
        for(int i = 0; i < PAWN_ATTACKS[side].length; i++){
            pruned = tileMask & PAWN_ATTACK_PRUNES[side][i];
            if(pruned == 0){
                continue;
            }
            shift = PAWN_ATTACKS[side][i];
            int toTile = tile + PAWN_ATTACKS[side][i];
            long toTileMask = 1l << toTile;
            if((toTileMask & all[1 - side]) == 0){ //No enemy piece on target tile
                continue;
            }
            moves.add(moveBuilder.withToTile(toTile).build());
        }

        //En Passant
        int enPassantTile = position.getEnPassant();
        //If the last move wasn't a 2 tile pawn push
        if(enPassantTile == -1){
            return moves;
        }

        if(Math.abs(tile - enPassantTile) == 1){
            moves.add(moveBuilder.withToTile(enPassantTile + (side == WHITE ? 8 : -8)).isEnPassant(true).build());
        }
        return moves;
    }

}
