package org.george.chess.movegenerator;

import static org.george.chess.util.Constants.*;

import org.george.chess.util.Logger;

public class MoveGenerator {

    private static final Logger<MoveGenerator> logger = Logger.of(MoveGenerator.class);

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

    private long generateSlidingPieceMoves(
            final long[][] pieces,
            final long[] all,
            final int side,
            final int piece) {
        long out = 0L;
        for (int i = 0; i < SHIFTS[piece].length; i++) {
            long tracker = pieces[side][piece];
            int shift = SHIFTS[piece][i];
            while (tracker != 0) {
                tracker &= PRUNES[piece][i];
                tracker = shift < 0 ? tracker >>> (-shift) : tracker << shift;
                tracker &= ~all[side];
                out |= tracker;
                tracker ^= tracker & all[1 - side];
            }
        }
        return out;
    }

    private long generateNonSlidingPieceMoves(
            final long[][] pieces,
            final long[] all,
            final int side,
            final int piece) {
        long out = 0L;
        for (int i = 0; i < SHIFTS[piece].length; i++) {
            int shift = SHIFTS[piece][i];
            long pruned = (pieces[side][piece] & PRUNES[piece][i]);
            pruned = shift < 0 ? pruned >>> (-shift) : pruned << shift;
            pruned &= ~all[side];
            out |= pruned;
        }
        return out;
    }

    long generatePawnMoves(final long[][] pieces, final long[] all, final int side) {
        long moves = 0L;
        long pawns = pieces[side][PAWN];
        long allPieces = all[WHITE] | all[BLACK];
        //Captures
        for(int i = 0; i < PAWN_ATTACKS[side].length; i++){
            long pruned = pawns & PAWN_ATTACK_PRUNES[side][i];
            moves |= (side == WHITE ? pruned << PAWN_ATTACKS[WHITE][i] : pruned >>> PAWN_ATTACKS[BLACK][i]) & all[1 - side];
        }

        //Normal Moves
        long unmovedPawns = pawns & STARTING_RANK[side];
        pawns ^= unmovedPawns;
        unmovedPawns = (side == WHITE ? unmovedPawns << 8 : unmovedPawns >>> 8) & ~allPieces;
        moves |= unmovedPawns;
        pawns |= unmovedPawns;
        pawns = (side == WHITE ? pawns << 8 : pawns >>> 8) & ~allPieces;
        moves |= pawns;

        return moves;
    }

    long generateKnightMoves(final long[][] pieces, final long[] all, final int side) {
        return generateNonSlidingPieceMoves(pieces, all, side, KNIGHT);
    }

    long generateBishopMoves(final long[][] pieces, final long[] all, final int side) {
        return generateSlidingPieceMoves(pieces, all, side, BISHOP);
    }

    long generateRookMoves(final long[][] pieces, final long[] all, final int side) {
        return generateSlidingPieceMoves(pieces, all, side, ROOK);
    }

    long generateQueenMoves(final long[][] pieces, final long[] all, final int side) {
        return generateSlidingPieceMoves(pieces, all, side, QUEEN);
    }

    long generateKingMoves(final long[][] pieces, final long[] all, final int side) {
        return generateNonSlidingPieceMoves(pieces, all, side, KING);
    }
}
