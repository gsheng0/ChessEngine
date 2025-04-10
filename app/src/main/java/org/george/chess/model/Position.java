package org.george.chess.model;

import static org.george.chess.util.Constants.*;
import java.util.Arrays;

import org.george.chess.util.BitBoard;

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

        public static Position START_POSITION = new Position(BitBoard.GET_START_POSITION(), 0, 0, new boolean[2][2]);

        public boolean canCastleKingSide(){
                return castle[turn][KING_SIDE];
        }

        public boolean canCastleQueenSide(){
                return castle[turn][QUEEN_SIDE];
        }

        public Position copy(){
                long[][] piecesCopy = new long[2][KING + 1];
                for(int s = WHITE; s <= BLACK; s++){
                        for(int p = PAWN; p <= KING; p++){
                                piecesCopy[s][p] = pieces[s][p];
                        }
                }
                boolean[][] castleCopy = new boolean[2][2];
                for(int i = 0; i < 2; i++){
                        for(int j = 0; j < 2; j++){
                                castleCopy[i][j] = castle[i][j];
                        }
                }
                return new Position(piecesCopy, turn, enPassant, castleCopy);
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
