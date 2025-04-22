package org.george.chess.service;

import static org.george.chess.util.Constants.*;
import org.george.chess.model.Position;

public class AttackMaskServiceImpl {
    
    public long generateAttackMask(final Position position, final int side){
        long attackMask = 0l;
        final long[][] pieces = position.getPieces();
        for(int p = PAWN; p <= KING; p++){
            attackMask |= pieces[side][p]; 
        }
        return attackMask;
    }
}
