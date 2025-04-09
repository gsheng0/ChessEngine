package org.george.chess.model;

/**
17 
Promotion     
|     10            3
|     From tile     Piece
000 0 000000 000000 000 0
    |        To tile    Side
    |        4          0
    En Passant
    16
 */

import static org.george.chess.util.Constants.*;

import org.george.chess.util.BitBoard;

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

        public static Move parseUCIString(Position position, String move){
                System.out.println("Parsing move: " + move);
                char[] from = move.substring(0, 2).toCharArray();
                char[] to = move.substring(2).toCharArray();
                System.out.println(new String(from));
                System.out.println(new String(to));
                int fromTile = BitBoard.namedTileToIndex(from);
                int toTile = BitBoard.namedTileToIndex(to);
                long[][] pieces = position.getPieces();
                long out = 0l;

                //Check for Castles
                if(to[1] == from[1] && to[1] == '8' && (pieces[BLACK][KING] & (1l << fromTile)) != 0 && Math.abs(from[0] - from[1]) > 1){ 
                        if(from[0] == 'e' && to[0] == 'g'){
                                return Move.KING_SIDE_CASTLE(BLACK);
                        } else{
                                return Move.QUEEN_SIDE_CASTLE(BLACK);
                        }
                } else if(to[1] == from[1] && to[1] == '1' && (pieces[WHITE][KING] & (1l << fromTile)) != 0 && Math.abs(from[0] - from[1]) > 1){ 
                        if(from[0] == 'e' && to[0] == 'g'){
                                return Move.KING_SIDE_CASTLE(BLACK);
                        } else{
                                return Move.QUEEN_SIDE_CASTLE(BLACK);
                        }
                }

                if(to.length > 2){
                        char p = to[2];
                        if(p == 'q'){
                                out |= QUEEN << PROMOTION_SHIFT;
                        } else if(p == 'r'){
                                out |= ROOK << PROMOTION_SHIFT;
                        } else if(p == 'b'){
                                out |= BISHOP << PROMOTION_SHIFT;
                        } else if(p == 'n'){
                                out |= KNIGHT << PROMOTION_SHIFT;
                        }
                }
                long tileMask = 1l << fromTile;
                for(long piece = PAWN; piece <= KING; piece++){
                        for(int side = WHITE; side <= BLACK; side++){
                                if((tileMask & pieces[side][(int)piece]) == 0){
                                        continue;
                                }
                                out |= side | (piece << PIECE_SHIFT);
                        }
                }
                
                out |= toTile << TO_TILE_SHIFT;
                out |= fromTile << FROM_TILE_SHIFT;

                return Move.of(out);
        }
}
