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
        if(piece == PAWN){
            logger.log("Piece on tile " + tile + " is a pawn");
            return generatePawnMoves(position, tile, side, all);
        } else if(piece == ROOK || piece == QUEEN || piece == BISHOP){
            logger.log("Piece on tile " + tile + " is a sliding piece");
            return generateSlidingPieceMoves(position, tile, piece, side, all);
        } else if(piece == KNIGHT){
            logger.log("Piece on tile " + tile + " is a knight");
            return generateKnightMoves(position, tile, side, all);
        } else if(piece == KING){
            logger.log("Piece on tile " + tile + " is a king");
            return generateKingMoves(position, tile, side, all);
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
        if((tileMask & (FIRST_RANK << 8)) != 0 && moves.size() == 1){
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
        //Pawn isn't on a rank that is a two tile push
        if((tileMask & EN_PASSANT_RANK[side]) == 0){ 
            return moves;
        }

        if(Math.abs(tile - enPassantTile) == 1){
            moves.add(moveBuilder.withToTile(enPassantTile + (side == WHITE ? 8 : -8)).withEnPassant(1).build());
        }
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
