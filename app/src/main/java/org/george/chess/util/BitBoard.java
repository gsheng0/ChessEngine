package org.george.chess.util;

import static org.george.chess.util.Constants.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Arrays;

import org.george.chess.model.Position;

import java.util.List;

public class BitBoard {

    public static long write(final List<Integer> indices) {
        long board = 0l;
        for (int index : indices) {
            board |= 1l << index;
        }
        return board;
    }

    public static long consecutiveOnes(int len) {
        return (long) (Math.pow(2, len) - 1);
    }

    //rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1

    public static long[][] fenToBitBoard(String FEN) {
        long[][] bitBoard = new long[2][KING + 1];
        String[] chunks = FEN.split(" ");
        String[] ranks = chunks[0].split("/");
        for (int i = 0; i < 8; i++) {
            char[] R = ranks[i].toCharArray();
            int start = 63 - (i * 8);
            for (int j = 0; j < R.length; j++) {
                if (isDigit(R[j])) {
                    start -= R[j] - '0';
                    continue;
                }
            }
        }
        return bitBoard;
    }

    public static void drawBitBoard(final Graphics g, final Position position, final ImageObserver observer){
        final long[][] pieces = position.getPieces();
        for(int side = WHITE; side <= BLACK; side++){
            for(int piece = PAWN; piece <= KING; piece++){
                long occupancy = pieces[side][piece];
                while(occupancy != 0){
                    int tile = 63 - Long.numberOfLeadingZeros(occupancy & -occupancy);
                    //System.out.println("Tile " + tile + " has a " + (side == WHITE ? "WHITE" : "BLACK") + " " + PIECE_NAMES[piece]);
                    occupancy &= occupancy - 1;
                    int x = 7 - tile % 8;
                    int y = 7 - tile / 8;
                    g.drawImage(PIECE_IMAGES[side][piece], x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, observer);
                }
            }
        }
    }

    public static int namedTileToIndex(char[] move){
        return (move[1] - '1') * 8 + 7 - (move[0] - 'a');
    }
    public static char[] indexToNamedTile(int tile){
        char l = (char)('a' + (7 - (tile%8)));
        char n = (char)('1' + tile/8);
        return new char[] { l, n };
    }

    public static class BoardBuilder {

        private long[][] pieces;

        public BoardBuilder() {
            pieces = new long[2][KING + 1];
        }

        public BoardBuilder with(int side, int piece, long board) {
            pieces[side][piece] = board;
            return this;
        }

        public BoardBuilder withAdditionally(int side, int piece, long board) {
            pieces[side][piece] |= board;
            return this;
        }

        public BoardBuilder clear() {
            pieces = new long[2][KING + 1];
            return this;
        }

        public long[][] get() {
            final long[][] out = new long[2][KING + 1];
            for(int i = 0; i < out.length; i++){
                for(int j = 0; j < out[0].length; j++){
                    out[i][j] = pieces[i][j];
                }
            }
            return out;
        }
    }

    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private static boolean isUpperCase(char c) {
        return c >= 'A' && c <= 'Z';
    }
}
