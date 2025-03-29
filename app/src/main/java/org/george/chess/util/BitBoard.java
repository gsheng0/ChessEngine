package org.george.chess.util;

import java.util.List;
import static org.george.chess.util.Constants.*;

public class BitBoard {
    public static long write(final List<Integer> indices) {
        long board = 0l;
        for (int index : indices) {
            board |= 1l << index;
        }
        return board;
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
            pieces[side][piece] = board;
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
}
