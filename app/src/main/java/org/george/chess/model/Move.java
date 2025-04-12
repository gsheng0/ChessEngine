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

        public static Builder builder(){
                return new Builder();
        }

        public String toString(){
                final StringBuilder builder = new StringBuilder();
                if(side() == WHITE){
                        builder.append("White");
                } else{
                        builder.append("Black");
                }
                builder.append(" ");

                int piece = this.piece();
                if(piece == KING_SIDE_CASTLE){
                        return builder.append("castles King side").toString();
                } else if(piece == QUEEN_SIDE_CASTLE){
                        return builder.append("castles Queen side").toString();
                }
                builder.append("moves ");
                builder.append(PIECE_NAMES[piece]);
                builder.append(" from ");
                builder.append(BitBoard.indexToNamedTile((int)from()));
                builder.append(" to ");
                builder.append(BitBoard.indexToNamedTile((int)to()));
                return builder.toString();
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
                char[] from = move.substring(0, 2).toCharArray();
                char[] to = move.substring(2).toCharArray();
                int fromTile = BitBoard.namedTileToIndex(from);
                int toTile = BitBoard.namedTileToIndex(to);
                long[][] pieces = position.getPieces();
                long out = 0l;

                //Check for Castles
                if(to[RANK] == from[RANK] && to[RANK] == '8' && (pieces[BLACK][KING] & (1l << fromTile)) != 0 && Math.abs(to[FILE] - from[FILE]) > 1){ 
                        if(from[FILE] == 'e' && to[FILE] == 'g'){
                                return Move.KING_SIDE_CASTLE(BLACK);
                        } else{
                                return Move.QUEEN_SIDE_CASTLE(BLACK);
                        }
                } else if(to[RANK] == from[RANK] && to[RANK] == '1' && (pieces[WHITE][KING] & (1l << fromTile)) != 0 && Math.abs(to[FILE] - from[FILE]) > 1){ 
                        if(from[FILE] == 'e' && to[FILE] == 'g'){
                                return Move.KING_SIDE_CASTLE(WHITE);
                        } else{
                                return Move.QUEEN_SIDE_CASTLE(WHITE);
                        }
                }

                //Checking for pawn promotion
                char promotion = to.length > 2 ? to[2] : '-';
                if(promotion == 'q'){
                        out |= QUEEN << PROMOTION_SHIFT;
                } else if(promotion == 'r'){
                        out |= ROOK << PROMOTION_SHIFT;
                } else if(promotion == 'b'){
                        out |= BISHOP << PROMOTION_SHIFT;
                } else if(promotion == 'n'){
                        out |= KNIGHT << PROMOTION_SHIFT;
                }

                //Finding which piece is being moved
                long tileMask = 1l << fromTile;
                int piece = -1;
                int side = -1;
                for(int p = PAWN; p <= KING; p++){
                        for(int s = WHITE; s <= BLACK; s++){
                                if((tileMask & pieces[s][p]) == 0){
                                        continue;
                                }
                                out |= s | (p << PIECE_SHIFT);
                                piece = p;
                                side = s;
                                break;
                        }
                }

                //Checking for En Passant
                if(piece == PAWN 
                        && ((1l << toTile) & pieces[1 - side][PAWN]) == 0 //Is capture tile empty
                        && from[FILE] != to[FILE]){      //Is pawn capture
                        out |= 1l << EN_PASSANT_SHIFT;
                }
                
                out |= toTile << TO_TILE_SHIFT;
                out |= fromTile << FROM_TILE_SHIFT;

                return Move.of(out);
        }

        public static class Builder {
                private int piece;
                private int to;
                private int from;
                private int enPassant;
                private int promotion;
                private int side;
                private Builder(){
                        this.piece = 0;
                        this.to = 0;
                        this.from = 0;
                        this.enPassant = 0;
                        this.promotion = 0;
                        this.side = 0;
                }

                public Builder clear(){
                        this.piece = 0;
                        this.to = 0;
                        this.from = 0;
                        this.enPassant = 0;
                        this.promotion = 0;
                        this.side = 0;
                        return this;
                }
                
                public Builder withPiece(final int piece){
                        this.piece = piece;
                        return this;
                }

                public Builder withFromTile(final int fromTile){
                        this.from= fromTile;
                        return this;
                }

                public Builder withToTile(final int toTile){
                        this.to = toTile;
                        return this;
                }

                public Builder withPromotionPiece(final int promotionPiece){
                        this.promotion= promotionPiece;
                        return this;
                }

                public Builder withEnPassant(final int enPassant){
                        this.enPassant = enPassant;
                        return this;
                }
                
                public Builder withSide(final int side){
                        this.side = side;
                        return this;
                } 

                public Move build(){
                        long move = side | 
                                    piece << PIECE_SHIFT |
                                    to << TO_TILE_SHIFT |
                                    from << FROM_TILE_SHIFT |
                                    promotion << PROMOTION_SHIFT |
                                    enPassant << EN_PASSANT_SHIFT;
                        return Move.of(move);
                }
        }
}
