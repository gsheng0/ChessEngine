package org.george.chess.model;

/**
Promotion     
|     From tile     Piece
000 0 000000 000000 000 0
    |        To tile    Side
    En Passant
3   1 6      6      3   1

 */

import static org.george.chess.util.Constants.*;

public class Move {
        private static final long PIECE_SHIFT = 1l;
        private static final long TO_TILE_SHIFT = 4l;
        private static final long FROM_TILE_SHIFT = 10l;
        private static final long EN_PASSANT_SHIFT = 16l;
        private static final long PROMOTION_SHIFT = 17l;

        private static final long SIDE = 1l;
        private static final long PIECE = 7l << PIECE_SHIFT;
        private static final long TO_TILE = 63l << TO_TILE_SHIFT;
        private static final long FROM_TILE = 63l << FROM_TILE_SHIFT;
        private static final long EN_PASSANT = 1l << EN_PASSANT_SHIFT;
        private static final long PROMOTION_PIECE = 7l << PROMOTION_SHIFT;

        public static final long KING_SIDE_CASTLE = 6l;
        public static final long QUEEN_SIDE_CASTLE = 7l;


        public final long move;

        private Move(long move){
                this.move = move;
        }

        public static Move of(long move){
                return new Move(move);
        }

        public int side(){
                return (int)(move & SIDE);
        }

        public int piece(){
                return (int)((move & PIECE) >> PIECE_SHIFT);
        }

        public long to(){
                return (move & TO_TILE) >> TO_TILE_SHIFT;
        }       

        public long from(){
                return (move & FROM_TILE) >> FROM_TILE_SHIFT;
        }

        public boolean isEnPassant(){
                return (move & EN_PASSANT) > 0;
        }

        public boolean isCastle(){
                return piece() > KING;
        }

        public int promotionPiece(){
                return (int)((move & PROMOTION_PIECE) >> PROMOTION_SHIFT);
        }

        public static Move QUEEN_SIDE_CASTLE(int side){
                return Move.of((QUEEN_SIDE_CASTLE << 1) | side);
        }

        public static Move KING_SIDE_CASTLE(int side){
                return Move.of((KING_SIDE_CASTLE << 1) | side);
        }
}
