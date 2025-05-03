package org.george.chess.service;

import java.util.ArrayList;
import java.util.List;

import org.george.chess.model.Move;
import org.george.chess.model.Position;

import static org.george.chess.util.Constants.*;

public class KingMovesService implements PieceMovesService{
    private final AttackMaskServiceImpl attackMaskService = new AttackMaskServiceImpl();
    public List<Move> generate(final Position position, final int tile, final int side){
        final List<Move> moves = new ArrayList<>();
        final long[][] pieces = position.getPieces();
        final long tileMask = 1l << tile;
        final long[] all = {0l, 0l};//TODO: fix
        final Move.Builder moveBuilder = Move.builder()
            .withSide(side)
            .withPiece(KING)
            .withFromTile(tile);

        final long attackMask = attackMaskService.generateAttackMask(position, 1 - side);
        //currently in check
        if((attackMask & tileMask) != 0){

        }

        for(int i = 0; i < SHIFTS[KING].length; i++){
            int shift = SHIFTS[KING][i];
            long pruned = tileMask & PRUNES[KING][i];
            pruned = shift < 0 ? pruned >>> -shift : pruned << shift;
            pruned &= ~(all[side] | attackMask);
            if(pruned != 0){
                moves.add(moveBuilder.withToTile(tile + shift).build());
            }
        }
        //check for castles
        long occupancyMask = all[WHITE] | all[BLACK];
        if(position.canCastleKingSide(side) && (occupancyMask & KING_SIDE_CASTLE_SELECTORS[side]) == 0){
            moves.add(Move.KING_SIDE_CASTLE(side));
        }
        if(position.canCastleQueenSide(side) && (occupancyMask & QUEEN_SIDE_CASTLE_SELECTORS[side]) == 0){
            moves.add(Move.QUEEN_SIDE_CASTLE(side));
        }

        return moves;
    }
}
