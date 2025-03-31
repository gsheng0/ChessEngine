package org.george.chess.util;

import static org.george.chess.util.Constants.*;

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
            return pieces;
        }
    }

    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private static boolean isUpperCase(char c) {
        return c >= 'A' && c <= 'Z';
    }
}
