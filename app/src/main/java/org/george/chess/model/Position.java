package org.george.chess.model;

import static org.george.chess.util.Constants.*;

public class Position {
        public final long[][] pieces;
        public final int turn;
        public final int enPassant;
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

        public void apply(Move move){ 
                //add conditions for checking for castles
                long to = 1 << move.to();
                pieces[move.side()][move.piece()] ^= 1 << move.from();
                pieces[move.side()][move.piece()] ^= to;

                for(int piece = PAWN; piece <= KING; piece++){
                        pieces[1 - move.side()][piece] &= ~to;
                }

                if(move.isEnPassant()){
                        pieces[1 - move.side()][PAWN] ^= 1 << (move.from() + (move.side() == WHITE ? -8 : 8));
                }
                //figure out promotion logic
        }
}
