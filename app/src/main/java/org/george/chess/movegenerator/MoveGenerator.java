package org.george.chess.movegenerator;

import static org.george.chess.util.Constants.*;
import org.george.chess.util.Logger;

public class MoveGenerator {
    private static final Logger<MoveGenerator> logger = Logger.of(MoveGenerator.class);

    public long[][] generateMoves(final long[][] pieces, final int side) {
        long[] white = pieces[WHITE];
        long[] black = pieces[BLACK];
        long[] cum = new long[2];
        for (int i = 0; i < pieces[WHITE].length; i++) {
            cum[WHITE] |= pieces[WHITE][i];
            cum[BLACK] |= pieces[BLACK][i];
        }
        long[][] moves = new long[2][KING + 1];
        moves[side][PAWN] = generatePawnMoves(pieces, cum, side);
        moves[side][KNIGHT] = generateKnightMoves(pieces, cum, side);
        moves[side][BISHOP] = generateBishopMoves(pieces, cum, side);
        moves[side][ROOK] = generateRookMoves(pieces, cum, side);
        moves[side][QUEEN] = generateQueenMoves(pieces, cum, side);
        moves[side][KING] = generateKingMoves(pieces, cum, side);
        return moves;
    }

    public long generateRookMoves(final long[][] pieces, final long[] cum, final int side) {
        logger.log(Long.toBinaryString(pieces[WHITE][ROOK]));
        logger.logBitBoard("White rooks", pieces[WHITE][ROOK]);
        logger.logBitBoard("White pieces", cum[WHITE]);
        logger.logBitBoard("Black pieces", cum[BLACK]);
        long out = 0l;
        for (int i = 0; i < ROOK_SHIFTS.length; i++) {
            long rook = pieces[side][ROOK];
            while (rook > 0) {
                rook &= ROOK_SHIFT_PRUNING[i];
                rook = rook << ROOK_SHIFTS[i];
                long occupied = ~cum[side];
                rook &= occupied;
                out |= rook;
                long captured = rook & cum[1 - side];
                rook ^= captured;
            }
        }
        logger.logBitBoard("Rook moves", out);

        return out;

    }

    long generatePawnMoves(final long[][] pieces, final long[] cum, final int side) {
        long moves = 0l;
        return moves;
    }

    long generateKnightMoves(final long[][] pieces, final long[] cum, final int side) {
        long moves = 0l;
        return moves;
    }

    long generateBishopMoves(final long[][] pieces, final long[] cum, final int side) {
        long moves = 0l;
        return moves;
    }

    long generateQueenMoves(final long[][] pieces, final long[] cum, final int side) {
        long moves = 0l;
        return moves;
    }

    long generateKingMoves(final long[][] pieces, final long[] cum, final int side) {
        long moves = 0l;
        return moves;
    }

}
