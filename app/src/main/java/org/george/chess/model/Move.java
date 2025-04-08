package org.george.chess.model;

/**
  From tile     Piece
0 000000 000000 000 0
En pass  To tile    Side
 */
public class Move {
        private static final long pieceShift = 1l;
        private static final long side = 1l;
        private static final long toTileShift = 4l;
        private static final long fromTileShift = 10l;
        private static final long enPassantShift = 16l;

        private static final long piece = 7l << pieceShift;
        private static final long toTile = 63l << toTileShift;
        private static final long fromTile = 63l << fromTileShift;
        private static final long enPassant = 1 << enPassantShift;
        public final long move;

        private Move(long move){
                this.move = move;
        }

        public Move of(long move){
                return new Move(move);
        }

        public int side(){
                return (int)(move & side);
        }

        public int piece(){
                return (int)((move & piece) >> pieceShift);
        }

        public long to(){
                return (move & toTile) >> toTileShift;
        }       

        public long from(){
                return (move & fromTile) >> fromTileShift;
        }

        public boolean isEnPassant(){
                return (move & enPassant) > 0;
        }
}
