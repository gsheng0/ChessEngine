package org.george.chess.movegenerator;

import org.george.chess.model.Board;
import java.util.List;
import static org.george.chess.util.Consants.BLACK;
import static org.george.chess.util.Consants.WHITE;

public class MoveGenerator {

    public static final long A_FILE = 0x8080808080808080L;
    public static final long H_FILE = 0x0101010101010101L;
    public static final int PAWN = 0;
    public static final int KNIGHT = 1;
    public static final int BISHOP = 2;
    public static final int ROOK = 3;
    public static final int QUEEN = 4;
    public static final int KING = 5;
    public static final int[] ROOK_SHIFTS = new int[] { 8, -8, 1, -1 };
    public static final long[] PRUNING = new int[] { (long)(Math.pow(2, 63) >> 8, (long)(Math.pow(2, 63) << 8, H_FILE, A_HILE }; 
    
    long generateMoves(long[][] pieces, int side){
        long[] white = pieces[WHITE];
        long[] black = pieces[BLALCK];
        long W = white[PAWN] | white[KNIGHT] | white[BISHOP] | white[ROOK] | white[QUEEN] | white[KING];
        long B = black[PAWN] | black[KNIGHT] | black[BISHOP] | black[ROOK] | black[QUEEN] | black[KING];
    }
    long generateRook(long[][] pieces, long[] cum, int side){
        long out = 0;
        for(int i = 0; i < ROOK_SHIFTS.length; i++){
            long rook = pieces[side][ROOK];
            while(rook > 0){
                rook &= PRUNING[i];
                rook = rook << ROOK_SHIFTS[i];
                long occupied = ~cum[1- side];
                rook &= occupied;
                out |= rook;
                long captured = rook & b;
                rook ^= captured;
            }
        }

        
        
        

    }


    
}
