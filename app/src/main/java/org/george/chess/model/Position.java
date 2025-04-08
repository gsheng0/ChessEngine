package org.george.chess.model;

import static org.george.chess.util.Constants.*;

public class Position {
        private long[][] pieces;
        private int turn;
        private int enPassant;
        private final boolean[][] castle;
        private static final int KING_SIDE = 0;
        private static final int QUEEN_SIDE = 1;
        public Position(final long[][] pieces, final int turn, final int enPassant, final boolean[][] castle){
                this.pieces = pieces;
                this.turn = turn;
                this.enPassant = enPassant;
                this.castle = castle;
        }

        public boolean canCastleKingSide(){
                return castle[turn][KING_SIDE];
        }

        public boolean canCastleQueenSide(){
                return castle[turn][QUEEN_SIDE];
        }

        //move this into an interace implementation to enable other variants of chess
        public void apply(Move move){ 
                int sideShift = move.side() == WHITE ? 0 : 56;
                //castles
                if(move.piece() == Move.KING_SIDE_CASTLE){
                        pieces[move.side()][KING] = 1l << (1 + sideShift);
                        pieces[move.side()][ROOK] &=  ~(1l << sideShift);
                        pieces[move.side()][ROOK] |= 1l << (2 + sideShift);
                        return;
                } else if(move.piece() == Move.QUEEN_SIDE_CASTLE){
                        pieces[move.side()][KING] = 1l << (5 + sideShift);
                        pieces[move.side()][ROOK] &= ~(1l << (7 + sideShift));
                        pieces[move.side()][ROOK] |= 1l << (4 + sideShift);
                        return;
                }

                //normal move
                long to = 1l << move.to();
                pieces[move.side()][move.piece()] ^= 1l << move.from();
                pieces[move.side()][move.piece()] ^= to;

                //removing captured piece
                for(int piece = PAWN; piece <= KING; piece++){
                        pieces[1 - move.side()][piece] &= ~to;
                }

                //removing en passant'd pawn
                if(move.isEnPassant()){
                        pieces[1 - move.side()][PAWN] ^= 1l << (move.from() + (move.side() == WHITE ? -8 : 8));
                }

                //setting possible en passant'able pawn square
                enPassant = -1;
                if(move.piece() == PAWN && 16 == Math.abs(move.to() - move.from())){
                        enPassant = (int)move.from();
                }
                
                //pawn promotion
                if(move.promotionPiece() > PAWN){
                        pieces[move.side()][PAWN] &= ~(1l << move.to());
                        pieces[move.side()][move.promotionPiece()] |= 1l << move.to();
                }

        }

        public long[][] getPieces(){
                return pieces;
        }

        public int getTurn(){
                return turn;
        }

        /**
         * Returns the tile index of the most recent move, if it is a 2 square pawn push
         */
        public int getEnPassant(){
                return enPassant;
        }
}
