package org.george.chess.movegenerator;

import static org.george.chess.util.Constants.*;

import org.george.chess.util.Logger;

public class MoveGenerator {

    private static final Logger<MoveGenerator> logger = Logger.of(
        MoveGenerator.class
    );

    public long[][] generateMoves(final long[][] pieces, final int side) {
        long[] all = new long[2];
        for (int i = 0; i < pieces[WHITE].length; i++) {
            all[WHITE] |= pieces[WHITE][i];
            all[BLACK] |= pieces[BLACK][i];
        }
        long[][] moves = new long[2][KING + 1];
        moves[side][PAWN] = generatePawnMoves(pieces, all, side);
        moves[side][KNIGHT] = generateKnightMoves(pieces, all, side);
        moves[side][BISHOP] = generateBishopMoves(pieces, all, side);
        moves[side][ROOK] = generateRookMoves(pieces, all, side);
        moves[side][QUEEN] = generateQueenMoves(pieces, all, side);
        moves[side][KING] = generateKingMoves(pieces, all, side);
        return moves;
    }

    public long generateRookMoves(
        final long[][] pieces,
        final long[] all,
        final int side
    ) {
        logger.log(Long.toBinaryString(pieces[WHITE][ROOK]));
        logger.logBitBoard("White rooks", pieces[WHITE][ROOK]);
        logger.logBitBoard("White pieces", all[WHITE]);
        logger.logBitBoard("Black pieces", all[BLACK]);
        long out = 0l;
        for (int i = 0; i < ROOK_SHIFTS.length; i++) {
            long rook = pieces[side][ROOK];
            while (rook != 0) {
                rook = rook & ROOK_SHIFT_PRUNING[i];
                rook = ROOK_SHIFTS[i] < 0
                    ? rook >>> (-1 * ROOK_SHIFTS[i])
                    : rook << ROOK_SHIFTS[i];
                rook &= ~all[side];
                out |= rook;
                rook ^= rook & all[1 - side];
            }
        }
        logger.logBitBoard("Rook moves", out);

        return out;
    }

    long generatePawnMoves(
        final long[][] pieces,
        final long[] all,
        final int side
    ) {
        long moves = 0l;
        return moves;
    }

    long generateKnightMoves(
        final long[][] pieces,
        final long[] all,
        final int side
    ) {
        long moves = 0l;
        return moves;
    }

    long generateBishopMoves(
        final long[][] pieces,
        final long[] all,
        final int side
    ) {
        long moves = 0l;
        return moves;
    }

    long generateQueenMoves(
        final long[][] pieces,
        final long[] all,
        final int side
    ) {
        long moves = 0l;
        return moves;
    }

    long generateKingMoves(
        final long[][] pieces,
        final long[] all,
        final int side
    ) {
        long moves = 0l;
        return moves;
    }
}
