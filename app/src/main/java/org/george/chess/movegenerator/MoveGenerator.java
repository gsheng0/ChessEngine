package org.george.chess.movegenerator;

import static org.george.chess.util.Constants.*;

import org.george.chess.util.Logger;
import org.george.chess.model.Move;
import org.george.chess.model.Position;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class MoveGenerator {

    private static final Logger<MoveGenerator> logger = Logger.of(MoveGenerator.class);

    public List<Move> generateMoves(final Position position, final int side){
        final List<Move> moves = new ArrayList<>();
        final long[] all = new long[2];
        final long[][] pieces = position.getPieces();
        for(int piece = PAWN; piece <= KING; piece++){
            all[side] |= pieces[side][piece];
            all[1 - side] |= pieces[1 - side][piece];
        }
        long remaining = all[side];
        while(remaining != 0){
            int tile = 63 - Long.numberOfLeadingZeros(remaining & -remaining);
            moves.addAll(generateMovesForTile(position, tile, all));
            remaining &= remaining - 1;
        }
        return moves;
    }

    public List<Move> generateMovesForTile(final Position position, final int tile, final long[] all){
        int piece = -1;
        int side = -1;
        final long tileMask = 1l << tile;
        final long[][] pieces = position.getPieces();
        for(int s = WHITE; s <= BLACK; s++){
            for(int p = PAWN; p <= KING; p++){
                all[s] |= pieces[s][p];
                if((pieces[s][p] & tileMask) == 0){
                    continue;
                }
                side = s;
                piece = p;
                break;
            }
        }

        return Collections.emptyList();
    }

    public List<Move> generateSlidingPieceMoves(final Position position, final int tile, final int piece, final int side, final long[] all){
        final List<Move> moves = new ArrayList<>();
        final long[][] pieces = position.getPieces();
        final long tileMask = 1l << tile;
        final Move.Builder moveBuilder = Move.builder().withSide(side)
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

    //TODO: Add attack/defend maps to help move generation
    public List<Move> generateKingMoves(final Position position, final int tile, final int side, final long[] all){
        final List<Move> moves = new ArrayList<>();
        final long[][] pieces = position.getPieces();
        final long tileMask = 1l << tile;
        final Move.Builder moveBuilder = Move.builder()
            .withSide(side)
            .withPiece(KING)
            .withFromTile(tile);

        for(int i = 0; i < SHIFTS[KING].length; i++){
            int shift = SHIFTS[KING][i];
            long pruned = tileMask & PRUNES[KING][i];
            pruned = shift < 0 ? pruned >>> -shift : pruned << shift;
            pruned &= ~all[side];
            if(pruned != 0){
                moves.add(moveBuilder.withToTile(tile + shift).build());
            }
        }

        return moves;
    }

    public List<Move> generateKnightMoves(final Position position, final int tile, final int side, final long[] all){
        final List<Move> moves = new ArrayList<>();
        final long[][] pieces = position.getPieces();
        final long tileMask = 1l << tile;
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

    public List<Move> generatePawnMoves(final Position position, final int tile, final int side, final long[] all){
        final List<Move> moves = new ArrayList<>();
        final long[][] pieces = position.getPieces();
        final long tileMask = 1l << tile;

        return moves;
    }


    public long[][] generateMoves(final long[][] pieces, final int side) {
        long[][] moves = new long[2][KING + 1];
        long[] all = new long[2];
        for (int i = 0; i < pieces[WHITE].length; i++) {
            all[WHITE] |= pieces[WHITE][i];
            all[BLACK] |= pieces[BLACK][i];
        }
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

    private long generatePawnMoves(final long[][] pieces, final long[] all, final int side) {
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
