package org.george.chess.model;

import static org.george.chess.util.Constants.*;
import java.util.Arrays;

import org.george.chess.util.BitBoard;
import org.george.chess.util.Logger;

//TODO: Fix loss of information issue when undoing moves
public class Position {
        private static final Logger logger = Logger.of(Position.class);
        private long[][] pieces;
        private int turn;
        private int enPassant;
        private boolean[][] castle = { {true, true}, {true, true} };
        private static final int KING_SIDE = 0;
        private static final int QUEEN_SIDE = 1;
        public Position(final long[][] pieces, final int turn, final int enPassant){
                this.pieces = pieces;
                this.turn = turn;
                this.enPassant = enPassant;
                this.castle = castle;
        }

        public Position(final long[][] pieces, final int turn, final int enPassant, final boolean[][] castle){
                this.pieces = pieces;
                this.turn = turn;
                this.enPassant = enPassant;
                this.castle = castle;
        }

        public static Position START_POSITION = new Position(BitBoard.GET_START_POSITION(), 0, 0);

        public boolean canCastleKingSide(int side){
                return castle[side][KING_SIDE];
        }

        public boolean canCastleQueenSide(int side){
                return castle[side][QUEEN_SIDE];
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
                final int sideShift = move.side() == WHITE ? 0 : 56;
                final int side = move.side();
                final int piece = move.piece();
                final long to = move.to();
                final long from = move.from();
                final long fromTileMask = 1l << from;

                //castles
                if(move.get() == Move.KING_SIDE_CASTLE(side).get()){
                        castle[side] = new boolean[] { false, false };
                        pieces[side][KING] = 1l << (1 + sideShift);
                        pieces[side][ROOK] &=  ~(1l << sideShift);
                        pieces[side][ROOK] |= 1l << (2 + sideShift);
                        return;
                } else if(move.get() == Move.QUEEN_SIDE_CASTLE(side).get()){
                        castle[side] = new boolean[] { false, false };
                        pieces[side][KING] = 1l << (5 + sideShift);
                        pieces[side][ROOK] &= ~(1l << (7 + sideShift));
                        pieces[side][ROOK] |= 1l << (4 + sideShift);
                        return;
                }
                
                if(piece == KING){
                        castle[side] = new boolean[] { false, false };
                }

                //normal move
                long toTileMask = 1l << to;
                pieces[side][piece] &= ~(1l << from);
                pieces[side][piece] |= toTileMask;

                //removing captured piece
                for(int p = PAWN; p<= KING; p++){
                        pieces[1 - side][p] &= ~toTileMask;
                }

                //removing en passant'd pawn
                if(move.isEnPassant()){
                        pieces[1 - side][PAWN] &= ~(1l << (to + (side == WHITE ? -8 : 8)));
                }

                //setting possible en passant'able pawn square
                enPassant = -1;
                if(piece == PAWN && 16 == Math.abs(to - from)){
                        enPassant = (int)to;
                }
                
                //pawn promotion
                if(move.promotionPiece() > PAWN){
                        pieces[side][PAWN] &= ~fromTileMask;
                        pieces[side][move.promotionPiece()] |= toTileMask;
                }
        }

        //TODO: Figure out a good way to keep track of castling availability
        public void unapply(Move move){
                final int side = move.side();
                final long to = move.to();
                final long from = move.from();
                final int piece = move.piece();
                final int sideShift = getSideShift(side);
                final long fromTileMask = 1l << from;
                final long toTileMask = 1l << to;

                //castles
                if(move.get() == Move.KING_SIDE_CASTLE(side).get()){
                        pieces[side][KING] = 1l << (3 + sideShift);
                        pieces[side][ROOK] |= 1l << sideShift;
                        pieces[side][ROOK] &= ~(1l << (2 + sideShift));
                        return;
                } else if(move.get() == Move.QUEEN_SIDE_CASTLE(side).get()){
                        pieces[side][KING] = 1l << (3 + sideShift);
                        pieces[side][ROOK] |= 1l << (7 + sideShift);
                        pieces[side][ROOK] &= ~(1l << (4 + sideShift));
                        return;
                }

                //Restoring captured piece
                if(move.captured() > 0l){
                        pieces[1 - side][move.captured() - 1] |= fromTileMask;
                }

                //moving piece back
                pieces[side][piece] &= ~(toTileMask);
                pieces[side][piece] |= fromTileMask;

                //putting enpassant'd pawn back
                if(move.isEnPassant()){
                        pieces[1 - side][PAWN] |= 1l << (to + (side == WHITE ? -8 : 8));
                }

                //undoing pawn promotion
                if(move.promotionPiece() > PAWN){
                        pieces[side][PAWN] |= fromTileMask;
                        pieces[side][move.promotionPiece()] &= ~toTileMask;
                }

        }

        private int getSideShift(int side){
                return side == WHITE ? 0 : 56;
        }

        public long[][] getPieces(){
                return pieces;
        }

        public int getTurn(){
                return turn;
        }

        /**
         * Returns the tile index of the most recent move, if it is a 2 square pawn push. Returns -1 otherwise
         */
        public int getEnPassant(){
                return enPassant;
        }
}
